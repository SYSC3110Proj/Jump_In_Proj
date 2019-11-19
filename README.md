# Jump In Project
## Group Members:
- Mika Argyle
- Zewen Chen
- Tiantian Lin
- Ruixuan Ni
- Craig Worthington

## Deliverables

Source code:
* Board.java: Model for the game board  
Author:Ruixuan Ni
* View.java: The view of game of a game board  
Author:Zewen Chen,Craig Worthington
* Controller.java: Main file of dealing with the clicking events delivered from view, and operating the play board  
Author:Ruixuan Ni,Craig Worthington
* Direction.java: Enum file for valid gamepiece movement and orientations  
Author:Ruixuan Ni
* NewFox.java: Main object for Fox gamepiece  
Author: Mika Argyle
* Rabbit.java: Main object for Rabbit gamepiece  
Author: Mika Argyle
* Tile.java: Main object for the each square on the game board  
Author: Zewen Chen
* Token.java: Main object for each token on the game board  
* PieceType.java: contains all the possible types of pieces.
* GridButton.java: Main object for the grid buttons on the game board
* Record.java: Records the movements of a token

Test code:
* BoardTest.java:Test of Board class
* NewFoxTest.java:Test of Fox class
* RabbitTest.java:Test of Rabbit class
* GridPointTest.java:Test of GridPoint class 
* RecordTest.java:Test of Record class
* TilePlayBoardTest.java:Test of TilePlayBoard class.   
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
1. Refactor the game model to use a more object-oriented approach. The play board consists of a 5x5 grid of tiles, and the pieces are represented by "Tokens". Each tile contains its own location on the board, and the token object that occupies its space (if no token occupies the space, it is considered null). The Token class contains info about the type of piece it is (Mushroom, Fox, Rabbit), as well as its location. The PlayBoard class contains the board, as well as the business logic to check if any given move is valid. (done by Craig Worthington)
2. To implement the undo and redo function, we created two stacks in FilePlayBoard class: one to record the movements made by player and ther other one recording the undo movements.
3. To implement the solver, a breadth first search was used to iterate through all possible moves for a given board state in order to find the solution with the smallest number of possible moves.   
  
## Known Issues
The search algorithm is not as efficient as it could be, so for more difficult puzzles, it can take a long time to solve. 

## Test solved puzzles

Puzzles constructors in TilePlayBoard class are distinguished by integer variables.  
There are three puzzles in solver test class passed sucessfully.
￼
￼
