package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class StandardGrid extends Grid{
	private HashMap<Point, Cell> cellMap;
	
	StandardGrid(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors){
		cellMap = new HashMap<Point, Cell>();
		for (int i = 0; i < row; i++){
			for (int j = 0; j < col; j++){
				Point point = new Point(i, j);
				int x = i * (int) cellSize;
				int y = j * (int) cellSize;
				Cell cell = new Cell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y);
				cellMap.put(point, cell);
			}
		}
	}
	
	public void assignNeighbors(){
		for (Point myPoint : cellMap.keySet()){
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			for (int i = (int) myPoint.getX() - 1; i < (int) myPoint.getX() + 2; i++){
				for (int j = (int) myPoint.getY() - 1; j < (int) myPoint.getY() + 2; j++){
					Point neighborsPoint = new Point(i, j);
					if (cellMap.containsKey(neighborsPoint) && !neighborsPoint.equals(myPoint)){
						neighbors.add(cellMap.get(new Point(i,j)));
					}
				}
			}
			cellMap.get(myPoint).setNeighbors(neighbors);
		}
	}
}
