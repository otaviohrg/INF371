package typo;

import java.awt.*;

public class Hbox extends Group{
    public Hbox(){
        this.width = 0;
        this.ascent = 0;
        this.descent = 0;
        this.stretchingCapacity = 0;
    }

    @Override
    public void add(Box b) {
        super.add(b);
        if(b.getAscent()>this.getAscent()) this.ascent = b.getAscent();
        if(b.getDescent()>this.getDescent()) this.descent = b.getDescent();
        this.width += b.getWidth();
        this.stretchingCapacity += b.getStretchingCapacity();
    }

    @Override
    public String getType() {
        return "Hbox";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        double mw = this.getWidth();
        double width = w;
        double newWidth;
        boolean success = true;
        if(mw > w){
            width = mw;
            success = false;
        }
        double delta = width - mw;
        double drawnDistance = 0;
        if(this.stretchingCapacity != 0){
            for(Box b: list){
                newWidth = b.getWidth()+delta*b.getStretchingCapacity()/this.stretchingCapacity;
                b.draw(graph, x+drawnDistance, y- b.getAscent()+this.ascent, newWidth);
                drawnDistance += newWidth;
            }
        }
        else {
            for(Box b: list){
                newWidth = b.getWidth()+delta/this.list.size();
                b.draw(graph, x+drawnDistance, y- b.getAscent()+this.ascent, newWidth);
                drawnDistance += newWidth;
            }
        }

        return success;
    }
}
