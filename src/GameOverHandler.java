import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameOverHandler {
    private Music music;
    private int score;
    private Random rand;

    public GameOverHandler(Music music, int score) {
        this.music = music;
        this.score = score;
        this.rand = new Random();
    }

    public void gameOver(boolean win, boolean teamChoice) {
        if (teamChoice) {
            gameOverG2(win);
        } else {
            gameOverNAVI(win);
        }
    }

    private void gameOverG2(boolean win) {
        String message;
        ImageIcon icon = null;
        if (win) {
            message = "You win! Your score: " + score;
            music.playWinningMusic();
        } else {
            message = "Game Over! Your score: " + score;
            music.playLosingMusic();
            icon = chooseIcon(Constant.G2LOSE1_IMAGE, Constant.G2LOSE2_IMAGE);
        }
        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    private void gameOverNAVI(boolean win) {
        String message;
        ImageIcon icon = null;
        if (win) {
            message = "You win! Your score: " + score;
            music.playWinningMusic();
        } else {
            message = "Game Over! Your score: " + score;
            music.playLosingMusic();
            icon = chooseIcon(Constant.G2LOSE1_IMAGE, Constant.G2LOSE2_IMAGE);
        }
        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    private ImageIcon chooseIcon(String img1, String img2) {
        ImageIcon icon1 = new ImageIcon(img1);
        ImageIcon icon2 = new ImageIcon(img2);
        int flag = rand.nextInt(10);
        if (flag <= 5) {
            return new ImageIcon(icon1.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        } else {
            return new ImageIcon(icon2.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        }
    }
}
