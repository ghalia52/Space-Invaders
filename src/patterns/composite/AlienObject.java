package patterns.composite;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Alien individuel comme objet feuille
 * Wrapper simple pour intégrer Alien dans le Composite Pattern
 */
public class AlienObject extends GameObject {
    private Color color;
    private int width = 30;
    private int height = 20;

    public AlienObject(int x, int y) {
        super(x, y);
        this.color = Color.GREEN;
    }

    public AlienObject(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public void update() {
        // Logique de mise à jour individuelle si nécessaire
    }

    @Override
    public void render(Graphics g) {
        if (!active) return;
        
        g.setColor(color);
        g.fillRect(x - width/2, y - height/2, width, height);
        
        // Yeux
        g.setColor(Color.WHITE);
        g.fillRect(x - 10, y - 5, 5, 5);
        g.fillRect(x + 5, y - 5, 5, 5);
    }

    public void moveBy(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void hit() {
        setActive(false);
    }
}