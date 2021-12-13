package Model;

import java.awt.*;
import java.awt.Point;

/**
 * This is ClayBrick class which inherits from Brick Class
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 *
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.DARK_GRAY;
    private static final int CLAY_STRENGTH = 1;

    /**
     * This is the constructor method for ClayBrick
     * @param point point of the brick
     * @param size dimension of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * create brick face
     * @param pos position of the brick
     * @param size size of the brick
     * @return return clay brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * get the brickFace to show the clay brick
     * @return return the brickFace which is clay brick
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }
}