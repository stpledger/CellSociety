package XML;

public class FireData extends DataType {
	private double fireCatch;

	public FireData(String gameType, String title, String width, String height, String probCatch, String randomAssign, String grid, String s) {
		super.init(width, height);
		super.setGameType(gameType);
		super.setTitle(title);
		super.setRandom(randomAssign);
		super.setGrid(grid);
		this.fireCatch = Double.parseDouble(probCatch);
		super.setStates(s);
	}
	
	public Double getProbCatch() {
		return fireCatch;
	}
	public void setProbCatch(double newVal) {
		fireCatch = newVal;
	}

}
