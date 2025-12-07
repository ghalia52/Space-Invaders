package entities;

/**
 * Interface pour tous les projectiles
 */
public interface Projectile {
    boolean draw();
    void setDraw(boolean draw);
    int getXCoord();
    int getYCoord();
    int getDamage();
    void setXCoord(int xCoord);
    void setYCoord(int yCoord);
}