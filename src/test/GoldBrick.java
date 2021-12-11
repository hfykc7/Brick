package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class GoldBrick extends Brick {

    private static final String NAME = "Gold Brick";
    private static final Color DEF_INNER = new Color(255, 215, 0);//gold
    private static final Color DEF_BORDER = new Color(170, 108, 57);//dark gold
    private static final int GOLD_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    public GoldBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,GOLD_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }


    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
