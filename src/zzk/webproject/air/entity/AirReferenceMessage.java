package zzk.webproject.air.entity;

import java.util.Objects;

public class AirReferenceMessage implements AirMessage {
    private String type;
    private String uuid;

    public AirReferenceMessage() {
    }

    public AirReferenceMessage(String type, String uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirReferenceMessage)) return false;
        AirReferenceMessage that = (AirReferenceMessage) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getUuid());
    }

    @Override
    public String toString() {
        return "AirReferenceMessage{" +
                "type='" + type + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
