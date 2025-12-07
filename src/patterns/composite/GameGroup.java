package patterns.composite;

import utils.Logger;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Composite dans le pattern Composite
 * Peut contenir d'autres composants (feuilles ou composites)
 */
public class GameGroup implements GameComponent {
    protected String name;
    protected List<GameComponent> children;
    protected int x, y;
    protected boolean active = true;

    public GameGroup(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        Logger.log("COMPOSITE", "GameGroup created: " + name);
    }

    public GameGroup(String name, int x, int y) {
        this(name);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if (!active) return;
        
        // Mise à jour de tous les enfants
        Iterator<GameComponent> iterator = children.iterator();
        while (iterator.hasNext()) {
            GameComponent component = iterator.next();
            if (component.isActive()) {
                component.update();
            } else {
                // Retirer les composants inactifs
                iterator.remove();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (!active) return;
        
        // Rendu de tous les enfants
        for (GameComponent component : children) {
            if (component.isActive()) {
                component.render(g);
            }
        }
    }

    @Override
    public void add(GameComponent component) {
        if (component != null && !children.contains(component)) {
            children.add(component);
            Logger.log("COMPOSITE", "Component added to " + name + 
                      " - Total: " + children.size());
        }
    }

    @Override
    public void remove(GameComponent component) {
        if (children.remove(component)) {
            Logger.log("COMPOSITE", "Component removed from " + name + 
                      " - Remaining: " + children.size());
        }
    }

    @Override
    public List<GameComponent> getChildren() {
        return new ArrayList<>(children); // Retourne une copie
    }

    @Override
    public boolean isComposite() {
        return true;
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
        // Propager aux enfants
        for (GameComponent child : children) {
            child.setActive(active);
        }
        Logger.log("COMPOSITE", name + " set to " + (active ? "active" : "inactive"));
    }

    public String getName() {
        return name;
    }

    public int getChildCount() {
        return children.size();
    }

    /**
     * Retire tous les enfants inactifs
     */
    public void cleanupInactive() {
        int beforeCount = children.size();
        children.removeIf(component -> !component.isActive());
        int afterCount = children.size();
        
        if (beforeCount != afterCount) {
            Logger.log("COMPOSITE", name + " cleaned up: " + 
                      (beforeCount - afterCount) + " inactive components removed");
        }
    }

    /**
     * Compte tous les composants actifs (récursivement)
     */
    public int countActiveComponents() {
        int count = 0;
        for (GameComponent component : children) {
            if (component.isActive()) {
                count++;
                if (component.isComposite()) {
                    GameGroup group = (GameGroup) component;
                    count += group.countActiveComponents();
                }
            }
        }
        return count;
    }
}