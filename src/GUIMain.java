package src;
import game.CreditChecker;
import game.GUI;
import game.GameLogic;
import game.Tile;
import grid.TiliaCollection;



public class GUIMain {

	
	public static void main (String[] args){
		
		
			TiliaCollection collectionBlue = new TiliaCollection(100000,CreditChecker.BLUE_PROCESSING_THREAD);
			Integer[] rew = collectionBlue.getRewards();
			TiliaCollection collectionRed = new TiliaCollection(100000,rew,CreditChecker.RED_PROCESSING_THREAD);
			GUI gui = new GUI();
			
			
			

			// Scenario 1: Trying out the A* star algorithm
			// uncomment the following code.
			// complete the code in the class AStarWalker
			// Answer the following questions in your report:
			// On which turn (aprox.) does : 
			// --  does the blue sprite start moving
			// --  the blue sprite pick up the Tilia.
			// --  the blue sprite return home.
			//
			// You can increase the speed variable or iterations variable if you want
			
			
			System.out.println("Starting scenario 1: ");
			final String map = "simple_map.txt";
			double speed = 0.2;
			int pathCredits= 25; // can check 25 squares in a single iteration
			int calcCredits =10000;
			GameLogic.AGENT_STRATEGY bluePathStrategy = GameLogic.AGENT_STRATEGY.ASTARWALKER;
			GameLogic.AGENT_STRATEGY redPathStrategy = GameLogic.AGENT_STRATEGY.RANDOM_WALKER;
			GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.SIMPLE_SOLVER;
			GameLogic.PROCESSING_STRATEGY redProcessingStrategy = GameLogic.PROCESSING_STRATEGY.SIMPLE_SOLVER;
			int iterations = 500;
			
			// end scenario 1
		
			 
			// Scenario 2: That's not fair
			// uncomment the following code.
			// use recursive solvers for both teams
			// Answer the following questions in your report:
			// Run the code a couple of times. Answer the following questions in your report
			// --  On average, who wins the game?
			// --  What problem does the blue team have?
			// --  Implement advancedStrategy in GameLogic and replace 
			// --  Briefly describe what you did and how it effects the game outcome.
			//
			// You can increase the speed variable 
		/*	
			
			System.out.println("Starting scenario 2: ");
			final String map = "not_fair.txt";
			double speed = 0.0001;
			int pathCredits= 40; // do not change
			int calcCredits =30; // do not change
			GameLogic.AGENT_STRATEGY bluePathStrategy = GameLogic.AGENT_STRATEGY.ASTARWALKER; // do not change
			GameLogic.AGENT_STRATEGY redPathStrategy = GameLogic.AGENT_STRATEGY.RANDOM_WALKER; // do not change
			GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.RECURSIVE_SOLVER; // do not change
			GameLogic.PROCESSING_STRATEGY redProcessingStrategy = GameLogic.PROCESSING_STRATEGY.RECURSIVE_SOLVER;// do not change
			int iterations = 1300; // do not change
			// end scenario 2
			*/
			
			
			
			// Scenario 3: Processing problems
			// uncomment the following code.
			// Answer the following questions in your report:
			// Run the code a couple of times. Answer the following questions in your report
			// --  On average, who wins the game?
			// --  What problem does the blue team have?
			// --  Change the processing strategy of the blue team to the ADVANCED_SOLVER and add the needed code in GameLogic
			// --  You can chose to solve this problem strategy wise or by using a different solver (or multiple different solvers)
			// --  Briefly describe what you did and how it effects the game outcome.
			//
			// You can increase the speed variable 
			/*
			
			System.out.println("Starting scenario 3: ");
			final String map = "process_problems.txt";
			double speed = 0.001;
			int pathCredits= 10; // do not change
			int calcCredits =20; // do not change
			GameLogic.AGENT_STRATEGY bluePathStrategy = GameLogic.AGENT_STRATEGY.ASTARWALKER; // do not change
			GameLogic.AGENT_STRATEGY redPathStrategy = GameLogic.AGENT_STRATEGY.RANDOM_WALKER; // do not change
			GameLogic.PROCESSING_STRATEGY redProcessingStrategy = GameLogic.PROCESSING_STRATEGY.DYNAMIC_SOLVER;// do not change
			
			int iterations = 1200; // do not change
			
			// change this 
			//GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.DYNAMIC_SOLVER; // do not change
			//GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.ADVANCED_SOLVER; // do not change
			GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.GREEDY_SOLVER;
			//end scenario 3
			
			*/
			
			// Scenario 4  : entrances  (If you implemented hierarchical version)
			// uncomment the following code.
			// 
			// Answer the following questions in your report:
			// Run the code a couple of times. Answer the following questions in your report
			// --  Add and implement another AGENT_STRATEGY to deal with this specific map, hierarchical approach
			// --  Try to get the highest score possible within the alloted time
			// --  You can preprocess the map in the GameLogic class, however specific pathfinding x,y -> a,b 
			//     should occur in the new Agent class and use the method 'protected Tile getTile(int spot)' of the class Agent
			//     to use the normal credit system.
			// -- Describe your strategy in your report and write down your score for various values of the variable 'iterations'
			// 
			//
			// You can increase the speed variable 
			
			/*
			
			System.out.println("Starting scenario 4: ");
			final String map = "entrance.txt";
			double speed = 0.1;
			int pathCredits= 10; // do not change
			int calcCredits =2000; // do not change
			GameLogic.AGENT_STRATEGY bluePathStrategy = GameLogic.AGENT_STRATEGY.HIERARCHICALWALKER; // do not change
			GameLogic.AGENT_STRATEGY redPathStrategy = GameLogic.AGENT_STRATEGY.RANDOM_WALKER; // do not change
			GameLogic.PROCESSING_STRATEGY redProcessingStrategy = GameLogic.PROCESSING_STRATEGY.DYNAMIC_SOLVER;// do not change
			GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.DYNAMIC_SOLVER; // do not change

			int iterations = 8000; 
			// end scenario 4
			*/
			
			/*
			// Scenario 5 OPTIONAL : Creative scenario
			// Create a nice scenario and describe it briefly in your report. 
			// You can also use the 'map generator' excel file to create a new map.The game only supports maps of the original size.
			// If you want you can make changes to the code to include new features, however make sure that at all times, the previous
			// scenarios work as originally intented. 

			// add here
			
			final String map = "ownmap.txt";
			double speed = 0.9;
			int pathCredits= 25; // can check 25 squares in a single iteration
			int calcCredits =10000;
			GameLogic.AGENT_STRATEGY bluePathStrategy = GameLogic.AGENT_STRATEGY.ASTARWALKER;
			GameLogic.AGENT_STRATEGY redPathStrategy = GameLogic.AGENT_STRATEGY.RANDOM_WALKER;
			GameLogic.PROCESSING_STRATEGY blueProcessingStrategy = GameLogic.PROCESSING_STRATEGY.SIMPLE_SOLVER;
			GameLogic.PROCESSING_STRATEGY redProcessingStrategy = GameLogic.PROCESSING_STRATEGY.SIMPLE_SOLVER;
			int iterations = 500;
			// end scenario 5
			*/
			
			GameLogic logic = new GameLogic(iterations, pathCredits,calcCredits,collectionBlue, collectionRed, gui ,map, speed, bluePathStrategy, redPathStrategy, blueProcessingStrategy,redProcessingStrategy);
			gui.setLogic (logic);
			logic.updateGUI();
			
			while (logic.isUpdatingGUI()) {
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logic.start();
			gui.signalEnd();

			long st = System.currentTimeMillis();
			
			while(System.currentTimeMillis() - st <5000){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gui.repaint();

			}
			
			gui.die();
			
		
	}  
}
