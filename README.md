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
Author: Craig Worthington
* XMLHanlder.java: Used to store/load games to/from XML files   
Author: Ruixuan Ni
* BuilderWindow.java: generate a gui allow user to customize a game and save it afterwords   
Author: Zewen Chen

Test code:
* BoardTest.java:Test of Board class
* NewFoxTest.java:Test of Fox class
* RabbitTest.java:Test of Rabbit class
* GridPointTest.java:Test of GridPoint class 
* RecordTest.java:Test of Record class
* TilePlayBoardTest.java:Test of TilePlayBoard class. 
* XMLHandlerTest.java:Test of XMLHandler class. 
Author:Tiantian Lin

UML Diagram:     
Author:Zewen Chen



## Execution Instructions
### 1. Start the game:
Start the game by running the Control.java file

There are three default games, player can press "start with" and choose a default game
### 2. Play the game
To move pieces, just select the pieces and then click the desitinate square
pieces would move to the desitination if possible

if you want to save current state, just press file->save, the saved board can be load to view by file->load

you can redo/undo your movements by press game->redo/undo

if you want to see the solution, just press game->get solution, the detailed steps would be shown on the text field under the board

## Changes and Design
1. To save/load files, we created a new class XMLHandler, and use this class to transfer board to XML files/load XML files to set new boards. In XML files, we saved the row and column of each pieces
2. Remove a large amount of duplicate codes in Controller
3. Add new functions in gui
4. Remove dupulicate and unused method among pieces class and TilePlayBoard class avoid hard coding
5. Make gamepieces setable in TilePlayBoard class and edit the solver-test to make differrent puzzles' gamepieces visable
  
## Known Issues
The jar file should be put in file with game1.xml, game2.xml, game3.xml, or it won't be able to load default games.


￼
