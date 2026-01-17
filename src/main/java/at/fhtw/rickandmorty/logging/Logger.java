package at.fhtw.rickandmorty.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "application.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static synchronized void log(String level, String message) {

        File file = new File(LOG_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            String logEntry = String.format("%s [%s] %s", timestamp, level, message);
            writer.write(logEntry +  "\r\n");
        } catch (Exception e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}