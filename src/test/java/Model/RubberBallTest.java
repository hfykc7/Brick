package Model;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class RubberBallTest {

    private static final int DEF_RADIUS = 10;
    private final Point2D center = new Point2D.Double(300,430);
    RubberBall rubberBall = new RubberBall(center);

    @Test
    void makeBall() {
        assertEquals(rubberBall.getBallFace(), rubberBall.makeBall(center, DEF_RADIUS,DEF_RADIUS));
    }
}