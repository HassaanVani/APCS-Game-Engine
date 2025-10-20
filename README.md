# APCS Turn-Based RPG Engine

A Java game engine designed to teach **polymorphism** through collaborative level design.

> **📌 NOTE:** This project has TWO versions - **Console** and **GUI (Swing)**
> - See `WHICH_VERSION.md` for guidance on which to use
> - This README covers the **Console version**
> - For GUI version, see `README_GUI.md`

## 🎮 Project Overview

This is a turn-based RPG framework where:
- **Each student designs ONE unique level** (extending the `Level` class)
- **The engine connects all levels** into a cohesive game experience
- **Polymorphism makes it work** - the engine treats all levels the same through abstract classes and interfaces

## 🎯 Learning Objectives

This project teaches:
1. **Polymorphism** - Different level implementations work with the same engine
2. **Inheritance** - Extending abstract classes (Level, Enemy, Item, Entity)
3. **Abstract Classes vs Interfaces** - Understanding when to use each
4. **Code Reusability** - Shared game mechanics across all levels
5. **Collaborative Programming** - Multiple students contributing to one project

## 📁 Project Structure

```
APCS-Game-Engine/
├── src/
│   ├── core/                   # Core engine (DO NOT MODIFY)
│   │   ├── Entity.java         # Abstract base for all characters
│   │   ├── Player.java         # Player character
│   │   ├── Enemy.java          # Abstract enemy class
│   │   ├── Item.java           # Abstract item class
│   │   ├── Level.java          # Abstract level class (KEY!)
│   │   ├── GameEngine.java     # Main game loop
│   │   └── CombatSystem.java   # Turn-based combat
│   │
│   ├── examples/               # Example implementations
│   │   ├── ForestLevel.java    # Example level 1
│   │   ├── CaveLevel.java      # Example level 2
│   │   ├── Goblin.java         # Example enemy
│   │   ├── Orc.java            # Example enemy
│   │   ├── HealthPotion.java   # Example item
│   │   └── StrengthPotion.java # Example item
│   │
│   └── Main.java               # Entry point
│
├── STUDENT_TEMPLATE.java       # Template for students
└── README.md                   # This file
```

## 🚀 Quick Start

### For Students

1. **Copy the template:**
   ```bash
   Copy STUDENT_TEMPLATE.java to src/YourName_Level.java
   ```

2. **Customize your level:**
   - Change class name to `YourName_Level`
   - Add your level name and description
   - Implement the `start(Player player)` method
   - Create custom enemies and items (optional)

3. **Test your level:**
   - Add your level to `Main.java`
   - Compile and run the game

### For Teachers

1. **Collect student levels:**
   - Students submit their `StudentName_Level.java` files
   - Place them in the `src/` directory

2. **Integrate levels:**
   - In `Main.java`, add each student level:
   ```java
   engine.addLevel(new Student1_Level(engine.getScanner(), engine.getCombatSystem()));
   engine.addLevel(new Student2_Level(engine.getScanner(), engine.getCombatSystem()));
   ```

3. **Run the complete game:**
   ```bash
   javac src/**/*.java
   java -cp src Main
   ```

## 🎓 How Polymorphism Works Here

### The Core Concept

```java
// In GameEngine.java:
public void addLevel(Level level) {
    levels.add(level);  // Accepts ANY class that extends Level
}

// In the game loop:
for (Level currentLevel : levels) {
    currentLevel.start(player);  // Polymorphism in action!
    // Different implementation for each student's level
}
```

### Key Polymorphic Relationships

1. **Level** (abstract) → `ForestLevel`, `CaveLevel`, `Student_Level`
   - Engine calls `start()` on any Level type
   - Each level has unique implementation

2. **Enemy** (abstract) → `Goblin`, `Orc`, `Student_Enemy`
   - All enemies have `takeTurn()` method
   - Each enemy has unique AI behavior

3. **Item** (abstract) → `HealthPotion`, `StrengthPotion`, `Student_Item`
   - All items have `use()` method
   - Each item has different effects

4. **Entity** (abstract) → `Player`, `Enemy`
   - Shared combat mechanics
   - Different roles in the game

## 📝 Student Assignment Guide

### Minimum Requirements

Your level MUST:
1. Extend the `Level` class
2. Implement the `start(Player player)` method
3. Call `complete()` when the level is finished
4. Handle player death (check `!player.isAlive()`)

### What You Can Do

- **Tell a story** with text and choices
- **Create custom enemies** by extending `Enemy`
- **Create custom items** by extending `Item`
- **Add combat encounters** using the `CombatSystem`
- **Give rewards** (items, gold, experience)
- **Create puzzles** and decision points
- **Set the mood** with descriptive text

### Example Level Structure

```java
@Override
public void start(Player player) {
    // 1. Introduction
    System.out.println("Welcome to my level!");
    
    // 2. Story/Exploration
    System.out.println("You enter a mysterious place...");
    
    // 3. Combat Encounter
    MyEnemy enemy = new MyEnemy();
    if (!combat.startCombat(player, enemy)) {
        return;  // Player died
    }
    
    // 4. Rewards
    player.addItem(new MyItem());
    player.addGold(50);
    
    // 5. Complete
    complete();
}
```

## 🎨 Creative Ideas for Levels

- **The Haunted Castle** - Ghosts and skeletons
- **The Desert Ruins** - Ancient guardians
- **The Ice Caverns** - Frozen enemies
- **The Volcano** - Fire creatures
- **The Underwater Temple** - Sea monsters
- **The Sky Islands** - Flying enemies
- **The Dark Dungeon** - Classic dungeon crawl
- **The Enchanted Garden** - Magical creatures

## 🔧 Compilation and Running

### Compile
```bash
javac src/**/*.java
```

### Run
```bash
java -cp src Main
```

### For Windows (if wildcards don't work)
```powershell
javac src/core/*.java src/examples/*.java src/Main.java
java -cp src Main
```

## 📚 API Reference

### Core Classes

#### Level (Abstract)
```java
public abstract class Level {
    public abstract void start(Player player);
    protected void complete();
    public boolean isCompleted();
}
```

#### Enemy (Abstract)
```java
public abstract class Enemy extends Entity {
    public abstract void takeTurn(Player player);
    public abstract String getDescription();
    public void onDefeat(Player player);
}
```

#### Item (Abstract)
```java
public abstract class Item {
    public abstract void use(Player player);
}
```

#### Player Methods
```java
player.displayStatus();
player.addItem(Item item);
player.addGold(int amount);
player.gainExperience(int exp);
player.isAlive();
player.getHealth();
```

#### CombatSystem
```java
boolean startCombat(Player player, Enemy enemy);
// Returns true if player wins, false if player loses
```

## 🤝 Contributing

Students should:
1. Follow the template structure
2. Use meaningful names for classes
3. Comment your code
4. Test your level thoroughly
5. Make sure your level is beatable!

## 📖 Polymorphism Quiz Questions

After completing this project, students should be able to answer:

1. Why can the `GameEngine` accept any level without knowing its specific type?
2. How does the `start()` method demonstrate polymorphism?
3. What's the difference between `Enemy` (abstract class) and a potential `Combatable` (interface)?
4. Why do all levels need to extend `Level` instead of just implementing similar methods?
5. How does polymorphism make this project scalable for many students?

## 📄 License

Educational use for AP Computer Science courses.

---

**Happy Coding! May your levels be creative and your code be polymorphic! 🎮**