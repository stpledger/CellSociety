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
	DataType game;
	
	public XMLParser(File file) {
		this.file = file;
		try {
			game = openFile(file);
			System.out.println();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
	}

	private DataType openFile(File file) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
		try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc;
				try {
					doc = dBuilder.parse(file);
					doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("data");
					Node nNode = nList.item(0);
					Element eElement = (Element) nNode;
					if(eElement.getAttribute("gameType").equals("GameOfLife")) {
						String title = eElement.getElementsByTagName("title").item(0).getTextContent();
						String width = eElement.getElementsByTagName("width").item(0).getTextContent();
						String height = eElement.getElementsByTagName("height").item(0).getTextContent();
						GoLData life = new GoLData("GameOfLife", title, width, height);
						return life;
					}
				} catch (SAXException e) {
					e.printStackTrace();
				}
				
		} catch (IOException e) {
				e.printStackTrace();
		}
		return null;
	}
	public DataType getData() {
		return game;
	}
}
