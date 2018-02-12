package XML;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;

import cellsociety_team21.*;

public class XMLSaver {
    private FireData fire;
    private SegregationData seg;
    private WatorData wator;
    private boolean SAVED;
    private static final String DATA = "data";
    private static final String GAMETYPE = "gameType";
    private static final String TITLE = "title";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String RANDOMASSIGN = "randomAssign";
    private static final String FALSE = "False";
    private static final String TRUE = "True";
    private static final String GRID = "grid";
    private static final String STATES = "states";
    private static final String COMMA = ",";
    private static final String CATCHFIREPROBABILITY = "probCatch";
	private static final String STARTENERGY = "startEnergy";
	private static final String REPRODUCTION = "reproduction";
	private static final String ENERGYFISH = "energyFish";
	private static final String RATIO = "ratio";
	private static final String GOL = "GameOfLife";
	private static final String FIRE = "Fire";
	private static final String SEGREGATION = "Segregation";
	private static final String WATOR = "Wator";
	private static final String USER = "user.dir";
	private static final String XML = ".xml";
    
	public XMLSaver(DataType data, StandardGrid grid, boolean gridLines, int width, int height) {
		SAVED = false;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			//root element
			Element rootElement = doc.createElement(DATA);
			doc.appendChild(rootElement);
			Attr gameType = doc.createAttribute(GAMETYPE);
			gameType.setValue(data.getGameType());
			rootElement.setAttributeNode(gameType);
			
			Element title = doc.createElement(TITLE);
			title.appendChild(doc.createTextNode(data.getTitle()));
			rootElement.appendChild(title);
			
			Element widthT = doc.createElement(WIDTH);
			widthT.appendChild(doc.createTextNode(Integer.toString(width)));
			rootElement.appendChild(widthT);
			
			Element heightT = doc.createElement(HEIGHT);
			heightT.appendChild(doc.createTextNode(Integer.toString(height)));
			rootElement.appendChild(heightT);
			
			Element randomAssign = doc.createElement(RANDOMASSIGN);
			randomAssign.appendChild(doc.createTextNode(FALSE));
			rootElement.appendChild(randomAssign);
			
			Element gridL = doc.createElement(GRID);
			String g;
			if(gridLines) g=TRUE;
			else g = FALSE;
			gridL.appendChild(doc.createTextNode(g));
			rootElement.appendChild(gridL);
			
			Element states = doc.createElement(STATES);
			HashMap<Point, Cell> map = grid.getCellMap();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < width; i++){
				for (int j = 0; j < height; j++){
					Point point = new Point(i, j);
					sb.append(map.get(point).getCurrentState()+COMMA);
				}
			}
			String res = sb.toString();
			res = res.substring(0, res.length()-1);
			states.appendChild(doc.createTextNode(res));
			rootElement.appendChild(states);
			
			if(data.getGameType().equals(FIRE)) {
				fire = (FireData) data;
				Element probCatch = doc.createElement(CATCHFIREPROBABILITY);
				probCatch.appendChild(doc.createTextNode(Double.toString(fire.getProbCatch())));
				rootElement.appendChild(probCatch);
			}else if(data.getGameType().equals(WATOR)) {
				wator = (WatorData) data;
				Element startE = doc.createElement(STARTENERGY);
				startE.appendChild(doc.createTextNode(Integer.toString(wator.getStartEnergy())));
				rootElement.appendChild(startE);
				
				Element repro = doc.createElement(REPRODUCTION);
				repro.appendChild(doc.createTextNode(Integer.toString(wator.getReproduction())));
				rootElement.appendChild(repro);
				
				Element fishE = doc.createElement(ENERGYFISH);
				fishE.appendChild(doc.createTextNode(Integer.toString(wator.getFishEnergy())));
				rootElement.appendChild(fishE);
			}else if(data.getGameType().equals(SEGREGATION)) {
				seg = (SegregationData) data;
				Element ratio = doc.createElement(RATIO);
				ratio.appendChild(doc.createTextNode(Double.toString(seg.getRatio())));
				rootElement.appendChild(ratio);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        String path = System.getProperty(USER);
	        StreamResult result = new StreamResult(new File(path+"/data/simSaves"+data.getGameType()+XML));
	        transformer.transform(source, result);
	        SAVED = true;
		}catch(Exception e){
			SAVED = false;
		}
	}
	public boolean isSaved() {
		return SAVED;
	}
	
}
