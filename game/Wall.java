package game;
import java.util.ArrayList;

public class Wall {
	public int clusternum;
	public ArrayList<Integer> blockedClusters;
	
	public Wall(int clusternum){
		this.clusternum = clusternum;
		blockedClusters = new ArrayList<Integer>();
	}
}
