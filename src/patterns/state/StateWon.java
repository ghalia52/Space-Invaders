package patterns.state;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import game.Board;
import utils.Constants;
import utils.Logger;

/**
 * État de victoire - Écran affiché quand le joueur gagne
 */
public class StateWon implements State, ActionListener {

    private JPanel wonPanel;
    private Board board;
    private int finalScore;

    public StateWon(Board board, int score) {
        this.board = board;
        this.finalScore = score;
        Logger.info("StateWon created with score: " + score);
        initializeUI();
    }

    private void initializeUI() {
        wonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient background - Victorious gold/green theme
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(10, 30, 10),
                    0, getHeight(), new Color(30, 60, 30)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Victory stars
                g2d.setColor(new Color(255, 215, 0, 200)); // Gold
                java.util.Random random = new java.util.Random(123);
                for (int i = 0; i < 150; i++) {
                    int x = random.nextInt(getWidth());
                    int y = random.nextInt(getHeight());
                    int size = random.nextInt(4) + 1;
                    g2d.fillOval(x, y, size, size);
                }
            }
        };
        
        wonPanel.setLayout(new BoxLayout(wonPanel, BoxLayout.PAGE_AXIS));
        wonPanel.setPreferredSize(Constants.SCREEN_SIZE);

        wonPanel.add(Box.createVerticalGlue());

        // Victory Header with glow effect
        JLabel header = new JLabel("★ VICTORY! ★");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setForeground(new Color(255, 215, 0)); // Gold
        header.setFont(new Font("Arial", Font.BOLD, 80));
        wonPanel.add(header);

        wonPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Congratulations message
        JLabel congrats = new JLabel("You defeated all the aliens!");
        congrats.setAlignmentX(Component.CENTER_ALIGNMENT);
        congrats.setForeground(Color.WHITE);
        congrats.setFont(new Font("Arial", Font.BOLD, 28));
        wonPanel.add(congrats);

        wonPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Score panel with border
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
        scorePanel.setOpaque(false);
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel scoreLabel = new JLabel("FINAL SCORE");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(new Color(255, 255, 150));
        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        scorePanel.add(scoreLabel);
        
        scorePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel scoreValue = new JLabel(String.valueOf(finalScore));
        scoreValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreValue.setForeground(new Color(255, 215, 0));
        scoreValue.setFont(new Font("Monospaced", Font.BOLD, 60));
        scorePanel.add(scoreValue);
        
        wonPanel.add(scorePanel);

        wonPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Return button
        JButton returnButton = createStyledButton("Return to Menu");
        returnButton.setActionCommand("RETURN");
        returnButton.addActionListener(this);
        wonPanel.add(returnButton);
        
        wonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Play again button
        JButton playAgainButton = createStyledButton("Play Again");
        playAgainButton.setActionCommand("PLAY_AGAIN");
        playAgainButton.addActionListener(this);
        wonPanel.add(playAgainButton);

        wonPanel.add(Box.createVerticalGlue());
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(new Color(34, 139, 34)); // Forest green
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 60));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(50, 205, 50)); // Lime green
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(34, 139, 34));
            }
        });
        
        return button;
    }

    @Override
    public JPanel getMainPanel() {
        return wonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("RETURN")) {
            Logger.info("Returning to menu from victory screen");
            board.setCurrentState(new StateMenu(board));
        } else if (command.equals("PLAY_AGAIN")) {
            Logger.info("Starting new game from victory screen");
            board.setCurrentState(new StateGame(board));
        }
    }
    
    @Override
    public void onEnter() {
        State.super.onEnter();
        Logger.info("★★★ VICTORY! ★★★ - Final Score: " + finalScore);
    }
    
    @Override
    public void onExit() {
        State.super.onExit();
        Logger.info("Leaving victory screen");
    }
}