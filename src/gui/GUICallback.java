package gui;

import javax.swing.JButton;

/**
 * Interface for GUI interaction with levels
 * Levels use these methods to display content and get user input
 */
public interface GUICallback {
    /**
     * Display text to the player
     */
    void displayText(String text);
    
    /**
     * Clear all text from display
     */
    void clearText();
    
    /**
     * Display a separator line
     */
    void displaySeparator();
    
    /**
     * Wait for player to click a button with given text
     * Returns when button is clicked
     */
    void waitForButton(String buttonText, Runnable callback);
    
    /**
     * Present multiple choice options and wait for selection
     * Returns the index of the selected choice (0-based)
     */
    void showChoices(String[] choices, ChoiceCallback callback);
    
    /**
     * Update player stats display
     */
    void updateStats();
    
    /**
     * Clear all buttons
     */
    void clearButtons();
}
