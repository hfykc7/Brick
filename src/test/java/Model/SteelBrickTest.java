package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    public static final int RIGHT = 20;
    private final Point point = new Point(150,300);
    private final Dimension size = new Dimension(450, 300);
    SteelBrick steelBrick = new SteelBrick(point, size);

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point,size),steelBrick.makeBrickFace(point,size));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(point,size),steelBrick.getBrick());
    }

    @Test
    void setImpact() {
        assertFalse(steelBrick.setImpact(point,RIGHT));
    }

    @Test
    void impact() {
        steelBrick.impact();
        assertFalse(steelBrick.isBroken());
    }
}