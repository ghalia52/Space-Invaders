package patterns.decorator;

import utils.Logger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;


public class ShieldDecorator extends ShipDecorator {
    private int shieldStrength = 3; // Nombre de hits absorbables
    private Color shieldColor = new Color(0, 150, 255, 100);

    public ShieldDecorator(Ship ship) {
        super(ship, 10000); // 10 secondes
        Logger.log("DECORATOR", "Shield activated - Strength: " + shieldStrength);
    }

    @Override
    public void render(Graphics g) {
        // Dessiner le vaisseau d'abord
        decoratedShip.render(g);
        
        // Dessiner le bouclier par-dessus
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(shieldColor);
        
        int x = getX();
        int y = getY();
        int shieldSize = 50;
        g2d.fillOval(x - shieldSize/2, y - shieldSize/2, shieldSize, shieldSize);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    @Override
    public int getSpeed() {
        return decoratedShip.getSpeed();
    }

    @Override
    public int getFireRate() {
        return decoratedShip.getFireRate();
    }

    @Override
    public int getFirePower() {
        return decoratedShip.getFirePower();
    }

    @Override
    public boolean hasShield() {
        return shieldStrength > 0 && !isExpired();
    }

    @Override
    public String getStatus() {
        return decoratedShip.getStatus() + " + Shield(" + shieldStrength + ")";
    }

    /**
     * Réduit la force du bouclier lors d'un impact
     */
    public boolean absorbHit() {
        if (shieldStrength > 0) {
            shieldStrength--;
            Logger.log("DECORATOR", "Shield absorbed hit - Remaining: " + shieldStrength);
            
            if (shieldStrength == 0) {
                Logger.log("DECORATOR", "Shield depleted");
            }
            return true;
        }
        return false;
    }
}