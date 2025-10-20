@echo off
echo Compiling APCS Game Engine (GUI Version)...
echo.

REM Compile core classes
javac src/core/*.java

REM Compile GUI classes
javac -cp src src/gui/*.java

REM Compile examples
javac -cp src src/examples/*.java

REM Compile Main
javac -cp src src/MainGUI.java

echo.
echo Compilation complete!
echo Run the game with: run-gui.bat
pause
