package mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.GridPoint;
import gamePieces.PieceType;
import gamePieces.Token;
import mvc.controller.Record;

/**
 * The RecordTest class contains tests for all the methods in the Record class.
 * @author Tiantian Lin
 */

public class RecordTest {

	
	@Test
	public void testGetPiece() {
		Token rabbit = new Token(new GridPoint(1,2),PieceType.RABBIT);
		Record instance = new Record(rabbit,new GridPoint(1,2),new GridPoint(2,2));
		assertEquals(PieceType.RABBIT,instance.getPiece().getPieceType());
	}

	@Test
	public void testGetLastLoc() {
		Token rabbit = new Token(new GridPoint(1,2),PieceType.RABBIT);
		Record instance = new Record(rabbit,new GridPoint(1,2),new GridPoint(2,2));
		assertEquals(1,instance.getLastLoc().getRow());
		assertEquals(2,instance.getLastLoc().getCol());
		
	}

	@Test
	public void testGetNextLoc() {
		Token rabbit = new Token(new GridPoint(1,2),PieceType.RABBIT);
		Record instance = new Record(rabbit,new GridPoint(1,2),new GridPoint(2,2));
		assertEquals(2,instance.getNextLoc().getRow());
		assertEquals(2,instance.getNextLoc().getCol());
	}

	
	
}