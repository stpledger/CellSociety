package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;

public abstract class Grid extends HashMap<Point, Cell>{
	
	abstract void assignNeighbors();
	
	abstract ArrayList<Cell> getCells();
	
	abstract void switchStates(HashMap<String,Paint> stateColors);
	
}
