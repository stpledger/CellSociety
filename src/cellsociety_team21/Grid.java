package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;


public abstract class Grid {
	
	public abstract void assignNeighborsDiag();
	
	public abstract void assignNeighborsNoDiag();
	
	public abstract ArrayList<Cell> getCells();
	

	public abstract HashMap<Point, Cell> getCellMap();
	
	public abstract void switchStates(HashMap<String,Paint> stateColors);

}
