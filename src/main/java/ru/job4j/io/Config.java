package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Config {

    private final String path;

    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            reader.lines().forEach(
                str -> {
                    String line = str.trim();
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        int index = str.indexOf("=");
                        if (index <= 0 || index == str.length() - 1) {
                            throw new IllegalArgumentException("Invalid line: " + str);
                        }
                        String key = str.substring(0, index);
                        String value = str.substring(index + 1);
                        values.put(key, value);
                    }
                }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            reader.lines().forEach(output::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void main(String[] args) {
        Config config = new Config("data/app.properties");
        config.load();
        Map<String, String> values = config.values;
        System.out.println(values);
    }

}
