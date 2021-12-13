package Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This is CementBrick class which inherits from Brick Class
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 *
 */
public class CementBrick extends Brick {
    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = Color.DARK_GRAY;
    private static final int CEMENT_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;


    /**
     *This is the constructor method for CementBrick
     * @param point point of the brick
     * @param size  dimension of the brick
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * create brick face
     * @param pos position of the brick
     * @param size size of the brick
     * @return return cement brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * This method is called when the ball hits the brick
     * @param point point of the impact
     * @param dir direction of the impact
     * @return return boolean
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir,this);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * get the brickFace to show the cement brick
     * @return return the brickFace which is cement brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * draw a crack if it is cracked and update the brickFace to add the crack
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * repair the brick and remove cracks on it
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}