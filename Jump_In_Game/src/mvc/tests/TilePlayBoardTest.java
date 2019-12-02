package mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Board;
import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;
import gamePieces.Rabbit;
import mvc.controller.TilePlayBoard;
import mvc.controller.XMLHandler;

/**
 * The TilePlayBoardTest class contains tests for all the methods in the TilePlayBoard class.
 * @author Tiantian Lin
 */

public class TilePlayBoardTest {

	
	
	@Test
	public void testSetFox() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setFox(new GridPoint(2,1), Direction.SOUTH);
		NewFox newFox = instance.getFoxAtLocation(new GridPoint(2,1));
		assertEquals(Direction.SOUTH,newFox.getOrientation());
	}

	@Test
	public void testMoveRabbit() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setRabbit(0, 3);
		instance.setMushroom(1, 3);
		Rabbit r1 = instance.getRabbit(0);
		instance.moveRabbit(r1, new GridPoint(2,3));
		assertEquals(2,r1.getRow());
		assertEquals(3,r1.getCol());
	}

	@Test
	public void testGetNearestJumpPoint() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setMushroom(1,3);
		instance.setRabbit(0,3);
		GridPoint p = instance.getNearestJumpPoint(instance.getRabbit(0), Direction.SOUTH);
		assertEquals(2,p.getRow());
		assertEquals(3,p.getCol());
		
	}

	@Test
	public void testTestValidFoxMove() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setFox(new GridPoint(2,1), Direction.SOUTH);
		assertFalse(instance.testValidFoxMove(instance.getFox(0), new GridPoint(1,2)));
		assertTrue(instance.testValidFoxMove(instance.getFox(0), new GridPoint(3,1)));
		
	}

	@Test
	public void testMoveFox() {
		TilePlayBoard instance = new TilePlayBoard();
	    instance.setFox(new GridPoint(2,1), Direction.SOUTH);
		NewFox fox = instance.getFox(0);
		instance.moveFox(fox, new GridPoint(2,1));
		assertEquals(2,instance.getFox(0).getHead().getRow());
		assertEquals(1,instance.getFox(0).getHead().getCol());
		
		
	}

	@Test
	public void testRedo() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setRabbit(0,3);
		instance.setMushroom(1,3);
		instance.moveRabbit(instance.getRabbit(0), new GridPoint(2,3));
		instance.undo();
		instance.redo();
		assertEquals(2,instance.getRabbit(0).getRow());
		assertEquals(3,instance.getRabbit(0).getCol());
		
	}

	@Test
	public void testUndo() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setRabbit(0,3);
		instance.setMushroom(1,3);
		instance.moveRabbit(instance.getRabbit(0), new GridPoint(2,3));
		instance.undo();
		assertEquals(0,instance.getRabbit(0).getRow());
		assertEquals(3,instance.getRabbit(0).getCol());
		
	}


	@Test
	public void testIsWin() {
		TilePlayBoard instance = new TilePlayBoard();
		assertEquals(false,instance.isWin());
	}

	@Test
	public void testGetRabbitNum() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setRabbit(1, 1);
		assertEquals(1,instance.getRabbitNum());
	}

	@Test
	public void testGetFoxNum() {
		TilePlayBoard instance = new TilePlayBoard();
		instance.setFox(new GridPoint(2,1), Direction.SOUTH);
		assertEquals(1,instance.getFoxNum());
	}

	/**
	 * Add two method test save(),load(),and resetboard()
	 * 
	 */
	
	
	
	
	/*
	  
	  H  *   *   *   H 
	  *  *   *   R1  *
	  *  f1  H   m1  R2
	  *  f1  *   f2  f2
	  H  R3  m2  *   H
	  
	  */
	@Test
	public void testSave() {
		
		TilePlayBoard board = new TilePlayBoard();
		board.setMushroom(2, 3);
		board.setMushroom(4, 2);
		board.setFox(new GridPoint(3,1),Direction.SOUTH);
		board.setFox(new GridPoint(3,4),Direction.EAST);
		board.setRabbit(1,3);
		board.setRabbit(2,4);
		board.setRabbit(4,1);
		
		//Rabbit r2 = board.getRabbit(1);
		//board.moveRabbit(r2, new GridPoint(2,2));
		board.save(board, "test");
		board.load("test");
		XMLHandler h1 = new XMLHandler(1);
		h1.setBoard(board);
		TilePlayBoard newboard = h1.getBoard();
		
		assertEquals(true,board.equals(newboard));
	}

	
	@Test
	public void testResetBoard() {
		
		TilePlayBoard board = new TilePlayBoard();
		board.setMushroom(2, 3);
		board.setMushroom(4, 2);
		board.setFox(new GridPoint(3,1),Direction.SOUTH);
		board.setFox(new GridPoint(3,4),Direction.EAST);
		board.setRabbit(1,3);
		board.setRabbit(2,4);
		board.setRabbit(4,1);
		
		Board reset = new Board();
		board.resetBoard(reset);
		board.getBoard();
		
		assertEquals(true,reset.equals(board.getBoard()));
		
	}


	



}