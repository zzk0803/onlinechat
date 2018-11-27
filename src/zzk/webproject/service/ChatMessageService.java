package zzk.webproject.service;

import zzk.webproject.air.AirMessage;

import java.util.List;
import java.util.function.Predicate;

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

    public void save(AirMessage message) {
         chatMessageServiceImplementor.save(message);
    }

    public void mapLongText(String referenceUUID, String text) {
        chatMessageServiceImplementor.mapLongText(referenceUUID, text);
    }

    public String getLongText(String referenceUUID) {
        return chatMessageServiceImplementor.getLongText(referenceUUID);
    }

    public boolean isExist(String referenceUUID) {
        return chatMessageServiceImplementor.isExist(referenceUUID);
    }

    public AirMessage getMessage(String referenceUUID) {
        return chatMessageServiceImplementor.get(referenceUUID);
    }
    
    public List<AirMessage> list(Predicate<AirMessage> messagePredicate){
        return chatMessageServiceImplementor.list(messagePredicate);
    }
}
