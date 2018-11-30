package zzk.webproject.service;

import javax.servlet.http.HttpSession;
import java.util.*;

public class Roster {
    private static final Map<String, HttpSession> USERNAME_SESSION_MAP = new HashMap<>();
    private static final Map<HttpSession, String> SESSION_USERNAME_MAP = new HashMap<>();

    private static void put(String username, HttpSession session) {
        USERNAME_SESSION_MAP.put(username, session);
        SESSION_USERNAME_MAP.put(session, username);
    }

    private static void remove(String username) {
        HttpSession theSession = USERNAME_SESSION_MAP.get(username);
        USERNAME_SESSION_MAP.remove(username);
        SESSION_USERNAME_MAP.remove(theSession);
    }

    private static void remove(HttpSession session) {
        String theUsername = SESSION_USERNAME_MAP.get(session);
        SESSION_USERNAME_MAP.remove(session);
        SESSION_USERNAME_MAP.remove(theUsername);
    }

    public static String getUsername(HttpSession session) {
        return SESSION_USERNAME_MAP.get(session);
    }

    public static String getUsername(String sessionId) {
        String username = null;
        Set<HttpSession> httpSessions = SESSION_USERNAME_MAP.keySet();
        for (HttpSession session : httpSessions) {
            String currentSessionId = session.getId();
            if (sessionId.equals(currentSessionId)) {
                username = SESSION_USERNAME_MAP.get(session);
                break;
            }
        }
        return username;
    }

    public static HttpSession getSession(String username) {
        return USERNAME_SESSION_MAP.get(username);
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
