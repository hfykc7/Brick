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

import Model.Ball;

import java.awt.*;

/**
 * This is Player class. Create player bar and implement operations of player bar.
 *
 * @author Cheo Kai Wen
 * @version 1.5
 * @since 2021
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

    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        setMoveAmount(0);
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    public void move(){
        double x = ballPoint.getX() + getMoveAmount();
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    public void moveLeft(){
        setMoveAmount(-DEF_MOVE_AMOUNT);
    }

    public void moveRight(){
        setMoveAmount(DEF_MOVE_AMOUNT);
    }

    public void stop(){
        setMoveAmount(0);
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * @return move amount
     */
    public int getMoveAmount() {
        return moveAmount;
    }

    /**
     * @param moveAmount set the move amount
     */
    public void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }
}