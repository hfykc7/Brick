package View;

import java.io.*;
import javax.sound.sampled.*;

/**
 * Sound class to play background music when run the game.
 *
 * @author Cheo Kai Wen
 * @version 1.5
 * @since 2021
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
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}