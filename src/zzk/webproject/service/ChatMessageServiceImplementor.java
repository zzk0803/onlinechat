package zzk.webproject.service;

import java.util.List;
import java.util.function.Predicate;
import zzk.webproject.air.AirMessage;

public abstract class ChatMessageServiceImplementor {

    public abstract int save(AirMessage message);

    public abstract boolean isExist(int messageId);

    public abstract AirMessage get(int messageId);

    public abstract List<AirMessage> list(Predicate<AirMessage> messageFliter);
}
