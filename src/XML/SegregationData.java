package XML;

/**
 * An extension of DataType for the Segregation simulation
 * 
 * @author Harry Wang
 */

public class SegregationData extends DataType{
	private double ratio;
	public SegregationData(String GameType, String Title, String width, String height, String Tratio, String randomAssign, String grid, String s) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
		super.setRandom(randomAssign);
		super.setGrid(grid);
		super.setStates(s);
		this.ratio = Double.parseDouble(Tratio);
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double newVal) {
		ratio = newVal;
	}
}
