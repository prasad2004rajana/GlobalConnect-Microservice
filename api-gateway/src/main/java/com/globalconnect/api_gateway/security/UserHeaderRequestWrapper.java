package com.globalconnect.api_gateway.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class UserHeaderRequestWrapper extends HttpServletRequestWrapper {

    public static final String USER_ID_HEADER = "X-User-Id";

    private final String userId;

    public UserHeaderRequestWrapper(HttpServletRequest request, String userId) {
        super(request);
        this.userId = userId;
    }

    @Override
    public String getHeader(String name) {
        if (USER_ID_HEADER.equalsIgnoreCase(name)) {
            return userId;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (USER_ID_HEADER.equalsIgnoreCase(name)) {
            return userId == null ? Collections.emptyEnumeration()
                    : Collections.enumeration(Collections.singletonList(userId));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new LinkedHashSet<>();
        Enumeration<String> original = super.getHeaderNames();
        while (original.hasMoreElements()) {
            String n = original.nextElement();
            if (!USER_ID_HEADER.equalsIgnoreCase(n)) {
                names.add(n);
            }
        }
        if (userId != null) {
            names.add(USER_ID_HEADER);
        }
        return Collections.enumeration(names);
    }
}