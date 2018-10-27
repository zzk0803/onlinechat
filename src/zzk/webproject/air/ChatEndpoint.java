package zzk.webproject.air;

import org.apache.tomcat.websocket.WsSession;
import zzk.webproject.util.OnlineUserUtil;
import zzk.webproject.util.SimpleJsonFormatter;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/chat")
public class ChatEndpoint {
    private static final List<ChatEndpoint> CHAT_ENDPOINTS = new LinkedList<>();
    private Session session;

    private String getUsername(Session session) {
        return OnlineUserUtil.getUsername(((WsSession) session).getHttpSessionId());
    }

    @OnOpen
    public void open(Session session) {
        registerEndpoint(this);
        this.session = session;
        broadcast(new AirEventMessage("online", getUsername(session)));
    }

    @OnClose
    public void end() {
        broadcast(new AirEventMessage("offline", getUsername(session)));
        unregisterEndpoint(this);
        session = null;
    }

    @OnMessage
    public void message(String message) {
        broadcast(getUsername(this.session) + ":" + message);
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
        for (ChatEndpoint endpoint : CHAT_ENDPOINTS) {
            try {
                if (this == endpoint) {
                    continue;
                }
                endpoint.session.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
}