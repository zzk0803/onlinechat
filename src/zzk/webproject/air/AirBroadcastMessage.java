package zzk.webproject.air;

import zzk.webproject.util.SimpleJsonFormatter;

import java.util.Objects;

public class AirBroadcastMessage implements AirMessage {
    private final String type = "message";
    private String sourceAccount;
    private String message;

    public AirBroadcastMessage() {
    }

    public AirBroadcastMessage(String sourceAccount, String message) {
        this.sourceAccount = sourceAccount;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirBroadcastMessage)) return false;
        AirBroadcastMessage that = (AirBroadcastMessage) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getSourceAccount(), that.getSourceAccount()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getSourceAccount(), getMessage());
    }
}
