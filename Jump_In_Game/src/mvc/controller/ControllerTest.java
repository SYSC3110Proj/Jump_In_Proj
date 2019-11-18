package mvc.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import tree.MovementData;

public class ControllerTest {

	@Test
	public void testFindSolution() {
		Controller instance = new Controller();
		ArrayList<MovementData> tree = instance.findSolution();
		
				
	}

}
