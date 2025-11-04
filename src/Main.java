import core.GameEngine;
import examples.*;

/**
 * Main class - Entry point for the game
 * 
 * This demonstrates POLYMORPHISM - the engine treats all levels the same
 * through the Level interface, even though each level is unique!
 */
public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        
        engine.addLevel(new ForestLevel(engine.getScanner(), engine.getCombatSystem()));
        engine.addLevel(new CaveLevel(engine.getScanner(), engine.getCombatSystem()));
        engine.start();
    }
}
