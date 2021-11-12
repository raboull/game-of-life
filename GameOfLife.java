/*
  Program summary: Text based version of Conway's biological simulation "The Game of Life".
  
  Program details: 
  --Starting execution point for the program.
  --Asks the user is they want to simulate a regular of a prosperous bioshpere.
  --Starting data is read from a text file and the simulation continues on a turn by turn basis.
  --The starting data consists of empty spaces or stars to be read into an array of references to 'Critters'.
  --Spaces in the input file will result in a critter whose appearance is 'EMPTY'.
  --Stars in the input file will result in a critter whose appearance is the 'DEGAULT_APPEARANCE'.
  --Each turn the rules of births and deaths wil change the pattern.
  --Reads information from a file, creates a biosphere and continuously runs new turns until the user quits.
  --Prgram runs the simulation until the user enter 'q' or 'Q' to quit the program.
  --Each turn the program will display 3 states of the biosphere.
  --The user is asked to enter 'R' or 'r' if they would like to simulate a regular biosphere.
  --The user is adked to enter 'P' or 'p' if they would like to simulate a prosperous biosphere.
  --Until an expected input is provided by the user the prompt will repeat infinitely.
  --Any input file can be used with a regular or a prosperous biosphere.

  Limitations:
  --The input text files can only contain spaces, '*', and up to one T charector.
  --The input file can only accommodate one Tamminator in the biosphere.
  --The input file is 10x10, other sizes are not accounted for.

  Version: March 28, 2021
  --Added user input loop and option for a prosperous biosphere simulation.

  Version: March 20, 2021
  --Implemented the initial version.
 
*/

import java.util.Scanner;

/* No additional methods and nor attributes to be added. */ 

public class GameOfLife
{
    public static void main (String [] args){
        
        //get user input for the biosphere type they would like to run
        Scanner in = new Scanner(System.in);//create a scanner object to get user input
        char firstChar = ' ';//initialize a variable that will store the first char if it is present
        //the following loop will keep asking for user input until a valid input is provided
        while(firstChar == ' '||(firstChar != 'P' && firstChar != 'R')){
                    
            System.out.println("Choose birth and death rules");
            System.out.println("(p)rosperous bioshpere");
            System.out.println("(r)egular bioshpere");
            System.out.print("Bioshpere type: ");
            String entry = in.nextLine();//store the line entered by the user
            if (entry.length()>0){//check if anything was typed by the user into the input line
            
                entry = entry.toUpperCase();//force the input to uppercase
                firstChar = entry.charAt(0);//store the first char of the input
            }
        }
    
        //start the type of biosphere the user would like to simulate
        if(firstChar == 'R'){//Check if the user requested a regular biosphere
            Biosphere regular;
            regular = new Biosphere(FileInitialization.read());
            regular.start(Biosphere.LONELINESS_THRESHOLD, Biosphere.OVERCROWDING_THRESHOLD, Biosphere.BIRTH_THRESHOLD);
        }
        else if (firstChar == 'P'){//check if the user requested a prosperous biosphere
            ProsperousBiosphere prosperous;
            prosperous = new ProsperousBiosphere(FileInitialization.read());
            prosperous.start(ProsperousBiosphere.LONELINESS_THRESHOLD, ProsperousBiosphere.OVERCROWDING_THRESHOLD, ProsperousBiosphere.BIRTH_THRESHOLD);;
        } 

    }
}



