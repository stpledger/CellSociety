package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Paint;

public class StandardGridWrap extends StandardGrid {

	public StandardGridWrap(int row, int col, ArrayList<String> initStates, double cellSize,
			HashMap<String, Paint> colors) {
		super(row, col, initStates, cellSize, colors, "");
		assignNeighborsWrap(row, col);
	}

	public StandardGridWrap() {
		super();
	}

	public void assignNeighborsWrap(int row, int col){
		for(int i = 0; i < col; i++){
			getCellMap().get(new Point(i, 0)).addNeighbor("west",getCellMap().get(new Point(i, row - 1)));
		}
		for(int i = 0; i < col; i++){
			getCellMap().get(new Point(i, row - 1)).addNeighbor("east", getCellMap().get(new Point(i, 0)));
		}
		for(int i = 0; i < row; i++){
			getCellMap().get(new Point(0, i)).addNeighbor("north", getCellMap().get(new Point(col - 1, i)));
		}
		for(int i = 0; i < row; i++){
			getCellMap().get(new Point(col - 1, i)).addNeighbor("south", getCellMap().get(new Point(0, i)));
		}
	}
}
