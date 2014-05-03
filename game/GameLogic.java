package game;

import grid.Tilia;
import grid.TiliaCollection;
import grid.solvers.DynamicSolver;
import grid.solvers.GreedySolver;
import grid.solvers.RecursiveSolver;
import grid.solvers.SimpleSolver;
import grid.solvers.Solver;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.management.RuntimeErrorException;

import pathfinding.AStarWalker;
import pathfinding.Agent;
import pathfinding.RandomWalker;
import pathfinding.HierarchicalWalker;
import game.Node;


public class GameLogic {

	// self reference, useful for the thread starting
	private GameLogic thisObject = this;

	// static finals
	private static final Tile OUT_OF_BOUNDS_TILE = new Tile(Tile.ROCK_TILE); // the map is always surrounded by rocks

	private final double DELAY = 125; // arbitrary time unit to be multiplied with the speed factor to determine game speed

	// game strategy variables
	public static enum AGENT_STRATEGY {
		RANDOM_WALKER, ASTARWALKER, SIMPLE, HIERARCHICALWALKER
	}; // strategy path enum

	public static enum PROCESSING_STRATEGY {
		DYNAMIC_SOLVER, RECURSIVE_SOLVER, GREEDY_SOLVER, SIMPLE_SOLVER, ADVANCED_SOLVER
	}; // strategy processing enum

	private PROCESSING_STRATEGY blueProcStrategy; // strat of blue team to
													// process
	private PROCESSING_STRATEGY redProcStrategy; // strat of red team to process
	private AGENT_STRATEGY blueTeamPathStrategy; // strat of blue team for
													// pathfinding
	private AGENT_STRATEGY redTeamPathStrategy; // strat of read team for
												// pathfinding

	// game specific variables
	private int pathCredits; // credits each iteration for each character to
								// calc path
	private int calcCredits; // credits each iteration for each team to process
	private HashMap<Integer, Integer> coinLocations; // locations of the coins
	// team specific variables
	private MinHeap<Coin> coins_red;
	private MinHeap<Coin> coins_blue;

	private ArrayList<Agent> blueTeam = new ArrayList<Agent>(); // characters in
																// blue team
	private ArrayList<Agent> redTeam = new ArrayList<Agent>(); // characters in
																// red team
	private ArrayList<Agent> allSprites = new ArrayList<Agent>(); // all
																	// characters
	private int stepCount; // which turn are we on
	private int totalSteps; // total amount of turns
	private int blueCamp; // location of blue camp
	private int redCamp; // location of red camp
	private int squaresX; // mapwidth
	private int squaresY; // mapheight
	private String map; // map filename
	// gui variables
	private volatile boolean updatingGUI = false; // are we still waiting for
													// the gui update to finish?
	private GUI gui; // gui object
	private HashMap<String, Tile> mapper = new HashMap<String, Tile>(); // maps
																		// strings
																		// from
																		// map
																		// to
																		// tile
																		// types
	private Tile[] gridIdentifier; // the map in tiles
	private double speed; // gui update speed

	// gui bar
	private volatile long scoreBlue; // score of blue team
	private volatile long scoreRed; // score of read team
	private volatile int processingBlue; // which tilia is blue team processing
	private volatile int processingRed; // which tilia is red team processing
	private LinkedList<Integer> queueRed = new LinkedList<Integer>(); // process
																		// queue
																		// of
																		// red
	private LinkedList<Integer> queueBlue = new LinkedList<Integer>(); // process queue of blue

	private volatile long blueToAddScore = -1; // finished tilia waiting to be added to score
	private volatile long redToAddScore = -1; // finished tilia waiting to be added to score
	// selection of tilia's for each team
	private TiliaCollection collectionRed; // red team tilias
	private TiliaCollection collectionBlue; // blue team tilias
	
	// arraylists used in preprocessing the map
	public ArrayList<Node> cluster1Nodes; 
	public ArrayList<Node> cluster2Nodes; 
	public ArrayList<Node> cluster3Nodes; 
	public ArrayList<Node> cluster4Nodes; 
	public ArrayList<Node> cluster5Nodes; 
	public ArrayList<Node> cluster6Nodes; 
	public ArrayList<Node> cluster7Nodes; 
	public ArrayList<Node> cluster8Nodes; 
	public ArrayList<Node> cluster9Nodes; 
	public ArrayList<Node> cluster10Nodes; 
	public ArrayList<Node> cluster11Nodes; 
	public ArrayList<Node> cluster12Nodes; 
	public ArrayList<Node> cluster13Nodes; 
	public ArrayList<Node> cluster14Nodes; 
	public ArrayList<Node> cluster15Nodes; 
	public ArrayList<Node> cluster16Nodes; 
	public ArrayList<Node> cluster17Nodes; 
	public ArrayList<Node> cluster18Nodes; 
	public ArrayList<Node> cluster19Nodes;
	public ArrayList<Node> cluster20Nodes;
	public ArrayList<ArrayList<Node>> allNodes;

	public GameLogic(int totalSteps, int pathCredits, int calcCredits, TiliaCollection collectionBlue, TiliaCollection collectionRed, GUI gui, String map, double speed, GameLogic.AGENT_STRATEGY blueTeamPathStrategy,
			GameLogic.AGENT_STRATEGY redTeamPathStrategy, GameLogic.PROCESSING_STRATEGY blueProcStrategy, GameLogic.PROCESSING_STRATEGY redProcStrategy) {

		this.totalSteps = totalSteps;
		this.pathCredits = pathCredits;
		this.calcCredits = calcCredits;
		this.collectionBlue = collectionBlue;
		this.collectionRed = collectionRed;
		this.gui = gui;
		this.map = map;
		this.speed = speed;
		this.blueTeamPathStrategy = blueTeamPathStrategy;
		this.redTeamPathStrategy = redTeamPathStrategy;
		this.blueProcStrategy = blueProcStrategy;
		this.redProcStrategy = redProcStrategy;

		this.coinLocations = new HashMap<Integer, Integer>();
		for (String x : Tile.tiletypes) {
			mapper.put(x, new Tile(x));
		}

		this.squaresX = gui.getButtonsX();
		this.squaresY = gui.getButtonsY();
		this.gridIdentifier = new Tile[squaresX * squaresY];

		// read map from file
		readFromFile(gridIdentifier, map);

		for (Agent a : redTeam) {
			a.setCampLocation(redCamp);
		}

		for (Agent a : blueTeam) {
			a.setCampLocation(blueCamp);
		}

		this.processingBlue = -1;
		this.processingRed = -1;

		initialize_coins();
		//map preprocessing
		cluster1Nodes = new ArrayList<Node>(); 
		cluster2Nodes = new ArrayList<Node>();  
		cluster3Nodes = new ArrayList<Node>();   
		cluster4Nodes = new ArrayList<Node>();   
		cluster5Nodes = new ArrayList<Node>();   
		cluster6Nodes = new ArrayList<Node>();   
		cluster7Nodes = new ArrayList<Node>();   
		cluster8Nodes = new ArrayList<Node>();   
		cluster9Nodes = new ArrayList<Node>();   
		cluster10Nodes = new ArrayList<Node>();   
		cluster11Nodes = new ArrayList<Node>();   
		cluster12Nodes = new ArrayList<Node>();   
		cluster13Nodes = new ArrayList<Node>();   
		cluster14Nodes = new ArrayList<Node>();   
		cluster15Nodes = new ArrayList<Node>();   
		cluster16Nodes = new ArrayList<Node>();   
		cluster17Nodes = new ArrayList<Node>();   
		cluster18Nodes = new ArrayList<Node>();   
		cluster19Nodes = new ArrayList<Node>(); 
		cluster20Nodes = new ArrayList<Node>(); 
		allNodes = new ArrayList<ArrayList<Node>>();

	}

	public void start() {
		//uncomment to preprocess
		//preprocessMap();  //do the preprocessing for map 4
		// start game steps
		long lastStep = System.currentTimeMillis();

		while (stepCount < totalSteps) {

			// increase the step count
			stepCount++;

			// gamelogic steps
			this.executeGameLogic();

			while (
			// there are multiple conditions where we should wait to end the
			// 'turn' or 'iteration'
			// the first is the condition that not enough time has passed. This
			// is vital because otherwhise the visualization
			// would run too fast.
			System.currentTimeMillis() - lastStep < (DELAY * speed) ||
			// the second condition is that each of the processing (pathfinding
			// and processing) should have spent their amount of credits or have
			// finished computing
					!CreditChecker.areAllThreadsFinished()) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			lastStep = System.currentTimeMillis();

			this.setUpdatingGUI(true);
			this.updateGUI();

			// wait until the gui has finished updating
			while (this.updatingGUI) {
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private void readFromFile(Tile[] tbs, String file) {

		int height = squaresY;
		int width = squaresX;
		int countSprites = 0;

		BufferedReader reader = null;
		int i = 0;
		int lines = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			lines++;

			while (lines <= height) {
				String[] tokens = line.split("\t");
				if (tokens.length != width) {
					throw new IOException();
				}

				for (String x : tokens) {

					Tile tile = mapper.get(x);

					// if the tile did not map, it should be an int value (and
					// as such a coin)
					if (tile == null) {
						int value = Integer.parseInt(x);
						this.coinLocations.put(i, value);
						tile = mapper.get(Tile.COIN_TILE);
					}

					else if (tile.getIdentifier().equals(Tile.BLUE_CAMP_TILE)) {
						this.blueCamp = i;
					} else if (tile.getIdentifier().equals(Tile.RED_CAMP_TILE)) {
						this.redCamp = i;
					} else if (tile.getIdentifier().equals(Tile.BLUE_SPRITE_TILE)) {
						countSprites++;
						Agent a = null;
						if (this.blueTeamPathStrategy == AGENT_STRATEGY.RANDOM_WALKER) {
							a = new RandomWalker("" + countSprites, false, this, pathCredits);
						} else if (this.blueTeamPathStrategy == AGENT_STRATEGY.ASTARWALKER) {
							a = new AStarWalker("" + countSprites, false, this, pathCredits);
						}
						// ADD OTHER STRATEGIES FOR BLUE HERE
						else if (this.blueTeamPathStrategy == AGENT_STRATEGY.HIERARCHICALWALKER) {
							a = new HierarchicalWalker("" + countSprites, false, this, pathCredits);
						}

						// *****
						else {
							System.err.println("Illegal walker type");
							System.exit(-1);
						}
						a.setLocation(i);
						this.blueTeam.add(a);
						this.allSprites.add(a);
					} else if (tile.getIdentifier().equals(Tile.RED_SPRITE_TILE)) {
						countSprites++;
						Agent a = null;
						if (this.redTeamPathStrategy == AGENT_STRATEGY.RANDOM_WALKER) {
							a = new RandomWalker("" + countSprites, true, this, pathCredits);
						} else if (this.redTeamPathStrategy == AGENT_STRATEGY.ASTARWALKER) {
							a = new AStarWalker("" + countSprites, true, this, pathCredits);
						}
						// add other strategies for red team here

						// *****
						else {
							System.err.println("Illegal walker type");
							System.exit(-1);
						}
						this.redTeam.add(a);
						a.setLocation(i);
						this.allSprites.add(a);
					}
					tbs[i] = tile;
					i++;

				}
				line = reader.readLine();
				lines++;

			}

		} catch (IOException e) {
			System.err.println("Error reading  map file");
			e.printStackTrace();
			System.exit(-1);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void simpleAStarStrategy(final Agent a) {

		if (a.getCarrying() > -1) {
			a.calcPath(calcXFromCoor(a.getCampLocation()), calcYFromCoor(a.getCampLocation())); // if carrying a Tilia, go to camp
		} else { // go pick up a random tilia
			ArrayList<Integer> coins = new ArrayList<Integer>(coinLocations.keySet());
			int coin;

			if (coins.size() > 1) {
				coin = coins.get(new Random().nextInt(coinLocations.size() - 1));
			} else if (coins.size() == 1) {
				coin = coins.get(0);
			} else {
				coin = a.getCampLocation();
			}
			a.calcPath(calcXFromCoor(coin), calcYFromCoor(coin));
		}

	}

	private void simpleRandomStrategy(final Agent a) {

		a.calcPath(0, 0);

	}

	private void redStrategy(final Agent a) {

		// get the creditchecker and register
		CreditChecker.addOrResetCalculatingThread(a.getId(), a.getInitialCredits());

		// EVERYTHING SHOULD HAPPEN IN THIS NEW THREAD BLOCK
		new Thread(new Runnable() {
			public void run() {

				if (redTeamPathStrategy.equals(AGENT_STRATEGY.ASTARWALKER)) {
					// simpleAStarStrategy(a);
					advancedStrategy(a);

				} else if (redTeamPathStrategy.equals((AGENT_STRATEGY.RANDOM_WALKER))) {
					simpleRandomStrategy(a);

				}
				// ADD OTHER STRATEGIES HERE

				// *****
				else {
					System.err.println("Illegal red path strategy.");
					System.exit(-1);
				}

			}
		}).start();

	}

	private int getDistance(int coin, boolean red) {
		int x = 0;
		int y = 0;
		if (red) {
			x = (calcXFromCoor(coin) - calcXFromCoor(redCamp));
			y = (calcYFromCoor(coin) - calcYFromCoor(redCamp));
		} else {
			x = (calcXFromCoor(coin) - calcXFromCoor(blueCamp));
			y = (calcYFromCoor(coin) - calcYFromCoor(blueCamp));
		}
		return (int) Math.sqrt((x) * (x) + (y) * (y));

	}

	private void initialize_coins() {
		ArrayList<Integer> coins = new ArrayList<Integer>(coinLocations.keySet());
		coins_red = new MinHeap<Coin>();
		coins_blue = new MinHeap<Coin>();
		for (Integer coin : coins) {
			Coin red = new Coin(getDistance(coin, true), coin);
			Coin blue = new Coin(getDistance(coin, false), coin);
			coins_red.add(red);
			coins_blue.add(blue);

		}

	}

	private void advancedStrategy(Agent a) {// afstand wordt bevoordeeld tov padlengte omwille van complexiteit

		if (a.getCarrying() > -1) {
			a.calcPath(calcXFromCoor(a.getCampLocation()), calcYFromCoor(a.getCampLocation())); // if carrying a Tilia, go to camp
		} 
		else { // go pick up a tilia closest to position
			int coin = 0;
			if (a.isRed()) {
				if (coins_red.size() >= 1) {
					coin = coins_red.extractMin().getlocation();
				}

			} else {
				if (coins_blue.size() >= 1) {
					coin = coins_blue.extractMin().getlocation();
				}

			}
			if (coin == 0) {
				coin = a.getCampLocation();
			}
			a.calcPath(calcXFromCoor(coin), calcYFromCoor(coin));
		}

	}

	private void preprocessMap() {
		// werkt enkel voor de 'entrance'-map
		// -> 20 clusters met vaste hoogte en breedte, behalve onderste rij,
		// clusters zijn genummerd van links naar rechts en boven naar onder (=>
		// eerst op 1 lijn)
		// de openingen worden wel bepaald door het programma
		int clusterHeight = 10;
		int clusterWidth = 15;
		int coordinate;
		Tile current;
		Node tmp;
		String tileType;
		ArrayList<Integer> processedOpenings = new ArrayList<Integer>();
		ArrayList<Integer> openings = new ArrayList<Integer>();
		
		allNodes.add(cluster1Nodes);
		allNodes.add(cluster2Nodes);
		allNodes.add(cluster3Nodes);
		allNodes.add(cluster4Nodes);
		allNodes.add(cluster5Nodes);
		allNodes.add(cluster6Nodes);
		allNodes.add(cluster7Nodes);
		allNodes.add(cluster8Nodes);
		allNodes.add(cluster9Nodes);
		allNodes.add(cluster10Nodes);
		allNodes.add(cluster11Nodes);
		allNodes.add(cluster12Nodes);
		allNodes.add(cluster13Nodes);
		allNodes.add(cluster14Nodes);
		allNodes.add(cluster15Nodes);
		allNodes.add(cluster16Nodes);
		allNodes.add(cluster17Nodes);
		allNodes.add(cluster18Nodes);
		allNodes.add(cluster19Nodes);
		allNodes.add(cluster20Nodes);
		
		//hulpvariabelen
			int hor_begin=0, hor_end=0, ver_begin=0, ver_end=0, ver_x=0, hor_y=0;
			
		for(int j=1; j < 16; j++){
			switch(j){
			case 1: 
				hor_begin = 0; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 14; hor_y = 9;
				break;
			case 2: 
				hor_begin = 15; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 29; hor_y = 9;
				break;
			case 3: 
				hor_begin = 30; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 44; hor_y = 9;
				break;
			case 4: 
				hor_begin = 45; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 59; hor_y = 9;
				break;
			case 5: 
				hor_begin = 60; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth; ver_end = ver_begin;
				ver_x = 0; hor_y = 9;
				break;
			case 6: 
				hor_begin = 0; ver_begin = 10; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 14; hor_y = 19;
				break;
			case 7: 
				hor_begin = 15; ver_begin = 10; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 29; hor_y = 19;
				break;
			case 8: 
				hor_begin = 30; ver_begin = 10; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 44; hor_y = 19;
				break;
			case 9: 
				hor_begin = 45; ver_begin = 10; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 59; hor_y = 19;
				break;
			case 10: 
				hor_begin = 60; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth; ver_end = ver_begin;
				ver_x = 0; hor_y = 19;
				break;
			case 11: 
				hor_begin = 0; ver_begin = 20; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 14; hor_y = 29;
				break;
			case 12: 
				hor_begin = 15; ver_begin = 20; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 29; hor_y = 29;
				break;
			case 13: 
				hor_begin = 30; ver_begin = 20; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 44; hor_y = 29;
				break;
			case 14: 
				hor_begin = 45; ver_begin = 20; 
				hor_end = hor_begin + clusterWidth -1; ver_end = ver_begin + clusterHeight;
				ver_x = 59; hor_y = 29;
				break;
			case 15: 
				hor_begin = 60; ver_begin = 0; 
				hor_end = hor_begin + clusterWidth; ver_end = ver_begin;
				ver_x = 0; hor_y = 29;
				break;
			}
		
		for (int i = ver_begin; i < ver_end; i++) {
			coordinate = returnCoorFromXY(ver_x, i);
			current = getTile(coordinate);
			tileType = current.getIdentifier();
			if (tileType.equals(Tile.GRASS_TILE)) {
				openings.add(coordinate);
			}
		}
		if (openings.size() > 0)
			processedOpenings = processOpenings(openings);

		for (int i = 0; i < processedOpenings.size(); i++) {
			tmp = new Node(j, j+1, processedOpenings.get(i));
			allNodes.get(j-1).add(tmp);
			allNodes.get(j).add(tmp);
		}

		openings.clear();
		processedOpenings.clear();
		for (int i = hor_begin; i < hor_end; i++) {
			coordinate = returnCoorFromXY(i, hor_y);
			current = getTile(coordinate);
			tileType = current.getIdentifier();
			if (tileType.equals(Tile.GRASS_TILE)) {
				openings.add(coordinate);
			}
		}
		if (openings.size() > 0)
			processedOpenings = processOpenings(openings);


		for (int i = 0; i < processedOpenings.size(); i++) {
			tmp = new Node(j, j+5, processedOpenings.get(i));
			allNodes.get(j-1).add(tmp);
			allNodes.get(j+4).add(tmp);
		}

		openings.clear();
		processedOpenings.clear();

		}
		// the last 4 clusters are handled seperatly for convenience
		for(int j=0; j<4; j++){
			for (int i = 30; i < 42; i++) {
				coordinate = returnCoorFromXY(14+15*j, i);
				current = getTile(coordinate);
				tileType = current.getIdentifier();
				if (tileType.equals(Tile.GRASS_TILE)) {
					openings.add(coordinate);
				}
			}
			if (openings.size() > 0)
				processedOpenings = processOpenings(openings);
			
			for (int i = 0; i < processedOpenings.size(); i++) {
				tmp = new Node(16+j, 17+j, processedOpenings.get(i));
				allNodes.get(15+j).add(tmp);
				allNodes.get(16+j).add(tmp);
			}

			openings.clear();
			processedOpenings.clear();
		}	
	}

	// deze methode zorgt ervoor dat 1 grote opening als 1 opening wordt aangezien, in plaats van bvb 3 verschillende naast elkaar
	private ArrayList<Integer> processOpenings(ArrayList<Integer> openings) {
		int prev = openings.get(0);
		int current;
		boolean check = false;
		int counter=0; //tellen hoeveel openingen na elkaar komen
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 1; i < openings.size(); i++) {
			current = openings.get(i);
			if ((current == prev + 1) || (current == prev + this.squaresX)) {
				counter++;
				check = true;
			} else
				check = false;
			if (!check) {
				ret.add(openings.get(i - counter/2 - 1));
				counter=0;
			}
			prev = current;
		}
		ret.add(openings.get(openings.size() - 1 - counter/2));
		return ret;
	}

	private void hierarchicalStrategy(Agent a) {
		
		if (a.getCarrying() > -1) {
			a.calcPath(calcXFromCoor(a.getCampLocation()), calcYFromCoor(a.getCampLocation())); // if carrying a Tilia, go to camp
		} 
		else { // go pick up a tilia closest to position
			int coin = 0;
			if (a.isRed()) {
				if (coins_red.size() >= 1) {
					coin = coins_red.extractMin().getlocation();
				}

			} else {
				if (coins_blue.size() >= 1) {
					coin = coins_blue.extractMin().getlocation();
				}

			}
			if (coin == 0) {
				coin = a.getCampLocation();
			}
			a.calcPath(calcXFromCoor(coin), calcYFromCoor(coin));
		}

	}

	private void blueStrategy(final Agent a) {

		// get the creditchecker and register
		CreditChecker.addOrResetCalculatingThread(a.getId(), a.getInitialCredits());

		// EVERYTHING SHOULD HAPPEN IN THIS NEW THREAD BLOCK
		new Thread(new Runnable() {
			public void run() {

				if (blueTeamPathStrategy.equals(AGENT_STRATEGY.ASTARWALKER)) {

					// replace simpleAStarStrategy with advancedStrategy
					//
					// simpleAStarStrategy(a);
					advancedStrategy(a);

				} else if (blueTeamPathStrategy.equals((AGENT_STRATEGY.RANDOM_WALKER))) {

					simpleRandomStrategy(a);

				}
				// ADD OTHER STRATEGIES HERE
				else if (blueTeamPathStrategy.equals((AGENT_STRATEGY.HIERARCHICALWALKER))) {

					hierarchicalStrategy(a);

				}

				// *****
				else {
					System.err.println("Illegal blue path strategy.");
					System.exit(-1);
				}

			}
		}).start();

	}

	private void blueProcStrategy(Tilia x) {

		final Solver solver;

		if (this.blueProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.DYNAMIC_SOLVER)) {
			solver = new DynamicSolver(x.getSide());
		} else if (this.blueProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.RECURSIVE_SOLVER)) {
			solver = new RecursiveSolver();
		} else if (this.blueProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.SIMPLE_SOLVER)) {
			solver = new SimpleSolver();
		} else if (this.blueProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.GREEDY_SOLVER)) {
			// add here
			solver = new GreedySolver();
		} else if (this.blueProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.ADVANCED_SOLVER)) {
			// add here
			solver = null;
		}
		// ADD other options here
		else {
			solver = null;
			System.err.println("Invalid processing strategy blue");
			System.exit(-1);
		}

		final Tilia y = x;

		// everything should happen in this new thread block
		new Thread(new Runnable() {
			public void run() {
				long toAddScore = solver.getScore(y, calcCredits);
				thisObject.setBlueToAddScore(toAddScore);
			}
		}).start();

	}

	private void redProcStrategy(Tilia x) {

		final Solver solver;

		if (this.redProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.DYNAMIC_SOLVER)) {
			solver = new DynamicSolver(x.getSide());
		} else if (this.redProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.RECURSIVE_SOLVER)) {
			solver = new RecursiveSolver();
		} else if (this.redProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.SIMPLE_SOLVER)) {
			solver = new SimpleSolver();
		} else if (this.redProcStrategy.equals(GameLogic.PROCESSING_STRATEGY.GREEDY_SOLVER)) {
			solver = new GreedySolver();
		}
		// ADD other options here
		else {
			solver = null;
			System.err.println("Invalid processing strategy red");
			System.exit(-1);
		}

		final Tilia y = x;

		// everything should happen in this new thread block
		new Thread(new Runnable() {
			public void run() {
				long toAddScore = solver.getScore(y, calcCredits);
				thisObject.setRedToAddScore(toAddScore);
			}
		}).start();
	}

	private void executeGameLogic() {

		// do processing logic

		// check if there is something in the queue and we are not processing
		// anything
		// note that at this stage, no threads can be running as we specifically
		// wait until a thread signals it is either
		// finished or at is stuck because it has no credits left
		// as this is the case we should not worry about other threads updating
		// these variables!

		// first check if something is finished processing for blue
		if (this.blueToAddScore > -1) {
			// add the score
			this.scoreBlue += this.blueToAddScore;
			// indicate that nothing is processing
			this.blueToAddScore = -1;
			this.processingBlue = -1;
		}

		// next check if something is finished processing for red
		if (this.redToAddScore > -1) {
			// add the score
			this.scoreRed += this.redToAddScore;
			// indicate that nothing is processing
			this.redToAddScore = -1;
			this.processingRed = -1;
		}

		// next check if blue can start processing something or it is in need of
		// new credits
		if (this.processingBlue == -1) {
			// check if there is something in the queue
			if (!this.queueBlue.isEmpty()) {
				// items are always processed in the order they were submitted
				this.processingBlue = this.queueBlue.poll();
				blueProcStrategy(collectionBlue.getGrid(this.processingBlue));
			}
		} else {
			CreditChecker.giveNewCredit(CreditChecker.BLUE_PROCESSING_THREAD);
		}

		// next check if red can start processing something or it is in need of
		// new credits
		if (this.processingRed == -1) {
			// check if there is something in the queue
			if (!this.queueRed.isEmpty()) {
				// items are always processed in the order they were submitted
				this.processingRed = this.queueRed.poll();
				redProcStrategy(collectionRed.getGrid(this.processingRed));
			}
		} else {
			CreditChecker.giveNewCredit(CreditChecker.RED_PROCESSING_THREAD);
		}

		// first decide the order of the agents to perform actions
		Collections.shuffle(this.allSprites);

		boolean[] actionPerformed = new boolean[this.allSprites.size()];
		for (int i = 0; i < actionPerformed.length; i++) {
			actionPerformed[i] = false;
		}

		// first check all the movements
		for (int i = 0; i < this.allSprites.size(); i++) {
			Agent a = this.allSprites.get(i);
			// check if agent wants to move
			int wantsToMove = a.wantsToMove();
			// yes it does want to move , then spent this turn moving
			if (wantsToMove != -1) {
				executeMovementAgent(a, wantsToMove);
				actionPerformed[i] = true;
			}
		}

		// now reset all the sprites as there is a bug with overlap
		for (int i = 0; i < this.allSprites.size(); i++) {
			Agent a = this.allSprites.get(i);
			if (a.isRed()) {
				gridIdentifier[a.getLocation()] = mapper.get(Tile.RED_SPRITE_TILE);
			} else {
				gridIdentifier[a.getLocation()] = mapper.get(Tile.BLUE_SPRITE_TILE);
			}
		}

		// now, all the agents which did not move and are not performing an
		// action can get a new command
		for (int i = 0; i < this.allSprites.size(); i++) {
			if (actionPerformed[i]) {
				continue;
			}
			Agent a = this.allSprites.get(i);
			Boolean bool = CreditChecker.isIdling(a.getId());
			if (bool) {
				CreditChecker.giveNewCredit(a.getId());
			} else {
				if (a.isRed()) {
					redStrategy(a);
				} else {
					blueStrategy(a);
				}
			}

		}

	}

	private void coinDrop(boolean isRed, int side) {

		if (isRed) {

			this.queueRed.add(side);

		} else {

			this.queueBlue.add(side);

		}

	}

	public int calcXFromCoor(int coor) {

		if (coor < 0 || coor >= gridIdentifier.length) {
			return -1;
		}
		return coor % this.squaresX;
	}

	public int calcYFromCoor(int coor) {
		if (coor < 0 || coor >= gridIdentifier.length) {
			return -1;
		}
		return coor / this.squaresX;
	}

	public int returnCoorFromXY(int x, int y) {

		if (x < 0 || x >= this.squaresX) {
			return -1;
		}
		if (y < 0 || y >= this.squaresY) {
			return -1;
		}
		return (x + (y * this.squaresX));
	}

	public Tile getTile(int spot) {

		if (spot < 0 || spot >= gridIdentifier.length) {
			return GameLogic.OUT_OF_BOUNDS_TILE;
		}
		// TODO Auto-generated method stub
		return this.gridIdentifier[spot];
	}

	private boolean executeMovementAgent(Agent a, int wantsToMove) {

		int currentLocation = a.getLocation();
		int xCurrent = this.calcXFromCoor(currentLocation);
		int yCurrent = this.calcYFromCoor(currentLocation);
		int xWants = this.calcXFromCoor(wantsToMove);
		int yWants = this.calcYFromCoor(wantsToMove);

		boolean isRed = a.isRed();

		// check if this is a valid movement request, otherwhise crash the game
		// check if wantsToMove is in bounds

		if (wantsToMove < 0 || wantsToMove >= this.gridIdentifier.length) {
			System.err.println("REQUESTED MOVE OUT OF BOUNDS. EXITING.");
			System.exit(-1);

		}

		// we can either move up, down, left or right, just check the four cases
		// crash the game if the move is not one of these choices
		if (!((xCurrent == xWants - 1 && yCurrent == yWants) || (xCurrent == xWants + 1 && yCurrent == yWants) || (yCurrent == yWants + 1 && xCurrent == xWants) || (yCurrent == yWants - 1 && xCurrent == xWants))) {

			System.err.println(xCurrent);
			System.err.println(yCurrent);
			System.err.println(xWants);
			System.err.println(yWants);
			System.err.println("REQUESTED MOVE NOT UP,DOWN,RIGHT OR LEFT.");
			System.exit(-1);
		}

		// move is not legal because there is a rock
		if (gridIdentifier[wantsToMove].getIdentifier().equals(Tile.ROCK_TILE)) {
			System.err.println("REQUESTED TO MOVE ON ROCK SQUARE.");
			System.exit(-1);
		}

		// move is also illegal if you're tyring to move into the enemies camp

		if ((gridIdentifier[wantsToMove].getIdentifier().equals(Tile.BLUE_CAMP_TILE) && isRed) || (gridIdentifier[wantsToMove].getIdentifier().equals(Tile.RED_CAMP_TILE) && !isRed)) {
			System.err.println("REQUESTED TO MOVE ON ENEMIES CAMP.");
			System.exit(-1);
		}

		// I guess if we get here, we can actually move!
		// Then there are three options, either the goto tile is a coin or else
		// its a grass field or its own camp

		// if its a camp, the sprite shouldnt move, but if is carrying a coin it
		// should drop of the coin and the movement list should go empty
		if (isRed && (gridIdentifier[wantsToMove].getIdentifier().equals(Tile.RED_CAMP_TILE))) {

			int coin = a.getCarrying();

			if (coin != -1) {
				coinDrop(isRed, a.getCarrying());
				a.setCarrying(-1);
			}
			a.clearMovementList();
			return false;
		} else if (!isRed && (gridIdentifier[wantsToMove].getIdentifier().equals(Tile.BLUE_CAMP_TILE))) {
			int coin = a.getCarrying();

			if (coin != -1) {
				coinDrop(isRed, a.getCarrying());
				a.setCarrying(-1);
			}

			a.clearMovementList();
			return false;
		}

		// if its a coin, two things can happen, either the actor is already
		// carrying a coin, in which case the coin being held is dropped at the
		// current location of the actor, then the actor moves to destination
		// and removed the coin there and puts it in inventory
		// else if he is not carrying anything, he removes the coin and moves to
		// the target square
		if ((gridIdentifier[wantsToMove].getIdentifier().equals(Tile.COIN_TILE))) {
			int carrying = a.getCarrying();

			// if he is carrying something, drop the coint
			if (carrying != -1) {
				// current location becomes a coin
				gridIdentifier[currentLocation] = mapper.get(Tile.COIN_TILE);
				// update coint location of coin dropped
				this.coinLocations.put(currentLocation, carrying);
			} else {
				gridIdentifier[currentLocation] = mapper.get(Tile.GRASS_TILE);
			}
			// targetlocation becomes sprite (red or blue)
			if (isRed) {
				gridIdentifier[wantsToMove] = mapper.get(Tile.RED_SPRITE_TILE);
			} else {
				gridIdentifier[wantsToMove] = mapper.get(Tile.BLUE_SPRITE_TILE);
			}

			// pick up the coin
			int pickup = this.coinLocations.get(wantsToMove);
			// give it to sprite
			a.setCarrying(pickup);
			// remove it from coint map
			this.coinLocations.remove(wantsToMove);
			// remove the action
			a.popMovement();
			a.setLocation(wantsToMove);

			return true;
		}

		if ((gridIdentifier[wantsToMove].getIdentifier().equals(Tile.GRASS_TILE))) {

			if (isRed) {
				gridIdentifier[wantsToMove] = mapper.get(Tile.RED_SPRITE_TILE);
			} else {
				gridIdentifier[wantsToMove] = mapper.get(Tile.BLUE_SPRITE_TILE);
			}

			gridIdentifier[currentLocation] = mapper.get(Tile.GRASS_TILE);
			a.popMovement();

			a.setLocation(wantsToMove);

			return true;

		}

		// this is still fatal if there is a head on collision, it is easier to
		// just allow for passing movements
		if (gridIdentifier[wantsToMove].getIdentifier().equals(Tile.BLUE_SPRITE_TILE) || gridIdentifier[wantsToMove].getIdentifier().equals(Tile.RED_SPRITE_TILE)) {

			if (isRed) {
				gridIdentifier[wantsToMove] = mapper.get(Tile.RED_SPRITE_TILE);
			} else {
				gridIdentifier[wantsToMove] = mapper.get(Tile.BLUE_SPRITE_TILE);
			}

			gridIdentifier[currentLocation] = mapper.get(Tile.GRASS_TILE);
			a.popMovement();
			a.setLocation(wantsToMove);

			return true;
		}

		throw new RuntimeErrorException(new Error("Ran out of options."));

	}

	public long getBlueToAddScore() {
		return blueToAddScore;
	}

	public void setBlueToAddScore(long blueToAddScore) {
		this.blueToAddScore = blueToAddScore;
	}

	public long getRedToAddScore() {
		return redToAddScore;
	}

	public void setRedToAddScore(long redToAddScore) {
		this.redToAddScore = redToAddScore;
	}

	public boolean isUpdatingGUI() {
		return updatingGUI;
	}

	public int getSquaresX() {
		return squaresX;
	}

	public int getSquaresY() {
		return squaresY;
	}

	public void updateGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui.updateGUI(gridIdentifier, processingBlue, processingRed, scoreBlue, scoreRed, queueBlue, queueRed, stepCount, totalSteps);
			}
		});
	}

	public synchronized void setUpdatingGUI(boolean updating) {
		this.updatingGUI = updating;
	}

}
