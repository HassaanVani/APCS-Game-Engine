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
    private String currentDialogue = "";
    
    public enum GameState {
        PLAYING,
        BATTLE,
        PAUSED,
        MENU,
        DIALOGUE
    }
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        
        keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        
        setupGame();
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameState == GameState.BATTLE) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int W = getWidth() > 0 ? getWidth() : SCREEN_WIDTH;
                    int H = getHeight() > 0 ? getHeight() : SCREEN_HEIGHT;
                    double scaleX = (double) W / SCREEN_WIDTH;
                    double scaleY = (double) H / SCREEN_HEIGHT;
                    int battleX = (int) (mouseX / scaleX);
                    int battleY = (int) (mouseY / scaleY);
                    
                    battleSystem.handleMouseClick(battleX, battleY);
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                if (gameState == GameState.BATTLE) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int W = getWidth() > 0 ? getWidth() : SCREEN_WIDTH;
                    int H = getHeight() > 0 ? getHeight() : SCREEN_HEIGHT;
                    double scaleX = (double) W / SCREEN_WIDTH;
                    double scaleY = (double) H / SCREEN_HEIGHT;
                    int battleX = (int) (mouseX / scaleX);
                    int battleY = (int) (mouseY / scaleY);
                    
                    battleSystem.handleMouseMove(battleX, battleY);
                }
            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
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
        
        // Play level specific BGM
        if (level.getBGMFilename() != null) {
            SoundManager.playBGM(level.getBGMFilename());
        }
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
    
    private int shootCooldown = 0;
    
    private void update() {
        if (gameState == GameState.PLAYING) {
            if (shootCooldown > 0) shootCooldown--;
            player.update();
            
            // Check for overworld sword attacks
            if (keyHandler.cPressed && player.hasSword() && player.getSwordSwingActiveFrames() == 0) {
                player.startSwordSwing();
                SoundManager.playSE("swing.wav");
                
                int range = TILE_SIZE;
                int hitX = player.getWorldX();
                int hitY = player.getWorldY();
                switch (player.direction) {
                    case UP:    hitY -= range; break;
                    case DOWN:  hitY += range; break;
                    case LEFT:  hitX -= range; break;
                    case RIGHT: hitX += range; break;
                }
                
                Rectangle swordBox = new Rectangle(hitX + 8, hitY + 8, 32, 32);
                if (currentLevel != null) {
                    // Check hits on overworld or hybrid enemies
                    for (int i = 0; i < currentLevel.getEnemies().size(); i++) {
                        Enemy enemy = currentLevel.getEnemies().get(i);
                        if (!enemy.isDefeated() && 
                            (enemy.getEncounterType() == Enemy.EncounterType.OVERWORLD_ACTION || 
                             enemy.getEncounterType() == Enemy.EncounterType.HYBRID)) {
                            if (enemy.getCollisionBox().intersects(swordBox)) {
                                enemy.takeDamage(player.getAttackPower());
                                SoundManager.playSE("hit.wav");
                                if (!enemy.isAlive()) {
                                    currentLevel.onEnemyDefeated(enemy);
                                }
                            }
                        }
                    }
                }
                keyHandler.cPressed = false;
            }
            
            // Check for overworld bow attacks
            if (keyHandler.xPressed && player.hasBow() && player.getArrows() > 0 && shootCooldown == 0) {
                shootCooldown = 30; // 30 frames cooldown (0.5s)
                player.addArrows(-1);
                SoundManager.playSE("shoot.wav");
                
                int pX = player.getWorldX() + (TILE_SIZE - 8) / 2;
                int pY = player.getWorldY() + (TILE_SIZE - 8) / 2;
                Projectile proj = new Projectile(pX, pY, player.direction, player.getAttackPower());
                if (currentLevel != null) {
                    currentLevel.addProjectile(proj);
                }
                keyHandler.xPressed = false;
            }
            
            // Update camera to follow player
            updateCamera();
            
            if (currentLevel != null) {
                currentLevel.update();
                currentLevel.checkCollisions(player);

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
            
            // Handle overworld interaction (SPACE or ENTER)
            if (keyHandler.spacePressed || keyHandler.enterPressed) {
                checkInteraction();
                keyHandler.spacePressed = false;
                keyHandler.enterPressed = false;
            }
            
            // ESC key returns to hub
            if (keyHandler.escapePressed && currentLevel != hubLevel) {
                returnToHub();
                keyHandler.escapePressed = false;
            }
        } else if (gameState == GameState.DIALOGUE) {
            if (keyHandler.spacePressed || keyHandler.enterPressed) {
                gameState = GameState.PLAYING;
                keyHandler.spacePressed = false;
                keyHandler.enterPressed = false;
            }
        } else if (gameState == GameState.BATTLE) {
            battleSystem.update();
        }
    }
    
    private void checkInteraction() {
        if (currentLevel == null) return;
        
        int targetX = player.getWorldX();
        int targetY = player.getWorldY();
        int reach = TILE_SIZE;
        
        switch (player.direction) {
            case UP:    targetY -= reach; break;
            case DOWN:  targetY += reach; break;
            case LEFT:  targetX -= reach; break;
            case RIGHT: targetX += reach; break;
        }
        
        Rectangle interactArea = new Rectangle(targetX + 8, targetY + 8, 32, 32);
        for (Interactable obj : currentLevel.interactables) {
            if (obj.getCollisionBox().intersects(interactArea)) {
                obj.onInteract(player, this);
                break;
            }
        }
    }
    
    public void showDialogue(String text) {
        this.currentDialogue = text;
        this.gameState = GameState.DIALOGUE;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int W = getWidth() > 0 ? getWidth() : SCREEN_WIDTH;
        int H = getHeight() > 0 ? getHeight() : SCREEN_HEIGHT;
        
        if (gameState == GameState.PLAYING || gameState == GameState.DIALOGUE) {
            double scale = getRenderScale();
            g2.scale(scale, scale);
            
            // Apply camera translation
            g2.translate(-cameraX, -cameraY);
            
            // Draw level
            if (currentLevel != null) {
                currentLevel.render(g2);
            }
            
            // Draw player
            player.render(g2);
            
            // Draw sword swing effect
            if (player.getSwordSwingActiveFrames() > 0) {
                g2.setColor(new Color(255, 255, 255, 180));
                g2.setStroke(new BasicStroke(3));
                int swingX = player.getWorldX();
                int swingY = player.getWorldY();
                
                switch (player.direction) {
                    case UP:
                        g2.drawArc(swingX - 8, swingY - 16, TILE_SIZE + 16, TILE_SIZE, 30, 120);
                        break;
                    case DOWN:
                        g2.drawArc(swingX - 8, swingY + 16, TILE_SIZE + 16, TILE_SIZE, 210, 120);
                        break;
                    case LEFT:
                        g2.drawArc(swingX - 16, swingY - 8, TILE_SIZE, TILE_SIZE + 16, 120, 120);
                        break;
                    case RIGHT:
                        g2.drawArc(swingX + 16, swingY - 8, TILE_SIZE, TILE_SIZE + 16, 300, 120);
                        break;
                }
            }
            
            // Reset translation for UI
            g2.translate(cameraX, cameraY);
            
            // Draw UI (health bar, etc)
            drawUI(g2);
            
            // Draw dialogue box if in dialogue state
            if (gameState == GameState.DIALOGUE) {
                drawDialogueBox(g2);
            }
        } else if (gameState == GameState.BATTLE) {
            double scaleX = (double) W / SCREEN_WIDTH;
            double scaleY = (double) H / SCREEN_HEIGHT;
            g2.scale(scaleX, scaleY);
            
            battleSystem.render(g2);
        }
        
        g2.dispose();
    }
    
    private void drawDialogueBox(Graphics2D g2) {
        int viewportW = getViewportWidth();
        int viewportH = getViewportHeight();
        
        int boxX = TILE_SIZE * 2;
        int boxY = viewportH - TILE_SIZE * 3 - 20;
        int boxWidth = viewportW - TILE_SIZE * 4;
        int boxHeight = TILE_SIZE * 2 + 10;
        
        // Translucent dark box background
        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);
        
        // White border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
        
        // Text rendering
        g2.setFont(new Font("Consolas", Font.PLAIN, 16));
        g2.setColor(Color.WHITE);
        
        int textX = boxX + 20;
        int textY = boxY + 35;
        java.util.List<String> lines = wrapText(currentDialogue, g2.getFontMetrics(), boxWidth - 40);
        for (String line : lines) {
            g2.drawString(line, textX, textY);
            textY += 22;
        }
        
        // Advance hint
        g2.setFont(new Font("Consolas", Font.ITALIC, 11));
        g2.drawString("Press SPACE/ENTER to continue", boxX + boxWidth - 215, boxY + boxHeight - 12);
    }
    
    /**
     * Utility method to wrap text into multiple lines given font metrics and max width in pixels.
     */
    public static java.util.List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
        java.util.List<String> lines = new java.util.ArrayList<>();
        if (text == null) return lines;
        String[] paragraphs = text.split("\n", -1);
        for (String paragraph : paragraphs) {
            if (paragraph.isEmpty()) {
                lines.add("");
                continue;
            }
            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();
            for (String word : words) {
                String testLine = currentLine.length() == 0 ? word : currentLine.toString() + " " + word;
                int width = fm.stringWidth(testLine);
                if (width > maxWidth) {
                    if (currentLine.length() > 0) {
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder(word);
                    } else {
                        lines.add(word);
                    }
                } else {
                    currentLine.append(currentLine.length() == 0 ? "" : " ").append(word);
                }
            }
            if (currentLine.length() > 0) {
                lines.add(currentLine.toString());
            }
        }
        return lines;
    }
    
    /**
     * Update camera position to follow player (Pokemon-style screen transitions)
     * Camera snaps to screen boundaries
     */
    public int getViewportWidth() {
        if (currentLevel == null) return SCREEN_WIDTH;
        int W = getWidth() > 0 ? getWidth() : SCREEN_WIDTH;
        int H = getHeight() > 0 ? getHeight() : SCREEN_HEIGHT;
        int mapW = currentLevel.getMapWidth() * TILE_SIZE;
        int mapH = currentLevel.getMapHeight() * TILE_SIZE;
        double scale = Math.max(1.0, Math.max((double) W / mapW, (double) H / mapH));
        return (int) (W / scale);
    }
    
    public int getViewportHeight() {
        if (currentLevel == null) return SCREEN_HEIGHT;
        int W = getWidth() > 0 ? getWidth() : SCREEN_WIDTH;
        int H = getHeight() > 0 ? getHeight() : SCREEN_HEIGHT;
        int mapW = currentLevel.getMapWidth() * TILE_SIZE;
        int mapH = currentLevel.getMapHeight() * TILE_SIZE;
        double scale = Math.max(1.0, Math.max((double) W / mapW, (double) H / mapH));
        return (int) (H / scale);
    }
    
    public double getRenderScale() {
        if (currentLevel == null) return 1.0;
        int W = getWidth() > 0 ? getWidth() : SCREEN_WIDTH;
        int H = getHeight() > 0 ? getHeight() : SCREEN_HEIGHT;
        int mapW = currentLevel.getMapWidth() * TILE_SIZE;
        int mapH = currentLevel.getMapHeight() * TILE_SIZE;
        return Math.max(1.0, Math.max((double) W / mapW, (double) H / mapH));
    }
    
    private void updateCamera() {
        if (currentLevel == null) return;
        
        int playerCenterX = player.getWorldX() + TILE_SIZE / 2;
        int playerCenterY = player.getWorldY() + TILE_SIZE / 2;
        
        int viewportW = getViewportWidth();
        int viewportH = getViewportHeight();
        
        // Calculate which screen the player is on
        int screenX = playerCenterX / viewportW;
        int screenY = playerCenterY / viewportH;
        
        // Snap camera to that screen
        int targetCameraX = screenX * viewportW;
        int targetCameraY = screenY * viewportH;
        
        // Clamp camera to level bounds
        int maxCameraX = Math.max(0, currentLevel.getMapWidth() * TILE_SIZE - viewportW);
        int maxCameraY = Math.max(0, currentLevel.getMapHeight() * TILE_SIZE - viewportH);
        
        cameraX = Math.max(0, Math.min(targetCameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(targetCameraY, maxCameraY));
    }
    
    private void drawUI(Graphics2D g2) {
        // Draw player health bar
        int barX = 20;
        int barY = 20;
        int barWidth = 200;
        int barHeight = 16;
        
        // Health Background
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(barX - 2, barY - 2, barWidth + 4, barHeight + 4);
        // Health bar
        g2.setColor(Color.RED);
        int healthWidth = (int) ((player.getHealth() / (double) player.getMaxHealth()) * barWidth);
        g2.fillRect(barX, barY, healthWidth, barHeight);
        // Health Border
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, barY, barWidth, barHeight);
        // Health Text
        g2.setFont(new Font("Arial", Font.BOLD, 11));
        g2.setColor(Color.WHITE);
        g2.drawString("HP: " + player.getHealth() + " / " + player.getMaxHealth(), barX + 5, barY + 12);
        
        // Draw player mana bar
        int manaY = 40;
        // Mana Background
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(barX - 2, manaY - 2, barWidth + 4, barHeight + 4);
        // Mana bar
        g2.setColor(new Color(50, 150, 255));
        int manaWidth = (int) ((player.getMana() / (double) player.getMaxMana()) * barWidth);
        g2.fillRect(barX, manaY, manaWidth, barHeight);
        // Mana Border
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, manaY, barWidth, barHeight);
        // Mana Text
        g2.setColor(Color.WHITE);
        g2.drawString("MP: " + player.getMana() + " / " + player.getMaxMana(), barX + 5, manaY + 12);
        
        // Draw weapons & ammo UI
        if (player.hasSword() || player.hasBow()) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(barX - 2, 60 - 2, 130, 22);
            g2.setColor(Color.WHITE);
            g2.drawRect(barX, 60, 126, 18);
            
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            StringBuilder weaponText = new StringBuilder();
            if (player.hasSword()) weaponText.append("Sword(C) ");
            if (player.hasBow()) weaponText.append("Bow(X) x").append(player.getArrows());
            g2.drawString(weaponText.toString().trim(), barX + 5, 72);
        }
        
        // Draw Gold HUD in top right
        int goldX = getViewportWidth() - 120;
        int goldY = 20;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(goldX - 2, goldY - 2, 104, 24);
        g2.setColor(Color.WHITE);
        g2.drawRect(goldX, goldY, 100, 20);
        
        // Gold Coin Icon
        g2.setColor(Color.YELLOW);
        g2.fillOval(goldX + 8, goldY + 4, 12, 12);
        g2.setColor(Color.ORANGE);
        g2.drawOval(goldX + 8, goldY + 4, 12, 12);
        
        // Gold Text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        g2.drawString(player.getGold() + " G", goldX + 28, goldY + 14);
    }
    
    public void startBattle(Enemy enemy) {
        SoundManager.playSE("encounter.wav");
        SoundManager.playBGM("battle.wav");
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
        
        // Restore level specific BGM
        if (currentLevel != null && currentLevel.getBGMFilename() != null) {
            SoundManager.playBGM(currentLevel.getBGMFilename());
        }
    }
    
    // Getters
    public Player getPlayer() { return player; }
    public KeyHandler getKeyHandler() { return keyHandler; }
    public GameState getGameState() { return gameState; }
    public void setGameState(GameState state) { this.gameState = state; }
}
