package zzk.webproject.air;

import java.util.Objects;

public class AirP2PMessage implements AirMessage {
    private final String type = "message";
    private String sourceAccount;
    private String targetAccount;
    private String message;

    public AirP2PMessage() {
    }

    public AirP2PMessage(String sourceAccount, String targetAccount, String message) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
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

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
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
        if (!(o instanceof AirP2PMessage)) return false;
        AirP2PMessage that = (AirP2PMessage) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getSourceAccount(), that.getSourceAccount()) &&
                Objects.equals(getTargetAccount(), that.getTargetAccount()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getSourceAccount(), getTargetAccount(), getMessage());
    }
}
