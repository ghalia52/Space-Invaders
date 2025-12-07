package patterns.state;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.Board;
import utils.Constants;
import utils.Logger;

/**
 * État du menu principal - SIMPLIFIÉ
 * Point d'entrée de l'application
 */
public class StateMenu implements State, ActionListener {
    
    private JPanel menuPanel;
    private Board board;
    
    public StateMenu(Board board) {
        this.board = board;
        Logger.info("StateMenu created");
        initializeUI();
    }
    
    private void initializeUI() {
        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(5, 10, 25),
                    0, getHeight(), new Color(15, 25, 50)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Stars
                g2d.setColor(new Color(255, 255, 255, 150));
                java.util.Random random = new java.util.Random(42);
                for (int i = 0; i < 100; i++) {
                    int x = random.nextInt(getWidth());
                    int y = random.nextInt(getHeight());
                    int size = random.nextInt(3) + 1;
                    g2d.fillRect(x, y, size, size);
                }
            }
        };
        
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
        menuPanel.setPreferredSize(Constants.SCREEN_SIZE);
        
        // Add vertical glue to center content
        menuPanel.add(Box.createVerticalGlue());
        
        // Title
        JLabel titleLabel = new JLabel("SPACE INVADERS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 72));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleLabel);
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Design Patterns Project");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        subtitleLabel.setForeground(new Color(200, 200, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(subtitleLabel);
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        
        // Start Game Button
        JButton startButton = createMenuButton("Start Game");
        startButton.setActionCommand("START_GAME");
        startButton.addActionListener(this);
        menuPanel.add(startButton);
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Instructions
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setOpaque(false);
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.PAGE_AXIS));
        instructionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructionsTitle = new JLabel("HOW TO PLAY:");
        instructionsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        instructionsTitle.setForeground(new Color(255, 255, 150));
        instructionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsPanel.add(instructionsTitle);
        
        instructionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        String[] instructions = {
            "← → or A D: Move your ship",
            "SPACE: Shoot",
            "ESC: Pause game",
            "",
            "Destroy all aliens to win!",
            "Don't let them reach Earth!"
        };
        
        for (String instruction : instructions) {
            JLabel label = new JLabel(instruction);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.WHITE);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            instructionsPanel.add(label);
        }
        
        menuPanel.add(instructionsPanel);
        
        // Add vertical glue to center content
        menuPanel.add(Box.createVerticalGlue());
        
        // Credits at bottom
        JLabel creditsLabel = new JLabel("State • Decorator • Composite • Factory Patterns");
        creditsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        creditsLabel.setForeground(new Color(150, 150, 200));
        creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(creditsLabel);
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 32));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(400, 80));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getBackground();
            
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    @Override
    public JPanel getMainPanel() {
        return menuPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("START_GAME")) {
            Logger.info("Starting new game from menu");
            board.setCurrentState(new StateGame(board));
        }
    }
    
    @Override
    public void onEnter() {
        State.super.onEnter();
        Logger.info("Main menu displayed");
    }
    
    @Override
    public void onExit() {
        State.super.onExit();
        Logger.info("Leaving main menu");
    }
}