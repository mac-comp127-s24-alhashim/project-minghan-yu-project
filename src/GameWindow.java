import javax.swing.JFrame;
import java.awt.Dimension;

public class GameWindow {
    private JFrame frame;

    public GameWindow(String title, int width, int height, Game game) {
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
