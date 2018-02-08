package cellsociety_team21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GOLSimulation extends BasicSim {

	public GOLSimulation(){
		super();
		this.getStates().add("alive");
		this.getStates().add("dead");
		this.getStateColors().put("alive", Color.GREEN);
		this.getStateColors().put("dead", Color.RED);
	}

}
