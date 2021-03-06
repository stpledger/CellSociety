package cellsociety_team21;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;

public interface Simulation {
	
	public void updateGrid(Grid grid);
	
	public HashMap<String, Paint> getStateColors();
	
	public ArrayList<String> getStates();
}
