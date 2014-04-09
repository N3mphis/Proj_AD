package game;


public class Coin implements Comparable<Coin>{
	private int distance;
	private int location;
	
	public Coin(int distance, int location) {
			this.distance = distance;
			this.location = location;
	}
	
	public int getDistance(){
		return distance;
	}
	public int getlocation(){
		return location;
	}


	@Override
	public int compareTo(Coin o) {
		if(o==null) return 0;
			return this.getDistance()-o.getDistance();

	}
		
		
}


