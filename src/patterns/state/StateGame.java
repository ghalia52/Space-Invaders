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
 * FIXED: Decorators now properly affect gameplay
 * - SpeedBoost visibly increases movement
 * - TripleShot fires 3 projectiles
 * - RapidFire reduces cooldown
 * - Shield protects from alien bombs (if implemented)
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
    private int lastPowerUpScore = 0;
    
    private int playerX;
    private int playerY;
    
    private long lastShotTime;

    public StateGame(Board board) {
        this.board = board;
        this.projectileFactory = new ProjectileFactory();
        this.player = new Player();
        this.projectiles = new ArrayList<>();
        this.score = 0;
        this.isPaused = false;
        this.playerX = 400;
        this.playerY = 550;
        this.lastShotTime = 0;
        
        Logger.state("StateGame instantiated");
        setup();
    }

    private void setup() {
        Logger.info("Setting up game with all design patterns...");
        
        gamePanel = new GamePanel();
        
        // Initialize with BasicShip
        playerShip = new BasicShip(player);
        Logger.decorator("BasicShip created - Speed:" + playerShip.getSpeed() + 
                        " FireRate:" + playerShip.getFireRate() + "ms");
        
        gameScene = new GameScene();
        Logger.composite("GameScene created (root composite)");
        
        createAlienFormation();
        
        Logger.info("Game setup complete - All 4 patterns active!");
    }
    
    private void createAlienFormation() {
        Logger.composite("Creating alien formation...");
        
        alienFormation = AlienFormation.createRectangularFormation(
            "MainFormation", 5, 11, 150, 50, 40
        );
        
        alienFormation.setSpeed(1);
        gameScene.getEnemyGroup().add(alienFormation);
        
        Logger.composite("AlienFormation (5x11=55 aliens) added to GameScene");
    }
    
    private void checkAndApplyPowerUps() {
        if (score >= lastPowerUpScore + 50 && score > 0) {
            lastPowerUpScore = score;
            applyRandomPowerUp();
        }
    }
    
    private void applyRandomPowerUp() {
        int random = (int)(Math.random() * 4);
        
        String powerUpName = "";
        switch(random) {
            case 0:
                playerShip = new SpeedBoostDecorator(playerShip);
                powerUpName = "SpeedBoost (Speed x2)";
                break;
            case 1:
                playerShip = new TripleShotDecorator(playerShip);
                powerUpName = "TripleShot (3 bullets)";
                break;
            case 2:
                playerShip = new RapidFireDecorator(playerShip);
                powerUpName = "RapidFire (Cooldown x0.5)";
                break;
            case 3:
                playerShip = new ShieldDecorator(playerShip);
                powerUpName = "Shield (3 hits)";
                break;
        }
        
        Logger.decorator(powerUpName + " applied! Current stats: Speed=" + 
                        playerShip.getSpeed() + " FireRate=" + playerShip.getFireRate());
    }
    
    /**
     * CRITICAL FIX: Directly use decorator values instead of caching
     */
    private void fireProjectile() {
        long currentTime = System.currentTimeMillis();
        
        // Get fire rate DIRECTLY from decorator (not cached)
        long shotCooldown = playerShip.getFireRate();
        
        if (currentTime - lastShotTime < shotCooldown) {
            return; // Still on cooldown
        }
        
        // Get fire power DIRECTLY from decorator
        int firePower = playerShip.getFirePower();
        
        if (firePower == 1) {
            // Normal single shot
            Projectile p = projectileFactory.makeProjectile(
                Constants.NORMAL_PROJECTILE_ID, playerX, playerY - 20
            );
            projectiles.add(p);
            Logger.info("Normal shot fired (FirePower=1)");
            
        } else if (firePower >= 3) {
            // Triple shot
            Projectile left = projectileFactory.makeProjectile(
                Constants.NORMAL_PROJECTILE_ID, playerX - 15, playerY - 20
            );
            Projectile center = projectileFactory.makeProjectile(
                Constants.NORMAL_PROJECTILE_ID, playerX, playerY - 20
            );
            Projectile right = projectileFactory.makeProjectile(
                Constants.NORMAL_PROJECTILE_ID, playerX + 15, playerY - 20
            );
            projectiles.add(left);
            projectiles.add(center);
            projectiles.add(right);
            Logger.info("Triple shot fired! (FirePower=" + firePower + ")");
        }
        
        lastShotTime = currentTime;
    }
    
    private void updateProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        
        while (it.hasNext()) {
            Projectile projectile = it.next();
            
            if (!projectile.draw()) {
                it.remove();
                continue;
            }
            
            projectile.setYCoord(projectile.getYCoord() - 15);
            
            if (projectile.getYCoord() < 0) {
                projectile.setDraw(false);
                it.remove();
            }
        }
    }
    
    private void checkCollisions() {
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
                                
                                Logger.info("Hit! Score: " + score + " (Remaining: " + 
                                           formation.countActiveComponents() + ")");
                                
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
        
        checkAndApplyPowerUps();
        checkAlienReachedPlayer();
    }
    
    private boolean checkProjectileAlienCollision(Projectile projectile, AlienObject alien) {
        int px = projectile.getXCoord();
        int py = projectile.getYCoord();
        int ax = alien.getX();
        int ay = alien.getY();
        
        return px > ax - 15 && px < ax + 15 && py > ay - 10 && py < ay + 10;
    }
    
    private void checkAlienReachedPlayer() {
        for (GameComponent component : gameScene.getEnemyGroup().getChildren()) {
            if (component instanceof AlienFormation) {
                AlienFormation formation = (AlienFormation) component;
                for (GameComponent alienComp : formation.getChildren()) {
                    if (alienComp instanceof AlienObject && alienComp.isActive()) {
                        AlienObject alien = (AlienObject) alienComp;
                        if (alien.getY() > 500) {
                            Logger.info("Alien reached player - Game Over!");
                            gameOver();
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public void gameWon() {
        Logger.state("STATE TRANSITION: PLAYING -> WON");
        Logger.info("VICTORY! Final score: " + score);
        
        if (gamePanel != null && gamePanel.updateTimer != null) {
            gamePanel.updateTimer.stop();
        }
        
        board.setCurrentState(new StateWon(board, score));
    }
    
    public void gameOver() {
        Logger.state("STATE TRANSITION: PLAYING -> LOST");
        Logger.info("GAME OVER! Final score: " + score);
        
        if (gamePanel != null && gamePanel.updateTimer != null) {
            gamePanel.updateTimer.stop();
        }
        
        board.setCurrentState(new StateLost(board, score));
    }

    @Override
    public JPanel getMainPanel() {
        return gamePanel;
    }
    
    @Override
    public void onEnter() {
        State.super.onEnter();
        Logger.state("STATE: Entering PLAYING state");
        Logger.info("Controls: LEFT/RIGHT = Move | SPACE = Shoot | ESC = Pause");
        
        SwingUtilities.invokeLater(() -> {
            if (gamePanel != null) {
                gamePanel.requestFocusInWindow();
            }
        });
    }
    
    @Override
    public void onExit() {
        State.super.onExit();
        Logger.state("STATE: Exiting PLAYING state");
        
        if (gamePanel != null && gamePanel.updateTimer != null) {
            gamePanel.updateTimer.stop();
        }
    }

    class GamePanel extends JPanel {
        
        Timer updateTimer;
        
        public GamePanel() {
            initializePanel();
            setupKeyListener();
            setupMouseListener();
            startGameLoop();
        }
        
        private void initializePanel() {
            setPreferredSize(Constants.SCREEN_SIZE);
            setBackground(Color.BLACK);
            setFocusable(true);
            
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    requestFocusInWindow();
                }
            });
        }
        
        private void setupKeyListener() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    handleKeyPress(e.getKeyCode());
                }
            });
        }
        
        /**
         * CRITICAL FIX: Get speed directly from decorator each time
         */
        private void handleKeyPress(int keyCode) {
            switch(keyCode) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!isPaused) {
                        // Get speed DIRECTLY from decorator
                        int speed = playerShip.getSpeed();
                        playerX -= speed;
                        if (playerX < 30) playerX = 30;
                        
                        Logger.info("Move LEFT - Speed: " + speed + " NewX: " + playerX);
                    }
                    break;
                    
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!isPaused) {
                        // Get speed DIRECTLY from decorator
                        int speed = playerShip.getSpeed();
                        playerX += speed;
                        if (playerX > 770) playerX = 770;
                        
                        Logger.info("Move RIGHT - Speed: " + speed + " NewX: " + playerX);
                    }
                    break;
                    
                case KeyEvent.VK_SPACE:
                    if (!isPaused) {
                        fireProjectile(); // Uses decorator fire rate
                    }
                    break;
                    
                case KeyEvent.VK_ESCAPE:
                    isPaused = !isPaused;
                    Logger.state("Game " + (isPaused ? "PAUSED" : "RESUMED"));
                    break;
            }
            repaint();
        }
        
        private void setupMouseListener() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    requestFocusInWindow();
                }
            });
        }
        
        private void startGameLoop() {
            updateTimer = new Timer(16, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isPaused) {
                        gameScene.update();
                        updateProjectiles();
                        checkCollisions();
                        playerShip.update(); // Updates decorators
                        repaint();
                    }
                }
            });
            updateTimer.start();
            Logger.info("Game loop started at 60 FPS");
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
        
        private void drawBackground(Graphics2D g2d) {
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(5, 10, 25),
                0, getHeight(), new Color(15, 25, 50)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.setColor(new Color(255, 255, 255, 150));
            java.util.Random starRandom = new java.util.Random(42);
            for (int i = 0; i < 50; i++) {
                int sx = starRandom.nextInt(getWidth());
                int sy = starRandom.nextInt(getHeight());
                g2d.fillRect(sx, sy, 2, 2);
            }
        }
        
        private void drawGameElements(Graphics2D g2d) {
            if (gameScene != null) {
                gameScene.render(g2d);
            }
            
            for (Projectile projectile : projectiles) {
                if (projectile.draw()) {
                    drawProjectile(g2d, projectile);
                }
            }
        }
        
        private void drawProjectile(Graphics2D g2d, Projectile projectile) {
            int px = projectile.getXCoord();
            int py = projectile.getYCoord();
            
            g2d.setColor(new Color(255, 255, 200, 100));
            g2d.fillRect(px - 5, py - 14, 10, 28);
            
            g2d.setColor(new Color(255, 255, 100));
            g2d.fillRect(px - 3, py - 12, 6, 24);
        }
        
        private void drawPlayerShip(Graphics2D g2d) {
            g2d.setColor(new Color(0, 255, 255));
            int[] xPoints = {playerX, playerX - 30, playerX + 30};
            int[] yPoints = {playerY - 25, playerY + 20, playerY + 20};
            g2d.fillPolygon(xPoints, yPoints, 3);
            
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawPolygon(xPoints, yPoints, 3);
            
            g2d.setColor(new Color(255, 255, 100));
            g2d.fillOval(playerX - 8, playerY - 5, 16, 16);
            
            // Shield visual effect
            if (playerShip.hasShield()) {
                g2d.setColor(new Color(0, 150, 255, 100));
                g2d.fillOval(playerX - 35, playerY - 30, 70, 70);
                g2d.setColor(new Color(0, 200, 255));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(playerX - 35, playerY - 30, 70, 70);
            }
        }
        
        private void drawUI(Graphics2D g2d) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 28));
            g2d.drawString("SCORE: " + score, 20, 45);
            
            int aliensRemaining = alienFormation.countActiveComponents();
            g2d.drawString("ALIENS: " + aliensRemaining, 20, 80);
            
            // Show current decorator status
            g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
            g2d.setColor(new Color(255, 215, 0));
            String status = playerShip.getStatus();
            if (!status.equals("BasicShip")) {
                g2d.drawString("STATUS: " + status, 20, 115);
            }
            
            // Show actual stats
            g2d.setColor(new Color(150, 255, 150));
            g2d.drawString("Speed: " + playerShip.getSpeed() + 
                          " | FireRate: " + playerShip.getFireRate() + "ms" +
                          " | FirePower: " + playerShip.getFirePower(), 20, 140);
            
            // Next power-up indicator
            int nextPowerUp = lastPowerUpScore + 50;
            g2d.drawString("Next Power-Up: " + nextPowerUp + " pts", 20, 165);
        }
        
        private void drawPauseOverlay(Graphics2D g2d) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.setColor(new Color(255, 255, 100));
            g2d.setFont(new Font("Arial", Font.BOLD, 72));
            String pauseText = "PAUSED";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(pauseText);
            g2d.drawString(pauseText, (getWidth() - textWidth) / 2, getHeight() / 2);
        }
    }
}