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
 * This is GameModel class
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class GameModel {

    private static final int LEVELS_COUNT = 6;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int GOLD = 4;

    private Random rnd;
    private Rectangle area;

    public Brick[] bricks;
    public Ball ball;
    public Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private int scoreCount;
    private boolean ballLost;

    /**
     *
     * This is constructor for GameModel
     * @param drawArea rectangle where the game is drawn
     * @param brickCount number of bricks
     * @param lineCount lines of bricks
     * @param brickDimensionRatio ratio of brick (width : height)
     * @param ballPos position of the ball (300, 430)
     */
    public GameModel(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        int speedX,speedY;

        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);

        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
    }

    /**
     * @param drawArea rectangle where the game is drawn
     * @param brickCnt number of bricks
     * @param lineCnt lines of bricks
     * @param brickSizeRatio ratio of brick (width : height)
     * @param type brick type
     * @return level with single brick type
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;
    }

    /**
     * @param drawArea rectangle where the game is drawn
     * @param brickCnt number of bricks
     * @param lineCnt lines of bricks
     * @param brickSizeRatio ratio of brick (width : height)
     * @param typeA first brick type
     * @param typeB second brick type
     * @return level with two types of brick
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    /**
     * make new rubber ball
     * @param ballPos position of the ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * create levels of the game
     * @param drawArea rectangle where the game is drawn
     * @param brickCount number of bricks
     * @param lineCount lines of bricks
     * @param brickDimensionRatio ratio of brick (width : height)
     */
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,GOLD);
        tmp[5] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,GOLD);
        return tmp;
    }

    /**
     * call the move methods in Player class and Ball class
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * if there is any impact,implement all ball and brick properties in the game
     */
    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
            scoreCount += 10;
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            scoreCount += 10;
            ballLost = true;
        }
    }

    /**
     * set what will occur when the ball makes impact with GameModel
     * @return boolean value that indicate whether the ball makes impact with GameModel
     */
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Crack.LEFT);
            }
        }
        return false;
    }

    /**
     * This method implements when ball makes impact with border of the game
     * @return boolean value
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * get the brick count
     * @return brick count
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * get the ball count
     * @return ball count
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * get the score count
     * @return score count
     */
    public int getScoreCount() {
        return scoreCount;
    }

    /**
     * reset the score count after game over
     * @return score count
     */
    public int resetScoreCount() {
        scoreCount = 0;
        return scoreCount;
    }

    /**
     * get the number of ball lost
     * @return boolean value of ballLost
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * reset the position of player and ball
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;

        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);

        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    /**
     * reset the brick count and ball count
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    /**
     * boolean value = 1 if ball count = 0
     * @return boolean value
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * boolean value = 1 if brick count = 0
     * @return boolean value
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * go to the next level and reset brickCount back to 31
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
        this.brickCount = 1;
    }

    /**
     * boolean value = 1 if there is remaining level
     * @return boolean value
     */
    public boolean hasLevel(){
        return level < levels.length;
    }

    /**
     * set the speed x of the ball
     * @param s integer value for speed x
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * set the speed y of the ball
     * @param s integer value for speed y
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * reset the ball count to 3
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * make brick according to the brick type
     * @param point point of the brick
     * @param size dimension of the brick
     * @param type type of the brick
     * @return new brick of the specified type
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            case GOLD:
                out = new GoldBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }
}