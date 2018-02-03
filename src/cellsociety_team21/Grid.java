package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;

public abstract class Grid extends HashMap<Point, Cell>{
	private HashMap<Point, Cell> cellMap;
	
	public abstract void assignNeighbors();
	
	public ArrayList<Cell> getCells(){
		return new ArrayList<Cell>(cellMap.values());
	}
	
	public void switchStates(HashMap<String,Paint> stateColors){
		for(Cell cell : cellMap.values()){
			cell.switchState();
		}
	}
	
}
