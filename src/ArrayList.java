/*
 * Lela Root 9/26/2023 List Implementation
 * Arraylist implementation built with an array
 */


import java.util.Iterator;

import net.datastructures.List;

public class ArrayList<E> implements List<E> {
	
	private E[] data;
	private int size = 0;
	private int cap = 0;
	
	private class ArrayListIterator implements Iterator<E> {
		int index = 0;
		
		@Override
		public boolean hasNext() {
			if (index < size) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public E next() {
			index++;
			return data[index-1];
		}
		
	}

	//constructor for arraylist with default capacity
	public ArrayList() {
		this(16);//default capacity of 16
		cap = 16;
	}

	//constructor for arraylist with given capacity
	public ArrayList(int capacity) {
		data = (E[]) new Object[capacity];
		cap = capacity;
	}
	
	//returns size of the arraylist
	@Override
	public int size() {
		return size;
	}

	// Return the current capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled. 
	public int capacity() {
		return cap;
	}
	
	//returns whether the array is empty
	@Override
	public boolean isEmpty() {
		if (size == 0){
			return true;
		} else {
			return false;
		}
	}

	//helper method to double the capacity when the arraylist is full
	private void doubleCapacity() {
		E[] newData;
		newData = (E[]) new Object[cap * 2];
			
		cap = cap * 2;
			
		for (int i = 0; i < size; i++) {
			newData[i] = data[i];
		}
			
		data = newData;
	}
		
	//returns the element at the given index
	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		if((i < 0) || (i >= size)) {
			throw new IndexOutOfBoundsException ("Can't get element, no data at that index");
		}
		
		return data[i];
	}

	//sets the contents of the element at the given index
	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if((i < 0) || (i >= size)) {
			throw new IndexOutOfBoundsException ("Can't set, no data at that index/index is out of bounds");
		}
		
		E temp = data[i];
		data[i] = e;
		return temp;
	}

	//adds a new element at the given index
	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		if((i < 0) || (i > size)) {
			throw new IndexOutOfBoundsException ("Can't add, that index is out of bounds");
		}
		if (size == cap) {
			doubleCapacity();
		}
		
		size++;
		for (int j = size - 1; j > i; j--) {
			data[j] = data[j-1];
		}
		data[i] = e;
		
	}
	
	//removes the element at the given index
	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if((i < 0) || (i >= size)) {
			throw new IndexOutOfBoundsException ("Can't remove, no data at that index");
		}
		
		E temp = data[i];
		for (int j = i; j < size; j++) {
			data[i] = data[i + 1];
			i++;
		}
		size--;
		return temp;
	}

	//adds a new element at the beginning of the arraylist
	public void addFirst(E e)  {
		if (size == cap) {
			doubleCapacity();
		}
		size++;
		for (int j = size - 1; j > 0; j--) {
			data[j] = data[j-1];
		}
		data[0] = e;
	}
	
	//adds a new element at the end of the arraylist
	public void addLast(E e)  {
		if (size == cap) {
			doubleCapacity();
		}
		size++;
		data[size - 1] = e;
	}
	
	//removes the element at the beginning of the arraylist
	public E removeFirst() throws IndexOutOfBoundsException {
		if(size == 0) {
			throw new IndexOutOfBoundsException ("Can't remove first, size is zero");
		}
		E temp = data[0];
		for (int j = 0; j < size; j++) {
			data[j] = data[j + 1];
		}
		size--;
		return temp;
	}
	
	//removes the element at the end of the arraylist
	public E removeLast() throws IndexOutOfBoundsException {
		if(size == 0) {
			throw new IndexOutOfBoundsException ("Can't remove last, size is zero");
		}
		
		E temp = data[size-1];
		size--;
		return temp;
	}
	
	
	@Override
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}



}
