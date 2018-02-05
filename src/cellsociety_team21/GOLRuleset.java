package cellsociety_team21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GOLRuleset extends Ruleset {

	public GOLRuleset(){
		super();
		this.getStates().add("alive");
		this.getStates().add("dead");
		this.getStateColors().put("alive", Color.GREEN);
		this.getStateColors().put("dead", Color.RED);
	}

	@Override
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			assignNext(cell);
		}
		grid.switchStates(this.getStateColors());
	}

	private void assignNext(Cell c) {
		int liveNeighbors = 0;
		for(Cell neighbor : c.getNeighbors()) {
			if(neighbor.getCurrentState().equals("alive")) {
				liveNeighbors++;
			}
		}
		if(c.getCurrentState().equals("alive")) {
			if(liveNeighbors<2 || liveNeighbors>3) {
				c.setNextState("dead");
			}
			else {
				c.setNextState("alive");
			}
		}
		else if(c.getCurrentState().equals("dead")){
			if(liveNeighbors==3) {
				c.setNextState("alive");
			}
			else {
				c.setNextState("dead");
			}
		}
		else {
			throw new IllegalStateException("Cell state not consistent with simulation options");
		}
	}

}
