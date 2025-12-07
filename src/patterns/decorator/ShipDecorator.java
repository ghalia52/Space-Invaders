package patterns.decorator;

import utils.Logger;
import java.awt.Graphics;


public abstract class ShipDecorator implements Ship {
    protected Ship decoratedShip;
    protected long appliedTime;
    protected long duration; // Durée en millisecondes (0 = permanent)

    public ShipDecorator(Ship ship, long duration) {
        this.decoratedShip = ship;
        this.appliedTime = System.currentTimeMillis();
        this.duration = duration;
        
        String decoratorName = this.getClass().getSimpleName();
        Logger.log("DECORATOR", decoratorName + " applied to " + ship.getStatus());
    }

    @Override
    public void render(Graphics g) {
        decoratedShip.render(g);
    }

    @Override
    public void update() {
        decoratedShip.update();
        checkExpiration();
    }

    @Override
    public int getX() {
        return decoratedShip.getX();
    }

    @Override
    public int getY() {
        return decoratedShip.getY();
    }

    @Override
    public void setX(int x) {
        decoratedShip.setX(x);
    }

    @Override
    public void setY(int y) {
        decoratedShip.setY(y);
    }

    /**
     * Vérifie si le power-up a expiré
     */
    protected void checkExpiration() {
        if (duration > 0) {
            long elapsed = System.currentTimeMillis() - appliedTime;
            if (elapsed >= duration) {
                onExpire();
            }
        }
    }

    /**
     * Appelé quand le power-up expire
     */
    protected void onExpire() {
        String decoratorName = this.getClass().getSimpleName();
        Logger.log("DECORATOR", decoratorName + " expired");
    }

    /**
     * Retourne le vaisseau décoré (pour le "unwrapping")
     */
    public Ship getDecoratedShip() {
        return decoratedShip;
    }

    public boolean isExpired() {
        if (duration == 0) return false;
        return (System.currentTimeMillis() - appliedTime) >= duration;
    }
}