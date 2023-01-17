package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, String> mapPrev = new HashMap<>();
        Map<Integer, String> mapCurr = new HashMap<>();

        for (User user : previous) {
            mapPrev.put(user.getId(), user.getName());
        }
        for (User user : current) {
            mapCurr.put(user.getId(), user.getName());
        }

        int added = 0;
        int changed = 0;
        int deleted = 0;

        for (Integer integer : mapPrev.keySet()) {
            if (mapCurr.containsKey(integer)) {
                if (!mapCurr.get(integer).equals(mapPrev.get(integer))) {
                    changed++;
                }
            } else {
                deleted++;
            }
        }

        for (Integer integer : mapCurr.keySet()) {
            if (!mapPrev.containsKey(integer)) {
                added++;
            }
        }
        return new Info(added, changed, deleted);
    }
}