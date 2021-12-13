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
package Controller;

import Model.Ball;
import Model.GameModel;
import View.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This is DebugConsole class that make debug console
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";

    private JFrame owner;
    private DebugPanel debugPanel;
    private GameView gameBoard;
    private GameModel wall;

    /**
     * constructor of debugConsole
     * @param owner the frame
     * @param wall gamemodel
     * @param gameBoard gameview
     */
    public DebugConsole(JFrame owner, GameModel wall, GameView gameBoard){

        this.wall = wall;
        this.owner = owner;
        this.gameBoard = gameBoard;
        initialize();

        debugPanel = new DebugPanel(wall);
        this.add(debugPanel,BorderLayout.CENTER);

        this.pack();
    }

    /**
     * initialize the variables of debug console
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }

    /**
     * set location of debug console
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * implement when window closing
     * @param windowEvent window is detected or not
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameBoard.repaint();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }


    /**
     * implement when window activated
     * @param windowEvent window is detected or not
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = wall.ball;
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
