package zzk.webproject.air.entity;

import zzk.webproject.util.SimpleJsonFormatter;

public interface AirMessage {
    default String toJson() {
        return SimpleJsonFormatter.toJsonString(this);
    }
}
