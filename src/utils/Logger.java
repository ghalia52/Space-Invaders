package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {
    private static final String LOG_FILE = "game.log";
    private static PrintWriter writer;
    private static SimpleDateFormat dateFormat = 
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static boolean initialized = false;

    /**
     * Initialise le système de logging
     */
    public static void initialize() {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true), true);
            initialized = true;
            log("INFO", "=== Game Session Started ===");
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
    public static void log(String type, String message) {
        if (!initialized) {
            initialize();
        }

        String timestamp = dateFormat.format(new Date());
        String logEntry = String.format("[%s] [%s] %s", timestamp, type, message);
        
        // Écrire dans le fichier
        if (writer != null) {
            writer.println(logEntry);
            writer.flush();
        }
        
        // Aussi afficher dans la console (optionnel)
        System.out.println(logEntry);
    }

    /**
     * Ferme le logger proprement
     */
    public static void close() {
        if (writer != null) {
            log("INFO", "=== Game Session Ended ===");
            writer.close();
            initialized = false;
        }
    }

    // Méthodes de commodité
    public static void info(String message) {
        log("INFO", message);
    }

    public static void state(String message) {
        log("STATE", message);
    }

    public static void decorator(String message) {
        log("DECORATOR", message);
    }

    public static void composite(String message) {
        log("COMPOSITE", message);
    }

    public static void warning(String message) {
        log("WARNING", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }
}