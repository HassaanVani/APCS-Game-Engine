package levels;

import engine.LevelRegistry;
import java.awt.Color;

public class Levels {
    
    public static void registerAll() {
        // ====== EXAMPLE LEVELS (Delete or keep as demos) ======
        LevelRegistry.register("Forest", new Color(34, 139, 34), ForestLevel::new);
        LevelRegistry.register("Cave", new Color(80, 80, 80), CaveLevel::new);
        LevelRegistry.register("Large Forest", new Color(34, 200, 34), LargeForestLevel::new);
        
        // ====== STUDENT LEVELS ======
        // Add your level here! Copy the line below and change the values:
        // LevelRegistry.register("YourName's Level", new Color(R, G, B), YourName_Level::new);
        
        LevelRegistry.register("Jupvir's Cavern", new Color(200, 100, 255), JuppyCavernLevel::new);
        
        // More examples:
        // LevelRegistry.register("Ice Cave", new Color(150, 200, 255), IceCaveLevel::new);
        // LevelRegistry.register("Volcano", new Color(255, 100, 50), VolcanoLevel::new);
        
        // TIP: If you don't pick a color, one is auto-generated from the name:
        // LevelRegistry.register("My Level", MyLevel::new);
    }
}
