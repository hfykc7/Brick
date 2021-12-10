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
    private static final String START = "<SPACE> to Start / Pause";
    private static final String LEFT = "<A> to Move Left";
    private static final String RIGHT = "<D> to Move Right";
    private static final String PAUSE_MENU = "<ESC> to Go to Pause Menu";

    private static final Color BG_COLOR = Color.GREEN.brighter();
    private static final Color TEXT_COLOR = Color.WHITE;



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

