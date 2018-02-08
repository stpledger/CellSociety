package cellsociety_team21;

import java.util.Collection;
import java.util.Random;

import javafx.scene.shape.Shape;

public class RPSCell extends BasicCell {

	private int myCurrentStrength;
	private int myNextStrength;
	
	RPSCell(Shape shape, String initState, int x, int y) {
		super(shape, initState, x, y);
		myCurrentStrength = 0;
		myNextStrength = 0;
	}

	@Override
	public void assignNextState(Collection<Cell> cells, Simulation sim) {
		RPSCell neighbor = (RPSCell) this.getNeighbors().get(new Random().nextInt(this.getNeighbors().size()));
		if(this.getCurrentState().equals("white")) {
			if(!neighbor.getCurrentState().equals("white") && neighbor.getCurrentStrength()<9){
				this.setNextState(neighbor.getCurrentState());
				this.setNextStrength(neighbor.getCurrentStrength()+1);
			}
			else {
				this.setNextState("white");
				this.setNextStrength(0);
			}
		}
		else if(this.getCurrentState().equals("red")){
			if(neighbor.getCurrentState().equals("green")) {
				eatenBy("green", neighbor.getCurrentStrength());
			}
			else {
				remain("red", this.getCurrentStrength());
			}
		}
		else if(this.getCurrentState().equals("green")){
			if(neighbor.getCurrentState().equals("blue")) {
				eatenBy("blue", neighbor.getCurrentStrength());
			}
			else {
				remain("green", this.getCurrentStrength());
			}
		}
		else if(this.getCurrentState().equals("blue")){
			if(neighbor.getCurrentState().equals("red")) {
				eatenBy("red", neighbor.getCurrentStrength());
			}
			else {
				remain("blue", this.getCurrentStrength());
			}
		}
	}

	public int getCurrentStrength() {
		return myCurrentStrength;
	}
	
	public void setNextStrength(int nextStrength) {
		myNextStrength = nextStrength;
	}
	
	private void eatenBy(String color, int theirStrength) {
		this.setNextState(color);
		this.setNextStrength(theirStrength-1);
		if(this.getNextStrength()<0) {
			this.setNextStrength(0);
		}
	}
	
	private void remain(String color, int currentStrength) {
		this.setNextState(color);
		this.setNextStrength(currentStrength);
	}
	
	private int getNextStrength() {
		return myNextStrength;
	}
	
}
