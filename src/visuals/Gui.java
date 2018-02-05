package visuals;

import XML.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Gui {
	private GridPane pane;
	private Scene scene;
	private Desktop desktop;
	private Stage stage;
	private int width;
	private int height;
	private String gameType;
	private static final String main = "Main Menu";
	private static final String widthText = "Width:";
	private static final String heightText = "Height:";
	private static final String SIM = "Simulation";
	private static final String FILE = "File";
	private static final int SIZE = 450;
	
	public Gui(Stage stage) {
		this.stage = stage;
		this.desktop = Desktop.getDesktop();
		this.pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    
	    this.scene = new Scene(pane, SIZE, SIZE);
	    
	    
	    Text splash = new Text(main);
	    splash.setFont(Font.font("Arial", FontWeight.NORMAL,20));
	    Text sim = new Text();
	    sim.setFont(Font.font("Arial", FontWeight.NORMAL,20));
	
	    Label widthLabel = new Label(widthText);
	    pane.add(widthLabel, 0, 1);
	    final TextField widthText = new TextField();
	    pane.add(widthText, 1, 1);
	    Label heightLabel = new Label(heightText);
	    pane.add(heightLabel,0,2);
	    final TextField heightText = new TextField();
	    pane.add(heightText, 1, 2);
	
	    Button calculateButton = new Button(SIM);
	    Button fileSelector = new Button(FILE);
	    HBox file = new HBox(10);
	    file.setAlignment(Pos.CENTER);
	    file.getChildren().add(fileSelector);
	    HBox hbox = new HBox(10);
	    hbox.setAlignment(Pos.BOTTOM_RIGHT);
	    hbox.getChildren().add(calculateButton);
	    pane.add(splash, 0, 0);
	    pane.add(sim, 1, 0);
	    pane.add(hbox, 1, 4);      
	    pane.add(file, 0, 4);
	    FileChooser filechooser = new FileChooser();
	    calculateButton.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent event) {
	        		if(!widthText.getText().equals("")) width = Integer.parseInt(widthText.getText());
	        		if(!heightText.getText().equals("")) height = Integer.parseInt(heightText.getText());
	        		if(gameType == null) {
	        			System.out.println("please choose an xml file");//output some form of error
	        			return;
	        		}
	        		Simulation run = new Simulation(pane, stage, scene, gameType, width, height);
	        }
	    });
	    fileSelector.setOnAction(new EventHandler<ActionEvent>() {
	    		public void handle(ActionEvent event) {
	    			File file = filechooser.showOpenDialog(stage);
	    			if(file != null) {
	    				openFile(file);
	    				widthText.setText(Integer.toString(width));
	    				heightText.setText(Integer.toString(height));
	    				sim.setText(gameType);
	    			}
	    		}
	    });
	}
	private void openFile(File file) {
        XMLParser parsed = new XMLParser(file);
        width = parsed.getData().getWidthInt();
        height = parsed.getData().getHeightInt();
        gameType = parsed.getData().getGameType();
    }
	public Scene getScene() {
		return scene;
	}
}
