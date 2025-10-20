package core;

/**
 * Callback interface for combat completion
 */
public interface CombatCallback {
    void onCombatEnd(boolean playerWon);
}
