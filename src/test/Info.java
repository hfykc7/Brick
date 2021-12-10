package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Info extends JComponent implements MouseListener, MouseMotionListener {

    private static final String INFO_TITLE = "How to Play";
    private static final String START = "1. Press SPACE : Start / Pause the game";
    private static final String LEFT = "2. Press Left: Move paddle to Left";
    private static final String RIGHT = "3. Press Right : Move paddle to Right";
    private static final String PAUSE = "4. Press ESC : Enter / Exit pause menu";
    private static final String CONSOLE = "5. Press ALT + SHIFT + F1 : Open console";
    private static final String BACK_TEXT = "BACK TO MENU";


    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color CLICKED_BUTTON_COLOR = Color.DARK_GRAY;
    private static final Color CLICKED_TEXT = Color.GRAY;

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font infoTitleFont;
    private Font textFont;
    private Font buttonFont;

    private Rectangle menuFace;
    private Rectangle backButton;

    private GameFrame owner;

    private boolean backClicked;

    Image picture_1;

    public Info(GameFrame owner, Dimension area) {

        picture_1 = Toolkit.getDefaultToolkit().getImage("pic/greywall.jpeg");

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 2, area.height / 11);
        backButton = new Rectangle(btnDim);

        infoTitleFont = new Font("CabbagetownStd", Font.BOLD, 40);
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

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(tmp);
        g2d.setColor(prev);
        g2d.drawImage(picture_1,0,0,this);
    }

    private void drawText (Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D infoTitleRect = infoTitleFont.getStringBounds(INFO_TITLE, frc);
        Rectangle2D startRect = textFont.getStringBounds(START, frc);
        Rectangle2D leftRect = textFont.getStringBounds(LEFT, frc);
        Rectangle2D rightRect = textFont.getStringBounds(RIGHT, frc);
        Rectangle2D pauseRect = textFont.getStringBounds(PAUSE, frc);
        Rectangle2D consoleRect = textFont.getStringBounds(CONSOLE, frc);

        int sX, sY;

        sX = (int) (menuFace.getWidth() - infoTitleRect.getWidth()) / 2;
        sY = (int) (menuFace.getHeight() / 5);

        g2d.setFont(infoTitleFont);
        g2d.drawString(INFO_TITLE, sX, sY);

        sX = (int) (menuFace.getWidth() - startRect.getWidth()) / 2;
        sY += (int) startRect.getHeight() * 1.7;

        g2d.setFont(textFont);
        g2d.drawString(START, sX, sY);

        sX = (int) (menuFace.getWidth() - leftRect.getWidth()) / 2;
        sY += (int) leftRect.getHeight() * 1.4;

        g2d.setFont(textFont);
        g2d.drawString(LEFT, sX, sY);

        sX = (int) (menuFace.getWidth() - rightRect.getWidth()) / 2;
        sY += (int) rightRect.getHeight() * 1.4;

        g2d.setFont(textFont);
        g2d.drawString(RIGHT, sX, sY);

        sX = (int) (menuFace.getWidth() - pauseRect.getWidth()) / 2;
        sY += (int) pauseRect.getHeight() * 1.4;

        g2d.setFont(textFont);
        g2d.drawString(PAUSE, sX, sY);

        sX = (int) (menuFace.getWidth() - consoleRect.getWidth()) / 2;
        sY += (int) consoleRect.getHeight() * 1.4;

        g2d.setFont(textFont);
        g2d.drawString(CONSOLE, sX, sY);
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (backButton.contains(p)) {
            owner.enableHomeMenu();
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
