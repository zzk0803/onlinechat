package zzk.webproject.service;

import javax.servlet.http.HttpSession;
import java.util.*;

public class Roster {
    private static final Map<String, String> USERNAME_SESSIONID_MAP = new HashMap<>();
    private static final Map<String, String> SESSIONID_USERNAME_MAP = new HashMap<>();

    private static void put(String username, HttpSession session) {
        USERNAME_SESSIONID_MAP.put(username, session.getId());
        SESSIONID_USERNAME_MAP.put(session.getId(), username);
    }

    private static void remove(String username) {
        String sessionId = USERNAME_SESSIONID_MAP.get(username);
        USERNAME_SESSIONID_MAP.remove(username);
        SESSIONID_USERNAME_MAP.remove(sessionId);
    }

    private static void remove(HttpSession session) {
        String theUsername = SESSIONID_USERNAME_MAP.get(session.getId());
        SESSIONID_USERNAME_MAP.remove(session.getId());
        SESSIONID_USERNAME_MAP.remove(theUsername);
    }

    public static String getUsername(HttpSession session) {
        return SESSIONID_USERNAME_MAP.get(session.getId());
    }

    public static String getUsername(String sessionId) {
        return SESSIONID_USERNAME_MAP.get(sessionId);
    }

    public static String getSessionId(String username) {
        return USERNAME_SESSIONID_MAP.get(username);
    }

    public static void register(String username, HttpSession session) {
        put(username, session);
    }

    public static void unregister(String username) {
        remove(username);
    }

    public static void unregister(HttpSession session) {
        remove(session);
    }
}
