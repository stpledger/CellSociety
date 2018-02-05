package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Paint;

public class StandardGridDiag extends StandardGrid {

	public StandardGridDiag(int row, int col, ArrayList<String> initStates, double cellSize,
			HashMap<String, Paint> colors) {
		super(row, col, initStates, cellSize, colors);
		assignNeighborsDiag();
	}
	
	public StandardGridDiag(){
		super();
	}
	
	private void assignNeighborsDiag(){
		for (Point myPoint : getCellMap().keySet()){
			for (int i = (int) myPoint.getX() - 1; i < (int) myPoint.getX() + 2; i+=2){
				for (int j = (int) myPoint.getY() - 1; j < (int) myPoint.getY() + 2; j+=2){
					Point neighborsPoint = new Point(i, j);
					if (getCellMap().containsKey(neighborsPoint)){
						getCellMap().get(myPoint).addNeighbor(getCellMap().get(neighborsPoint));
					}
				}
			}
		}
	}
}
