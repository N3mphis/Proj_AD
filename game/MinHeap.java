package game;

import java.util.ArrayList;

/**
 * class with methods to create and maintain a MinHeap
 */
public class MinHeap<Type extends Comparable<Type>> {

	ArrayList<Type> heap;// in which Type can be String, Integer, ...

	/**
	 * create new MinHeap object
	 */
	public MinHeap() {
		heap = new ArrayList<Type>();

	}

	/**
	 * add performs the following actions: - increase length of the heap by 1 -
	 * start at last (=new) leaf: compare the node to be added with the parent,
	 * exchange if necessary; continue until the root, if necessary - use
	 * compareTo to compare nodes
	 * 
	 * @param node
	 *            : to be added to the MinHeap
	 */
	public void add(Type node) {
		heap.add(node);
		minHeapify_bottom_up(heap.size() - 1);
	}

	/**
	 * performs the following actions: - extract the root, - decrease the length
	 * of the heap by 1 - take care of the heap property (via minHeapify)
	 * 
	 * @return root node (=minimum of the heap)
	 */
	public Type extractMin() {// (formerly called 'remove()')
		if (heap.size() == 0)
			return null;
		Type temp = heap.get(0);
		heap.set(0, heap.get(heap.size() - 1));
		heap.remove(heap.size() - 1);
		minHeapify_top_down(0);
		return temp;
	}

	/**
	 * From the given index onwards, take care of the MinHeap property, assuming
	 * that the children of the current node are roots of correct MinHeaps. see
	 * Cormen p. 154 (but Min instead of Max)
	 * 
	 * @param index
	 */
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



