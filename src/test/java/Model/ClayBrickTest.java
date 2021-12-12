package Model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {

    private final Point point = new Point(150,300);
    private final Dimension size = new Dimension(450, 300);
    ClayBrick clayBrick = new ClayBrick(point, size);


    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(point,size),clayBrick.makeBrickFace(point,size));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(point,size),clayBrick.getBrick());
    }
}