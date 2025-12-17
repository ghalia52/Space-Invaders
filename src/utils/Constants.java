package utils;

import java.awt.*;

/**
 * Constantes du jeu - Configuration complète
 * Toutes les valeurs magiques sont définies ici
 */
public interface Constants {
    
    // ===== SCREEN =====
    Dimension SCREEN_SIZE = new Dimension(1000, 800);
    
    // ===== PLAYER =====
    int PLAYER_START_X = 400;
    int PLAYER_START_Y = 550;
    int PLAYER_MIN_X = 30;          // Limite gauche
    int PLAYER_MAX_X = 770;         // Limite droite
    int PLAYER_WIDTH = 40;
    int PLAYER_HEIGHT = 30;
    String PLAYER_IMAGE = "resources/player.png";
    
    // ===== PLAYER STATS (pour Decorator Pattern) =====
    int BASE_PLAYER_SPEED = 8;
    int BASE_FIRE_RATE = 300;       // ms entre les tirs
    int BASE_FIRE_POWER = 1;
    
    // ===== ALIENS =====
    int ALIEN_ROWS = 5;
    int ALIEN_COLUMNS = 11;
    int NORMAL_ALIEN_WIDTH = 30;
    int NORMAL_ALIEN_HEIGHT = 20;
    int NORMAL_ALIEN_HEALTH = 1;
    int ALIEN_GAME_OVER_Y = 500;    // Si un alien atteint Y=500, game over
    String NORMAL_ALIEN_IMAGE = "resources/alien.png";
    
    // ===== PROJECTILES (Factory Pattern IDs) =====
    int NORMAL_PROJECTILE_ID = 0;
    int POWER_PROJECTILE_ID = 1;
    int ALIEN_BOMB_ID = 2;
    
    // Normal Shot
    int NORMAL_PROJECTILE_WIDTH = 5;
    int NORMAL_PROJECTILE_HEIGHT = 15;
    int NORMAL_PROJECTILE_DAMAGE = 1;
    int NORMAL_PROJECTILE_SPEED = 15;
    String NORMAL_PROJECTILE_IMAGE = "resources/projectile.png";
    
    // Power Shot
    int POWER_PROJECTILE_WIDTH = 8;
    int POWER_PROJECTILE_HEIGHT = 20;
    int POWER_PROJECTILE_DAMAGE = 2;
    String POWER_PROJECTILE_IMAGE = "resources/power_projectile.png";
    
    // Alien Bomb
    int ALIEN_BOMB_WIDTH = 6;
    int ALIEN_BOMB_HEIGHT = 12;
    int ALIEN_BOMB_DAMAGE = 1;
    String ALIEN_BOMB_IMAGE = "resources/bomb.png";
    
    // ===== SCORING =====
    int POINTS_PER_ALIEN = 10;
    int POWERUP_SCORE_INTERVAL = 50;  // Power-up tous les 50 points
    
    // ===== DECORATOR DURATIONS (ms) =====
    long SPEED_BOOST_DURATION = 5000;     // 5 secondes
    long TRIPLE_SHOT_DURATION = 8000;     // 8 secondes
    long RAPID_FIRE_DURATION = 7000;      // 7 secondes
    long SHIELD_DURATION = 10000;         // 10 secondes
    
    // ===== GAME LOOP =====
    int GAME_LOOP_DELAY = 16;  // ~60 FPS (1000ms / 60 = 16.67ms)
}