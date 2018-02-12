package cellsociety_team21;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Paint;
/**
 * This abstract super class is meant to be implemented once for each new simulation
 * It holds everything relevant to the rules of the sim except rules related to the geometry of the grids and cells
 * Instance variable myStates holds all possible states that a cell can have, including empty
 * Instance variable my state colors holds the pairs of states and what color they should be displayed as
 * @author benauriemma
 *
 */
public abstract class BasicSim implements Simulation {
	
	private HashMap<String, Paint> myStateColors;
	private ArrayList<String> myStates;
	
	/**
	 * @param states possible in this simulation
	 * @param stateColors , keys are all possible states, and their values are what color they should be displayed as
	 */
	public BasicSim() {
		myStates = new ArrayList<String>();
		myStateColors = new HashMap<String, Paint>();
	}
	
	/**
	 * This method should be called once from a driver class to step the grid forward one evolution
	 * It should iterate through each cell and use helper methods to determine and assign their next state, then call grid.switchStates() to switch to the next generation
	 * @param grid is the Grid object that should be updated
	 */
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			cell.assignNextState(grid.getCells(), this);
		}
		for(Cell cell : grid.getCells()) {
			cell.switchState(this.getStateColors());
		}
	}

	/**
	 * @return myStateColors to be passed to Grid so that is can set colors
	 */
	public HashMap<String, Paint> getStateColors(){
		return myStateColors;
	}
	
	public ArrayList<String> getStates(){
		return myStates;
	}
}
