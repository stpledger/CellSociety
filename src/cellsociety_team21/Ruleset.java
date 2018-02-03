package cellsociety_team21;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Paint;

public abstract class Ruleset {
	
	private HashMap<String, Paint> myStateColors;
	private ArrayList<String> myStates;
	
	abstract public void updateGrid(Grid grid);

	public HashMap<String, Paint> getStateColors(){
		return myStateColors;
	}
	
	public ArrayList<String> getStates(){
		return myStates;
	}
}
