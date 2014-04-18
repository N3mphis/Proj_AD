package pathfinding;

public class Values implements Comparable<Values>{
	public int tilenum;
	public int g_val;
	public int f_val;
	public Values prev_tile;
	public Values(int tilenum, int g_val, int f_val, Values prev){
		this.tilenum = tilenum;
		this.g_val = g_val;
		this.f_val = f_val;
		prev_tile = prev;
	}
	public Values(Values copy){
		this(copy.tilenum,copy.g_val, copy.f_val, copy.prev_tile);
	}
	public Values(int tilenum, int g_val, int f_val){ // om de eerste tegel toe te voegen aan de lijst
		this.tilenum = tilenum;
		this.g_val = g_val;
		this.f_val = f_val;
		prev_tile = null;
	}
	public int compareTo(Values b){
		if(this.f_val < b.f_val) return -1;
		else if (this.f_val == b.f_val) return 0;
		else return 1;
	}
	
	public boolean equals(Object b){
		if(b==null)return false;
		if(!(b instanceof Values))return false;
		Values v = (Values) b;
		return this.tilenum==v.tilenum;
	}
}
