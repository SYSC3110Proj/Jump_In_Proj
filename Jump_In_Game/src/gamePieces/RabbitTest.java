package gamePieces;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Tiantian Lin
 */

public class RabbitTest {

	@Test
	/** Test the constructor of rabbit
	 * 
	 */
	
	public void testRabbit() {
		Rabbit instance = new Rabbit(1,2,"rabbit1");
		 assertEquals(1,instance.getRow());
		 assertEquals(2,instance.getColumn());
		 assertEquals("rabbit1",instance.getName());
	}

}

