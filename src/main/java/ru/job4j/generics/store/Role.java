package ru.job4j.generics.store;

public class Role extends Base {

    private final String userRole;

    public Role(String id, String userRole) {
        super(id);
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}