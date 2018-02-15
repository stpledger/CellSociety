package cellsociety_team21;
import visuals. *;

import java.awt.Desktop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main Driver of the simulation. It instatiates a new simulation through the Gui class.
 * 
 * @author Harry Wang
 */

public class MainDriver extends Application{
	private static final String TITLE = "Cell Society Team 21";
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Gui init = new Gui(stage, false);
		Scene scene = init.getScene();
		stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
	}
}
