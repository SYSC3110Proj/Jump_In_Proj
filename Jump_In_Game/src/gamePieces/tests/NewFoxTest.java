package gamePieces.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;

public class NewFoxTest {

	

	@Test
	public void testNewFoxGridPointDirectionString() {
		NewFox instance = new NewFox(new GridPoint(3, 0), Direction.WEST,"fox1");
		assertEquals(3, instance.getHead().getRow());
		assertEquals(0, instance.getHead().getCol());
		assertEquals(3, instance.getTail().getRow());
		assertEquals(1, instance.getTail().getCol());
	}

	@Test
	public void testGetOrientation() {
		NewFox instance = new NewFox(new GridPoint(1, 1),Direction.EAST ,"fox1");
		assertEquals(Direction.EAST, instance.getOrientation());
	}

	@Test
	public void testEqualsObject() {
		NewFox instance = new NewFox(new GridPoint(3, 0), Direction.WEST,"fox1");
		NewFox instance2 = new NewFox(new GridPoint(3, 0), Direction.WEST,"fox1");
		
		assertEquals(true,instance.equals(instance2));
	}

}
