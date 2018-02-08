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
	private static final String XML = ".xml";
	private static int FIRST = 0;
	private DataType data;
	private String title;
	private String height;
	private String width;
	private boolean fileFailed;

	
	public XMLParser(File file) {
		this.fileFailed = false;
		this.file = file;
		try {
			openFile(file);
		} catch (ParserConfigurationException e) {
			fileFailed = true;
		} 
	}

	private void openFile(File file) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        String filePath = file.getAbsolutePath();
        String fileType = filePath.substring(filePath.length()-4);
        if(!fileType.equals(XML)) {
        		fileFailed = true;
        		return;
        }

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
						assignTWH(eElement);
						data = new GoLData(GOL, title, width, height);
					}else if(eElement.getAttribute(GAMETYPE).equals(FIRE)) {
						assignTWH(eElement);
						String probCatch = eElement.getElementsByTagName(CATCHFIREPROBABILITY).item(FIRST).getTextContent();
						String initFireX = eElement.getElementsByTagName("startFireX").item(FIRST).getTextContent();
						String initFireY = eElement.getElementsByTagName("startFireY").item(FIRST).getTextContent();
						data = new FireData(FIRE, title, width, height, probCatch, initFireX, initFireY);
					}else if(eElement.getAttribute(GAMETYPE).equals(SEGREGATION)) {
						assignTWH(eElement);
						String ratio = eElement.getElementsByTagName(RATIO).item(FIRST).getTextContent();
						data = new SegregationData(SEGREGATION, title, width, height, ratio);
					}else if(eElement.getAttribute(GAMETYPE).equals(WATOR)) {
						assignTWH(eElement);
						String sEnergy = eElement.getElementsByTagName(STARTENERGY).item(FIRST).getTextContent();
						String repro = eElement.getElementsByTagName(REPRODUCTION).item(FIRST).getTextContent();
						String fEnergy = eElement.getElementsByTagName(ENERGYFISH).item(FIRST).getTextContent();
						data = new WatorData(WATOR, title, width, height, sEnergy, repro, fEnergy);
					}
				} catch (SAXException e) {
					fileFailed = true;
				}
				
		} catch (IOException e) {
				fileFailed = true;
		}
	}
	
	public void assignTWH(Element eElement) {
		title = eElement.getElementsByTagName(TITLE).item(FIRST).getTextContent();
		width = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
		height = eElement.getElementsByTagName(HEIGHT).item(FIRST).getTextContent();
	}
	
	public DataType getData() {
		return data;
	}
	
	public boolean isFailed() {
		return fileFailed;
	}

}
