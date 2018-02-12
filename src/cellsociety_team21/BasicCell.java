package cellsociety_team21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class BasicCell implements Cell {
	
	private Shape myShape;
	private String myCurrentState;
	private String myNextState;
	private int myXPos;
	private int myYPos;
	private Map<String, Cell> myNeighbors;
	
	BasicCell(Shape shape, String initState, int x, int y){
		this.myShape = shape;
		this.myCurrentState = initState;
		this.myXPos = x;
		this.myYPos = y;
		myNeighbors = new HashMap<String, Cell>();
	}

	public Shape getShape(){
		return myShape;
	}
	
	public String getCurrentState(){
		return myCurrentState;
	}
	
	public String getNextState(){
		return myNextState;
	}
	
	public void setNextState(String state){
		myNextState = state;
	}
	
	public void switchState(Map<String, Paint> stateColors){
		this.setCurrentState(this.getNextState());
		myNextState = null;
		this.setColor(stateColors.get(myCurrentState));
	}
	
	public void addNeighbor(String whichNeighbor, Cell neighbor){
		myNeighbors.put(whichNeighbor, neighbor);
	}
	
	public void setLocation(int x, int y){
		myXPos = x;
		myYPos = y;
	}
	
	public int getX(){
		return myXPos;
	}
	
	public int getY(){
		return myYPos;
	}
	
	protected List<Cell> getNeighbors(){
		return new ArrayList<Cell>(myNeighbors.values());
	}
	
	public Map<String, Cell> getNeighborsMap(){
		return myNeighbors;
	}
	
	private void setCurrentState(String state){
		myCurrentState = state;
	}
	
	protected void setColor(Paint color){
		myShape.setFill(color);
	}
	
}
