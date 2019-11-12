package gamePieces.tests;

import static org.junit.Assert.assertEquals;

import gamePieces.Direction;
import gamePieces.Fox;
import gamePieces.GridPoint;
import gamePieces.NewFox;

public class NewFoxTest {

	/**
	 * Test the constructor of NewFox that uses two points
	 */
	public void NewFoxTwoPointConstructorTest() {
		NewFox instance = new NewFox(new GridPoint(1, 1), new GridPoint(1, 2));
		assertEquals(1, instance.getHead().getRow());
		assertEquals(1, instance.getHead().getCol());
		assertEquals(1, instance.getTail().getRow());
		assertEquals(2, instance.getTail().getCol());
	}
	
	/**
	 * Test the constructor for Fox with the head point and a direction
	 */
	public void NewFoxOnePointDirectionConstructorTest() {
		NewFox instance = new NewFox(new GridPoint(3, 0), Direction.WEST);
		assertEquals(3, instance.getHead().getRow());
		assertEquals(0, instance.getHead().getCol());
		assertEquals(3, instance.getTail().getRow());
		assertEquals(1, instance.getTail().getCol());
	}
	
	/**
	 * Test the direction that a fox is facing after being built with two points
	 */
	public void NewFoxTwoPointOrientationTest() {
		NewFox instance = new NewFox(new GridPoint(1, 1), new GridPoint(1, 2));
		assertEquals(Direction.EAST, instance.getOrientation());
	}

}
