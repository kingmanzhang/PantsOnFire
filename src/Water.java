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
 *
 * The Water class is responsible for defining water objects. 
 * Its appearance is defined by a graphic object
 * It has another three fields, speed, distanceTraveled and moving direction
 * The speed field indicates the moving speed of a water object
 * The distranceTraveled field tracks how much distance has an water object traveled
 * The direction field indicates the moving direction of a water object
 * @author Xingmin Zhang
 */
public class Water {
	
	private Graphic graphic;//a field for the graphic of water
	private float speed; //moving speed of a water object
	private float distanceTraveled; //tracks the distance traveled by a water object
	private float direction; //tracks the moving direction
	
	/**
	 * Constructor of water
	 * The constructor contains information for the position of water when created, and
	 * the moving direction
	 * @param x position of a water object
	 * @param y position of a water object
	 * @param direction moving direction of a water object
	 */
	public Water(float x, float y, float direction) {
		this.graphic = new Graphic("WATER");
		graphic.setPosition(x, y);
		this.speed = 0.7f;
		this.direction = direction;
		this.distanceTraveled = 0.0f; //tracking total distance traveled by water
	}
	
	/**
	 * public method to access to the graphic field
	 * @return the graphic field of a water object
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	/**
	 * The method updates a water object
	 * It should move to the next position with the defined direction
	 * @param time
	 * @return
	 */
	public Water update(int time) {
		//only update the position of water when it has traveled less than 200 pixels. 
		if (this.distanceTraveled < 200) {
			graphic.draw();
			//move to the next position with the defined direction
			graphic.setDirection(direction);
			graphic.setX((graphic.getX() + (float) Math.cos(direction) * speed * time));
			graphic.setY((graphic.getY() + (float) Math.sin(direction) * speed * time));
			//update total distance traveled by object
			this.distanceTraveled += speed * time;
			}
		
		//if water traveled less than 200 pixels, return the water object
		//otherwise, let water disappear
		if (this.distanceTraveled <= 200) {
			return this;	
		}
		else {
			return null;
		}
		
	}
	
}
