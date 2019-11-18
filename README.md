# Jump In Project
## Group Members:
- Mika Argyle
- Zewen Chen
- Tiantian Lin
- Ruixuan Ni
- Craig Worthington

## Deliverables

Source code:
* TilePlayBoard.java: Model for the game board  
Author:Ruixuan Ni
* View.java: The view of game of a game board  
Author:Zewen Chen,Craig Worthington
* Control.java: Main file of dealing with the clicking events delivered from view, and operating the play board  
Author:Ruixuan Ni,Craig Worthington
* Direction.java: Enum file for valid gamepiece movement and orientations  
Author:Ruixuan Ni
* NewFox.java: Main object for Fox gamepiece  
Author: Mika Argyle
* Rabbit.java: Main object for Rabbit gamepiece  
Author: Mika Argyle
* Tile.java: Main object for the each square on the game board  
Author: Zewen Chen
* Board.java: Main object for the each square on the game board  
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
3. To implement the solver, we created a tree:   
  
## Known Issues

## Test solved puzzles

Puzzles constructors in TilePlayBoard class distinguish by integer variable.  
There are three puzzles in solver test class passed sucessfully.
￼
￼
