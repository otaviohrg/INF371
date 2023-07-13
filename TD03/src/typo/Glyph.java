package typo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;


public class Glyph extends Box {
    final private static FontRenderContext frc
            = new FontRenderContext(null, false, false);
    final private Font font;
    final private char[] chars;
    final private Rectangle2D bounds;

    public Glyph(Font font, char c) {
        this.font = font;
        this.chars = new char[]{c};
        TextLayout layout = new TextLayout(String.valueOf(chars[0]), font, frc);
        bounds = layout.getBounds();
    }

    @Override
    public double getStretchingCapacity() {
        return 0;
    }

    @Override
    public double getWidth() {
        return bounds.getWidth();
    }

    @Override
    public double getAscent() {
        return -bounds.getY();
    }

    @Override
    public double getDescent() {
        return bounds.getHeight()+bounds.getY();
    }

    @Override
    public boolean doDraw(Graphics graph, double x, double y, double w) {
        graph.setFont(font);
        graph.drawChars(chars, 0,chars.length, (int)(x-bounds.getX()), (int)(y-bounds.getY()));
        return true;
    }

    public String toString() {
        return String.format("Glyph(%s)", chars[0]) + super.toString();
    }
}