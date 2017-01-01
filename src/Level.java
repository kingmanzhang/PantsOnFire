///////////////////////////////////////////////////////////////////////////////
//
// Title:            Program 3: Pants on Fire
// Files:            Fire.java, Fireball.java, Hero.java, Level.java
//							Pant.java, Water.java
// Semester:         Fall 2016
//
// Author:           Xingmin Zhang	
// Email:            xzhang66@wisc.edu
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     none
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.
//
// Persons:          none
// Online Sources:   none
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 */
public class Level
{
	private Random randGen;    //a random number generator
	private Hero hero;         //main character for game
	private Water [] water;     //waters ejected from hero
	private ArrayList <Pant> pants;  //pant list
	private ArrayList <Fireball> fireballs;  //fireball list
	private ArrayList <Fire> fires; //fire list
   private int controlType; //track which control type the player want to use
	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their beginning states.
	 * @param randGen is the only Random number generator that should be used
	 * throughout this level, by the Level itself and all of the Objects within.
	 * @param level is a string that either contains the word "RANDOM", or the 
	 * contents of a level file that should be loaded and played. 
	 */
	public Level(Random randGen, String level) 
	{ 	
		this.randGen = new Random();
		this.water = new Water[8];     //initialize water array to contain 8 waters
		this.pants = new ArrayList<Pant>(); //initialize pants list
		this.fireballs = new ArrayList<Fireball>(); //initialize fireball list
		this.fires = new ArrayList<Fire>(); //initialize fire list
		
      //start the game at a random level
      if (level.equals("RANDOM")) {
      	createRandomLevel();
      }
      
      //start the game according to the specified level
      else {
      	loadLevel(level);
      }
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called.  This can be used to control the speed that
	 * objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short
	 * 3 second pause and a message indicating that the player has lost.  When
	 * this method returns "ADVANCE", a short pause and win message will be 
	 * followed by the creation of a new level which replaces this one.  When
	 * this method returns anything else (including "CONTINUE"), the GameEngine
	 * will simply continue to call this update() method as usual. 
	 */
	public String update(int time) 
	{
		//move hero to the next place
		hero.update(time, water);
		
		//move water to hit other objects
		for (int i = 0; i < 8; i++) {
			if(water[i] != null) {
				water[i] = water[i].update(time);	
			}
		}

		//if a pant gets hit by a fireball, the pant disappear and a new fire is 
		//createt at the same position
      for (int i = 0; i < pants.size(); i++) {
	      pants.get(i).update(time);
	      Fire newFire = pants.get(i).handleFireballCollisions(fireballs);
	      if (newFire != null) {
	      	fires.add(newFire);
	      }
		}
    
      //a fire ejects a fireball every 3000 to 6000 ms
      //if a fire gets hit by fireballs for 40 times, it will be destroyed and 
      //disappear
      for (int i = 0; i < fires.size(); i++) {
      	fires.get(i).handleWaterCollisions(water);
      	Fireball fireball = fires.get(i).update(time);
         if (fireball!= null) {
         	fireballs.add(fireball);
         }
      }
      
      //if a fireball collides with a water, both are destroyed and disappear
      for (int i = 0; i < fireballs.size(); i++) {
      	fireballs.get(i).update(time);
      	fireballs.get(i).handleWaterCollisions(water);
      }
      
      //remove dead fireballs, pants and fires
      for (int i = 0; i < fireballs.size(); i++) {
	      if (fireballs.get(i).shouldRemove()) {
	      	fireballs.remove(i);
	      }
      }
      for (int i = 0; i < pants.size(); i++) {
      	if(pants.get(i).shouldRemove()) {
      		pants.remove(i);
      	}
      }
      for (int i = 0; i < fires.size(); i++) {
      	if(fires.get(i).shouldRemove()) {
      		fires.remove(i);
      	}
      }
      
      //if the hero is hit with a fireball, the game is lost
      //if the hero destroys all fires, the play advance to the next level
      //?What is CONTINUE?
      if (hero.handleFireballCollisions(fireballs) ||
      		pants.size() == 0) {
      	return "QUIT";
      }
      else if (fires.size() == 0) {
      	return "ADVANCE";
      }
      else
      	return "CONTINUE"; 
	}	

	/**
	 * This method returns a string of text that will be displayed in the
	 * upper left hand corner of the game window.  Ultimately this text should 
	 * convey the number of unburned pants and fires remaining in the level.  
	 * However, this may also be useful for temporarily displaying messages that 
	 * help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() 
	{
		//return how many pants and fires are still left
		String status = String.format("Pants Left: %d\nFires Left: %d", 
				pants.size(), fires.size());
		return status;
	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires,
	 * and 20 randomly positioned Pants.
	 */
	public void createRandomLevel() 
	{ 
		//set the control type to a random type
		controlType = randGen.nextInt(3) + 1;
		
		//create a hero at the center of the game screen
		this.hero = new Hero((float)(GameEngine.getWidth()/2.0), 
				(float)(GameEngine.getHeight()/2.0), controlType);
		
		//create 20 pant at random positions in the game screen
		for (int i = 0; i < 20; i++) {
			pants.add(new Pant(GameEngine.getWidth() * randGen.nextFloat(), 
					GameEngine.getHeight() * randGen.nextFloat(),randGen));
		}
		//create 6 fire at random positions in the game screen
      for (int i = 0; i < 6; i++) {
 			fires.add(new Fire(GameEngine.getWidth() * randGen.nextFloat(), 
 					GameEngine.getHeight() * randGen.nextFloat(),randGen));
		}
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * @param level is a string containing the contents of a custom level file 
	 * that is read in by the GameEngine.  The contents of this file are then 
	 * passed to Level through its Constructor, and then passed from there to 
	 * here when a custom level is loaded.  You can see the text within these 
	 * level files by dragging them onto the code editing view in Eclipse, or 
	 * by printing out the contents of this level parameter.  Try looking 
	 * through a few of the provided level files to see how they are formatted.
	 * The first line is always the "ControlType: #" where # is either 1, 2, or
	 * 3.  Subsequent lines describe an object TYPE, along with an X and Y 
	 * position, formatted as: "TYPE @ X, Y".  This method should instantiate 
	 * and initialize a new object of the correct type and at the correct 
	 * position for each such line in the level String.
	 */
	public void loadLevel(String level) 
	{ 
		//use a scanner to read the level
		Scanner scnr = new Scanner(level); 
		
		//the number in the first line is control type
		scnr.next();
		controlType = scnr.nextInt();
		
		//next, parse each line to create a corresponding game character
		String gameCharacter; //what game character to create in each line
		float [] position;    //what is the initial position for the game character
		scnr.nextLine();      //move to the second line from the end of the first line
		
		//whenever there is a new line that defines a game character, parse the 
		//new line and create a corresponding game character
		while(scnr.hasNextLine() && scnr.hasNext()) { 
			
			//first remove the coma(,) so that one can parse out the numbers
			String newLine = removeComma(scnr.nextLine());
			
			//extract the game character and pass it to gameCharacter
			gameCharacter = gameCharacterExtractor(newLine);
			
			//extract the position of the game character and pass it to the 
			//position array
			position = floatExtractor(newLine);
			
			//create the corresponding game character at defined position
			if (gameCharacter.equals("HERO")) { //if the next word is "HERO"
				hero = new Hero(position[0], position[1], controlType);
			}
			else if (gameCharacter.equals("FIRE")) {
				fires.add(new Fire(position[0], position[1], randGen));
			}
			else if (gameCharacter.equals("PANT")) {
				pants.add(new Pant(position[0], position[1], randGen));
			}
			else
				;
		}		
	}

	/**
	 * This is a helper method that removes comma (',') from a line of strings
	 * After removing the comma, the numbers are separated by space and easier
	 * to handle
	 * @param str is a line of string that defines that game character and position
	 * it should follow this format: "FIRE @ 541.0, 387.0"
	 * @return a new string after removing the comma. It will return 
	 * "FIRE @ 541.0 387.0" for the example string.
	 */
	private static String removeComma(String str) {
		String stringCommaRemoved = ""; //store the new string
		Scanner scnr = new Scanner(str);
		for (int i = 0; i < str.length(); i++) {
			//copy all characters to the new string except the comma
			if(str.charAt(i) != ',') {   
				stringCommaRemoved += str.charAt(i);
			}
		}
		scnr.close();
		return stringCommaRemoved;
	}

	/**
	 * This is a helper method that extracts the first two float numbers from the 
	 * string. 
	 * @param str is the comma-free line, for example "FIRE @ 541.0 387.0"
	 * @return return a float array that contains the first two float numbers,
	 * such as [541.0f, 387.0f], for the position of the game character
	 */
	
	private static float[] floatExtractor (String str) {
		
		//define an array to store two float numbers
		float[] floatNum = new float[2];
		//use a scanner to extract the first two (only two) float numbers
		Scanner scnr = new Scanner(str);
		int i = 0;
		//go throw the tokens in a line of strings
		//if a token is a float number, pass it to the floatNum array
		while(scnr.hasNext()) {
			if(scnr.hasNextFloat()) {
				floatNum[i] = scnr.nextFloat();
				i++;
				}
			else
				scnr.next();	
		if(i == 2) //only pass two numbers to the array
			break;
		}
		scnr.close();
		return floatNum;
	}
	
	/**
	 * This is a helper method to extract the word for the game character
	 * @param str is a line of strings for defining a new character
	 * @return the first word in the string, which should be "HERO", "FIRE", or "PANT"
	 * otherwise, report an error message
	 */

	private static String gameCharacterExtractor (String str) {
		String gameCharacter; //which game character to create
		Scanner scnr = new Scanner(str);
		gameCharacter = scnr.next(); //the game character is the first word
		scnr.close();
		if (gameCharacter.equals("HERO") ||  
				gameCharacter.equals("FIRE") ||
					gameCharacter.equals("PANT")) {
			return gameCharacter;
		}
		else 
			System.out.println("Game character cannot be found");
			return "Game character not found!";
	}
	
	/**
	 * This method creates and runs a new GameEngine with its first Level.  Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * @param args is the sequence of custom level files to play through.
	 */
	public static void main(String[] args)
	{
		GameEngine.start(null,args);
	}
}


