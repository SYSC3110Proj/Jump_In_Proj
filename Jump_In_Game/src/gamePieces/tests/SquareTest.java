package gamePieces.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Square;

/**
 * The SquareTest class contains tests for all the methods in the Square class.
 * @author Tiantian Lin
 */

public class SquareTest {

	@Test
	/**
	 * Test of Constructor:IllegalArgumentException
	 * column and row should less than 5 and greater than 0
	 */
	public void testSquareIntInt() {
		Square instance = new Square(2,3);
		assertEquals(2,instance.getRow());
		assertEquals(3,instance.getColumn());
		
		try {
			Square instance1 = new Square(5,3);
		    fail();
		} catch (IllegalArgumentException e) {}
		
		try {
			Square instance2 = new Square(3,6);
		    fail();
		} catch (IllegalArgumentException e) {}
		
	}
	
		

	@Test
	/**
	 * Test Constructor of the square class
	 */
	public void testSquareIntIntString() {
		Square instance = new Square(2,3,"fox1");
		assertEquals(2,instance.getRow());
		assertEquals(3,instance.getColumn());
		assertEquals("fox1",instance.getName());
	}

	@Test
	/**
	 * Test getRow() method of the square class
	 */
	public void testGetRow() {
		Square instance = new Square(2,3,"fox1");
		assertEquals(2,instance.getRow());
	}

	@Test
	/**
	 * Test getcolumn() method of the square class
	 */
	public void testGetColumn() {
		Square instance = new Square(2,3,"fox1");
		assertEquals(3,instance.getColumn());
	}

	@Test
	/**
	 * Test setName() method of the square class
	 */
	public void testSetName() {
		Square instance = new Square(2,3);
		instance.setName("fox1");
	}

	@Test
	/**
	 * Test getName() method of the square class
	 */
	public void testGetName() {
		Square instance = new Square(2,3,"fox1");
		assertEquals("fox1",instance.getName());
	}

	@Test
	/**
	 * Test atHole() method of the square class
	 * test the five holes at the play board
	 */
	public void testAtHole() {
		Square instance = new Square(2,3);
		assertEquals(false,instance.atHole());
		Square hole1 = new Square(0,0);
		Square hole2 = new Square(0,4);
		Square hole3 = new Square(4,0);
		Square hole4 = new Square(4,4);
		Square hole5 = new Square(2,2);
		assertEquals(true,hole1.atHole());
		assertEquals(true,hole2.atHole());
		assertEquals(true,hole3.atHole());
		assertEquals(true,hole4.atHole());
		assertEquals(true,hole5.atHole());
	}

	@Test
	/**
	 * Test Move() method of the square class
	 * the method will throw IllegalArgumentException if the number of row or column is invalid
	 * column and row should less than 5 and greater than 0
	 */
	public void testMove() {
		Square instance = new Square(2,3);
		try {
			instance.move(0,5);
		    fail();
		} catch (IllegalArgumentException e) {}
		
		Square instance1 = new Square(3,3);
		
		try {
			instance1.move(5,0);
		    fail();
		} catch (IllegalArgumentException e) {}
		
		Square instance2 = new Square(3,2);
		instance2.move(2,2);
		
		assertEquals(2,instance2.getRow());
		assertEquals(2,instance2.getColumn());
	}

	@Test
	/**
	 * Test isOccupied() method of the square class
	 * if the square has fox or rabbit, return true, else return false
	 */
	public void testIsOccupied() {
		Square instance = new Square(2,3,"fox1");
		assertEquals(true,instance.isOccupied());
		
		Square instance1 = new Square(0,3);
		assertEquals(false,instance1.isOccupied());
	}

	@Test
	/**
	 * Test toString() method of the square class
	 * if the square has fox or rabbit, return true, else return false
	 */
	public void testToString() {
		Square instance = new Square(2,3);
		assertEquals(null,instance.toString());
		
		Square instance1 = new Square(3,3,"fox1");
		assertEquals("fox1",instance1.toString());
	}

}
