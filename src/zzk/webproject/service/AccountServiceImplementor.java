package zzk.webproject.service;

abstract class AccountServiceImplementor {
    public abstract boolean register(String username, String password);

    public abstract boolean login(String username, String password);

    public abstract boolean deleteAccount(String username);
}
