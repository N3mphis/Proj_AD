package grid.solvers;

import grid.CuttingResult;
import grid.Tilia;

import java.util.ArrayList;

public class SimpleSolver extends Solver {
	
	
	
	// a very simple solver that just cuts the Tilia into pieces of one
	// note that this method does not use the method public ArrayList<Tilia> cut (int gridSize) 
	// as such it can be used to start part two of the assignment before completing part one
	
	public CuttingResult getReward(Tilia x){
		
		int side = x.getSide();
		
        ArrayList<Integer> pieces = new ArrayList<Integer>();
        for (int i = 0 ; i < side*side; i++){
            pieces.add(1);
        }
        int reward = x.getCollection().getGrid(1).getReward();
        CuttingResult result = new CuttingResult(side, 0, reward*side*side, pieces);

        return result;
	}
}
