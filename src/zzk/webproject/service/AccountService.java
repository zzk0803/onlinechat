package zzk.webproject.service;

public class AccountService {
    private AccountServiceImplementor accountService;

    public AccountService(AccountServiceImplementor accountService) {
        this.accountService = accountService;
    }

    public boolean register(String username, String password) {
        return accountService.register(username, password);
    }

    public boolean login(String username, String password) {
        return accountService.login(username, password);
    }

    public boolean deleteAccount(String username) {
        return accountService.deleteAccount(username);
    }
}
