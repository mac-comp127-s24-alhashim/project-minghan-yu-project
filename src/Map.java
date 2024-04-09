import java.awt.Graphics;
import java.awt.Color;

public class Map {
    private final int tileSize = 32; 
    private final int width, height; 
    private int[][] tiles; 

    
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width][height];
        generateMap(); 
    }

    
    private void generateMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    tiles[x][y] = 1; // 1 represents a wall
                } else {
                    tiles[x][y] = 0; // 0 represents a walkable tile
                }
            }
        }
    }

    public void render(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] == 1) {
                    g.setColor(Color.GRAY); // Color for walls
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else {
                    g.setColor(Color.BLACK); // Color for empty space
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
    }
}
