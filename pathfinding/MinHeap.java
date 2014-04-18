package pathfinding;
import java.util.ArrayList;
import pathfinding.Values;
import java.util.HashMap;
public class MinHeap {

	public ArrayList <Values> heap;
	public HashMap <Integer, Integer> find_index; // om de index snel te kunnen bepalen
	public MinHeap(int max_tile) {
		heap = new ArrayList<Values>();

		find_index = new HashMap <Integer, Integer>();
	}

	public void add(Values node) {
		heap.add(node);
			find_index.put(node.tilenum, heap.size() - 1);
		minHeapify_bottom_up(heap.size() - 1);
	}

	public Values extractMin() {// (formerly called 'remove()')
		if (heap.size() == 0)
			return null;
		Values temp = heap.get(0);
			find_index.remove(temp.tilenum);
		heap.set(0, heap.get(heap.size() - 1));
			if(heap.size()!= 1) find_index.put(heap.get(heap.size() - 1).tilenum, 0); //indien er maar 1 element in de heap zit, moeten we daar rekening mee houden
		heap.remove(heap.size() - 1);
		minHeapify_top_down(0);
		return temp;
	}

	public int containsElement(Values check){ // unnecessary ?
		//return -1 if element is not found
		int ret=-1;
		for(int i=0; i<heap.size();i++){
			if(heap.get(i).equals(check)){ 
				ret = i;
				i=heap.size();
			}
		}
		return ret;
	}
	
	public void set(int pos, Values set_it){
		heap.set(pos,set_it);
	}
	
	public void minHeapify_top_down(int index) {
		int small;
		if (index * 2 + 1 >= heap.size())
			return;
		else if (2 * index + 2 == heap.size()) {
			if (heap.get(2 * index + 1).f_val < heap.get(index).f_val) {
				Values temp = heap.get(2 * index + 1);
				heap.set(2 * index + 1, heap.get(index));
					find_index.put(heap.get(index).tilenum, 2*index+1);
				heap.set(index, temp);
					find_index.put(temp.tilenum, index);
			}
			return;
		}
		if (heap.get(index * 2 + 1).f_val < heap.get(2 * index + 2).f_val) {
			small = index * 2 + 1;
		} else {
			small = 2 * index + 2;
		}
		if (heap.get(small).f_val < heap.get(index).f_val) {
			Values temp = heap.get(small);
			heap.set(small, heap.get(index));
				find_index.put(heap.get(index).tilenum, small);
			heap.set(index, temp);
				find_index.put(temp.tilenum, index);
			minHeapify_top_down(small);
		}
	}

	public void minHeapify_bottom_up(int index) {
		if (index == 0)
			return;
		int parent = (index - 1) / 2;
		if (heap.get(index).f_val < heap.get(parent).f_val) {
			Values temp = heap.get(parent);
			heap.set(parent, heap.get(index));
				find_index.put(heap.get(index).tilenum,parent);
			heap.set(index, temp);
				find_index.put(temp.tilenum, index);
			minHeapify_bottom_up(parent);
		}
	}

	public int size() {
		if(heap.size()==0){
			System.out.println("");
		}
		return heap.size();
	}

	public Values min() {// get root but do not remove
		if(heap.size()==0){
			System.out.println();
		}

		return heap.get(0);
	}

	public boolean isEmpty() {
		return heap.isEmpty();// use method from ArrayList
	}

}



