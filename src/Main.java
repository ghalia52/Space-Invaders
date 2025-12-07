import javax.swing.*;
import game.Board;
import utils.Logger;
import utils.Constants;

/**
 * Point d'entrée principal de l'application
 * FIXED: Properly connects Board to Frame for UI updates
 */
public class Main {
    public static void main(String[] args) {
        // CRITIQUE: Initialiser le logger avant toute chose
        Logger.initialize();
        Logger.info("Application starting...");
        
        // Créer et configurer la fenêtre de jeu
        SwingUtilities.invokeLater(() -> {
            try {
                // Create the board first
                Board board = new Board();
                
                // Create the frame
                JFrame frame = new JFrame("Space Invaders - Design Patterns");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                
                // CRITICAL FIX: Connect board to frame BEFORE adding panel
                board.setFrame(frame);
                
                // Add the initial panel
                frame.add(board.getMainPanel());
                
                // Set size from Constants and show
                frame.setSize(Constants.SCREEN_SIZE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                Logger.info("Game window created successfully");
            } catch (Exception e) {
                Logger.error("Failed to create game window: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // Hook de fermeture pour logger proprement
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.info("Application shutting down...");
            Logger.close();
        }));
    }
}