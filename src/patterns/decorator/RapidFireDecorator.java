package patterns.decorator;

import utils.Logger;
import java.awt.Graphics;


public class RapidFireDecorator extends ShipDecorator {
    private double fireRateMultiplier = 0.5; // 2x plus rapide

    public RapidFireDecorator(Ship ship) {
        super(ship, 7000); // 7 secondes
        Logger.log("DECORATOR", "RapidFire applied - Fire rate x2");
    }

    @Override
    public int getSpeed() {
        return decoratedShip.getSpeed();
    }

    @Override
    public int getFireRate() {
        if (isExpired()) {
            return decoratedShip.getFireRate();
        }
        return (int)(decoratedShip.getFireRate() * fireRateMultiplier);
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
        return decoratedShip.getStatus() + " + RapidFire";
    }
}