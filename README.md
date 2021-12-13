# Software Maintenance Coursework #

 ## Brick_Destroy ##
This is a simple arcade video game.
Goal of the game is to destroy a wall with a small ball.


## How to Play : ##

Press <kbd>SPACE</kbd> : Start / Pause the game<br>
Press <kbd>Left</kbd> : Move paddle to Left<br>
Press <kbd>Right</kbd> : Move paddle to Right<br>
Press <kbd>ESC</kbd> : Enter / Exit pause menu<br>
Press <kbd>ALT</kbd> + <kbd>SHITF</kbd> + <kbd>F1</kbd> : Open console<br>

*The game automatically pause if the frame loses focus*

## Refactoring ##
1. Implemented MVC Design Pattern in the game.
- Split classes into Model, View, Controller packages.
- Create mainGame package for main class.

2. Rename classes
- Wall -> GameModel
- GameBoard -> GameView
- GameFrame -> GameController

3. Extract class from GameModel
- The original GameModel class has too many instructions, so i split the code related to crack into a new class
- Extracted crack related code into Crack class.

4. Improve code presentation
- Removed extra lines and blank space
- Comment on all methods for better understanding of the code

5. Build file using Maven

6. JUnit Test on some classes
- CementBrickTest
- ClayBrickTest
- GoldBrickTest
- PlayerTest
- RubberBallTest
- SteelBrickTest

## Addition ##
1. Added ScoreBoard
- ScoreBoard displays the past scores by player
- Input page will show up when game over, 

2. Added Info page
- Info displays the command keys to play the game

3. Added additional levels and one different Brick type
- More levels available for the player to play
- When level increases, the bricks are harder and will crack before breaking
 
4. Added Background music and Background image
- To make the game look more interesting and attractive

## Documentation ##
1. README file
2. Included Javadocs
- All javadocs file can be accessed in the JavaDocs folder
3. Class diagram
4. Github commit history
5. Github commit
Github project url : https://github.com/hfykc7/Brick

