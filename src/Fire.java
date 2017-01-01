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


import java.util.Random;

/**
 * The Fire class is responsible for defining Fire object. 
 * Its appearance is defined by the graphic class. 
 * It has four private fields, graphic, randGen, fireballCountdown, and heat
 * The graphic field indicates the graphical appearance of a Fire object.
 * The randGen refers to a random number generator object. 
 * The field fireballCountdown tracks times that has passed, and signal the 
 * fire object to eject a fireball when the value gets to zero. 
 * The field head indicates how "healthy" the fire object is. The fire object 
 * loses one heat when it gets hit by a water object. When the value goes down
 * to zero, the fire object is dead.
 * @author Xingmin Zhang
 *
 */

public class Fire {
	//a field to the graphics class
	private Graphic graphic; 
	//a reference to a random number generator
	private Random randGen; 
	//a field to count the time down before fireball appears
	private int fireballCountdown; 
	//a field to indicate the status of fire. 
	private int heat;
	 
	public Fire(float x, float y, Random randGen) {
		this.graphic = new Graphic("FIRE");  //set the appearance of fire
		this.randGen = randGen;//pass the random number generator to the reference
		graphic.setPosition(x, y);//set the position of fire
		//the countdown time starts from a random numbe between 3000 and 6000
		fireballCountdown = randGen.nextInt(3001) + 3000;
		this.heat = 40; //the fire has 40 heat; when heat is 0, fire is dead
	}
	
	/**
	 * This method allows access to the graphic field of Fire
	 * @return the graphic field
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	
	/**
	 * This method updates a fire object
	 * When a fire object is live (has heat), it ejects a fireball every 3-6 seconds.
	 * @param time - is the amount of time in milliseconds that has elapsed since the 
	 * last time this update was called.
	 * @return null unless a new Fireball was just created and launched. In that case, 
	 * a reference to that new Fireball is returned instead.
	 */
	public Fireball update(int time) {
		Fireball fireball = null;
		if(heat > 0) {
				graphic.draw();
				fireballCountdown = fireballCountdown - time;
				if (fireballCountdown <= 0) {
					//eject a new fireball
				   fireball = new Fireball(graphic.getX(), graphic.getY(), 
				   		randGen.nextFloat() * (float)Math.PI * 2);
				   //then reset fireballCountdown
					fireballCountdown = randGen.nextInt(3001) + 3000;
				}
		} 
		return fireball;
	}
	
	
	/**
	 * This method handles collision with a water object
	 * When a fire object is hit with a water, it loses one heat and the water object
	 * disappears
	 * @param water: an array of water objects
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if(water[i] != null && graphic.isCollidingWith(water[i].getGraphic())) {
				heat--;
				water[i] = null;
			}
		}
	}
	

	/**
	 * The method determines whether a fire object should be removed
	 * When a fire object has 0 heat, it is no longer alive and should be removed.
	 * @return whether it should be removed
	 */
	public boolean shouldRemove() {
		if (heat > 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
