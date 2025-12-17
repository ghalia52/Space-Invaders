package patterns.decorator;

import utils.Logger;
import utils.Constants;
import java.awt.Graphics;
import java.awt.Color;

public class TripleShotDecorator extends ShipDecorator {
    
    public TripleShotDecorator(Ship ship) {
        super(ship, Constants.TRIPLE_SHOT_DURATION);
        Logger.log("DECORATOR", "TripleShot applied");
    }

    @Override
    public void render(Graphics g) {
        decoratedShip.render(g);
        
        if (!isExpired()) {
            g.setColor(Color.RED);
            int x = getX();
            int y = getY();
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
        return 3;
    }

    @Override
    public boolean hasShield() {
        return decoratedShip.hasShield();
    }

    @Override
    public String getStatus() {
        if (isExpired()) {
            return decoratedShip.getStatus();
        }
        return decoratedShip.getStatus() + " + TripleShot";
    }
}