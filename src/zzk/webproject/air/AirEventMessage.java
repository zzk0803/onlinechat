package zzk.webproject.air;

import zzk.webproject.util.SimpleJsonFormatter;

import java.util.Objects;

public class AirEventMessage implements AirMessage {
    private String type;
    private String username;

    public AirEventMessage() {
    }

    public AirEventMessage(String type, String username) {
        this.type = type;
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirEventMessage)) return false;
        AirEventMessage that = (AirEventMessage) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "AirEventMessage{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public String toJson() {
        return SimpleJsonFormatter.toJsonString(this);
    }

}
