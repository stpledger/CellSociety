package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;

public abstract class Grid extends HashMap<Point, Cell>{
	
	public abstract void assignNeighbors();
	
	public abstract ArrayList<Cell> getCells();
	
	public abstract void switchStates(HashMap<String,Paint> stateColors);
	
}
