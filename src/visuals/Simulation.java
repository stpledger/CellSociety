package visuals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import cellsociety_team21.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulation {
	public static final int FRAMES_PER_SECOND = 10;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final double CELLSIZE = 50.0;
    private static final int BUTTONHEIGHT = 50;
    private static final Color BACKGROUND = Color.ALICEBLUE;
    private static final String GAMEOFLIFE = "GameOfLife";
    private static final String FIRE = "Fire";
    private static final String MAIN = "Main";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String RESET = "Reset";
    private GridPane pane;
    private Group root;
    private Stage stage;
    private Scene scene;
    private String gameType;
    private Timeline animation;
    private int width;
    private int height;
    private boolean pause;
    private Ruleset ruleset;
    private StandardGrid grid;
    private int SIZEW;
    private int SIZEH;
    private double probCatch;
    private int fireX;
    private int fireY;
    private boolean diag;
    

	public Simulation(GridPane pane, Stage stage, Scene scene, String gameType, int width, int height, double probCatch, int x, int y) {
		this.SIZEW = ((int)CELLSIZE*width)+2*BUTTONHEIGHT;
		this.SIZEH = ((int)CELLSIZE*height)+2*BUTTONHEIGHT;
		this.pane = pane;
		this.stage = stage;
		this.scene = scene;
		this.gameType = gameType;
		this.width = width;
		this.height = height;
		this.probCatch = probCatch;
		this.fireX = x;
		this.fireY = y;
		this.diag = true;
		setUpSim();
	}
	private Button buttonMaker(String text, double x, double y) {
		Button temp = new Button(text);
		temp.setLayoutX(x);
		temp.setLayoutY(y);
		return temp;
	}
	private void setUpSim() {
		pause = false;
		pane.getChildren().clear();
		root = new Group();
		scene = new Scene(root, SIZEW, SIZEH, BACKGROUND);
		stage.setScene(scene);
		stage.show();
		Button mainMenu = buttonMaker(MAIN, 0, 0);
		Button start = buttonMaker(START, 0, BUTTONHEIGHT);
		Button stop = buttonMaker(STOP, 0, BUTTONHEIGHT*2);
		Button reset = buttonMaker(RESET, 0, BUTTONHEIGHT*3);
		root.getChildren().addAll(mainMenu, start, stop, reset);
		initGrid();
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(animation!=null)animation.stop();
				Gui restart = new Gui(stage);
				Scene scene = restart.getScene();
		        stage.setScene(scene);
		        stage.show();
			}
		});
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				pause = false;
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
		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				setUpSim();
				pause = true;
			}
		});
	}
	private void initGrid() {
		ArrayList<String> initStates = new ArrayList<String>();
		if(gameType == GAMEOFLIFE) {
			initStates = GoLStates(initStates);
			ruleset = new GOLRuleset();
		}else if(gameType == FIRE) {
			initStates = FireStates(initStates);
			ruleset = new FireRuleset(probCatch);
		}
		//add other gametypes
		HashMap<String, Paint> colors = ruleset.getStateColors();
		grid = new StandardGrid(width, height, initStates, CELLSIZE, colors, diag);
		HashMap<Point, Cell> map = grid.getCellMap();
		for(Point c: map.keySet()) {
			Shape temp = map.get(c).getShape();
			temp.setLayoutX(map.get(c).getX()+50);
			temp.setLayoutY(map.get(c).getY()+50);
			root.getChildren().add(temp);
			
		}
	}
	private ArrayList<String> FireStates(ArrayList<String> init){
		int total = width*height;
		for(int i=0;i<=total+1;i++) {
			if(i==fireX*fireY)init.add("burning");
			init.add("tree");
		}
		return init;
	}
	private ArrayList<String> GoLStates(ArrayList<String> init){
		int total = width*height;
		for(int i=0;i<=total+1;i++) {
			int a = (int) Math.round(Math.random());
			if(a==1)init.add("alive");
			else init.add("dead");
		}
		return init;
	}
	private void step(double secondDelay) {
		if(pause)return;
		ruleset.updateGrid(grid);
	}
}
