package cellsociety_team21;

import java.util.ArrayList;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public  class Cell {
	
	private Shape myShape;
	private String myCurrentState;
	private String myNextState;
	private int myXPos;
	private int myYPos;
	private ArrayList<Cell> myNeighbors;


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
	
	public void switchState(){
		myCurrentState = myNextState;
		myNextState = null;
	}
	
	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}
	
	public void setNeighbors(ArrayList<Cell> neighbors){
		myNeighbors = neighbors;
	}
	
	public void setColor(Paint color){
		myShape.setFill(color);
	}
	
	public void setLocation(int x, int y){
		myXPos = x;
		myYPos = y;
	}
	
	public int getXLocation(){
		return myXPos;
	}
	
	public int getYLocation(){
		return myYPos;
	}

}
