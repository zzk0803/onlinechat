package zzk.webproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import zzk.webproject.air.AirMessage;
import zzk.webproject.air.MessageType;

public class MemoryChatMessageImplementor extends ChatMessageServiceImplementor {
    private static final List<AirMessage> MESSAGE=new ArrayList<>();

    @Override
    public int save(AirMessage message) {
        MESSAGE.add(message);
        return MESSAGE.indexOf(message);
    }

    @Override
    public boolean isExist(int messageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isExist(String referenceUUID) {
        return MESSAGE.stream()
                .filter(message->MessageType.REFERENCE.name().equalsIgnoreCase(message.getType()))
                .filter(message->referenceUUID.equals(message.getContent()))
                .count()>0;
    }

    @Override
    public AirMessage get(int messageId) {
        return MESSAGE.get(messageId);
    }

    @Override
    public List<AirMessage> list(Predicate<AirMessage> messageFliter) {
        List<AirMessage> messages=MESSAGE.stream()
                .filter(messageFliter)
                .collect(Collectors.toList());
        return messages;
    }

}
