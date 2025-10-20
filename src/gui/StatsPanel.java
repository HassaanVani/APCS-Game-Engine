package gui;

import javax.swing.*;
import java.awt.*;
import core.Player;

/**
 * Side panel showing player stats
 */
public class StatsPanel extends JPanel {
    private JLabel nameLabel;
    private JProgressBar healthBar;
    private JLabel healthLabel;
    private JLabel levelLabel;
    private JLabel expLabel;
    private JLabel attackLabel;
    private JLabel defenseLabel;
    private JLabel goldLabel;
    private JTextArea inventoryArea;
    
    public StatsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 0));
        setBackground(new Color(40, 40, 50));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel statsContainer = new JPanel();
        statsContainer.setLayout(new BoxLayout(statsContainer, BoxLayout.Y_AXIS));
        statsContainer.setBackground(new Color(40, 40, 50));
        
        // Title
        JLabel titleLabel = new JLabel("PLAYER STATS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsContainer.add(titleLabel);
        statsContainer.add(Box.createVerticalStrut(20));
        
        // Name
        nameLabel = createStatsLabel("Hero", 16, true);
        statsContainer.add(nameLabel);
        statsContainer.add(Box.createVerticalStrut(15));
        
        // Health bar
        healthBar = new JProgressBar(0, 100);
        healthBar.setValue(100);
        healthBar.setStringPainted(true);
        healthBar.setForeground(new Color(220, 60, 60));
        healthBar.setBackground(new Color(60, 60, 70));
        healthBar.setMaximumSize(new Dimension(230, 25));
        statsContainer.add(healthBar);
        
        healthLabel = createStatsLabel("HP: 100/100", 12, false);
        statsContainer.add(healthLabel);
        statsContainer.add(Box.createVerticalStrut(10));
        
        // Stats
        levelLabel = createStatsLabel("Level: 1", 14, false);
        statsContainer.add(levelLabel);
        
        expLabel = createStatsLabel("EXP: 0/100", 12, false);
        statsContainer.add(expLabel);
        statsContainer.add(Box.createVerticalStrut(10));
        
        attackLabel = createStatsLabel("⚔ Attack: 15", 14, false);
        statsContainer.add(attackLabel);
        
        defenseLabel = createStatsLabel("🛡 Defense: 5", 14, false);
        statsContainer.add(defenseLabel);
        
        goldLabel = createStatsLabel("💰 Gold: 0", 14, false);
        statsContainer.add(goldLabel);
        
        statsContainer.add(Box.createVerticalStrut(20));
        
        // Inventory
        JLabel invLabel = new JLabel("INVENTORY");
        invLabel.setFont(new Font("Arial", Font.BOLD, 14));
        invLabel.setForeground(new Color(200, 200, 255));
        invLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsContainer.add(invLabel);
        statsContainer.add(Box.createVerticalStrut(5));
        
        inventoryArea = new JTextArea(8, 20);
        inventoryArea.setEditable(false);
        inventoryArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        inventoryArea.setBackground(new Color(30, 30, 40));
        inventoryArea.setForeground(new Color(200, 200, 200));
        inventoryArea.setText("Empty");
        JScrollPane invScroll = new JScrollPane(inventoryArea);
        invScroll.setMaximumSize(new Dimension(230, 150));
        statsContainer.add(invScroll);
        
        add(statsContainer, BorderLayout.NORTH);
    }
    
    private JLabel createStatsLabel(String text, int fontSize, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(new Color(220, 220, 220));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    /**
     * Update the display with current player stats
     */
    public void updateStats(Player player) {
        if (player == null) return;
        
        nameLabel.setText(player.getName());
        
        int healthPercent = (int)((double)player.getHealth() / player.getMaxHealth() * 100);
        healthBar.setValue(healthPercent);
        healthBar.setString(player.getHealth() + "/" + player.getMaxHealth());
        healthLabel.setText("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        
        levelLabel.setText("Level: " + player.getLevel());
        expLabel.setText("EXP: " + player.getExperience() + "/" + (player.getLevel() * 100));
        attackLabel.setText("⚔ Attack: " + player.getAttackPower());
        defenseLabel.setText("🛡 Defense: " + player.getDefense());
        goldLabel.setText("💰 Gold: " + player.getGold());
        
        // Update inventory
        if (player.getInventory().isEmpty()) {
            inventoryArea.setText("Empty");
        } else {
            StringBuilder inv = new StringBuilder();
            for (int i = 0; i < player.getInventory().size(); i++) {
                inv.append((i + 1)).append(". ")
                   .append(player.getInventory().get(i).getName())
                   .append("\n");
            }
            inventoryArea.setText(inv.toString());
        }
    }
}
