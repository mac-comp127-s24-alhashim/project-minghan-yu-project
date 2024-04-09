import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.ArrayList;


public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private JFrame frame;
    private boolean running = false;
    private Thread gameThread;
    private Map gameMap;

    private List<Zombie> zombies;
    private List<Robot> robots;
    private Player player;

    public Game() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);

        zombies = new ArrayList<>(); 
        robots = new ArrayList<>(); 
        
        initializeEntities();

        frame = new JFrame("Top-Down Shooter");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        this.setFocusable(true);
        this.requestFocus();

        this.addKeyListener(new GameKeyListener(player));
      
    }

    private void initializeEntities() {
        gameMap = new Map(25, 19); 
        player = new Player(100, 100);

        zombies.add(new Zombie(100, 100));
        zombies.add(new Zombie(200, 150));
        robots.add(new Robot(200, 100));
        
        player.addWeapon(new Pistol());
        player.addWeapon(new Rifle());
        player.addWeapon(new AWP());
    }

    public void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;
        double delta = 0;
        int frames = 0;
        int ticks = 0;
        long lastTimer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;

            while (delta >= 1) {
                ticks++;
                update();
                delta -= 1;
                shouldRender = true;
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    private void update() {
        player.update();
        zombies.forEach(zombie -> zombie.update(player));
        robots.forEach(robot -> robot.update(player));
    
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());

        gameMap.render(g);

        player.render(g);
        zombies.forEach(zombie -> zombie.render(g));
        robots.forEach(robot -> robot.render(g));

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
