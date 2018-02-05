package cellsociety_team21;

import java.util.HashMap;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public class WaTorCell extends Cell {
	
	private int myCurrentEnergy;
	private int myNextEnergy;
	private int myCurrentTimeTilReproduction;
	private int myNextTimeTilReproduction;

	public WaTorCell(Shape shape, String initState, int x, int y, int initEnergy, int initRepro) {
		super(shape, initState, x, y);
		this.myCurrentEnergy = initEnergy;
		this.myCurrentTimeTilReproduction = initRepro;
	}
	
	public int getCurrentEnergy(){
		return myCurrentEnergy;
	}
	
	public int getCurrentTimeTilReproduction(){
		return myCurrentTimeTilReproduction;
	}
	
	public void setNextEnergy(int nextEnergy){
		myNextEnergy = nextEnergy;
	}
	
	public void setNextTimeTilReproduction(int nextRepro){
		myNextTimeTilReproduction = nextRepro;
	}
	
	@Override
	public void switchState(HashMap<String, Paint> stateColors){
		setCurrentState(getNextState());
		setNextState("");
		getShape().setFill(stateColors.get(getCurrentState()));
		myCurrentEnergy = myNextEnergy;
		myCurrentTimeTilReproduction = myNextTimeTilReproduction;
	}

}
