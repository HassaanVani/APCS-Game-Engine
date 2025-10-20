package core;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main game engine that manages levels and game flow
 * This class demonstrates polymorphism by treating all Level objects the same
 * regardless of their specific implementation
 */
public class GameEngine {
    private Player player;
    private ArrayList<Level> levels;
    private int currentLevelIndex;
    private Scanner scanner;
    private CombatSystem combatSystem;
    
    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.combatSystem = new CombatSystem(scanner);
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
    }
    
    /**
     * Add a level to the game
     * POLYMORPHISM: Can accept any object that extends Level
     */
    public void addLevel(Level level) {
        levels.add(level);
    }
    
    /**
     * Start the game
     */
    public void start() {
        displayWelcome();
        createPlayer();
        
        // POLYMORPHISM IN ACTION: 
        // Each level can be a different class, but we treat them all the same
        while (currentLevelIndex < levels.size() && player.isAlive()) {
            Level currentLevel = levels.get(currentLevelIndex);
            
            System.out.println("\n\nProgress: Level " + (currentLevelIndex + 1) + " of " + levels.size());
            player.displayStatus();
            
            currentLevel.displayIntro();
            currentLevel.start(player); // Polymorphism - different implementation for each level
            
            if (currentLevel.isCompleted()) {
                currentLevelIndex++;
                
                if (currentLevelIndex < levels.size()) {
                    System.out.println("\nPreparing next level...");
                    waitForEnter();
                }
            } else {
                System.out.println("\nLevel failed. Game Over.");
                break;
            }
        }
        
        if (currentLevelIndex >= levels.size() && player.isAlive()) {
            displayVictory();
        }
        
        scanner.close();
    }
    
    /**
     * Display welcome message
     */
    private void displayWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("   WELCOME TO THE TURN-BASED RPG ENGINE");
        System.out.println("   A Polymorphism Learning Platform");
        System.out.println("=".repeat(60));
        System.out.println();
    }
    
    /**
     * Create the player character
     */
    private void createPlayer() {
        System.out.print("Enter your hero's name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Hero";
        }
        player = new Player(name);
        System.out.println("\nWelcome, " + name + "!");
        player.displayStatus();
    }
    
    /**
     * Display victory message
     */
    private void displayVictory() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   *** CONGRATULATIONS! ***");
        System.out.println("   You have completed all levels!");
        System.out.println("=".repeat(60));
        System.out.println("\nFinal Stats:");
        player.displayStatus();
        System.out.println("Level: " + player.getLevel());
        System.out.println("Gold: " + player.getGold());
    }
    
    /**
     * Wait for user to press enter
     */
    private void waitForEnter() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
    
    // Getters
    public Player getPlayer() { return player; }
    public Scanner getScanner() { return scanner; }
    public CombatSystem getCombatSystem() { return combatSystem; }
}
