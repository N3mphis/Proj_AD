package grid.solvers;

import grid.CuttingResult;
import grid.Tilia;

import java.util.ArrayList;

public class GreedySolver extends Solver{
	private static ArrayList<Float> quality;
	public  GreedySolver (){
	}
		
		
	public CuttingResult getReward(Tilia x){
		if(quality == null){
		quality = x.getCollection().get_tilias();
		}	
		int side = x.getSide();
        ArrayList<Integer> pieces = new ArrayList<Integer>();
      
		long sum=0;
		float maxratio= 0;
		int bestside = 0;
		//Determine side with best ratio
		for(int i = 0;i<=side&&i<quality.size();i++){
			if(quality.get(i)>maxratio){
				maxratio = quality.get(i);
				bestside=i;
			}
		}
		int amountbest = (side/bestside)*(side/bestside);
		for(int i = 0;i<amountbest;i++){
			pieces.add(bestside);			
		}
		 ArrayList<Tilia> cuts = new ArrayList<Tilia>();
		 if(bestside*(side/bestside)>=side){
			 			 
		 }else{
		 cuts=x.cut(bestside*(side/bestside));
		 cuts.remove(0); //eerste is groot blok
		for(Tilia tilia:cuts){
			sum+=tilia.getReward();
			pieces.add(tilia.getSide());	
		}
	}
		sum+=(long)(quality.get(bestside)*(bestside*bestside*amountbest));
		CuttingResult best = new CuttingResult(side, 0, sum, pieces);
		return best;

		
	}
	

		
}
