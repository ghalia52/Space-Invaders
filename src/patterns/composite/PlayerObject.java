package patterns.composite;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Représentation du joueur comme objet feuille du Composite Pattern
 * Utilisé pour intégrer le joueur dans la hiérarchie GameScene
 */
public class PlayerObject extends GameObject {
    private Color color;
    private int width = 40;
    private int height = 30;

    public PlayerObject(int x, int y) {
        super(x, y);
        this.color = Color.CYAN;
    }

    @Override
    public void update() {
        // La logique de mise à jour est gérée par StateGame
    }

    @Override
    public void render(Graphics g) {
        if (!active) return;
        
        // Dessiner le vaisseau du joueur
        g.setColor(color);
        
        // Corps principal
        int[] xPoints = {x, x - width/2, x + width/2};
        int[] yPoints = {y - height/2, y + height/2, y + height/2};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // Cockpit
        g.setColor(Color.WHITE);
        g.fillOval(x - 5, y - 5, 10, 10);
    }

    public void moveBy(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}