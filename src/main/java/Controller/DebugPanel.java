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

import Model.GameModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This is DebugPanel class that make debug panel
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 9/12/2021
 *
 */
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    /**
     * constructor of debugPanel
     * @param wall gameModel
     */
    public DebugPanel(GameModel wall){

        initialize();

        JButton skipLevel = makeButton("SKIP LEVEL", e -> wall.nextLevel());
        JButton resetBalls = makeButton("Reset Balls",e -> wall.resetBallCount());

        ballXSpeed = makeSlider(-4,4,e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(-4,4,e -> wall.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);
    }

    /**
     * initialize the variables of debug panel
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }


    /**
     * @param title
     * @param e
     * @return
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * make the slider of debug panel
     * @param min minimum value
     * @param max maximum value
     * @param e
     * @return
     */
    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * set values of ball speed
     * @param x ball speed x
     * @param y ball speed y
     */
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }
}