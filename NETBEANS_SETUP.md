# NetBeans Project Setup

This project has been configured to work with Apache NetBeans IDE.

## 📦 Opening the Project in NetBeans

1. **Open NetBeans IDE**
2. Go to **File → Open Project** (or press `Ctrl+Shift+O`)
3. Navigate to the `APCS-Game-Engine` folder
4. Click **Open Project**

NetBeans will recognize this as a Java Application project.

## 🎮 Running the Project

### Console Version (Default)
1. Right-click on the project in the Projects panel
2. Select **Run** (or press `F6`)
3. The main console game (`Main.java`) will start

### 2D GUI Version
1. Right-click on `Main2D.java` in the `src` folder
2. Select **Run File** (or press `Shift+F6`)

### GUI Swing Version
1. Right-click on `MainGUI.java` in the `src` folder
2. Select **Run File** (or press `Shift+F6`)

## 🔨 Building the Project

- **Build**: Right-click project → **Build** (or press `F11`)
- **Clean and Build**: Right-click project → **Clean and Build** (or press `Shift+F11`)
- **Clean**: Right-click project → **Clean**

Build output will be in the `dist/` folder as `APCS-Game-Engine.jar`

## 🧪 Project Structure in NetBeans

```
APCS-Game-Engine
├── Source Packages
│   ├── core/              # Core engine classes
│   ├── engine/            # 2D engine classes
│   ├── gui/               # GUI components
│   ├── examples/          # Example levels
│   ├── levels/            # Student level implementations
│   ├── enemies/           # Enemy classes
│   ├── Main.java          # Console version entry
│   ├── Main2D.java        # 2D version entry
│   └── MainGUI.java       # GUI version entry
├── Test Packages          # (empty - for future tests)
└── Libraries              # Java SDK libraries

## ⚙️ Configuration Files

- **nbproject/project.xml**: NetBeans project configuration
- **nbproject/project.properties**: Build properties
- **nbproject/build-impl.xml**: Ant build implementation
- **build.xml**: Main Ant build script
- **manifest.mf**: JAR manifest file

## 🎓 For Students

### Adding Your Level to NetBeans

1. Right-click on **Source Packages** → **New** → **Java Class**
2. Name it `YourName_Level` (e.g., `Smith_Level`)
3. Copy the template from `STUDENT_TEMPLATE.java`
4. Implement your level logic
5. NetBeans will automatically compile your code

### Compiling Individual Files

- Right-click on any `.java` file → **Compile File** (or press `F9`)

### Running Individual Classes

- Right-click on any `.java` file with a `main()` method → **Run File** (or press `Shift+F6`)

## 📚 Useful NetBeans Features

### Code Completion
- Press `Ctrl+Space` for auto-completion suggestions

### Fix Imports
- Press `Ctrl+Shift+I` to automatically fix missing imports

### Format Code
- Press `Alt+Shift+F` to auto-format your code

### Refactor
- Right-click on a class/method → **Refactor** for safe renaming

### Documentation
- Press `Ctrl+Space` over a method to see JavaDoc

## 🔧 Troubleshooting

### Build Errors
- Make sure Java JDK 11+ is installed
- Go to **Tools → Java Platforms** to verify your JDK

### Project Not Recognized
- Ensure all `nbproject/` files are present
- Try **File → Close Project** then **Open Project** again

### Main Class Not Found
- Right-click project → **Properties** → **Run**
- Verify **Main Class** is set to `Main`

## 🚀 Advanced Usage

### Changing Main Class
1. Right-click project → **Properties**
2. Go to **Run** category
3. Change **Main Class** to `Main2D` or `MainGUI`
4. Click **OK**

### Adding External Libraries
1. Right-click project → **Properties**
2. Go to **Libraries** category
3. Click **Add JAR/Folder**
4. Select your library files

### Setting JVM Arguments
1. Right-click project → **Properties**
2. Go to **Run** category
3. Add arguments in **VM Options** field

## 📝 Batch Scripts Still Work!

The original `.bat` files still work for command-line compilation:
- `compile.bat` - Compile console version
- `compile-2d.bat` - Compile 2D version
- `compile-gui.bat` - Compile GUI version
- `run.bat` - Run console version
- `run-2d.bat` - Run 2D version
- `run-gui.bat` - Run GUI version

## 📖 Additional Resources

- [NetBeans Documentation](https://netbeans.apache.org/kb/)
- See `README.md` for project overview
- See `STUDENT_QUICKSTART.md` for student instructions
- See `TEACHER_GUIDE.md` for teacher information

---

**Happy Coding in NetBeans! 🎮**
