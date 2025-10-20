import core.GameEngine;
import examples.*;

/**
 * Main class - Entry point for the game
 * 
 * INSTRUCTIONS FOR TEACHERS:
 * 1. Each student creates their own level by extending the Level class
 * 2. Add student levels to the game engine using engine.addLevel()
 * 3. The engine will run all levels in sequence
 * 
 * This demonstrates POLYMORPHISM - the engine treats all levels the same
 * through the Level interface, even though each student's level is unique!
 */
public class Main {
    public static void main(String[] args) {
        // Create the game engine
        GameEngine engine = new GameEngine();
        
        // POLYMORPHISM IN ACTION:
        // Each level is a different class, but we add them the same way
        // The engine doesn't need to know the specific type of each level
        
        // Add example levels (students will create their own!)
        engine.addLevel(new ForestLevel(engine.getScanner(), engine.getCombatSystem()));
        engine.addLevel(new CaveLevel(engine.getScanner(), engine.getCombatSystem()));
        
        // TO ADD STUDENT LEVELS:
        // engine.addLevel(new StudentName_Level(engine.getScanner(), engine.getCombatSystem()));
        // engine.addLevel(new AnotherStudent_Level(engine.getScanner(), engine.getCombatSystem()));
        
        // Start the game - the engine will run through all levels
        engine.start();
    }
}
