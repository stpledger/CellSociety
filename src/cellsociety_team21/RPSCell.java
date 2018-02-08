package cellsociety_team21;

import java.util.Collection;
import java.util.Random;

import javafx.scene.shape.Shape;

public class RPSCell extends BasicCell {

	private final String RED = "red";
	private final String BLUE = "blue";
	private final String GREEN = "green";
	private final String WHITE = "white";
	
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
		if(this.getCurrentState().equals(WHITE)) {
			if(!neighbor.getCurrentState().equals(WHITE) && neighbor.getCurrentStrength()<9){
				this.setNextState(neighbor.getCurrentState());
				this.setNextStrength(neighbor.getCurrentStrength()+1);
			}
			else {
				this.setNextState(WHITE);
				this.setNextStrength(0);
			}
		}
		else if(this.getCurrentState().equals(RED)){
			if(neighbor.getCurrentState().equals(GREEN)) {
				eatenBy(GREEN, neighbor.getCurrentStrength());
			}
			else {
				remain(RED, this.getCurrentStrength());
			}
		}
		else if(this.getCurrentState().equals(GREEN)){
			if(neighbor.getCurrentState().equals(BLUE)) {
				eatenBy(BLUE, neighbor.getCurrentStrength());
			}
			else {
				remain(GREEN, this.getCurrentStrength());
			}
		}
		else if(this.getCurrentState().equals(BLUE)){
			if(neighbor.getCurrentState().equals(RED)) {
				eatenBy(RED, neighbor.getCurrentStrength());
			}
			else {
				remain(BLUE, this.getCurrentStrength());
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
