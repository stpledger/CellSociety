package XML;

public class GoLData extends DataType{
	
	public GoLData(String GameType, String Title, String width, String height) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
	}
}
