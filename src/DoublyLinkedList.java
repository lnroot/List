/*
 * Lela Root 9/26/2023 List Implementation
 * Doubly Linked List implementation
 */

import java.util.Iterator;

import net.datastructures.Position;
import net.datastructures.PositionalList;


public class DoublyLinkedList<E> implements PositionalList<E> {
	
	private Node<E> header;
	private Node<E> tail;
	private int size = 0;
	
	/* 
	 * Node class which should contain element and pointers to previous and next nodes
	 */
	private static class Node<E> implements Position<E> {
		
		private E element;
		private Node<E> prev;
		private Node<E> next;
		
		//Node constructor
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}
		
		//returns the element contained in the node
		@Override
		public E getElement() {
			if (next == null) {
				throw new IllegalStateException("");
			}
			return element;
		}
		
		//returns the next node
		public Node<E> getNext(){
			return next;
		}
		
		//returns the previous node
		public Node<E> getPrev() {
			return prev;
		}
		
		//sets a pointer to the next node
		public void setNext(Node<E> n) {
			next = n;
		}
		
		//sets a pointer to the previous node
		public void setPrev(Node<E> p) {
			prev = p;
		}
		
		//sets the element in the node
		public void setElement(E e) {
			element = e;
		}
		
	}
	
	//Doubly Linked Positional List constructor
	public DoublyLinkedList() {
		header = new Node<>(null, null, null);
		tail = new Node<>(null, header, null);
		header.setNext(tail);
	}

	/*
	 * helper method that "validates" a position, and returns it as a node
	 */
	private Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if(!(p instanceof Node)) throw new IllegalArgumentException("p is not a Node");
		
		Node<E> node = (Node<E>) p;
		
		if(node.getNext() == null) throw new IllegalArgumentException("p does not exist");
		 
		return node;
	}
	
	/*
	 * helper method that returns a position given a node, and null if the node is header or tail
	 */
	private Position<E> position(Node<E> n){
		if (n == header || n == tail) {
			return null;
		}
		return n;
	}
	
	//returns size of list
	@Override
	public int size() {
		return size;
	}

	//returns whether list is empty
	@Override
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		} else {
			return false;
		}
	}

	//returns the first position of the list
	@Override
	public Position<E> first() {
		return position(header.getNext());
	}

	//returns the last position of the list
	@Override
	public Position<E> last() {
		return position(tail.getPrev());
	}

	//returns the position before the current position
	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p).getPrev();
		return position(node);
	}

	//returns the position after the current position
	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p).getNext();
		return position(node);
	}
	
	/*
	 * helper method that takes as input a new element, the preceding node, and the succeeding node, and adds a new node
	 * returns the position of the new node
	 */
	private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
		Node<E> newNode = new Node<>(e, pred, succ);
		pred.setNext(newNode);
		succ.setPrev(newNode);
		size++;
		
		return newNode;
	}

	//adds a new position at the beginning of the list, returns that new position
	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header, header.getNext());
	}

	//adds a new position at the end of the list, returns that new position
	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, tail.getPrev(), tail);
	}

	//adds a new position before the current position, returns that new position
	@Override
	public Position<E> addBefore(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e, node.getPrev(), node);
	}

	//adds a new position after the current position, returns the new position
	@Override
	public Position<E> addAfter(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e, node, node.getNext());
	}

	//sets the element at a position
	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E element = node.getElement();
		node.setElement(e);
		return element;
	}

	/*
	 * helper method that removes a node
	 */
	private void removeBetween(Node<E> n) {
		Node<E> pred = n.getPrev();
		Node<E> succ = n.getNext();
		
		pred.setNext(succ);
		succ.setPrev(pred);
		
		size--;
		
		n.setElement(null);
		n.setNext(null);
		n.setPrev(null);
	}
	
	//remove the given position
	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E element = node.getElement();	
		removeBetween(node);
		return element;
	}

	//removes the first position in the list
	public E removeFirst() throws IllegalArgumentException {
		Node<E> node = header.getNext();
		E element = node.getElement();
		removeBetween(node);
		return element;
	}
	
	//removes the last position in the list
	public E removeLast() throws IllegalArgumentException {
		Node<E> node = tail.getPrev();
		E element = node.getElement();
		removeBetween(node);
		return element;
	}
	


	/*
	 * Returns an array containing all of the elements in this list 
	 * in proper sequence (from first to last element).
	 * The returned array will be "safe" in that no references to it are maintained by this list. 
	 * (In other words, this method must allocate a new array). 
	 * The caller is thus free to modify the returned array.
	*/
	public Object [] toArray() {
		Object[] arrayOut = new Object[size];
		Node<E> current = header.next;
		
		for (int i = 0; i < size; i++) {
			arrayOut[i] = current.getElement();
			current = current.next;
		}
		
		return arrayOut;
	}
	

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	/*
	 *Element iterator will return one element at a time  
	 */
	private class ElementIterator implements Iterator<E> {

		Iterator<Position<E>> posIterator = new PositionIterator( );
		
		@Override
		public boolean hasNext() {
			return posIterator.hasNext( );
		}

		@Override
		public E next() {
			return posIterator.next( ).getElement( );
		}

	}
	


	
	/************************************************************************
	 * 
	 * The method positions(), PositionInterable class and PositionIterator class
	 * have been fully implemented.  
	 * It depends the methods first(), after() that you are implementing. 
	 *
	 ************************************************************************/

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}
	
	/*
	 * Position iterator will return one Position at a time  
	 */
	private class PositionIterable implements Iterable<Position<E>> {

		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		}
		
	}
	
	private class PositionIterator implements Iterator<Position<E>> {
		Position<E> p=first();
		
		@Override
		public boolean hasNext() {
			return p!=null;
		}

		@Override
		public Position<E> next() {
			Position<E> cur = p;
			p = after(p);
			return cur;
		}
		
	}

}
