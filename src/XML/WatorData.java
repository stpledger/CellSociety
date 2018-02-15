package XML;
/**
 * An extension of DataType for the Predator-Prey simulation
 * 
 * @author Harry Wang
 */
public class WatorData extends DataType{
	private int startEnergy;
	private int reproduction;
	private int fishEnergy;
	
	public WatorData(String GameType, String Title, String width, String height, String sEnergy, String repro, String eFish, String randomAssign, String grid, String s) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
		super.setRandom(randomAssign);
		super.setGrid(grid);
		super.setStates(s);
		this.startEnergy = Integer.parseInt(sEnergy);
		this.reproduction = Integer.parseInt(repro);
		this.fishEnergy = Integer.parseInt(eFish);
	}
	public int getStartEnergy() {
		return startEnergy;
	}
	public int getReproduction() {
		return reproduction;
	}
	public int getFishEnergy() {
		return fishEnergy;
	}
	public void setStartEnergy(int newV) {
		startEnergy = newV;
	}
	public void setReproduction(int newV) {
		reproduction = newV;
	}
}
