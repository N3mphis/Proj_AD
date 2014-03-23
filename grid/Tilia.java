package grid;
import game.CreditChecker;
import java.util.ArrayList;

public class Tilia {

	private int side ; // Size of the tilia
	private int reward ; // Reward for having such a tilia
	private TiliaCollection collection; // This collection holds all the possible tilia. Use this to avoid making new tilia objects all the time.
	private String isRedOrBlueGridTile; // This is used internally to manage the game credits of the blue and red team, do not change or remove this variable
	

	// use this constructor for usage outside the game
	public Tilia(int side,int reward,TiliaCollection collection){
		this.side = side;
		this.reward = reward;
		this.collection = collection;
		this.isRedOrBlueGridTile = null;
	}
	
	// use this constructor for usage in the game
	public Tilia(int side,int reward,TiliaCollection collection,String isRedOrBlueGridTile){
		this.side = side;
		this.reward = reward;
		this.collection = collection;
		this.isRedOrBlueGridTile = isRedOrBlueGridTile;
	}
	
	/*
    Assignment 1
    Complete the cut and gridCalculator methods

    The cut method should return the uniquely defined list of Grids after cutting a square with side length tiliaSize from the original Grid.
    This method will be called repeatedly to find the optimal way of cutting the Grid.
    When the size of the cut is equal or larger than the original Tilia, nothing can be done, so only the Tilia itself will be returned.
    When the size of the cut is smaller than the original Tilia, you have to compute which Tilias should be added to the list
    You should use the tiliaCalculator-method to compute the Tilias resulting from a rectangular piece.
    Look at the presentation to see the uniquely defined decomposition for cutting a rectangular piece.

    */

    public ArrayList<Tilia> cut (int tiliaSize){
	

    	// do not remove this line
    	this.doComplexityLogic();

		ArrayList<Tilia> tilias = new ArrayList<>();

		if (tiliaSize >= side  ){
			System.err.println("Warning: attempting to cut a square into a bigger or equal square;");
			tilias.add(this);
			return tilias;
		}
		
		else {
			// TODO: Add the square that is cut out
			tilias.add(getCollection().getGrid(tiliaSize));
			

            // TODO: Process the leftover pieces using the gridCalculator function
			tiliaCalculator(side,side-tiliaSize,tilias);
			tiliaCalculator(tiliaSize,side-tiliaSize,tilias);

		}
		
		return tilias;
	}
	

	private void tiliaCalculator (int length,int width, ArrayList<Tilia> list){
        // TODO: Process a rectangle
		int i,max,min;
		if(length>=width){
			max = length;
			min = width;
		}else{
			max = width;
			min = length;
		}
		if(min==1){
			for(i=0;i<max;i++){
				list.add(getCollection().getGrid(1));
			}
			return;
		}
		if(min==0){
			return;
		}
		for(i=0;i<max/min;i++){
			list.add(getCollection().getGrid(min));
		}
		
		tiliaCalculator(max-i*min,min,list);
        
	}
	
	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public TiliaCollection getCollection() {
		return collection;
	}

	public void setCollection(TiliaCollection collection) {
		this.collection = collection;
	}

	public String toString (){
		
		return side+" ("+reward+")";
		
	}
	
	public String getIsRedOrBlueGridTile() {
		return isRedOrBlueGridTile;
	}

	public void setIsRedOrBlueGridTile(String isRedOrBlueGridTile) {
		this.isRedOrBlueGridTile = isRedOrBlueGridTile;
	}
	
	public void doComplexityLogic (){
		
    	
    	// **************************** Do not change the following code block, it used for synchronizing the game state ****************************
    	if (this.isRedOrBlueGridTile == null){
        	Counter.increaseCount();
    	}else{
    		while (!CreditChecker.canIGoRequest(this.isRedOrBlueGridTile)){
    			CreditChecker.idling(this.isRedOrBlueGridTile,true);
    			try {
    				Thread.sleep(20);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}	
    	}
		CreditChecker.idling(this.isRedOrBlueGridTile,false);

    	// **************************** End code block ****************************
	
	}

}