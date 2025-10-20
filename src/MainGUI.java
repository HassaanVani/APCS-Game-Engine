import core.*;
import examples.*;

/**
 * Main class for GUI version - Entry point for the game
 * 
 * INSTRUCTIONS FOR TEACHERS:
 * 1. Each student creates their own level by extending the LevelGUI class
 * 2. Add student levels to the game engine using engine.addLevel()
 * 3. The engine will run all levels in sequence with a Swing GUI
 * 
 * This demonstrates POLYMORPHISM - the engine treats all levels the same
 * through the LevelGUI interface, even though each student's level is unique!
 */
public class MainGUI {
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create the game engine
        GameEngineGUI engine = new GameEngineGUI();
        
        // POLYMORPHISM IN ACTION:
        // Each level is a different class, but we add them the same way
        // The engine doesn't need to know the specific type of each level
        
        // Add example levels (students will create their own!)
        engine.addLevel(new ForestLevelGUI(engine.getCombatSystem()));
        engine.addLevel(new CaveLevelGUI(engine.getCombatSystem()));
        
        // TO ADD STUDENT LEVELS:
        // engine.addLevel(new StudentName_LevelGUI(engine.getCombatSystem()));
        // engine.addLevel(new AnotherStudent_LevelGUI(engine.getCombatSystem()));
        
        // Start the game - the engine will run through all levels with GUI
        engine.start();
    }
}
