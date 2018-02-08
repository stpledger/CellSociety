package cellsociety_team21;

import java.util.Collection;

import javafx.scene.shape.Shape;

public class GOLCell extends BasicCell {

	GOLCell(Shape shape, String initState, int x, int y) {
		super(shape, initState, x, y);
		}

	@Override
	public void assignNextState(Collection<Cell> cells, Simulation sim) {
		// TODO Auto-generated method stub
		int liveNeighbors = 0;
		for(Cell neighbor : this.getNeighbors()) {
			if(neighbor.getCurrentState().equals("alive")) {
				liveNeighbors++;
			}
		}
		if(this.getCurrentState().equals("alive")) {
			if(liveNeighbors<2 || liveNeighbors>3) {
				this.setNextState("dead");
			}
			else {
				this.setNextState("alive");
			}
		}
		else if(this.getCurrentState().equals("dead")){
			if(liveNeighbors==3) {
				this.setNextState("alive");
			}
			else {
				this.setNextState("dead");
			}
		}
		else {
			throw new IllegalStateException("Cell state not consistent with simulation options");
		}
	}

}
