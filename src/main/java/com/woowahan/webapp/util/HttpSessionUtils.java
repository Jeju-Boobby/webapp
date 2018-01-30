package com.woowahan.webapp.util;

import com.woowahan.webapp.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLogOn(HttpSession session) {
        Object user = session.getAttribute(USER_SESSION_KEY);
        if (user == null) {
            return false;
        }

        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLogOn(session)) {
            return null;
        }

        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
