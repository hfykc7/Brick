/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2021  Cheo Kai Wen
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Model;

import java.awt.*;

/**
 * This is Player class to create player bar and implement operations of the player bar
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 */

public class Player {

    public static final Color BORDER_COLOR = Color.GRAY;
    public static final Color INNER_COLOR = Color.LIGHT_GRAY;

    private static final int DEF_MOVE_AMOUNT = 5;

    private final Rectangle playerFace;
    private final Point ballPoint;

    private int moveAmount;
    private final int min;
    private final int max;

    /**
     * This is the constructor for Player class
     * @param ballPoint point where the ball locate on the rectangle
     * @param width width of the rectangle
     * @param height  height of the rectangle
     * @param container window of the game
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        setMoveAmount(0);
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    /**
     * This method is to make a rectangle
     * @param width  width of the rectangle
     * @param height  height of the rectangle
     * @return rectangle
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * This method is to check the impact of the ball
     * @param b object for ball
     * @return boolean value
     */
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * set location of the player
     */
    public void move(){
        double x = ballPoint.getX() + getMoveAmount();
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * move the player in left direction
     */
    public void moveLeft(){
        setMoveAmount(-DEF_MOVE_AMOUNT);
    }

    /**
     *  move player in right direction
     */
    public void moveRight(){
        setMoveAmount(DEF_MOVE_AMOUNT);
    }

    /**
     * stop player moving
     */
    public void stop(){
        setMoveAmount(0);
    }

    /**
     * get shape of the player
     * @return shape of the player
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * @param p point where the player locate before start moving
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * get the move amount of the player
     * @return move amount of the player
     */
    public int getMoveAmount() {
        return moveAmount;
    }

    /**
     * @param moveAmount set the move amount of the player
     */
    public void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }
}