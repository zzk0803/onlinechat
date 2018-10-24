package zzk.webproject.service;

public class Services {
    private static final AccountService ACCOUNT_SERVICE_INSTANCE = new FacilitatedTestingAccountService(new MemoryAccountServiceImplementor());

    public static AccountService getAccountServiceInstance() {
        return ACCOUNT_SERVICE_INSTANCE;
    }
}
