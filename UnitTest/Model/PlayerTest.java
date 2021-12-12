package Model;

import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Point point = new Point(150,300);
    Player player = new Player(new Point (12,2),10,5,new Rectangle(20,15));


    @Test
    void moveTo() {
        player.moveTo(new Point(200,350));
        Point playerPos = new Point(200-(10/2),350);
        assertEquals(new Point(195,350),playerPos);
    }

    @Test
    void moveLeft() {

    }

    @Test
    void moveRight() {
    }

    @Test
    void stop() {
    }

    @org.junit.jupiter.api.Test
    void getPlayerFace() {
    }

    @org.junit.jupiter.api.Test
    void getMoveAmount() {
    }

    @org.junit.jupiter.api.Test
    void setMoveAmount() {
    }
}