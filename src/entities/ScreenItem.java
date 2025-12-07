package entities;

import java.awt.image.BufferedImage;

/**
 * Interface pour tous les objets affichables à l'écran
 */
public interface ScreenItem {
    BufferedImage getImage();
    int getXCoord();
    int getYCoord();
    int getHeight();
    int getWidth();
    boolean draw();
}