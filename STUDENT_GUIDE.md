# Student Guide - APCS 2D RPG Game Engine

## Quick Start Guide

### Creating Your First Level

1. **Copy the template**: Use `EASY_LEVEL_TEMPLATE.java`
2. **Rename the file**: Change to `YourName_Level.java`
3. **Rename the class**: Change `YourName_Level` to match your filename
4. **Customize**: Follow the comments in the template

### Creating Your First Enemy

1. **Copy the template**: Use `EASY_ENEMY_TEMPLATE.java`
2. **Rename the file**: Change to `YourName_Enemy.java`
3. **Rename the class**: Change `YourName_Enemy` to match your filename
4. **Customize**: Adjust stats and behavior

---

## Level Building Made Easy

### The LevelBuilder Helper

Every level has a `builder` object that makes creating maps super easy!

#### Basic Setup
```java
@Override
public void setupMap() {
    // Step 1: Fill background
    builder.fillBackground(0);  // 0 = grass
    
    // Step 2: Create border
    builder.createBorder(1);  // 1 = wall
    
    // Step 3: Add your design!
}
```

### LevelBuilder Methods

#### `fillBackground(tileType)`
Fill the entire map with one tile type.
```java
builder.fillBackground(0);  // Fill with grass
```

#### `createBorder(wallType)`
Create walls around the edge of the map.
```java
builder.createBorder(1);  // Create wall border
```

#### `createRoom(x, y, width, height, floorType, wallType)`
Create a rectangular room.
```java
builder.createRoom(3, 3, 6, 5, 0, 1);
// Creates a 6x5 room at position (3,3)
// Floor is type 0, walls are type 1
```

#### `createHorizontalCorridor(startX, endX, y, pathType)`
Create a horizontal path.
```java
builder.createHorizontalCorridor(2, 10, 6, 3);
// Path from x=2 to x=10 at y=6
```

#### `createVerticalCorridor(x, startY, endY, pathType)`
Create a vertical path.
```java
builder.createVerticalCorridor(8, 2, 8, 3);
// Path from y=2 to y=8 at x=8
```

#### `createDoor(x, y, floorType)`
Create an opening in a wall.
```java
builder.createDoor(8, 4, 0);
// Creates walkable tile at (8,4)
```

#### `createCircle(centerX, centerY, radius, tileType)`
Create a circular area (great for ponds, arenas).
```java
builder.createCircle(8, 6, 2, 2);
// Creates a circle of water (type 2) with radius 2
```

#### `createScatteredTiles(x, y, width, height, tileType, density)`
Randomly place tiles (great for trees, rocks).
```java
builder.createScatteredTiles(2, 2, 12, 8, 1, 0.15);
// Scatter walls in area with 15% density
```

#### `createCheckerboard(x, y, width, height, type1, type2)`
Create a checkerboard pattern.
```java
builder.createCheckerboard(3, 3, 8, 6, 0, 3);
// Alternating grass and path tiles
```

#### `createMaze(x, y, width, height, wallType, pathType)`
Create a simple maze.
```java
builder.createMaze(2, 2, 12, 8, 1, 0);
// Creates a maze with walls and paths
```

#### `createDiagonalLine(startX, startY, endX, endY, tileType)`
Create a diagonal line.
```java
builder.createDiagonalLine(2, 2, 14, 10, 3);
// Diagonal path from (2,2) to (14,10)
```

---

## Working with Sprites

### Three Ways to Add Sprites

#### 1. Colored Shapes (Easiest - No Files!)
```java
// For enemies (in constructor)
setCustomSprite(Color.RED, "circle");
setCustomSprite(Color.BLUE, "triangle");
setCustomSprite(Color.GREEN, "diamond");
setCustomSprite(Color.YELLOW, "square");

// For player
player.setCustomSprite(Color.CYAN, "circle");
```

#### 2. Image with Fallback (Recommended)
```java
// For enemies
setSpriteOrFallback("enemies/slime.png", Color.GREEN);
// Tries to load image, uses green circle if not found

// For tiles
setTileSpriteOrColor(0, "tiles/grass.png", Color.GREEN);
```

#### 3. Image Only
```java
// For enemies
setCustomSprite("enemies/myenemy.png");

// For tiles
setTileSprite(0, "tiles/grass.png");

// For player
player.setCustomSprite("players/hero.png");
```

### Tile Sprites in Levels

```java
@Override
public void setupMap() {
    // Option 1: Use image files
    setTileSprite(0, "tiles/grass.png");
    setTileSprite(1, "tiles/wall.png");
    
    // Option 2: Use with fallback colors
    setTileSpriteOrColor(0, "tiles/grass.png", Color.GREEN);
    setTileSpriteOrColor(1, "tiles/wall.png", Color.BROWN);
    
    // Option 3: Create programmatically
    setTileSprite(0, SpriteManager.createTileSprite(Color.GREEN, "grass"));
    setTileSprite(1, SpriteManager.createTileSprite(Color.BROWN, "brick"));
    
    // Then build your map
    builder.fillBackground(0);
    builder.createBorder(1);
}
```

### SpriteManager Methods

```java
// Create colored sprite
BufferedImage sprite = SpriteManager.createColoredSprite(Color.RED);

// Create shape sprite
BufferedImage circle = SpriteManager.createShapeSprite(Color.BLUE, "circle");

// Create tile with pattern
BufferedImage grass = SpriteManager.createTileSprite(Color.GREEN, "grass");
BufferedImage brick = SpriteManager.createTileSprite(Color.BROWN, "brick");
BufferedImage water = SpriteManager.createTileSprite(Color.BLUE, "water");
BufferedImage stone = SpriteManager.createTileSprite(Color.GRAY, "stone");

// Load from file
BufferedImage loaded = SpriteManager.loadSprite("enemies/slime.png");

// Load with fallback
BufferedImage safe = SpriteManager.getSpriteOrFallback("player.png", Color.BLUE);
```

---

## Tile Types Reference

### Standard Tiles
- **0** = Grass/Floor (walkable)
- **1** = Wall (blocks movement)
- **2** = Water (blocks movement)
- **3** = Path (walkable)
- **4+** = Your custom tiles!

### Defining Tile Colors
```java
@Override
protected Color getTileColor(int tileType) {
    switch (tileType) {
        case 0: return new Color(34, 139, 34);    // Green
        case 1: return new Color(139, 69, 19);    // Brown
        case 2: return new Color(70, 130, 180);   // Blue
        case 3: return new Color(210, 180, 140);  // Tan
        case 4: return new Color(255, 100, 0);    // Orange (lava)
        case 5: return new Color(200, 200, 255);  // Light blue (ice)
        default: return Color.GRAY;
    }
}
```

### Custom Collision
```java
@Override
public boolean isTileSolid(int tileX, int tileY) {
    if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
        return true; // Out of bounds
    }
    int tileType = tileMap[tileX][tileY];
    
    // Define which tiles block movement
    return tileType == 1 || tileType == 2 || tileType == 4;
    // Walls, water, and lava block movement
}
```

---

## Enemy Creation Guide

### Basic Enemy Structure
```java
public class MyEnemy extends Enemy {
    public MyEnemy() {
        super(
            "Enemy Name",  // Name
            50,            // Health
            12,            // Attack
            5,             // Defense
            30,            // EXP reward
            15             // Gold reward
        );
        
        // Set sprite
        setCustomSprite(Color.RED, "circle");
    }
    
    @Override
    public String performBattleAction(Player player) {
        int damage = attack();
        player.takeDamage(damage);
        return name + " attacks for " + damage + " damage!";
    }
    
    @Override
    public String getDescription() {
        return "A dangerous enemy!";
    }
}
```

### Battle Action Ideas

#### Simple Attack
```java
int damage = attack();
player.takeDamage(damage);
return name + " attacks for " + damage + " damage!";
```

#### Random Miss
```java
if (Math.random() < 0.2) {  // 20% miss chance
    return name + " missed!";
}
int damage = attack();
player.takeDamage(damage);
return name + " attacks for " + damage + " damage!";
```

#### Critical Hit
```java
if (Math.random() < 0.15) {  // 15% crit chance
    int crit = attack() * 3;
    player.takeDamage(crit);
    return name + " CRITICAL HIT for " + crit + " damage!";
}
int damage = attack();
player.takeDamage(damage);
return name + " attacks for " + damage + " damage!";
```

#### Special Ability
```java
if (Math.random() < 0.3) {  // 30% special chance
    int special = attack() * 2;
    player.takeDamage(special);
    return name + " uses POWER ATTACK for " + special + " damage!";
} else {
    int damage = attack();
    player.takeDamage(damage);
    return name + " attacks for " + damage + " damage!";
}
```

#### Health-Based Behavior
```java
if (health < maxHealth / 2) {
    // Desperate when low health
    int damage = attack() * 2;
    player.takeDamage(damage);
    return name + " desperate attack for " + damage + " damage!";
} else {
    int damage = attack();
    player.takeDamage(damage);
    return name + " attacks for " + damage + " damage!";
}
```

---

## Enemy Stats Guide

### Health (HP)
- **Weak**: 20-40
- **Normal**: 50-80
- **Strong**: 100-150
- **Boss**: 200+

### Attack Power
- **Weak**: 5-10
- **Normal**: 12-18
- **Strong**: 20-30
- **Boss**: 35+

### Defense
- **Weak**: 2-5
- **Normal**: 5-10
- **Strong**: 12-20
- **Boss**: 25+

### Experience Reward
- **Weak**: 10-20
- **Normal**: 25-50
- **Strong**: 60-100
- **Boss**: 150+

### Gold Reward
- **Weak**: 5-15
- **Normal**: 15-30
- **Strong**: 35-60
- **Boss**: 100+

---

## Complete Level Example

```java
package levels;

import engine.*;
import enemies.*;
import java.awt.*;

public class MyAwesomeLevel extends GameLevel {
    
    public MyAwesomeLevel() {
        super("My Awesome Level", 16, 12);
        startX = 100;
        startY = 100;
    }
    
    @Override
    public void setupMap() {
        // Background
        builder.fillBackground(0);
        
        // Border
        builder.createBorder(1);
        
        // Create a castle-like structure
        builder.createRoom(5, 3, 6, 6, 3, 1);  // Main room
        builder.createDoor(8, 3, 0);  // North entrance
        builder.createDoor(8, 8, 0);  // South entrance
        
        // Add a pond
        builder.createCircle(12, 9, 1, 2);
        
        // Scatter some trees
        builder.createScatteredTiles(1, 1, 4, 4, 1, 0.2);
    }
    
    @Override
    public void setupEnemies() {
        Slime slime1 = new Slime();
        slime1.setPosition(300, 300);
        addEnemy(slime1);
        
        Bat bat1 = new Bat();
        bat1.setPosition(500, 200);
        addEnemy(bat1);
    }
    
    @Override
    protected Color getTileColor(int tileType) {
        switch (tileType) {
            case 0: return new Color(34, 139, 34);
            case 1: return new Color(139, 69, 19);
            case 2: return new Color(70, 130, 180);
            case 3: return new Color(210, 180, 140);
            default: return Color.GRAY;
        }
    }
}
```

---

## Tips for Success

### Level Design
1. **Plan first**: Draw your map on graph paper (16x12 grid)
2. **Keep it simple**: Don't overcrowd with obstacles
3. **Test frequently**: Run your level often to check it works
4. **Leave space**: Players need room to move around
5. **Think about flow**: How will players navigate your level?

### Enemy Design
1. **Balance stats**: Don't make enemies too hard or too easy
2. **Variety**: Give each enemy unique behavior
3. **Placement**: Don't put enemies at spawn point
4. **Test battles**: Make sure combat is fun and fair
5. **Theme**: Match enemy to level theme

### Sprites
1. **Start simple**: Use colored shapes first
2. **Consistent size**: Keep sprites 48x48 pixels
3. **Test fallbacks**: Make sure colors look good
4. **Organize files**: Use the sprites/ folder structure
5. **Attribution**: Credit any downloaded sprites

---

## Common Issues & Solutions

### "Could not load sprite"
- Check filename spelling
- Make sure file is in sprites/ folder
- Use fallback colors: `setSpriteOrFallback()`

### Player gets stuck
- Check `isTileSolid()` method
- Make sure paths are walkable (type 0 or 3)
- Test collision boundaries

### Enemies too hard/easy
- Adjust stats in constructor
- Balance attack power and defense
- Test battles multiple times

### Map looks wrong
- Check tile coordinates (0-indexed)
- Verify mapWidth=16, mapHeight=12
- Use builder methods for complex shapes

---

## Next Steps

1. **Create your first level** using `EASY_LEVEL_TEMPLATE.java`
2. **Create your first enemy** using `EASY_ENEMY_TEMPLATE.java`
3. **Test your creations** in the game
4. **Iterate and improve** based on playtesting
5. **Add sprites** to make it look better
6. **Share with classmates** and get feedback!

---

## Resources

- **Templates**: `EASY_LEVEL_TEMPLATE.java`, `EASY_ENEMY_TEMPLATE.java`
- **Examples**: Check `ForestLevel.java`, `CaveLevel.java`
- **Sprites**: See `sprites/README.md`
- **Original Template**: `STUDENT_TEMPLATE_2D.java`

Happy coding! 🎮
