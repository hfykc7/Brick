package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CementBrickTest{

    public static final int RIGHT = 20;
    private final Point point = new Point(150,300);
    private final Dimension size = new Dimension(450, 300);
    CementBrick cementBrick = new CementBrick(point, size);

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point,size),cementBrick.makeBrickFace(point,size));
    }

    @Test
    void setImpact() {
        assertFalse(cementBrick.setImpact(point,RIGHT));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(point,size),cementBrick.getBrick());
    }

    @Test
    void repair() {
        assertFalse(cementBrick.isBroken());
    }
}