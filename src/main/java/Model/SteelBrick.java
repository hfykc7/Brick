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
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This is SteelBrick class which inherits from Brick Class
 *
 * @author Cheo Kai Wen
 * @version 1.0
 * @since 9/12/2021
 *
 */
public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private final Random rnd;
    private final Shape brickFace;

    /**
     * This is the constructor method for SteelBrick
     * @param point point of the brick
     * @param size dimension of the brick
     */
    public SteelBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }

    /**
     * create brick face
     * @param pos position of the brick
     * @param size size of the brick
     * @return return steel brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * get the brickFace to show the steel brick
     * @return return the brickFace which is steel brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * This method is called when the ball hits the brick
     * @param point point of the impact
     * @param dir direction of the impact
     * @return return boolean
     */
    public  boolean setImpact(Point2D point , Point dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

     /**
     * impact the ball on the steel brick
     */
    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }
}