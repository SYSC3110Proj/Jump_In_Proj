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
1.Delete the Command.java,CommandWord.java,Parser.java and Game.java:  
When using GUI, there is no need to read player's input value.   
2.Create Rabbit.java:     
Make each piece more clear in the code.   
3.Fix several bugs in PlayBoard.java.  
4.Make MVC structure for the gui:  
PlayBoard in this structure is a model, we add a view to show all the panel, menu and dialog we need, and add a control class to deal with all action events, determine which view should be shown in the JFrame.  

## Known Issues
