package ru.job4j.io;

import java.io.FileInputStream;
import java.io.IOException;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileInputStream input = new FileInputStream("data/even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = input.read()) != -1) {
                text.append((char) read);
            }
            String[] lines = text.toString().split(System.lineSeparator());
            for (String line : lines) {
                int n = Integer.parseInt(line);
                String msg = n % 2 == 0 ? " - четное " : " - нечетное ";
                System.out.println(n + msg + "число");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
