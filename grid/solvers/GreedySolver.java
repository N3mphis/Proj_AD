package grid.solvers;

import grid.CuttingResult;
import grid.Tilia;

import java.util.ArrayList;

public class GreedySolver extends Solver{
	static ArrayList<Float> quality;
	public  GreedySolver (){
	}
		
		
	public CuttingResult getReward(Tilia x){
		if(GreedySolver.quality==null){GreedySolver.quality = x.getCollection().get_tilias();};
		int side = x.getSide();

        ArrayList<Integer> pieces = new ArrayList<Integer>();
        pieces.add(side);
      
		float fmax=(float) 0.0;
		long sum=0;
		int bestside = 0;

		for(int i = 0;i<side;i++){
			if(GreedySolver.quality.get(i)>fmax){
				fmax = GreedySolver.quality.get(i);
				bestside=i;
			}
		}
		ArrayList<Tilia> temp = new ArrayList<Tilia>();
		temp.add(x);
		boolean cont = true;
		while(cont){
			cont=false;
		for(Tilia cut : temp){
			if(cut.getSide()>bestside){
				cont=true;
			}
		}
		cuttingPossible(bestside,temp);
		}
		for(Tilia tilia:temp){
			sum+=tilia.getReward();
			pieces.add(tilia.getSide());	
		}
		CuttingResult best = new CuttingResult(side, 0, sum, pieces);
		return best;

		
	}
private void cuttingPossible(int bestside,ArrayList<Tilia> temp){
	int size = temp.size();
	for(int i=0;i<size;i++){
		if(temp.get(i).getSide()>bestside){
			for(Tilia cut : temp.get(i).cut(bestside)){
				temp.add(cut);
			}
			temp.remove(i);
		}
	}
}	
	

		
}
