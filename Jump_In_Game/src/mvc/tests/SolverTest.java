package mvc.tests;

import static org.junit.Assert.*;

import org.junit.Test;

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

		TilePlayBoard board = new TilePlayBoard(1);
		Solver solve =  new Solver(board);
		solve.findSolution();
		assertEquals(true,solve.getWinState());

		TilePlayBoard board1 = new TilePlayBoard(2);
		Solver solve1 =  new Solver(board1);
		solve1.findSolution();
		assertEquals(true,solve1.getWinState());

		TilePlayBoard board2 = new TilePlayBoard(3);
		Solver solve2 =  new Solver(board2);
		solve2.findSolution();
		assertEquals(true,solve2.getWinState());



	}

}


