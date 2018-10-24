package zzk.webproject.service;

public class FacilitatedTestingAccountService extends AccountService {

    public FacilitatedTestingAccountService() {
    }

    public FacilitatedTestingAccountService(AccountServiceImplementor accountService) {
        super(accountService);
    }

    @Override
    public boolean register(String username, String password) {
        return super.register(username, password);
    }

    @Override
    public boolean login(String username, String password) {
        this.register(username, password);
        return super.login(username, password);
    }

    @Override
    public boolean deleteAccount(String username) {
        return super.deleteAccount(username);
    }
}
