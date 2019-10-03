
public class MyLinkedList implements ListInterface {

	/* TODO: Write a LinkedList implementation for all the methods specified in ListInterface */ 
	
	protected class Node{
		Object item;
		Node next;

		Node(Object o){ 
			item = o;
			next = null;
		}

		Node(Object o, Node next){ 
			item = o;
			this.next = next;
		}
	}

	protected Node head;
	protected int size;

	public MyLinkedList(){
		head = null;
		size = 0;
	}

	public boolean isEmpty(){
		if(size == 0)
			return true;
		else
			return false;
	}

	public int size(){
		return size;
	}

	public void add(int index, Object value){
		if (index >= 0 && index <= size) {
			if (index == 0) {
				Node n = new Node(value, head);
				head = n;
			}
			else if(index == size){
				Node temp = head;
				while(temp.next != null){
					temp = temp.next;
				}
				temp.next = new Node(value, null);
			}
			else {
				Node temp = head;
				Node prev = null;
				for(int i = 0; i < index; i++){
					prev = temp;
					temp = temp.next;
				}
				if(temp != null)
					prev.next = new Node(value, temp);
			}
			size++;
		}
		else {
			throw new ListIndexOutOfBoundsException("List index out of bounds on add");
		}
	}

	public void remove(int index){
		if (index >= 0 && index < size) {
			if (index == 0){
				head = head.next;
			}
			else {
				Node curr = head;
				Node prev = null;
				for(int i = 0; i < index; i++){
					prev = curr;
					curr = curr.next;
				}
				prev.next = curr.next;
			}
			size--;
		}
		else {
			throw new ListIndexOutOfBoundsException("List index out of bounds on remove");
		}
	}

	public void removeAll(){
		head = null;
		size = 0;
	}

	public Object get(int index){
		if (index >= 0 && index < size) {
			Node curr = head;
			for(int i = 0; i < index; i++){
				curr = curr.next;
			}
			return curr.item;
		}
		else{
			throw new ListIndexOutOfBoundsException("List index out of bounds on get");
		}
	}

	public int find(Object o){
		Node curr = head;
		for(int index = 0; index < size; index++){
			if(curr.item.equals(o))
				return index;
			curr = curr.next;
		}
		return -1; 
	}

	public String toString(){
		String s;
		s = "(";
		Node curr = head;
		for(int i = 0; i < size; i++){
			if(i == size - 1)
				s += curr.item;
			else
				s += curr.item + ", ";
			curr = curr.next;
		}
		s += ")";
		return s;
	}
}

