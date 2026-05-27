package engine;

import javax.sound.sampled.*;
import java.io.File;

/**
 * SoundManager - Audio manager that plays WAV files in separate threads
 * Falls back to console output if sound files are missing.
 */
public class SoundManager {
    private static Clip activeBgm = null;
    private static String activeBgmName = "";
    private static final String SOUND_DIR = "sound/";
    
    /**
     * Play looping background music
     * @param filename Name of BGM file (e.g., "theme.wav")
     */
    public static void playBGM(String filename) {
        if (activeBgmName.equals(filename)) {
            return; // Already playing
        }
        
        stopBGM();
        activeBgmName = filename;
        
        new Thread(() -> {
            try {
                File file = new File(SOUND_DIR + filename);
                if (!file.exists()) {
                    System.out.println("[BGM Playing]: " + filename);
                    return;
                }
                
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                activeBgm = clip;
            } catch (Exception e) {
                System.err.println("Error playing BGM " + filename + ": " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Stop currently playing BGM
     */
    public static void stopBGM() {
        if (activeBgm != null) {
            activeBgm.stop();
            activeBgm.close();
            activeBgm = null;
            activeBgmName = "";
        }
    }
    
    /**
     * Play one-shot Sound Effect (SE)
     * @param filename Name of sound file (e.g., "hit.wav")
     */
    public static void playSE(String filename) {
        new Thread(() -> {
            try {
                File file = new File(SOUND_DIR + filename);
                if (!file.exists()) {
                    System.out.println("[SFX Played]: " + filename);
                    return;
                }
                
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception e) {
                System.err.println("Error playing SE " + filename + ": " + e.getMessage());
            }
        }).start();
    }
}
