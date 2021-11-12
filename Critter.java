/*
  Program summary: Tracks information associated with the basic life form inhabiting the simulated world.
  
  Program details: 
  --The critter mignt have neighbors in adjacent cells.
  --Methods to count neighbors around a Critter object are included in this class.
  --A boolean method is present to determine whether a potential location is inside a Biosphere that the Critter occupies. 

  Limitations:
  --Only two types of critter appearances are available, * and space.
  
  Version: March 28, 2021
  --Methods to count neighbors are implemented.
  
  Version: March 20, 2021
  --Initial version is created.
 
*/

public class Critter
{
    public static final char DEFAULT_APPEARANCE = '*';
    public static final char EMPTY = ' ';
    
    private char appearance;
    private int numberOfNeighbors;

    public Critter (){
        setAppearance(DEFAULT_APPEARANCE);
        numberOfNeighbors = -1;//initialize value of neighbors to an invalid number
    }

    public Critter(char ch){
	    setAppearance(ch);
        numberOfNeighbors = -1;//initialize value of neighbors to an invalid number
    }

    public char getAppearance(){
	    return appearance;
    } 

    public void setAppearance(char newAppearance){
        appearance = newAppearance;
    } 

    //this function returns the number of neighbors for this Critter object
    public int getNumberOfNeighbors(){
        return this.numberOfNeighbors;
    }

    //this function updates the number of neighbors for this Critter object
    public void setNumberOfNeighbors(int newNumberOfNeigbors){
        this.numberOfNeighbors = newNumberOfNeigbors;
    }

    public String toString()
    {
	String s = "" + appearance;
        return(s);
    }

    //this method counts the number of neighbors for this Critter and updates the related attribute
    public void updateNumberOfNeighbors(int r, int c, Critter[][] biosphereArray){

        int neighborCounter = 0;

        if (isNeighborAbove(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborAboveRight(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborRight(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborBelowRight(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborBelow(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborBelowLeft(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborLeft(r,c,biosphereArray))
            neighborCounter++;
        if (isNeighborAboveLeft(r,c,biosphereArray))
            neighborCounter++;

        this.numberOfNeighbors = neighborCounter;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE above the input location?
    protected boolean isNeighborAbove(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter above the input Critter location
        boolean isNeighborAbove = false;

        //store the location above the input coordintes
        int rAboveOfInput = r-1;
        int cAboveOfInput = c;

        //check if the location above the input is inside the Biosphere
        if(isInsideBiosphere(rAboveOfInput, cAboveOfInput) == true){
            //check if critter above is of DEFAULT_APPEARANCE
            if(biosphereArray[rAboveOfInput][cAboveOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborAbove = true;
            }
        }
        return isNeighborAbove;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE above-right of the input location?
    protected boolean isNeighborAboveRight(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter above-right of the input Critter location
        boolean isNeighborAboveRight = false;

        //store the location above-right of the input coordintes
        int rAboveRightOfInput = r-1;
        int cAboveRightOfInput = c+1;
        
        //check if the location above-right of the input is inside the Biosphere
        if(isInsideBiosphere(rAboveRightOfInput, cAboveRightOfInput) == true){
            //check if critter above-right is of DEFAULT_APPEARANCE
            if(biosphereArray[rAboveRightOfInput][cAboveRightOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborAboveRight = true;
            }
        }
        return isNeighborAboveRight;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE right of the input location?
    protected boolean isNeighborRight(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter right of the input Critter location
        boolean isNeighborRight = false;

        //store the location right of the input coordintes
        int rRightOfInput = r;
        int cRightOfInput = c+1;
        
        //check if the location right of the input is inside the Biosphere
        if(isInsideBiosphere(rRightOfInput, cRightOfInput) == true){
            //check if critter to the right is of DEFAULT_APPEARANCE
            if(biosphereArray[rRightOfInput][cRightOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborRight = true;
            }
        }
        return isNeighborRight;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE below-right of the input location?
    protected boolean isNeighborBelowRight(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter below-right of the input Critter location
        boolean isNeighborBelowRight = false;

        //store the location below-right of the input coordintes
        int rBelowRightOfInput = r+1;
        int cBelowRightOfInput = c+1;
        
        //check if the location below-right of the input is inside the Biosphere
        if(isInsideBiosphere(rBelowRightOfInput, cBelowRightOfInput) == true){
            //check if critter below-right of the input location is of DEFAULT_APPEARANCE
            if(biosphereArray[rBelowRightOfInput][cBelowRightOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborBelowRight = true;
            }
        }
        return isNeighborBelowRight;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE below the input location?
    protected boolean isNeighborBelow(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter below the input Critter location
        boolean isNeighborBelow = false;

        //store the location below-right of the input coordintes
        int rBelowOfInput = r+1;
        int cBelowOfInput = c;
        
        //check if the location below the input is inside the Biosphere
        if(isInsideBiosphere(rBelowOfInput, cBelowOfInput) == true){
            //check if critter below the input location is of DEFAULT_APPEARANCE
            if(biosphereArray[rBelowOfInput][cBelowOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborBelow = true;
            }
        }
        return isNeighborBelow;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE below-left of the input location?
    protected boolean isNeighborBelowLeft(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter below-left of the input Critter location
        boolean isNeighborBelowLeft = false;

        //store the location below-left of the input coordintes
        int rBelowLeftOfInput = r+1;
        int cBelowLeftOfInput = c-1;
        
        //check if the location below-left of the input is inside the Biosphere
        if(isInsideBiosphere(rBelowLeftOfInput, cBelowLeftOfInput) == true){
            //check if critter below-left of the input location is of DEFAULT_APPEARANCE
            if(biosphereArray[rBelowLeftOfInput][cBelowLeftOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborBelowLeft = true;
            }
        }
        return isNeighborBelowLeft;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE left of the input location?
    protected boolean isNeighborLeft(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter left of the input Critter location
        boolean isNeighborLeft = false;

        //store the location left of the input coordintes
        int rLeftOfInput = r;
        int cLeftOfInput = c-1;
        
        //check if the location left of the input is inside the Biosphere
        if(isInsideBiosphere(rLeftOfInput, cLeftOfInput) == true){
            //check if critter left of the input location is of DEFAULT_APPEARANCE
            if(biosphereArray[rLeftOfInput][cLeftOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborLeft = true;
            }
        }
        return isNeighborLeft;
    }

    //this function answers the question: is there a critter with DEFAULT_APPEARANCE above-left of the input location?
    protected boolean isNeighborAboveLeft(int r, int c, Critter[][] biosphereArray){
        //this boolean flag answers whether there is a critter above-left of the input Critter location
        boolean isNeighborAboveLeft = false;

        //store the location above-left of the input coordintes
        int rAboveLeftOfInput = r-1;
        int cAboveLeftOfInput = c-1;
        
        //check if the location above-left of the input is inside the Biosphere
        if(isInsideBiosphere(rAboveLeftOfInput, cAboveLeftOfInput) == true){
            //check if critter above-left of the input location is of DEFAULT_APPEARANCE
            if(biosphereArray[rAboveLeftOfInput][cAboveLeftOfInput].appearance == Critter.DEFAULT_APPEARANCE){
                isNeighborAboveLeft = true;
            }
        }
        return isNeighborAboveLeft;
    }

    //this method answers the question: is the inputLocation inside the world?
    private boolean isInsideBiosphere(int r, int c){
        boolean isInsideBiosphere = false;
        
        if(r>=0 && r< Biosphere.ROWS && c>=0 && c<Biosphere.ROWS)
            isInsideBiosphere = true;
        
        return isInsideBiosphere;
    }
}

