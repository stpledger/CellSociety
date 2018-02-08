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
	public static final int FRAMES_PER_SECOND = 100;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final double CELLSIZE = 50.0;
    private static final int BUTTONHEIGHT = 50;
    private static final int BUTTONHEIGHTPAD = BUTTONHEIGHT+10;
    private static final Color BACKGROUND = Color.ALICEBLUE;
    private static final String GAMEOFLIFE = "GameOfLife";
    private static final String SEGREGATION = "Segregation";
    private static final String WATOR = "Wator";
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
    private double ratio;
    private int TOTAL;
    private int startEnergy;
    private int reproduction;
    private int fishEnergy;
    private WaTorRuleset WaTorRules;
    private WaTorGrid WaTorgrid;
    

	public Simulation(GridPane pane, Stage stage, Scene scene, String gameType, int width, int height, double probCatch, int x, int y, double ratio, int sEnergy, int repro, int fEnergy) {
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
		this.ratio = ratio;
		this.TOTAL = width*height;
		this.startEnergy = sEnergy;
		this.reproduction = repro;
		this.fishEnergy = fEnergy;
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
		HashMap<String, Paint> colors;
		if(gameType.equals(GAMEOFLIFE)) {
			initStates = GoLStates(initStates);
			ruleset = new GOLRuleset();
			colors = ruleset.getStateColors();
		}else if(gameType.equals(FIRE)) {
			initStates = FireStates(initStates);
			ruleset = new FireRuleset(probCatch);
			colors = ruleset.getStateColors();
		}else if(gameType.equals(SEGREGATION)) {
			initStates = SegStates(initStates);
			ruleset = new SegregationRuleset(ratio);
			colors = ruleset.getStateColors();
		}else {
			initStates = WatorStates(initStates);
			WaTorRules = new WaTorRuleset(startEnergy, reproduction, fishEnergy);
			colors = WaTorRules.getStateColors();
		}
		
		if(gameType.equals(WATOR)) {
			WaTorgrid = new WaTorGrid(width, height, initStates, CELLSIZE, colors, startEnergy, reproduction);
			HashMap<Point, Cell> map = WaTorgrid.getCellMap();
			for(Point c: map.keySet()) {
				Shape temp = map.get(c).getShape();
				temp.setLayoutX(map.get(c).getX()+BUTTONHEIGHTPAD);
				temp.setLayoutY(map.get(c).getY()+BUTTONHEIGHT);
				root.getChildren().add(temp);
			}
		}
		else {
			grid = new StandardGridDiag(width, height, initStates, CELLSIZE, colors);
			HashMap<Point, Cell> map = grid.getCellMap();
			for(Point c: map.keySet()) {
				Shape temp = map.get(c).getShape();
				temp.setLayoutX(map.get(c).getX()+BUTTONHEIGHTPAD);
				temp.setLayoutY(map.get(c).getY()+BUTTONHEIGHT);
				root.getChildren().add(temp);
			}
		}
	}
	private ArrayList<String> WatorStates(ArrayList<String> init){
		for(int i=0;i<=TOTAL+1;i++) {
			int a = (int)(Math.random()*5);
			if(a==0)init.add("fish");
			if(a==1)init.add("shark");
			else init.add("water");
		}
		return init;
	}
	private ArrayList<String> SegStates(ArrayList<String> init){
		for(int i=0;i<=TOTAL+1;i++) {
			int a = (int)(Math.random()*5);
			if(a==0)init.add("o");
			if(a==1)init.add("x");
			else init.add("empty");
		}
		return init;
	}
	private ArrayList<String> FireStates(ArrayList<String> init){
		for(int i=0;i<=TOTAL+1;i++) {
			if(i==fireX*fireY)init.add("burning");
			init.add("tree");
		}
		return init;
	}
	private ArrayList<String> GoLStates(ArrayList<String> init){
		for(int i=0;i<=TOTAL+1;i++) {
			int a = (int) Math.round(Math.random());
			if(a==1)init.add("alive");
			else init.add("dead");
		}
		return init;
	}
	private void step(double secondDelay) {
		if(pause)return;
		if(gameType.equals(WATOR)) {
			WaTorRules.updateGrid(WaTorgrid);
		}
		else {
			ruleset.updateGrid(grid);
		}
	}
}
