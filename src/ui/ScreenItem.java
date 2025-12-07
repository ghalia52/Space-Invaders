package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Composant UI de base pour tous les éléments d'écran
 * Facilite la création d'interfaces cohérentes
 */
public abstract class ScreenItem extends JPanel {
    
    protected Color backgroundColor;
    protected Color foregroundColor;
    protected Font font;
    
    public ScreenItem() {
        setOpaque(false);
        initializeDefaults();
    }
    
    /**
     * Initialise les valeurs par défaut
     */
    protected void initializeDefaults() {
        backgroundColor = new Color(10, 15, 35);
        foregroundColor = Color.WHITE;
        font = new Font("Arial", Font.PLAIN, 16);
    }
    
    /**
     * Dessine le fond avec gradient
     */
    protected void drawGradientBackground(Graphics2D g2d, Color color1, Color color2) {
        GradientPaint gradient = new GradientPaint(
            0, 0, color1,
            0, getHeight(), color2
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Dessine un champ d'étoiles en arrière-plan
     */
    protected void drawStarField(Graphics2D g2d, int starCount, int seed) {
        java.util.Random random = new java.util.Random(seed);
        g2d.setColor(new Color(255, 255, 255, 150));
        
        for (int i = 0; i < starCount; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            int size = random.nextInt(3) + 1;
            g2d.fillRect(x, y, size, size);
        }
    }
    
    /**
     * Dessine du texte centré
     */
    protected void drawCenteredText(Graphics2D g2d, String text, int y, Font font, Color color) {
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2d.drawString(text, (getWidth() - textWidth) / 2, y);
    }
    
    /**
     * Crée un bouton stylisé
     */
    protected JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(fgColor, 2));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalBg = button.getBackground();
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(fgColor);
                button.setForeground(bgColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(originalBg);
                button.setForeground(fgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Dessine un panneau avec bordure lumineuse
     */
    protected void drawGlowPanel(Graphics2D g2d, int x, int y, int width, int height, 
                                 Color glowColor, Color fillColor) {
        // Lueur externe
        g2d.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), 
                              glowColor.getBlue(), 50));
        g2d.fillRoundRect(x - 5, y - 5, width + 10, height + 10, 20, 20);
        
        // Panneau principal
        g2d.setColor(fillColor);
        g2d.fillRoundRect(x, y, width, height, 15, 15);
        
        // Bordure
        g2d.setColor(glowColor);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, width, height, 15, 15);
    }
}