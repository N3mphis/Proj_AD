package grid;

import java.util.HashMap;
import java.util.Random;

public class TiliaCollection {
	
	private HashMap<Integer,Tilia> tilias ;
	private Random r = new Random();
	
	public TiliaCollection (int top){
		tilias = new HashMap<Integer,Tilia>();
		Tilia gr = new Tilia(0,0,this);
		tilias.put(0,gr);
        gr = new Tilia(1,1,this);
        tilias.put(1, gr);
        for (int i = 2 ; i <= top ; i ++){
            int result = r.nextInt(10);
            if (r.nextBoolean()){
                result = -result;
            }
            result +=2;
            int price = result+(i*i);
            if (price<0){ price = 0;}
            Tilia tilia = new Tilia(i,price,this);
            tilias.put(i, tilia);
        }
	}

    public TiliaCollection (int top, int seed){
        tilias = new HashMap<Integer,Tilia>();
        Tilia gr = new Tilia(0,0,this);
        tilias.put(0,gr);
        gr = new Tilia(1,1,this);
        tilias.put(1, gr);
        r.setSeed(seed);
        for (int i = 2 ; i <= top ; i ++){
            int result = r.nextInt(10);
            if (r.nextBoolean()){
                result = -result;
            }
            result +=2;
            int price = result+(i*i);
            if (price<0){ price = 0;}
            Tilia tilia = new Tilia(i,price,this);
            tilias.put(i, tilia);
        }
    }
	
	public TiliaCollection (int top,String redOrBlue){
		tilias = new HashMap<Integer,Tilia>();
		Tilia gr = new Tilia(0,0,this,redOrBlue);
		tilias.put(0,gr);
        gr = new Tilia(1,1,this);
        tilias.put(1, gr);
        for (int i = 2 ; i <= top ; i ++){
            int result = r.nextInt(10);
            if (r.nextBoolean()){
                result = -result;
            }
            result +=3;
            int price = result+(i*i);
            if (price<0){ price = 0;}
            Tilia tilia = new Tilia(i,price,this,redOrBlue);
            tilias.put(i, tilia);
        }
	}

    public TiliaCollection (int top, Integer[] rewards){
        tilias = new HashMap<Integer,Tilia>();
        Tilia gr = new Tilia(0,0,this);
        tilias.put(0,gr);
        for (int i = 0 ; i < top ; i ++){
            Tilia tilia = new Tilia(i+1,rewards[i],this);
            tilias.put(i+1, tilia);
        }
    }
    
    
    public TiliaCollection (int top, Integer[] rewards,String redOrBlue){
        tilias = new HashMap<Integer,Tilia>();
        Tilia gr = new Tilia(0,0,this,redOrBlue);
        tilias.put(0,gr);
        for (int i = 0 ; i < top ; i ++){
            Tilia tilia = new Tilia(i+1,rewards[i],this,redOrBlue);
            tilias.put(i+1, tilia);
        }
    }

	public Tilia getGrid(int side){
		return tilias.get(side);
	}
	


	public Integer[] getRewards (){
	
		Integer[] ret = new Integer[this.tilias.keySet().size()];
		for (int i =1 ; i < this.tilias.keySet().size(); i++){
			ret[i-1] = new Integer(this.tilias.get(i).getReward());
		}
		return ret;
	}
}
