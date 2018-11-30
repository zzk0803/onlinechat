package zzk.webproject.air;

import java.io.Serializable;

public class AirMessage implements Serializable {
    private String content;
    private String type;
    private Boolean broadcastMessage;
    private String fromAccount;
    private String toAccount;

    public AirMessage() {
        this.type = MessageType.SHORT_MESSAGE.name();
        this.broadcastMessage = Boolean.TRUE;
    }

    public AirMessage(String content) {
        this();
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type.name();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getBroadcastMessage() {
        return broadcastMessage;
    }

    public void setBroadcastMessage(Boolean broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AirMessage)) {
            return false;
        }

        AirMessage that = (AirMessage) o;

        if (getContent() != null ? !getContent().equals(that.getContent()) : that.getContent() != null) {
            return false;
        }
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) {
            return false;
        }
        if (getBroadcastMessage() != null ? !getBroadcastMessage().equals(that.getBroadcastMessage()) : that.getBroadcastMessage() != null) {
            return false;
        }
        if (getFromAccount() != null ? !getFromAccount().equals(that.getFromAccount()) : that.getFromAccount() != null) {
            return false;
        }
        return getToAccount() != null ? getToAccount().equals(that.getToAccount()) : that.getToAccount() == null;
    }

    @Override
    public int hashCode() {
        int result = getContent() != null ? getContent().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getBroadcastMessage() != null ? getBroadcastMessage().hashCode() : 0);
        result = 31 * result + (getFromAccount() != null ? getFromAccount().hashCode() : 0);
        result = 31 * result + (getToAccount() != null ? getToAccount().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AirMessage{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", broadcastMessage=" + broadcastMessage +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                '}';
    }
}
