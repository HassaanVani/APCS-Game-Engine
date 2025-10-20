# Teacher's Guide - APCS Turn-Based RPG Engine

## 📋 Project Overview

This project is designed to teach **polymorphism** through a collaborative game development exercise. Each student creates one level for a turn-based RPG, and the game engine seamlessly integrates all levels using polymorphic principles.

## 🎯 Educational Goals

### Primary Objectives
1. **Understand Polymorphism** - Students see how different implementations work through a common interface
2. **Practice Inheritance** - Students extend abstract classes
3. **Apply OOP Principles** - Encapsulation, abstraction, inheritance, and polymorphism
4. **Collaborative Coding** - Multiple students contribute to one cohesive project

### AP CS Standards Covered
- Inheritance and polymorphism
- Abstract classes and methods
- Method overriding
- Object-oriented design
- ArrayList and collections

## 📚 How to Teach This Project

### Week 1: Introduction
1. **Explain the project concept** (15 min)
   - Show the completed example with ForestLevel and CaveLevel
   - Play through both levels to demonstrate

2. **Teach polymorphism basics** (30 min)
   - Use the Level class as the main example
   - Show how GameEngine.addLevel() accepts any Level subclass
   - Demonstrate dynamic binding with start() method

3. **Walk through the code** (45 min)
   - Review core classes: Entity, Level, Enemy, Item
   - Explain abstract methods and why they're used
   - Show the example implementations

### Week 2: Planning & Design
1. **Level Design Workshop** (1 class period)
   - Students plan their level on paper
   - Story outline
   - Enemy types and stats
   - Item rewards
   - Decision points

2. **Design Review**
   - Review each student's plan
   - Ensure difficulty is appropriate
   - Check for creativity and variety

### Week 3-4: Implementation
1. **Coding Sessions**
   - Students implement their levels
   - Provide support and debugging help
   - Regular check-ins on progress

2. **Peer Review**
   - Students play each other's levels
   - Provide feedback
   - Fix bugs

### Week 5: Integration & Presentation
1. **Final Integration**
   - Collect all student levels
   - Add them to Main.java
   - Test the complete game

2. **Class Playthrough**
   - Play the complete game as a class
   - Discuss what makes each level unique
   - Celebrate the collaboration!

## 🔧 Technical Setup

### Prerequisites
- Java JDK 8 or higher
- Any IDE or text editor
- Command line or IDE for compilation

### Initial Setup for Class

1. **Clone or download the repository**
2. **Test the example game:**
   ```powershell
   javac src/core/*.java src/examples/*.java src/Main.java
   java -cp src Main
   ```
3. **Verify everything runs correctly**

### Student Workflow

1. **Each student gets:**
   - Copy of STUDENT_TEMPLATE.java
   - Access to core and examples packages (read-only)

2. **Students create:**
   - `StudentName_Level.java` (required)
   - `StudentName_Enemy.java` (optional)
   - `StudentName_Item.java` (optional)

3. **Submission:**
   - Students submit their Java files
   - You place them in `src/` directory
   - Add to Main.java

## 📊 Grading Rubric (Example - 100 points)

### Functionality (40 points)
- [ ] Level extends Level class correctly (10 pts)
- [ ] start() method is implemented (10 pts)
- [ ] Level is completable (10 pts)
- [ ] Handles player death properly (10 pts)

### Creativity (30 points)
- [ ] Unique story/theme (10 pts)
- [ ] Creative enemy or item implementations (10 pts)
- [ ] Interesting choices or puzzles (10 pts)

### Code Quality (20 points)
- [ ] Clean, readable code (5 pts)
- [ ] Proper comments and documentation (5 pts)
- [ ] Appropriate difficulty balance (5 pts)
- [ ] No bugs or crashes (5 pts)

### Polymorphism Understanding (10 points)
- [ ] Can explain how their level uses polymorphism (10 pts)

## 🎓 Discussion Questions

### Before Project
1. What is polymorphism?
2. Why would we want different classes to share a common interface?
3. What's the difference between abstract classes and interfaces?

### During Project
1. Why does your level need to extend the Level class?
2. How does the game engine know how to run your level?
3. What would happen if you didn't call complete()?

### After Project
1. How did polymorphism make this collaborative project possible?
2. Could we have done this without polymorphism? How?
3. What other projects could benefit from this design pattern?

## 🐛 Common Issues & Solutions

### Issue: Student level won't compile
**Solution:** Check package declarations and import statements

### Issue: Level crashes during gameplay
**Solution:** Verify player death checks and null pointer guards

### Issue: Combat system doesn't work
**Solution:** Make sure Student_Enemy extends Enemy correctly

### Issue: Items don't appear
**Solution:** Check that addItem() is called properly

## 🎨 Extension Ideas

### For Advanced Students
1. **Boss Enemies** - Special enemy with multiple phases
2. **Equipment System** - Permanent items that modify stats
3. **NPCs** - Non-combat characters with dialogue
4. **Shops** - Spend gold on items
5. **Save System** - Persist player progress
6. **Special Abilities** - Player skills with cooldowns

### Class Expansions
1. **Multiple Paths** - Levels can be completed in different orders
2. **Difficulty Modes** - Easy/Normal/Hard scaling
3. **Achievement System** - Track player accomplishments
4. **Level Editor** - GUI for creating levels
5. **Multiplayer** - Two players cooperate or compete

## 📝 Assessment Ideas

### Quizzes
- Identify polymorphism in provided code
- Write abstract class implementations
- Debug inheritance issues

### Written Reflections
- "How did polymorphism benefit our collaborative project?"
- "What was the most challenging part of implementing your level?"
- "How would you improve the game engine?"

### Presentations
- Students present their level design choices
- Explain their custom enemy AI
- Demonstrate polymorphism using their code

## 🔄 Reusability

This project can be reused year after year:
- Different students create different levels
- Best levels from previous years can become examples
- Framework remains the same
- Endless variety in student creativity

## 📞 Support

### Resources for Teachers
- Review the example levels for guidance
- Test student levels individually before integration
- Keep a backup of working code before adding new levels
- Use version control (Git) for tracking changes

### Debugging Steps
1. Compile each student's level independently
2. Test with a simple main method
3. Check for infinite loops or missing complete() calls
4. Verify enemy stats are reasonable

## 🎉 Celebration Ideas

- **Class Game Tournament** - Who can complete the game fastest?
- **Level Awards** - Most creative, hardest, best story, etc.
- **Showcase** - Display the game to other classes or parents
- **Code Review Session** - Discuss interesting implementations

---

## Final Notes

This project works best when:
- Students understand basic OOP before starting
- You provide clear examples and templates
- There's time for iteration and improvement
- The class celebrates the collaborative achievement

**Remember:** The goal is not just to create a game, but to deeply understand polymorphism through practical application!

Good luck with your class! 🎮📚
