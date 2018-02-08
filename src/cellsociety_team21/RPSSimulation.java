package cellsociety_team21;

import javafx.scene.paint.Color;

public class RPSSimulation extends BasicSim {
	
	RPSSimulation(){
		super();
		this.getStates().add("white");
		this.getStates().add("blue");
		this.getStates().add("red");
		this.getStates().add("green");
		this.getStateColors().put("white", Color.WHITE);
		this.getStateColors().put("blue", Color.BLUE);
		this.getStateColors().put("red", Color.RED);
		this.getStateColors().put("green", Color.GREEN);
	}
	
}