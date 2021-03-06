package zzk.webproject.service;

public final class Services {
    private static final AccountService ACCOUNT_SERVICE_INSTANCE = new AccountService(new AccountServiceMemoryImplementor());

    private static final ChatMessageService CHAT_MESSAGE_SERVICE = new ChatMessageService(new ChatMessageMemoryImplementor());

    public static AccountService getAccountServiceInstance() {
        return ACCOUNT_SERVICE_INSTANCE;
    }

    public static ChatMessageService getChatMessageService() {
        return CHAT_MESSAGE_SERVICE;
    }
}
