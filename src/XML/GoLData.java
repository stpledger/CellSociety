package XML;

public class GoLData extends DataType{
	
	public GoLData(String GameType, String Title, String width, String height, String randomAssign, String grid, String s) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
		super.setRandom(randomAssign);
		super.setGrid(grid);
		super.setStates(s);
	}
}
