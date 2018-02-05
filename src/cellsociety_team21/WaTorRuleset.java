package cellsociety_team21;

import java.util.ArrayList;
import java.util.Random;

public class WaTorRuleset extends Ruleset {

	private static final int DEFAULT_START_ENERGY = 3;
	private static int myStartEnergy;
	private static final int DEFAULT_REPRODUCTION_TIME = 3;
	private static int myReproductionTime;
	private static final int DEFAULT_ENERGY_PER_FISH = 2;
	private static int myEnergyPerFish;

	WaTorRuleset(int startEnergy, int reproductionTime, int energyPerFish){
		super();
		myStartEnergy = startEnergy;
		myReproductionTime = reproductionTime;
		myEnergyPerFish = energyPerFish;
	}

	WaTorRuleset(){
		this(DEFAULT_START_ENERGY, DEFAULT_REPRODUCTION_TIME, DEFAULT_ENERGY_PER_FISH);
	}

	@Override
	public void updateGrid(Grid grid) {
		for(WaTorCell cell : grid.getCells()) {
			assignNext(cell);
		}
		grid.switchStates(this.getStateColors());

	}

	private void assignNext(WaTorCell cell) {
		if(cell.getCurrentState().equals("water")) {
			cell.setNextState("water");
			return;
		}
		else if(cell.getCurrentState().equals("fish")) {
			ArrayList<WaTorCell> potentialDestinations = new ArrayList<WaTorCell>();
			for(WaTorCell neighbor : cell.getNeighbors()) {
				if(neighbor.getCurrentState().equals("water") && (neighbor.getNextState().equals("water") || neighbor.getNextState().equals(null))) {
					potentialDestinations.add(neighbor);
				}
			}
			if(potentialDestinations.size()!=0) {
				WaTorCell destination = potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
				if(cell.getTimeTilReproduction()<=0) {
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
				ArrayList<WaTorCell> potentialDestinations = new ArrayList<WaTorCell>();
				boolean fishAvailable;
				for(WaTorCell neighbor : cell.getNeighbors()) {
					if(neighbor.getCurrentState().equals("fish") && !neighbor.getNextState().equals("shark")) {
						potentialDestinations.add(neighbor);
					}
				}
				if(potentialDestinations.size()==0) {
					fishAvailable = false;
					for(WaTorCell neighbor : cell.getNeighbors()) {
						if(neighbor.getCurrentState().equals("water") && !neighbor.getNextState().equals("shark")) {
							potentialDestinations.add(neighbor);
						}
					}
				}
				else {
					fishAvailable = true;
				}
				if(potentialDestinations.size()!=0) {
					WaTorCell destination = potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
					if(cell.getTimeTilReproduction()<=0) {
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
						cell.setNextEnergy(cell.getCurrentEnergy+1);
					}
					else {
						cell.setNextEnergy(cell.getCurrentEnergy-1);
					}
				}
			}
		}
		else {
			throw new IllegalArgumentException("An unsatisfied cell could not be moved");
		}
	}

}
