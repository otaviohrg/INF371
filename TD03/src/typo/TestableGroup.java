package typo;

import java.awt.*;

public class TestableGroup extends Group{

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        return false;
    }


    @Override
    public String getType() {
        return null;
    }
}
