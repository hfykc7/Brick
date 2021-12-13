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

import View.GameView;
import View.HomeMenu;
import View.Info;
import View.ScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

/**
 * This is GameController class
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class GameController extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameView gameBoard;
    private HomeMenu homeMenu;
    private Info info;
    private ScoreBoard scoreBoard;

    private boolean gaming;


    /**
     * This is the constructor for GameController
     * @throws IOException
     */
    public GameController()throws IOException {
        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameView(this);

        homeMenu = new HomeMenu(this,new Dimension(450,400));

        info = new Info(this,new Dimension(450,400));

        scoreBoard = new ScoreBoard(this,new Dimension(450,400));

        this.add(homeMenu,BorderLayout.CENTER);
        this.add(info,BorderLayout.CENTER);
        this.add(scoreBoard,BorderLayout.CENTER);

        this.setUndecorated(true);
    }

    /**
     * initialise title of the game
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     *  enable the game after clicking Start Button in HomeMenu
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }

    /**
     * enable the HomeMenu after clicking Back to Menu Button in Info screen
     */
    public void enableHomeMenu(){
        this.dispose();
        this.remove(info);
        this.remove(scoreBoard);
        this.remove(gameBoard);
        this.add(homeMenu,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }

    /**
     * enable the Info screen after clicking Info Button in HomeMenu
     */
    public void enableInfo(){
        this.dispose();
        this.remove(homeMenu);
        this.add(info,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }

    /**
     * enable the ScoreBoard screen after clicking ScoreBoard Button in HomeMenu
     */
    public void enableScoreBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.add(scoreBoard, BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        //to avoid problems with graphics focus controller is added here
        this.addWindowFocusListener(this);
    }

    /**
     *
     */
    public void enableScoreHomeMenu() {
        this.dispose();
        this.remove(scoreBoard);
        this.add(homeMenu, BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        //to avoid problems with graphics focus controller is added here
        this.addWindowFocusListener(this);
    }

    /**
     * set the location of game frame and screen size
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    /**
     * detect whether the window gains focus or not
     * @param windowEvent status of the window
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard has been displayed
            at least once
         */
        gaming = true;
    }

    /**
     * if window lost focus, display Focus Lost and stop timer
     * @param windowEvent status of the window
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();
    }
}