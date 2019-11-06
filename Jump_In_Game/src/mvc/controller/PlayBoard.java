package mvc.controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import gamePieces.Direction;
import gamePieces.Fox;
import gamePieces.PieceType;
import gamePieces.Rabbit;
import gamePieces.RabbitColour;
import gamePieces.Square;

/** 
 * The PlayBoard class allows for a default setup as well as the repositionin of pieces on the board, contains the method
 * to check whether the game is won or not, and lastly has all the methods needed to play the game, such as move pieces.
 * @author Ruixuan Ni
 */

public class PlayBoard {
	private Square board[][];	// Format: board[col][row]
//	private Rabbit r1, r2, r3; //3 rabbits
	private Fox[] f1, f2;  //2 foxes
	
	private ArrayList<Rabbit> rabbits;
	private ArrayList<Fox> foxes;
	//private int cmushroom; //3 mushroom

	/**
	 * Constructor for PlayBoard class
	 */
	public PlayBoard() {
		board = new Square[5][5];
		
		this.rabbits = new ArrayList<Rabbit>(3);
		
		for (int i = 0; i < 5; i++) {//row
			for (int j = 0; j < 5; j++) {//column
				board[i][j] = new Square(i,j); //(row,column)
			}
		}
		
		
		// set Hole
		board[0][0].setName("Hole");
		board[0][4].setName("Hole");
		board[2][2].setName("Hole");
		board[4][0].setName("Hole");
		board[4][4].setName("Hole");
		
		board[0][0].setPieceType(PieceType.HOLE);
		board[0][4].setPieceType(PieceType.HOLE);
		board[2][2].setPieceType(PieceType.HOLE);
		board[4][0].setPieceType(PieceType.HOLE);
		board[4][4].setPieceType(PieceType.HOLE);

		//default gameboard
		setRabbit(1,0,3);
		setRabbit(2,2,4);
		setRabbit(3,4,1);
		
		setFox(1, Direction.VERTICAL);
		setFox(3, Direction.HORIZONTAL);
		
		board[1][3].setName("Mushroom");
		board[4][2].setName("Mushroom");
		
		board[1][3].setPieceType(PieceType.MUSHROOM);
		board[4][2].setPieceType(PieceType.MUSHROOM);
		
	}
	
	public Square getSquareAt(Point pt) {
		return this.board[pt.y][pt.x];
	}


	/**
	 * Test for win condition by checking if all rabbits are in holes
	 * @return True if all rabbits are in holes, false otherwise
	 */
	public boolean isWin() {
		return rabbits.get(0).atHole() && rabbits.get(1).atHole() && rabbits.get(2).atHole();
	}

	/**
	 * Retrieve a fox object
	 * @param i The index of the fox to be retrieved
	 * @return the fox object at index i
	 */
	public Fox[] getFox(String str) {
		if (str.equals("fox1")) {
			return f1;
		} else if (str.equals("fox2")) {
			return f2;
		}
		return null;
	}

	/**
	 * Retrieve a rabbit
	 * @param i The index of the rabbit to retrieve
	 * @return A Square object that represents the rabbit at index i
	 */
	public Rabbit getRabbit(String str){
		if (str.equals("rabbit1")) {
			return rabbits.get(0);
		} else if (str.equals("rabbit2")) {
			return rabbits.get(1);
		} else if (str.equals("rabbit3")) {
			return rabbits.get(2);
		}
		return null;
	}
	
	/**
	 * Retrieve a rabbit
	 * @param i The index of the rabbit to retrieve
	 * @return A Square object that represents the rabbit at index i
	 */
	public Rabbit getRabbit(RabbitColour colour){
		if (colour.equals(RabbitColour.BROWN)) {
			return rabbits.get(0);
		} else if (colour.equals(RabbitColour.BROWN)) {
			return rabbits.get(1);
		} else if (colour.equals(RabbitColour.GREY)) {
			return rabbits.get(2);
		} else {
			return null;
		}
	}
	
	/**
	 * Set the location of a rabbit on the board
	 * @param x The x coordinate of the rabbit location
	 * @param y The y coordinate of the rabbit location
	 * @return True if the rabbit was successfully created
	 */
	public void setRabbit(int i, int x, int y) {	
		if (i == 1) {
			rabbits.add(new Rabbit(x, y, "rabbit"+i));
			board[x][y] = rabbits.get(0);
		} else if(i == 2) {
			rabbits.add(new Rabbit(x, y, "rabbit"+i));
			board[x][y] = rabbits.get(1);
		} else if(i == 3) {
			rabbits.add(new Rabbit(x, y,"rabbit"+i));
			board[x][y] = rabbits.get(2);
		} 
	}

	/**
	 * Set the fox helper
	 * @param x
	 * @param direction
	 * @return
	 */
	private Fox[] setFoxHelper(int x, Direction direction) {
		int empty = 0;
		Fox[] temp = new Fox[2];

		if (direction.equals(Direction.HORIZONTAL)) {
			for (int j = 0; j < 5; j++) { // find if there are two empty board connected to put a fox in
				if (empty < 1) {
					if (!board[x][j].isOccupied()) {
						empty++;
					} else {
						empty = 0;
					}
				} else if (!board[x][j].isOccupied()){
					temp[0] = new Fox(x, j, Direction.HORIZONTAL);
					temp[1] = new Fox(x, j - 1, Direction.HORIZONTAL);
					board[x][j] = temp[0];
					board[x][j-1] = temp[1];
					return temp;
				}
			}
		} else if (direction.equals(Direction.VERTICAL)) {
			for (int i = 0; i < 5; i++) {
				if (empty < 1){
					if (!board[i][x].isOccupied()) {
						empty++;
					} else {
						empty = 0;
					}
				} else if (!this.board[i][x].isOccupied()) {
					temp[0] = new Fox(i, x, Direction.VERTICAL);
					temp[1] = new Fox(i - 1, x, Direction.VERTICAL);
					board[i][x] = temp[0];
					board[i-1][x] = temp[1];
					return temp;
				}
			}
		}
		throw new IllegalArgumentException("cannot set fox there");
	}

	/**
	 * Set the location of a fox on the board
	 * @param x The x coordinate of the fox
	 * @param direction The direction of the fox
	 * @return True if the fox was successfully created
	 */
	public void setFox(int x, Direction direction) {
		if (x == 0 || x == 2 || x == 4) {
		}

		if (f1 == null) {
			f1 = setFoxHelper(x, direction);
			f1[0].setName("fox1");
			f1[1].setName("fox1");
		} else if(f2 == null) {
			f2 = setFoxHelper(x, direction);
			f2[0].setName("fox2");
			f2[1].setName("fox2");
		}
	}

	/**
	 * Move a square to a new location
	 * @param s The square to move
	 * @param x The x coordinate of the new location
	 * @param y The y coordinate of the new location
	 */
	private void move(Square s, int x, int y) {
		int row = s.getRow();
		int col = s.getColumn();
		
		if (!(x == row && y == col) && x > -1 && x < 5 && y > -1 && y < 5) {
			board[x][y] = s;
			board[row][col] = new Square(row, col);
			if (s.atHole()) {
				board[row][col].setName("Hole");
			}
			s.move(x, y);
		}
	}
	
	/**
	 * Move a square to a new location
	 * @param rabbit the rabbit to be moved
	 * @param Point the new point for the rabbit to move to
	 */
	public void moveRabbit(Rabbit rabbit, Point point) {
		// NOTE: board is [row][col] aka [y][x]
		Rectangle rect = new Rectangle(0, 0, 5, 5);
		
		int currRow = rabbit.getRow();
		int currCol = rabbit.getColumn();
		
		System.out.println("currRow = " + currRow + ", currCol = " + currCol);
		System.out.println("Point (x,y) = (" + point.x + "," + point.y + ")");
		
		System.out.println(board[2][4].getPieceType());
		
		
		if (rect.contains(point) && !(point.x == currCol && point.y == currRow)) {
			board[point.y][point.x] = rabbit;
			board[currRow][currCol] = new Square(currRow, currCol);
			
			if (rabbit.atHole()) {
				board[currRow][currCol].setName("Hole");
			}
			rabbit.move(point.y, point.x);	// set the internal location of the square
		}
	}
	
	/**
	 * Test if a rabbit can jump in a certain direction
	 * @param r The rabbit to be moved
	 * @param direction The direction to test the jump
	 * @return True if the rabbit is able to jump in that direction
	 */
	public boolean canJumpIn(Rabbit r, Direction direction) {
		if (r == null || direction == null) {
			return false;
		}

		// get rabbit's location
		int row = r.getRow();
		int col = r.getColumn();

		if (direction.equals(Direction.NORTH)) {
			if (row > 0 && this.board[row-1][col].isOccupied()) {	// check if rabbit can move upwards, and if the space north of the rabbit is occupied
				for (int i = 0; i <= row; i++) {
					if (board[row-i][col].isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board[row+1][col].isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board[row+i][col].isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board[row][col+1].isOccupied()) {
				for (int j = 0; j < 5-col; j++) {
					if (board[row][col+j].isOccupied() == false) {
						return true;
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board[row][col-1].isOccupied()) {
				for (int j = 0; j <= col; j++) {
					if (board[row][col-j].isOccupied() == false) {
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	/**
	 * Return the nearest point that the rabbit can jump to in a certain direction
	 * @param rabbit the rabbit to be moved
	 * @param direction the direction in which the rabbit will move
	 * @return Point where the rabbit can jump to
	 */
	public Point getNearestJumpPoint(Rabbit rabbit, Direction direction) {
		// get rabbit's location
		int row = rabbit.getRow();
		int col = rabbit.getColumn();

		if (direction.equals(Direction.NORTH)) {
			if (row > 0 && this.board[row-1][col].isOccupied()) {
				for (int i = 0; i <= row; i++) {
					if (board[row-i][col].isOccupied() == false) {
						return new Point(col, row-i);
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board[row+1][col].isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board[row+i][col].isOccupied() == false) {
						return new Point(col, row+i);
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board[row][col+1].isOccupied()) {
				for (int j = 0; j < 5-col; j++) {
					if (board[row][col+j].isOccupied() == false) {
						return new Point(col+j, row);
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board[row][col-1].isOccupied()) {
				for (int j = 0; j <= col; j++) {
					if (board[row][col-j].isOccupied() == false) {
						return new Point(col-j, row);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Move a rabbit to a new location
	 * @param r The rabbit to move
	 * @param direction The direction that the rabbit will jump
	 * @return True if the move was successful
	 */
	public Point jumpTo(Rabbit r, Direction direction) {
		
		// get rabbit's location
		int row = r.getRow();
		int col = r.getColumn();

		if (direction.equals(Direction.NORTH)) {
			if (row > 0 && this.board[row-1][col].isOccupied()) {
				for (int i = 0; i <= row; i++) {
					if (board[row-i][col].isOccupied() == false) {
						move(r, row-i, col);
						return new Point(col, row-i);
					}
				}
			}
		} else if (direction.equals(Direction.SOUTH)) {
			if (row < 4 && this.board[row+1][col].isOccupied()) {
				for (int i = 0; i < 5-row; i++) {
					if (board[row+i][col].isOccupied() == false) {
						move(r, row+i, col);
						return new Point(col, row+i);
					}
				}
			}
		} else if (direction.equals(Direction.EAST)) {
			if (col < 4 && board[row][col+1].isOccupied()) {
				for (int j = 0; j < 5-col; j++) {
					if (board[row][col+j].isOccupied() == false) {
						move(r, row, col+j);
						return new Point(col+j, row);
					}
				}
			}
		} else if (direction.equals(Direction.WEST)) {
			if (col > 0 && board[row][col-1].isOccupied()) {
				for (int j = 0; j <= col; j++) {
					if (board[row][col-j].isOccupied() == false) {
						move(r, row, col-j);
						return new Point(col-j, row);
					}
				}
			}
		}
		return null;

	}

	/**
	 * Get the location of a fox
	 * @param f The fox to find the location of
	 * @return The row or column where the fox is located
	 */
	public int getFoxLocation(Fox[] f) {
		if (f[0].getDirection().equals(Direction.HORIZONTAL)) {
			return f[0].getColumn();
		} else if (f[0].getDirection().equals(Direction.VERTICAL)) {
			return f[0].getRow();
		} else {
			return 0;
		}
	}

	/**
	 * Move a fox to a new point
	 * @param f The fox to be moved
	 * @param point the point to move the fox to
	 * @return true if the move was successful
	 */
	public boolean moveTo(Fox[] f, int point) {
		//get fox's location
		Fox head = f[0];
		int row = head.getRow();
		int col = head.getColumn();
		
		if(point > 4) point = 4;
		if(point < 0) point = 0;

		if (f[0].getDirection().equals(Direction.HORIZONTAL)) {
			if (point > col && !board[row][point].isOccupied() && 
					!(board[row][point-1].isOccupied() && !board[row][point-1].getName().equals(f[1].getName()))) {
				move(f[0], row, point);
				move(f[1], row, point-1);
				return true;
			} 
			else if(point < col && !board[row][point].isOccupied() &&
					!(board[row][point+1].isOccupied() && !board[row][point+1].getName().equals(f[1].getName()))){
				move(f[1], row, point);
				move(f[0], row, point+1);
				return true;
			}
			else return false;			
		}
		else if(head.getDirection().equals(Direction.VERTICAL)) {
			if (point > row && !board[point][col].isOccupied() &&
					!(board[point-1][col].isOccupied() && !board[point-1][col].getName().equals(f[1].getName()))) {
				move(f[0], point, col);
				move(f[1], point-1, col);
				return true;
			} 
			else if(point < row && !board[point][col].isOccupied() && 
					!(board[point+1][col].isOccupied()&& !board[point+1][col].getName().equals(f[1].getName()))){
				move(f[1], point, col);
				move(f[0], point+1, col);
				return true;
			}
			else return false;	
		}
		else return false;
	}
	
	public String[][] getBoardName(){
		String[][] name = new String[5][5];
		
		for(int r=0; r<5; r++) {
			for(int c=0; c<5; c++) {
				name[r][c] = board[r][c].toString();
			}
		}
		
		return name;
	}
}

