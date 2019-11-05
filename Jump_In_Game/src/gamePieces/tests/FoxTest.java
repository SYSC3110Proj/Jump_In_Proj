package gamePieces.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Direction;
import gamePieces.Fox;

/**
 * The FoxTest class contains tests for all the methods in the Fox class.
 * @author Tiantian Lin
 */

public class FoxTest {

	@Test
	/** Test the constructor of fox with name
	 * 
	 */
	public void testFoxIntIntDirectionString() {
		 Fox instance = new Fox(1,2,Direction.VERTICAL,"fox1");
		 assertEquals(1,instance.getRow());
		 assertEquals(2,instance.getColumn());
		 assertEquals("VERTICAL",instance.getDirection().name());
		 assertEquals("fox1",instance.getName());
	}

	@Test
	/** Test the constructor of fox without name
	 * 
	 */
	public void testFoxIntIntDirection() {
		Fox instance = new Fox(1,2,Direction.VERTICAL);
		assertEquals(1,instance.getRow());
		assertEquals(2,instance.getColumn());
		assertEquals("VERTICAL",instance.getDirection().name());
	}

	@Test
	/**
	 * Test of getDirection method, of class Fox
	 */
	public void testGetDirection() {
		Fox instance = new Fox(1,2,Direction.VERTICAL);
		assertEquals("VERTICAL",instance.getDirection().name());
	}

}
