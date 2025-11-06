@echo off
echo Compiling 2D RPG Engine...
echo.

javac src/engine/*.java src/enemies/*.java src/levels/*.java src/Main.java

if %errorlevel% == 0 (
    echo.
    echo Compilation successful!
    echo Run with: run.bat
) else (
    echo.
    echo Compilation failed! Check errors above.
)

pause
