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

    /**
     * 保存接收到的信息
     * @param message
     */
    public void save(AirMessage message) {
         chatMessageServiceImplementor.save(message);
    }

    /**
     * 保存接收到的长文本
     * @param referenceUUID
     * @param text
     */
    public void mapLongText(String referenceUUID, String text) {
        chatMessageServiceImplementor.mapLongText(referenceUUID, text);
    }

    /**
     * 获取保存的长文本
     * @param referenceUUID
     * @return
     */
    public String getLongText(String referenceUUID) {
        return chatMessageServiceImplementor.getLongText(referenceUUID);
    }

    /**
     * 检查长文本是否存在
     * @param referenceUUID
     * @return
     */
    public boolean isExist(String referenceUUID) {
        return chatMessageServiceImplementor.isExist(referenceUUID);
    }

    /**
     * 获取长文本
     * @param referenceUUID
     * @return
     */
    public AirMessage getMessage(String referenceUUID) {
        return chatMessageServiceImplementor.get(referenceUUID);
    }

    /**
     * 筛选信息返回列表
     * @param messagePredicate
     * @return
     */
    public List<AirMessage> list(Predicate<AirMessage> messagePredicate){
        return chatMessageServiceImplementor.list(messagePredicate);
    }
}
