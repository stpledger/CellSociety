package XML;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {
	private File file;
	private static final String DATA = "data";
	private static final String GOL = "GameOfLife";
	private static final String FIRE = "Fire";
	private static final String SEGREGATION = "Segregation";
	private static final String WATOR = "Wator";
	private static final String GAMETYPE = "gameType";
	private static final String TITLE = "title";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String RATIO = "ratio";
	private static final String CATCHFIREPROBABILITY = "probCatch";
	private static final String STARTENERGY = "startEnergy";
	private static final String REPRODUCTION = "reproduction";
	private static final String ENERGYFISH = "energyFish";
	private static int FIRST = 0;
	private static String whichGame;
	private GoLData gol;
	private FireData fire;
	private SegregationData seg;
	private WatorData wator;
	
	public XMLParser(File file) {
		this.file = file;
		try {
			openFile(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
	}

	private void openFile(File file) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
		try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc;
				try {
					doc = dBuilder.parse(file);
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName(DATA);
					Node nNode = nList.item(FIRST);
					Element eElement = (Element) nNode;
					if(eElement.getAttribute(GAMETYPE).equals(GOL)) {
						whichGame = GOL;
						String title = eElement.getElementsByTagName(TITLE).item(FIRST).getTextContent();
						String width = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String height = eElement.getElementsByTagName(HEIGHT).item(FIRST).getTextContent();
						gol = new GoLData(GOL, title, width, height);
						//return life;
					}else if(eElement.getAttribute(GAMETYPE).equals(FIRE)) {
						whichGame = FIRE;
						String title = eElement.getElementsByTagName(TITLE).item(FIRST).getTextContent();
						String width = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String height = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String probCatch = eElement.getElementsByTagName(CATCHFIREPROBABILITY).item(FIRST).getTextContent();
						String initFireX = eElement.getElementsByTagName("startFireX").item(FIRST).getTextContent();
						String initFireY = eElement.getElementsByTagName("startFireY").item(FIRST).getTextContent();
						fire = new FireData(FIRE, title, width, height, probCatch, initFireX, initFireY);
						//return life;
					}else if(eElement.getAttribute(GAMETYPE).equals(SEGREGATION)) {
						whichGame = SEGREGATION;
						String title = eElement.getElementsByTagName(TITLE).item(FIRST).getTextContent();
						String width = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String height = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String ratio = eElement.getElementsByTagName(RATIO).item(FIRST).getTextContent();
						seg = new SegregationData(SEGREGATION, title, width, height, ratio);
						//segregation has been parsed, need to add that into simulation/GUI
					}else if(eElement.getAttribute(GAMETYPE).equals(WATOR)) {
						whichGame = WATOR;
						String title = eElement.getElementsByTagName(TITLE).item(FIRST).getTextContent();
						String width = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String height = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String sEnergy = eElement.getElementsByTagName(STARTENERGY).item(FIRST).getTextContent();
						String repro = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						String fEnergy = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
						wator = new WatorData(WATOR, title, width, height, sEnergy, repro, fEnergy);
					}
				} catch (SAXException e) {
					e.printStackTrace();
				}
				
		} catch (IOException e) {
				e.printStackTrace();
		}
		//return null;
	}
	public String whichGame() {
		return whichGame;
	}
	public GoLData getGOLData() {
		if(gol!=null) return gol;
		return null;//change this later since we don't want nulls
	}
	public FireData getFireData() {
		if(fire!=null) return fire;
		return null;//change this later since we don't want nulls
	}
	public SegregationData getSegData() {
		if(seg!=null) return seg;
		return null;//change this later since we don't want nulls
	}
	public WatorData getWatorData() {
		if(wator!=null) return wator;
		return null;
	}
}
