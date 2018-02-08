package cellsociety_team21;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WaTorSimulation extends BasicSim {

	private static final int DEFAULT_START_ENERGY = 3;
	private static int myStartEnergy;
	private static final int DEFAULT_REPRODUCTION_TIME = 3;
	private static int myReproductionTime;
	private static final int DEFAULT_ENERGY_PER_FISH = 2;
	private static int myEnergyPerFish;

	public WaTorSimulation(int startEnergy, int reproductionTime, int energyPerFish){
		super();
		this.myStartEnergy = startEnergy;
		this.myReproductionTime = reproductionTime;
		this.myEnergyPerFish = energyPerFish;
		this.getStates().add("water");
		this.getStates().add("fish");
		this.getStates().add("shark");
		this.getStateColors().put("water", Color.AQUA);
		this.getStateColors().put("fish", Color.CORAL);
		this.getStateColors().put("shark", Color.DARKGRAY);
	}
/*
	@Override
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			//try {
			assignNext((WaTorCell) cell);
			//}
			//catch (Exception e){
			//	throw new IllegalArgumentException("Tried to pass non-WaTor cells to WaTorRuleset");
			//}
		}
		grid.switchStates(this.getStateColors());

	}
*/
	/*
	private void assignNext(WaTorCell cell) {
		//System.out.println(cell.getNeighbors().size());

	}
*/
	public int getStartEnergy() {
		return myStartEnergy;
	}
	
	public int getReproductionTime() {
		return myReproductionTime;
	}
	
	public int getEnergyPerFish() {
		return myEnergyPerFish;
	}
	
}
