package zzk.webproject.service;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import zzk.webproject.air.AirMessage;

public class ChatMessageService {
    private ChatMessageServiceImplementor chatMessageServiceImplementor;

    public ChatMessageService() {
    }

    public ChatMessageService(ChatMessageServiceImplementor chatMessageServiceImplementor) {
        this.chatMessageServiceImplementor = chatMessageServiceImplementor;
    }

    public ChatMessageServiceImplementor getChatMessageServiceImplementor() {
        return chatMessageServiceImplementor;
    }

    public void setChatMessageServiceImplementor(ChatMessageServiceImplementor chatMessageServiceImplementor) {
        this.chatMessageServiceImplementor = chatMessageServiceImplementor;
    }

    public int save(AirMessage message) {
        return chatMessageServiceImplementor.save(message);
    }

    public boolean isExist(int id) {
        return chatMessageServiceImplementor.isExist(id);
    }

    public AirMessage getMessage(int id) {
        return chatMessageServiceImplementor.get(id);
    }
    
    public List<AirMessage> list(Predicate<AirMessage> messageFliter){
        return chatMessageServiceImplementor.list(messageFliter);
    }
}
