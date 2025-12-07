package utils;

import java.awt.*;

/**
 * Constantes du jeu - Configuration complète
 */
public interface Constants {
    
    // Screen dimensions
    Dimension SCREEN_SIZE = new Dimension(1000, 800);
    
    // Colors
    Color COLOR_WINDOW_BG = new Color(10, 15, 35);
    Color COLOR_BUTTON_BG = new Color(70, 130, 180);
    Color COLOR_BUTTON_HOVER = new Color(100, 149, 237);
    Color COLOR_WHITE = Color.WHITE;
    Color COLOR_TITLE = new Color(255, 215, 0);
    
    // Fonts
    Font FONT_HEADER = new Font("Arial", Font.BOLD, 72);
    Font FONT_SUB_HEADER = new Font("Arial", Font.BOLD, 36);
    Font FONT_BUTTON = new Font("Arial", Font.BOLD, 24);
    Font FONT_TEXT = new Font("Arial", Font.PLAIN, 18);
    
    // Sizes
    Dimension HEADER_SIZE = new Dimension(700, 120);
    Dimension SUB_HEADER_SIZE = new Dimension(600, 80);
    Dimension BUTTON_SIZE = new Dimension(300, 60);
    Dimension BUTTON_RETURN_SIZE = new Dimension(250, 55);
    Dimension ABOUT_TEXT_BOX = new Dimension(650, 250);
    Dimension CREDIT_TEXT_SIZE = new Dimension(550, 50);
    
    // Spacing
    Dimension HEADER_PADDING = new Dimension(0, 40);
    Dimension HEADER_BUTTON_PADDING = new Dimension(0, 60);
    Dimension BUTTON_PADDING = new Dimension(0, 25);
    Dimension RETURN_BUTTON_PADDING = new Dimension(0, 40);
    Dimension LOST_BUTTON_PADDING = new Dimension(0, 60);
    Dimension CREDIT_BUTTON_PADDING = new Dimension(0, 50);
    
    // Player
    int PLAYER_START_X = 400;
    int PLAYER_START_Y = 550;
    int PLAYER_WIDTH = 40;
    int PLAYER_HEIGHT = 30;
    String PLAYER_IMAGE = "resources/player.png";
    
    // Aliens
    int ALIEN_ROWS = 5;
    int ALIEN_COLUMNS = 11;
    int NORMAL_ALIEN_WIDTH = 30;
    int NORMAL_ALIEN_HEIGHT = 20;
    int NORMAL_ALIEN_HEALTH = 1;
    int TOUGH_ALIEN_HEALTH = 2;
    int ALIEN_MOVEMENT_MODIFIER = 5;
    String TOUGH_ALIEN_IMAGE = "resources/tough_alien.png";
    String NORMAL_ALIEN_IMAGE = "resources/alien.png";
    
    // Projectiles - Factory Pattern IDs
    int NORMAL_PROJECTILE_ID = 0;
    int POWER_PROJECTILE_ID = 1;
    int ALIEN_BOMB_ID = 2;
    
    int NORMAL_PROJECTILE_WIDTH = 5;
    int NORMAL_PROJECTILE_HEIGHT = 15;
    int NORMAL_PROJECTILE_DAMAGE = 1;
    String NORMAL_PROJECTILE_IMAGE = "resources/projectile.png";
    
    int POWER_PROJECTILE_WIDTH = 8;
    int POWER_PROJECTILE_HEIGHT = 20;
    int POWER_PROJECTILE_DAMAGE = 2;
    String POWER_PROJECTILE_IMAGE = "resources/power_projectile.png";
    
    int ALIEN_BOMB_WIDTH = 6;
    int ALIEN_BOMB_HEIGHT = 12;
    int ALIEN_BOMB_DAMAGE = 1;
    String ALIEN_BOMB_IMAGE = "resources/bomb.png";
    
    // Directions for alien movement
    int DIRECTION_LEFT = 0;
    int DIRECTION_RIGHT = 1;
    int DIRECTION_DOWN = 2;
}