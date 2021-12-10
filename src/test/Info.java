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
    private static final String START = "1. Press <kbd>SPACE</kbd> : Start / Pause the game\n2. Press <kbd>Left</kbd> : Move paddle to Left\n3. Press <kbd>Right</kbd> : Move paddle to Right\n4. Press <kbd>ESC</kbd> : Enter / Exit pause menu\n5. Press <kbd>ALT</kbd> + <kbd>SHITF</kbd> + <kbd>F1</kbd> : Open console";
    //private static final String LEFT = "Press <kbd>Left</kbd> : Move paddle to Left\n";
    //private static final String RIGHT = "Press <kbd>Right</kbd> : Move paddle to Right\n";
    //private static final String PAUSE_MENU = "Press <kbd>ESC</kbd> : Enter / Exit pause menu\n";
    //private static final String CONSOLE = "Press <kbd>ALT</kbd> + <kbd>SHITF</kbd> + <kbd>F1</kbd> : Open console";
    private static final String BACK_TEXT = "BACK TO MENU";


    private static final Color BG_COLOR = Color.GREEN.brighter();
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(200, 8, 21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(255, 216, 0);//school bus yellow
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font infoTitleFont;
    private Font textFont;
    private Font buttonFont;

    private Rectangle menuFace;
    private Rectangle backButton;

    private GameFrame owner;

    private boolean backClicked;

    public Info(GameFrame owner) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 2, area.height / 11);
        backButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, DASHES, 0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        infoTitleFont = new Font("CabbagetownStd", Font.BOLD, 40);
        textFont = new Font("Helvetica", Font.PLAIN, 20);
        buttonFont = new Font("CooperHewitt-Book", Font.PLAIN, backButton.height - 2);


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

            //g2d.drawImage(pic, 0, 0, this);

            g2d.setColor(prev);
        }

        private void drawText (Graphics2D g2d){

            g2d.setColor(TEXT_COLOR);

            FontRenderContext frc = g2d.getFontRenderContext();

            Rectangle2D infoTitleRect = infoTitleFont.getStringBounds(INFO_TITLE, frc);
            Rectangle2D startRect = textFont.getStringBounds(START, frc);

            int sX, sY;

            sX = (int) (menuFace.getWidth() - infoTitleRect.getWidth()) / 2;
            sY = (int) (menuFace.getHeight() / 4);

            g2d.setFont(infoTitleFont);
            g2d.drawString(INFO_TITLE, sX, sY);

            sX = (int) (menuFace.getWidth() - startRect.getWidth()) / 2;
            sY += (int) startRect.getHeight() * 1.1;//add 10% of String height between the two strings

            g2d.setFont(textFont);
            g2d.drawString(START, sX, sY);
        }


        private void drawButton (Graphics2D g2d){

            FontRenderContext frc = g2d.getFontRenderContext();

            Rectangle2D btxtRect = buttonFont.getStringBounds(BACK_TEXT, frc);

            g2d.setFont(buttonFont);

            int x = (menuFace.width - backButton.width) / 2;
            int y = (int) ((menuFace.height - backButton.height) * 0.6);

            backButton.setLocation(x, y);

            x = (int) (backButton.getWidth() - btxtRect.getWidth()) / 2;
            y = (int) (backButton.getHeight() - btxtRect.getHeight()) / 2;

            x += backButton.x;
            y += backButton.y + (backButton.height * 0.3);

            if (backClicked) {
                Color tmp = g2d.getColor();
                g2d.setColor(CLICKED_BUTTON_COLOR);
                //g2d.draw(startButton);
                g2d.setColor(CLICKED_TEXT);
                g2d.drawString(BACK_TEXT, x, y);
                g2d.setColor(tmp);
            } else {
                //g2d.draw(startButton);
                g2d.drawString(BACK_TEXT, x, y);
            }

            x = backButton.x;
            y = backButton.y;

            y *= 1.2;
        }
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
