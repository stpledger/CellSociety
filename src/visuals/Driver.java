package visuals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import XML.*;
import cellsociety_team21.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Driver {
	public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int BUTTONHEIGHT = 50;
    private static final int BUTTONHEIGHTPAD = BUTTONHEIGHT+10;
    private final static int SIZEW = 600;
    private final static int SIZEH = 600;
    private static final Color BACKGROUND = Color.ALICEBLUE;
    private static final Color BLACK = Color.BLACK;
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String GAMEOFLIFE = "GameOfLife";
    private static final String SEGREGATION = "Segregation";
    private static final String WATOR = "Wator";
    private static final String FIRE = "Fire";
    private static final String MAIN = "Main";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String RESET = "Reset";
	private static final String BUTTON = "button";
	private static final String LABEL = "label";
	private static final String TEXT = "text";
	private static final String TEXTFIELD = "textfield";
	private static final String EMPTY = "";
	private static final String COLUMNS = "Columns: ";
	private static final String ROWS = "Rows: ";
	private static final String SUBMIT = "Submit";
	private static final String PFIRE = "Prob of Fire:";
	private static final String RATIO = "Ratio:";
	private static final String STARTENERGY = "Start Energy:";
	private static final String REPRODUCTIONTIME = "Reproduction Time:";
	private static final String INTERROR = "Enter an integer";
	private static final String DOUBLEERROR = "Enter a double";
    private Group root;
    private Stage stage;
    private Scene scene;
    private String gameType;
    private Timeline animation;
    private int width, height;
    private boolean pause;
    private StandardGrid grid;
    private double CELLSIZE;
    private GoLData gol;
    private FireData fire;
    private SegregationData seg;
    private WatorData wator;
    private TextField repro, sEnergy, segRatio, pFireText;
    private boolean randomAssign;
    private boolean gridLines;
    private ArrayList<String> simStates;
    private Simulation simulation;
    //TODO: add validation checking for grid values given, grid lines and speed of sim

	public Driver (Stage stage, Scene scene, String gameType, DataType data) {
		this.stage = stage;
		this.scene = scene;
		this.gameType = gameType;
		this.width = data.getWidthInt();
		this.height = data.getHeightInt();
		this.randomAssign = data.isRandom();
		this.gridLines = data.isGrid();
		this.simStates = data.getStates();
		if(gameType.equals(GAMEOFLIFE)) this.gol = (GoLData) data;
		else if(gameType.equals(FIRE)) this.fire = (FireData) data;
		else if(gameType.equals(SEGREGATION)) this.seg = (SegregationData) data;
		else if(gameType.equals(WATOR)) this.wator = (WatorData) data;
		this.CELLSIZE = (SIZEH-10-3*BUTTONHEIGHTPAD)/(Math.max(width, height));
		setUpSim();
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
	
	private void setUpSim() {
		pause = false;
		root = new Group();
		scene = new Scene(root, SIZEW, SIZEH, BACKGROUND);
		stage.setScene(scene);
		stage.show();
		Button mainMenu = (Button) nodeMaker(MAIN, 0, 0, BUTTON);
		Button start = (Button) nodeMaker(START, 0, BUTTONHEIGHT, BUTTON);
		Button stop = (Button) nodeMaker(STOP, 0, BUTTONHEIGHT*2, BUTTON);
		Button reset = (Button) nodeMaker(RESET, 0, BUTTONHEIGHT*3, BUTTON);
		Button submit = (Button) nodeMaker(SUBMIT, 0, BUTTONHEIGHT*4, BUTTON);
		Text col = (Text) nodeMaker(COLUMNS, 0, BUTTONHEIGHT*5, TEXT);
		TextField widthText = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*5), TEXTFIELD);
		widthText.setText(Integer.toString(width));
		Text row = (Text) nodeMaker(ROWS, 0, BUTTONHEIGHT*6, TEXT);
		TextField heightText = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*6), TEXTFIELD);
		heightText.setText(Integer.toString(height));
		root.getChildren().addAll(mainMenu, start, stop, reset, submit, col, row, widthText, heightText);
		if(gameType.equals(FIRE)) {
			Text pFire = (Text) nodeMaker(PFIRE, 0, BUTTONHEIGHT*7, TEXT);
			pFireText = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*7), TEXTFIELD);
			pFireText.setText(Double.toString(fire.getProbCatch()));
			root.getChildren().addAll(pFire, pFireText);
		}
		if(gameType.equals(SEGREGATION)) {
			Text segT = (Text) nodeMaker(RATIO, 0, BUTTONHEIGHT*7, TEXT);
			segRatio = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*7), TEXTFIELD);
			segRatio.setText(Double.toString(seg.getRatio()));
			root.getChildren().addAll(segT, segRatio);
		}
		if(gameType.equals(WATOR)) {
			Text startE = (Text) nodeMaker(STARTENERGY, 0, BUTTONHEIGHT*7, TEXT);
			sEnergy = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*7), TEXTFIELD);
			sEnergy.setText(Integer.toString(wator.getStartEnergy()));
			Text reproT = (Text) nodeMaker(REPRODUCTIONTIME, 0, BUTTONHEIGHT*8, TEXT);
			repro = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*8), TEXTFIELD);
			repro.setText(Integer.toString(wator.getReproduction()));
			root.getChildren().addAll(startE, sEnergy, reproT, repro);
		}
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
		submit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(isParsable(widthText.getText(), INT)) width = Integer.parseInt(widthText.getText());
				else {
					widthText.setText(INTERROR);
					return;
				}
				if(isParsable(heightText.getText(), INT)) height = Integer.parseInt(heightText.getText());
				else {
					heightText.setText(INTERROR);
					return;
				}
				CELLSIZE = (SIZEH-10-3*BUTTONHEIGHTPAD)/(Math.max(width, height));
				if(gameType.equals(FIRE)) {
					if(isParsable(pFireText.getText(), DOUBLE)) fire.setProbCatch(Double.parseDouble(pFireText.getText()));
					else {
						pFireText.setText(DOUBLEERROR);
						return;
					}
				}
				if(gameType.equals(SEGREGATION)) {
					if(isParsable(segRatio.getText(), DOUBLE)) seg.setRatio(Double.parseDouble(segRatio.getText()));
					else {
						segRatio.setText(DOUBLEERROR);
						return;
					}
				}
				if(gameType.equals(WATOR)) {
					if(isParsable(sEnergy.getText(), INT)) wator.setStartEnergy(Integer.parseInt(sEnergy.getText()));
					else sEnergy.setText(INTERROR);
					if(isParsable(repro.getText(), INT)) wator.setReproduction(Integer.parseInt(repro.getText()));
					else repro.setText(INTERROR);
				}
				setUpSim();
				pause = true;
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
	private boolean isParsable(String tester, String type) {
		boolean parsable = true;
		if(type.equals(INT)) {
			try {
				Integer.parseInt(tester);
			}catch(Exception e){
				parsable = false;
			}
		}else if(type.equals(DOUBLE)) {
			try {
				Double.parseDouble(tester);
			}catch(Exception e) {
				parsable = false;
			}
		}
		return parsable;
	}
	private void initGrid() {
		if(gameType.equals(GAMEOFLIFE)) {
			simulation = new GOLSimulation();
		}else if(gameType.equals(FIRE)) {
			simulation = new FireSimulation(fire.getProbCatch());
		}else if(gameType.equals(SEGREGATION)) {
			simulation = new SegregationSimulation(seg.getRatio());
		}else {
			simulation = new WaTorSimulation(wator.getStartEnergy(), wator.getReproduction(), wator.getFishEnergy());
		}
		
		ArrayList<String> initStates = new ArrayList<String>();
		initStates = initializeStates(initStates);
		HashMap<String, Paint> colors = simulation.getStateColors();
		if(gameType.equals(WATOR)) {
			grid = new WaTorGrid(width, height, initStates, CELLSIZE, colors, wator.getStartEnergy(), wator.getReproduction());
		}
		else {
			grid = new StandardGridDiag(width, height, initStates, CELLSIZE, colors, gameType);
		}
		setUpGridVisuals(grid.getCellMap());
	}
	private void setUpGridVisuals(HashMap<Point, Cell> map) {
		for(Point c: map.keySet()) {
			Shape temp = map.get(c).getShape();
			temp.setLayoutX(map.get(c).getX()+10+2*BUTTONHEIGHTPAD);
			temp.setLayoutY(map.get(c).getY()+2*BUTTONHEIGHT);
			if(gridLines) temp.setStroke(BLACK);
			root.getChildren().add(temp);
		}
	}
	private ArrayList<String> initializeStates(ArrayList<String> init) {
		if(randomAssign) {
			for(int i=0;i<=width*height;i++) {
				int temp = (int)(Math.random()*simStates.size());
				init.add(simStates.get(temp));
			}
			return init;
		}
		else {
			init = simStates;
			if(simStates.size()<width*height) {
				ArrayList<String> diffStates = simulation.getStates();//should use a proper holder of all simulation states
				for(int i=simStates.size();i<=width*height;i++) {
					int temp = (int)(Math.random()*diffStates.size());
					init.add(diffStates.get(temp));
				}
			}
			return init;
		}
	}

	private void step(double secondDelay) {
		if(pause)return;	 
		simulation.updateGrid(grid);
	}
}
