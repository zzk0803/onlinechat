package zzk.webproject.service;

import sun.security.pkcs11.Secmod;

public class Services {
    private static final AccountService ACCOUNT_SERVICE_INSTANCE = new AccountService(new DatabaseAccountServiceImplementor());

    private static final ChatMessageService CHAT_MESSAGE_SERVICE = new ChatMessageService();

    public static AccountService getAccountServiceInstance() {
        return ACCOUNT_SERVICE_INSTANCE;
    }

    public static ChatMessageService getChatMessageService() {
        return CHAT_MESSAGE_SERVICE;
    }
}
