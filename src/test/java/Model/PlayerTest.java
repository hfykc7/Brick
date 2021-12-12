package Model;

import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Point ballpoint = new Point(250, 300);
    Player player = new Player(ballpoint, 100, 5, new Rectangle(20, 15));

    private Point2D center = new Point2D.Double(300,430);
    RubberBall rubberball = new RubberBall(center);

    @Test
    void impact() {
        assertFalse(player.impact(rubberball));
    }

    @Test
    void move() {
        player.move();
        assertEquals(200, 250 - 100 / 2);
    }

    @Test
    void moveLeft() {
        player.moveLeft();
        assertEquals(-5, player.getMoveAmount());
    }

    @Test
    void moveRight() {
        player.moveRight();
        assertEquals(5, player.getMoveAmount());
    }

    @Test
    void stop() {
        player.stop();
        assertEquals(0, player.getMoveAmount());
    }

    @Test
    void moveTo() {
        player.moveTo(new Point(200, 350));
        Point playerPos = new Point(200 - (100 / 2), 350);
        assertEquals(new Point(150, 350), playerPos);
    }

    @Test
    void getPlayerFace() {
        Rectangle playerFace = new Rectangle(200,300,100,5);
        assertEquals(playerFace, player.getPlayerFace());
    }
}