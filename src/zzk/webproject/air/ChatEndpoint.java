package zzk.webproject.air;

import org.apache.tomcat.websocket.WsSession;
import zzk.webproject.util.OnlineUserUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/chat", encoders = {AirMessageEncoder.class}, decoders = {AirMessageDecoder.class})
public class ChatEndpoint {
    public static final Logger logger = Logger.getLogger(ChatEndpoint.class.getName());

    private static final int LASTED_MESSAGE_CAPACITY = 6;
    private static final LinkedList<AirMessage> LASTED_MESSAGE = new LinkedList<>();
    private static final LinkedList<ChatEndpoint> CHAT_ENDPOINTS = new LinkedList<>();

    private Session session;

    @OnOpen
    public void open(Session session) {
        this.session = session;
        registerEndpoint(this);

        String username = getUsername(session);
        AirMessage message = new AirMessage();
        message.setType(MessageType.SYSTEM_MESSAGE);
        message.setContent("online");
        message.setFromAccount(username);
        broadcast(message);

        transferUnacceptedMessage(session);
        transferOnlineFriend(session);

        logger.log(Level.INFO, String.format("用户%s连接到了websocket", username));
    }

    @OnClose
    public void end() {
        String username = getUsername(session);
        AirMessage message = new AirMessage();
        message.setType(MessageType.SYSTEM_MESSAGE);
        message.setContent("offline");
        message.setFromAccount(username);
        broadcast(message);

        unregisterEndpoint(this);
        session = null;
        logger.log(Level.INFO, String.format("用户%s从websocket断开", username));
    }

    @OnMessage
    public void message(AirMessage airMessage) {
        airMessage.setFromAccount(getUsername(this.session));
        recordMessage(airMessage);
        broadcast(airMessage);
        logger.log(Level.INFO, airMessage.toString());
    }

    @OnError
    public void error(Throwable throwable) {
        throwable.printStackTrace();
        end();
        logger.log(Level.SEVERE, throwable.getMessage());
    }

    /**
     * 从wssession获取用户名
     *
     * @param session
     * @return
     */
    private String getUsername(Session session) {
        return OnlineUserUtil.getUsername(
                ((WsSession) session).getHttpSessionId()
        );
    }

    /**
     * 记录连接到websocket的终端
     *
     * @param endpoint
     */
    private void registerEndpoint(ChatEndpoint endpoint) {
        CHAT_ENDPOINTS.add(endpoint);
    }

    /**
     * 删除终端
     *
     * @param endpoint
     */
    private void unregisterEndpoint(ChatEndpoint endpoint) {
        CHAT_ENDPOINTS.remove(endpoint);
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
        for (ChatEndpoint endpoint : CHAT_ENDPOINTS) {
            try {
                if (exclude == endpoint) {
                    continue;
                }
                endpoint.session.getBasicRemote().sendObject(object);
            } catch (IOException | EncodeException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    /**
     * 记录接受的信息
     * 用于推送历史信息
     *
     * @param message
     */
    private void recordMessage(AirMessage message) {
        if (LASTED_MESSAGE.size() > LASTED_MESSAGE_CAPACITY) {
            LASTED_MESSAGE.removeFirst();
        }
        LASTED_MESSAGE.addLast(message);
    }

    /**
     * 推送历史信息
     *
     * @param session
     */
    private void transferUnacceptedMessage(Session session) {
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        LASTED_MESSAGE.stream()
                .filter(airMessage -> MessageType.SHORT_MESSAGE.name().equals(airMessage.getType()))
                .forEach(airMessage -> {
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
    private void transferOnlineFriend(Session session) {
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        for (ChatEndpoint endpoint : CHAT_ENDPOINTS) {
            try {
                Session currentSession = endpoint.session;
                if (currentSession == session) {
                    continue;
                }
                AirMessage airMessage = new AirMessage("online");
                airMessage.setType(MessageType.SYSTEM_MESSAGE);
                airMessage.setFromAccount(getUsername(session));
                basicRemote.sendObject(airMessage);
            } catch (EncodeException | IOException e) {
                logger.severe(e.getMessage());
            }
        }

    }
}

