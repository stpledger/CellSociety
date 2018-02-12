package cellsociety_team21;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public class ForagingCell extends BasicCell {

	ForagingCell(Shape shape, String initState, int x, int y) {
		super(shape, initState, x, y);
		myCurrentAnts = new ArrayList<Ant>();
		myNextAnts = new ArrayList<Ant>();
	}


	private ArrayList<Ant> myCurrentAnts;
	private ArrayList<Ant> myNextAnts;
	private double myCurrentFoodPhero;
	private double myNextFoodPhero;
	private double myCurrentHomePhero;
	private double myNextHomePhero;

	@Override
	public void assignNextState(Collection<Cell> cells, Simulation sim) {
		if(this.getCurrentState().equals("nest")) {
			birthAnts(sim);
		}
		for(Ant ant : myCurrentAnts) {
			ant.forage(getNeighborsMap(), this, sim);
		}
		evaporate(sim);
		diffuse(sim);
	}

	private void birthAnts(Simulation sim) {
		for(int k = 0; k<((ForagingSimulation) sim).getAntsBornPerTime(); k++) {
			this.myNextAnts.add(new Ant(((ForagingSimulation) sim).getStartingAge()));
		}
	}

	public void diffuse(Simulation sim) {
		for(Cell neighbor : this.getNeighbors()) {
			//The following unweildy lines set the next pheromone level of each neighbor to their next pheromone level plus however much diffuses from this cell
			((ForagingCell) neighbor).setNextHomePhero(((ForagingCell) neighbor).getNextHomePhero()*(1+((ForagingSimulation) sim).getDiffusion())*this.getNextHomePhero());
			((ForagingCell) neighbor).setNextFoodPhero(((ForagingCell) neighbor).getNextFoodPhero()*(1+((ForagingSimulation) sim).getDiffusion())*this.getNextFoodPhero());
		}
		//The following unweildy lines set the next pheromone level of this cell to its next pheromone level minus however much diffused from this cell to neighbors
		this.setNextFoodPhero(this.getNextFoodPhero()*(1-(((ForagingSimulation) sim).getDiffusion())*(this.getNeighbors().size())));
		this.setNextHomePhero(this.getNextHomePhero()*(1-(((ForagingSimulation) sim).getDiffusion())*(this.getNeighbors().size())));
	}

	public void evaporate(Simulation sim) {
		this.setNextFoodPhero(myNextFoodPhero*(1-((ForagingSimulation) sim).getEvaporation()));
		this.setNextHomePhero(myNextHomePhero*(1-((ForagingSimulation) sim).getEvaporation()));
	}

	@Override
	public void switchState(Map<String, Paint> stateColors){
		super.switchState(stateColors);
		this.myCurrentAnts = this.myNextAnts;
		this.myNextAnts.clear();
		this.setCurrentFoodPhero(this.getNextFoodPhero());
		this.setNextFoodPhero(0);
		this.setCurrentHomePhero(this.getNextHomePhero());
		this.setNextHomePhero(0);
	}

	@Override
	protected void setColor(Paint color){
		super.setColor(color);
		if(this.getCurrentState().equals("GROUND")) {
			this.getShape().setOpacity(this.getCurrentAnts().size()*10);
		}
	}

	public List<Ant> getCurrentAnts(){
		return myCurrentAnts;
	}

	public List<Ant> getNextAnts() {
		return myNextAnts;
	}

	public void setNextAnts(ArrayList<Ant> myNextAnts) {
		this.myNextAnts = myNextAnts;
	}

	public double getCurrentFoodPhero() {
		return myCurrentFoodPhero;
	}

	public void setCurrentFoodPhero(double myCurrentFoodPhero) {
		this.myCurrentFoodPhero = myCurrentFoodPhero;
	}

	public double getNextFoodPhero() {
		return myNextFoodPhero;
	}

	public void setNextFoodPhero(double myNextFoodPhero) {
		this.myNextFoodPhero = myNextFoodPhero;
	}

	public double getCurrentHomePhero() {
		return myCurrentHomePhero;
	}

	public void setCurrentHomePhero(double myCurrentHomePhero) {
		this.myCurrentHomePhero = myCurrentHomePhero;
	}

	public double getNextHomePhero() {
		return myNextHomePhero;
	}

	public void setNextHomePhero(double myNextHomePhero) {
		this.myNextHomePhero = myNextHomePhero;
	}

}
