package gamePieces.tests;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * @author ZewenChen
 *
 */

import gamePieces.Board;
import gamePieces.GridPoint;
import gamePieces.PieceType;
import gamePieces.Tile;
import gamePieces.Token;
import junit.framework.TestCase;

public class BoardTest extends TestCase {
	public Board board;
	public GridPoint point1;
	public GridPoint point2;
	public Tile tile;
	public Tile tile2;
	public Token token;
	public void setUp() {
		this.board= new Board();
		this.point1=new GridPoint(1,1);
		this.point2=new GridPoint(2,2);
		this.token=new Token(point1,PieceType.RABBIT );
		this.tile=new Tile(point1, token,true);
		this.tile=new Tile(point1, token,true);
		board.setTileAt(point1, tile);
	}
	@Test
	public void testGetTileAtGridPoint() {
		assertEquals("get correct point",tile,board.getTileAt(point1));
	}

	@Test
	public void testGetTileAtIntInt() {
		assertEquals("get correct point",tile,board.getTileAt(1,1));
	}

	@Test
	public void testSetTileAtGridPointTile() {
		board.setTileAt(point2, tile2);
		assertEquals("point setted",tile2,board.getTileAt(2, 2));
	}

	@Test
	public void testSetTileAtIntIntTile() {
		board.setTileAt(2,2, tile2);
		assertEquals("point setted",tile2,board.getTileAt(2,2));
	}

}
