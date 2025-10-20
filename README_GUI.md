# APCS Turn-Based RPG Engine - GUI Version 🎮

A Java Swing-based game engine designed to teach **polymorphism** through collaborative level design.

## 🎮 What's Different in the GUI Version?

Instead of console text, the game features:
- **Beautiful Swing GUI** with dark theme
- **Visual stats panel** showing player health, inventory, and stats
- **Button-based choices** instead of keyboard input
- **Smooth text display** in a scrollable area
- **Modern UI** with hover effects and colors

## 🚀 Quick Start

### Run the Example Game

**Option 1: Use batch files (easiest)**
```
1. Double-click compile-gui.bat
2. Double-click run-gui.bat
```

**Option 2: Command line**
```powershell
javac src/core/*.java src/gui/*.java src/examples/*.java src/MainGUI.java
java -cp src MainGUI
```

### For Students

1. **Copy the template:**
   - Copy `STUDENT_TEMPLATE_GUI.java` to `src/StudentName_LevelGUI.java`

2. **Key differences from console version:**
   - Extend `LevelGUI` instead of `Level`
   - Use `gui.displayText()` instead of `System.out.println()`
   - Use `gui.waitForButton()` for continuation
   - Use `gui.showChoices()` for player choices
   - Use **callbacks** (lambda functions) for async flow

3. **Example Pattern:**
```java
@Override
public void start(Player player) {
    this.player = player;
    
    gui.displayText("Story text here...");
    
    gui.waitForButton("Continue", () -> {
        gui.clearButtons();
        nextMethod(); // Continue your story
    });
}

private void nextMethod() {
    gui.displayText("More story...");
    
    String[] choices = {"Option 1", "Option 2"};
    gui.showChoices(choices, (choiceIndex) -> {
        gui.clearButtons();
        if (choiceIndex == 0) {
            // Handle option 1
        } else {
            // Handle option 2
        }
    });
}
```

## 📁 GUI-Specific Files

```
src/
├── gui/                       # GUI components
│   ├── GameWindow.java        # Main window
│   ├── GamePanel.java         # Game display area
│   ├── StatsPanel.java        # Player stats sidebar
│   ├── GUICallback.java       # Interface for level interaction
│   └── ChoiceCallback.java    # Choice handling
│
├── core/
│   ├── LevelGUI.java          # Abstract level for GUI ⭐
│   ├── GameEngineGUI.java     # GUI game engine
│   ├── CombatSystemGUI.java   # GUI combat system
│   └── CombatCallback.java    # Combat completion callback
│
├── examples/
│   ├── ForestLevelGUI.java    # Example GUI level
│   └── CaveLevelGUI.java      # Example GUI level
│
└── MainGUI.java               # Entry point
```

## 🎨 GUI Features

### Game Window (1000x700 pixels)
- **Left Panel** (750px): Story text and choice buttons
- **Right Panel** (250px): Player stats and inventory

### Visual Elements
- 📊 **Health Bar** - Visual HP indicator
- 🎒 **Inventory Display** - Real-time item list
- ⚔️ **Stat Display** - Attack, defense, level, gold
- 🎨 **Color-coded UI** - Dark theme with blue accents
- 🖱️ **Hover Effects** - Interactive buttons

### Text Display
- Scrollable text area
- Monospaced font for readability
- Auto-scroll to latest content
- Separator lines for sections

## 🔧 Understanding Callbacks

The GUI version uses **asynchronous programming**:

### Console Version (Sequential):
```java
System.out.println("Make a choice:");
int choice = scanner.nextInt();
if (choice == 1) {
    doSomething();
}
```

### GUI Version (Callback-based):
```java
gui.displayText("Make a choice:");
gui.showChoices(choices, (choiceIndex) -> {
    if (choiceIndex == 0) {
        doSomething();
    }
});
```

**Why?** GUI events happen later - we provide a function that runs when the player clicks.

## 📝 Student Level Structure

```java
public class MyLevelGUI extends LevelGUI {
    private CombatSystemGUI combat;
    private Player player;
    
    public MyLevelGUI(CombatSystemGUI combat) {
        super("Level Name", "Description");
        this.combat = combat;
    }
    
    @Override
    public void start(Player player) {
        this.player = player;
        
        // 1. Show intro
        gui.displayText("Story...");
        
        // 2. Continue with button
        gui.waitForButton("Continue", () -> {
            gui.clearButtons();
            showChoices();
        });
    }
    
    private void showChoices() {
        // 3. Show choices
        String[] choices = {"Option 1", "Option 2"};
        gui.showChoices(choices, (choice) -> {
            gui.clearButtons();
            handleChoice(choice);
        });
    }
    
    private void handleChoice(int choice) {
        // 4. Handle the choice
        if (choice == 0) {
            // Combat
            Goblin enemy = new Goblin();
            combat.startCombat(player, enemy, (won) -> {
                if (won) {
                    gui.displayText("Victory!");
                    complete();
                }
            });
        } else {
            complete();
        }
    }
}
```

## 🎓 What Students Learn

### Same as Console Version:
- ✅ Polymorphism
- ✅ Inheritance
- ✅ Abstract classes
- ✅ OOP design

### Additional GUI Concepts:
- ✅ **Event-driven programming**
- ✅ **Callbacks and lambdas**
- ✅ **Swing GUI basics**
- ✅ **Asynchronous flow**

## 🆚 Console vs GUI Comparison

| Feature | Console | GUI |
|---------|---------|-----|
| Extend | `Level` | `LevelGUI` |
| Text Output | `System.out.println()` | `gui.displayText()` |
| Wait for Input | `scanner.nextLine()` | `gui.waitForButton()` |
| Get Choice | `scanner.nextInt()` | `gui.showChoices()` |
| Flow Control | Sequential | Callback-based |
| Visual Feedback | Text only | Colors, bars, icons |
| Stats Display | Print on demand | Always visible |

## 🔧 API Reference

### GUICallback Methods

```java
// Display text
gui.displayText(String text);

// Clear display
gui.clearText();

// Show separator line
gui.displaySeparator();

// Wait for button click
gui.waitForButton(String buttonText, Runnable callback);

// Show multiple choices
gui.showChoices(String[] choices, ChoiceCallback callback);

// Update stats panel
gui.updateStats();

// Clear all buttons
gui.clearButtons();
```

### Combat System

```java
combat.startCombat(Player player, Enemy enemy, CombatCallback callback);

// Callback receives boolean:
(playerWon) -> {
    if (playerWon) {
        // Victory
    } else {
        // Defeat
    }
}
```

## 🐛 Common Issues

### Issue: Nothing happens when I click
**Solution:** Make sure you called `gui.clearButtons()` before adding new buttons

### Issue: Text doesn't show
**Solution:** Use `gui.displayText()` not `System.out.println()`

### Issue: Buttons don't appear
**Solution:** Check that your callback method calls `gui.waitForButton()` or `gui.showChoices()`

### Issue: Level never completes
**Solution:** Call `complete()` at the end of your level

## 📚 Example Levels

Study these files to see GUI in action:
- `src/examples/ForestLevelGUI.java` - Simple combat and choices
- `src/examples/CaveLevelGUI.java` - More complex with multiple paths

## 🎮 Adding Your Level to the Game

In `src/MainGUI.java`:

```java
// Add after the example levels
engine.addLevel(new ForestLevelGUI(engine.getCombatSystem()));
engine.addLevel(new CaveLevelGUI(engine.getCombatSystem()));
engine.addLevel(new YourName_LevelGUI(engine.getCombatSystem())); // ← Add yours!
```

## 🎨 Customization Ideas

Students can make levels unique with:
- Different story themes
- Unique enemy types
- Creative choice scenarios
- Multiple paths
- Hidden secrets
- Boss battles

## 📖 Further Reading

- Study the example levels for patterns
- Read `STUDENT_TEMPLATE_GUI.java` for detailed comments
- Check the console version for comparison
- Ask your teacher for help!

---

**The GUI version provides the same polymorphism learning with a modern, visual interface!** 🚀

**Created for AP Computer Science students**
**Built with Java Swing**
**Designed for learning and creativity**
