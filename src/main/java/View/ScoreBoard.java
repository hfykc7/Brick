package View;

// Java Program to illustrate Reading from FileReader
// using BufferedReader Class

import Model.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class ScoreBoard extends JComponent implements MouseListener, MouseMotionListener{

    private static final String SCORE_TITLE = "Score Board";
    private static final String NAME = "Name";
    private static final String SCORES = "Scores";
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

    private GameFrame owner;

    private boolean backClicked;

    public ScoreBoard(GameFrame owner, Dimension area) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

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

    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

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

    private void drawText (Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D scoreTitleRect = scoreTitleFont.getStringBounds(SCORE_TITLE, frc);
        Rectangle2D nameRect = menuFont.getStringBounds(NAME, frc);
        Rectangle2D scoresRect = menuFont.getStringBounds(SCORES, frc);

        int sX, sY;

        sX = (int) (menuFace.getWidth() - scoreTitleRect.getWidth()) / 2;
        sY = (int) (menuFace.getHeight() / 5);

        g2d.setFont(scoreTitleFont);
        g2d.drawString(SCORE_TITLE, sX, sY);

        sX = (int)(menuFace.getWidth() - nameRect.getWidth()) / 5;
        sY += (int) nameRect.getHeight() * 1.4;

        g2d.setFont(menuFont);
        g2d.drawString(NAME,sX,sY);

        sX = (int)(menuFace.getWidth() - scoresRect.getWidth()) / 2;
        sY += (int) scoresRect.getHeight() * 0.1;

        g2d.setFont(menuFont);
        g2d.drawString(SCORES,sX,sY);
    }

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
/*
    // main driver method
    private void reader throws IO Exception{
        // File path is passed as parameter
        File file = new File("src/main/resources/score.txt");

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st;

        while ((st = br.readLine()) != null)

            // Print the string
            System.out.println(st);
    }

    /*
    private String displayScores() throws IOException {
        Integer[] scores = FileController.readFromFile();
        Arrays.sort(scores, Collections.reverseOrder());
        return Arrays.toString(Arrays.stream(scores).limit(scoresToPrint).toArray());
    }
*/

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (backButton.contains(p)) {
            owner.enableScoreHomeMenu();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (backButton.contains(p)) {
            backClicked = true;
            repaint(backButton.x, backButton.y, backButton.width + 1, backButton.height + 1);
        }
    }

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

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }
}