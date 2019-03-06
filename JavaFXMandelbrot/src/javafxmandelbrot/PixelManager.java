package javafxmandelbrot;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Joris
 */
public class PixelManager implements Runnable {

    private final GraphicsContext gc;
    private Pixel[] pixelArray;
    private int i = 0;

    PixelManager(GraphicsContext gc, int size) {
        this.gc = gc;
        pixelArray = new Pixel[size];
    }

    public synchronized void add(Pixel p) {
        pixelArray[i++] = p;
    }


    public void show() {
        // TODO: display all pixels in pixelArray[], and empty the array afterwards
        Platform.runLater(this);
    }

    @Override
    public synchronized void run() {
        for (Pixel pixel : pixelArray) {
            if (pixel != null) {
                gc.setFill(pixel.getColor());
                gc.fillRect(pixel.getX(), pixel.getY(), 1, 1);
            }
        }
        pixelArray = new Pixel[pixelArray.length];
        i = 0;
    }
}
