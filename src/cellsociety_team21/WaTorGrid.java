package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class WaTorGrid extends StandardGridWrap {
	public WaTorGrid(int row, int col, ArrayList<String> initStates, double cellSize,
					HashMap<String, Paint> colors, int initEnergy, int initRepro){
		super();
		updateGrid(row, col, initStates, cellSize, colors, initEnergy, initRepro);
		assignNeighborsNoDiag();
		assignNeighborsWrap(row, col);
	}
	
	private void updateGrid(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors,
			int initEnergy, int initRepro) {
		for (int i = 0; i < row; i++){
			for (int j = 0; j < col; j++){
				Point point = new Point(i, j);
				int x = i * (int) cellSize;
				int y = j * (int) cellSize;
				Cell cell = new WaTorCell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y, initEnergy, initRepro);
				getCellMap().put(point, cell);
			}
		}
	}
}
