package game;

public class Node {
	public int cluster1;
	public int cluster2;
	public int tilenum;

	public Node (int cluster1, int cluster2, int tilenum){
		this.cluster1 = cluster1;
		this.cluster2 = cluster2;
		this.tilenum = tilenum;
	}
	
	public boolean equals (Object o){
		if(o instanceof Node) return ((Node)o).tilenum == this.tilenum;
		else return false;
	}
}
