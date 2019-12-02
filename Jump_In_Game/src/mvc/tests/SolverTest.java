package mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import gamePieces.Direction;
import gamePieces.GridPoint;
import mvc.controller.Solver;
import mvc.controller.TilePlayBoard;

/**
 * 
 * @author TiantianLin
 *
 */
public class SolverTest {

	@Test
	public void testFindSolution() {

		TilePlayBoard board = new TilePlayBoard();
		board.setMushroom(2, 3);
		board.setMushroom(4, 2);
		board.setFox(new GridPoint(3,1),Direction.SOUTH);
		board.setFox(new GridPoint(3,4),Direction.EAST);
		board.setRabbit(1,3);
		board.setRabbit(2,4);
		board.setRabbit(4,1);
		Solver solve =  new Solver(board);
		solve.findSolution();
		assertEquals(true,solve.getWinState());
		
		
		

		TilePlayBoard board1 = new TilePlayBoard();
		board1.setMushroom(2,3);
		board1.setMushroom(4,2);
		board1.setFox(new GridPoint(3,1),Direction.SOUTH);
		board1.setFox(new GridPoint(3,4),Direction.EAST);
		board1.setRabbit(1,3);
		board1.setRabbit(4,1);
		Solver solve1 =  new Solver(board1);
		solve1.findSolution();
		assertEquals(true,solve1.getWinState());
		
		
		
		TilePlayBoard board2 = new TilePlayBoard();
		board2.setMushroom(0,3);
		board2.setMushroom(1,2);
		board2.setMushroom(3,2);
		board2.setRabbit(0,1);
		board2.setRabbit(4,2);
		Solver solve2 =  new Solver(board2);
		solve2.findSolution();
		assertEquals(true,solve2.getWinState());



	}

}


