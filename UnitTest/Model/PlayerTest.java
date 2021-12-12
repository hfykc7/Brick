package Model;

import org.junit.jupiter.api.Test;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Point ballpoint = new Point(250, 300);
    Player player = new Player(ballpoint, 100, 5, new Rectangle(20, 15));

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
}