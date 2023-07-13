package typo;

import java.awt.Font;
import java.awt.Graphics;

public class Space extends Box{
    private double width;
    private double stretchingCapacity;

    public Space(double minDim, double stretchingCapacity){
        this.width = minDim;
        this.stretchingCapacity = stretchingCapacity;
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        return false;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getAscent() {
        return 0;
    }

    @Override
    public double getDescent() {
        return 0;
    }

    @Override
    public double getStretchingCapacity() {
        return this.stretchingCapacity;
    }

    public String toString() {
        return "Space" + super.toString();
    }
}

