package zzk.webproject.air;

import org.apache.tomcat.websocket.WsSession;
import zzk.webproject.service.ChatMessageService;
import zzk.webproject.service.Services;
import zzk.webproject.util.StringUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/chat", encoders = {AirMessageEncoder.class}, decoders = {AirMessageDecoder.class})
public class ChatEndpoint {
    private static final Logger LOGGER = Logger.getLogger(ChatEndpoint.class.getName());

    private static ScheduledExecutorService heartBeatScheduledService = Executors.newSingleThreadScheduledExecutor();

    private static List<Runnable> heartBeatTestRunnable = Collections.singletonList(new Runnable() {
        @Override
        public void run() {
            AirMessage heartBeat = new AirMessage("TIP");
            heartBeat.setBroadcastMessage(Boolean.TRUE);
            heartBeat.setType(MessageType.HEART_BEAT);
            ENDPOINTS.forEach(endpoint -> endpoint.sendObject(endpoint, heartBeat));
            LOGGER.info("heart-beat-test-running");
        }
    });

    private static boolean heartBeatSwitch = false;

    private static final TimeUnit HEART_BEAT_UNIT = TimeUnit.MINUTES;

    private static final Long HEAT_BEAT_PERIOD = 1L;

    private static ChatMessageService chatMessageService = Services.getChatMessageService();

    private static final LinkedList<ChatEndpoint> ENDPOINTS = new LinkedList<>();

    private Session session;

    static {
        startHeartBeat();
    }

    private static void startHeartBeat() {
        LOGGER.info("heart beat test task is start");
        heartBeatTestRunnable.forEach(runnable -> heartBeatScheduledService.scheduleWithFixedDelay(runnable, 5, HEAT_BEAT_PERIOD, HEART_BEAT_UNIT));
        heartBeatSwitch = true;
    }

    private static void stopHeartBeat() {
        heartBeatScheduledService.shutdownNow();
        heartBeatSwitch = false;
        LOGGER.info("heart beat test task is end");
    }

    @OnOpen
    public void open(Session session) {
        this.session = session;
        registerEndpoint(this);

        String username = getUsernameByWsSession(session);
        AirMessage message = new AirMessage();
        message.setType(MessageType.SYSTEM_MESSAGE);
        message.setContent("online");
        message.setFromAccount(username);
        broadcast(message);
        recordMessage(message);

        pushUnacceptedMessage(session);
        pushOnlineFriend(session);

        LOGGER.log(Level.INFO, String.format("用户%s连接到了websocket", username));
    }

    @OnClose
    public void end() {
        String username = getUsernameByWsSession(session);
        AirMessage message = new AirMessage();
        message.setType(MessageType.SYSTEM_MESSAGE);
        message.setContent("offline");
        message.setFromAccount(username);
        broadcast(message);
        recordMessage(message);

        unregisterEndpoint(this);
        session = null;
        LOGGER.log(Level.INFO, String.format("用户%s从websocket断开", username));
    }

    @OnMessage
    public void message(AirMessage airMessage) {
        if (MessageType.valueOf(airMessage.getType()) == MessageType.HEART_BEAT) {
            return;
        }
        String toAccount = airMessage.getToAccount();
        if (notBroadcastMessage(airMessage) && messageNotBlack(toAccount)) {
            ChatEndpoint endpoint = getEndPointByUsername(toAccount);
            sendObject(endpoint, airMessage);
        } else {
            broadcast(airMessage);
        }
        recordMessage(airMessage);
        LOGGER.log(Level.INFO, airMessage.toString());
    }

    @OnError
    public void error(Throwable throwable) {
        LOGGER.log(Level.SEVERE, throwable.getMessage());
        end();
    }

    private boolean messageNotBlack(String toAccount) {
        return StringUtil.nonBlank(toAccount);
    }

    private boolean notBroadcastMessage(AirMessage airMessage) {
        return !airMessage.getBroadcastMessage();
    }

    /**
     * 从wssession获取用户名
     *
     * @param session
     * @return
     */
    private String getUsernameByWsSession(Session session) {
        return Roster.getUsername(
                ((WsSession) session).getHttpSessionId()
        );
    }

    private ChatEndpoint getEndPointByUsername(String username) {
        Optional<ChatEndpoint> chatEndpoint = ENDPOINTS.stream()
                .filter(endpoint -> username.equals(getUsernameByWsSession(endpoint.session)))
                .findFirst();
        return chatEndpoint.get();
    }

    /**
     * 记录连接到websocket的终端
     *
     * @param endpoint
     */
    private void registerEndpoint(ChatEndpoint endpoint) {
        ENDPOINTS.add(endpoint);
    }

    /**
     * 删除终端
     *
     * @param endpoint
     */
    private void unregisterEndpoint(ChatEndpoint endpoint) {
        ENDPOINTS.remove(endpoint);
    }

    private void sendObject(ChatEndpoint endpoint, Object message) {
        try {
            endpoint.session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播Object类型的信息，不过格式会是Json的
     *
     * @param object
     */
    private void broadcast(Object object) {
        broadcast(object, this);
    }

    /**
     * 向除了@param exclude的所有终端发送Object信息
     *
     * @param object
     * @param exclude
     */
    private void broadcast(Object object, ChatEndpoint exclude) {
        for (ChatEndpoint endpoint : ENDPOINTS) {
            if (exclude == endpoint) {
                continue;
            }
            sendObject(endpoint, object);
        }
    }

    /**
     * 记录接受的信息
     * 用于推送历史信息
     *
     * @param message
     */
    private void recordMessage(AirMessage message) {
        chatMessageService.save(message);
    }

    /**
     * 推送历史信息
     *
     * @param session
     */
    private void pushUnacceptedMessage(Session session) {
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        chatMessageService.list(airMessage -> MessageType.SHORT_MESSAGE.name().equals(airMessage.getType())
                || MessageType.REFERENCE.name().equals(airMessage.getType())
        ).forEach(airMessage -> {
            try {
                basicRemote.sendObject(airMessage);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 推送在线的用户
     *
     * @param session
     */
    private void pushOnlineFriend(Session session) {
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        for (ChatEndpoint endpoint : ENDPOINTS) {
            try {
                Session currentSession = endpoint.session;
                if (currentSession == session) {
                    continue;
                }
                AirMessage airMessage = new AirMessage("online");
                airMessage.setType(MessageType.SYSTEM_MESSAGE);
                airMessage.setFromAccount(getUsernameByWsSession(currentSession));
                basicRemote.sendObject(airMessage);
            } catch (EncodeException | IOException e) {
                LOGGER.severe(e.getMessage());
            }
        }
    }

    public static void close() {
        stopHeartBeat();
    }
}

