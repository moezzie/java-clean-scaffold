package se.moeser.javacleanscaffold.domain.valueobject;

public class Role {
    public static String PREFIX = "ROLE_";
    public static String NONE = Role.PREFIX + "NONE";
    public static String USER = Role.PREFIX + "USER";
    public static String ADMIN = Role.PREFIX + "ROLE_ADMIN";

    private String role = Role.NONE;

    public Role(String role) {
        this.role = role;
    }

    public String toString() {
        return this.role;
    }
}
