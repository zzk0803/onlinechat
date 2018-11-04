package zzk.webproject.service;

import java.util.HashMap;
import java.util.Map;

public class MemoryChatMessageImplementor extends ChatMessageServiceImplementor {
    public static final Map<String, String> UUID_MESSAGE_MAP = new HashMap<>();

    @Override
    public void save(String uuid, String author, String message) {
        mapMessageWithUUID(uuid, message);
    }

    private void mapMessageWithUUID(String uuid, String message) {
        UUID_MESSAGE_MAP.put(uuid, message);
    }

    @Override
    public boolean isExist(String uuid) {
        return UUID_MESSAGE_MAP.containsKey(uuid);
    }

    @Override
    public String get(String uuid) {
        return UUID_MESSAGE_MAP.get(uuid);
    }
}
