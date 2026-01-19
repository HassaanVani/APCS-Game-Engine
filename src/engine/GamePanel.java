package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Main game panel - handles rendering and game loop
 * 2D top-down game engine (like Zelda/Pokemon)
 */
public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    public static final int TILE_SIZE = 48; // 48x48 pixel tiles
    public static final int SCREEN_COL = 16;
    public static final int SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COL;  // 768 pixels
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROW; // 576 pixels
    
    // Camera system for multi-screen levels
    private int cameraX = 0;
    private int cameraY = 0;
    
    // Game loop
    private Thread gameThread;
    private final int FPS = 60;
    
    // Game components
    private KeyHandler keyHandler;
    private Player player;
    private GameLevel currentLevel;
    private BattleSystem battleSystem;
    private GameState gameState = GameState.PLAYING;
    
    // Level management
    private java.util.HashMap<String, GameLevel> levels = new java.util.HashMap<>();
    private GameLevel hubLevel;
    
    public enum GameState {
        PLAYING,
        BATTLE,
        PAUSED,
        MENU
    }
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        
        setupGame();
    }
    
    private void setupGame() {
        player = new Player(keyHandler);
        battleSystem = new BattleSystem(this);
    }
    
    public void setLevel(GameLevel level) {
        this.currentLevel = level;
        level.initialize(this);
        player.setPosition(level.getStartX(), level.getStartY());
        
        // Reset camera to starting position
        updateCamera();
    }
    
    /**
     * Register a level by name so we can switch to it
     */
    public void registerLevel(String name, GameLevel level) {
        levels.put(name, level);
    }
    
    /**
     * Set the hub level
     */
    public void setHubLevel(GameLevel hub) {
        this.hubLevel = hub;
    }
    
    /**
     * Switch to a level by name
     */
    public void switchToLevel(String levelName) {
        GameLevel level = levels.get(levelName);
        if (level != null) {
            setLevel(level);
        }
    }
    
    /**
     * Return to hub
     */
    public void returnToHub() {
        if (hubLevel != null) {
            setLevel(hubLevel);
        }
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
    
    private void update() {
        if (gameState == GameState.PLAYING) {
            player.update();
            
            // Update camera to follow player
            updateCamera();
            
            // Check collisions with level
            if (currentLevel != null) {
                currentLevel.checkCollisions(player);
                
                // Check for door transitions (if we're in hub)
                if (currentLevel instanceof levels.HubLevel) {
                    levels.HubLevel hub = (levels.HubLevel) currentLevel;
                    Door door = hub.checkDoorCollision(player);
                    if (door != null) {
                        switchToLevel(door.getTargetLevelName());
                    }
                }
                
                // Check for enemy encounters
                Enemy enemy = currentLevel.checkEnemyEncounter(player);
                if (enemy != null) {
                    startBattle(enemy);
                }
            }
            
            // ESC key returns to hub
            if (keyHandler.escapePressed && currentLevel != hubLevel) {
                returnToHub();
                keyHandler.escapePressed = false;
            }
        } else if (gameState == GameState.BATTLE) {
            battleSystem.update();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if (gameState == GameState.PLAYING) {
            // Apply camera translation
            g2.translate(-cameraX, -cameraY);
            
            // Draw level
            if (currentLevel != null) {
                currentLevel.render(g2);
            }
            
            // Draw player
            player.render(g2);
            
            // Reset translation for UI
            g2.translate(cameraX, cameraY);
            
            // Draw UI (health bar, etc)
            drawUI(g2);
        } else if (gameState == GameState.BATTLE) {
            battleSystem.render(g2);
        }
        
        g2.dispose();
    }
    
    /**
     * Update camera position to follow player (Pokemon-style screen transitions)
     * Camera snaps to screen boundaries
     */
    private void updateCamera() {
        if (currentLevel == null) return;
        
        int playerCenterX = player.getWorldX() + TILE_SIZE / 2;
        int playerCenterY = player.getWorldY() + TILE_SIZE / 2;
        
        // Calculate which screen the player is on
        int screenX = playerCenterX / SCREEN_WIDTH;
        int screenY = playerCenterY / SCREEN_HEIGHT;
        
        // Snap camera to that screen
        int targetCameraX = screenX * SCREEN_WIDTH;
        int targetCameraY = screenY * SCREEN_HEIGHT;
        
        // Clamp camera to level bounds
        int maxCameraX = Math.max(0, currentLevel.getMapWidth() * TILE_SIZE - SCREEN_WIDTH);
        int maxCameraY = Math.max(0, currentLevel.getMapHeight() * TILE_SIZE - SCREEN_HEIGHT);
        
        cameraX = Math.max(0, Math.min(targetCameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(targetCameraY, maxCameraY));
    }
    
    private void drawUI(Graphics2D g2) {
        // Draw player health bar
        int barX = 20;
        int barY = 20;
        int barWidth = 200;
        int barHeight = 20;
        
        // Background
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(barX - 2, barY - 2, barWidth + 4, barHeight + 4);
        
        // Health bar
        g2.setColor(Color.RED);
        int healthWidth = (int) ((player.getHealth() / (double) player.getMaxHealth()) * barWidth);
        g2.fillRect(barX, barY, healthWidth, barHeight);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, barY, barWidth, barHeight);
        
        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        String healthText = player.getHealth() + " / " + player.getMaxHealth();
        g2.drawString(healthText, barX + 5, barY + 15);
    }
    
    public void startBattle(Enemy enemy) {
        gameState = GameState.BATTLE;
        battleSystem.startBattle(player, enemy);
    }
    
    public void endBattle(boolean playerWon) {
        gameState = GameState.PLAYING;
        if (playerWon && currentLevel != null) {
            currentLevel.onEnemyDefeated(battleSystem.getCurrentEnemy());
        } else{
            player.setPosition(player.getWorldX()-GamePanel.TILE_SIZE, player.getWorldY());
        }
    }
    
    // Getters
    public Player getPlayer() { return player; }
    public KeyHandler getKeyHandler() { return keyHandler; }
    public GameState getGameState() { return gameState; }
    public void setGameState(GameState state) { this.gameState = state; }
}
