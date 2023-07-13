package typo;

import java.awt.*;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Group extends Box{
    protected double width;
    protected double ascent;
    protected double descent;
    protected double stretchingCapacity;

    protected final LinkedList<Box> list = new LinkedList<Box>();
    public void add(Box b){
        list.add(b);
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getAscent() {
        return ascent;
    }

    @Override
    public double getDescent() {
        return descent;
    }

    @Override
    public double getStretchingCapacity() {
        return stretchingCapacity;
    }

    abstract public String getType();

    @Override
    public String toString() {
        return recursiveToString(0);
    }

    public String recursiveToString(int n) {
        String tabs = IntStream.range(0, n).mapToObj(i -> "\t").collect(Collectors.joining());
        StringBuilder str = new StringBuilder(tabs + this.getType() + super.toString() + "{\n");
        int numTabs = n+1;
        for(Box b : this.list){
            if(b instanceof Group){
                str.append(((Group) b).recursiveToString(numTabs));
            }
            else{
                str.append(IntStream.range(0, numTabs).mapToObj(i -> "\t").collect(Collectors.joining()));
                str.append(b.toString()).append(",\n");
            }
        }
        str.append(IntStream.range(0, n).mapToObj(i -> "\t").collect(Collectors.joining())).append("}\n");
        return str.toString();
    }
}
