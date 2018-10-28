package zzk.webproject.air;

import java.util.Objects;

public class AirAccountEventMessage implements AirMessage {
    private String type;
    private String event;
    private String username;

    public AirAccountEventMessage() {
    }

    public AirAccountEventMessage(String type, String event, String username) {
        this.type = type;
        this.event = event;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirAccountEventMessage)) return false;
        AirAccountEventMessage that = (AirAccountEventMessage) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getEvent(), that.getEvent()) &&
                Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getEvent(), getUsername());
    }

    @Override
    public String toString() {
        return "AirAccountEventMessage{" +
                "type='" + type + '\'' +
                ", event='" + event + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
