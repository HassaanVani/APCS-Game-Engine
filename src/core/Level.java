package core;

/**
 * Abstract Level class - THE KEY POLYMORPHISM CONCEPT
 * Each student creates their own level by extending this class
 * The game engine can run any level that extends this class
 */
public abstract class Level {
    protected String levelName;
    protected String description;
    protected boolean completed;
    
    public Level(String levelName, String description) {
        this.levelName = levelName;
        this.description = description;
        this.completed = false;
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
        System.out.println("\n*** LEVEL COMPLETE: " + levelName + " ***\n");
    }
    
    /**
     * Display level introduction
     */
    public void displayIntro() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("LEVEL: " + levelName);
        System.out.println("=".repeat(50));
        System.out.println(description);
        System.out.println("=".repeat(50) + "\n");
    }
    
    // Getters
    public String getLevelName() { return levelName; }
    public String getDescription() { return description; }
}
