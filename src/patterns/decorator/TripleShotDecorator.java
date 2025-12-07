package patterns.decorator;

import utils.Logger;
import java.awt.Graphics;
import java.awt.Color;


public class TripleShotDecorator extends ShipDecorator {
    
    public TripleShotDecorator(Ship ship) {
        super(ship, 8000); // 8 secondes
        Logger.log("DECORATOR", "TripleShot applied");
    }

    @Override
    public void render(Graphics g) {
        decoratedShip.render(g);
        
        // Indicateur visuel de triple shot
        if (!isExpired()) {
            g.setColor(Color.RED);
            int x = getX();
            int y = getY();
            // Trois petites lignes au-dessus du vaisseau
            g.fillRect(x - 10, y - 15, 2, 5);
            g.fillRect(x, y - 15, 2, 5);
            g.fillRect(x + 10, y - 15, 2, 5);
        }
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
        if (isExpired()) {
            return decoratedShip.getFirePower();
        }
        return 3; // Triple shot
    }

    @Override
    public boolean hasShield() {
        return decoratedShip.hasShield();
    }

    @Override
    public String getStatus() {
        return decoratedShip.getStatus() + " + TripleShot";
    }
}