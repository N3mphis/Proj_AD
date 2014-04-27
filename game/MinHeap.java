package game;
import java.util.ArrayList;
public class MinHeap<Type extends Comparable<Type>> {

	public ArrayList<Type> heap;

	public MinHeap() {
		heap = new ArrayList<Type>();
	}

	public void add(Type node) {
		heap.add(node);
		minHeapify_bottom_up(heap.size() - 1);
	}

	public Type extractMin() {// (formerly called 'remove()')
		if (heap.size() == 0)
			return null;
		Type temp = heap.get(0);
		heap.set(0, heap.get(heap.size() - 1));
		heap.remove(heap.size() - 1);
		minHeapify_top_down(0);
		return temp;
	}

	public int containsElement(Type check){
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
	
	public void set(int pos, Type set_it){
		heap.set(pos,set_it);
	}
	
	public void minHeapify_top_down(int index) {
		int small;
		if (index * 2 + 1 >= heap.size())
			return;
		else if (2 * index + 2 == heap.size()) {
			if (heap.get(2 * index + 1).compareTo(heap.get(index)) < 0) {
				Type temp = heap.get(2 * index + 1);
				heap.set(2 * index + 1, heap.get(index));
				heap.set(index, temp);
			}
			return;
		}
		if (heap.get(index * 2 + 1).compareTo(heap.get(2 * index + 2)) < 0) {
			small = index * 2 + 1;
		} else {
			small = 2 * index + 2;
		}
		if (heap.get(small).compareTo(heap.get(index)) < 0) {
			Type temp = heap.get(small);
			heap.set(small, heap.get(index));
			heap.set(index, temp);
			minHeapify_top_down(small);
		}
	}

	public void minHeapify_bottom_up(int index) {
		if (index == 0)
			return;
		int parent = (index - 1) / 2;
		if (heap.get(index).compareTo(heap.get(parent)) < 0) {
			Type temp = heap.get(parent);
			heap.set(parent, heap.get(index));
			heap.set(index, temp);
			minHeapify_bottom_up(parent);
		}
	}

	public int size() {
		return heap.size();
	}

	public Type min() {// get root but do not remove
		return heap.get(0);
	}

	public boolean isEmpty() {
		return heap.isEmpty();// use method from ArrayList
	}

}



