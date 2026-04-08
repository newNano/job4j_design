package ru.job4j.io;

import java.io.*;

public class Analysis {

    public void unavailable(String source, String target) {
        boolean unavailable = false;
        String start = null;
        try (BufferedReader in = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(new FileWriter(target))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(" ");
                String status = parts[0];
                String time = parts[1];
                if (!unavailable && isServerDown(status)) {
                    unavailable = true;
                    start = time;
                } else if (unavailable && isServerUp(status)) {
                    unavailable = false;
                    out.println(start + ";" + time + ";");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения/записи файла", e);
        }
    }

    private boolean isServerDown(String status) {
        return "400".equals(status) || "500".equals(status);
    }

    private boolean isServerUp(String status) {
        return "200".equals(status) || "300".equals(status);
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}