package pathfinding;
import game.CreditChecker;
import game.GameLogic;
import game.Tile;

import java.util.LinkedList;
import java.util.Random;



public abstract class Agent {

	protected LinkedList<Integer> movementList = new LinkedList<Integer>();
	
	protected int initialCredits;

	public int getInitialCredits() {
		return initialCredits;
	}


	public void setInitialCredits(int initialCredits) {
		this.initialCredits = initialCredits;
	}
	protected GameLogic logic ;
	
	protected static final Random random = new Random();
	protected String id;
	protected boolean red ;
	
	protected int campLocation;
	
	public void popMovement(){
		this.movementList.pop();
	}
	
	public void clearMovementList(){
		this.movementList.clear();
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public boolean isRed() {
		return red;
	}


	public int getLocation() {
		return location;
	}


	public void setLocation(int location) {
		this.location = location;
	}


	public void setRed(boolean red) {
		this.red = red;
	}

	protected int location ;

	






	public int getCampLocation() {
		return campLocation;
	}


	public void setCampLocation(int campLocation) {
		this.campLocation = campLocation;
	}
	protected int carrying =-1;
	
	
	public abstract void calcPath (int x, int y);


	
	public int wantsToMove (){
		if (movementList.isEmpty()){
			return -1;
		}
		else{
			return movementList.peek();
		}
	}


	
	public int getCarrying() {
		return carrying;
	}
	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}

	protected Tile getTile(int spot){
		

		while (!CreditChecker.canIGoRequest(id)){
			CreditChecker.idling(id,true);
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CreditChecker.idling(id,false);

		
		
		return 	logic.getTile(spot);
		
		
		
	}
	

	
	
}
