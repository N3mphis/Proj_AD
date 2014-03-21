package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI extends JPanel {


	
	private GUI thisObject = this;
	private volatile boolean gameEnded = false;
	private static final Random random = new Random();

	private int buttonSide = 16;
	private int gridX = 1200;
	private int gridY = 672;
	
	private int offsetY = 50;
	private int offset = 0;
	
	private int buttonsX = (int) (this.gridX/ this.buttonSide);
	private int buttonsY = (int) (this.gridY/ this.buttonSide);

	public int getButtonsX() {
		return buttonsX;
	}



	public void setButtonsX(int buttonsX) {
		this.buttonsX = buttonsX;
	}



	public int getButtonsY() {
		return buttonsY;
	}



	public void setButtonsY(int buttonsY) {
		this.buttonsY = buttonsY;
	}



	private Tile[] grid;
	
	private String scoreBlue = "Score team blue: ";
	private String scoreRed  = "Score team red: ";
	
	private JFrame frame;
	
	private String scoreCounterBlue ="0";
	private String scoreCounterRed ="0";
	
	private String procItemRed  = "Red is processing Tile: ";
	private String procItemBlue = "Blue is processing Tile: ";
	
	private String quededItemRed  = "Red queue: ";
	private String quededItemBlue = "Blue queue: ";
	
	private LinkedList<String> queueRed = new LinkedList<String>();
	private LinkedList<String> queueBlue = new LinkedList<String>();
	
	private String processingRed = "None";
	private String processingBlue = "None";
	
	private int iterationCountDown;
	private int totalIterations;

	
	private Dimension dim = new Dimension(1200,722);
	
	private GameLogic logic = null;
	
	public GUI(){
		

   

        grid = null;

		frame = new JFrame();
        frame.setTitle("Tilia");
        frame.add(this);
        this.setLayout(null);
        this.setSize(dim);
        this.setPreferredSize(dim);
        this.setBackground(Color.WHITE);
        this.setLocation(offset,offset);
	    this.setSize(new Dimension((int)dim.getWidth()+50,(int)dim.getHeight()+50));
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	   	frame.setVisible(true);
	       

	        
	}
	
	
	private void updateBar (Integer processingBlue, Integer processingRed, long scoreCounterBlue, long scoreCounterRed, LinkedList<Integer> blueList, LinkedList<Integer> redList, int iterationCountDown,int totalIterations ){
		
		
		// these variables should be updated
		
		if (processingBlue==-1){
			this.processingBlue = "<Nothing>";
		}else{
			this.processingBlue = ""+processingBlue;
		}
		
		if (processingRed==-1){
			this.processingRed = "<Nothing>";

		}else{
			this.processingRed = ""+processingRed;
		}
		
		this.scoreCounterBlue = ""+scoreCounterBlue;
		this.scoreCounterRed = ""+scoreCounterRed;

		this.queueBlue.clear();
		this.queueRed.clear();
		
		
		for (Integer x : blueList){
			this.queueBlue.add(x+"");
		}
		
		for (Integer x : redList){
			this.queueRed.add(x+"");
		}
		
		this.iterationCountDown = iterationCountDown;
		this.totalIterations = totalIterations;
		
		
		
	}

	



	private void updateGridIdentifier(Tile[] gridIdentifier) {
		
			if (this.grid==null || this.grid.length!= gridIdentifier.length){
				this.grid = new Tile[gridIdentifier.length];
			}
		
			for (int i = 0 ; i < gridIdentifier.length ; i++){
				this.grid[i] = gridIdentifier[i];
			}
		
	}



	public void paint(Graphics g){
		
		
		super.paint(g);
		
		
		
		
		// queue
		
		StringBuffer blue = new StringBuffer();
		StringBuffer red = new StringBuffer();

		if (this.queueBlue.size()==0){
			blue.append("<Empty>");
		}else{
			for (int i = 0 ; i < this.queueBlue.size() ; i++){
				blue.append(this.queueBlue.get(i));
				if (i==5 || i==this.queueBlue.size()-1){ break;}
				blue.append("-");
			}
		}
		

		if (this.queueRed.size()==0){
			red.append("<Empty>");
		}else{
			for (int i = 0 ; i < this.queueRed.size() ; i++){
				red.append(this.queueRed.get(i));
				if (i==5 || i==this.queueRed.size()-1){ break;}
				red.append("-");
			}
		}
		// paint the bar
		Font original = g.getFont();
		
		g.setFont(new Font("default", Font.BOLD, 12));
		g.drawString(this.scoreBlue,10,20);
		g.drawString(this.scoreRed, 650, 20);

		
		// draw some text
		g.drawString(this.procItemBlue, 10, 40);
		g.drawString(this.procItemRed, 650, 40);
		
		// if thread is processing something
		g.drawString(this.quededItemBlue, 210, 40);
		g.drawString(this.quededItemRed, 850, 40);
		
		g.setFont(original);
		
		// if thread is processing something
		g.drawString(this.processingBlue, 145, 40);
		g.drawString(this.processingRed, 785, 40);
		g.drawString(""+this.scoreCounterBlue,110,20);
		g.drawString(""+this.scoreCounterRed, 750, 20);
		g.drawString(red.toString(), 950, 40);
		g.drawString(blue.toString(), 300, 40);

		g.drawString("Iterations: "+iterationCountDown+"/"+totalIterations,480, 30);


		if (grid!=null){
		
			// paint grid
			for (int i = 0 ; i < buttonsY ; i++ ){
				int y =  (i *buttonSide);
				for(int j = 0 ; j < buttonsX ; j++){
					int x = ( j * buttonSide);
					Tile tile = grid[i*buttonsX + j];
					// add the offset here to allow room for the bar with processing updates
					g.drawImage(grid[i*buttonsX + j].draw(),x , y+offsetY, 16, 16, null);
				}
			if(gameEnded){
				Font f = g.getFont();
				Color col = g.getColor();
				g.setFont(new Font("TimesRoman", Font.PLAIN, 82)); 
				g.setColor(Color.WHITE);
				g.drawString("The End...",400,400);
				g.setFont(f);
				g.setColor(col);
			}
			
		}
			
	


			
			
			
		}


		
	}


	private void setEnd(){
		
		this.gameEnded=true;
	}

	public void signalEnd() {
		System.out.println("GAME END: ");
		System.out.println("BLUE TEAM SCORE: "+scoreCounterBlue);
		System.out.println("RED TEAM SCORE: "+scoreCounterRed);

		EventQueue.invokeLater(new Runnable(){public void run(){
			
			thisObject.setEnd();
			
		}});		
		
		
	}



	public void die() {

		EventQueue.invokeLater(new Runnable(){public void run(){
			


			
			thisObject.getFrame().dispose();
			
		}});		
		
	}



	public JFrame getFrame() {
		return frame;
	}



	public void setFrame(JFrame frame) {
		this.frame = frame;
	}



	public void updateGUI(Tile[] gridIdentifier, int processingBlue, int processingRed,long scoreBlue, long scoreRed, LinkedList<Integer> queueBlue,LinkedList<Integer> queueRed,int iterationCountDown,int totalIterations) {
		this.updateGridIdentifier(gridIdentifier);
		this.updateBar(processingBlue, processingRed, scoreBlue, scoreRed, queueBlue, queueRed,iterationCountDown,totalIterations);
		
		repaint();
		// signal that we ended updating the gui
		this.logic.setUpdatingGUI(false);
	}



	public void setLogic(GameLogic logic) {

		this.logic = logic;
	}
	
	
	

	
        
        
		
		
	





	
	
}
