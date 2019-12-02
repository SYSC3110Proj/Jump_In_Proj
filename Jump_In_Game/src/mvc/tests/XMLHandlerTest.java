package mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.xml.sax.SAXException;

import gamePieces.Board;
import gamePieces.Direction;
import gamePieces.GridPoint;
import mvc.controller.TilePlayBoard;
import mvc.controller.XMLHandler;

public class XMLHandlerTest {

	

	@Test
	public void testImportXMLFile() throws Exception {
		
		XMLHandler h1 = new XMLHandler();
		
		TilePlayBoard board = new TilePlayBoard();
		board.setMushroom(2, 3);
		board.setMushroom(4, 2);
		board.setFox(new GridPoint(3,1),Direction.SOUTH);
		board.setFox(new GridPoint(3,4),Direction.EAST);
		board.setRabbit(1,3);
		board.setRabbit(2,4);
		board.setRabbit(4,1);
		
		
		h1.setBoard(board);
		h1.toXMLFile("test");
		h1.importXMLFileByName("test");
		
		
		XMLHandler h2 = new XMLHandler();
		h2.importXMLFileByName("test");
		
		
		assertEquals(true,board.getRabbits().get(0).equals(h2.getBoard().getRabbits().get(0)));
		assertEquals(true,board.getRabbits().get(0).equals(h2.getBoard().getRabbits().get(0)));
		assertEquals(true,board.getRabbits().get(0).equals(h2.getBoard().getRabbits().get(0)));
		assertEquals(true,board.getRabbit(1).equals(h2.getBoard().getRabbit(1)));
		assertEquals(true,board.getRabbit(2).equals(h2.getBoard().getRabbit(2)));
		assertEquals(true,board.getFox(0).equals(h2.getBoard().getFox(0)));
		assertEquals(true,board.getFox(1).equals(h2.getBoard().getFox(1)));
		assertEquals(true,board.getMushrooms().get(0).equals(h2.getBoard().getMushrooms().get(0)));
		assertEquals(true,board.getMushrooms().get(1).equals(h2.getBoard().getMushrooms().get(1)));
		
		//assertEquals(2,h2.getBoard().getFoxNum());
		//assertEquals(Direction.SOUTH,h2.getBoard().getFox(0).getOrientation());
		//assertEquals(true,board.equals(h1.getBoard()));
		//assertEquals(true,board.compare2board(h2.getBoard()));
	}

	

}
