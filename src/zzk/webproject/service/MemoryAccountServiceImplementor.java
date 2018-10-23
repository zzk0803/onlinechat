package zzk.webproject.service;

import java.util.HashMap;
import java.util.Map;

class MemoryAccountServiceImplementor extends AccountServiceImplementor {

    static final Map<String, String> MEMORY_REALM = new HashMap<>();

    @Override
    public synchronized boolean register(String username, String password) {
        if (notExistUsername(username)) {
            return false;
        }
        MEMORY_REALM.put(username, password);
        return hasExistUsername(username);
    }

    @Override
    public synchronized boolean login(String username, String password) {
        if (notExistUsername(username)) {
            return false;
        }
        return matchRealm(username, password);
    }

    @Override
    public synchronized boolean deleteAccount(String username) {
        MEMORY_REALM.remove(username);
        return notExistUsername(username);
    }

    boolean matchRealm(String subject, String principle) {
        String password = MEMORY_REALM.get(subject);
        return password.equals(principle);
    }

    boolean notExistUsername(String username) {
        return !hasExistUsername(username);
    }

    boolean hasExistUsername(String username) {
        return MEMORY_REALM.containsKey(username);
    }
}
