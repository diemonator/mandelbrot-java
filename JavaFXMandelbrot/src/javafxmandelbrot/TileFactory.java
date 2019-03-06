package javafxmandelbrot;

/**
 *
 * @author Joris
 */
public class TileFactory {

    private final int pxmax;    // nrof pixels in x direction
    private final int pymax;    // nrof pixels in y direction
    private int nrofTiles;
    private int tileWidth;      // in pixels
    private int tileHeight;
    private double cx;          // center coordinate
    private double cy;
    private double step;        // step per pixel
    private double ulx;         // upper left coordinate
    private double uly;

    TileFactory(int x, int y, double cx, double cy, double step) {
        this.pxmax = x;
        this.pymax = y;
        this.cx = cx;
        this.cy = cy;
        this.step = step;
        this.nrofTiles = 1;
        ulx = cx - (pxmax / 2. * step);
        uly = cy + (pymax / 2. * step);
    }

    private void reCenter(double x, double y) {
        //System.out.println("old c:" + cx+","+cy+ " ul:"+ulx+","+uly);
        cx += (x - pxmax / 2.) * step;
        cy -= (y - pymax / 2.) * step;  // math and pixel are inverted for y
        ulx = cx - (pxmax / 2. * step);
        uly = cy + (pymax / 2. * step);
        //System.out.println("new c:" + cx+","+cy+ " ul:"+ulx+","+uly);
    }

    public void setNrofTiles (int nrofTiles)
    {
        this.nrofTiles = nrofTiles;
        this.tileWidth = pxmax / this.nrofTiles;
        this.tileHeight = pymax / this.nrofTiles;
    }
    
    public void zoomIn(double x, double y) {
        step *= 2.;
        reCenter(x, y);
    }

    public void zoomOut(double x, double y) {
        step /= 2.;
        reCenter(x, y);
    }
    
    public void pan(double x, double y) {
        reCenter(x, y);
    }
    
    public Tile createTile(int i, int j)
    {
        int pxmin = i * tileWidth;  // start pixel of this tile
        int pymin = j * tileHeight;
        
        Tile t = new Tile(pxmin, tileWidth,
                    pymin, tileHeight,
                    ulx + pxmin * step,
                    uly - pymin * step,
                    step
                    );
        return t;
    }
}
