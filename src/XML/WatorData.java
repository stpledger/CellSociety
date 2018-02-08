package XML;

public class WatorData extends DataType{
	private int startEnergy;
	private int reproduction;
	private int fishEnergy;
	
	public WatorData(String GameType, String Title, String width, String height, String sEnergy, String repro, String eFish) {
		super.init(width, height);
		super.setGameType(GameType);
		super.setTitle(Title);
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
