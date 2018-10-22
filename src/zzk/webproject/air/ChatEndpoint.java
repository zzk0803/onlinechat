package zzk.webproject.air;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;

@ServerEndpoint(value = "/ws/chat")
public class ChatEndpoint {
    private static final List<ChatEndpoint> CHAT_ENDPOINTS = new LinkedList<>();
    private Session session;

    @OnOpen
    public void open(Session session) {
        this.session = session;
        CHAT_ENDPOINTS.add(this);
    }

    @OnClose
    public void end() {

    }

    @OnMessage
    public void message(String message) {

    }

    @OnError
    public void error(Throwable throwable) {

    }
}
