public class MyQueue implements QueueInterface {
	/* 
	* TODO 2: Implement "MyQueue"
	*/
	private MyLinkedList list;

	public MyQueue() {
		list = new MyLinkedList(); //creates new LinkedList to use for queue
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void enqueue(Object item) {
		list.add(list.size(), item); //adds to end of queue
	}

	public Object dequeue() { 
		if(isEmpty()) throw new QueueException("Tried to dequeue() an empty queue");

		Object firstItem = list.get(0);
		list.remove(0); //removes front(first) item in queue
		return firstItem;
	}

	public void dequeueAll() {
		list.removeAll();
	}

	public Object peek() {
		if(isEmpty()) throw new QueueException("Tried to peek() an empty queue");
					
		return list.get(0); //shows front(first) item in queue
	}

	public String toString() {
		return list.toString(); //left to right = front to back
	}

	
	/* 
	* TODO 2: Implement "MyQueue"
	*/

} 