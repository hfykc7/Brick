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
package View;

import Controller.DebugConsole;
import Controller.FileController;
import Controller.GameController;
import Model.GameModel;
import Model.Ball;
import Model.Brick;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.io.IOException;

/**
 * This is GameBoard class to display the game
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class GameView extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private static final String CONTINUE = "CONTINUE";
    private static final String RESTART = "RESTART";
    private static final String BACK = "BACK TO MAIN";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = Color.ORANGE;
    private static final Color PAUSE_COLOR = Color.WHITE;

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;

    private final GameModel wall;

    private String message;

    private boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;
    private GameController owner;

    private final DebugConsole debugConsole;

    /**
     * make the game board
     * @param owner the game frame
     */
    public GameView(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("SansSerif",Font.PLAIN,TEXT_SIZE);

        this.owner = (GameController) owner;

        this.initialize();
        message = "";
        wall = new GameModel(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,6/2,3,new Point(300,430));

        debugConsole = new DebugConsole(owner,wall,this);
        //initialize the first level
        wall.nextLevel();

        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            //display values of bricks,balls and score
            message = String.format("Bricks: %d Balls: %d Score: %d",wall.getBrickCount(),wall.getBallCount(),wall.getScoreCount());

            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.wallReset();
                    //display game over and score added
                    message = "Game over!  Score: " + wall.getScoreCount();

                    String name = JOptionPane.showInputDialog("Enter your Name: ");

                    try {
                        FileController.appendToFileName(name);
                        FileController.appendToFile(wall.getScoreCount());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                wall.resetScoreCount();
                wall.ballReset();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "All walls destroyed!  Score: " + wall.getScoreCount();

                    String name = JOptionPane.showInputDialog("Enter your Name: ");

                    try {
                        FileController.appendToFileName(name);
                        FileController.appendToFile(wall.getScoreCount());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    wall.resetScoreCount();
                    wall.ballReset();
                    gameTimer.stop();
                }
            }
            repaint();
        });
    }

    /**
     * initialize GameView, add key, mouse, mouseMotionListener
     *
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * draw the GameBoard in the game screen
     * @param g graphic
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.player,g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * set background colour of the game
     * @param g2d Graphics2D
     */
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    /**
     * draw the brick in the game
     * @param brick brick
     * @param g2d Graphics2D
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    /**
     * draw the ball to the game
     * @param ball ball
     * @param g2d Graphics2D
     */
    private void drawBall(Ball ball, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * draw the player paddle's shape and set its inner and border colour
     * @param p player
     * @param g2d Graphics2D
     */
    private void drawPlayer(Player p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * call  obscureGameBoard and drawPauseMenu to draw the pause menu screen
     * @param g2d Graphics2D
     */
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * draw the pause menu container and set its colours
     * manage the transparency of the screen
     * @param g2d Graphics2D
     */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * draw the fonts, colour of the strings shown in the pause menu screen
     * draw continue,restart and back to main menu button
     * @param g2d Graphics2D
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(PAUSE_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);
        g2d.setColor(MENU_COLOR);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;

        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);
        g2d.setColor(MENU_COLOR);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);
        g2d.setColor(MENU_COLOR);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(BACK,x,y);
        g2d.setColor(MENU_COLOR);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);

        y *= 1.2;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * set what to act when a specific key is pressed
     * @param keyEvent to detect if key has been pressed
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_LEFT:
                wall.player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                wall.player.moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.player.stop();
        }
    }

    /**
     * detect when the key is not pressed to stop the player paddle's movement
     * @param keyEvent to detect if key has been pressed
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.player.stop();
    }

    /**
     * detect if mouse is clicked on either buttons
     * @param mouseEvent to detect if mouse has been clicked
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p))
            owner.enableHomeMenu();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * set how the cursor of mouse look when it moves to any buttons in the pause menu
     * @param mouseEvent to detect if mouse has been clicked
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     * set what will be shown when the game lost focus
     */
    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }
}