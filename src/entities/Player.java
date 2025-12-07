package entities;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import utils.Constants;
import utils.Logger;

/**
 * Classe du joueur
 * Peut être encapsulée dans le Decorator Pattern via BasicShip
 */
public class Player implements ScreenItem {

    private int xCoord;
    private int yCoord;
    private BufferedImage image;

    public Player() {
        this.xCoord = Constants.PLAYER_START_X;
        this.yCoord = Constants.PLAYER_START_Y;

        try {
            image = ImageIO.read(new File(Constants.PLAYER_IMAGE));
            Logger.info("Player image loaded successfully");
        } catch (IOException e) {
            Logger.error("Failed to load player image: " + e.getMessage());
            // Créer une image par défaut
            image = new BufferedImage(
                Constants.PLAYER_WIDTH, 
                Constants.PLAYER_HEIGHT, 
                BufferedImage.TYPE_INT_ARGB
            );
        }
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getXCoord() {
        return xCoord;
    }

    @Override
    public int getYCoord() {
        return yCoord;
    }

    @Override
    public int getHeight() {
        return Constants.PLAYER_HEIGHT;
    }

    @Override
    public int getWidth() {
        return Constants.PLAYER_WIDTH;
    }

    @Override
    public boolean draw() {
        return true;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    
    /**
     * Méthodes pour compatibilité avec le Decorator Pattern
     */
    public int getX() {
        return xCoord;
    }
    
    public int getY() {
        return yCoord;
    }
    
    public void setX(int x) {
        this.xCoord = x;
    }
    
    public void setY(int y) {
        this.yCoord = y;
    }
    
    /**
     * Méthode de dessin pour le decorator
     */
    public void draw(Graphics g) {
        g.drawImage(image, xCoord, yCoord, getWidth(), getHeight(), null);
    }
    
    /**
     * Mise à jour du joueur
     */
    public void update() {
        // Logique de mise à jour si nécessaire
    }
}