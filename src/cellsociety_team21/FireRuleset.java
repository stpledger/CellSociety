package cellsociety_team21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class FireRuleset extends Ruleset {

	private static final double DEFAULT_PROB_CATCH = 0.5;
	private static double myProbCatch;
	
	FireRuleset(double probCatch){
		super();
		this.getStates().add("burning");
		this.getStates().add("tree");
		this.getStates().add("empty");
		this.getStateColors().put("tree", Color.GREEN);
		this.getStateColors().put("burning", Color.RED);
		this.getStateColors().put("empty", Color.BLACK);
		myProbCatch = probCatch;
	}
	
	FireRuleset(){
		this(DEFAULT_PROB_CATCH);
	}

	@Override
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			assignNext(cell);
		}
		grid.switchStates(this.getStateColors());
	}

	void assignNext(Cell c) {
		if(c.getCurrentState().equals("empty") || c.getCurrentState().equals("burning")) {
			c.setNextState("empty");
			return;
		}
		else if(c.getCurrentState().equals("tree")){
			for(Cell neighbor : c.getNeighbors()) {
				if(neighbor.getCurrentState().equals("burning")) {
					Random r = new Random();
					double number = r.nextDouble();
					if(number<myProbCatch) {
						c.setNextState("burning");
						return;
					}
					c.setNextState("tree");
					return;
				}
			}
			c.setNextState("tree");
		}
		else {
			throw new IllegalStateException("Cell state not consistent with simulation options");
		}
	}

}
