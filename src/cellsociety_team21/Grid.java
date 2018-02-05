package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;

public abstract class Grid {
	private HashMap<Point, Cell> cellMap;
	
	public abstract void assignNeighborsDiag();
	
	public abstract void assignNeighborsNoDiag();
	
	public ArrayList<Cell> getCells(){
		return new ArrayList<Cell>(cellMap.values());
	}
	
	public HashMap<Point, Cell> getCellMap(){
		return cellMap;
	}
	
	public void switchStates(HashMap<String,Paint> stateColors){
		for(Cell cell : this.getCells()){
			cell.switchState();
		}
	}
	
}
