package zzk.webproject.air;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/ws/chat")
public class ChatEndpoint {
    private static final List<ChatEndpoint> CHAT_ENDPOINTS = new LinkedList<>();
    private Session session;

    @OnOpen
    public void open(Session session) {
        this.session = session;
        CHAT_ENDPOINTS.add(this);
        broadcast(session.getId()+"上线了");
    }

    @OnClose
    public void end() {
        session=null;
        CHAT_ENDPOINTS.remove(this);
    }

    @OnMessage
    public void message(String message) {
        broadcast(message);
        broadcast(session.getId() + "下线了");
    }

    @OnError
    public void error(Throwable throwable) {
        
    }
    
    private void broadcast(String message){
        for (int index = 0; index < CHAT_ENDPOINTS.size(); index++) {
            try {
                ChatEndpoint current = CHAT_ENDPOINTS.get(index);
                if (this == current) {
                    continue;
                }
                current.session.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
