package ru.job4j.map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {

    private String name;
    private int children;
    private Calendar birthday;

    public User(String name,
                int children,
                Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name)
                          && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    public static void main(String[] args) {
        int n = 16;
        Map<User, Object> map = new HashMap<>(n);
        Calendar birthday = Calendar.getInstance();
        User user1 = new User("Max", 7, birthday);
        int hashCode1 = user1.hashCode();
        int hash1 = hashCode1 ^ (hashCode1 >>> 16);
        int bucket1 = (n - 1) & hash1;
        Object o1 = new Object();

        User user2 = new User("Max", 7, birthday);
        int hashCode2 = user2.hashCode();
        int hash2 = hashCode2 ^ (hashCode2 >>> 16);
        int bucket2 = (n - 1) & hash2;
        Object o2 = new Object();

        map.put(user1, o1);
        map.put(user2, o2);

        System.out.printf("user1 - хэшкод: %d, хэш: %d, бакет: %d%n",
                        hashCode1, hash1, bucket1);
        System.out.printf("user2 - хэшкод: %d, хэш: %d, бакет: %d%n",
                hashCode2, hash2, bucket2);
        System.out.println("size " + map.size());
    }
}