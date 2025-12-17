package patterns.decorator;

import entities.Player;
import utils.Constants;
import utils.Logger;
import java.awt.Graphics;

/**
 * DECORATOR PATTERN - Component concret
 * Vaisseau de base qui wrappe le Player
 */
public class BasicShip implements Ship {
    private Player player;
    private int baseSpeed;
    private int baseFireRate;
    private int baseFirePower;

    public BasicShip(Player player) {
        this.player = player;
        this.baseSpeed = Constants.BASE_PLAYER_SPEED;
        this.baseFireRate = Constants.BASE_FIRE_RATE;
        this.baseFirePower = Constants.BASE_FIRE_POWER;
        Logger.log("DECORATOR", "BasicShip created - Speed:" + baseSpeed + " FireRate:" + baseFireRate + "ms");
    }

    @Override
    public void render(Graphics g) {
        player.draw(g);
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public int getSpeed() {
        return baseSpeed;
    }

    @Override
    public int getFireRate() {
        return baseFireRate;
    }

    @Override
    public int getFirePower() {
        return baseFirePower;
    }

    @Override
    public int getX() {
        return player.getX();
    }

    @Override
    public int getY() {
        return player.getY();
    }

    @Override
    public void setX(int x) {
        player.setX(x);
    }

    @Override
    public void setY(int y) {
        player.setY(y);
    }

    @Override
    public boolean hasShield() {
        return false;
    }

    @Override
    public String getStatus() {
        return "BasicShip";
    }

    public Player getPlayer() {
        return player;
    }
}