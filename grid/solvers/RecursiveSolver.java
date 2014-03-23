package grid.solvers;

import grid.CuttingResult;
import grid.Tilia;

import java.util.ArrayList;

/*
    Assignment 2
    Complete the recursive solver

    The getReward method should return the optimal result for cutting Grid x.
    This result is described by the size of x, the size of the first square cut out, the total reward and the resulting pieces.
    This implementation should be recursive:
        Cut a square from the grid, compute the resulting pieces using the grid functions you implemented in assignment one,
        and call the function on the resulting squares.
        Make sure you return the best possible result in the end.

    Use the Test class to see if your solution is working correctly.

    Do a speed test and describe the results in your report.
*/


public class RecursiveSolver  extends Solver{

	public RecursiveSolver (){}

	public CuttingResult getReward(Tilia x){
		int side = x.getSide();

        ArrayList<Integer> pieces = new ArrayList<Integer>();
        pieces.add(side);
        CuttingResult best = new CuttingResult(side, 0, x.getReward(), pieces);

		long currentReward;

		// Try possible cuts
		for (int i =1 ; i < side ; i++){
			ArrayList<Integer> pieces_tmp = new ArrayList<Integer>();
			long sum =0;
			for (Tilia tilia : x.cut(i)){
                // TODO : Process smaller pieces
				CuttingResult temp = getReward(tilia);
				for(int j=0; j < temp.getPieces().size();j++)
					pieces_tmp.add(temp.getPieces().get(j));
				sum+=temp.getReward();
              
			}
            // TODO: Select optimal result
            if (sum > best.getReward()){
              best.setPieces(pieces_tmp);
              best.setReward(sum);
			}
		}
		return best;
	}
}
