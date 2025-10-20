package core;

import gui.GUICallback;

/**
 * Abstract Level class for GUI version
 * Each student creates their own level by extending this class
 * The game engine can run any level that extends this class
 */
public abstract class LevelGUI {
    protected String levelName;
    protected String description;
    protected boolean completed;
    protected GUICallback gui;
    
    public LevelGUI(String levelName, String description) {
        this.levelName = levelName;
        this.description = description;
        this.completed = false;
    }
    
    /**
     * Set the GUI callback for this level
     */
    public void setGUICallback(GUICallback gui) {
        this.gui = gui;
    }
    
    /**
     * Start the level - students implement their level's logic here
     * This is where polymorphism shines - each level has different implementation
     * but the game engine calls this method the same way for all levels
     */
    public abstract void start(Player player);
    
    /**
     * Check if the level has been completed
     */
    public boolean isCompleted() {
        return completed;
    }
    
    /**
     * Mark level as completed
     */
    protected void complete() {
        this.completed = true;
        gui.displaySeparator();
        gui.displayText("*** LEVEL COMPLETE: " + levelName + " ***");
        gui.displaySeparator();
    }
    
    /**
     * Display level introduction
     */
    public void displayIntro() {
        gui.displaySeparator();
        gui.displayText("LEVEL: " + levelName);
        gui.displaySeparator();
        gui.displayText(description);
        gui.displaySeparator();
    }
    
    // Getters
    public String getLevelName() { return levelName; }
    public String getDescription() { return description; }
}
