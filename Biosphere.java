/*
  Program summary: Simulates a Biosphere and applies rules of Birth and Death to input Critters.
  
  Program details: 
  --Uses an array of the Critter type to track the current state of the simulated world.
  --Each turn the rules of births and deaths will change the Critter states.
  --The sole determinant if a birth or death occurs in a particular location is the number of critters in the neighboring squares.
  --Cells that contain a critter will have the critter die of loneliness if fewer than 2 neighbors are present.
  --Cells that contain a critter will have the critter die of Over-crowding if more than 3 neighbors are present.
  --Cells that are empty will have new critter born into it if it has exactly 3 neighbors (otherwise it remains empty).
  --One array element may contain a reference to a Taminator object.
  --Displays 3 states of the world side by side, the included states are:
            --The previous state before any birth/death rules and before the effect of the Taminator have been applied.
            --The affest of births and deaths applied on the previous state.
            --At the Taminator's current location critters are 'taminated' and the taminator teleports to a new random location.
  --The turns continue until a user inputs the letter q when prompted after every turn is done and displayed.
  --The effect of Taminator's abilities will occur on state after births and deaths have occured. 

  Limitations:
  --User cannot specify alternate rules for birth and death of critters.
  --The biosphere is hardcoded to be only 10x10 in size.
  --The order of the output arrays can not be altered.
  --The biosphere can only host Regular Critters and Taminators, no other organisms can be created.

  Version: March 28, 2021
  --Refactored the start() method to require parameters, this allows for easy extension of the class into a Prosperous biosphere.

  Version: March 20, 2021
  --Initial version is created.
 
*/

import java.util.Scanner;

public class Biosphere{

    public static final int ROWS = 10;
    public static final int COLUMNS = 10;
    public static final String HORIZONTAL_LINE = "  - - - - - - - - - -";
    public static final String HORIZONTAL_COUNT = "  0 1 2 3 4 5 6 7 8 9 ";
    private Critter [][] current;
    
    //these three Critter matricies store the Biosphere at different states
    private Critter [][] previousGeneration;
    private Critter [][] afterBirthAndDeath;
    private Critter [][] afterTaminator;

    //there three threshold values specify death and birth rules of regular biosphere Critters
    public static final int LONELINESS_THRESHOLD = 2;
    public static final int OVERCROWDING_THRESHOLD = 3;
    public static final int BIRTH_THRESHOLD = 3;
    
    public Biosphere(Critter [][] aWorld){
    
        //Original code
        current = aWorld;

        //initialize critter arrays that will be used in the simulation.
        previousGeneration = new Critter[Biosphere.ROWS][Biosphere.COLUMNS];
        afterBirthAndDeath = new Critter[Biosphere.ROWS][Biosphere.COLUMNS];
        afterTaminator = new Critter[Biosphere.ROWS][Biosphere.COLUMNS];
    }
    
    public void display()
    { 
        int i;
        int r;
        int c;
	    System.out.println("  PREVIOUS GENERATION");
	    System.out.println(HORIZONTAL_COUNT);
        System.out.print(" ");
        for (i = 0; i < ROWS; i++)
            System.out.print(" -"); //Line of dashes before the array
        System.out.println();
	    for (r = 0; r < ROWS; r++)
	    {
	        System.out.print(r); //Line # before each row
	        for (c = 0; c < COLUMNS; c++){
		        System.out.print("|" + current[r][c]); //Bounding line left of array element
            }
            System.out.println("|"); //Bounding line at the of the row for the last element
            System.out.print(" ");
            for (i = 0; i < ROWS; i++)
                System.out.print(" -");  //Bounding line below each array element
            System.out.println();
        }
    }

    //this method will display three states of the Critters present in the biosphere side by side
    public void displayThreeStates(){
        //print headears for each matrix 
        System.out.println("  PREVIOUS GENERATION               BIRTHS & DEATHS                TAMINATOR");
        System.out.println(HORIZONTAL_COUNT+"            "+HORIZONTAL_COUNT+"         "+HORIZONTAL_COUNT);
        System.out.print("  - - - - - - - - - -               - - - - - - - - - -            - - - - - - - - - -"); //Line of dashes before the array
        System.out.println();
        for (int r = 0; r < ROWS; r++){
            System.out.print(r); //Line # before each row
            
            for (int c = 0; c < COLUMNS; c++){//print a row of the previousGeneration matrix
                System.out.print("|" + previousGeneration[r][c]); //Bounding line left of array element
            }
            System.out.print("|            ");//insert padding before the Births and Deaths matrix is printed
            System.out.print(r);//once again number the matrix rows
            
            for (int c = 0; c < COLUMNS; c++){//print a row of the previousGeneration matrix
                System.out.print("|" + afterBirthAndDeath[r][c]); //Bounding line left of array element
            }
            System.out.print("|         ");//insert padding before the Taminator matrix is printed
            System.out.print(r);//once again number the matrix rows
            
            for (int c = 0; c < COLUMNS; c++){//print a row of the previousGeneration matrix
                System.out.print("|" + afterTaminator[r][c]); //Bounding line left of array element
            }        
            System.out.println("|"); //Bounding line at the of the row for the last element
            System.out.print("  - - - - - - - - - -               - - - - - - - - - -            - - - - - - - - - -");  //Bounding line below each array element
            System.out.println();
            }
    }

    public Critter [][] getCurrent() 
    {
        return(current);
    }

    public void runTurn()
    {
         display();
    }

    //this method applies the birth and death rules to critters in the previousGeneration matrix
    private void simulateBirthAndDeath(int lonelinessThreshold, int overcrowdingThreshold, int birthThreshold){
        
        //count the number of neighbors each Critter cell has in the prviousGeneration matrix
        countCritterNeighbors(previousGeneration);

        //copy the previous generation matrix into the afterBirthAndDeath matrix
        deepCopyCritterArray(previousGeneration, afterBirthAndDeath);

        //remove Critters that meet the death by lonliness conditions
        deathByLonliness(afterBirthAndDeath, lonelinessThreshold);   
        
        //remove Critters that meet the death by overcrowding condition
        deathByOvercrowding(afterBirthAndDeath, overcrowdingThreshold); 

        //add Critters that meet the birth by neighbors condition
        birthByNeighbors(afterBirthAndDeath, birthThreshold);
    }

    //this method kills off Critters that meet the death by loneliness condition
    private void deathByLonliness(Critter[][] inputMatrix, int lonelinessThreshold){        
        for (int r=0; r<ROWS; r++){//itterate through the inputMatrix
            for (int c=0; c<COLUMNS; c++){
                if( inputMatrix[r][c].getAppearance() == Critter.DEFAULT_APPEARANCE ){
                    //get the number of neighbors in the current Critter object
                    int numOfNeignbors = inputMatrix[r][c].getNumberOfNeighbors();

                    if (numOfNeignbors >= 0 && numOfNeignbors < lonelinessThreshold){//check if current Critter is lonely
                        inputMatrix[r][c].setAppearance(Critter.EMPTY);//make the Critter object empty
                        inputMatrix[r][c].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
                        //inputMatrix[r][c].updateNumberOfNeighbors(r, c, inputMatrix);
                    }
                }
            }
        }
    }

    //this method kills off Critters that meet the death by overcrowding condition
    private void deathByOvercrowding(Critter[][] inputMatrix, int overcrowdingThreshold){      
        for (int r=0; r<ROWS; r++){//itterate through the inputMatrix
            for (int c=0; c<COLUMNS; c++){
                if( inputMatrix[r][c].getAppearance() == Critter.DEFAULT_APPEARANCE ){
                    //get the number of neighbors in the current Critter object
                    int numOfNeignbors = inputMatrix[r][c].getNumberOfNeighbors();

                    if (numOfNeignbors > overcrowdingThreshold){//check if current Critter is overcrowded
                        inputMatrix[r][c].setAppearance(Critter.EMPTY);//make the Critter object empty
                        inputMatrix[r][c].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
                        //inputMatrix[r][c].updateNumberOfNeighbors(r, c, inputMatrix);
                    }
                }
            }
        }
    }

    //this method creates Critters that meet the birth by neighbors condition
    private void birthByNeighbors(Critter[][] inputMatrix, int birthThreshold){      
        for (int r=0; r<ROWS; r++){//itterate through the inputMatrix
            for (int c=0; c<COLUMNS; c++){
                if( inputMatrix[r][c].getAppearance() == Critter.EMPTY ){//check if current Critter cell is empty
                    //get the number of neighbors around the current empty Critter cell
                    int numOfNeignbors = inputMatrix[r][c].getNumberOfNeighbors();

                    if (numOfNeignbors == birthThreshold){//check if empty cell has exactly three neighbors
                        inputMatrix[r][c].setAppearance(Critter.DEFAULT_APPEARANCE);//make the Critter visible
                        inputMatrix[r][c].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
                        //inputMatrix[r][c].updateNumberOfNeighbors(r, c, inputMatrix);
                    }
                }
            }
        }
    }

    //count critter neighbors
    private void countCritterNeighbors(Critter[][] inputMatrix){
        for (int r=0; r<ROWS; r++){//itterate through the inputMatrix
            for (int c=0; c<COLUMNS; c++){
                if( inputMatrix[r][c] != null ){
                    inputMatrix[r][c].updateNumberOfNeighbors(r, c, inputMatrix);
                }
            }
        }
    }

    //this method applies the taminator rules to critters in the afterBirthAndDeath matrix
    private void simulateTaminator(){
        //copy the afterBirthAndDeath matrix into the afterTaminatorMatrix
        deepCopyCritterArray(afterBirthAndDeath, afterTaminator);

        int rowTaminator = -1;
        int colTaminator = -1;
        boolean taminatorPresent = false;

        //find a cell that contains the taminator
        for (int r=0; r<ROWS; r++){//itterate through the inputMatrix
            for (int c=0; c<COLUMNS; c++){
                if( afterTaminator[r][c].getAppearance() == Taminator.DEFAULT_APPEARANCE){//check if current cell is a taminator
                    rowTaminator = r;
                    colTaminator = c;
                    taminatorPresent = true;                    
                }
            }
        }

        if (taminatorPresent == true){//do the following if a Taminator is present
            //make sure that the found Taminator cell is of Taminator type
            if(afterTaminator[rowTaminator][colTaminator] instanceof Taminator){
                //cast the Critter cell that contains a Taminator object as Taminator and call the taminateNeighbors method
                ((Taminator) afterTaminator[rowTaminator][colTaminator]).taminateNeighbors(rowTaminator, colTaminator, afterTaminator);
                //teleport the Taminator to a new location
                ((Taminator) afterTaminator[rowTaminator][colTaminator]).teleport(rowTaminator, colTaminator, afterTaminator);
            }
        }
    }

    //this method created a deep copy from the sourceArray to the destinationArray of Critter objects
    private void deepCopyCritterArray(Critter[][] sourceArray, Critter[][] destinationArray){
 
        wipeArray(destinationArray);//wipe the destination matrix before copying elements into it

        for (int r=0; r<ROWS; r++){//copy all critters from sourceArray to destinationArray
            for (int c=0; c<COLUMNS; c++){
                if( sourceArray[r][c] != null ){ 
                    //if the current sourceArray cell is a Critter object type do this
                    if(sourceArray[r][c] instanceof Critter){
                        destinationArray[r][c] = new Critter();
                        destinationArray[r][c].setAppearance(sourceArray[r][c].getAppearance());
                        destinationArray[r][c].setNumberOfNeighbors(sourceArray[r][c].getNumberOfNeighbors());
                    }
                    //if the current sourceArray cell is a Taminator object type do this
                    if(sourceArray[r][c] instanceof Taminator){
                        destinationArray[r][c] = new Taminator();
                        destinationArray[r][c].setAppearance(sourceArray[r][c].getAppearance());
                        destinationArray[r][c].setNumberOfNeighbors(sourceArray[r][c].getNumberOfNeighbors());
                    }
                }
            }
        }
    }

    //this method will set all element to null of the input array
    private void wipeArray(Critter[][] inputArray){
        //itterate through the inputArray matrix and set its elements to null
        for (int r=0; r<ROWS; r++){
            for (int c=0; c<COLUMNS; c++){
                if( inputArray[r][c] != null ){
                    inputArray[r][c] = null;
                }
            }
        }
    }

    //This function pauses the simulation and asks the user if they want to continue
    private boolean askUserToContinue(){
        
        boolean continueFlag = true;//initialize a boolean flag that will be used as the return value
        Scanner in = new Scanner(System.in);//create a scanner object
        
        //display the prompt message to the user
        System.out.print("Press enter to continue, or enter q to end the simulation: ");
        String entry = in.nextLine();//store the line entered by the user
        char firstChar = ' ';//initialize a variable that will store the first char if it is present

        if (entry.length()>0){//check if anything was typed by the user into the input line
            entry = entry.toUpperCase();//force the input to uppercase
            firstChar = entry.charAt(0);//store the first char of the input
            if (firstChar == 'Q' )
                continueFlag = false;
        }
        
        return continueFlag;
    }

    //this method runs a regular Biosphere simulation until an end condition is met
    public void start(int lonelinessThreshold, int overcrowdingThreshold, int birthThreshold){

        //Initialize previousGeneration
        previousGeneration = current;//assign a different pointer to the current world for nomenclature that matches assignment description

        boolean continue_simulation = true;//flag to indicate if the simulation should keep running

        while(continue_simulation == true)//keep running the simulation until an end condition is met
        {   
            //perform birth and death simulation
            simulateBirthAndDeath(lonelinessThreshold, overcrowdingThreshold, birthThreshold);

            //set the Taminator loose
            simulateTaminator();     
            
            //display all three states side by side
            displayThreeStates();
            
            //get user input to either continue or quit and assign the result to a flag of continuation
            continue_simulation = askUserToContinue();

            //set previousGeneration array to be same as afterTaminator array
            deepCopyCritterArray(afterTaminator, previousGeneration);
        }        
    }
}
