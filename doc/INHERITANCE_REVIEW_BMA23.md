# Inheritance Review

## Part 1

1. We encapsulate all of the rules and logic in the Ruleset subclasses.  This means that a Cell doesn't know anything except its own state, and the driver class only has to make one call (i.e., updateAllCells()) to move to the next generation.

2. The only inheritance in my area is Ruleset and its subclasses.  This is based on the behavior of evolving cell states.

3. Ruleset is open because we're extending it to add new simulations.  Our design choices allow both Cell and Driver to be closed.

4. There shouldn't be any exceptions in our section; they're all handled elsewhere.  For example, if the XML class reads in states that don't exist in this simulation, that error is caught by the XML class.

5. One main factor in defining goodness for us is how many classes have to change to add new simulations.  We only have to change 2, and they are independent, which means that if we have 3 different shaped grids and 3 different rulesets, we don't have to create 9 classes (1 for each possible pair), we only have to create 6 classes.

## Part 2

1. Our area is called by the main driver class, and it interacts with Grid by iterating through each Cell in it and assigning it's state.