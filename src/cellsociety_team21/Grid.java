package cellsociety_team21;

import java.util.List;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;

import javafx.scene.paint.Paint;


public abstract class Grid {
	
	public abstract Collection<Cell> getCells();

	public abstract HashMap<Point, Cell> getCellMap();
	
	public abstract void switchStates(HashMap<String,Paint> stateColors);

}
