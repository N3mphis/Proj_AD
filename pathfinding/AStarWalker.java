package pathfinding;
import game.CreditChecker;
import game.GameLogic;
import game.Tile;

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
		return Math.abs(current_x - goal_x)+ Math.abs(current_y - goal_y);
	}
	
	public LinkedList<Integer> getAStarPath(int start, int goal){
		// TODO: add A*
		// voor A* zullen we de 'g' functie als volgt definieren: het aantal stappen voordat we bij de knoop zijn aangekomen, elke stap heeft dus constante kost 1
		// de functie 'h' wordt de ingeschatte kost om tot bij de goal te geraken, we kiezen voor |huidige_x_coördinaat - goal_x_coördinaat| + |huidige_y_coördinaat - goal_y_coördinaat|

		LinkedList<Integer> moves = new LinkedList<Integer>(); // de lijst om terug te geven
		ArrayList<Integer> openList = new ArrayList<Integer>(); // in deze lijst plaatsen we de int-waarde van de tegel en vlak daarna het aantal stappen
																// dat we al gedaan hebben (de g-waarde) en daarna de f-waarde (g+h waardes)
		ArrayList<Integer> closedList = new ArrayList<Integer>();
		openList.add(start);
		openList.add(0);
		openList.add(get_h(start, goal));
		
		int xCurrent;
		int yCurrent;
		int x_add;
		int y_add;
		int f_val;
		int h_val;
		int newtile;
		Tile tile;
		String tileType;
		HashMap<Integer, Integer> backtrack = new HashMap<Integer, Integer>(); // deze hashmap houdt voor een tegel waar we geweest zijn bij vanwaar we kwamen
		backtrack.put(start, -1); // we houden bij dat we van de startknoop zijn vertrokken
		boolean keep_looking = true;
		
		while(keep_looking){
		
		// minimum vinden
		int tmp_min=openList.get(2);
		int min_pos=0;
		for(int i=5;i<openList.size();i=i+3){
			if(openList.get(i)<tmp_min){
				tmp_min = openList.get(i);
				min_pos = i-2;
			}
		}
		// buren van de knoop toevoegen aan de openList
		xCurrent = logic.calcXFromCoor(openList.get(min_pos));
		yCurrent = logic.calcYFromCoor(openList.get(min_pos));
		
		// buur 1
		x_add = xCurrent+1;
		y_add = yCurrent; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + openList.get(min_pos+1)+1; // oude g-waarde +1 + de h-waarde
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		if (tileType.equals(Tile.ROCK_TILE) ||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(backtrack.containsKey(newtile)){ // de huidige tegel is al eens toegevoegd geweest
				boolean check = false;
				for(int i=0;i<openList.size();i=i+3){
					if(openList.get(i)==newtile){
						check = true;
						if(openList.get(i+2) > f_val){ //f-waarden vergelijken
							openList.set(i+1, openList.get(min_pos+1)+1); // g-waarde aanpassen
							openList.set(i+2, f_val); // f-waarde aanpassen
							backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
						}
					}
				}
				if(!check){
					for(int i=0;i<closedList.size();i=i+3){
						if(closedList.get(i) == newtile){
							if(closedList.get(i+2) > f_val){ //f-waarden vergelijken
								closedList.remove(i); // tegel zelf verwijderen
								closedList.remove(i); // g-waarde verwijderen
								closedList.remove(i); // f-waarde verwijderen
								openList.add(newtile); // tegel opnieuw toevoegen
								openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
								openList.add(f_val); // f-waarde berekenen en toevoegen
								backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
							}
						}
					}
				}
			}
			
			else{
			openList.add(newtile);
			openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
			openList.add(f_val); // f-waarde berekenen en toevoegen
			backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
		
			}
		}
		if(h_val==0){ 
			keep_looking = false;
		}
		}
		
		// buur 2
		x_add = xCurrent-1;
		y_add = yCurrent; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + openList.get(min_pos+1)+1; // oude g-waarde +1 + de h-waarde
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		if (tileType.equals(Tile.ROCK_TILE) ||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(backtrack.containsKey(newtile)){ // de huidige tegel is al eens toegevoegd geweest
				boolean check = false;
				for(int i=0;i<openList.size();i=i+3){
					if(openList.get(i)==newtile){
						check = true;
						if(openList.get(i+2) > f_val){ //f-waarden vergelijken
							openList.set(i+1, openList.get(min_pos+1)+1); // g-waarde aanpassen
							openList.set(i+2, f_val); // f-waarde aanpassen
							backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
						}
					}
				}
				if(!check){
					for(int i=0;i<closedList.size();i=i+3){
						if(closedList.get(i) == newtile){
							if(closedList.get(i+2) > f_val){ //f-waarden vergelijken
								closedList.remove(i); // tegel zelf verwijderen
								closedList.remove(i); // g-waarde verwijderen
								closedList.remove(i); // f-waarde verwijderen
								openList.add(newtile); // tegel opnieuw toevoegen
								openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
								openList.add(f_val); // f-waarde berekenen en toevoegen
								backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
							}
						}
					}
				}
			}
			
			else{
			openList.add(newtile);
			openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
			openList.add(f_val); // f-waarde berekenen en toevoegen
			backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
		
			}
		}
		if(h_val==0){ 
			keep_looking = false;
		}
		}
		
		// buur 3
		x_add = xCurrent;
		y_add = yCurrent+1; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + openList.get(min_pos+1)+1; // oude g-waarde +1 + de h-waarde
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		if (tileType.equals(Tile.ROCK_TILE) ||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(backtrack.containsKey(newtile)){ // de huidige tegel is al eens toegevoegd geweest
				boolean check = false;
				for(int i=0;i<openList.size();i=i+3){
					if(openList.get(i)==newtile){
						check = true;
						if(openList.get(i+2) > f_val){ //f-waarden vergelijken
							openList.set(i+1, openList.get(min_pos+1)+1); // g-waarde aanpassen
							openList.set(i+2, f_val); // f-waarde aanpassen
							backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
						}
					}
				}
				if(!check){
					for(int i=0;i<closedList.size();i=i+3){
						if(closedList.get(i) == newtile){
							if(closedList.get(i+2) > f_val){ //f-waarden vergelijken
								closedList.remove(i); // tegel zelf verwijderen
								closedList.remove(i); // g-waarde verwijderen
								closedList.remove(i); // f-waarde verwijderen
								openList.add(newtile); // tegel opnieuw toevoegen
								openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
								openList.add(f_val); // f-waarde berekenen en toevoegen
								backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
							}
						}
					}
				}
			}
			
			else{
			openList.add(newtile);
			openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
			openList.add(f_val); // f-waarde berekenen en toevoegen
			backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
		
			}
		}
		if(h_val==0){ 
			keep_looking = false;
		}
		}
		
		// buur 4
		x_add = xCurrent;
		y_add = yCurrent-1; 
		newtile = logic.returnCoorFromXY(x_add, y_add);
		if(newtile!=-1){
		h_val = get_h(newtile, goal); // als dit 0 is, zijn we aangekomen
		f_val = h_val + openList.get(min_pos+1)+1; // oude g-waarde +1 + de h-waarde
		tile = this.getTile(newtile);
		tileType = tile.getIdentifier();
		if (tileType.equals(Tile.ROCK_TILE) ||(this.red && tileType.equals(Tile.BLUE_CAMP_TILE))||(!this.red && tileType.equals(Tile.RED_CAMP_TILE))){
			// deze tegel voegen we niet toe
		}
		else { // toevoegen van de tegel aan de openList als dit moet
			
			if(backtrack.containsKey(newtile)){ // de huidige tegel is al eens toegevoegd geweest
				boolean check = false;
				for(int i=0;i<openList.size();i=i+3){
					if(openList.get(i)==newtile){
						check = true;
						if(openList.get(i+2) > f_val){ //f-waarden vergelijken
							openList.set(i+1, openList.get(min_pos+1)+1); // g-waarde aanpassen
							openList.set(i+2, f_val); // f-waarde aanpassen
							backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
						}
					}
				}
				if(!check){
					for(int i=0;i<closedList.size();i=i+3){
						if(closedList.get(i) == newtile){
							if(closedList.get(i+2) > f_val){ //f-waarden vergelijken
								closedList.remove(i); // tegel zelf verwijderen
								closedList.remove(i); // g-waarde verwijderen
								closedList.remove(i); // f-waarde verwijderen
								openList.add(newtile); // tegel opnieuw toevoegen
								openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
								openList.add(f_val); // f-waarde berekenen en toevoegen
								backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
							}
						}
					}
				}
			}
			
			else{
			openList.add(newtile);
			openList.add(openList.get(min_pos+1)+1); // de g-waarde van de knoop vanwaar we komen + 1
			openList.add(f_val); // f-waarde berekenen en toevoegen
			backtrack.put(newtile, openList.get(min_pos)); // de tegel vanwaar we kwamen bijhouden
		
			}
		}
		if(h_val==0){ 
			keep_looking = false;
		}
		}
		
		// verwijderen van de knoop wiens buren we hebben bekeken
		closedList.add(openList.get(min_pos));
		closedList.add(openList.get(min_pos+1));
		closedList.add(openList.get(min_pos+2));
		openList.remove(min_pos); // tegel zelf verwijderen
		openList.remove(min_pos); // g-waarde verwijderen
		openList.remove(min_pos); // f-waarde verwijderen
		
		} // einde van de while - lus
		
		// de moves-lijst juist zetten
		int get_prev = goal;
		LinkedList<Integer> tmp = new LinkedList<Integer>();
		while(backtrack.get(get_prev)!= -1){	
			tmp.add(get_prev);
			get_prev = backtrack.get(get_prev);
		}
		// tmp omdraaien
		for(int i=0; i<tmp.size(); i++){
			moves.add(tmp.get(tmp.size()-i-1));
		}

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
