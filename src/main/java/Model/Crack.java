package Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is Crack class that create crack on the brick
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class Crack{

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    public static Random rnd = new Random();

    private final GeneralPath crack;

    private final int crackDepth;
    private final int steps;

    /**
     * determine the depth of the crack formed on the brick
     * @param crackDepth depth of the crack formed on the brick
     * @param steps steps before the brick breaks
     */
    public Crack(int crackDepth, int steps){
        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }

    /**
     * draw the brick
     * @return crack on the brick
     */
    public GeneralPath draw(){
        return crack;
    }

    /**
     * reset the crack
     */
    public void reset(){
        crack.reset();
    }

    /**
     * draw the crack of the brick
     * @param point point of the impact
     * @param direction direction of the impact
     * @param bricks brick
     */
    protected void makeCrack(Point2D point, int direction, Brick bricks){
        Rectangle bounds = bricks.brickFace.getBounds();

        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();

        switch(direction){
            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;
        }
    }

    /**
     * @param start starting point of impact
     * @param end ending point of impact
     */
    protected void makeCrack(Point start,Point end){

        GeneralPath path = new GeneralPath();

        path.moveTo(start.x,start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps;i++){

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i,CRACK_SECTIONS,steps))
                y += jumps(jump,JUMP_PROBABILITY);

            path.lineTo(x,y);
        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);
    }

    /**
     * make ball bounce back in random direction
     * @param bound bound
     * @return random integer
     */
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    private boolean inMiddle(int i,int steps,int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);
        return  (i > low) && (i < up);
    }

    /**
     * @param bound
     * @param probability
     * @return
     */
    private int jumps(int bound,double probability){

        if(rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

    /**
     * make a random point for the crack
     * @param from from one point
     * @param to to one point
     * @param direction direction of the random point, either horizontal or vertical direction
     * @return position of random point
     */
    private Point makeRandomPoint(Point from,Point to, int direction){

        Point out = new Point();
        int pos;

        switch(direction){
            case HORIZONTAL:
                pos = rnd.nextInt(to.x - from.x) + from.x;
                out.setLocation(pos,to.y);
                break;
            case VERTICAL:
                pos = rnd.nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x,pos);
                break;
        }
        return out;
    }
}