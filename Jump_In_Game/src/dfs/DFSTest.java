package dfs;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.GridPoint;
import mvc.controller.TilePlayBoard;

public class DFSTest {



	@Test
	public void testGetSolvent() {
		TilePlayBoard instancepd = new TilePlayBoard();
		DFS instance = new DFS(instancepd);
		instance.getSolvent();
		instancepd.moveRabbit(instancepd.getRabbit(0), new GridPoint(4,3));
		instancepd.moveRabbit(instancepd.getRabbit(0), new GridPoint(4,0));
		instancepd.moveRabbit(instancepd.getRabbit(1), new GridPoint(4,4));
		assertEquals(true,instancepd.isWin());
		
	}

}
