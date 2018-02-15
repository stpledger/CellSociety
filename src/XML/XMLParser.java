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

/**
 * This class handles all the XML parsing. For each simulation's XML, it reads in the data and assigns it accordingly to a DataType
 * 
 * @author Harry Wang
 */

public class XMLParser {
	private File file;
	private static final String DATA = "data";
	private static final String GOL = "GameOfLife";
	private static final String FIRE = "Fire";
	private static final String SEGREGATION = "Segregation";
	private static final String WATOR = "Wator";
	private static final String FORAGE = "Ants";
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
	private static final String RANDOMASSIGN = "randomAssign";
	private static final String GRID = "grid";
	private static final String STATES = "states";
	private static final String TRUE = "True";
	private static final String MAXANTS = "maxAntsPerCell";
	private static final String DIFF = "diffusion";
	private static final String EVAP = "evaporation";
	private static final String PHERO = "phero";
	private static final String ANTSBORN = "antsBorn";
	private static final String STARTAGE = "startAge";
	private static final int FILE_EXTENSION = 4;
	private static int FIRST = 0;
	private DataType data;
	private String title;
	private String height;
	private String width;
	private String randomAssign;
	private String grid;
	private String states;
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
	
	/*
	 * This open's the file and tries to assign all the attributes to a DataType extension
	 */
	private void openFile(File file) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        String filePath = file.getAbsolutePath();
        String fileType = filePath.substring(filePath.length()-FILE_EXTENSION);
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
						try {
							assignTWH(eElement);
						}catch(Exception e) {
							fileFailed = true;
							return;
						}
						data = new GoLData(GOL, title, width, height, randomAssign, grid, states);
							
					}else if(eElement.getAttribute(GAMETYPE).equals(FIRE)) {
						try {
							assignTWH(eElement);
						}catch(Exception e) {
							fileFailed = true;
							return;
						}
						String probCatch = eElement.getElementsByTagName(CATCHFIREPROBABILITY).item(FIRST).getTextContent();
						data = new FireData(FIRE, title, width, height, probCatch, randomAssign, grid, states);
					}else if(eElement.getAttribute(GAMETYPE).equals(SEGREGATION)) {
						try {
							assignTWH(eElement);
						}catch(Exception e) {
							fileFailed = true;
							return;
						}
						String ratio = eElement.getElementsByTagName(RATIO).item(FIRST).getTextContent();
						data = new SegregationData(SEGREGATION, title, width, height, ratio, randomAssign, grid, states);
					}else if(eElement.getAttribute(GAMETYPE).equals(WATOR)) {
						try {
							assignTWH(eElement);
						}catch(Exception e) {
							fileFailed = true;
							return;
						}
						String sEnergy = eElement.getElementsByTagName(STARTENERGY).item(FIRST).getTextContent();
						String repro = eElement.getElementsByTagName(REPRODUCTION).item(FIRST).getTextContent();
						String fEnergy = eElement.getElementsByTagName(ENERGYFISH).item(FIRST).getTextContent();
						data = new WatorData(WATOR, title, width, height, sEnergy, repro, fEnergy, randomAssign, grid, states);
					}else if(eElement.getAttribute(GAMETYPE).equals(FORAGE)) {
						try {
							assignTWH(eElement);
						}catch(Exception e) {
							fileFailed = true;
							return;
						}
						String maxAnts = eElement.getElementsByTagName(MAXANTS).item(FIRST).getTextContent();
						String diff = eElement.getElementsByTagName(DIFF).item(FIRST).getTextContent();
						String evap = eElement.getElementsByTagName(EVAP).item(FIRST).getTextContent();
						String phero = eElement.getElementsByTagName(PHERO).item(FIRST).getTextContent();
						String ant = eElement.getElementsByTagName(ANTSBORN).item(FIRST).getTextContent();
						String start = eElement.getElementsByTagName(STARTAGE).item(FIRST).getTextContent();
						data = new ForageData(FORAGE, title, width, height, randomAssign, grid, states, maxAnts, diff, evap, phero, ant, start);
					}
				} catch (SAXException e) {
					fileFailed = true;
					return;
				}
				
		} catch (IOException e) {
			fileFailed = true;
			return;
		}
	}
	
	/*
	 * Since all the simulations and DataType extensions have these elements in common, I abstracted this out into a separate function
	 */
	public void assignTWH(Element eElement){
		title = eElement.getElementsByTagName(TITLE).item(FIRST).getTextContent();
		width = eElement.getElementsByTagName(WIDTH).item(FIRST).getTextContent();
		height = eElement.getElementsByTagName(HEIGHT).item(FIRST).getTextContent();
		randomAssign = eElement.getElementsByTagName(RANDOMASSIGN).item(FIRST).getTextContent();
		states = eElement.getElementsByTagName(STATES).item(FIRST).getTextContent();
		grid = eElement.getElementsByTagName(GRID).item(FIRST).getTextContent();
		if(!randomAssign.equals(TRUE)) states = eElement.getElementsByTagName(STATES).item(FIRST).getTextContent();
	}
	
	public DataType getData() {
		return data;
	}
	
	public boolean isFailed() {
		return fileFailed;
	}

}
