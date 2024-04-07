/*
 * Lela Root 9/26/2023 List Application
 * An application of DoublyLinkedList using the LRU procedure
 * Keep a list of only the most recently used and therefore relevant data
 */

import net.datastructures.*;

/*
 * The design of this program is related to Cache replacement policy in OS 
 * https://en.wikipedia.org/wiki/Cache_replacement_policies#Least_recently_used_(LRU)
 * 
 * LRU: Discards the least recently used items first.
 * 
 * Let's say we have a online store and would like to keep a list of certain number of customers
 * who visited our site from the most recent to the least recent.  
 * 
 * For example: 
 * 
 *  Let's say K=4, which means we only keep the most recent 4 customers. 
 *  And the visiting sequence of the customers: A B C D E D F. Each letter represent a customer's name.
 *
 *  The recent list will change as customers coming:
 *   
 *   A          -- A is the first customer
 *   B A        -- B is the next customer. B is not on the list and B's visit is sooner than A's
 *   C B A      -- C is the next customer. C is not on the list and C's visit is sooner than B's
 *   D C B A    -- D is the next customer. D is not on the list and D's visit is sooner than C's
 *   E D C B    -- E is the next customer. E is not on the list. But The list is full. 
 *                 Kick out A as it is the least recent one. 
 *                 Add E to the most recent one. 
 *              
 *   D E C B    -- D is the next customer. D is on the list already.  
 *                 D's place is moved from second to the first. 
 *   
 *   F D E C    -- F is the next customer. F is not on the list. The list is full.
 *                 Kick out B and put F as the most recent one 
 *   
 *  For simplicity we only keep the customer name in the list. 
 *  But to make the list useful, we will store some other information associated with the customer.  
 * 
 */


public class Customers {
	
	private DoublyLinkedList<String> list;
	private int capacity; 
	
	
	/* Keep up to K customers who visits us recently from the most recent to the least recent. */
	public Customers(int K) {
		capacity = K;
		list = new DoublyLinkedList<String>(); 
	}

	/* customer visits us, one at a time */
	public void visit(String customer) {
		for(Position<String> s: list.positions()) { //using foreach, figure out if customer is already in list
			if (s.getElement().equals(customer)) {
				list.remove(s);//remove customer, then add them to beginning of list
				list.addFirst(customer);
				return;
			}
		}
		
		if(list.size() == capacity) {//if list is full, remove the last customer and add new customer in first
			list.removeLast();
			list.addFirst(customer);
		} else {//otherwise, just add the new customer at the beginning
			list.addFirst(customer);
		}
	}
	
	/* return the most recent K customers */
	public PositionalList<String> getList() {
		return list;
	}
		
	/* start a new customer list.  
	 */
	public void resetList() {
		list = new DoublyLinkedList<String>();
		
	}
	
}
