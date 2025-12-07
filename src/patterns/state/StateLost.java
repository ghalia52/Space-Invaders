package patterns.state;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.Board;
import utils.Constants;
import utils.Logger;

/**
 * État de défaite - Écran affiché quand le joueur perd
 */
public class StateLost implements State, ActionListener {
    
    private JPanel lostPanel;
    private Board board;
    private int finalScore;

    public StateLost(Board board, int score) {
        this.board = board;
        this.finalScore = score;
        Logger.info("StateLost created with score: " + score);
        initializeUI();
    }

    private void initializeUI() {
        lostPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background - Dark red/black theme for game over
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(40, 0, 0),
                    0, getHeight(), new Color(10, 0, 0)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dim stars
                g2d.setColor(new Color(255, 100, 100, 100)); // Reddish dim stars
                java.util.Random random = new java.util.Random(456);
                for (int i = 0; i < 80; i++) {
                    int x = random.nextInt(getWidth());
                    int y = random.nextInt(getHeight());
                    int size = random.nextInt(3) + 1;
                    g2d.fillRect(x, y, size, size);
                }
            }
        };
        
        lostPanel.setLayout(new BoxLayout(lostPanel, BoxLayout.PAGE_AXIS));
        lostPanel.setPreferredSize(Constants.SCREEN_SIZE);

        lostPanel.add(Box.createVerticalGlue());

        // Game Over header
        JLabel header = new JLabel("GAME OVER");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setForeground(new Color(220, 20, 60)); // Crimson red
        header.setFont(new Font("Arial", Font.BOLD, 80));
        lostPanel.add(header);

        lostPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Defeat message
        JLabel defeatMsg = new JLabel("The aliens have invaded Earth...");
        defeatMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        defeatMsg.setForeground(new Color(255, 150, 150));
        defeatMsg.setFont(new Font("Arial", Font.ITALIC, 24));
        lostPanel.add(defeatMsg);

        lostPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Score panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
        scorePanel.setOpaque(false);
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel scoreLabel = new JLabel("FINAL SCORE");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(new Color(255, 200, 200));
        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        scorePanel.add(scoreLabel);
        
        scorePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel scoreValue = new JLabel(String.valueOf(finalScore));
        scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreValue.setForeground(new Color(220, 20, 60));
        scoreValue.setFont(new Font("Monospaced", Font.BOLD, 60));
        scorePanel.add(scoreValue);
        
        lostPanel.add(scorePanel);

        lostPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Return button
        JButton returnButton = createStyledButton("Return to Menu");
        returnButton.setActionCommand("RETURN");
        returnButton.addActionListener(this);
        lostPanel.add(returnButton);
        
        lostPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Try again button
        JButton tryAgainButton = createStyledButton("Try Again");
        tryAgainButton.setActionCommand("TRY_AGAIN");
        tryAgainButton.addActionListener(this);
        lostPanel.add(tryAgainButton);

        lostPanel.add(Box.createVerticalGlue());
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(new Color(139, 0, 0)); // Dark red
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 60));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 20, 60), 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(178, 34, 34)); // Firebrick
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(139, 0, 0));
            }
        });
        
        return button;
    }

    @Override
    public JPanel getMainPanel() {
        return lostPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("RETURN")) {
            Logger.info("Returning to menu from game over screen");
            board.setCurrentState(new StateMenu(board));
        } else if (command.equals("TRY_AGAIN")) {
            Logger.info("Starting new game from game over screen");
            board.setCurrentState(new StateGame(board));
        }
    }
    
    @Override
    public void onEnter() {
        State.super.onEnter();
        Logger.info("💀 GAME OVER 💀 - Final Score: " + finalScore);
    }
    
    @Override
    public void onExit() {
        State.super.onExit();
        Logger.info("Leaving game over screen");
    }
}