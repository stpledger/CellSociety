package cellsociety_team21;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class WaTorRuleset extends Ruleset {

	private static final int DEFAULT_START_ENERGY = 3;
	private static int myStartEnergy;
	private static final int DEFAULT_REPRODUCTION_TIME = 3;
	private static int myReproductionTime;
	private static final int DEFAULT_ENERGY_PER_FISH = 2;
	private static int myEnergyPerFish;

	public WaTorRuleset(int startEnergy, int reproductionTime, int energyPerFish){
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

	WaTorRuleset(){
		this(DEFAULT_START_ENERGY, DEFAULT_REPRODUCTION_TIME, DEFAULT_ENERGY_PER_FISH);
	}

	@Override
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			try {
				assignNext((WaTorCell) cell);
			}
			catch (Exception e){
				throw new IllegalArgumentException("Tried to pass non-WaTor cells to WaTorRuleset");
			}
		}
		grid.switchStates(this.getStateColors());

	}

	private void assignNext(WaTorCell cell) {
		if(cell.getCurrentState().equals("water")) {
			cell.setNextState("water");
			return;
		}
		else if(cell.getCurrentState().equals("fish")) {
			ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
			for(Cell neighbor : cell.getNeighbors()) {
				if(neighbor.getCurrentState().equals("water") && (neighbor.getNextState().equals("water") || neighbor.getNextState().equals(null))) {
					potentialDestinations.add(neighbor);
				}
			}
			if(potentialDestinations.size()!=0) {
				WaTorCell destination = (WaTorCell) potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
				if(cell.getCurrentTimeTilReproduction()<=0) {
					cell.setNextState("fish");
					cell.setNextTimeTilReproduction(myReproductionTime);
					destination.setNextTimeTilReproduction(myReproductionTime);
				}
				else {
					cell.setNextState("water");
					cell.setNextTimeTilReproduction(0);
					destination.setNextTimeTilReproduction(myReproductionTime-1);
				}
				cell.setNextEnergy(0);
				destination.setNextState("fish");
				destination.setNextEnergy(0);
			}
			else {
				cell.setNextState("fish");
				cell.setNextTimeTilReproduction(cell.getCurrentTimeTilReproduction()-1);
				cell.setNextEnergy(0);
			}
		}
		else if(cell.getCurrentState().equals("shark")) {
			if(cell.getCurrentEnergy()==0) {
				cell.setNextState("water");
				cell.setNextEnergy(0);
				cell.setNextTimeTilReproduction(0);
			}
			else {
				ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
				boolean fishAvailable;
				for(Cell neighbor : cell.getNeighbors()) {
					if(neighbor.getCurrentState().equals("fish") && !neighbor.getNextState().equals("shark")) {
						potentialDestinations.add(neighbor);
					}
				}
				if(potentialDestinations.size()==0) {
					fishAvailable = false;
					for(Cell neighbor : cell.getNeighbors()) {
						if(neighbor.getCurrentState().equals("water") && !neighbor.getNextState().equals("shark")) {
							potentialDestinations.add(neighbor);
						}
					}
				}
				else {
					fishAvailable = true;
				}
				if(potentialDestinations.size()!=0) {
					WaTorCell destination = (WaTorCell) potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
					if(cell.getCurrentTimeTilReproduction()<=0) {
						cell.setNextState("shark");
						cell.setNextTimeTilReproduction(myReproductionTime);
						cell.setNextEnergy(myStartEnergy);
						destination.setNextTimeTilReproduction(myReproductionTime);
					}
					else {
						cell.setNextState("water");
						cell.setNextTimeTilReproduction(0);
						cell.setNextEnergy(0);
						destination.setNextTimeTilReproduction(myReproductionTime-1);
					}
					destination.setNextState("shark");
					destination.setNextEnergy(cell.getCurrentEnergy()-1);
				}
				else {
					cell.setNextState("shark");
					cell.setNextTimeTilReproduction(cell.getCurrentTimeTilReproduction()-1);
					if(fishAvailable) {
						cell.setNextEnergy(cell.getCurrentEnergy()+1);
					}
					else {
						cell.setNextEnergy(cell.getCurrentEnergy()-1);
					}
				}
			}
		}
		else {
			throw new IllegalArgumentException("An unsatisfied cell could not be moved");
		}
	}

}
