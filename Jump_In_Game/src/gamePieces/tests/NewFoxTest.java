package gamePieces;

import static org.junit.Assert.*;

import org.junit.Test;

public class NewFoxTest {

	@Test
	public void testNewFoxGridPointGridPointString() {
		NewFox instance = new NewFox(new GridPoint(1, 1), new GridPoint(1, 2),"fox1");
		assertEquals(1, instance.getHead().getRow());
		assertEquals(1, instance.getHead().getCol());
		assertEquals(1, instance.getTail().getRow());
		assertEquals(2, instance.getTail().getCol());
	}

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
