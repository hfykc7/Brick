package View;

import Controller.FileController;
import Controller.GameController;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * This is ScoreBoard class that display the name and score of the players
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 */
public class ScoreBoard extends JComponent implements MouseListener, MouseMotionListener{

    private static final String SCORE_TITLE = "Score Board";
    private static final String NAME = "Name";
    private static final String SCORES = "Score";
    private static final String BACK_TEXT = "BACK TO MENU";

    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color CLICKED_BUTTON_COLOR = Color.GREEN;
    private static final Color CLICKED_TEXT = Color.DARK_GRAY;
    private static final Color BG_COLOR = Color.DARK_GRAY.darker();
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font scoreTitleFont;
    private Font menuFont;
    private Font textFont;
    private Font buttonFont;

    private Rectangle menuFace;
    private Rectangle backButton;

    private GameController owner;

    private int scoreToPrint = 20;
    private String nameText;
    private String scoreText;
    private boolean backClicked;

    /**
     * make the score board page
     * @param owner GameController
     * @param area dimension
     * @throws IOException
     */
    public ScoreBoard(GameController owner, Dimension area) throws IOException {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        nameText = displayName();
        scoreText = displayScore();

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 2, area.height / 11);
        backButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        scoreTitleFont = new Font("CabbagetownStd", Font.BOLD, 40);
        menuFont = new Font("Noto Mono",Font.BOLD,25);
        textFont = new Font("SansSerif", Font.BOLD, 20);
        buttonFont = new Font("CooperHewitt-Book", Font.BOLD, backButton.height - 7);
    }

    /**
     * This method is to paint the ScoreBoard by calling drawMenu method
     * @param g graphics
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * draw the menu face of ScoreBoard
     * @param g2d graphics2D object
     */
    public void drawMenu (Graphics2D g2d){

        drawContainer(g2d);

        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x, y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x, -y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * draw the background and border for Scoreboard
     * @param g2d graphics2D object
     */
    private void drawContainer (Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);
        g2d.setColor(prev);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    /**
     * draw the texts on ScoreBoard
     * @param g2d graphics2D object
     */
    private void drawText (Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D scoreTitleRect = scoreTitleFont.getStringBounds(SCORE_TITLE, frc);
        Rectangle2D nameRect = menuFont.getStringBounds(NAME, frc);
        Rectangle2D scoresRect = menuFont.getStringBounds(SCORES, frc);

        int sX, sY;

        sY = (int) (menuFace.getHeight() / 6);
        sX = (int) (menuFace.getWidth() - scoreTitleRect.getWidth()) / 2;
        sY += (int) scoreTitleRect.getHeight() * 1.1;

        g2d.setFont(scoreTitleFont);
        g2d.drawString(SCORE_TITLE, sX, sY);

        g2d.setFont(menuFont);
        g2d.drawString(NAME,120,150);

        g2d.setFont(menuFont);
        g2d.drawString(SCORES,sX,sY);

        g2d.setFont(menuFont);
        g2d.drawString(SCORES,250,150);

        g2d.setColor(Color.WHITE);
        g2d.setFont(menuFont);
        g2d.drawString(nameText,120,180);

        g2d.setColor(Color.WHITE);
        g2d.setFont(menuFont);
        g2d.drawString(scoreText,260,180);
    }

    /**
     * draw the Back button on ScoreBoard
     * @param g2d graphics2D object
     */
    private void drawButton (Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D btxtRect = buttonFont.getStringBounds(BACK_TEXT, frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - backButton.width) / 2;
        int y = (int) ((menuFace.height - backButton.height) * 0.9);

        backButton.setLocation(x, y);

        x = (int) (backButton.getWidth() - btxtRect.getWidth()) / 2;
        y = (int) (backButton.getHeight() - btxtRect.getHeight()) / 2;

        x += backButton.x;
        y += backButton.y + (backButton.height * 0.9);

        if (backClicked) {
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(backButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(BACK_TEXT, x, y);
            g2d.setColor(tmp);
        }
        else {
            g2d.draw(backButton);
            g2d.drawString(BACK_TEXT, x, y);
        }

        x = backButton.x;
        y = backButton.y;

        y *= 1.2;
    }

    /**
     * display score result
     * @return score result in string array
     * @throws IOException
     */
    private String displayScore() throws IOException {
        Integer[] score = FileController.readFromFile();
        String result = StringUtils.join(score,"\n");

        return result;
    }


    /**
     * display name input by player
     * @return name input by player in string array
     * @throws IOException
     */
    private String displayName() throws IOException {
        String[] name = FileController.readFromFileName();
        String result = StringUtils.join(name,"\n");

        return result;
    }

    /**
     * implement this method when mouse is clicked
     * @param mouseEvent mouse is clicked or not
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (backButton.contains(p)) {
            owner.enableScoreHomeMenu();
        }
    }

    /**
     * implement this method when mouse is pressed
     * @param mouseEvent mouse action
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (backButton.contains(p)) {
            backClicked = true;
            repaint(backButton.x, backButton.y, backButton.width + 1, backButton.height + 1);
        }
    }

    /**
     * implement this method when mouse is released
     * @param mouseEvent mouse is acted or not
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (backClicked) {
            backClicked = false;
            repaint(backButton.x, backButton.y, backButton.width + 1, backButton.height + 1);
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
        if (backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }
}