package cellsociety_team21;

import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class StandardGrid extends Grid{
	private static final String GAMEOFLIFE = "GameOfLife";
    private static final String SEGREGATION = "Segregation";
    private static final String WATOR = "Wator";
    private static final String FIRE = "Fire";
    private static final String FORAGING = "Ants";
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
				else if(gameType.equals(FORAGING)) cell = new ForagingCell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y);
				else cell = new FireCell(new Rectangle(cellSize, cellSize, colors.get(initStates.get((i * row) + j))), initStates.get((i * row) + j), x, y);
				cellMap.put(point, cell);
			}
		}
	}
	public Collection<Cell> getCells(){
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
			int x = (int) myPoint.getX();
			int y = (int) myPoint.getY();
			if (getCellMap().containsKey(new Point(x, y-1))){
				getCellMap().get(myPoint).addNeighbor("west",getCellMap().get(new Point(x, y-1)));
			}
			if (getCellMap().containsKey(new Point(x, y+1))){
				getCellMap().get(myPoint).addNeighbor("east",getCellMap().get(new Point(x, y+1)));
			}
			if (getCellMap().containsKey(new Point(x+1, y))){
				getCellMap().get(myPoint).addNeighbor("south",getCellMap().get(new Point(x+1, y)));
			}
			if (getCellMap().containsKey(new Point(x-1, y))){
				getCellMap().get(myPoint).addNeighbor("north",getCellMap().get(new Point(x-1, y)));
			}
		}
	}
}
