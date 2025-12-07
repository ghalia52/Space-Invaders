package patterns.decorator;

import java.awt.Graphics;


public interface Ship {
    void render(Graphics g);
    void update();
    int getSpeed();
    int getFireRate();
    int getFirePower();
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    boolean hasShield();
    String getStatus(); // Pour le logging
}