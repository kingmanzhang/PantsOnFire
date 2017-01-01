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

/**
 * The Hero class is responsible for defining the main character for the game. 
 * The appearance of the Hero class is defined by the Graphic class.
 * the class also has speed and control type fields.
 */
public class Hero {
	
	private Graphic graphic;
	private float speed;
	private int controlType;
	//Constructor of Hero class includes the position (x, y) and control type
	public Hero(float x, float y, int controlType)
	{
		this.controlType = controlType;
		this.graphic = new Graphic("HERO"); 
		this.speed = 0.12f;
		graphic.setPosition(x, y);
	}
	
	/**
	 * This is a help method that calculates the distance from Hero to the 
	 * mouse cursor
	 * @return the distance from the Hero to the mouse cursor
	 */
	private float distance() {
		float distance; 
		distance = (float) 
				(Math.sqrt(Math.pow(graphic.getX() - GameEngine.getMouseX(), 2) 
				+Math.pow(graphic.getY() - GameEngine.getMouseY(), 2)));
		return distance;
	}
	
	/**
	 * This method allows access to the graphic field of hero
	 * @return the graphic field of hero
	 */
	public Graphic getGraphic() {
		return graphic;
	}
	
	
	/**
	 * This method handle the collision of hero and fireballs
	 * when the hero is hit with a fireball, the hero is dead
	 * @param fireballs-is a list of fireballs
	 * @return whether hero has collided with a fireball
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		boolean hasCollided = false;
		for (int i = 0; i < fireballs.size(); i++) {
			if (graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				hasCollided = true;
				break;	
			}			
		}
		return hasCollided;
	}
	
	
	/**
	 * The update() method updates the position of Hero based on control type
	 * @param time-the time that has passed since the last time this is called
	 * @param water-an array of water objects
	 */
	
	public void update(int time, Water [] water) {
		graphic.draw();
		//if control type is 1, move Hero right, left, up or down and Hero face the 
		//same direction of movement
		if (controlType == 1) {
			//move graphic to the right, and set the direction to face right
			if(GameEngine.isKeyHeld("D")) { 
				//one way to set direction of Hero
				//graphic.setDirection(0.0f); 
				//the other way to set direction of Hero
				graphic.setDirection(graphic.getX() + speed*time, graphic.getY());
				graphic.setX((graphic.getX() + speed*time) % GameEngine.getWidth());
				
			}
			//move graphic to the left, and set the direction to face left
			if(GameEngine.isKeyHeld("A")) {
				//graphic.setDirection((float)Math.PI);
				graphic.setDirection(graphic.getX() - speed*time, graphic.getY());
				graphic.setX((GameEngine.getWidth() + graphic.getX() - speed*time) 
						% GameEngine.getWidth());
				
			}
			//move graphic up, and set the direction to face up
			if(GameEngine.isKeyHeld("W")) {
				//graphic.setDirection((float)Math.PI * 3 / 2);
				graphic.setDirection(graphic.getX(), graphic.getY() - speed*time);
				graphic.setY((GameEngine.getHeight() + graphic.getY() - speed*time) 
						% GameEngine.getHeight());
				
			}
			//move graphic down, and set the direction to face down
			if(GameEngine.isKeyHeld("S")) {
				//graphic.setDirection((float)Math.PI / 2);
				graphic.setDirection(graphic.getX(), graphic.getY() + speed*time);
				graphic.setY((graphic.getY() + speed*time) % GameEngine.getHeight());
				
			}
		}
		
		//if control type is 2, move Hero right, left, up or down and Hero face the 
		//direction of the mouse cursor
		else if (controlType == 2) {
			if(GameEngine.isKeyHeld("D")) { 
				graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
				graphic.setX((graphic.getX() + speed*time) % GameEngine.getWidth());
				
			}
			
			if(GameEngine.isKeyHeld("A")) {
				graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
				graphic.setX((GameEngine.getWidth() + graphic.getX() - speed*time) 
						% GameEngine.getWidth());
				
			}
			
			if(GameEngine.isKeyHeld("W")) {
				
				graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
				graphic.setY((GameEngine.getHeight() + graphic.getY() - speed*time) 
						% GameEngine.getHeight());
				
			}
			
			if(GameEngine.isKeyHeld("S")) {
		
				graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
				graphic.setY((graphic.getY() + speed*time) % GameEngine.getHeight());
				
			}
			}
		
		//if control type is 3, move Hero toward the mouse cursor automatically and 
		//Hero face the direction of the mouse cursor
		else if (controlType == 3) {
			
			if (distance() > 20) {
				graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
				graphic.setX((graphic.getX() + graphic.getDirectionX() * speed * time));
				graphic.setY(graphic.getY() + graphic.getDirectionY() * speed * time);
				
			}
			
		}
		
		//if Space is being pressed or mouse (left) is being clicked, generate
		//a new water object until there are 8
		if(GameEngine.isKeyHeld("SPACE") || GameEngine.isKeyHeld("MOUSE")) {
			for (int i = 0; i < 8; i++) {
				if (water[i] == null) {
					water[i] = new Water(graphic.getX(), graphic.getY(), 
							graphic.getDirection());
					break;
				}
			}
		}
		
	}
}
