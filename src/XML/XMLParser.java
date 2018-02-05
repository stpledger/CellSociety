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
	private static final String GAMETYPE = "gameType";
	private static final String TITLE = "title";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String CATCHFIREPROBABILITY = "probCatch";
	private static int FIRST = 0;
	private static String whichGame;
	GoLData gol;
	FireData fire;
	
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
}
