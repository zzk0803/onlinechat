package zzk.webproject.service;

import zzk.webproject.air.AirMessage;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemoryChatMessageImplementor extends ChatMessageServiceImplementor {
    public static final int MESSAGE_MAXIMUM = 32;
    private static final LinkedList<AirMessage> MESSAGES = new LinkedList<>();
    public static final Map<String, String> LONGTEXT = new HashMap<>();

    @Override
    public void save(AirMessage message) {
        MESSAGES.offerFirst(message);
        if (MESSAGES.size() > MESSAGE_MAXIMUM) {
            MESSAGES.removeFirst();
        }
    }

    @Override
    public void mapLongText(String referenceUUID, String longText) {
        LONGTEXT.put(referenceUUID, longText);
    }

    @Override
    public String getLongText(String referenceUUID) {
        return LONGTEXT.get(referenceUUID);
    }

    @Override
    public boolean isExist(String referenceUUID) {
        for (AirMessage message : MESSAGES) {
            if (referenceUUID.equals(message.getContent())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AirMessage get(String referenceUUID) {
        for (AirMessage message : MESSAGES) {
            if (referenceUUID.equals(message.getContent())) {
                return message;
            }
        }
        return null;
    }

    @Override
    public List<AirMessage> list(Predicate<AirMessage> airMessagePredicate) {
        return MESSAGES.stream().filter(airMessagePredicate).collect(Collectors.toList());
    }
}
