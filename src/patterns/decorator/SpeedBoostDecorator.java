package patterns.decorator;

import utils.Logger;
import java.awt.Graphics;
import java.awt.Color;


public class SpeedBoostDecorator extends ShipDecorator {
    private int speedMultiplier = 2;

    public SpeedBoostDecorator(Ship ship) {
        super(ship, 5000); // 5 secondes
        Logger.log("DECORATOR", "SpeedBoost applied - Speed x" + speedMultiplier);
    }

    @Override
    public void render(Graphics g) {
        decoratedShip.render(g);
        
        // Effet visuel : traînée de vitesse
        if (!isExpired()) {
            g.setColor(new Color(255, 255, 0, 100));
            int x = getX();
            int y = getY();
            g.fillOval(x - 15, y + 10, 10, 5);
            g.fillOval(x + 5, y + 10, 10, 5);
        }
    }

    @Override
    public int getSpeed() {
        if (isExpired()) {
            return decoratedShip.getSpeed();
        }
        return decoratedShip.getSpeed() * speedMultiplier;
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
        return decoratedShip.hasShield();
    }

    @Override
    public String getStatus() {
        return decoratedShip.getStatus() + " + SpeedBoost";
    }
}