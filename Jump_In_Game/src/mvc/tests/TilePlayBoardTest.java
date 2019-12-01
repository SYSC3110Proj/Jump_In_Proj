package mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;
import gamePieces.Rabbit;
import mvc.controller.TilePlayBoard;

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

	

	

}