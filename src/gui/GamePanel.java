package gui;

import javax.swing.*;
import java.awt.*;
import core.*;

/**
 * Main game display panel - shows story text, combat, choices
 */
public class GamePanel extends JPanel {
    private JTextArea storyTextArea;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private GameEngineGUI engine;
    
    public GamePanel(GameEngineGUI engine) {
        this.engine = engine;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(30, 30, 40));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Story/text display area
        storyTextArea = new JTextArea();
        storyTextArea.setEditable(false);
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true);
        storyTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        storyTextArea.setBackground(new Color(20, 20, 30));
        storyTextArea.setForeground(new Color(220, 220, 220));
        storyTextArea.setCaretColor(Color.WHITE);
        storyTextArea.setMargin(new Insets(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(storyTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 120), 2));
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel for choices
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 40));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Display text in the story area
     */
    public void displayText(String text) {
        storyTextArea.append(text + "\n");
        storyTextArea.setCaretPosition(storyTextArea.getDocument().getLength());
    }
    
    /**
     * Clear all text
     */
    public void clearText() {
        storyTextArea.setText("");
    }
    
    /**
     * Clear all buttons
     */
    public void clearButtons() {
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    /**
     * Add a choice button
     */
    public JButton addButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(60, 100, 140));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(80, 120, 160));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 100, 140));
            }
        });
        
        buttonPanel.add(button);
        buttonPanel.revalidate();
        buttonPanel.repaint();
        
        return button;
    }
    
    /**
     * Show a separator line
     */
    public void displaySeparator() {
        displayText("\n" + "=".repeat(60) + "\n");
    }
}
