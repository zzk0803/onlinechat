package zzk.webproject.air;

import org.apache.tomcat.websocket.WsSession;
import zzk.webproject.air.entity.AirAccountEventMessage;
import zzk.webproject.air.entity.AirBroadcastMessage;
import zzk.webproject.air.entity.AirP2PMessage;
import zzk.webproject.util.OnlineUserUtil;
import zzk.webproject.util.SimpleJsonFormatter;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/chat")
public class ChatEndpoint {
    public static final int LASTED_MESSAGE_CAPACITY = 3;
    private static final Deque<String> LASTED_MESSAGE = new ArrayDeque<>();
    private static final List<ChatEndpoint> CHAT_ENDPOINTS = new LinkedList<>();
    private Session session;

    private String getUsername(Session session) {
        return OnlineUserUtil.getUsername(((WsSession) session).getHttpSessionId());
    }

    @OnOpen
    public void open(Session session) {
        registerEndpoint(this);
        this.session = session;
        broadcast(new AirAccountEventMessage("event", "online", getUsername(session)));
        transferUnacceptedMessage(session);
    }

    @OnClose
    public void end() {
        broadcast(new AirAccountEventMessage("event", "offline", getUsername(session)));
        unregisterEndpoint(this);
        session = null;
    }

    @OnMessage
    public void message(String message) {
        recordMessage(message);
        broadcast(message);
    }

    @OnError
    public void error(Throwable throwable) {
        throwable.printStackTrace();
        end();
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
        for (ChatEndpoint endpoint : CHAT_ENDPOINTS) {
            try {
                if (this == endpoint) {
                    continue;
                }
                endpoint.session.getBasicRemote().sendText(SimpleJsonFormatter.toJsonString(object));
            } catch (IOException e) {
                Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void recordMessage(String message) {
        LASTED_MESSAGE.push(message);
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
}