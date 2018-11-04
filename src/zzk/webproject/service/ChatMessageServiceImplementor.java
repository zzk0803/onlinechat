package zzk.webproject.service;

public abstract class ChatMessageServiceImplementor {
    public abstract void save(String uuid, String author, String message);

    public abstract boolean isExist(String uuid);

    public abstract String get(String uuid);
}
