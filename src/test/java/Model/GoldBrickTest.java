package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GoldBrickTest {

    public static final int RIGHT = 20;
    private final Point point = new Point(150,300);
    private final Dimension size = new Dimension(450, 300);
    GoldBrick goldBrick = new GoldBrick(point, size);

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point,size),goldBrick.makeBrickFace(point,size));
    }

    @Test
    void setImpact() {
        assertFalse(goldBrick.setImpact(point,RIGHT));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(point,size),goldBrick.getBrick());
    }

    @Test
    void repair() {
        assertFalse(goldBrick.isBroken());
    }
}