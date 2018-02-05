package visuals;

import java.util.ArrayList;
import java.util.HashMap;

import cellsociety_team21.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulation {
	public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private GridPane pane;
    private Stage stage;
    private Scene scene;
    private String gameType;
    private Timeline animation;
    private int width;
    private int height;
    private boolean pause;
    private Ruleset ruleset;
    private StandardGrid grid;

	public Simulation(GridPane pane, Stage stage, Scene scene, String gameType, int width, int height) {
		this.pane = pane;
		this.stage = stage;
		this.scene = scene;
		this.gameType = gameType;
		this.width = width;
		this.height = height;
		setUpSim();
	}
	private void setUpSim() {
		pause = false;
		pane.getChildren().clear();
		Button mainMenu = new Button("Main");
		pane.setAlignment(Pos.TOP_LEFT);
		pane.add(mainMenu, 0, 1);
		Button start = new Button("Start");
		pane.add(start,  0, 2);
		Button stop = new Button("Stop");
		pane.add(stop, 0, 3);
		initGrid();
		ruleset = new GOLRuleset();
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				animation.stop();
				Gui restart = new Gui(stage);
				Scene scene = restart.getScene();
		        stage.setScene(scene);
		        stage.show();
			}
		});
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		                e -> step(SECOND_DELAY));
				if(animation!=null) {
					animation.stop();
				}
				animation = new Timeline();
				animation.setCycleCount(Timeline.INDEFINITE);
				animation.getKeyFrames().add(frame);
				animation.play();
			}
		});
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				pause = true;
			}
		});
	}
	private void initGrid() {
		ArrayList<String> initStates = new ArrayList<String>();
		int total = width*height;
		double cellSize = 1.0;//NEED TO RUN THROUGH THIS
		HashMap<String, Paint> colors = new HashMap<String, Paint>();
		grid = new StandardGrid(height, width, initStates, cellSize, colors);
	}
	private void step(double secondDelay) {
		if(pause)return;
		System.out.println("yolo");
		ruleset.updateGrid(grid);
	}
}
