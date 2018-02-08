package cellsociety_team21;

import java.util.Collection;
import java.util.Random;

import javafx.scene.shape.Shape;

public class FireCell extends BasicCell {

	FireCell(Shape shape, String initState, int x, int y) {
		super(shape, initState, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void assignNextState(Collection<Cell> cells, Simulation sim) {
		FireSimulation fireSim = (FireSimulation) sim;
		//System.out.println(this.getCurrentState());
		if(this.getCurrentState().equals("empty") || this.getCurrentState().equals("burning")) {
			this.setNextState("empty");
			return;
		}
		else if(this.getCurrentState().equals("tree")){
			for(Cell neighbor : this.getNeighbors()) {
				if(neighbor.getCurrentState().equals("burning")) {
					Random r = new Random();
					double number = r.nextDouble();
					if(number<fireSim.getProbCatch()) {
						this.setNextState("burning");
						return;
					}
					this.setNextState("tree");
					return;
				}
			}
			this.setNextState("tree");
		}
		else {
			throw new IllegalStateException("Cell state not consistent with simulation options");
		}

	}
}