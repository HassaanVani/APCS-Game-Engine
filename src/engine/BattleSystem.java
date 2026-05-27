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
    private int selectedAction = 0; // 0=Fight, 1=Item, 2=Run
    private int selectedItemIndex = 0;
    
    private enum BattleState {
        INTRO,
        PLAYER_TURN,
        ITEM_SELECT,
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
                // Navigate menu (FIGHT, ITEM, RUN)
                if (keys.leftPressed) {
                    selectedAction = (selectedAction - 1 + 3) % 3;
                    keys.leftPressed = false;
                } else if (keys.rightPressed) {
                    selectedAction = (selectedAction + 1) % 3;
                    keys.rightPressed = false;
                }
                
                // Select action
                if (keys.spacePressed || keys.enterPressed) {
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                    
                    if (selectedAction == 0) {
                        // Fight
                        playerAttack();
                    } else if (selectedAction == 1) {
                        // Items
                        if (player.getInventory().isEmpty()) {
                            message = "No items in inventory!";
                            messageTimer = 45;
                        } else {
                            state = BattleState.ITEM_SELECT;
                            selectedItemIndex = 0;
                        }
                    } else {
                        // Run
                        if (Math.random() < enemy.getRunChance()) {
                            SoundManager.playSE("run.wav");
                            message = "Got away safely!";
                            messageTimer = 60;
                            gamePanel.endBattle(false);
                        } else {
                            SoundManager.playSE("hit.wav");
                            message = "Can't escape!";
                            messageTimer = 40;
                            state = BattleState.ENEMY_TURN;
                        }
                    }
                }
                break;
                
            case ITEM_SELECT:
                int itemCount = player.getInventory().size();
                
                // Scroll inventory
                if (keys.upPressed) {
                    selectedItemIndex = (selectedItemIndex - 1 + itemCount) % itemCount;
                    keys.upPressed = false;
                } else if (keys.downPressed) {
                    selectedItemIndex = (selectedItemIndex + 1) % itemCount;
                    keys.downPressed = false;
                }
                
                // Cancel
                if (keys.escapePressed) {
                    state = BattleState.PLAYER_TURN;
                    message = "What will you do?";
                    keys.escapePressed = false;
                }
                
                // Confirm Use
                if (keys.spacePressed || keys.enterPressed) {
                    keys.spacePressed = false;
                    keys.enterPressed = false;
                    
                    if (itemCount > 0) {
                        Item item = player.getInventory().get(selectedItemIndex);
                        item.use(player);
                        player.removeItem(item);
                        
                        // Play heal or stat SE
                        if (item.getType() == Item.ItemType.POTION) {
                            SoundManager.playSE("heal.wav");
                        } else {
                            SoundManager.playSE("buff.wav");
                        }
                        
                        message = "Used " + item.getName() + "!";
                        messageTimer = 60;
                        state = BattleState.ENEMY_TURN;
                    } else {
                        state = BattleState.PLAYER_TURN;
                    }
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
        SoundManager.playSE("hit.wav");
        message = "You dealt " + damage + " damage!";
        messageTimer = 60;
        
        if (!enemy.isAlive()) {
            state = BattleState.VICTORY;
            SoundManager.playSE("victory.wav");
            message = enemy.getName() + " defeated! Gained " + enemy.getExpReward() + " EXP!";
        } else {
            state = BattleState.ENEMY_TURN;
        }
    }
    
    private void enemyAttack() {
        String action = enemy.performBattleAction(player);
        SoundManager.playSE("hit.wav");
        message = action;
        messageTimer = 60;
        
        if (!player.isAlive()) {
            state = BattleState.DEFEAT;
            SoundManager.playSE("defeat.wav");
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
        
        // Draw inventory menu if selecting item
        if (state == BattleState.ITEM_SELECT) {
            drawInventoryMenu(g2);
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
        g2.setFont(new Font("Consolas", Font.PLAIN, 18));
        g2.drawString(message, boxX + 20, boxY + 40);
        
        // "Press SPACE to continue" hint
        if (state == BattleState.INTRO || state == BattleState.VICTORY || state == BattleState.DEFEAT) {
            g2.setFont(new Font("Consolas", Font.ITALIC, 12));
            g2.drawString("Press SPACE to continue", boxX + 20, boxY + 80);
        }
    }
    
    private void drawActionMenu(Graphics2D g2) {
        int menuX = GamePanel.SCREEN_WIDTH / 2 - 120;
        int menuY = GamePanel.SCREEN_HEIGHT - 125;
        int buttonWidth = 110;
        int buttonHeight = 50;
        int spacing = 15;
        
        // Fight button
        drawButton(g2, "FIGHT", menuX, menuY, buttonWidth, buttonHeight, selectedAction == 0);
        
        // Item button
        drawButton(g2, "ITEM", menuX + buttonWidth + spacing, menuY, buttonWidth, buttonHeight, selectedAction == 1);
        
        // Run button
        drawButton(g2, "RUN", menuX + (buttonWidth + spacing) * 2, menuY, buttonWidth, buttonHeight, selectedAction == 2);
    }
    
    private void drawInventoryMenu(Graphics2D g2) {
        int boxX = 50;
        int boxY = GamePanel.SCREEN_HEIGHT - 280;
        int boxWidth = 300;
        int boxHeight = 120;
        
        // Dark translucent inventory background
        g2.setColor(new Color(0, 0, 0, 220));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);
        
        // White border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
        
        g2.setFont(new Font("Consolas", Font.BOLD, 14));
        g2.drawString("INVENTORY (ESC to back):", boxX + 15, boxY + 25);
        
        java.util.ArrayList<Item> inv = player.getInventory();
        int itemCount = inv.size();
        
        int startIdx = Math.max(0, selectedItemIndex - 2);
        int endIdx = Math.min(itemCount, startIdx + 3);
        
        int itemY = boxY + 50;
        for (int i = startIdx; i < endIdx; i++) {
            Item item = inv.get(i);
            if (i == selectedItemIndex) {
                g2.setColor(new Color(100, 150, 255));
                g2.drawString("> " + item.getName(), boxX + 20, itemY);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString("  " + item.getName(), boxX + 20, itemY);
            }
            itemY += 20;
        }
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
