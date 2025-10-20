@echo off
echo Compiling APCS Game Engine...
echo.

REM Compile core classes
javac src/core/*.java

REM Compile examples
javac -cp src src/examples/*.java

REM Compile Main
javac -cp src src/Main.java

echo.
echo Compilation complete!
echo Run the game with: run.bat
pause
