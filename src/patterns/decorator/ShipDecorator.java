package patterns.decorator;

import utils.Logger;
import java.awt.Graphics;

public abstract class ShipDecorator implements Ship {
    protected Ship decoratedShip;
    protected long appliedTime;
    protected long duration;
    private boolean hasExpired = false; // FIX: Track if already expired

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

    protected void checkExpiration() {
        if (duration > 0 && !hasExpired) { // FIX: Only check if not already expired
            long elapsed = System.currentTimeMillis() - appliedTime;
            if (elapsed >= duration) {
                hasExpired = true; // FIX: Mark as expired
                onExpire();
            }
        }
    }

    protected void onExpire() {
        String decoratorName = this.getClass().getSimpleName();
        Logger.log("DECORATOR", decoratorName + " expired");
    }

    public Ship getDecoratedShip() {
        return decoratedShip;
    }

    public boolean isExpired() {
        if (duration == 0) return false;
        return (System.currentTimeMillis() - appliedTime) >= duration;
    }
}