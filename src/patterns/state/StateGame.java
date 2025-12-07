package patterns.state;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import game.Board;
import entities.*;
import patterns.factory.ProjectileFactory;
import patterns.decorator.*;
import patterns.composite.*;
import utils.Constants;
import utils.Logger;

/**
 * État du jeu en cours - Implémentation du State Pattern
 * Gère la logique du jeu Space Invaders avec intégration des patterns obligatoires
 * FIXED: Properly stops game loop when transitioning to Won/Lost states
 */
public class StateGame implements State {

    private Board board;
    private GamePanel gamePanel;
    private ProjectileFactory projectileFactory;
    private Player player;
    private Ship playerShip;
    private GameScene gameScene;
    private AlienFormation alienFormation;
    private ArrayList<Projectile> projectiles;
    private int score;
    private boolean isPaused;
    
    // Position et vitesse du joueur
    private int playerX;
    private int playerY;
    private int playerSpeed;
    
    // Contrôle de la cadence de tir
    private long lastShotTime;
    private static final long SHOT_COOLDOWN = 300; // ms entre chaque tir

    public StateGame(Board board) {
        this.board = board;
        this.projectileFactory = new ProjectileFactory();
        this.player = new Player();
        this.projectiles = new ArrayList<>();
        this.score = 0;
        this.isPaused = false;
        this.playerX = 400;
        this.playerY = 550;
        this.playerSpeed = 8;
        this.lastShotTime = 0;
        
        Logger.state("StateGame instantiated");
        setup();
    }

    /**
     * Configuration initiale du jeu
     * Initialise tous les composants nécessaires
     */
    private void setup() {
        Logger.info("Setting up game...");
        
        // Création du panneau de jeu
        gamePanel = new GamePanel();
        
        // Application du Decorator Pattern - Vaisseau de base
        playerShip = new BasicShip(player);
        Logger.decorator("BasicShip decorator applied to Player");
        
        // Création de la scène de jeu (Composite Pattern)
        gameScene = new GameScene();
        Logger.composite("GameScene created (root composite)");
        
        // Création de la formation d'aliens
        createAlienFormation();
        
        Logger.info("Game setup complete - Ready to play!");
    }
    
    /**
     * Création de la formation d'aliens utilisant le Composite Pattern
     * Les aliens sont organisés en formation rectangulaire
     */
    private void createAlienFormation() {
        Logger.composite("Creating alien formation...");
        
        alienFormation = AlienFormation.createRectangularFormation(
            "MainFormation",
            5,      // rows
            11,     // columns
            150,    // startX
            50,     // startY
            40      // spacing
        );
        
        alienFormation.setSpeed(1);
        gameScene.getEnemyGroup().add(alienFormation);
        
        Logger.composite("AlienFormation added to GameScene (composite structure)");
        Logger.info("Alien formation created: 5 rows x 11 columns = 55 aliens");
    }
    
    /**
     * Tir d'un projectile par le joueur
     * Utilise la Factory Pattern pour créer les projectiles
     */
    private void fireProjectile() {
        long currentTime = System.currentTimeMillis();
        
        // Vérifier le cooldown
        if (currentTime - lastShotTime < SHOT_COOLDOWN) {
            return;
        }
        
        // Factory Pattern - Création du projectile
        Projectile p = projectileFactory.makeProjectile(
            Constants.NORMAL_PROJECTILE_ID,
            playerX,
            playerY - 20
        );
        projectiles.add(p);
        lastShotTime = currentTime;
        
        Logger.info("Projectile fired from position (" + playerX + ", " + playerY + ")");
    }
    
    /**
     * Mise à jour des projectiles
     * Gère le déplacement et la suppression des projectiles hors écran
     */
    private void updateProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        
        while (it.hasNext()) {
            Projectile projectile = it.next();
            
            if (!projectile.draw()) {
                it.remove();
                continue;
            }
            
            // Déplacement vers le haut
            projectile.setYCoord(projectile.getYCoord() - 15);
            
            // Suppression si hors écran
            if (projectile.getYCoord() < 0) {
                projectile.setDraw(false);
                it.remove();
            }
        }
    }
    
    /**
     * Détection et gestion des collisions
     * Vérifie les collisions projectiles-aliens et aliens-joueur
     */
    private void checkCollisions() {
        // Collision projectiles avec aliens (Composite Pattern traversal)
        for (Projectile projectile : projectiles) {
            if (!projectile.draw()) continue;
            
            for (GameComponent component : gameScene.getEnemyGroup().getChildren()) {
                if (component instanceof AlienFormation) {
                    AlienFormation formation = (AlienFormation) component;
                    
                    for (GameComponent alienComp : formation.getChildren()) {
                        if (alienComp instanceof AlienObject && alienComp.isActive()) {
                            AlienObject alien = (AlienObject) alienComp;
                            
                            if (checkProjectileAlienCollision(projectile, alien)) {
                                projectile.setDraw(false);
                                alien.hit();
                                score += 10;
                                
                                Logger.info("Collision detected! Alien destroyed. Score: " + score);
                                
                                // Vérifier la victoire
                                if (formation.countActiveComponents() == 0) {
                                    gameWon();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Vérifier si les aliens ont atteint le joueur (Game Over)
        checkAlienReachedPlayer();
    }
    
    /**
     * Vérifie la collision entre un projectile et un alien
     */
    private boolean checkProjectileAlienCollision(Projectile projectile, AlienObject alien) {
        int px = projectile.getXCoord();
        int py = projectile.getYCoord();
        int ax = alien.getX();
        int ay = alien.getY();
        
        return px > ax - 15 && px < ax + 15 && py > ay - 10 && py < ay + 10;
    }
    
    /**
     * Vérifie si un alien a atteint la position du joueur
     */
    private void checkAlienReachedPlayer() {
        for (GameComponent component : gameScene.getEnemyGroup().getChildren()) {
            if (component instanceof AlienFormation) {
                AlienFormation formation = (AlienFormation) component;
                for (GameComponent alienComp : formation.getChildren()) {
                    if (alienComp instanceof AlienObject && alienComp.isActive()) {
                        AlienObject alien = (AlienObject) alienComp;
                        if (alien.getY() > 500) {
                            Logger.info("Alien reached player position - Game Over!");
                            gameOver();
                            return;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Victoire - Transition vers StateWon (State Pattern)
     * FIXED: Now properly stops the game loop
     */
    public void gameWon() {
        Logger.state("PLAYING -> WON");
        Logger.info("Victory! Final score: " + score);
        
        // CRITICAL FIX: Stop the game loop BEFORE transitioning
        if (gamePanel != null && gamePanel.updateTimer != null) {
            gamePanel.updateTimer.stop();
            Logger.info("Game loop stopped for state transition");
        }
        
        // Now transition to Won state
        board.setCurrentState(new StateWon(board, score));
    }
    
    /**
     * Défaite - Transition vers StateLost (State Pattern)
     * FIXED: Now properly stops the game loop
     */
    public void gameOver() {
        Logger.state("PLAYING -> GAME_OVER");
        Logger.info("Game Over! Final score: " + score);
        
        // CRITICAL FIX: Stop the game loop BEFORE transitioning
        if (gamePanel != null && gamePanel.updateTimer != null) {
            gamePanel.updateTimer.stop();
            Logger.info("Game loop stopped for state transition");
        }
        
        // Now transition to Lost state
        board.setCurrentState(new StateLost(board, score));
    }

    @Override
    public JPanel getMainPanel() {
        return gamePanel;
    }
    
    @Override
    public void onEnter() {
        State.super.onEnter();
        Logger.state("Entering PLAYING state");
        Logger.info("Game started - Controls: ← → to move, SPACE to shoot, ESC to pause");
        
        // Demander le focus au panneau
        SwingUtilities.invokeLater(() -> {
            if (gamePanel != null) {
                gamePanel.requestFocusInWindow();
                Logger.info("Focus requested for game panel");
            }
        });
    }
    
    @Override
    public void onExit() {
        State.super.onExit();
        Logger.state("Exiting PLAYING state");
        
        // CRITICAL: Ensure timer is stopped when exiting
        if (gamePanel != null && gamePanel.updateTimer != null) {
            gamePanel.updateTimer.stop();
            Logger.info("Game loop stopped on exit");
        }
    }

    /**
     * Panneau de jeu principal
     * Gère l'affichage et les interactions utilisateur
     * FIXED: Timer is now accessible for proper cleanup
     */
    class GamePanel extends JPanel {
        
        Timer updateTimer; // CHANGED: Made package-private for access from outer class
        
        public GamePanel() {
            initializePanel();
            setupKeyListener();
            setupMouseListener();
            startGameLoop();
        }
        
        /**
         * Initialisation du panneau
         */
        private void initializePanel() {
            setPreferredSize(Constants.SCREEN_SIZE);
            setBackground(Color.BLACK);
            setFocusable(true);
            
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    requestFocusInWindow();
                    Logger.info("GamePanel displayed - Focus acquired");
                }
            });
        }
        
        /**
         * Configuration du listener clavier
         */
        private void setupKeyListener() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    handleKeyPress(e.getKeyCode());
                }
            });
        }
        
        /**
         * Gestion des touches du clavier
         */
        private void handleKeyPress(int keyCode) {
            switch(keyCode) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!isPaused) {
                        playerX -= playerSpeed;
                        if (playerX < 30) playerX = 30;
                    }
                    break;
                    
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!isPaused) {
                        playerX += playerSpeed;
                        if (playerX > 770) playerX = 770;
                    }
                    break;
                    
                case KeyEvent.VK_SPACE:
                    if (!isPaused) {
                        fireProjectile();
                    }
                    break;
                    
                case KeyEvent.VK_ESCAPE:
                    isPaused = !isPaused;
                    Logger.state("Game " + (isPaused ? "PAUSED" : "RESUMED"));
                    break;
            }
            repaint();
        }
        
        /**
         * Configuration du listener souris pour récupérer le focus
         */
        private void setupMouseListener() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    requestFocusInWindow();
                }
            });
        }
        
        /**
         * Démarrage de la boucle de jeu
         */
        private void startGameLoop() {
            updateTimer = new Timer(16, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isPaused) {
                        gameScene.update();
                        updateProjectiles();
                        checkCollisions();
                        repaint();
                    }
                }
            });
            updateTimer.start();
            Logger.info("Game loop started (60 FPS target)");
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawBackground(g2d);
            drawGameElements(g2d);
            drawPlayerShip(g2d);
            drawUI(g2d);
            
            if (isPaused) {
                drawPauseOverlay(g2d);
            }
        }
        
        /**
         * Dessine le fond du jeu avec gradient et étoiles
         */
        private void drawBackground(Graphics2D g2d) {
            // Gradient de fond
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(5, 10, 25),
                0, getHeight(), new Color(15, 25, 50)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Étoiles d'arrière-plan
            g2d.setColor(new Color(255, 255, 255, 150));
            java.util.Random starRandom = new java.util.Random(42);
            for (int i = 0; i < 50; i++) {
                int sx = starRandom.nextInt(getWidth());
                int sy = starRandom.nextInt(getHeight());
                g2d.fillRect(sx, sy, 2, 2);
            }
        }
        
        /**
         * Dessine les éléments du jeu (aliens et projectiles)
         */
        private void drawGameElements(Graphics2D g2d) {
            // Dessiner les aliens (Composite Pattern)
            if (gameScene != null) {
                gameScene.render(g2d);
            }
            
            // Dessiner les projectiles
            for (Projectile projectile : projectiles) {
                if (projectile.draw()) {
                    drawProjectile(g2d, projectile);
                }
            }
        }
        
        /**
         * Dessine un projectile avec effet de lueur
         */
        private void drawProjectile(Graphics2D g2d, Projectile projectile) {
            int px = projectile.getXCoord();
            int py = projectile.getYCoord();
            
            // Lueur externe
            g2d.setColor(new Color(255, 255, 200, 100));
            g2d.fillRect(px - 5, py - 14, 10, 28);
            
            // Corps du projectile
            g2d.setColor(new Color(255, 255, 100));
            g2d.fillRect(px - 3, py - 12, 6, 24);
        }
        
        /**
         * Dessine le vaisseau du joueur
         */
        private void drawPlayerShip(Graphics2D g2d) {
            // Corps principal - Triangle cyan
            g2d.setColor(new Color(0, 255, 255));
            int[] xPoints = {playerX, playerX - 30, playerX + 30};
            int[] yPoints = {playerY - 25, playerY + 20, playerY + 20};
            g2d.fillPolygon(xPoints, yPoints, 3);
            
            // Contour blanc
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawPolygon(xPoints, yPoints, 3);
            
            // Cockpit jaune
            g2d.setColor(new Color(255, 255, 100));
            g2d.fillOval(playerX - 8, playerY - 5, 16, 16);
            
            // Reflet du cockpit
            g2d.setColor(new Color(255, 255, 255, 180));
            g2d.fillOval(playerX - 5, playerY - 2, 6, 6);
            
            // Lueur des moteurs
            g2d.setColor(new Color(255, 100, 0, 100));
            g2d.fillOval(playerX - 20, playerY + 15, 10, 10);
            g2d.fillOval(playerX + 10, playerY + 15, 10, 10);
        }
        
        /**
         * Dessine l'interface utilisateur (HUD)
         */
        private void drawUI(Graphics2D g2d) {
            // Score
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 28));
            g2d.drawString("SCORE: " + score, 20, 45);
            
            // Nombre d'aliens restants
            int aliensRemaining = alienFormation.countActiveComponents();
            g2d.drawString("ALIENS: " + aliensRemaining, 20, 80);
            
            // Instructions
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.setColor(new Color(255, 255, 150, 200));
            String controls = "← → or A D: Move  |  SPACE: Shoot  |  ESC: Pause";
            FontMetrics fm = g2d.getFontMetrics();
            int controlsWidth = fm.stringWidth(controls);
            g2d.drawString(controls, (getWidth() - controlsWidth) / 2, getHeight() - 15);
        }
        
        /**
         * Dessine l'overlay de pause
         */
        private void drawPauseOverlay(Graphics2D g2d) {
            // Fond semi-transparent
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Texte "PAUSED"
            g2d.setColor(new Color(255, 255, 100));
            g2d.setFont(new Font("Arial", Font.BOLD, 72));
            String pauseText = "PAUSED";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(pauseText);
            g2d.drawString(pauseText, (getWidth() - textWidth) / 2, getHeight() / 2 - 20);
            
            // Instructions
            g2d.setFont(new Font("Arial", Font.PLAIN, 24));
            g2d.setColor(Color.WHITE);
            String resumeText = "Press ESC to Resume";
            textWidth = g2d.getFontMetrics().stringWidth(resumeText);
            g2d.drawString(resumeText, (getWidth() - textWidth) / 2, getHeight() / 2 + 40);
        }
    }
}