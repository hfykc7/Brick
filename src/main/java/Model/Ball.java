package Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * This is Ball class that contains logics and properties of the ball
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
abstract public class Ball {

    private Shape ballFace;

    private final Point2D center;

    public Point2D up;
    public Point2D down;
    public Point2D left;
    public Point2D right;

    private final Color border;
    private final Color inner;

    private int speedX;
    private int speedY;

    /**
     *
     * This is constructor method for Ball class
     * @param center point of center of the ball
     * @param radiusA horizontal radius
     * @param radiusB vertical radius
     * @param inner inner colour of the ball
     * @param border border colour of the ball
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());

        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     * This method is to make the ball face
     * @param center point of center of the ball
     * @param radiusA horizontal radius
     * @param radiusB vertical radius
     * @return return shape of the ball with parameters specified
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * update the position of the ball to move the ball
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);

        ballFace = tmp;
    }

    /**
     * set the speed of the ball
     * @param x set the horizontal speed of the ball
     * @param y set the vertical speed of the ball
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * set the ball speed on the x axis
     * @param s is the new horizontal speed of the ball
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * set the ball speed on the y axis
     * @param s is the new  speed of vertical the ball
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * reverse the horizontal speed of the ball
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * reverse the vertical speed of the ball
     */
    public void reverseY(){
        speedY *= -1;
    }


    /**
     * @return return border colour of the ball
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * @return return inner colour of the ball
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * get position of the ball center
     * @return return x and y coordinates of the ball center
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * get ball face of the ball
     * @return return the shape of ball face
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * move the ball back to its original position
     * @param p is the point where the position of the ball is before start moving
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * set position of the ball
     * @param width width of the ball
     * @param height height of the ball
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * get the horizontal speed of the ball
     * @return return the horizontal speed of the ball
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * get the vertical speed of the ball
     * @return return the vertical speed of the ball
     */
    public int getSpeedY(){
        return speedY;
    }
}
