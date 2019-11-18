package mvc.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testFindSolution() {
		
		TilePlayBoard board = new TilePlayBoard(1);
		Solver solve =  new Solver(board);
		solve.findSolution();
		assertEquals(true,solve.getwinState());
		
		TilePlayBoard board1 = new TilePlayBoard(2);
		Solver solve1 =  new Solver(board);
		solve1.findSolution();
		assertEquals(true,solve1.getwinState());
		
		TilePlayBoard board2 = new TilePlayBoard(3);
		Solver solve2 =  new Solver(board);
		solve2.findSolution();
		assertEquals(true,solve2.getwinState());
		
		
		
	}

}
