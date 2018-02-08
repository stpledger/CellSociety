package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class StandardGrid extends Grid{
	private static final String GAMEOFLIFE = "GameOfLife";
    private static final String SEGREGATION = "Segregation";
    private static final String WATOR = "Wator";
    private static final String FIRE = "Fire";
	private HashMap<Point, Cell> cellMap; 
	private String gameType;
	
	public StandardGrid(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors, String game){
		this.cellMap = new HashMap<Point, Cell>();
		this.gameType = game;
		updateGrid(row, col, initStates, cellSize, colors);
		assignNeighborsNoDiag();
	}
	
	public StandardGrid() {
		this.cellMap = new HashMap<Point, Cell>();
	}
	
	private void updateGrid(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors) {
		for (int i = 0; i < row; i++){
			for (int j = 0; j < col; j++){
				Point point = new Point(i, j);
				int x = i * (int) cellSize;
				int y = j * (int) cellSize;
				//THE FOLLOWING LINE MUST CHANGE TO CHANGE SIMULATIONS.  NEED TO MAKE IT RESPOND TO GUI/XML INPUT INSTEAD
				Cell cell;
				if(gameType.equals(SEGREGATION)) cell = new SegregationCell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y);
				else if(gameType.equals(GAMEOFLIFE)) cell = new GOLCell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y);
				else cell = new FireCell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y);
				cellMap.put(point, cell);
			}
		}
	}
	public ArrayList<Cell> getCells(){
		return new ArrayList<Cell>(cellMap.values());
	}
	
	public void switchStates(HashMap<String,Paint> stateColors){
		for(Cell cell : getCells()){
			cell.switchState(stateColors);
		}
	}
	
	public HashMap<Point, Cell> getCellMap(){
		return cellMap;
	}
	
	public void assignNeighborsNoDiag(){
		for (Point myPoint : cellMap.keySet()){
			//ArrayList<Cell> neighbors = new ArrayList<Cell>();
			for (int i = (int) myPoint.getX() - 1; i < (int) myPoint.getX() + 2; i++){
				for (int j = (int) myPoint.getY() - 1; j < (int) myPoint.getY() + 2; j++){
					Point neighborsPoint = new Point(i, j);
					if (cellMap.containsKey(neighborsPoint) && !neighborsPoint.equals(myPoint) && 
							(myPoint.getX() == neighborsPoint.getX() || myPoint.getY() == neighborsPoint.getY())){
						cellMap.get(myPoint).addNeighbor(cellMap.get(new Point(i,j)));
					}
				}
			}
			//cellMap.get(myPoint).setNeighbors(neighbors);
		}
	}
}
