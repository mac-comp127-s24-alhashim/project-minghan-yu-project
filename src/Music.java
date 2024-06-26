import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

public class Music {
    private Clip clip;

    public void playMusic(String path) {
        if (clip != null && clip.isRunning()) {
            clip.stop(); 
        }
        try {
            File soundFile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start(); 
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error in playing music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
