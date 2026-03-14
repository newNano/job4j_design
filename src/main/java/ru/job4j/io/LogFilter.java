package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> result = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            input.lines().forEach(str -> {
                String[] array = str.split(" ");
                if ("404".equals(array[array.length - 2])) {
                    result.add(str);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void saveTo(String out) {
        List<String> data = filter();
        /* save DATA to out */
        try (PrintWriter output = new PrintWriter(
                new BufferedOutputStream(
                    new FileOutputStream(out)
                ))) {
            data.forEach(output::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        logFilter.filter().forEach(System.out::println);
        logFilter.saveTo("data/404.txt");
    }
}
