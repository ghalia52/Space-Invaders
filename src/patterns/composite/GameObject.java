package patterns.composite;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet feuille (Leaf) dans le pattern Composite
 * Représente un objet individuel qui ne peut pas avoir d'enfants
 */
public abstract class GameObject implements GameComponent {
    protected int x, y;
    protected boolean active = true;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Graphics g);

    @Override
    public void add(GameComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf object");
    }

    @Override
    public void remove(GameComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf object");
    }

    @Override
    public List<GameComponent> getChildren() {
        return new ArrayList<>(); // Retourne une liste vide
    }

    @Override
    public boolean isComposite() {
        return false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}