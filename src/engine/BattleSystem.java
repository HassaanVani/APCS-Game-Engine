package engine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Pokemon-style turn-based battle system
 */
public class BattleSystem {
    private GamePanel gamePanel;
    private Player player;
    private Enemy enemy;
    private BattleState state = BattleState.INTRO;
    private String message = "";
    private int messageTimer = 0;
    private int selectedAction = 0; // 0=Fight, 1=Run
    
    private enum BattleState {
        INTRO,
        PLAYER_TURN,
        ENEMY_TURN,
        VICTORY,
        DEFEAT
    }
    
    public BattleSystem(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void startBattle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.state = BattleState.INTRO;
        this.message = "A wild " + enemy.getName() + " appeared!";
        this.messageTimer = 60;
        this.selectedAction = 0;
    }
    
    public void update() {
        if (messageTimer > 0) {
            messageTimer--;
            return;
        }
        
        KeyHandler keys = gamePanel.getKeyHandler();
        
        switch (state) {
            case INTRO:
                if (keys.spacePressed || keys.enterPressed) {
                    state = BattleState.PLAYER_TURN;
                    message = "What will you do?";
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
                
            case PLAYER_TURN:
                // Navigate menu
                if (keys.leftPressed) {
                    selectedAction = 0;
                    keys.leftPressed = false;
                } else if (keys.rightPressed) {
                    selectedAction = 1;
                    keys.rightPressed = false;
                }
                
                // Select action
                if (keys.spacePressed || keys.enterPressed) {
                    if (selectedAction == 0) {
                        // Fight
                        playerAttack();
                    } else {
                        // Run (50% chance)
                        if (Math.random() < 0.5) {
                            message = "Got away safely!";
                            messageTimer = 60;
                            gamePanel.endBattle(false);
                        } else {
                            message = "Can't escape!";
                            messageTimer = 40;
                            state = BattleState.ENEMY_TURN;
                        }
                    }
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
                
            case ENEMY_TURN:
                enemyAttack();
                break;
                
            case VICTORY:
                if (keys.spacePressed || keys.enterPressed) {
                    gamePanel.endBattle(true);
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
                
            case DEFEAT:
                if (keys.spacePressed || keys.enterPressed) {
                    gamePanel.endBattle(false);
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                }
                break;
        }
    }
    
    private void playerAttack() {
        int damage = player.attack();
        enemy.takeDamage(damage);
        message = "You dealt " + damage + " damage!";
        messageTimer = 60;
        
        if (!enemy.isAlive()) {
            state = BattleState.VICTORY;
            message = enemy.getName() + " defeated! Gained " + enemy.getExpReward() + " EXP!";
        } else {
            state = BattleState.ENEMY_TURN;
        }
    }
    
    private void enemyAttack() {
        String action = enemy.performBattleAction(player);
        message = action;
        messageTimer = 60;
        
        if (!player.isAlive()) {
            state = BattleState.DEFEAT;
            message = "You were defeated!";
        } else {
            state = BattleState.PLAYER_TURN;
        }
    }
    
    public void render(Graphics2D g2) {
        // Draw battle background
        g2.setColor(new Color(40, 40, 60));
        g2.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        
        // Draw enemy sprite (larger)
        if (enemy != null && enemy.getSprite() != null) {
            int enemyX = GamePanel.SCREEN_WIDTH - 200;
            int enemyY = 100;
            g2.drawImage(enemy.getSprite(), enemyX, enemyY, 
                        GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 2, null);
            
            // Enemy health bar
            drawHealthBar(g2, enemy, enemyX, enemyY - 30);
        }
        
        // Draw player sprite
        if (player != null && player.getSprite() != null) {
            int playerX = 100;
            int playerY = GamePanel.SCREEN_HEIGHT - 250;
            g2.drawImage(player.getSprite(), playerX, playerY, 
                        GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 2, null);
            
            // Player health bar
            drawHealthBar(g2, player, playerX, playerY - 30);
        }
        
        // Draw message box
        drawMessageBox(g2);
        
        // Draw action menu if player's turn
        if (state == BattleState.PLAYER_TURN) {
            drawActionMenu(g2);
        }
    }
    
    private void drawHealthBar(Graphics2D g2, Entity entity, int x, int y) {
        int barWidth = 150;
        int barHeight = 20;
        
        // Name
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString(entity.getName(), x, y - 5);
        
        // Background
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x, y, barWidth, barHeight);
        
        // Health
        g2.setColor(Color.GREEN);
        int healthWidth = (int) ((entity.getHealth() / (double) entity.getMaxHealth()) * barWidth);
        g2.fillRect(x, y, healthWidth, barHeight);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, barWidth, barHeight);
        
        // Text
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        String hpText = entity.getHealth() + "/" + entity.getMaxHealth();
        g2.drawString(hpText, x + 5, y + 15);
    }
    
    private void drawMessageBox(Graphics2D g2) {
        int boxX = 50;
        int boxY = GamePanel.SCREEN_HEIGHT - 150;
        int boxWidth = GamePanel.SCREEN_WIDTH - 100;
        int boxHeight = 100;
        
        // Box background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
        
        // Message text
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.drawString(message, boxX + 20, boxY + 40);
        
        // "Press SPACE to continue" hint
        if (state == BattleState.INTRO || state == BattleState.VICTORY || state == BattleState.DEFEAT) {
            g2.setFont(new Font("Arial", Font.ITALIC, 12));
            g2.drawString("Press SPACE to continue", boxX + 20, boxY + 80);
        }
    }
    
    private void drawActionMenu(Graphics2D g2) {
        int menuX = GamePanel.SCREEN_WIDTH / 2 - 150;
        int menuY = GamePanel.SCREEN_HEIGHT - 150;
        int buttonWidth = 120;
        int buttonHeight = 50;
        int spacing = 20;
        
        // Fight button
        drawButton(g2, "FIGHT", menuX, menuY, buttonWidth, buttonHeight, selectedAction == 0);
        
        // Run button
        drawButton(g2, "RUN", menuX + buttonWidth + spacing, menuY, buttonWidth, buttonHeight, selectedAction == 1);
    }
    
    private void drawButton(Graphics2D g2, String text, int x, int y, int width, int height, boolean selected) {
        // Background
        if (selected) {
            g2.setColor(new Color(100, 150, 255));
        } else {
            g2.setColor(new Color(60, 60, 80));
        }
        g2.fillRect(x, y, width, height);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, width, height);
        
        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height + fm.getAscent()) / 2 - 2;
        g2.drawString(text, textX, textY);
    }
    
    public Enemy getCurrentEnemy() {
        return enemy;
    }
}
