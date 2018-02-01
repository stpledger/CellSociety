package cellsociety_team21;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class maindriver extends Application{
	private static final String TITLE = "Cell Society Team 21";
	private Group root;
	private Scene scene;
	private GridPane pane;
	private Desktop desktop = Desktop.getDesktop();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle(TITLE);
		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
	    pane.setHgap(10);
	    pane.setVgap(10);
	    pane.setPadding(new Insets(25, 25, 25, 25));
        scene = new Scene(pane, 450, 450);

        Text splash = new Text("Main Menu");
        splash.setFont(Font.font("Arial", FontWeight.NORMAL,20));

        Label total = new Label("Width:");
        pane.add(total, 0, 1);
        final TextField width = new TextField();
        pane.add(width, 1, 1);
        Label percent = new Label("Height:");
        pane.add(percent,0,2);
        final TextField height = new TextField();
        pane.add(height, 1, 2);

        Button calculateButton = new Button("Simulation");
        Button fileSelector = new Button("File");
        HBox file = new HBox(10);
        file.setAlignment(Pos.CENTER);
        file.getChildren().add(fileSelector);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(calculateButton);
        pane.add(splash, 0, 0, 2, 1);
        pane.add(hbox, 1, 4);      
        FileChooser filechooser = new FileChooser();
        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });
        fileSelector.setOnAction(new EventHandler<ActionEvent>() {
        		public void handle(ActionEvent event) {
        			File file = filechooser.showOpenDialog(stage);
        			if(file != null) {
        				openFile(file);
        			}
        		}
        });
        pane.add(file, 0, 4);
        stage.setScene(scene);
        stage.show();
	}
	private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println("rip");
        }
    }
	
	
}
