package javafxmandelbrot;

/**
 *
 * @author Joris
 */
public class Tile {

    int xmin;
    int nx;
    int ymin;
    int ny;
    double ulx;
    double uly;
    double step;

    Tile(int xmin, int nx, int ymin, int ny, double ulx, double uly, double step) {
        this.xmin = xmin;
        this.nx = nx;
        this.ymin = ymin;
        this.ny = ny;
        this.ulx = ulx;
        this.uly = uly;
        this.step = step;
    }
}
