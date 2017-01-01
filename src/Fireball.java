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

/**
 * The Fireball class is responsible to define fireballs. 
 * It has four private field, graphic, speed, isAlive and direction. 
 * The graphic field indicates the graphic appearance of a fireball. 
 * The speed field indicates the moving speed of a fireball.
 * the isAlive field tracks the life of a fireball. 
 * The direction field indicates the moving direction of a fireball. 
 * @author Xingmin Zhang
 *
 */
public class Fireball {

	private Graphic graphic;//a field to the graphic class
	private float speed; //a field to track the moving speed of fireball
	private boolean isAlive; //a field to track the status of fireball
	private float direction; //a field to indicate the moving direction of fireball
	
	public Fireball(float x, float y, float directionAngle) {
		this.graphic = new Graphic("FIREBALL"); //set the appearance of fireball
		graphic.setPosition(x, y); //set the position of fireball
		this.speed = 0.2f; //set the moving speed of fireball
		this.direction = directionAngle; //set the moving direction of fireball
		this.isAlive = true; //set the status of fireball as alive
	}
	
	
	/**
	 * This method allows acess to the graphic field of a fireball. 
	 * @return the graphic field
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	

	/**
	 * This method updates a fireball if it is alive.
	 * move fireball to the next position
	 * after the fireball is moved to more than 100 pixels of any edge, 
	 * the fireball is no longer live
	 * @param time is the amount of time in milliseconds that has elapsed since the 
	 * last time this update was called
	 */
	public void update(int time) {
		   if (isAlive) {
		   	graphic.draw();
		   	graphic.setDirection(direction);
				graphic.setX((graphic.getX() + (float) Math.cos(direction) * speed * time));
				graphic.setY((graphic.getY() + (float) Math.sin(direction) * speed * time));
			   if (graphic.getX() < -100
			   		|| (graphic.getX() - GameEngine.getWidth()) > 100
			   		|| graphic.getY() < -100
			   		|| (graphic.getY() - GameEngine.getHeight()) > 100) {
				   	isAlive = false;  	
			   }
		   }
		   else {
		   	
		   }
	}
	
	/**
	 * This method handles collision of a fireball with a water object
	 * When a fireball collide with a water, both fireball and water is no longer alive
	 * @param water-an array of water objects
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if(water[i] != null && graphic.isCollidingWith(water[i].getGraphic())) {
				
					water[i] = null;
					isAlive = false;
				
			}
		}
	}
	
	/**
	 * This is a public method for other classes to change the status of fireball
	 */
	public void destroy() {
		isAlive = false;
	}
	
	/**
	 * a method to test whether the object needs to be removed
	 * @return whether a fireball should be removed
	 */
	public boolean shouldRemove() {
		return !isAlive;
	}
}
