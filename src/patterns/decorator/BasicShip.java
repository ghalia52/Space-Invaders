package patterns.decorator;

import entities.Player;
import utils.Logger;
import java.awt.Graphics;

public class BasicShip implements Ship {
    private Player player;
    private int baseSpeed = 2;
    private int baseFireRate = 300; // ms entre les tirs
    private int baseFirePower = 1;

    public BasicShip(Player player) {
        this.player = player;
        Logger.log("INFO", "BasicShip created");
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