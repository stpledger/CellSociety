package XML;

public class SegregationData extends DataType{
	private double ratio;
	public SegregationData(String GameType, String Title, String width, String height, String Tratio) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
		this.ratio = Double.parseDouble(Tratio);
	}
	public double getRatio() {
		return ratio;
	}
}
