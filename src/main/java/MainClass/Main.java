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
package MainClass;

import Controller.GameController;

import java.awt.*;
import java.io.IOException;

/**
 *This is Main class for Brick Destroy game
 *
 * @author Cheo Kai Wen
 * @version 2.0
 * @since 2021
 */
public class Main{

    /**
     * main method to run the game
     * @param args string array argument
     */
    public static void main(String[] args){

        EventQueue.invokeLater(() -> {
            try {
                new GameController().initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}