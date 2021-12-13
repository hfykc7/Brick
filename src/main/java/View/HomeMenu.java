/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
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

import Controller.GameController;
import Model.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * This is HomeMenu class that show home menu page
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to";
    private static final String GAME_TITLE = "BRICK DESTROY";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "START";
    private static final String SCORE_TEXT = "SCOREBOARD";
    private static final String INFO_TEXT = "INFO";
    private static final String QUIT_TEXT = "QUIT";

    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color CLICKED_BUTTON_COLOR = Color.GREEN;
    private static final Color CLICKED_TEXT = Color.LIGHT_GRAY;

    private final Font greetingsFont;
    private final Font gameTitleFont;
    private final Font creditsFont;
    private final Font buttonFont;

    private final Rectangle menuFace;
    private final Rectangle startButton;
    private final Rectangle scoreButton;
    private final Rectangle infoButton;
    private final Rectangle quitButton;

    private final GameController owner;

    private boolean startClicked;
    private boolean scoreClicked;
    private boolean infoClicked;
    private boolean quitClicked;

    Image picture;

    /**
     * This method is the constructor for HomeMenu
     * @param owner GameController
     * @param area dimension size
     */
    public HomeMenu(GameController owner, Dimension area){

        picture = Toolkit.getDefaultToolkit().getImage("src/main/resources/brick.jpeg");

        Sound bgm = new Sound();
        bgm.playSound("Sound/bgm.wav");

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 2, area.height / 11);
        startButton = new Rectangle(btnDim);
        scoreButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
        quitButton = new Rectangle(btnDim);

        greetingsFont = new Font("DialogInput",Font.PLAIN,20);
        gameTitleFont = new Font("CabbagetownStd",Font.BOLD,40);
        creditsFont = new Font("DialogInput",Font.PLAIN,10);
        buttonFont = new Font("CooperHewitt-Book",Font.PLAIN,startButton.height-2);
    }

    /**
     * This method is to paint the HomeMenu by calling drawMenu method
     * @param g graphics
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * draw the menu face of HomeMenu
     * @param g2d graphics2D object
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);
        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * draw the background image and border for HomeMenu
     * @param g2d graphics2D object
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(tmp);
        g2d.setColor(prev);
        g2d.drawImage(picture,0,0,this);
    }

    /**
     * draw the texts on HomeMenu
     * @param g2d graphics2D object
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.5;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);
    }

    /**
     * draw the Back button on HomeMenu
     * @param g2d graphics2D object
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D sTxtRect = buttonFont.getStringBounds(SCORE_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(QUIT_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.5);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 1.0);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.25;

        scoreButton.setLocation(x,y);

        x = (int)(scoreButton.getWidth() - sTxtRect.getWidth()) / 2;
        y = (int)(scoreButton.getHeight() - sTxtRect.getHeight()) / 2;

        x += scoreButton.x;
        y += scoreButton.y + (scoreButton.height * 1.0);

        if(scoreClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(scoreButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(SCORE_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(scoreButton);
            g2d.drawString(SCORE_TEXT,x,y);
        }

        x = scoreButton.x;
        y = scoreButton.y;

        y *= 1.2;

        infoButton.setLocation(x,y);

        x = (int)(infoButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - iTxtRect.getHeight()) / 2;

        x += infoButton.x;
        y += infoButton.y + (infoButton.height * 1.0);

        if(infoClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);
        }

        x = infoButton.x;
        y = infoButton.y;

        y *= 1.2;

        quitButton.setLocation(x,y);

        x = (int)(quitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(quitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += quitButton.x;
        y += quitButton.y + (startButton.height * 1.0);

        if(quitClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(quitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(QUIT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(quitButton);
            g2d.drawString(QUIT_TEXT,x,y);
        }

        x = quitButton.x;
        y = quitButton.y;

        y *= 1.2;
    }

    /**
     * implement this method when mouse is clicked
     * @param mouseEvent mouse is clicked or not
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
        else if(scoreButton.contains(p)){
            owner.enableScoreBoard();
        }
        else if(infoButton.contains(p)){
            owner.enableInfo();
        }
        else if(quitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    /**
     * implement this method when mouse is pressed
     * @param mouseEvent mouse action
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(scoreButton.contains(p)){
            scoreClicked = true;
            repaint(scoreButton.x,scoreButton.y,scoreButton.width+1,scoreButton.height+1);
        }
        else if(infoButton.contains(p)){
            infoClicked = true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
        else if(quitButton.contains(p)){
            quitClicked = true;
            repaint(quitButton.x,quitButton.y,quitButton.width+1,quitButton.height+1);
        }
    }

    /**
     * implement this method when mouse is released
     * @param mouseEvent mouse is acted or not
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(scoreClicked){
            scoreClicked = false;
            repaint(scoreButton.x,scoreButton.y,scoreButton.width+1,scoreButton.height+1);
        }
        else if(infoClicked){
            infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
        else if(quitClicked){
            quitClicked = false;
            repaint(quitButton.x,quitButton.y,quitButton.width+1,quitButton.height+1);
        }
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
     * implement this method when mouse is moved
     * @param mouseEvent mouse is acted or not
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || scoreButton.contains(p) || infoButton.contains(p) || quitButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }
}