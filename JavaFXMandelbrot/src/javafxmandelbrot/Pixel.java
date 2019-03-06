package javafxmandelbrot;

import javafx.scene.paint.Color;

/**
 *
 * @author Joris
 */
public class Pixel {

    private final int x;
    private final int y;
    private final Color color;
    
    Pixel(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.color = c;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public Color getColor ()
    {
        return color;
    }
}
