package Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This is GoldBrick class which inherits from Brick Class
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 *
 */
public class GoldBrick extends Brick {

    private static final String NAME = "Gold Brick";
    private static final Color DEF_INNER = new Color(255, 215, 0);//gold
    private static final Color DEF_BORDER = new Color(170, 108, 57);//dark gold
    private static final int GOLD_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;

    /**
     * This is the constructor method for GoldBrick
     * @param point point of the brick
     * @param size dimension of the brick
     */
    public GoldBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,GOLD_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * create brick face
     * @param pos position of the brick
     * @param size size of the brick
     * @return return gold brick
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
     * get the brickFace to show the gold brick
     * @return return the brickFace which is gold brick
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