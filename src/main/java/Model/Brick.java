package Model;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is Brick class to create Brick
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 *
 */
abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    protected Shape brickFace;

    public static Random rnd;

    private final Color border;
    private final Color inner;

    private final int fullStrength;
    private int strength;

    private boolean broken;


    /**
     * This is the constructor for Brick to initialise the variables
     * @param name name of the brick
     * @param pos position of the brick
     * @param size size of the brick
     * @param border border colour of the brick
     * @param inner inner colour of the brick
     * @param strength strength of the brick
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
        broken = false;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
    }

    /**
     * @return brickFace of the brick
    */

    public Shape BrickFace(){
        return brickFace;
    }

    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * @param point point of impact
     * @param dir direction of the impact
     * @return boolean value that represents the brick is broken or not
     */
    public  boolean setImpact(Point2D point, int dir){
        if(broken)
            return false;
        impact();
        return broken;
    }

    public abstract Shape getBrick();

    /**
     * @return border colour of the brick
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * @return inner colour of the brick
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * @param b <- ball
     * @return position of ball impact on the brick
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * @return boolean value that represents the brick is broken or not
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * repair broken brick
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * decrease strength and check the brick is broken or not
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }
}