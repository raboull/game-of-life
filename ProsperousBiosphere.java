/*
  Program summary: Extends the Biosphere class.
  
  Program details: 
  --This class has different rules for births and deaths than its parent Biosphere class.
  --Deaths occur if there is fewer than one neighbor or more than four neighbors.
  --Births occur if there is exactly 4 neighbors.

  Limitations:
  --Same limitations as its parent class.
  --The beath and birth rules can not be changed by the user.

  Version: March 28, 2021
  --Added a start method that simply calls the start method of this classes' super class.

  Version: March 20, 2021
  --Initial version is implemented.
 
*/

public class ProsperousBiosphere extends Biosphere 
{

    public static final int LONELINESS_THRESHOLD = 1;
    public static final int OVERCROWDING_THRESHOLD = 4;
    public static final int BIRTH_THRESHOLD = 4;
      
    //This is the prosperous biosphere constructor
    public ProsperousBiosphere(Critter [][] aWorld){
        super(aWorld);
    }

    //this method runs a prosperous Biosphere simulation until an end condition is met
    public void start(int lonelinessThreshold, int overcrowdingThreshold, int birthThreshold){
        //call the parent start method and pass the threshold parameters of the Prosperous Biosphere
        super.start(lonelinessThreshold, overcrowdingThreshold, birthThreshold);       
    }
}
