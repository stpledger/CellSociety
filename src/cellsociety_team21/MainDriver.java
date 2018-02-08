package cellsociety_team21;
import visuals. *;

import java.awt.Desktop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainDriver extends Application{
	private static final String TITLE = "Cell Society Team 21";
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Gui init = new Gui(stage);
		Scene scene = init.getScene();
		stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
	}
}