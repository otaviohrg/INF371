package typo;

import java.awt.*;

public class Vbox extends Group{
    private final double lineSkip;
    private boolean empty = true;
    public Vbox(double lineSkip){
        this.width = 0;
        this.ascent = 0;
        this.descent = 0;
        this.stretchingCapacity = 0;
        this.lineSkip = lineSkip;
    }

    @Override
    public void add(Box b) {
        super.add(b);
        if(b.getWidth() > this.width) this.width = b.getWidth();
        if(b.getStretchingCapacity() > this.stretchingCapacity) this.stretchingCapacity = b.getStretchingCapacity();
        if(empty) empty = false;
        else this.ascent += this.lineSkip;
        this.ascent += this.descent + b.getAscent();
        this.descent = b.getDescent();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getType() {
        return "Vbox";
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        double yDistance = 0;
        for(Box b: list){
            yDistance += b.getAscent();
            b.draw(graph, x, y- b.getAscent()+yDistance, w);
            yDistance += this.lineSkip;
        }
        return true;
    }
}
