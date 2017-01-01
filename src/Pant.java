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
/**
 * The Pant class is responsible to define a pant for the game. 
 * It has three private fields, graphic, randGen, and isAlive. 
 * The graphic field indicates the appearance.
 * The isAlive field tracks the life of a pant. 
 * The player of the game should try to protect their pants from being hit by
 * fireballs. 
 * When a pant is hit by a fireball, it becomes a new fire that ejects fireballs.
 * When all the pants are lost, the player loses the game. 
 * @author Xingmin Zhang
 *
 */
public class Pant {
	private Graphic graphic; //a field to the graphic class
	private Random randGen;  //a reference to a random number generator
	private boolean isAlive; //a field to track the status of pant
	 

	//constructor
	public Pant(float x, float y, Random randGen) {
		this.graphic = new Graphic("Pant");  //set appearance to "PANT"
		this.randGen = randGen;  //use the random number generator passed during 
										 //instantiation
		this.graphic.setPosition(x, y); //set the position
		this.isAlive = true;//track the status of Pant
	}
	
	
	/**
	 * public method to access to the graphic field
	 * @return the graphic field of a pant object
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	
	/**
	 * update pant when it is still live
	 * @param time is the amount of time in milliseconds that has elapsed since the 
	 * last time this update was called
	 */
	public void update(int time) {
		if (isAlive) {
			graphic.draw();
		}
		
	}

	//
	/**
	 * The method handles fireball collision with a pant
	 * when a pant collides with a fireball, destroy the fireball and change pant to 
	 * a new fire object
	 * @param fireballs- a list of fireballs ejected from fire objects
	 * @return fire-if a pant collides with a fireball, the pant will disappear and a
	 * fire will be created at the same location of the pant. 
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		Fire fire = null;
		for (int i = 0; i < fireballs.size(); i++) {
			if (isAlive && fireballs.get(i) != null 
					&& graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				isAlive = false;
				fireballs.get(i).destroy();
				fire = new Fire (graphic.getX(), graphic.getY(), randGen);
			}
		}
		return fire;
	}
	
	/**
	 * a method to test whether the object needs to be removed
	 * @return whether the object needs to be removed
	 */
	public boolean shouldRemove() {
		return !isAlive;
	}

	
}
