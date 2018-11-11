package zzk.webproject.air;

import org.apache.tomcat.websocket.WsSession;
import zzk.webproject.air.entity.AirAccountEventMessage;
import zzk.webproject.air.entity.AirBroadcastMessage;
import zzk.webproject.air.entity.AirP2PMessage;
import zzk.webproject.air.entity.AirReferenceMessage;
import zzk.webproject.util.OnlineUserUtil;
import zzk.webproject.util.SimpleJsonFormatter;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/chat")
public class ChatEndpoint {
    public static final Logger logger = Logger.getLogger(ChatEndpoint.class.getName());

    private static final int LASTED_MESSAGE_CAPACITY = 6;
    private static final LinkedList<String> LASTED_MESSAGE = new LinkedList<>();
    private static final LinkedList<ChatEndpoint> CHAT_ENDPOINTS = new LinkedList<>();

    private Session session;

    @OnOpen
    public void open(Session session) {
        registerEndpoint(this);
        this.session = session;
        String username = getUsername(session);
        broadcast(new AirAccountEventMessage("event", "online", username));
        transferUnacceptedMessage(session);
        transferOnlineFriend(session);
        logger.log(Level.INFO, String.format("用户%s连接到了websocket", username));
    }

    @OnClose
    public void end() {
        String username = getUsername(session);
        broadcast(new AirAccountEventMessage("event", "offline", username));
        unregisterEndpoint(this);
        session = null;
        logger.log(Level.INFO, String.format("用户%s从websocket断开", username));
    }

    @OnMessage
    public void message(String message) {
        recordMessage(message);
        broadcast(message);
        logger.log(Level.INFO, String.format("用户%s发送了一条短消息:%s", getUsername(this.session), message));
    }

    @OnError
    public void error(Throwable throwable) {
        throwable.printStackTrace();
        end();
        logger.log(Level.SEVERE, throwable.getMessage());
    }

    public static void broadcastReferenceMessage(String author, String type, String uuid) {
        AirReferenceMessage referenceMessage = new AirReferenceMessage(type, uuid);
        CHAT_ENDPOINTS.stream()
                .filter(endpoint -> OnlineUserUtil.getUsername(((WsSession) endpoint.session).getHttpSessionId()).equals(author))
                .findFirst()
                .ifPresent(endpoint -> endpoint.broadcast(referenceMessage, endpoint));
    }

    private String getUsername(Session session) {
        return OnlineUserUtil.getUsername(
                ((WsSession) session).getHttpSessionId()
        );
    }

    private void registerEndpoint(ChatEndpoint endpoint) {
        CHAT_ENDPOINTS.add(endpoint);
    }

    private void unregisterEndpoint(ChatEndpoint endpoint) {
        CHAT_ENDPOINTS.remove(endpoint);
    }

    private void broadcast(String message) {
        AirBroadcastMessage airBroadcastMessage = new AirBroadcastMessage(this.getUsername(this.session), message);
        broadcast(airBroadcastMessage);
    }

    private void broadcast(Object object) {
        broadcast(object, this);
    }

    private void broadcast(Object object, ChatEndpoint exclude) {
        for (ChatEndpoint endpoint : CHAT_ENDPOINTS) {
            try {
                if (exclude == endpoint) {
                    continue;
                }
                endpoint.session.getBasicRemote().sendText(SimpleJsonFormatter.toJsonString(object));
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private void recordMessage(String message) {
        if (LASTED_MESSAGE.size() > LASTED_MESSAGE_CAPACITY) {
            LASTED_MESSAGE.removeLast();
        }
        LASTED_MESSAGE.addFirst(message);
    }

    private void transferUnacceptedMessage(Session session) {
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        for (String message : LASTED_MESSAGE) {
            try {
                basicRemote.sendText(new AirP2PMessage("onlineUser", "you", message).toJson());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void transferOnlineFriend(Session session) {
        RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        for (ChatEndpoint endpoint : CHAT_ENDPOINTS) {
            try {
                Session currentSession = endpoint.session;
                if (currentSession == session) {
                    continue;
                }
                basicRemote.sendText(new AirAccountEventMessage("event", "online", getUsername(currentSession)).toJson());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}