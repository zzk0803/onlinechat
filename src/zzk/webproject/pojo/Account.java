package zzk.webproject.pojo;

import java.time.LocalDateTime;

public class Account {
    private int id;
    private String username;
    private String password;
    private LocalDateTime registerDateTime;
    private LocalDateTime lastSignInDateTime;
    private boolean silent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }

    public void setRegisterDateTime(LocalDateTime registerDateTime) {
        this.registerDateTime = registerDateTime;
    }

    public LocalDateTime getLastSignInDateTime() {
        return lastSignInDateTime;
    }

    public void setLastSignInDateTime(LocalDateTime lastSignInDateTime) {
        this.lastSignInDateTime = lastSignInDateTime;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        return getUsername().equals(account.getUsername());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getUsername().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registerDateTime=" + registerDateTime +
                ", lastSignInDateTime=" + lastSignInDateTime +
                ", silent=" + silent +
                '}';
    }
}
