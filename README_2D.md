# APCS 2D RPG Engine 🎮

A **2D top-down RPG game engine** (like Zelda/Pokemon) built in Java to teach **polymorphism** through collaborative level design.

## 🎮 What Is This?

This is a **real 2D game engine** where:
- Players move with **WASD/Arrow keys** in a top-down view
- **Tile-based maps** with collision detection
- Walk into enemies to trigger **Pokemon-style turn-based battles**
- Each student creates **ONE level** with their own map and enemies
- **Polymorphism** connects all levels seamlessly

## 🚀 Quick Start

### Run the Game NOW

```batch
compile-2d.bat
run-2d.bat
```

**Controls:**
- `WASD` or `Arrow Keys` - Move player
- `SPACE` - Confirm in battle
- `Left/Right Arrows` - Select battle action (Fight/Run)

Walk into enemies to start battles!

## 🎨 Features

### Top-Down Exploration
- **Tile-based maps** (16x12 tiles, 48x48 pixels each)
- **Smooth 60 FPS movement**
- **Collision detection** (walls, water, obstacles)
- **Enemy encounters** (walk into them to battle)

### Pokemon-Style Battles
- **Turn-based combat** with health bars
- **Player actions:** Fight or Run
- **Enemy AI** - each enemy has unique behavior
- **EXP and leveling** system
- **Visual battle screen** with sprites

### Customization
- **Custom tile maps** - design your level layout
- **Custom sprites** - create or import images
- **Custom enemies** - unique behavior and stats
- **Custom tile colors** - theme your level

## 📁 Project Structure

```
src/
├── engine/              # Core game engine
│   ├── GamePanel.java   # Main game loop & rendering
│   ├── KeyHandler.java  # Keyboard input
│   ├── Player.java      # Player character
│   ├── Entity.java      # Base class for all entities
│   ├── Enemy.java       # Abstract enemy class ⭐
│   ├── GameLevel.java   # Abstract level class ⭐⭐
│   └── BattleSystem.java # Turn-based combat
│
├── levels/              # Student levels go here
│   ├── ForestLevel.java # Example level 1
│   └── CaveLevel.java   # Example level 2
│
├── enemies/             # Student enemies go here
│   ├── Slime.java       # Example enemy
│   └── Bat.java         # Example enemy
│
└── Main2D.java          # Entry point
```

## 🎓 How Polymorphism Works

### The Core Concept

```java
// In Main2D.java:
GameLevel level1 = new ForestLevel();    // Student 1's level
GameLevel level2 = new CaveLevel();      // Student 2's level
GameLevel level3 = new YourLevel();      // Your level!

gamePanel.setLevel(level1);  // Engine works the same for ALL levels!
```

**Polymorphism in Action:**
- All levels extend `GameLevel`
- Engine calls `setupMap()` and `setupEnemies()` the same way
- Each level has completely different implementation
- No changes needed to the engine!

## 📝 Creating Your Level

### Step 1: Copy the Template

```
Copy STUDENT_TEMPLATE_2D.java to src/levels/YourName_Level.java
```

### Step 2: Design Your Map

```java
@Override
public void setupMap() {
    // Fill with grass
    fillRect(0, 0, mapWidth, mapHeight, 0);
    
    // Create walls
    for (int x = 0; x < mapWidth; x++) {
        setTile(x, 0, 1);  // Top wall
        setTile(x, mapHeight - 1, 1);  // Bottom wall
    }
    
    // Add obstacles
    setTile(5, 5, 1);
    setTile(6, 5, 1);
}
```

### Step 3: Place Enemies

```java
@Override
public void setupEnemies() {
    Slime slime = new Slime();
    slime.setPosition(200, 200);
    addEnemy(slime);
}
```

### Step 4: Define Tile Colors

```java
@Override
protected Color getTileColor(int tileType) {
    switch (tileType) {
        case 0: return new Color(34, 139, 34);   // Grass
        case 1: return new Color(139, 69, 19);   // Wall
        case 2: return new Color(70, 130, 180);  // Water
        default: return Color.GRAY;
    }
}
```

## 🎨 Creating Custom Enemies

```java
public class Dragon extends Enemy {
    public Dragon() {
        super("Dragon", 100, 25, 10, 200, 100);
        createCustomSprite();
    }
    
    private void createCustomSprite() {
        sprite = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, 
                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = sprite.createGraphics();
        
        // Draw your sprite!
        g2.setColor(Color.RED);
        g2.fillOval(8, 8, 32, 32);
        
        g2.dispose();
    }
    
    @Override
    public String performBattleAction(Player player) {
        int damage = attack();
        player.takeDamage(damage);
        return name + " breathes fire for " + damage + " damage!";
    }
    
    @Override
    public String getDescription() {
        return "A fearsome dragon!";
    }
}
```

## 🖼️ Using Custom Sprites

### Option 1: Draw with Code (Simple)
```java
sprite = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
Graphics2D g2 = sprite.createGraphics();
g2.setColor(Color.GREEN);
g2.fillRect(8, 8, 32, 32);
g2.dispose();
```

### Option 2: Load Image Files (Advanced)
```java
import javax.imageio.ImageIO;
import java.io.File;

try {
    sprite = ImageIO.read(new File("sprites/player.png"));
} catch (Exception e) {
    e.printStackTrace();
}
```

**Image Requirements:**
- Size: 48x48 pixels recommended
- Format: PNG with transparency
- Place in `sprites/` folder

## 🎮 Game Systems

### Tile Types

| Type | Default Use | Walkable | Color |
|------|-------------|----------|-------|
| 0 | Grass/Floor | ✅ Yes | Green |
| 1 | Wall/Rock | ❌ No | Brown |
| 2 | Water | ❌ No | Blue |
| 3 | Path | ✅ Yes | Tan |
| 4+ | Custom | You decide | You define |

### Player Stats
- **Health:** Shown in top-left corner
- **Attack/Defense:** Affect battle damage
- **Level:** Increases with EXP
- **Gold:** Earned from defeating enemies

### Battle System
- **Turn-based:** Player acts, then enemy
- **Actions:** Fight (attack) or Run (50% chance)
- **Damage:** `damage = attack - defense` (min 1)
- **Victory:** Gain EXP and Gold

## 🎯 Level Design Tips

### Map Layout
1. **Always create border walls** - prevents player from escaping
2. **Leave open space** - don't make it too crowded
3. **Create pathways** - guide player through level
4. **Add variety** - mix walkable and blocked tiles
5. **Theme it** - use colors that match your theme

### Enemy Placement
1. **Not at spawn** - don't place enemies where player starts
2. **Space them out** - give player room to navigate
3. **2-5 enemies** per level is good
4. **Consider difficulty** - start easier, get harder

### Testing Checklist
- [ ] Player can spawn and move
- [ ] Can't walk through walls
- [ ] Enemies appear on map
- [ ] Battles trigger when touching enemies
- [ ] Can defeat all enemies
- [ ] Level is completable

## 🔧 API Reference

### GameLevel Methods

```java
// Map building
void setTile(int x, int y, int tileType)
void fillRect(int x, int y, int width, int height, int tileType)

// Enemy management  
void addEnemy(Enemy enemy)

// Override these
void setupMap()
void setupEnemies()
Color getTileColor(int tileType)
boolean isTileSolid(int tileX, int tileY)
```

### Enemy Methods

```java
// Constructor
Enemy(String name, int health, int attack, int defense, int exp, int gold)

// Override these
String performBattleAction(Player player)
String getDescription()

// Set position
void setPosition(int x, int y)
```

### Player Methods

```java
void takeDamage(int damage)
void heal(int amount)
void gainExperience(int exp)
void addGold(int amount)
int getHealth()
int getMaxHealth()
```

## 🎨 Example: Complete Custom Level

```java
package levels;

import engine.*;
import enemies.*;
import java.awt.*;

public class VolcanoLevel extends GameLevel {
    public VolcanoLevel() {
        super("Volcano Zone", 16, 12);
        startX = 100;
        startY = 500;
    }
    
    @Override
    public void setupMap() {
        // Lava floor
        fillRect(0, 0, mapWidth, mapHeight, 4);
        
        // Safe rock platforms
        fillRect(2, 10, 4, 2, 0);
        fillRect(8, 8, 3, 3, 0);
        fillRect(12, 5, 3, 4, 0);
        
        // Border walls
        for (int x = 0; x < mapWidth; x++) {
            setTile(x, 0, 1);
            setTile(x, 11, 1);
        }
        for (int y = 0; y < mapHeight; y++) {
            setTile(0, y, 1);
            setTile(15, y, 1);
        }
    }
    
    @Override
    public void setupEnemies() {
        FireSpirit spirit = new FireSpirit();
        spirit.setPosition(400, 350);
        addEnemy(spirit);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(80, 80, 80);    // Rock
            case 1: return new Color(40, 40, 40);    // Wall
            case 4: return new Color(255, 100, 0);   // Lava
            default: return Color.BLACK;
        }
    }
    
    @Override
    public boolean isTileSolid(int tileX, int tileY) {
        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return true;
        }
        int type = tileMap[tileX][tileY];
        return type == 1 || type == 4; // Walls and lava block
    }
}
```

## 🔄 Adding Your Level to the Game

In `src/Main2D.java`:

```java
// Change this line:
GameLevel level1 = new ForestLevel();

// To your level:
GameLevel level1 = new YourName_Level();
```

Or add level switching:

```java
gamePanel.setLevel(new ForestLevel());
// Later...
gamePanel.setLevel(new YourLevel());
```

## 🎯 What Students Learn

### Core Concepts
✅ **Polymorphism** - Different levels, same engine
✅ **Inheritance** - Extending GameLevel and Enemy
✅ **Abstract classes** - GameLevel, Enemy, Entity
✅ **2D graphics** - Rendering, sprites, colors
✅ **Game loops** - Update and render cycles
✅ **Collision detection** - Tile-based and entity-based
✅ **Event handling** - Keyboard input

### Skills Developed
✅ Object-oriented design
✅ 2D game development
✅ Creative problem solving
✅ Testing and debugging
✅ Collaborative programming

## 📚 Resources

### Example Files
- `src/levels/ForestLevel.java` - Simple grass level
- `src/levels/CaveLevel.java` - Cave theme
- `src/enemies/Slime.java` - Basic enemy
- `src/enemies/Bat.java` - Enemy with special ability

### Documentation
- `STUDENT_TEMPLATE_2D.java` - Full template with comments
- This README - Complete reference
- Code comments - Explained throughout

## 🐛 Troubleshooting

**Player can't move:**
- Check border walls aren't blocking spawn point
- Verify `isTileSolid()` is correct

**Enemies don't appear:**
- Call `addEnemy()` in `setupEnemies()`
- Check enemy position is on screen (0-768, 0-576)

**Battles don't start:**
- Make sure enemy has collision box
- Check player actually intersects enemy

**Graphics look wrong:**
- Verify sprite size (48x48 recommended)
- Check tile colors are defined

---

**Created for AP Computer Science courses**
**A real 2D game engine teaching real polymorphism!** 🎮🚀
