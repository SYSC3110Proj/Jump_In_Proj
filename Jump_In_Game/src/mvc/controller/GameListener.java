package mvc.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gamePieces.Direction;
import mvc.view.GridButton;

public class GameListener implements ActionListener {
	
	private boolean isSelecting;
	
	private String name;
	private Point sourcePoint, destPoint;
	
	public void actionPerformed(ActionEvent e) {
		// If the player is currently in the selection phase
		if (isSelecting == true) {
			
		} else {	// If the player is in the movement phase
			if (name != null) {
				if (!name.equals("Hole") && !name.equals("mushroom")) {
					
					destPoint = ((GridButton) e.getSource()).getGridLocation();
				
					if(name.equals("rabbit1") || name.equals("rabbit2") || name.equals("rabbit3")) {
						game.moveRabbitTo(game.getRabbit(name), getDirection(sourcePoint, destPoint));
						
					} else if (name.equals("fox1") || name.equals("fox2")) {
						if (game.getFox(name)[0].getDirection().equals(Direction.HORIZONTAL)) {
							game.moveTo(game.getFox(name), (int) destPoint.getX());
						} else {
							game.moveTo(game.getFox(name), (int) destPoint.getY());
						}
					}
					
					// Toggle both buttons to show as off
					sourceButton.setSelected(false);
					((GridButton) e.getSource()).setSelected(false);
					
					view.update(game.getBoardName());
				}
			}
			isSelecting = true;
			if (game.checkWinState()) {
				view.popWin();	
			}
		}
	}
}
