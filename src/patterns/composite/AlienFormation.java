package patterns.composite;

import entities.Alien;
import utils.Logger;
import java.awt.Graphics;

/**
 * Formation d'aliens utilisant le Composite Pattern
 * Gère le mouvement coordonné de plusieurs aliens
 */
public class AlienFormation extends GameGroup {
    private int direction = 1; // 1 = droite, -1 = gauche
    private int speed = 1;
    private int moveDownAmount = 10;
    private boolean shouldMoveDown = false;

    public AlienFormation(String name) {
        super(name);
        Logger.log("COMPOSITE", "AlienFormation created: " + name);
    }

    @Override
    public void update() {
        if (!active || children.isEmpty()) return;

        // Vérifier si la formation touche les bords
        checkBoundaries();

        // Déplacer toute la formation
        if (shouldMoveDown) {
            moveDown();
            shouldMoveDown = false;
            direction *= -1; // Changer de direction
        } else {
            moveHorizontally();
        }

        // Mettre à jour les enfants individuels
        super.update();
    }

    private void checkBoundaries() {
        int leftmost = Integer.MAX_VALUE;
        int rightmost = Integer.MIN_VALUE;

        for (GameComponent component : children) {
            if (component.isActive()) {
                int x = component.getX();
                leftmost = Math.min(leftmost, x);
                rightmost = Math.max(rightmost, x);
            }
        }

        // Limites de l'écran (à ajuster selon votre jeu)
        if ((direction > 0 && rightmost >= 750) || 
            (direction < 0 && leftmost <= 50)) {
            shouldMoveDown = true;
        }
    }

    private void moveHorizontally() {
        for (GameComponent component : children) {
            if (component.isActive() && component instanceof AlienObject) {
                AlienObject alien = (AlienObject) component;
                alien.moveBy(direction * speed, 0);
            }
        }
    }

    private void moveDown() {
        Logger.log("INFO", "AlienFormation moving down");
        for (GameComponent component : children) {
            if (component.isActive() && component instanceof AlienObject) {
                AlienObject alien = (AlienObject) component;
                alien.moveBy(0, moveDownAmount);
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        Logger.log("INFO", "AlienFormation speed set to: " + speed);
    }

    /**
     * Crée une formation rectangulaire d'aliens
     */
    public static AlienFormation createRectangularFormation(
            String name, int rows, int cols, int startX, int startY, int spacing) {
        
        AlienFormation formation = new AlienFormation(name);
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = startX + (col * spacing);
                int y = startY + (row * spacing);
                AlienObject alien = new AlienObject(x, y);
                formation.add(alien);
            }
        }
        
        Logger.log("COMPOSITE", "Rectangular formation created: " + 
                  rows + "x" + cols + " = " + (rows * cols) + " aliens");
        
        return formation;
    }
}