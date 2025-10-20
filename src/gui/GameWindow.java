package gui;

import javax.swing.*;
import java.awt.*;
import core.*;

/**
 * Main game window - displays the entire game interface
 */
public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    private StatsPanel statsPanel;
    private GameEngineGUI engine;
    
    public GameWindow(GameEngineGUI engine) {
        this.engine = engine;
        setupWindow();
        initializeComponents();
    }
    
    private void setupWindow() {
        setTitle("APCS Turn-Based RPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Main game panel (center)
        gamePanel = new GamePanel(engine);
        add(gamePanel, BorderLayout.CENTER);
        
        // Stats panel (right side)
        statsPanel = new StatsPanel();
        add(statsPanel, BorderLayout.EAST);
        
        // Set background color
        getContentPane().setBackground(new Color(40, 40, 50));
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public StatsPanel getStatsPanel() {
        return statsPanel;
    }
    
    public void updateStats(Player player) {
        statsPanel.updateStats(player);
    }
}
