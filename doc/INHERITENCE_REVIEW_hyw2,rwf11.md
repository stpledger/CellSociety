# Part 1

## What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?

I will be encapsulating all the GUI and visuals into a specific package. This way it won't have to interfere with the way the other parts of the program will operate. I will also encapsulate the XML parser into a separate package as well.

## What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

For the XML parser, I plan to have an abstract Data class that is inherited by the separate simulation data classes. This way, we keep everything separate and flexible if new simulations are added. The XML data parser class will just continue to parse in information and from there, run a specific simulation data class. For the visualization of the simulation, it will be separate from the GUI. Like the step function from before, it will constantly update to view what the current state of all the cells. 

## What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?

I am planning to use a class that manages all the things that is/will be displayed. This manager class will be closed to what is going on within the sub classes that it manages, but the manager is open to what it will display and execute. I will also have a super class

## What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

If an XML file is chosen that doesn't have enough arguments to start the simulation, I will make sure that the data parser at least goes through the data and have some kind of data validation to make sure the arguments that are passed are useful and necessary for running the simulation. This is to check for null pointers and also illegal arguments. From their it will just display an error pertaining to why this exception has occurred on the display.

## Why do you think your design is good (also define what your measure of good is)?

I think I've been able to make my design as simple as possible. I'm not sure whether this design philosophy is the best way to go, but it is the easiest for me to understand and comprehend.

# Part 2

## How is your area linked to/dependent on other areas of the project?

The visualizer will be linked when running the simulation. It will be constantly called to visualize everything and it will take in information that the ruleset classes return. 

## Are these dependencies based on the other class's behavior or implementation?

Apart from the visualizer, the other classes that I will make should not be dependent on any other class's behavior or implementation. The visualizer will need information about every cell's status and what it needs to visualize. 

## How can you minimize these dependencies?

In my opinion, this is as minimized as it can be without clumping everything into one class.

## Go over one pair of super/sub classes in detail to see if there is room for improvement. 

The Data class that is a super class for what kind of simulation is going to be run, and GoLData class that inherits the specific methods that the Data class has. This way, the XML parser will be able to abstract information and attribute it to these classes, but in the off chance that the user inputs an XML file that is not complete, the program won't crash, it will just attribute the relevant data to the class so the parser will not fail. While this is good to make sure the parser won't break, I could use a try/catch to reduce the number of classes used. However, this reduces the amount of workload on the class.

## Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).

The super class will have methods that the sub classes inherit and populate.

# Part 3

## Come up with at least five use cases for your part (most likely these will be useful for both teams).

* Choosing a file
* Parsing a file
* Stopping animations and starting animations
* Resetting and clearing all simulation data
* Changing the simulations 

## What feature/design problem are you most excited to work on?

The XML parser. Professor Duvall talked a lot about how it works and how it can be improved, so it would be interesting to see how well I do on it.

## What feature/design problem are you most worried about working on?

Visualizing all the updated information. Although I won't be updating the statuses of these cells, as Professor Duvall adds in more rules that our simulation needs to adhere to, this would complicate things and make it harder to do.
