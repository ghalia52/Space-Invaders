package patterns.composite;

import java.awt.Graphics;
import java.util.List;

/**
 * Interface de base pour le pattern Composite
 * Permet de traiter uniformément objets simples et compositions
 */
public interface GameComponent {
    void update();
    void render(Graphics g);
    void add(GameComponent component);
    void remove(GameComponent component);
    List<GameComponent> getChildren();
    boolean isComposite();
    int getX();
    int getY();
    boolean isActive();
    void setActive(boolean active);
}