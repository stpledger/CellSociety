package XML;

/**
 * An extension of DataType for the Foraging Ants simulation
 * 
 * @author Harry Wang
 */

public class ForageData extends DataType{
	private int maxAnts;
	private double diff;
	private double evap;
	private double phero;
	private int antsBorn;
	private int startAge;
	
	public ForageData(String GameType, String Title, String width, String height, String randomAssign, String grid, String s, String maxAnts2, String diff2, String evap2, String phero2, String ant, String start) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
		super.setRandom(randomAssign);
		super.setGrid(grid);
		super.setStates(s);
		this.maxAnts = Integer.parseInt(maxAnts2);
		this.diff = Double.parseDouble(diff2);
		this.evap = Double.parseDouble(evap2);
		this.phero = Double.parseDouble(phero2);
		this.antsBorn = Integer.parseInt(ant);
		this.startAge = Integer.parseInt(start);
	}
	public double getDiff() {
		return diff;
	}
	public void setDiff(double d) {
		diff = d;
	}
	public double getEvap() {
		return evap;
	}
	public void setEvap(double d) {
		evap = d;
	}
	public double getPhero() {
		return phero;
	}
	public void setPhero(double d) {
		phero = d;
	}
	public int getMaxAnts() {
		return maxAnts;
	}
	public void setMaxAnts(int i) {
		maxAnts = i;
	}
	public int getAntsBorn() {
		return antsBorn;
	}
	public void setAntsBorn(int i) {
		antsBorn = i;
	}
	public int getStartAge() {
		return startAge;
	}
	public void setStartAge(int i) {
		startAge = i;
	}
}
