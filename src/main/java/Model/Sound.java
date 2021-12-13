package Model;

import java.io.*;
import javax.sound.sampled.*;

/**
 * This is Sound class to play background music when running the game.
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class Sound{

    /**
     * @param s constructor to open audio input stream, get sound clip resource and load audio clip to play background music
     */
    public void playSound(String s) {

        try {
            File soundFile = new File("src/main/resources/bgm.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}