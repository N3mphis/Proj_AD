package grid.solvers;
import game.CreditChecker;
import grid.CuttingResult;
import grid.Tilia;


public abstract class Solver {

	
	// use this for other purposes
	public abstract CuttingResult getReward(Tilia x);

		
	// use this for the gui
	public long getScore (Tilia x, int initialCredits){
		
		CreditChecker.addOrResetCalculatingThread(x.getIsRedOrBlueGridTile(), initialCredits);
		long ret = getReward(x).getReward();
		CreditChecker.iDoNotNeedAnyMoreCredits(x.getIsRedOrBlueGridTile());

		return ret;
		
	}

	
	
}
