package pathfinding;
import game.CreditChecker;
import game.GameLogic;
import game.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AStarWalker extends Agent {


	private CreditChecker checker ;
	private int initialCredits ;


	public AStarWalker(String id, boolean red, GameLogic logic, int initialCredits){
		this.id = id;
		this.red = red;
		this.logic= logic;
		this.initialCredits = initialCredits;
	}
	
	@Override
	public void calcPath(int x, int y) {

		// get the creditchecker and register
		CreditChecker.addOrResetCalculatingThread(id, initialCredits);
		
		// remove the actual movementlist
		if (!movementList.isEmpty()){
			System.err.println("Movement list not empty.");
			System.exit(-1);
		}

		
		// do the actual pathfinding
		movementList = getAStarPath(location,logic.returnCoorFromXY(x,y));
		
		// finished task, so we can deregister from the creditchecker
		CreditChecker.iDoNotNeedAnyMoreCredits(id);
		
		
	}

	
	public LinkedList<Integer> getAStarPath(int start, int goal){
      
		// TODO: add A*
		return null;
    }


    public boolean canAccess(int position, int goal){
        
        
        String tileType = (this.getTile(position)).getIdentifier();
        
        
        
        boolean notAccessible = tileType.equals(Tile.ROCK_TILE) ||
                (this.red && tileType.equals(Tile.BLUE_CAMP_TILE)) ||
                (!this.red && tileType.equals(Tile.RED_CAMP_TILE)) || (tileType.equals(Tile.BLUE_CAMP_TILE) && position != goal) ||
                (tileType.equals(Tile.RED_CAMP_TILE) && position != goal);
        return !notAccessible;
    }

}