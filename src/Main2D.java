import engine.*;
import levels.*;
import javax.swing.JFrame;

/**
 * Main class for 2D RPG Engine
 * 
 * POLYMORPHISM DEMONSTRATION:
 * Different student levels all extend GameLevel
 * The engine treats them all the same way!
 */
public class Main2D {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("APCS 2D RPG Engine - Polymorphism Demo");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        // POLYMORPHISM IN ACTION!
        // Each student creates their own level that extends GameLevel
        // The hub connects them all together!
        
        // Create the hub (central corridor)
        GameLevel hub = new HubLevel();
        gamePanel.setHubLevel(hub);
        
        // Register all student levels by name
        // STUDENTS: Add your level here!
        gamePanel.registerLevel("Forest", new ForestLevel());
        gamePanel.registerLevel("Cave", new CaveLevel());
        // gamePanel.registerLevel("Student 1", new StudentName_Level());
        // gamePanel.registerLevel("Student 2", new AnotherStudent_Level());
        
        // Start in the hub
        gamePanel.setLevel(hub);
        gamePanel.startGameThread();
        
        System.out.println("=".repeat(60));
        System.out.println("APCS 2D RPG ENGINE - NOW RUNNING");
        System.out.println("=".repeat(60));
        System.out.println("Controls:");
        System.out.println("  WASD or Arrow Keys - Move");
        System.out.println("  SPACE - Confirm action in battle");
        System.out.println("  Left/Right Arrows - Select battle action");
        System.out.println("  ESC - Return to hub from any level");
        System.out.println();
        System.out.println("Walk into DOORS to enter levels!");
        System.out.println("Walk into ENEMIES to battle them!");
        System.out.println("=".repeat(60));
    }
}
