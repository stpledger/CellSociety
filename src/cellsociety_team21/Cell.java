package cellsociety_team21;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public interface Cell {
	
	
	
	/**
	 * This method uses the rules of the simulation to decide its next state
	 * In simulations that require movement, the next state of another cell in @param cells is also set
	 */
	public void assignNextState(Collection<Cell> cells, Simulation sim);
	
	/**
	 * To be called after all cells have assigned their next state
	 * Automatically updates color
	 * @param hashMap 
	 */
	public void switchState(Map<String, Paint> stateColors);
	
	/**
	 * Called by GUI for display purposes
	 * @return the Shape object of the cell
	 */
	public Shape getShape();
	
	/**
	 * To be called if GUI class wants to plot the number of cells in each state
	 * @return String of current state
	 */
	public String getCurrentState();

	/**
	 * To be called by cells looking to move to this cell's spot to make sure it hasn't been taken yet
	 * @return String of next state
	 */
	public String getNextState();
	
	/**
	 * Only to be called by other cells wishing to move to this cell
	 * MUST check getNextState() first to avoid unintentional overwriting of nextState
	 * @param state
	 */
	public void setNextState(String state);

	/**
	 * To be called by a grid who is assigning neighbors
	 * @param neighbor
	 */
	public void addNeighbor(String whichNeighbor, Cell neighbor);
	
	/**
	 * Set the location on screen of this cell
	 * Allows GUI to place the cell in the appropriate spot
	 * @param x coordinate
	 * @param y coordinate
	 */
	public void setLocation(int x, int y);
	
	/**
	 * Location on screen
	 * @return x coordinate
	 */
	public int getX();
	
	/**
	 * Location on screen
	 * @return y coordinate
	 */
	public int getY();
	
}
