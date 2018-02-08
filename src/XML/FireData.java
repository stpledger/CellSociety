package XML;

public class FireData extends DataType {
	private double fireCatch;
	private int fireX;
	private int fireY;

	public FireData(String gameType, String title, String width, String height, String probCatch, String x, String y) {
		super.init(width, height);
		super.setGameType(gameType);
		super.setTitle(title);
		this.fireCatch = Double.parseDouble(probCatch);
		this.fireX = Integer.parseInt(x);
		this.fireY = Integer.parseInt(y);
	}
	
	public Double getProbCatch() {
		return fireCatch;
	}
	public int getFireX() {
		return fireX;
	}
	public int getFireY() {
		return fireY;
	}
	public void setProbCatch(double newVal) {
		fireCatch = newVal;
	}

}
