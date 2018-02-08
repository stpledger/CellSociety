package visuals;

import XML.*;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Gui {
	private Scene scene;
	private Stage stage;
	private String gameType;
	private static final String main = "Main Menu";
	private static final String SIM = "Simulation";
	private static final String SIMULATION = "Sim: ";
	private static final String FILE = "File";
	private static final String NOFILE = "Incorrect XML File";
	private static final String BUTTON = "button";
	private static final String LABEL = "label";
	private static final String TEXT = "text";
	private static final String TEXTFIELD = "textfield";
	private static final String FONT = "Arial";
	private static final int SIZEW = 250;
	private static final int SIZEH = 200;
	private DataType data;
	private Group root;
	private XMLParser parsed;
	
	public Gui(Stage stage) {
		this.stage = stage;
		this.root = new Group();
	    this.scene = new Scene(root, SIZEW, SIZEH);
	    
	    Text splash = (Text) nodeMaker(main, (SIZEW/2)-50, 30, TEXT);
	    splash.setFont(Font.font(FONT, FontWeight.NORMAL,20));
	    Text sim = (Text) nodeMaker(SIMULATION, (SIZEW/2)-120, 70, TEXT);
	    sim.setFont(Font.font(FONT, FontWeight.NORMAL,20));
	    Button calculateButton = (Button) nodeMaker(SIM, 2*(SIZEW/4), SIZEH - 50, BUTTON);
	    Button fileSelector = (Button) nodeMaker(FILE, (SIZEW/4), SIZEH - 50, BUTTON);
	    root.getChildren().addAll(splash, sim, calculateButton, fileSelector);

	    FileChooser filechooser = new FileChooser();
	    calculateButton.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent event) {
	        		if(gameType == null || parsed.isFailed()) {
	        			sim.setText(SIMULATION+NOFILE);
	        			return;
	        		}
	        		Simulation run = new Simulation(stage, scene, gameType, data);
	        }
	    });
	    fileSelector.setOnAction(new EventHandler<ActionEvent>() {
	    		public void handle(ActionEvent event) {
	    			File file = filechooser.showOpenDialog(stage);
	    			if(file != null) {
	    				openFile(file);
	    				if(parsed.isFailed()) {
	    					sim.setText(SIMULATION + NOFILE);
	    					return;
	    				}
	    				sim.setText(SIMULATION + data.getGameType());
	    			}
	    		}
	    });
	}

	private Node nodeMaker(String text, double x, double y, String type) {
		Node temp;
		if(type.equals(BUTTON)) temp = new Button(text);
		else if(type.equals(LABEL)) temp = new Label(text);
		else if(type.equals(TEXT)) {
			temp = new Text(text);
		}
		else temp = new TextField();//must be textfield
		temp.setLayoutX(x);
		temp.setLayoutY(y);
		return temp;
	}
	private void openFile(File file) {
        parsed = new XMLParser(file);
        if(parsed.isFailed()) {
        		return;
        }
        data = parsed.getData();
        gameType = data.getGameType();
    }
	public Scene getScene() {
		return scene;
	}
}
