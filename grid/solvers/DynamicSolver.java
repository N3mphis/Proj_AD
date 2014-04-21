package grid.solvers;

import grid.CuttingResult;
import grid.Tilia;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Assignment 3
    Complete the dynamic solver

    The getReward method should return the optimal result for cutting Grid x.
    This result is described by the size of x, the size of the first square cut out, the total reward and the resulting pieces.
    This implementation should use Dynamic Programming:
        Save your temporary results to reuse them.
        Make sure you return the best possible result in the end.

    Use the Test class to see if your solution is working correctly.

    Do a speed test and compare your results with the basic recursion algorithm.
    Discuss shortly in your report and illustrate with a graph.
*/

public class DynamicSolver extends Solver{

	private static HashMap<Integer, CuttingResult> tabel = new HashMap<Integer, CuttingResult>();
	public  DynamicSolver (int size){
	}
		
		
	public CuttingResult getReward(Tilia x){

		int side = x.getSide();
		
		//Check if you have computed this before
        if(!tabel.containsKey(side)){
            ArrayList<Integer> pieces = new ArrayList<Integer>();
            pieces.add(x.getSide());
            // First two variables in CuttingResult are not used
            CuttingResult best = new CuttingResult(0, 0, x.getReward(), pieces);


    		// Try possible cuts
    		for (int i =1 ; i < x.getSide() ; i++){
    			ArrayList<Integer> pieces_tmp = new ArrayList<Integer>();
    			long sum =0;
    			//Try different cuts
    			for (Tilia tilia : x.cut(i)){
                    //Process smaller pieces
    				CuttingResult temp = getReward(tilia);
    				for(int j=0; j < temp.getPieces().size();j++){
    					pieces_tmp.add(temp.getPieces().get(j));
    				}
    					sum+=temp.getReward();
    				
                  
    			}
                //Select optimal result
                if (sum > best.getReward()){
                  best.setPieces(pieces_tmp);
                  best.setReward(sum);
    			}
    		}

		// Save optimal result
		tabel.put(side, best);
		return best;
	}
        else return tabel.get(side);
	}
		

		
}
