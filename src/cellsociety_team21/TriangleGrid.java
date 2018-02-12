package cellsociety_team21;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class TriangleGrid extends Grid {

	private static final String GAMEOFLIFE = "GameOfLife";
    private static final String SEGREGATION = "Segregation";
    private static final String WATOR = "Wator";
    private static final String FIRE = "Fire";
	private HashMap<Point, Cell> cellMap; 
	private String gameType;
	
	public TriangleGrid(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors, String game){
		this.cellMap = new HashMap<Point, Cell>();
		this.gameType = game;
		updateGrid(row, col, initStates, cellSize, colors);
		assignNeighborsNoDiag();
	}
	
	public TriangleGrid() {
		this.cellMap = new HashMap<Point, Cell>();
	}
	
	private void updateGrid(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors) {
		if(gameType.equals(GAMEOFLIFE)){
			GOLInit(row, col, initStates, cellSize, colors);
		}
		if(gameType.equals(SEGREGATION)){
			//SegInit(row, col, initStates, cellSize, colors);
		}
		if(gameType.equals(WATOR)){
			//WatorInit(row, col, initStates, cellSize, colors);
		}
		if(gameType.equals(FIRE)){
			//GFireInit(row, col, initStates, cellSize, colors);
		}
	}
	
	private void GOLInit(int row, int col, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors){
		for(int i = 0; i < row; i+=2){
			for(int j = 0; j < col; j += 2){
				
			}
		}
	}
	
	private Shape upTriangle(int i, int j, ArrayList<String> initStates, double cellSize, HashMap<String, Paint> colors){
		double topCoord = j * cellSize + (0.5 * cellSize);
		double leftCoord;
		return null;
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
