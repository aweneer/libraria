package cz.cvut.fel.ear.libraria.util;

import cz.cvut.fel.ear.libraria.model.Role;

public final class Constants {

    public static final String UTF_8_ENCODING = "UTF-8";

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String GUEST = "ROLE_GUEST";

    private Constants() {
        throw new AssertionError();
    }
}
