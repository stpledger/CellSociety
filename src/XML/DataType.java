package XML;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class DataType {
	private String GameType;
	private String TITLE;
	private String width;
	private String height;
	private boolean randomAssign;
	private boolean grid;
	private static final String DEFAULT = "10";
	private static final String EMPTY = "";
	private static final String TRUE = "True";
	private static final String COMMA = ",";
	private ArrayList<String> states;
	
	
	public String getGameType() {
		return GameType;
	}
	public void setGameType(String Game) {
		this.GameType = Game;
	}
	public String getTitle() {
		return TITLE;
	}
	public void setTitle(String Title) {
		this.TITLE = Title;
	}
	public String getWidth() {
		return width;
	}
	public String getHeight() {
		return height;
	}
	public void setWidth(String SetW) {
		this.width=SetW;
	}
	public void setHeight(String SetH) {
		this.height=SetH;
	}
	public int getWidthInt() {
		return Integer.parseInt(width);
	}
	public int getHeightInt() {
		return Integer.parseInt(height);
	}
	public void setRandom(String random) {
		randomAssign = random.equals(TRUE);
	}
	public boolean isRandom() {
		return randomAssign;
	}
	public void setGrid(String gridT) {
		grid = gridT.equals(TRUE);
	}
	public boolean isGrid() {
		return grid;
	}
	public void setStates(String s) {
		states = new ArrayList<String>(Arrays.asList(s.split(COMMA)));
	}
	public ArrayList<String> getStates(){
		return states;
	}
	public void init(String SetW, String SetH) {
		if(SetW.equals(EMPTY) && SetH.equals(EMPTY)) {
			setWidth(DEFAULT);
			setHeight(DEFAULT);
		}else if(SetW.equals(EMPTY) && !SetH.equals(EMPTY)){
			setWidth(DEFAULT);
			setHeight(SetH);
		}else if(!SetW.equals(EMPTY) && SetH.equals(EMPTY)) {
			setWidth(SetW);
			setHeight(DEFAULT);
		}else {
			setWidth(SetW);
			setHeight(SetH);
		}
	}
}
