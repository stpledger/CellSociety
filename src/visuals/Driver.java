package visuals;

import java.awt.Desktop;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import XML.*;
import cellsociety_team21.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Driver class sets up the simulations that are run.
 * 
 * @author Harry Wang
 */

public class Driver {
	private double FRAMES_PER_SECOND;
    private double MILLISECOND_DELAY;
    private double SECOND_DELAY;
    private static final int BUTTONHEIGHT = 50;
    private static final int BUTTONHEIGHTPAD = BUTTONHEIGHT+10;
    private final static int SIZEW = 600;
    private final static int SIZEH = 600;
    private static final Color BACKGROUND = Color.ALICEBLUE;
    private static final Color WHITE = Color.WHITE;
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String GAMEOFLIFE = "GameOfLife";
    private static final String SEGREGATION = "Segregation";
    private static final String WATOR = "Wator";
    private static final String FORAGING = "Ants";
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
	private static final String GRIDLINE = "Toggle Grid";
	private static final String STEP = "Step";
	private static final String SAVESTATES = "Save";
	private static final String FPS = "FPS: ";
	private static final String FPSDOUBLE = "FPS: %.2f";
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
    private ForageData forage;
    private TextField repro, sEnergy, segRatio, pFireText;
    private boolean randomAssign;
    private boolean gridLines;
    private ArrayList<String> simStates;
    private Simulation simulation;
    private boolean dataError;
    private boolean started;
    

	public Driver (Stage stage, Scene scene, String gameType, DataType data) {
		updateFrames(10);
		this.stage = stage;
		this.scene = scene;
		this.gameType = gameType;
		this.width = data.getWidthInt();
		this.height = data.getHeightInt();
		this.randomAssign = data.isRandom();
		this.gridLines = data.isGrid();
		this.simStates = data.getStates();
		this.dataError = false;
		this.started = false;
		if(gameType.equals(GAMEOFLIFE)) this.gol = (GoLData) data;
		else if(gameType.equals(FIRE)) this.fire = (FireData) data;
		else if(gameType.equals(SEGREGATION)) this.seg = (SegregationData) data;
		else if(gameType.equals(WATOR)) this.wator = (WatorData) data;
		else if(gameType.equals(FORAGING)) this.forage = (ForageData) data;
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
	/*
	 * This function allows the speed of the simulation to be changed
	 */
	private void updateFrames(double fps) {
		FRAMES_PER_SECOND = fps;
		MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
		SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	}
	/*
	 * Sets up all the UI functionality as well as allowing the User to interact with the simulation
	 * by changing the speed, grid size, and any specific simulation settings. i.e. Probabiliy of catching fire for the spreading fire sim.
	 */
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
		Button gridLINES = (Button) nodeMaker(GRIDLINE, BUTTONHEIGHT, 0, BUTTON);
		Button steps = (Button) nodeMaker(STEP, BUTTONHEIGHT, BUTTONHEIGHT, BUTTON);
		Text col = (Text) nodeMaker(COLUMNS, 0, BUTTONHEIGHT*5, TEXT);
		Button getSettings = (Button) nodeMaker(SAVESTATES, BUTTONHEIGHT, BUTTONHEIGHT*2, BUTTON);
		Slider slider = new Slider();
		Text sliderLabel = (Text) nodeMaker(FPS+ Double.toString(slider.getValue()), BUTTONHEIGHT*5, BUTTONHEIGHT*1.25, TEXT);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				sliderLabel.setText(String.format(FPSDOUBLE, new_val));
				updateFrames(new_val.doubleValue());
				if(pause || !started) return;
				animationFrame();
			}
		});
		slider.setMin(1);
		slider.setMax(80);
		slider.setValue(20);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(40);
		slider.setMinorTickCount(4);
		slider.setBlockIncrement(10);
		slider.setLayoutX(BUTTONHEIGHT*2);
		slider.setLayoutY(BUTTONHEIGHT);
		TextField widthText = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*5), TEXTFIELD);
		widthText.setText(Integer.toString(width));
		Text row = (Text) nodeMaker(ROWS, 0, BUTTONHEIGHT*6, TEXT);
		TextField heightText = (TextField) nodeMaker(EMPTY, 0, 10+(BUTTONHEIGHT*6), TEXTFIELD);
		heightText.setText(Integer.toString(height));
		root.getChildren().addAll(slider, sliderLabel, mainMenu, start, stop, reset, submit, col, row, widthText, heightText, gridLINES, steps, getSettings);
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
		steps.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				pause = false;
				step(SECOND_DELAY);
				pause = true;
			}
		});
		gridLINES.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gridLines = !gridLines;
				toggleGrid();
				pause = true;
			}
		});
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				returnMain();
			}
		});
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				pause = false;
				started = true;
				animationFrame();
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
					else {
						sEnergy.setText(INTERROR);
						return;
					}
					if(isParsable(repro.getText(), INT)) wator.setReproduction(Integer.parseInt(repro.getText()));
					else {
						repro.setText(INTERROR);
						return;
					}
				}
				setUpSim();
				pause = true;
			}
		});
		getSettings.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				XMLSaver save;
				if(gameType.equals(FIRE)) {
					save = new XMLSaver(fire, grid, gridLines, width, height);
				}
				else if(gameType.equals(SEGREGATION)) {
					save = new XMLSaver(seg, grid, gridLines, width, height);
				}
				else if(gameType.equals(WATOR)) {
					save = new XMLSaver(wator, grid, gridLines, width, height);
				}else {
					save = new XMLSaver(gol, grid, gridLines, width, height);
				}
				if (!save.isSaved()) {
					System.out.println("GG");
				}
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
	private void animationFrame() {
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
	private void returnMain() {
		if(animation!=null)animation.stop();
		Gui restart = new Gui(stage, dataError);
		dataError = false;
		Scene scene = restart.getScene();
		stage.setScene(scene);
		stage.show();
	}
	private void toggleGrid() {
		HashMap<Point, Cell> map = grid.getCellMap();
		for(Point p: map.keySet()) {
			Shape temp = map.get(p).getShape();
			if(gridLines) temp.setStroke(WHITE);
			else temp.setStroke(null);
		}
	}
	/*
	 * Function that checks to see if the input given by the User is parsable to dynamically change the grid
	 */
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
	/*
	 * Initializes the grid depending on which simulation is running
	 */
	private void initGrid() {
		if(gameType.equals(GAMEOFLIFE)) {
			simulation = new GOLSimulation();
		}else if(gameType.equals(FIRE)) {
			simulation = new FireSimulation(fire.getProbCatch());
		}else if(gameType.equals(FORAGING)) {
			simulation = new ForagingSimulation(forage.getMaxAnts(), forage.getDiff(), forage.getEvap(), forage.getPhero(), forage.getAntsBorn(), forage.getStartAge());
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
	/*
	 * Adds all the cells into the visuals of the grid
	 */
	private void setUpGridVisuals(HashMap<Point, Cell> map) {
		for(Point c: map.keySet()) {
			Shape temp = map.get(c).getShape();
			temp.setLayoutX(map.get(c).getX()+10+2*BUTTONHEIGHTPAD);
			temp.setLayoutY(map.get(c).getY()+2*BUTTONHEIGHT);
			root.getChildren().add(temp);
		}
		toggleGrid();
	}
	/*
	 * Initializes the beginning states of the simulation. If the states aren't given, it will randomly assign states.
	 */
	private ArrayList<String> initializeStates(ArrayList<String> init) {
		if(!validateStates(simStates, simulation.getStates())) returnMain();
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
				ArrayList<String> diffStates = simulation.getStates();
				for(int i=simStates.size();i<=width*height;i++) {
					int temp = (int)(Math.random()*diffStates.size());
					init.add(diffStates.get(temp));
				}
			}
			return init;
		}
	}
	/*
	 * Checks if the states given are valid ones of the simulation. If not, it will redirect to the main menu with an error on the GUI.
	 */
	private boolean validateStates(ArrayList<String> states, ArrayList<String> properStates) {
		boolean results = properStates.containsAll(states);
		dataError = !results;
		return results;
	}
	
	private void step(double secondDelay) {
		if(pause)return;	 
		simulation.updateGrid(grid);
	}
}
