# 🚀 How to Push This Project to GitHub

## Quick Commands (If You Know Git)

```bash
cd C:\Users\Hassaan\Desktop\APCS-Game-Engine
git add .
git commit -m "Initial commit: 2D RPG Engine with hub system"
git push origin main
```

---

## Complete Step-by-Step Guide

### Step 1: Initialize Git (If Not Already Done)

Check if git is initialized:
```bash
cd C:\Users\Hassaan\Desktop\APCS-Game-Engine
git status
```

If you see "not a git repository", initialize it:
```bash
git init
```

### Step 2: Create .gitignore

The project already has `.gitignore` but verify it includes:
```
*.class
*.log
.idea/
*.iml
.vscode/
.DS_Store
Thumbs.db
```

### Step 3: Stage All Files

Add all files to git:
```bash
git add .
```

Check what's staged:
```bash
git status
```

### Step 4: Create First Commit

```bash
git commit -m "Initial commit: 2D RPG Engine with polymorphic level system"
```

### Step 5: Create GitHub Repository

1. Go to https://github.com
2. Click **"New repository"** (+ icon top right)
3. Repository name: `APCS-Game-Engine`
4. Description: "2D top-down RPG game engine for teaching polymorphism"
5. Public or Private: Your choice
6. **DO NOT** initialize with README (we already have one)
7. Click **"Create repository"**

### Step 6: Link Local to GitHub

GitHub will show commands. Use the "push an existing repository" section:

```bash
git remote add origin https://github.com/YourUsername/APCS-Game-Engine.git
git branch -M main
git push -u origin main
```

Replace `YourUsername` with your actual GitHub username!

### Step 7: Verify

Go to your GitHub repository URL and refresh. You should see all files!

---

## Future Updates

After making changes:

```bash
# See what changed
git status

# Add changes
git add .

# Commit with message
git commit -m "Added volcano level"

# Push to GitHub
git push
```

---

## Common Issues & Solutions

### Issue: "Permission denied"
**Solution:** Use HTTPS URL and authenticate with token, or setup SSH keys

### Issue: "Repository not found"
**Solution:** Double-check the repository name and your username

### Issue: "Failed to push"
**Solution:** Pull first: `git pull origin main` then `git push`

### Issue: "Large files"
**Solution:** Remove .class files: `git rm -r --cached src/**/*.class`

---

## Recommended Repository Structure

```
APCS-Game-Engine/
├── README_2D.md              ← Main documentation
├── START_HERE_2D.md          ← Quick start
├── HOW_TO_ADD_YOUR_LEVEL.md  ← Student guide
├── STUDENT_TEMPLATE_2D.java  ← Template
├── src/                      ← Source code
├── compile-2d.bat            ← Build scripts
└── run-2d.bat
```

---

## What to Include in README on GitHub

Update the main README to explain:
1. **What it is** - 2D RPG engine for teaching polymorphism
2. **How to run** - compile-2d.bat and run-2d.bat
3. **For teachers** - How to use in class
4. **For students** - How to add levels
5. **Screenshots** - If you want to add them later

---

## Branches for Student Work

If you want students to submit via GitHub:

### Create Student Branch Template
```bash
git checkout -b template-student-level
# ... make template changes
git push origin template-student-level
```

### Students Fork and Submit
1. Students **fork** your repository
2. Create their level in their fork
3. Submit **pull request** with their level
4. You review and merge

---

## GitHub Desktop (Alternative)

If you prefer GUI:

1. Download **GitHub Desktop**: https://desktop.github.com/
2. Open the app
3. File → Add Local Repository
4. Browse to `C:\Users\Hassaan\Desktop\APCS-Game-Engine`
5. Click "Publish repository"
6. Fill in details and publish

---

## Quick Reference

### First Time Setup
```bash
cd C:\Users\Hassaan\Desktop\APCS-Game-Engine
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/YourUsername/APCS-Game-Engine.git
git push -u origin main
```

### Regular Updates
```bash
git add .
git commit -m "Description of changes"
git push
```

### Check Status
```bash
git status          # What's changed
git log             # Commit history
git remote -v       # Remote repository
```

---

## Adding a Good README.md to GitHub

Create a `README.md` at the root with:

```markdown
# APCS 2D RPG Game Engine

A 2D top-down RPG game engine (like Zelda/Pokemon) built in Java to teach **polymorphism** through collaborative game development.

## 🎮 Features

- Top-down movement with WASD/Arrow keys
- Tile-based maps with collision detection
- Pokemon-style turn-based battles
- Hub system connecting multiple student levels
- Each student creates one level - all connect seamlessly!

## 🚀 Quick Start

```bash
compile-2d.bat
run-2d.bat
```

## 📚 Documentation

- [START_HERE_2D.md](START_HERE_2D.md) - Quick start guide
- [README_2D.md](README_2D.md) - Complete documentation
- [HOW_TO_ADD_YOUR_LEVEL.md](HOW_TO_ADD_YOUR_LEVEL.md) - Student guide

## 🎯 For Teachers

Perfect for teaching:
- Polymorphism (main concept)
- Inheritance and abstract classes
- Object-oriented design
- Collaborative programming

## 🎓 For Students

1. Copy `STUDENT_TEMPLATE_2D.java`
2. Create your level (extend `GameLevel`)
3. Add to `Main2D.java`
4. Your level is now part of the game!

## 📸 Screenshots

(Add later if you want)

## 📄 License

Educational use for AP Computer Science courses.
```

---

## Tags and Releases

After pushing, create a release:

1. Go to your GitHub repository
2. Click "Releases" → "Create a new release"
3. Tag: `v1.0` 
4. Title: "Initial Release - 2D RPG Engine"
5. Description: Features and usage
6. Publish release

---

## Collaboration Setup

For classroom use:

### Option 1: Single Repository
- Teacher owns repo
- Students submit files directly
- Teacher adds to Main2D.java

### Option 2: Fork & Pull Request
- Teacher owns main repo
- Students fork
- Students submit PR with their level
- Teacher reviews and merges

### Option 3: Organization
- Create GitHub Organization
- Add students as members
- Each student has write access
- Use branches for each student

---

## Clone Instructions for Others

Share this with your teacher/classmates:

```bash
# Clone the repository
git clone https://github.com/YourUsername/APCS-Game-Engine.git

# Navigate to project
cd APCS-Game-Engine

# Run the game
compile-2d.bat
run-2d.bat
```

---

## Final Checklist

Before pushing:

- [ ] All files compile without errors
- [ ] `.gitignore` is set up properly
- [ ] No `.class` files included
- [ ] README is informative
- [ ] Documentation is complete
- [ ] Game runs successfully
- [ ] Student template is clear

---

**You're ready to share your project with the world!** 🚀

```bash
git add .
git commit -m "2D RPG Engine with hub and polymorphic levels"
git push origin main
```
