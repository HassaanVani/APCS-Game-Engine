package engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LevelRegistry {
    private static final List<LevelEntry> entries = new ArrayList<>();
    
    public static void register(String name, Color doorColor, Supplier<GameLevel> factory) {
        entries.add(new LevelEntry(name, doorColor, factory));
    }
    
    public static void register(String name, Supplier<GameLevel> factory) {
        int hash = Math.abs(name.hashCode());
        Color autoColor = new Color(
            50 + (hash % 150),
            50 + ((hash / 150) % 150),
            50 + ((hash / 22500) % 150)
        );
        register(name, autoColor, factory);
    }
    
    public static List<LevelEntry> getAll() {
        return new ArrayList<>(entries);
    }
    
    public static int count() {
        return entries.size();
    }
    
    public static class LevelEntry {
        public final String name;
        public final Color doorColor;
        public final Supplier<GameLevel> factory;
        
        public LevelEntry(String name, Color doorColor, Supplier<GameLevel> factory) {
            this.name = name;
            this.doorColor = doorColor;
            this.factory = factory;
        }
        
        public GameLevel create() {
            return factory.get();
        }
    }
}
