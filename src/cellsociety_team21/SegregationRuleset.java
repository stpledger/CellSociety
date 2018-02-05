package cellsociety_team21;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class SegregationRuleset extends SimpleMoveRuleset {


	private static final double DEFAULT_T_RATIO = 0.3;
	private static double myTRatio;

	/**
	 * Constructor for SegregationRuleset class
	 * @param tRatio is the portion of a cell's neighbors that have to be the same as it to be satisfied
	 */
	SegregationRuleset(double tRatio) {
		super();
		this.getStates().add("empty");
		this.getStates().add("x");
		this.getStates().add("o");
		this.getStateColors().put("x", Color.BLUE);
		this.getStateColors().put("o", Color.RED);
		this.getStateColors().put("empty", Color.GREY);
		myTRatio = tRatio;
	}

	/**
	 * The 0-argument constructor uses a hardcoded T ratio 0.3. This can be changed in the SegregationRuleset class
	 */
	SegregationRuleset() {
		this(DEFAULT_T_RATIO);
	}

	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			assignNext(cell, grid);
		}
		grid.switchStates(this.getStateColors());
	}


	/**
	 * @param cell is satisfied if t portion of its non-empty neighbors are of the same type as it
	 */
	@Override
	protected boolean isSatisfied(Cell cell) {
		int nonEmptyNeighbors = 0;
		int sameNeighbors = 0;
		for(Cell neighbor : cell.getNeighbors()) {
			if(neighbor.getCurrentState()!="empty") {
				nonEmptyNeighbors++;
			}
			if(neighbor.getCurrentState().equals(cell.getCurrentState())) {
				sameNeighbors++;
			}
		}
		if(nonEmptyNeighbors==0) {
			return true;
		}
		double sameRatio = sameNeighbors/nonEmptyNeighbors;
		return sameRatio>=myTRatio;
	}

}