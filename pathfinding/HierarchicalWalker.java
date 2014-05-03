package pathfinding;
import game.CreditChecker;
import game.GameLogic;
import game.Tile;
import pathfinding.MinHeap;
import pathfinding.Values;
import game.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HierarchicalWalker extends Agent {

	private CreditChecker checker ;
	private int initialCredits ;
	public HierarchicalWalker(String id, boolean red, GameLogic logic, int initialCredits){
		this.id = id;
		this.red = red;
		this.logic= logic;
		this.initialCredits = initialCredits;
	}
	
	@Override
	public void calcPath(int x, int y) {

		// get the creditchecker and register
		CreditChecker.addOrResetCalculatingThread(id, initialCredits);
		
		// remove the actual movementlist
		if (!movementList.isEmpty()){
			System.err.println("Movement list not empty.");
			System.exit(-1);
		}

		
		// do the actual pathfinding
		movementList = getPath(location,logic.returnCoorFromXY(x,y));
		
		// finished task, so we can deregister from the creditchecker
		CreditChecker.iDoNotNeedAnyMoreCredits(id);
		
		
	}
	
	public LinkedList<Integer> getPath (int position, int goal){
		LinkedList<Integer> ret = new LinkedList<Integer>();
		LinkedList<Integer> tmp = new LinkedList<Integer>();
		int xPos = logic.calcXFromCoor(position);
		int yPos = logic.calcYFromCoor(position);
		int xG = logic.calcXFromCoor(goal);
		int yG = logic.calcYFromCoor(goal);
		int i = calcCluster(xPos, yPos);
		int j = calcCluster(xG, yG);
		if(i==j){
			return getAStarPath (position, goal);
		}
		Node start= logic.allNodes.get(i-1).get(0);
		int min = 8192;
		for(int k = 0; k < logic.allNodes.get(i-1).size();k++){
			if(get_distance(goal,logic.allNodes.get(i-1).get(k).tilenum)< min){
				min = get_distance(goal,logic.allNodes.get(i-1).get(k).tilenum);
				start = logic.allNodes.get(i-1).get(k);
			}
		}
		
		
		if(logic.allNodes.get(j-1).size()==0){
			System.out.println();
		}
		Node end= logic.allNodes.get(j-1).get(0);
		min = 8192;
		for(int k = 0; k < logic.allNodes.get(j-1).size();k++){
			if((get_distance(position,logic.allNodes.get(j-1).get(k).tilenum) + get_distance(goal,logic.allNodes.get(j-1).get(k).tilenum))< min){
				min = (get_distance(position,logic.allNodes.get(j-1).get(k).tilenum) + get_distance(goal,logic.allNodes.get(j-1).get(k).tilenum));
				end = logic.allNodes.get(j-1).get(k);
			}
		}
		
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		visitedNodes.add(start);
		Node next = start;
		Node tmp_node = next;
		Node current = next; //the node we are examining
		Node prev_node = start; // we want to keep track of the last 2 nodes we came from, so we don't end up in an endless loop
		Node prev2_node = start;
		Node swapper; // a temporary value to be able to swap 2 nodes
		
		
		int debugcounter = 0;
		while(next!=end){ // stop when arrived at the correct node
		min = 8192;
		for(int k = 0; k < logic.allNodes.get(next.cluster1 - 1).size();k++){
			current = logic.allNodes.get(next.cluster1 - 1).get(k);
			if((current.tilenum != prev2_node.tilenum )&& (current.tilenum != prev_node.tilenum )&& (current.tilenum != next.tilenum ) || (current==end)){
				if(current==end){
					min = 0;
					tmp_node = current;
				}
				else if(get_distance(next.tilenum, current.tilenum)+ get_distance(current.tilenum, end.tilenum)< min){
					min = get_distance(next.tilenum, current.tilenum)+ get_distance(current.tilenum, end.tilenum);
					tmp_node = current;
				}
				else if(current.cluster1==end.cluster1 || current.cluster1==end.cluster2 || current.cluster2==end.cluster1 || current.cluster2==end.cluster2){
					if(tmp_node.cluster1 != end.cluster1 && tmp_node.cluster2 != end.cluster1 && tmp_node.cluster1 != end.cluster2 && tmp_node.cluster2 != end.cluster2){
						min=get_distance(current.tilenum, end.tilenum);
						tmp_node = current;
					}
					else if(get_distance(current.tilenum, end.tilenum) < min){
						min=get_distance(current.tilenum, end.tilenum);
						tmp_node = current;
					}
				} 
			}
		}
		
		if(min!=0){
			for(int k = 0; k < logic.allNodes.get(next.cluster2 - 1).size();k++){
				current = logic.allNodes.get(next.cluster2 - 1).get(k);
				if((current.tilenum != prev2_node.tilenum )&& (current.tilenum != prev_node.tilenum )&& (current.tilenum != next.tilenum ) || (current==end)){
					if(current==end){
						min = 0;
						tmp_node = current;
						break;
					}
					else if(get_distance(next.tilenum, current.tilenum)+ get_distance(current.tilenum, end.tilenum)< min){
						min = get_distance(next.tilenum, current.tilenum)+ get_distance(current.tilenum, end.tilenum);
						tmp_node = current;
					}
					else if(current.cluster1==end.cluster1 || current.cluster1==end.cluster2 || current.cluster2==end.cluster1 || current.cluster2==end.cluster2){
						if(tmp_node.cluster1 != end.cluster1 && tmp_node.cluster2 != end.cluster1 && tmp_node.cluster1 != end.cluster2 && tmp_node.cluster2 != end.cluster2){
							min=get_distance(current.tilenum, end.tilenum);
							tmp_node = current;
						}
						else if(get_distance(current.tilenum, end.tilenum) < min){
							min=get_distance(current.tilenum, end.tilenum);
							tmp_node = current;
						}
					} 
				}
			}	
		}
		visitedNodes.add(tmp_node);
		
		prev2_node = prev_node;
		swapper = next;
		next = tmp_node;
		prev_node = swapper;
		debugcounter++;
		if(debugcounter > 100){
			System.out.println();
		}
		}
		
		tmp = getAStarPath (position , visitedNodes.get(0).tilenum);
		ret.addAll(tmp);
		for(int k = 0; k < visitedNodes.size()-1;k++){
			tmp = getAStarPath (visitedNodes.get(k).tilenum, visitedNodes.get(k+1).tilenum);
			ret.addAll(tmp);	
		}
		tmp = getAStarPath (visitedNodes.get(visitedNodes.size()-1).tilenum, goal);
		ret.addAll(tmp);
		return ret;
	}
	
	public int calcCluster (int x, int y){
		int cluster=0;
		if(x < 15){
			if(y < 10){
				cluster=1;
			}
			else if(y < 20){
				cluster=6;
			}
			else if(y < 30){
				cluster=11;
			}
			else if(y < 43){
				cluster=16;
			}
		}
		else if(x < 30){
			if(y < 10){
				cluster=2;
			}
			else if(y < 20){
				cluster=7;
			}
			else if(y < 30){
				cluster=12;
			}
			else if(y < 43){
				cluster=17;
			}
		}
		else if(x < 45){
			if(y < 10){
				cluster=3;
			}
			else if(y < 20){
				cluster=8;
			}
			else if(y < 30){
				cluster=13;
			}
			else if(y < 43){
				cluster=18;
			}
		}
		else if(x < 60){
			if(y < 10){
				cluster=4;
			}
			else if(y < 20){
				cluster=9;
			}
			else if(y < 30){
				cluster=14;
			}
			else if(y < 43){
				cluster=19;
			}
		}
		else if(x < 75){
			if(y <= 10){
				cluster=5;
			}
			else if(y < 20){
				cluster=10;
			}
			else if(y < 30){
				cluster=15;
			}
			else if(y < 43){
				cluster=20;
			}
		}
		if(cluster==0){
			System.out.println("ERROR\nERROR\nERROR");
		}
		return cluster;
	}
	
	private int get_distance(int current, int goal){
		int current_x = logic.calcXFromCoor(current);
		int current_y = logic.calcYFromCoor(current);
		int goal_x = logic.calcXFromCoor(goal);
		int goal_y = logic.calcYFromCoor(goal);
		return (current_x - goal_x)*(current_x - goal_x)+ (current_y - goal_y)*(current_y - goal_y);
	}
	
	private int get_h(int current, int goal){
		int current_x = logic.calcXFromCoor(current);
		int current_y = logic.calcYFromCoor(current);
		int goal_x = logic.calcXFromCoor(goal);
		int goal_y = logic.calcYFromCoor(goal);
		return 2*(Math.abs(current_x - goal_x)+ Math.abs(current_y - goal_y));  // Het nemen van een goede stap wordt nu sterker bevoordeeld door de factor 2
	}
	public LinkedList<Integer> getAStarPath(int start, int goal){
		
		// voor A* zullen we de 'g' functie als volgt definieren: het aantal stappen voordat we bij de knoop zijn aangekomen, elke stap heeft dus constante kost 1
		// de functie 'h' wordt de ingeschatte kost om tot bij de goal te geraken, we kiezen voor |huidige_x_coördinaat - goal_x_coördinaat| + |huidige_y_coördinaat - goal_y_coördinaat|
		int max_tile = logic.getSquaresX() * logic.getSquaresX();
		LinkedList<Integer> moves = new LinkedList<Integer>(); // de lijst om terug te geven
		MinHeap openList = new MinHeap(max_tile); // in deze lijst plaatsen we de tegels die we mogelijks willen bekijken
		//ArrayList<Values> closedList = new ArrayList<Values>(); // hier komen de tegels die reeds werden bekeken
		Values start_val= new Values(start, 0, get_h(start, goal));
		int mark[] = new int[max_tile]; // hier markeren we of tegels al eens bezocht werden of niet
		boolean in_closed [] = new boolean[max_tile];
		int f_array [] = new int[max_tile];
		
		openList.add(start_val);
		
		int xCurrent;
		int yCurrent;
		int x_add;
		int y_add;
		int f_val;
		int h_val;
		int newtile;
		Values end_bundle= start_val; // initialiseren
		Tile tile;
		String tileType;
		//HashMap<Integer, Integer> backtrack = new HashMap<Integer, Integer>(); // deze hashmap houdt voor een tegel waar we geweest zijn bij vanwaar we kwamen
		//backtrack.put(start, -1); // we houden bij dat we van de startknoop zijn vertrokken
		boolean keep_looking = true;
		
		while(keep_looking){
		Values tmp_min = openList.min();// minimum ophalen
		openList.extractMin(); // tegel verwijderen uit de openlist
		in_closed[tmp_min.tilenum]= true;
		f_array[tmp_min.tilenum]= tmp_min.f_val;
		int min_pos = tmp_min.tilenum;
		
		// buren van de knoop toevoegen aan de openList
		xCurrent = logic.calcXFromCoor(min_pos);
		yCurrent = logic.calcYFromCoor(min_pos);

		
		// buur 1
		x_add = xCurrent+1;
		y_add = yCurrent; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + tmp_min.g_val + 1;
		Values bundle = new Values(newtile, tmp_min.g_val + 1, f_val, tmp_min);

		if(mark[newtile]==1){ // de huidige tegel zijn we al eens tegengekomen		
			if(openList.find_index.containsKey(newtile)){
				if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 4)){
					openList.set(openList.find_index.get(newtile), bundle);
				}
			}
			
			if(in_closed[newtile]){
				if(f_array[newtile] > f_val + 3){ //f-waarden vergelijken
					openList.add(bundle);
					in_closed[newtile]= false;
				}
			}	
		}
		else{
			mark[newtile]=1; // de tegel markeren
			tile = this.getTile(newtile);
			tileType = tile.getIdentifier();
			if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
				// deze tegel voegen we niet toe
			}
			else if(tileType.equals(Tile.COIN_TILE)){
				// deze tegel voegen we ook niet toe, anders geraakt het algoritme in de war
			}
			else {
				openList.add(bundle);
			}
		}
		if(h_val==0){
			end_bundle = new Values(bundle);
			keep_looking = false;
		}
		}
		
		// buur 2
		x_add = xCurrent-1;
		y_add = yCurrent; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + tmp_min.g_val + 1; // oude g-waarde +1 + de h-waarde
		Values bundle = new Values(newtile, tmp_min.g_val + 1, f_val, tmp_min);

		if(mark[newtile]==1){ // de huidige tegel zijn we al eens tegengekomen		
			if(openList.find_index.containsKey(newtile)){
				if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 4)){
					openList.set(openList.find_index.get(newtile), bundle);
				}
			}
			
			if(in_closed[newtile]){
				if(f_array[newtile] > f_val + 3){ //f-waarden vergelijken
					openList.add(bundle);
					in_closed[newtile]= false;
				}
			}	
		}
		else{
			mark[newtile]=1; // de tegel markeren
			tile = this.getTile(newtile);
			tileType = tile.getIdentifier();
			if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
				// deze tegel voegen we niet toe
			}
			else if(tileType.equals(Tile.COIN_TILE)){
				// deze tegel voegen we ook niet toe
			}
			else {
				openList.add(bundle);
			}
		}
		if(h_val==0){
			end_bundle = new Values(bundle);
			keep_looking = false;
		}
		}
		
		// buur 3
		x_add = xCurrent;
		y_add = yCurrent+1; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + tmp_min.g_val + 1; // oude g-waarde +1 + de h-waarde   -> verandert naar 2!
		Values bundle = new Values(newtile, tmp_min.g_val + 1, f_val, tmp_min);

		if(mark[newtile]==1){ // de huidige tegel zijn we al eens tegengekomen		
			if(openList.find_index.containsKey(newtile)){
				if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 4)){
					openList.set(openList.find_index.get(newtile), bundle);
				}
			}
			
			if(in_closed[newtile]){
				if(f_array[newtile] > f_val + 3){ //f-waarden vergelijken
					openList.add(bundle);
					in_closed[newtile]= false;
				}
			}	
		}
		else{
			mark[newtile]=1; // de tegel markeren
			tile = this.getTile(newtile);
			tileType = tile.getIdentifier();
			if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
				// deze tegel voegen we niet toe
			}
			else if(tileType.equals(Tile.COIN_TILE)){
				// deze tegel voegen we ook niet toe
			}
			else {
				openList.add(bundle);
			}
		}
		if(h_val==0){
			end_bundle = new Values(bundle);
			keep_looking = false;
		}
		}
		
		// buur 4
		x_add = xCurrent;
		y_add = yCurrent-1; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + tmp_min.g_val + 1; // oude g-waarde +1 + de h-waarde   -> verandert naar 2!
		Values bundle = new Values(newtile, tmp_min.g_val + 1, f_val, tmp_min);

		if(mark[newtile]==1){ // de huidige tegel zijn we al eens tegengekomen		
			if(openList.find_index.containsKey(newtile)){
				if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 4)){
					openList.set(openList.find_index.get(newtile), bundle);
				}
			}
			
			if(in_closed[newtile]){
				if(f_array[newtile] > f_val + 3){ //f-waarden vergelijken
					openList.add(bundle);
					in_closed[newtile]= false;
				}
			}	
		}
		else{
			mark[newtile]=1; // de tegel markeren
			tile = this.getTile(newtile);
			tileType = tile.getIdentifier();
			if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
				// deze tegel voegen we niet toe
			}
			else if(tileType.equals(Tile.COIN_TILE)){
				// deze tegel voegen we ook niet toe
			}
			else {
				openList.add(bundle);
			}
		}
		if(h_val==0){
			end_bundle = new Values(bundle);
			keep_looking = false;
		}
		}
		
		} // einde van de while - lus
		
		// de moves-lijst juist zetten
		Values get_prev = end_bundle;
		LinkedList<Integer> tmp = new LinkedList<Integer>();
		while(get_prev.g_val != 0){	
			tmp.add(get_prev.tilenum);
			get_prev = get_prev.prev_tile;
		}
		// tmp omdraaien
		for(int i=0; i<tmp.size(); i++){
			moves.add(tmp.get(tmp.size()-i-1));
		}
		return moves;
    }
}