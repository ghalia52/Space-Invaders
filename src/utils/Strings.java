package utils;

/**
 * Chaînes de caractères de l'interface utilisateur
 */
public class Strings {
    
    // Menu
    public static final String MENU_HEADER = "SPACE INVADERS";
    public static final String MENU_BUTTON_START = "Start Game";
    public static final String MENU_BUTTON_ABOUT = "About";
    public static final String MENU_BUTTON_CREDITS = "Credits";
    
    // About
    public static final String ABOUT_HEADER = "About";
    public static final String ABOUT_TEXT = "<html><center>" +
        "Space Invaders - Design Patterns Project<br><br>" +
        "This game demonstrates the implementation of:<br>" +
        "• State Pattern<br>" +
        "• Decorator Pattern<br>" +
        "• Composite Pattern<br>" +
        "• Factory Pattern<br><br>" +
        "Defend Earth from alien invaders!" +
        "</center></html>";
    
    // Credits
    public static final String CREDITS_HEADER = "Credits";
    public static final String CREDITS_TEXT = 
        "Developed for Design Patterns Course\n\n" +
        "Design Patterns Used:\n" +
        "- State Pattern (Game States)\n" +
        "- Decorator Pattern (Power-ups)\n" +
        "- Composite Pattern (Game Hierarchy)\n" +
        "- Factory Pattern (Projectile Creation)\n\n" +
        "Academic Year: 2025-2026";
    
    // Game Over
    public static final String LOST_HEADER = "GAME OVER";
    
    // Victory
    public static final String WIN_HEADER = "VICTORY!";
    public static final String WIN_SUB_HEADER = "Developed by:";
    public static final String DEVELOPER1 = "Developer 1 Name";
    public static final String DEVELOPER2 = "Developer 2 Name";
    
    // Buttons
    public static final String BUTTON_RETURN = "Return to Menu";
    public static final String BUTTON_BACK = "Back";
    
    // Actions
    public static final String ACTION_GAME = "START_GAME";
    public static final String ACTION_ABOUT = "SHOW_ABOUT";
    public static final String ACTION_CREDITS = "SHOW_CREDITS";
    public static final String ACTION_MENU = "RETURN_MENU";
    public static final String ACTION_RETURN = "RETURN";
}