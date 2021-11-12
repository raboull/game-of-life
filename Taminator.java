/*
  Program summary: The Taminator class is an extension of the Critter class.
  
  Program details: 
  --The Taminator is not used in the critter count for births and deaths.
  --In the simulation a new Taminator will never be born nor will a Taminator ever die.
  --There is at most one Taminator read in from file. 
  --The only source of the Taminator comes from the starting input file.
  --A Taminator object is able to determine the location of it's neighbors and change their state to "EMPTY".
  --After taminating neighbors the Taminator will randomly teleport to another location within the biosphere.
  --First the Taminator will taminate all its neighbors and after this it will teleport to its new location.

  Limitations:
  --A Taminator will only teleport into empty slots in the Biosphere.
  --A Taminator will teleport to a new location, and that new location must be different from its current location.

  Version: March 28, 2021
  --A method to Taminate neighbors is added.
  --A method to teleport to a random new location is added.

  Version: March 20, 2021
  --Initial version is created.
 
*/

import java.util.Random;//import to use the random number generator

public class Taminator extends Critter
{
    public static final char DEFAULT_APPEARANCE = 'T';

    public Taminator(){
	    super(DEFAULT_APPEARANCE);
    }

    public Taminator(char newAppearance){
	    super(newAppearance);
    }

    //this method performs tamination of critters.
    public void taminateNeighbors(int r, int c, Critter[][] biosphereArray){

      //check if a neighbor is present in any adjacent cell and execute tamination of ane present adjacent neighbor
      if (super.isNeighborAbove(r,c,biosphereArray))
        taminateCritterAbove(r, c, biosphereArray);
      if (super.isNeighborAboveRight(r,c,biosphereArray))
        taminateCritterAboveRight(r, c, biosphereArray);
      if (super.isNeighborRight(r,c,biosphereArray))
        taminateCritterRight(r, c, biosphereArray);
      if (super.isNeighborBelowRight(r,c,biosphereArray))
        taminateCritterBelowRight(r, c, biosphereArray);
      if (super.isNeighborBelow(r,c,biosphereArray))
        taminateCritterBelow(r, c, biosphereArray);
      if (super.isNeighborBelowLeft(r,c,biosphereArray))
        taminateCritterBelowLeft(r, c, biosphereArray);
      if (super.isNeighborLeft(r,c,biosphereArray))
        taminateCritterLeft(r, c, biosphereArray);
      if (super.isNeighborAboveLeft(r,c,biosphereArray))
        taminateCritterAboveLeft(r, c, biosphereArray);    
    }

    
    //this function teleports the Taminator to a new valid location
    public void teleport(int r, int c, Critter[][] biosphereArray){

        int rCurrent = r;//current row location of the Taminator
        int cCurrent = c;//current column location of the Taminator
        int rNew = -1;//this variable will hold the new randomly generated row location of the Taminator
        int cNew = -1;//this variable will hold the new randomly generated column location of the Taminator
        boolean validNewLocation = false;//this flag informs us if the new random locaiton is valid

        while (validNewLocation == false){       
          Random randomIntGenerator = new Random();//create a reference and instantiate an object of the Random class

          rNew = randomIntGenerator.nextInt(Biosphere.ROWS);//generate a new row location smaller than number of Rows of the Biosphere
          cNew = randomIntGenerator.nextInt(Biosphere.COLUMNS);//generate a new column location smaller than number of Columns of the Biosphere

          //make sure that the new Taminator location is different from the current Taminator location
          if(rNew!=rCurrent || cNew!=cCurrent){
            if(biosphereArray[rNew][cNew].getAppearance()==Critter.EMPTY){//make sure that the new Location is an empty Critter
              validNewLocation = true;
            }
          }
        }
        //place a Taminator in the new location
        biosphereArray[rNew][cNew] = new Taminator(Taminator.DEFAULT_APPEARANCE);
        //set the Taminator at the previous location to an empty Critter
        biosphereArray[rCurrent][cCurrent] = new Critter(Critter.EMPTY);
    }

    //this method taminates the critter above the Taminator
    private void taminateCritterAbove(int r, int c, Critter[][] biosphereArray){
      //store the location above the input coordintes
      int rAboveOfInput = r-1;
      int cAboveOfInput = c;     
      biosphereArray[rAboveOfInput][cAboveOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rAboveOfInput][cAboveOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter above-right of the Taminator
    private void taminateCritterAboveRight(int r, int c, Critter[][] biosphereArray){
      //store the location above-right of the input coordintes
      int rAboveRightOfInput = r-1;
      int cAboveRightOfInput = c+1;     
      biosphereArray[rAboveRightOfInput][cAboveRightOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rAboveRightOfInput][cAboveRightOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter right of the Taminator
    private void taminateCritterRight(int r, int c, Critter[][] biosphereArray){
      //store the location right of the input coordintes
      int rRightOfInput = r;
      int cRightOfInput = c+1;     
      biosphereArray[rRightOfInput][cRightOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rRightOfInput][cRightOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter below-right of the Taminator
    private void taminateCritterBelowRight(int r, int c, Critter[][] biosphereArray){
      //store the location below-right of the input coordintes
      int rBelowRightOfInput = r+1;
      int cBelowRightOfInput = c+1;     
      biosphereArray[rBelowRightOfInput][cBelowRightOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rBelowRightOfInput][cBelowRightOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter below the Taminator
    private void taminateCritterBelow(int r, int c, Critter[][] biosphereArray){
      //store the location below the input coordintes
      int rBelowOfInput = r+1;
      int cBelowOfInput = c;     
      biosphereArray[rBelowOfInput][cBelowOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rBelowOfInput][cBelowOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter below-left of the Taminator
    private void taminateCritterBelowLeft(int r, int c, Critter[][] biosphereArray){
      //store the location below the input coordintes
      int rBelowLeftOfInput = r+1;
      int cBelowLeftOfInput = c-1;     
      biosphereArray[rBelowLeftOfInput][cBelowLeftOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rBelowLeftOfInput][cBelowLeftOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter left of the Taminator
    private void taminateCritterLeft(int r, int c, Critter[][] biosphereArray){
      //store the location left of the input coordintes
      int rLeftOfInput = r;
      int cLeftOfInput = c-1;     
      biosphereArray[rLeftOfInput][cLeftOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rLeftOfInput][cLeftOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }

    //this method taminates the critter above-left of the Taminator
    private void taminateCritterAboveLeft(int r, int c, Critter[][] biosphereArray){
      //store the location above-left of the input coordintes
      int rAboveLeftOfInput = r-1;
      int cAboveLeftOfInput = c-1;     
      biosphereArray[rAboveLeftOfInput][cAboveLeftOfInput].setAppearance(Critter.EMPTY);//make the Critter object empty
      biosphereArray[rAboveLeftOfInput][cAboveLeftOfInput].setNumberOfNeighbors(-1);//set neighbor count to an invalid value
    }
}







