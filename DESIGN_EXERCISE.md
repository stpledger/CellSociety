Cell Society High Level Design

We will have a main driver class that will be dependent on a display class (that we will be used to display all the current states of all the cells), and also a Grid class that is built with a Cell class. The Cell class will be as simple as possible so it could be reused dependent on the different rules/scenarios. The grid will just be a grid based off the users specifications. The Grid/Cell class will be as general and simple as possible to be able to be generalized into the different rulesets.

We will then have a ruleset super class that can be inherited/extended by specific classes that hold the rules for each simulation. This way we can change rules without having to affect with the overall Grid/Cell classes. 

Overall, the project will be initialized in driver with the user's grid inputs which will initialize the grid and call in the display which will update constantly with the ruleset and display the corresponding outputs.

So we ran through CRC cards and tried to go through the segregation example with our design model and things make sense and we are all on the same page.