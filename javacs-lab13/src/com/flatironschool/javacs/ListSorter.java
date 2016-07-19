/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        if (list.size()<2) {
        	return list;
        }
        List<T> half1 = new ArrayList<T>();
        List<T> half2 = new ArrayList<T>();
        half1.addAll(list.subList(0, list.size()/2));
        half2.addAll(list.subList(list.size()/2, list.size()));

        ListSorter<T> sorter = new ListSorter<T>();
		half1 = sorter.mergeSort(half1, comparator);
		half2 = sorter.mergeSort(half2, comparator);

		//merge them
		List<T> res = new ArrayList<T>();
		int a = 0;
		int b = 0;
		while (a<half1.size() && b<half2.size()) {
			if (comparator.compare(half1.get(a), half2.get(b)) >= 0) {
				res.add(half2.get(b));
				b++;
			} else {
				res.add(half1.get(a));
				a++;
			}
		}
		res.addAll(half1.subList(a, half1.size()));
		res.addAll(half2.subList(b, half2.size()));
        return res;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> queue = new PriorityQueue<T>();
        int s = list.size();
        for (int i=0; i < s; i++) {
        	queue.offer(list.get(i));        	
        }
        list.clear();
        for (int i=0; i < s; i++) {
        	list.add(queue.poll());        	
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> queue = new PriorityQueue<T>();
        for (int i=0; i < k; i++) {
        	queue.offer(list.get(i));        	
        }
        for (int j=k; j < list.size(); j++) {
        	T smallest = queue.poll();
        	if (comparator.compare(list.get(j), smallest) >= 0) {
        		queue.offer(list.get(j));
        	} else {
        		queue.offer(smallest);
        	}       	
        }
        List<T> res = new ArrayList();
        for (int i=0; i < k; i++) {
        	res.add(queue.poll());
        }
        return res;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
