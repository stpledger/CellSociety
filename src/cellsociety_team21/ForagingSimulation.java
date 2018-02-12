package cellsociety_team21;

import javafx.scene.paint.Color;

public class ForagingSimulation extends BasicSim {
	
	private int myMaxAntsPerCell;
	private double myDiffusion;
	private double myEvaporation;
	private double myMaxPheromones;
	private int myAntsBornPerTime;
	private int myStartingAge;
	
	public ForagingSimulation(int maxAntsPerCell, double diffusionRatio, double evaporationRatio, double maxPheromones, int antsBornPerTime, int startingAge){
		super();
		setMaxAntsPerCell(maxAntsPerCell);
		setDiffusion(diffusionRatio);
		setEvaporation(evaporationRatio);
		setMaxPheromones(maxPheromones);
		setAntsBornPerTime(antsBornPerTime);
		this.getStates().add("nest");
		this.getStates().add("food");
		this.getStates().add("GROUND");
		this.getStateColors().put("nest", Color.BLUE);
		this.getStateColors().put("food", Color.BROWN);
		this.getStateColors().put("GROUND", Color.BLACK);
		this.setStartingAge(startingAge);
	}

	@Override
	public void updateGrid(Grid grid) {
		for(Cell cell : grid.getCells()) {
			cell.assignNextState(grid.getCells(), this);
		}
		for(Cell cell : grid.getCells()) {
			((ForagingCell) cell).evaporate(this);
			((ForagingCell) cell).diffuse(this);
		}
		for(Cell cell : grid.getCells()) {
			cell.switchState(this.getStateColors());
		}
	}
	
	public int getMaxAntsPerCell() {
		return myMaxAntsPerCell;
	}

	public void setMaxAntsPerCell(int myMaxAntsPerCell) {
		this.myMaxAntsPerCell = myMaxAntsPerCell;
	}

	public double getDiffusion() {
		return myDiffusion;
	}

	public void setDiffusion(double myDiffusion) {
		this.myDiffusion = myDiffusion;
	}

	public double getEvaporation() {
		return myEvaporation;
	}

	public void setEvaporation(double myEvaporation) {
		this.myEvaporation = myEvaporation;
	}

	public double getMaxPheromones() {
		return myMaxPheromones;
	}

	public void setMaxPheromones(double myMaxPheromones) {
		this.myMaxPheromones = myMaxPheromones;
	}

	public int getAntsBornPerTime() {
		return myAntsBornPerTime;
	}

	public void setAntsBornPerTime(int myAntsBornPerTime) {
		this.myAntsBornPerTime = myAntsBornPerTime;
	}

	public int getStartingAge() {
		return myStartingAge;
	}

	public void setStartingAge(int myStartingAge) {
		this.myStartingAge = myStartingAge;
	}
	
}

