package cellsociety_team21;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Ant {

	private ArrayList<String> directionList;
	private boolean hasFoodItem;
	private int myAge;

	Ant(int startingAge){
		directionList = new ArrayList<String>();
		directionList.add("north");
		directionList.add("northeast");
		directionList.add("east");
		directionList.add("southeast");
		directionList.add("south");
		directionList.add("southwest");
		directionList.add("west");
		directionList.add("northwest");
		myAge = startingAge;
	}

	public void forage(Map<String, Cell> neighbors, Cell currentCell, Simulation sim){
		myAge--;
		if(myAge==0) {
			return;
		}
		if(currentCell.getCurrentState().equals("nest") && hasFoodItem) {
			dropFood();
		}
		else if(currentCell.getCurrentState().equals("food") && !hasFoodItem) {
			pickUpFood();
		}
		if(hasFoodItem) {
			dropFoodPheromones((ForagingCell) currentCell, neighbors, (ForagingSimulation) sim);
			lookForNest(neighbors, currentCell);
		}
		else {
			dropHomePheromones((ForagingCell) currentCell, neighbors, (ForagingSimulation) sim);
			lookForFood(neighbors, currentCell);
		}
	}

	private void dropHomePheromones(ForagingCell currentCell, Map<String, Cell> neighbors, ForagingSimulation sim) {
		if(currentCell.getCurrentState().equals("nest")) {
			topOffHomePheromones(currentCell, sim);
		}
		else {
			addToHomePheromones(currentCell, neighbors, sim);
		}
	}

	private void dropFoodPheromones(ForagingCell currentCell, Map<String, Cell> neighbors, ForagingSimulation sim) {
		if(currentCell.getCurrentState().equals("food")) {
			topOffFoodPheromones(currentCell, sim);
		}
		else {
			addToFoodPheromones(currentCell, neighbors, sim);
		}
	}

	private void addToFoodPheromones(ForagingCell currentCell, Map<String, Cell> neighbors, ForagingSimulation sim) {
		double max = findMaxFoodPheromones(neighbors);
		double desired = max-2;
		double deposit = desired - currentCell.getCurrentFoodPhero();
		if(deposit>0) {
			currentCell.setNextFoodPhero(deposit);
		}
	}
	
	private void addToHomePheromones(ForagingCell currentCell, Map<String, Cell> neighbors, ForagingSimulation sim) {
		double max = findMaxHomePheromones(neighbors);
		double desired = max-2;
		double deposit = desired - currentCell.getCurrentHomePhero();
		if(deposit>0) {
			currentCell.setNextHomePhero(deposit);
		}
	}



	private double findMaxFoodPheromones(Map<String, Cell> neighbors) {
		double max = 0;
		for(Cell neighbor : neighbors.values()) {
			if(((ForagingCell) neighbor).getCurrentFoodPhero()>max) {
				max = ((ForagingCell) neighbor).getCurrentFoodPhero();
			}
		}
		return max;
	}
	
	private double findMaxHomePheromones(Map<String, Cell> neighbors) {
		double max = 0;
		for(Cell neighbor : neighbors.values()) {
			if(((ForagingCell) neighbor).getCurrentHomePhero()>max) {
				max = ((ForagingCell) neighbor).getCurrentHomePhero();
			}
		}
		return max;
	}

	private void topOffFoodPheromones(ForagingCell currentCell, ForagingSimulation sim) {
		currentCell.setNextFoodPhero(sim.getMaxPheromones());
	}
	
	private void topOffHomePheromones(ForagingCell currentCell, ForagingSimulation sim) {
		currentCell.setNextHomePhero(sim.getMaxPheromones());
	}

	private void lookForNest(Map<String, Cell> neighbors, Cell currentCell) {
		String orientation = getHomeOrientation(neighbors);
		ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
		addForwardDestinations(potentialDestinations, orientation, neighbors);
		potentialDestinations = removeIneligibleDestinations(potentialDestinations);
		if(potentialDestinations.size()==0) {
			addAllDestinations(potentialDestinations, orientation, neighbors);
			potentialDestinations = removeIneligibleDestinations(potentialDestinations);
		}
		if(potentialDestinations.size()==0) {
			moveTo((ForagingCell) currentCell);
		}
		else {
			ForagingCell destination = pickDestination(potentialDestinations);
			moveTo(destination);
		}
	}
	
	private void lookForFood(Map<String, Cell> neighbors, Cell currentCell) {
		String orientation = getFoodOrientation(neighbors);
		ArrayList<Cell> potentialDestinations = new ArrayList<Cell>();
		addForwardDestinations(potentialDestinations, orientation, neighbors);
		potentialDestinations = removeIneligibleDestinations(potentialDestinations);
		if(potentialDestinations.size()==0) {
			addAllDestinations(potentialDestinations, orientation, neighbors);
			potentialDestinations = removeIneligibleDestinations(potentialDestinations);
		}
		if(potentialDestinations.size()==0) {
			moveTo((ForagingCell) currentCell);
		}
		else {
			ForagingCell destination = pickDestination(potentialDestinations);
			moveTo(destination);
		}
	}



	private ForagingCell pickDestination(ArrayList<Cell> potentialDestinations) {
		ArrayList<Cell> weightedList = new ArrayList<Cell>();
		for(Cell c : potentialDestinations) {
			for(int k = 0; k<((ForagingCell) c).getCurrentHomePhero(); k++) {
				weightedList.add(c);
			}
		}
		ForagingCell destination = (ForagingCell) weightedList.get(new Random().nextInt(weightedList.size()));
		return destination;
	}

	private void addAllDestinations(ArrayList<Cell> potentialDestinations, String orientation, Map<String, Cell> neighbors) {
		for(String direction : directionList) {
			potentialDestinations.add(neighbors.get(direction));
		}
	}

	private void addForwardDestinations(ArrayList<Cell> potentialDestinations, String orientation, Map<String, Cell> neighbors) {
		for(String direction : getForwardDirections(orientation)) {
			potentialDestinations.add(neighbors.get(direction));
		}
	}

	private void moveTo(ForagingCell destination) {
		destination.getNextAnts().add(this);
	}

	private ArrayList<Cell> removeIneligibleDestinations(ArrayList<Cell> potentialDestinations) {
		ArrayList<Cell> ret = new ArrayList<Cell>(potentialDestinations);
		for(Cell c : potentialDestinations) {
			if(((ForagingCell) c).getCurrentAnts().size()>=10) {
				ret.remove(c);
			}
		}
		return ret;

	}

	private String getHomeOrientation(Map<String, Cell> neighbors) {
		double maxPheromones = -1;
		String bestDirection = "";
		for(String direction : directionList) {
			if(((ForagingCell) neighbors.get(direction)).getCurrentHomePhero()>maxPheromones) {
				maxPheromones = ((ForagingCell) neighbors.get(direction)).getCurrentHomePhero();
				bestDirection = direction;
			}
		}
		return bestDirection;
	}

	private String getFoodOrientation(Map<String, Cell> neighbors) {
		double maxPheromones = -1;
		String bestDirection = "";
		for(String direction : directionList) {
			if(((ForagingCell) neighbors.get(direction)).getCurrentFoodPhero()>maxPheromones) {
				maxPheromones = ((ForagingCell) neighbors.get(direction)).getCurrentFoodPhero();
				bestDirection = direction;
			}
		}
		return bestDirection;
	}
	
	private ArrayList<String> getForwardDirections(String orientation){
		int index = 0;
		ArrayList<String> ret = new ArrayList<String>();
		while(!directionList.get(index).equals(orientation)) {
			index++;
		}
		if(index==0) {
			ret.add(directionList.get(directionList.size()-1));
			ret.add(directionList.get(index));
			ret.add(directionList.get(index+1));
		}
		else if(index==directionList.size()-1) {
			ret.add(directionList.get(index-1));
			ret.add(directionList.get(index));
			ret.add(directionList.get(0));
		}
		else {
			ret.add(directionList.get(index-1));
			ret.add(directionList.get(index));
			ret.add(directionList.get(index+1));
		}
		return ret;
	}

	private void dropFood() {
		hasFoodItem = false;
	}

	private void pickUpFood() {
		hasFoodItem = true;
	}

}
