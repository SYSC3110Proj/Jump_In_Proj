# Jump In Project
## Group Members:
- Mika Argyle
- Zewen Chen
- Tiantian Lin
- Ruixuan Ni
- Craig Worthington

## Deliverables

Source code:
* PlayBoard.java: Model for the game board  
Author:Ruixuan Ni
* View.java: The view of game of a game board  
Author:Zewen Chen,Craig Worthington
* Control.java: Main file of dealing with the clicking events delivered from view, and operating the play board  
Author:Ruixuan Ni,Craig Worthington
* Direction.java: Enum file for valid gamepiece movement and orientations  
Author:Ruixuan Ni
* Fox.java: Main object for Fox gamepiece  
Author: Mika Argyle
* Rabbit.java: Main object for Rabbit gamepiece  
Author: Mika Argyle
* Square.java: Main object for the each square on the game board  
Author: Zewen Chen

Test code:
* PlayBoardTest.java:Test of PlayBoard class
* FoxTest.java:Test of Fox class
* RabbitTest.java:Test of Rabbit class
* SquareTest.java:Test of Square class  
Author:Tiantian Lin

UML Diagram:     
Author:Zewen Chen



## Execution Instructions
### 1. Start the game:
Start the game by running the Control.java file

The game will start with a default layout
### 2. Play the game
To move pieces, just select the pieces and then click the desitinate square
pieces would move to the desitination if possible

If you want to restart the game, just click the restart button in the top menu bar
this button would set the whole game to its original statue

## Changes and Design
1. Refactor all the playboard (done by Craig Worthington)
2. To implement the undo and redo function, we created two stacks in FilePlayBoard class: one to record the movements made by player and ther other one recording the undo movements.
3. To implement the solver, we add a dfs package, including class Choice and DSF:   
  * create an arrayList for the possible move of each rabbits, like(rabbit1, north)
  * clone the TilePlayBoard as the playboard of solver, when a possibility is proved impossible, undo the move and return to last level.  
  * a stack is used to recording each movement to make sure when return to last level, the solver will continue to try other   possibilities instead of repeating trying to prove ways that are proved impossible.
  * boolean[][] is created for make sure that rabbits are not repeating meaningless movements.
  * The while loop will end when the solver is found, or there seems to have no solvent.

## Known Issues
