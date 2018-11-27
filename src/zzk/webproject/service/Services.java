package zzk.webproject.service;

import sun.security.pkcs11.Secmod;

public class Services {
    private static final AccountService ACCOUNT_SERVICE_INSTANCE = new AccountService(new MemoryAccountServiceImplementor());

    private static final ChatMessageService CHAT_MESSAGE_SERVICE = new ChatMessageService(new MemoryChatMessageImplementor());

    public static AccountService getAccountServiceInstance() {
        return ACCOUNT_SERVICE_INSTANCE;
    }

    public static ChatMessageService getChatMessageService() {
        return CHAT_MESSAGE_SERVICE;
    }
}
