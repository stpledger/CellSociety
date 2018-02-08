package cellsociety_team21;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class SegregationSimulation extends BasicSim {


	private static double myTRatio;

	/**
	 * Constructor for SegregationRuleset class
	 * @param tRatio is the portion of a cell's neighbors that have to be the same as it to be satisfied
	 */
	public SegregationSimulation(double tRatio) {
		super();
		this.getStates().add("empty");
		this.getStates().add("x");
		this.getStates().add("o");
		this.getStateColors().put("x", Color.BLUE);
		this.getStateColors().put("o", Color.RED);
		this.getStateColors().put("empty", Color.GREY);
		this.myTRatio = tRatio;
	}

	/**
	 * The 0-argument constructor uses a hardcoded T ratio 0.3. This can be changed in the SegregationRuleset class
	 */
/*
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			assignNext(cell, grid);
		}
		grid.switchStates(this.getStateColors());
	}
	*/

	public double getTRatio() {
		return myTRatio;
	}
	

}