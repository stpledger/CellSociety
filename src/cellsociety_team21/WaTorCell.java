package cellsociety_team21;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public class WaTorCell extends BasicCell {

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
	public void switchState(Map<String, Paint> stateColors){
		super.switchState(stateColors);
		//setNextState("");
		myCurrentEnergy = myNextEnergy;
		myNextEnergy = 0;
		myCurrentTimeTilReproduction = myNextTimeTilReproduction;
		myNextTimeTilReproduction = 0;
	}

	@Override
	public void assignNextState(Collection<Cell> cells, Simulation sim) {
		WaTorSimulation waTorSim = (WaTorSimulation) sim;
		if(this.getCurrentState().equals("water")) {
			if(this.getNextState()==null) {
				this.setNextState("water");
				this.setNextEnergy(0);
				this.setNextTimeTilReproduction(0);
			}
		}
		else if(this.getCurrentState().equals("fish")) {
			this.fishAssign(cells, waTorSim);
		}
		else if(this.getCurrentState().equals("shark")) {
			this.sharkAssign(cells, waTorSim);
		}
		else {
			throw new IllegalArgumentException("An unsatisfied cell could not be moved");
		}

	}

	private void fishAssign(Collection<Cell> cells, WaTorSimulation waTorSim) {
		System.out.println("Fish has this many neighbors: "+this.getNeighbors().size());
		ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
		this.findFishDestinations(potentialDestinations);
		System.out.println("Fish has this many potential destintions: "+potentialDestinations.size());
		if(potentialDestinations.size()!=0) {
			WaTorCell destination = (WaTorCell) potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));

			if(this.getCurrentTimeTilReproduction()<=0) {
				this.reproduceTo(destination, waTorSim, "fish", 0, 0);
			}
			else {
				this.moveTo(destination, "fish", 0);
			}
		}
		else {
			System.out.println("stayed");
			this.setNextState("fish");
			this.setNextTimeTilReproduction(this.getCurrentTimeTilReproduction()-1);
			this.setNextEnergy(0);
		}
	}

	private void moveTo(WaTorCell destination, String type, int nextEnergy) {
		this.setNextState("water");
		this.setNextTimeTilReproduction(0);
		this.setNextEnergy(0);
		destination.setNextState(type);
		destination.setNextTimeTilReproduction(this.getCurrentTimeTilReproduction()-1);
		destination.setNextEnergy(nextEnergy);
	}

	private void reproduceTo(WaTorCell destination, WaTorSimulation waTorSim, String type, int destNextEnergy, int thisNextEnergy) {
		moveTo(destination, type, destNextEnergy);
		this.setNextState(type);
		this.setNextTimeTilReproduction(waTorSim.getReproductionTime());
		this.setNextEnergy(thisNextEnergy);
		destination.setNextTimeTilReproduction(waTorSim.getReproductionTime());
	}

	private void findFishDestinations(ArrayList<Cell> potentialDestinations) {
		for(Cell neighbor : this.getNeighbors()) {
			if(neighbor.getCurrentState().equals("water") && (neighbor.getNextState()==null || neighbor.getNextState().equals("water"))) {// (neighbor.getNextState().equals("water") || neighbor.getNextState().equals(null))) {
				potentialDestinations.add(neighbor);
			}
		}
	}

	private void sharkAssign(Collection<Cell> cells, WaTorSimulation waTorSim) {
		System.out.println("Shark has this many neighbors: "+this.getNeighbors().size());
		if(this.getCurrentEnergy()==0) {
			this.setNextState("water");
			this.setNextEnergy(0);
			this.setNextTimeTilReproduction(0);
		}
		else {
			ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
			boolean fishAvailable;
			fishAvailable = findSharkDestinations(potentialDestinations);
			//System.out.println("cell.getNeighbors: "+cell.getNeighbors());

			System.out.println("Shark has this many potential destinations: "+potentialDestinations.size());
			if(potentialDestinations.size()!=0) {
				WaTorCell destination = (WaTorCell) potentialDestinations.get(new Random().nextInt(potentialDestinations.size()));
				int destNextEnergy;
				if(fishAvailable) {
					destNextEnergy = this.getCurrentEnergy()+waTorSim.getEnergyPerFish();
				}
				else {
					destNextEnergy = this.getCurrentEnergy()-1;
				}
				if(this.getCurrentTimeTilReproduction()<=0) {
					reproduceTo(destination, waTorSim, "shark", destNextEnergy, waTorSim.getStartEnergy());
					this.setNextState("shark");
					this.setNextTimeTilReproduction(waTorSim.getReproductionTime());
					this.setNextEnergy(waTorSim.getStartEnergy());
					destination.setNextTimeTilReproduction(waTorSim.getReproductionTime());
				}
				else {
					moveTo(destination, "shark", destNextEnergy);
				}
				//destination.setNextState("shark");
				/*
				if(fishAvailable) {
					destination.setNextEnergy(this.getCurrentEnergy()+waTorSim.getEnergyPerFish());
				}
				else {
					destination.setNextEnergy(this.getCurrentEnergy()-1);
				}
				 */
			}
			else {
				this.setNextState("shark");
				this.setNextTimeTilReproduction(this.getCurrentTimeTilReproduction()-1);
				this.setNextEnergy(this.getCurrentEnergy()-1);
			}
		}
	}

	private boolean findSharkDestinations(ArrayList<Cell> potentialDestinations) {
		boolean fishAvailable;
		for(Cell neighbor : this.getNeighbors()) {
			if(neighbor.getCurrentState().equals("fish") && (neighbor.getNextState()==null || neighbor.getNextState().equals("water") || neighbor.getNextState().equals("fish"))) {
				potentialDestinations.add(neighbor);
			}
		}
		if(potentialDestinations.size()!=0) {
			fishAvailable = true;
		}
		else {
			fishAvailable = false;
			for(Cell neighbor : this.getNeighbors()) {
				if(neighbor.getCurrentState().equals("water") && (neighbor.getNextState()==null || neighbor.getNextState().equals("water") || neighbor.getNextState().equals("fish"))) {
					potentialDestinations.add(neighbor);
				}
			}
		}
		return fishAvailable;
	}

}
