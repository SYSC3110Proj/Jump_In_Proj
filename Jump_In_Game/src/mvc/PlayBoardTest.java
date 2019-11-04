package mvc;


import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Direction;
import gamePieces.Fox;
import gamePieces.Rabbit;

/**
 * @author Tiantian Lin
 */

public class PlayBoardTest {

	@Test
	/**
	 * Test isWin() method of the playboard class
	 */
	
	public void testIsWin() {
		PlayBoard instance = new PlayBoard();
		instance.setRabbit(1, 0, 0);
		instance.setRabbit(2, 2, 2);
		instance.setRabbit(3, 4, 4);
		assertEquals(true,instance.isWin());
		
		PlayBoard instance1 = new PlayBoard();
		instance1.setRabbit(1, 0, 0);
		instance1.setRabbit(2, 2, 2);
		instance1.setRabbit(3, 2, 3);
		assertEquals(false,instance1.isWin());
		
		
		
	}

	@Test
	/**
	 * Test getFox() method of the playboard class
	 */
	public void testGetFox() {
		PlayBoard instance = new PlayBoard();
		
		assertEquals("fox1",instance.getFox("fox1")[0].getName());
		assertEquals("fox1",instance.getFox("fox1")[1].getName());
		
		assertEquals("fox2",instance.getFox("fox2")[0].getName());
		assertEquals("fox2",instance.getFox("fox2")[1].getName());
		
		
		
		
	}

	@Test
	/**
	 * Test getRabbit() method of the playboard class
	 */
	public void testGetRabbit() {
		PlayBoard instance = new PlayBoard();
		assertEquals("rabbit1",instance.getRabbit("rabbit1").getName());
		assertEquals("rabbit2",instance.getRabbit("rabbit2").getName());
		assertEquals("rabbit3",instance.getRabbit("rabbit3").getName());
		assertEquals(null,instance.getRabbit("rabbit"));
	
	}
	

	@Test
	/**
	 * Test setRabbit() method of the playboard class
	 */
	public void testSetRabbit() {
		PlayBoard instance = new PlayBoard();
		instance.setRabbit(1, 2, 3);
		assertEquals(2,instance.getRabbit("rabbit1").getRow());
		assertEquals(3,instance.getRabbit("rabbit1").getColumn());
	}

	@Test
	/**
	 * Test setFox() method of the playboard class
	 */
	public void testSetFox() {
		PlayBoard instance = new PlayBoard();
		
		instance.setFox(1, Direction.HORIZONTAL);
		assertEquals(1,instance.getFox("fox1")[0].getColumn());
		assertEquals(1,instance.getFox("fox1")[1].getColumn());
		
		instance.setFox(3, Direction.VERTICAL);
		assertEquals(3,instance.getFox("fox2")[0].getRow());
		assertEquals(3,instance.getFox("fox2")[1].getRow());
		
		
	}

	
	
	@Test
	/**
	 * Test jumpTo() method of the playboard class
	 */
	public void testJumpTo() {
		PlayBoard instance = new PlayBoard();
		//test the rabbit name is invalid
		assertEquals(false,instance.jumpTo(instance.getRabbit("rabbit"), Direction.EAST));
	
		Rabbit r1 = instance.getRabbit("rabbit1");
		Rabbit r2 = instance.getRabbit("rabbit2");
		Rabbit r3 = instance.getRabbit("rabbit3");
		
		//test the rabbit1 can not move to north due to the constructor of position
		assertEquals(false,instance.jumpTo(r1, Direction.NORTH));
		//test move rabbit1 to other positions
		
		assertEquals(true,instance.jumpTo(r1, Direction.SOUTH));
		assertEquals(false,instance.jumpTo(r1, Direction.WEST));
		assertEquals(true,instance.jumpTo(r1, Direction.NORTH));
		assertEquals(false,instance.jumpTo(r1, Direction.EAST));
		
		
	}

	@Test
	/**
	 * Test getFoxLocation() method of the playboard class
	 */
	public void testGetFoxLocation() {
		PlayBoard instance = new PlayBoard();
		
		assertEquals(1,instance.getFoxLocation(instance.getFox("fox1")));
		assertEquals(1,instance.getFoxLocation(instance.getFox("fox2")));
		//assertEquals(0,instance.getFoxLocation(null));
		
		
	}

	@Test
	/**
	 * Test moveTo() method of the playboard class
	 */
	public void testMoveTo() {
		PlayBoard instance = new PlayBoard();
		
		 Fox[] f1= instance.getFox("fox1");
		 assertEquals(true,instance.moveTo(f1,2));
		 assertEquals(false,instance.moveTo(f1,3));
		 assertEquals(false,instance.moveTo(f1,5));
		 
		 
		 
		 
	}




}
