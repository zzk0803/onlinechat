package zzk.webproject.service;

import java.util.List;
import java.util.function.Predicate;
import zzk.webproject.air.AirMessage;

public abstract class ChatMessageServiceImplementor {

    public abstract void save(AirMessage message);

    public abstract void mapLongText(String referenceUUID, String longText);

    public abstract boolean isExist(String referenceUUID);

    public abstract AirMessage get(String referenceUUID);

    public abstract List<AirMessage> list(Predicate<AirMessage> airMessagePredicate);

    public abstract String getLongText(String referenceUUID);
}
