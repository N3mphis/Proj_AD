package pathfinding;
import game.CreditChecker;
import game.GameLogic;
import game.Tile;

import java.util.ArrayDeque;


public class RandomWalker extends Agent {

	
	
	
	public RandomWalker  (String id, boolean red, GameLogic logic, int initialCredits){
		this.id = id;
		this.red = red;
		this.logic= logic;
		this.initialCredits = initialCredits;
	}
	
	@Override
	public void calcPath(int x, int y) {


		// remove the actual movementlist
		if (!movementList.isEmpty()){
			System.out.println("Movement list not empty."+ this.getId());
			System.exit(-1);
		}

		
		// do the actual pathfinding
		
		// ignores any command to move to any position and just randomly moves one tile
		int move = random.nextInt(4);
		boolean completed= false;
	
		int xLocation = logic.calcXFromCoor(location);
		int yLocation = logic.calcYFromCoor(location);

		int xNewLocation ;
		int yNewLocation ;
		
		int newSpot=0;
		
		while(!completed){
			// move north
			if (move==0){
				xNewLocation = xLocation;
				yNewLocation = yLocation -1;
			// move south	
			}else if (move==1){
				xNewLocation = xLocation;
				yNewLocation = yLocation +1;
				// east
			}else if(move==2){
				xNewLocation = xLocation+1;
				yNewLocation = yLocation ;
				// west
			}else{
				xNewLocation = xLocation-1;
				yNewLocation = yLocation;
			}
			
			newSpot = logic.returnCoorFromXY(xNewLocation, yNewLocation);
			Tile tile = this.getTile(newSpot);
			String tileType = tile.getIdentifier();
			completed = true;
			if (tileType.equals(Tile.ROCK_TILE) ||
				(this.red && tileType.equals(Tile.BLUE_CAMP_TILE)) ||
				(!this.red && tileType.equals(Tile.RED_CAMP_TILE))
						){
				
				completed =false;
			}
			move = random.nextInt(5);
		}
		
		movementList.add(newSpot);
		
		// finished task, so we can deregister from the creditchecker
		CreditChecker.iDoNotNeedAnyMoreCredits(id);

		
	}

	
	

	
}
