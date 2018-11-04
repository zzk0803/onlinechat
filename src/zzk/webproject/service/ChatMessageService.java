package zzk.webproject.service;

import java.util.UUID;

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

    public String save(String author, String message) {
        String uuid = UUID.randomUUID().toString();
        chatMessageServiceImplementor.save(uuid, author, message);
        return uuid;
    }

    public boolean isExist(String uuid) {
        return chatMessageServiceImplementor.isExist(uuid);
    }

    public String getMessage(String uuid) {
        return chatMessageServiceImplementor.get(uuid);
    }
}
