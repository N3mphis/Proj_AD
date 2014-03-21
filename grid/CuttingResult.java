package grid;

import java.util.ArrayList;


public class CuttingResult {
	private int cutId ;
	private int cut ;
	private long reward;
	private ArrayList<Integer> pieces ;

	public CuttingResult(int cutId, int cut, long reward, ArrayList<Integer> pieces){
		this.cutId = cutId;
		this.cut = cut;
		this.reward = reward;
		this.pieces =pieces;
	}

	public int getCutId() {
		return cutId;
	}

	public void setCutId(int cutId) {
		this.cutId = cutId;
	}

	public int getCut() {
		return cut;
	}

	public void setCut(int cut) {
		this.cut = cut;
	}

	public long getReward() {
		return reward;
	}

	public void setReward(long reward) {
		this.reward = reward;
	}

	public ArrayList<Integer> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<Integer> pieces) {
		this.pieces = pieces;
	}

}