package XML;

public abstract class DataType {
	private String GameType;
	private String TITLE;
	protected String width;
	protected String height;
	private static final String DEFAULT = "10";
	private static final String EMPTY = "";
	
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
