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
    
    private static File findSoundFile(String filename) {
        File file = new File(SOUND_DIR + filename);
        if (file.exists()) {
            return file;
        }
        file = new File("src/" + SOUND_DIR + filename);
        if (file.exists()) {
            return file;
        }
        return null;
    }
    
    /**
     * Play looping background music
     * @param filename Name of BGM file (e.g., "theme.wav")
     */
    public static synchronized void playBGM(String filename) {
        if (activeBgmName.equals(filename)) {
            return; // Already playing
        }
        
        stopBGM();
        activeBgmName = filename;
        
        new Thread(() -> {
            try {
                File file = findSoundFile(filename);
                if (file == null) {
                    System.out.println("[BGM Playing]: " + filename);
                    return;
                }
                
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                
                synchronized (SoundManager.class) {
                    if (activeBgmName.equals(filename)) {
                        activeBgm = clip;
                    } else {
                        // BGM changed while loading, close the clip
                        clip.stop();
                        clip.close();
                        audioStream.close();
                    }
                }
            } catch (Exception e) {
                System.err.println("Error playing BGM " + filename + ": " + e.getMessage());
            }
        }).start();
    }
    
    /**
     * Stop currently playing BGM
     */
    public static synchronized void stopBGM() {
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
                File file = findSoundFile(filename);
                if (file == null) {
                    System.out.println("[SFX Played]: " + filename);
                    return;
                }
                
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try {
                            audioStream.close();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                });
                clip.start();
            } catch (Exception e) {
                System.err.println("Error playing SE " + filename + ": " + e.getMessage());
            }
        }).start();
    }
}
