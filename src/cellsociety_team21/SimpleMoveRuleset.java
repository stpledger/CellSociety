package cellsociety_team21;

import java.util.ArrayList;
import java.util.Random;

public abstract class SimpleMoveRuleset extends Ruleset {

	abstract protected boolean isSatisfied(Cell cell);

	/**
	 * If a cell is empty, set its next state to empty.  This might be changed later
	 * If a cell is satisfied, set its next state to its current state
	 * If a cell is dissatisfied, move it
	 * @param cell is the cell being checked
	 */
	protected void assignNext(Cell cell, Grid grid) {
		if(cell.getCurrentState().equals("empty")) {
			if(cell.getNextState()==null) {
				cell.setNextState("empty");
			}
			return;
		}
		if(isSatisfied(cell)) {
			cell.setNextState(cell.getCurrentState());
		}
		else {
			moveCell(cell, grid);
		}
	}

	/**
	 * This method is used to move an unsatisfied cell to a vacant location
	 * First, set its next to empty
	 * Then scan through all cells and assign this cell's current state to the first cell found which is empty and has not had its next state assigned to be something other than empty
	 * Throws an IllegalArgumentException if the cell can't be moved
	 * @param cell is the cell to be moved
	 * @param grid is used to get the list of other cells
	 */
	protected void moveCell(Cell cell, Grid grid) {
		ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
		for(Cell c : grid.getCells()) {
			if(c.getCurrentState().equals("empty") && (c.getNextState()==null || c.getNextState().equals("empty"))) {
				potentialDestinations.add(c);
			}
		}
		if(potentialDestinations.size()>0) {
			Cell destination = potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
			destination.setNextState(cell.getCurrentState());
			cell.setNextState("empty");
			System.out.println("moved cell");
		}
		else {
			cell.setNextState(cell.getCurrentState());
			//throw new IllegalArgumentException("An unsatisfied cell could not be moved");
		}
	}

}
