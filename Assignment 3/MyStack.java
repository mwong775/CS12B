public class MyStack implements StackInterface {
	/* 
	* TODO 1: Implement "MyStack"
	*/
	private MyLinkedList list;

	public MyStack() {
		list = new MyLinkedList(); //creates new LinkedList to use for stack
	}

	public boolean isEmpty() {
		return list.isEmpty();			
	}

	public void push(Object o) {
		list.add(0, o); //adds to top ("front")
	}

	public Object pop() {
		if(isEmpty()) throw new StackException("Tried to pop() on empty stack");
		
		Object topItem = list.get(0);
		list.remove(0); //removes from top (1st item)
		return topItem;
	}

	public Object peek() {
		if(isEmpty()) throw new StackException("Tried to peek() on empty stack");
		
		return list.get(0);
	}

	public void popAll() {
		list.removeAll();
	}

	public String toString() {
		return list.toString(); //left to right = top to bottom
	}
	
	/* 
	* TODO 1: Implement "MyStack"
	*/
}