package gamePieces.tests;

import static org.junit.Assert.*;


import org.junit.Test;

import gamePieces.GridPoint;
import gamePieces.Rabbit;


/**
 * The RabbitTest class contains tests for all the methods in the rabbit class.
 * @author Tiantian Lin
 */

public class RabbitTest {

	@Test
	/** Test the constructor of rabbit
	 * 
	 */
	public void testRabbit() {
		Rabbit instance = new Rabbit(new GridPoint(1,2),"rabbit1");
		assertEquals(1,instance.getRow());
		assertEquals(2,instance.getColumn());
		assertEquals("rabbit1",instance.getName());
	}
	
	public void testatHole() {
		
		Rabbit instance = new Rabbit(new GridPoint(0,0),"rabbit1");
		assertEquals(true,instance.atHole());
	}
	public void testequal() {
		Rabbit instance = new Rabbit(new GridPoint(0,0),"rabbit1");
		Rabbit instance2 = new Rabbit(new GridPoint(0,0),"rabbit1");
		assertEquals(true,instance.equals(instance2));
		
		Rabbit instance3 = new Rabbit(new GridPoint(1,0),"rabbit1");
		assertEquals(false,instance.equals(instance2));
	}


}