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

6. JUnit Test
- CementBrickTest
- ClayBrickTest
- GoldBrickTest
- PlayerTest
- RubberBallTest
- SteelBrickTest

