package javafxmandelbrot;

import javafx.scene.paint.Color;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Joris
 */
public class Mandelbrot implements Runnable {

    private final static int RED = 5;       // nrof bits for each color
    private final static int GREEN = 5;
    private final static int BLUE = 5;
    private final static int MAX_ITER = 1 << (RED + GREEN + BLUE);
    private final static double INFINITY = 64.;
    private final Tile tile;
    private final PixelManager pm;
    private CountDownLatch latch;

    Mandelbrot(Tile tile, PixelManager pm, CountDownLatch latch) {
        this.tile = tile;
        this.pm = pm;
        this.latch = latch;
    }

    private Color convertColor(int k) {
        int red;
        int green;
        int blue;

        // convert k into RGB
        red = k >> (GREEN + BLUE);
        red &= ((1 << RED) - 1);
        red <<= (8 - RED);
        green = k >> (BLUE);
        green &= ((1 << GREEN) - 1);
        green <<= (8 - GREEN);
        blue = k;
        blue &= ((1 << BLUE) - 1);
        blue <<= (8 - BLUE);
        return (Color.rgb(red, green, blue));
    }

    private Color calculatePoint(double x, double y) {
        int k;
        double zr, zi;  // real and imaginary part
        double tr, ti;  // temp

        zr = x;
        zi = y;

        for (k = 0; (k < MAX_ITER) && (((zr * zr) + (zi * zi)) < INFINITY); k++) {
            tr = (zr * zr) - (zi * zi); // complex square
            ti = 2. * zr * zi;

            zr = tr + x;                // complex add
            zi = ti + y;
        }
        //                                    2
        // k >= MAX_ITER or | z | >= INFINITY
        //
        return (convertColor(k));
    }

    public void calculate() {
        for (int j = 0; j < tile.ny && !Thread.currentThread().isInterrupted(); j++) {
            for (int i = 0; i < tile.nx && !Thread.currentThread().isInterrupted(); i++) {
                double x = tile.ulx + (i * tile.step);
                double y = tile.uly - (j * tile.step);   // math and pixel are inverted for y

                Color color = calculatePoint(x, y);
                Pixel pixel = new Pixel(i + tile.xmin, j + tile.ymin, color);
                pm.add(pixel);
            }
        }
        pm.show();
    }

    @Override
    public void run() {
        calculate();
        latch.countDown();
    }
}
