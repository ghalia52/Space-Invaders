package patterns.composite;

import utils.Logger;
import java.awt.Graphics;

/**
 * Scène de jeu complète utilisant le Composite Pattern
 * Organise tous les éléments du jeu dans une hiérarchie
 */
public class GameScene extends GameGroup {
    private GameGroup playerGroup;
    private GameGroup enemyGroup;
    private GameGroup projectileGroup;
    private GameGroup powerUpGroup;

    public GameScene() {
        super("GameScene");
        initialize();
    }

    private void initialize() {
        // Créer la hiérarchie
        playerGroup = new GameGroup("PlayerGroup");
        enemyGroup = new GameGroup("EnemyGroup");
        projectileGroup = new ProjectileGroup();
        powerUpGroup = new GameGroup("PowerUpGroup");

        // Ajouter à la scène
        add(playerGroup);
        add(enemyGroup);
        add(projectileGroup);
        add(powerUpGroup);

        Logger.log("COMPOSITE", "GameScene initialized with hierarchy");
    }

    public GameGroup getPlayerGroup() {
        return playerGroup;
    }

    public GameGroup getEnemyGroup() {
        return enemyGroup;
    }

    public GameGroup getProjectileGroup() {
        return projectileGroup;
    }

    public GameGroup getPowerUpGroup() {
        return powerUpGroup;
    }

    /**
     * Affiche la hiérarchie complète (debug)
     */
    public void printHierarchy() {
        Logger.log("COMPOSITE", "=== Game Hierarchy ===");
        Logger.log("COMPOSITE", "Scene: " + countActiveComponents() + " total components");
        Logger.log("COMPOSITE", "  Players: " + playerGroup.getChildCount());
        Logger.log("COMPOSITE", "  Enemies: " + enemyGroup.countActiveComponents());
        Logger.log("COMPOSITE", "  Projectiles: " + projectileGroup.getChildCount());
        Logger.log("COMPOSITE", "  PowerUps: " + powerUpGroup.getChildCount());
    }
}