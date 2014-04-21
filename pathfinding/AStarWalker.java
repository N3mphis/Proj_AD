package pathfinding;
import game.CreditChecker;
import game.GameLogic;
import game.Tile;
import pathfinding.MinHeap;
import pathfinding.Values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AStarWalker extends Agent {

	private CreditChecker checker ;
	private int initialCredits ;
	public AStarWalker(String id, boolean red, GameLogic logic, int initialCredits){
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
		movementList = getAStarPath(location,logic.returnCoorFromXY(x,y));
		
		// finished task, so we can deregister from the creditchecker
		CreditChecker.iDoNotNeedAnyMoreCredits(id);
		
		
	}

	// hulpfunctie om A* te implementeren
	private int get_h(int current, int goal){
		int current_x = logic.calcXFromCoor(current);
		int current_y = logic.calcYFromCoor(current);
		int goal_x = logic.calcXFromCoor(goal);
		int goal_y = logic.calcYFromCoor(goal);
		//return (current_x - goal_x)*(current_x - goal_x)+ (current_y - goal_y)*(current_y - goal_y);
		return 2*(Math.abs(current_x - goal_x)+ Math.abs(current_y - goal_y));  // Het nemen van een goede stap wordt nu sterker bevoordeeld door de factor 2
	}
	
	public LinkedList<Integer> getAStarPath(int start, int goal){
		
		// voor A* zullen we de 'g' functie als volgt definieren: het aantal stappen voordat we bij de knoop zijn aangekomen, elke stap heeft dus constante kost 1
		// de functie 'h' wordt de ingeschatte kost om tot bij de goal te geraken, we kiezen voor |huidige_x_coördinaat - goal_x_coördinaat| + |huidige_y_coördinaat - goal_y_coördinaat|
		
		int max_tile = logic.getSquaresX() * logic.getSquaresX(); // de maximum waarde voor een coordinaat van een tegel
		LinkedList<Integer> moves = new LinkedList<Integer>(); // de lijst om terug te geven
		MinHeap openList = new MinHeap(max_tile); // in deze lijst plaatsen we de tegels die we mogelijks willen bekijken
		//ArrayList<Values> closedList = new ArrayList<Values>(); // hier komen de tegels die reeds werden bekeken
		Values start_val= new Values(start, 0, get_h(start, goal));
		boolean mark[] = new boolean[max_tile]; // hier markeren we of tegels al eens bezocht werden of niet
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
		
		int counter=0; //debug waarde
		int reworkcounter =0; //debug waarde
		while(keep_looking){
		counter++;
		Values tmp_min = openList.extractMin();// minimum ophalen en tegel verwijderen uit de openlist
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
		f_val = h_val + tmp_min.g_val + 1; // oude g-waarde + 2 + de h-waarde
		Values bundle = new Values(newtile, tmp_min.g_val + 1, f_val, tmp_min);
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		
		if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(mark[newtile]){ // de huidige tegel is al eens toegevoegd geweest
				if(openList.find_index.containsKey(newtile)){
					if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 5)){
						reworkcounter++;
					openList.set(openList.find_index.get(newtile), bundle);
					}
				}
			
				if(in_closed[newtile]){
					if(f_array[newtile] > f_val+4){ //f-waarden vergelijken
						openList.add(bundle);
						in_closed[newtile]= false;
						reworkcounter++;
					}
				}
			}
			else{
			openList.add(bundle);
			mark[newtile]=true; // de tegel markeren
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
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		
		if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(mark[newtile]){ // de huidige tegel is al eens toegevoegd geweest
				if(openList.find_index.containsKey(newtile)){
					if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 5)){
						reworkcounter++;
					openList.set(openList.find_index.get(newtile), bundle);
					}
				}
//			
				if(in_closed[newtile]){
					if(f_array[newtile] > f_val+4){ //f-waarden vergelijken
						openList.add(bundle);
						in_closed[newtile]= false;
						reworkcounter++;
					}
				}
			}
			else{
			openList.add(bundle);
			mark[newtile]=true; // de tegel markeren
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
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		
		if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(mark[newtile]){ // de huidige tegel is al eens toegevoegd geweest
				if(openList.find_index.containsKey(newtile)){
					if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 5)){
						reworkcounter++;
					openList.set(openList.find_index.get(newtile), bundle); 
					}
				}
			
				if(in_closed[newtile]){
					if(f_array[newtile] > f_val+4){ //f-waarden vergelijken
						openList.add(bundle);
						in_closed[newtile]= false;
						reworkcounter++;
					}
				}
			}
			else{
			openList.add(bundle);
			mark[newtile]=true; // de tegel markeren
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
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		
		if (tileType.equals(Tile.ROCK_TILE)||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))||(tileType.equals(Tile.BLUE_CAMP_TILE) && newtile != goal)||(tileType.equals(Tile.RED_CAMP_TILE) && newtile != goal)){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(mark[newtile]){ // de huidige tegel is al eens toegevoegd geweest
				if(openList.find_index.containsKey(newtile)){
					if(openList.heap.get(openList.find_index.get(newtile)).g_val > (tmp_min.g_val + 5)){
						reworkcounter++;
					openList.set(openList.find_index.get(newtile), bundle);
					}
				}
			
				if(in_closed[newtile]){
					if(f_array[newtile] > f_val+4){ //f-waarden vergelijken
						openList.add(bundle);
						in_closed[newtile]= false;
						reworkcounter++;
					}
				}
			}
			else{
			openList.add(bundle);
			mark[newtile]=true; // de tegel markeren
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
		System.out.println(counter);
		System.out.println("ReworkCounter: "+ reworkcounter);
		return moves;
    }

    public boolean canAccess(int position, int goal){
        String tileType = (this.getTile(position)).getIdentifier();
    
       boolean notAccessible = tileType.equals(Tile.ROCK_TILE) ||
                (this.red && tileType.equals(Tile.BLUE_CAMP_TILE)) ||
                (!this.red && tileType.equals(Tile.RED_CAMP_TILE)) || (tileType.equals(Tile.BLUE_CAMP_TILE) && position != goal) ||
                (tileType.equals(Tile.RED_CAMP_TILE) && position != goal);
        return !notAccessible;
    }

}

