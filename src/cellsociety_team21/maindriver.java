package cellsociety_team21;
import visuals. *;

import java.awt.Desktop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class maindriver extends Application{
	private static final String TITLE = "Cell Society Team 21";
	private Scene scene;
	private GridPane pane;
	private Desktop desktop = Desktop.getDesktop();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Gui start = new Gui(stage);
		Scene scene = start.getScene();
		stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
	}
}
