package game;
import java.util.HashMap;


public class CreditChecker {

	
	public static final String  BLUE_PROCESSING_THREAD = "-78782";
	public static final String  RED_PROCESSING_THREAD =  "-281287";
	
	private static volatile HashMap<String,Integer> calcThreads = new HashMap<String,Integer>();;
	private static volatile HashMap<String,Boolean >calcFinishedThreads = new HashMap<String,Boolean>();
	private static volatile HashMap<String,Boolean >calcIdlingThreads = new HashMap<String,Boolean>();
	private static volatile HashMap<String,Integer >calcOriginalThreads =new HashMap<String,Integer>();

	
	private CreditChecker (){}
	
	public static synchronized boolean canIGoRequest (String identifier){
		
		
		int value = calcThreads.get(identifier);
		
		if (value > 0){
			// means a thread wants credit, so it is (no longer) finished
			calcFinishedThreads.put(identifier,false);
			calcThreads.put(identifier, value-1);
			return true;
		}else{
			return false;
		}
	}
	
	public static synchronized void addOrResetCalculatingThread (String identifier,int credits){
		
		calcThreads.put(identifier, credits);
		calcFinishedThreads.put(identifier,false);
		calcIdlingThreads.put(identifier,false);
		calcOriginalThreads.put(identifier,credits);

	}
	

	
	public static synchronized void removeCalculatingThread (String identifier){
		
		calcThreads.remove(identifier);
		calcFinishedThreads.remove(identifier);

	}

	public static synchronized boolean isIdling (String identifier){

		Boolean x= calcIdlingThreads.get(identifier);

		if (x==null){
			return false;
		}else{
			return x.booleanValue();
		}
	}
	
	public static synchronized void giveNewCredit(String x){
		idling(x,false);
		calcThreads.put(x, calcOriginalThreads.get(x));
	}
	
	public synchronized static void iDoNotNeedAnyMoreCredits(String identifier){
		
		calcFinishedThreads.put(identifier,true);

		
	}
	
	public static synchronized boolean areAllThreadsFinished (){
		
		// all threads should either have indicated that they finished or have no credits left in this turn
		for (String x: calcThreads.keySet()){
		
			
			if (  (!calcFinishedThreads.get(x)) &&  !calcIdlingThreads.get(x)){
				return false;
			}
			
		}
		
		return true;
		
	}

	public static synchronized void idling(String id,boolean idling) {

		calcIdlingThreads.put(id,idling);
		
	}


	
	
	
	
}
