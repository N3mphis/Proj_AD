package game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Tile {

	

	private BufferedImage image;
	private String identifier;

	public static final String GRASS_TILE = "GRASS";
	public static final String ROCK_TILE = "ROCK";
	public static final String COIN_TILE= "COIN";
	public static final String BLUE_CAMP_TILE = "BLUE_CAMP";
	public static final String BLUE_SPRITE_TILE = "BLUE_SPRITE";
	public static final String RED_CAMP_TILE = "RED_CAMP";
	public static final String RED_SPRITE_TILE = "RED_SPRITE";


	public static String[] tiletypes = {GRASS_TILE,ROCK_TILE,COIN_TILE,BLUE_CAMP_TILE,BLUE_SPRITE_TILE,RED_CAMP_TILE,RED_SPRITE_TILE};

	private static BufferedImage GRASS ;
	private static BufferedImage ROCK ;
	private static BufferedImage BLUE_CAMP; 
	private static BufferedImage BLUE_SPRITE; 
	private static BufferedImage RED_CAMP ;
	private static BufferedImage RED_SPRITE; 
	private static BufferedImage COIN;

	
	static {
		
		try {
			GRASS = ImageIO.read(new File("grass.png"));
			ROCK = ImageIO.read(new File("rock.png"));
			BLUE_CAMP = ImageIO.read(new File("camp_blue.png"));
			BLUE_SPRITE = ImageIO.read(new File("sprite_blue.png"));
			RED_CAMP = ImageIO.read(new File("camp_red.png"));
			RED_SPRITE = ImageIO.read(new File("sprite_red.png"));
			COIN = ImageIO.read(new File("coin.png"));
		}
		catch(Exception e){
			System.exit(-1);
		}
	}



	
	public Tile (String identifier){
		
		 this.identifier= identifier;
		 if     (identifier.equals("GRASS"))	  {image = GRASS;}
		 else if(identifier.equals("ROCK"))		  {image=  ROCK ;}
		 else if(identifier.equals("BLUE_CAMP"))  {image = BLUE_CAMP;}
		 else if(identifier.equals("BLUE_SPRITE")){image = BLUE_SPRITE;}
		 else if(identifier.equals("RED_CAMP"))   {image = RED_CAMP;}
		 else if(identifier.equals("RED_SPRITE")) {image = RED_SPRITE;}
		 else if(identifier.equals("COIN")) 	  {image = COIN;}

		
		
	}
	
	public String getIdentifier() {
		return identifier;
	}



	public BufferedImage draw (){
		return this.image;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
