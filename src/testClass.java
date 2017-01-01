import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class testClass {

	
	static void loadLevel(String level) 
	{ 
		 Random randGen = new Random();    //a random number generator
		 Hero hero;         //main character for game
		 Water [] water = new Water[8];    //waters ejected from hero
		 ArrayList <Pant> pants = new ArrayList<Pant>();  //pant list
		 ArrayList <Fireball> fireballs = new ArrayList<Fireball>();  //fireball list
		 ArrayList <Fire> fires = new ArrayList<Fire>(); //fire list
	    int controlType; //track which control type the player want to use

		
		Scanner scnr = new Scanner(level);
		scnr.next();
		controlType = scnr.nextInt();
		
		String gameCharacter;
		float [] position;
		while(scnr.hasNextLine()) { //if there is remaining line
			scnr.nextLine();//go to the next line
			//first remove the coma(,) so that one can parse out the numbers
			String newLine = removeComa(scnr.nextLine());
			gameCharacter = gameCharacterExtractor(newLine);
			position = floatExtractor(newLine);
			
			if(gameCharacter.equals("HERO")) { //if the next word is "HERO"
				hero = new Hero(position[0], position[1], controlType);
			}
			else if(gameCharacter.equals("FIRE")) {
				fires.add(new Fire(position[0], position[1], randGen));
			}
			else if (gameCharacter.equals("PANT")) {
				pants.add(new Pant(position[0], position[1], randGen));
			}
			else
				;
		}		
	}

	private static String removeComa(String str) {
		String stringComaRemoved = "";
		Scanner scnr = new Scanner(str);
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != ',') {
				stringComaRemoved += str.charAt(i);
			}
		}
		scnr.close();
		return stringComaRemoved;
	}

	//extract the first two float numbers from the string
	//return a float array that contains the first two float numbers
	private static float[] floatExtractor (String str) {
		float[] floatNum = new float[2];
		Scanner scnr = new Scanner(str);
		System.out.println(str);
		int i = 0;
		while(scnr.hasNext()) {
			if(scnr.hasNextFloat()) {
				floatNum[i] = scnr.nextFloat();
				i++;
				}
			else
				scnr.next();
			
		if(i == 2) 
			break;
		}
		scnr.close();
		return floatNum;
	}
	//extract the character to be created
	//return the character
	private static String gameCharacterExtractor (String str) {
		String gameCharacter;
		Scanner scnr = new Scanner(str);
		gameCharacter = scnr.next();
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
	
	
public static void main (String[] args) {
	String level1 = "ControlType: 3\nHERO @ 36.0, 552.0\nFIRE @ 115.0, 84.0";
	System.out.println(level1);
	//test removeComa
	System.out.println(removeComa(level1));
   //test gameCharacterExtractor
	System.out.println(gameCharacterExtractor("HERO @ 36.0 552.0"));
	//test floatExtractor
	float [] position1 = floatExtractor("HERO @ 36.0 552.0");
	System.out.println(position1[0]);
	System.out.println(position1[1]);
	
	//test loadLevels
	loadLevel("ControlType: 3\nHERO @ 36.0, 552.0");
			
	//System.out.println(floatExtractor(stringParser(level)).toString());
	//System.out.println(scnr.nextInt());
	//scnr.nextLine();
	//System.out.println(scnr.next());
	//System.out.println(scnr.nextFloat());
	//System.out.println("Level is " + scnr.nextInt());
	//scnr.nextLine();
	/*
	while(scnr.hasNextLine()) {
		
		if(scnr.next().equals("HERO")) {
			System.out.print("Hero found! Position is " + scnr.nextFloat() + " " + scnr.nextFloat());
		}
		else if(scnr.next().equals("FIRE")) {
			System.out.print("Fire found! Position is " + scnr.nextFloat() + " " + scnr.nextFloat());
		}
		else if (scnr.next().equals("PANT")) {
			System.out.print("Pant found! Position is " + scnr.nextFloat() + " " + scnr.nextFloat());
		}
		else
			;
		scnr.nextLine();
	};		
*/
	
}
}