package cellsociety_team21;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javafx.scene.shape.Shape;

public class SegregationCell extends BasicCell {

	SegregationCell(Shape shape, String initState, int x, int y) {
		super(shape, initState, x, y);
	}

	@Override
	public void assignNextState(Collection<Cell> cells, Simulation sim) {
		SegregationSimulation segSim = (SegregationSimulation) sim;
		if(this.getCurrentState().equals("empty")) {
			if(this.getNextState()==null) {
				this.setNextState("empty");
			}
			return;
		}
		if(this.isSatisfied(segSim)) {
			this.setNextState(this.getCurrentState());
		}
		else {
			this.move(cells);
		}
	}

	/**
	 * @param cell is satisfied if t portion of its non-empty neighbors are of the same type as it
	 */
	private boolean isSatisfied(SegregationSimulation segSim) {
		int nonEmptyNeighbors = 0;
		int sameNeighbors = 0;
		for(Cell neighbor : this.getNeighbors()) {
			if(neighbor.getCurrentState()!="empty") {
				nonEmptyNeighbors++;
			}
			if(neighbor.getCurrentState().equals(this.getCurrentState())) {
				sameNeighbors++;
			}
		}
		if(nonEmptyNeighbors==0) {
			return true;
		}
		double sameRatio = sameNeighbors/nonEmptyNeighbors;
		return sameRatio>=segSim.getTRatio();
	}
	
	/**
	 * This method is used to move an unsatisfied cell to a vacant location
	 * First, set its next to empty
	 * Then scan through all cells and assign this cell's current state to the first cell found which is empty and has not had its next state assigned to be something other than empty
	 * Throws an IllegalArgumentException if the cell can't be moved
	 * @param cell is the cell to be moved
	 * @param cells is used to get the list of other cells
	 */
	protected void move(Collection<Cell> cells) {
		ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
		for(Cell c : cells) {
			if(c.getCurrentState().equals("empty") && (c.getNextState()==null || c.getNextState().equals("empty"))) {
				potentialDestinations.add(c);
			}
		}
		if(potentialDestinations.size()>0) {
			Cell destination = potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
			destination.setNextState(this.getCurrentState());
			this.setNextState("empty");
			System.out.println("moved cell");
		}
		else {
			this.setNextState(this.getCurrentState());
			System.out.println("was not satisfied, could not be moved");
			//throw new IllegalArgumentException("An unsatisfied cell could not be moved");
		}
	}
	
}
