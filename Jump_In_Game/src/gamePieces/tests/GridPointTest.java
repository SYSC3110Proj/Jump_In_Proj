/**
 * 
 */
package gamePieces.tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import gamePieces.Direction;
import gamePieces.GridPoint;
import junit.framework.TestCase;

/**
 * @author ZewenChen
 *
 */
public class GridPointTest extends TestCase{
public GridPoint point;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.point=new GridPoint(1,1);
	}
	

	/**
	 * Test method for {@link gamePieces.GridPoint#getRow()}.
	 */
	@Test
	public void testGetRow() {
		assertEquals("get the row of this point",1,point.getRow());
	}

	/**
	 * Test method for {@link gamePieces.GridPoint#setRow(int)}.
	 */
	@Test
	public void testSetRow() {
		point.setRow(2);
		assertEquals("get the row of this point",2,point.getRow());
	}

	/**
	 * Test method for {@link gamePieces.GridPoint#getCol()}.
	 */
	@Test
	public void testGetCol() {
		assertEquals("get the col of this point",1,point.getCol());
	}

	/**
	 * Test method for {@link gamePieces.GridPoint#setCol(int)}.
	 */
	@Test
	public void testSetCol() {
		point.setCol(2);
		assertEquals("get the col of this point",point.getCol(),2);
	}

	/**
	 * Test method for {@link gamePieces.GridPoint#getDirectionTo(gamePieces.GridPoint)}.
	 */
	@Test
	public void testGetDirectionTo() {
		assertEquals("test Direction north",Direction.NORTH,point.getDirectionTo(new GridPoint(2,1)));
		assertEquals("test Direction south",Direction.SOUTH,point.getDirectionTo(new GridPoint(2,3)));
		assertEquals("test Direction north",Direction.WEST,point.getDirectionTo(new GridPoint(1,2)));
		assertEquals("test Direction north",Direction.EAST,point.getDirectionTo(new GridPoint(3,2)));
	}


}
