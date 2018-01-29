Cell Society Plan
=========

# Introduction

Our goal is to design a program that can run and any kind of cellular automata simulation. The simulation will be executed on an initialized grid given by the users inputs and run through a specific rule-set also given by the users input. In addition, the user could easily add an extra simulation by creating a new java class.  A main goal of our implementation is flexibility, in that we want to make it as easy as possible for a future developer to add new types of simulations.  These new types of simulations could include new rulesets, new grid and cell shapes, and new ways that the cells interact with each other (i.e. whether or not diagonal cells count as adjacent).

# Overview

For this project we are dividing it up into three parts. 1) the GUI, graphical updates of the simulation, and parsing in the XML files, 2) creating the Grid super/sub classes and Cell class, 3) the ruleset super/sub class that will contain the different rules. We are splitting it up this way as each part can be broken down individually. However, by the design of our implementation the second portion would be easier, and the teammate working on the second section when finished could support the teammate on the third problem. 

We will have a main driver class that will be dependent on a display class (that we will be used to display all the current states of all the cells), and also an abstract Grid superclass that gets extended into different types of Grids (e.g. SquareGrid). The Cell class will be as simple as possible so it does not need to change for different shapes of cells or types of rulesets.

We will then have an abstract Ruleset super class that can be extended by specific classes that hold the rules for each simulation. This way we can change rules without having to affect the overall Grid/Cell classes.  The Ruleset class will be simple enough that it can accomodate the rules of different types of simulations; for example, both simulations that change the state of a cell to a different state, and simulations that move the state of a cell to a different cell.

The program will be started by a Driver class that takes in the user's XML input to initialize the grid and display the simulation in a GUI.  The Display class will handle everything that the user sees, as well as any user inputs such as pressing play or pause, changing the speed, or stepping forward a unit time.

Below are a breakdown of each class we plan to implement, along with their instance variable and their public methods.  The reasons for each of these choices is described in the Design Details section.

![imageflow](https://coursework.cs.duke.edu/CompSci308_2018Spring/cellsociety_team21/raw/master/doc/FullSizeRender-1.jpg?raw=true) 


```java
class Cell{
Shape myShape;
String myCurrentState;
String myNextState;
Int myXPos;
Int myYPos;
ArrayList<Cell> myNeighbors;

Shape getShape()
String getCurrentState()
String getNextState()
Void setNextState()
Void switchState()
ArrayList<Cell> getNeighbors()
Void setNeighbors(ArrayList<Cell>)
Void setColor(Paint color)
}
```

```Java
Abstract class Grid extends HashMap<Coordinate,Cell>{
Abstract void assignNeighbors()
ArrayList<Cell> getCells()
Void switchStates(HashMap<String,Paint> stateColors)
}
```

```Java
Abstract class Ruleset {
HashMap<String, Paint> stateColors

Void updateGrid()//this calls various private helper methods to decide which cells need to change, changes them, updates their colors, etc.
}
```

```Java
Class display{
Group root;
Timeline animation;
Scene myScene;

Void updateScreen()
Void checkUserInput()
Void getXMLData()
}
```

```Java
Class driver{
Group root;
Timeline animation;
Scene myScene;

Void setUp()
Void step()
}
```

# User Interface

The user interface will be really simple. We will have a main menu that allows a user to select the starting inputs for the simulation (i.e. which XML file and which ruleset to follow). Then the display will turn into the simulation and there will be buttons/toolbars to toggle between speeds of the simulation and size of simulation as well as a pause and a back button to return to the main menu. We will try and limit the number of ways a user could give bad inputs into the project (for example speed: -10), by setting the button/toolbars to reflect this. Also, if the program was given bad input or bad data, it will print out an error message on the display and reset back to the main menu instead of crashing. 

![UI](https://coursework.cs.duke.edu/CompSci308_2018Spring/cellsociety_team21/raw/master/doc/FullSizeRender.jpg?raw=true) 

# Design Details

We chose to make the cell class general enough that it does not depend on what shape or size the cells are.  This decision was made because the Grid class will have to take these things into account no matter what, and we want to isolate the code that has to change when making new simulations into as few classes as possible.  This way, we can implement new shapes by extending the Grid class without touching the Cell class.  This is reflected in the code we provided in two ways: first, myShape is of type Shape, so it can be initialized to any shape, and second, myNeighbors is a simple ArrayList, so Grid can assign those neighbors to be any number of other cells, depending on how many other cells it touches, and whether we count corners or not.  We also do not include any logic for updating state within Cell, as this will be handled by Ruleset.

We chose to let Grid extend HashMap<Cell> so that it can be easily and quickly indexed, and so that it is not confined to any specific geometry.  If it was a 2D array, it could be rectangular.  It contains an abstract method setNeighbors that will add neighbors to a Cell’s myNeighbors, and this will be implemented in subclasses specific to the geometry of the grid.  It also contains the method switchStates, which applies the next states to each cell after all of the next states have been assigned.  This method takes in a map that contains states as strings and colors as values so that it can set the fill of each cell when it switches state.

The abstract Ruleset class will be extended for each new simulation that we (or another developer) adds.  It will contain all of the logic for assigning a nextState to a cell based on the currentState of it and its neighbors.  For simplicity, we have chosen to give it only one public method, which will step the entire grid forward into its next evolution.  This method can be called from driver either once to move one step, or periodically to “animate” the simulation.  The public method will rely on many internal private methods that help it determine its next state.  Some of these methods will be assigned as abstract methods in the Ruleset super class, if they are a method that would be required by any ruleset.  For methods that are only required by some rulesets, they will only be implemented in the subclasses.


# Design Considerations
**Whether Cell should be extended**
**Whether Grid should be extended**
**How to store cells in the grid**
* Hashmap
    * Pros-
    * Cons-
* 2D Array
    * Pros-
    * Cons-

**How myNeighbors is stored within Cell**
**How to pass new Color to Cell**

# Team Responsibilities

Harry Wang- XML input, GUI/Display class, Driver class
Scott Pledger- Cell class, Grid superclass, & Grid subclasses
Ben Auriemma- Ruleset superclass & Ruleset subclasses

This is the general separation of work that we are going to start with, but if any one member is overwhelmed with the work they have been assigned, then a member who has a lighter workload will help once they have completed their work. The general order we are looking to complete the project in is: XML input, Driver class, Cell class, Grid superclass and subclasses, Ruleset superclass and subclasses, GUI/Display class. However, we are going to get the barebone of each component done first so that things are up and running enough to test as we work to complete the various parts.

# Use cases

Apply rules to middle and edge cells
* To apply these rules, we will use the step function in driver to call an update function from the Game of Life ruleset class which follows the logic of the ruleset which will update the entire grid with both the middle and edge cells
Move to the next generation
* After all cells are updated from the function, we will pass that information into a display class that shows all the graphical visualizations including the GUI. The display class will loop through all the information (from a hashmap) of the grid and visualize each individual cell.
Set a simulation parameter
* From the XML, we will read it in through the driver class that then sends the information to the setUp() function in the driver class which uses the information to initialize it.
Switch simulations
* To change the simulation, the user has to hit menu to go back to the main menu and then choose a new simulation to execute.
