# APCS-Game-Engine

## Project description

Teaching-oriented Java 2D game engine with reusable level, entity, combat, and input primitives.

## Architecture

`engine/` supplies reusable systems; `enemies/` and `levels/` provide content; `Main.java` launches the game; templates and student guides support classroom extension.

## Technology

Java • Swing/AWT • Ant

## Run locally

`ant run`

## Repository guide

The implementation is organized so that entry points remain thin and domain-specific logic stays in the modules named above. Configuration, assets, and deployment files are kept separate from application code. Review the source tree before changing behavior, and keep secrets in local environment files rather than committing them.
