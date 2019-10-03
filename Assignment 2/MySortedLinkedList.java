public class MySortedLinkedList extends MyLinkedList {

	/* TODO 
	   define the method
	   public void add(Comparable c)
	   that, given a Comparable (an interface type for all Object subclasses that define a compareTo() method), adds it to the 
	   list in sorted order.
	*/

	@SuppressWarnings("unchecked")
	
	public void add(Comparable c){
		if(size == 0){
			Node n = new Node(c, head);
			head = n;
			size++;
		}
		else{
			Node temp = head;
			boolean notLast = false;

			for(int i = 0; i < size; i++){
				if(c.compareTo(temp.item) < 0){
					super.add(i, c); //object is not last and added to middle of list
					notLast = true;
					break;
					}
			temp = temp.next;
				}
		if(notLast == false){
			super.add(size, c); //object is last and added at the end
			}
		}	
	}
	/* TODO
	   override the method
	   void add(int index, Object o)
	   so that it throws an UnsupportedOperationException with the message "Do not call add(int, Object) on MySortedLinkedList".
	   Directly adding objects at an index would mess up the sorted order.
	*/
	public void add(int index, Object o){
	 	throw new UnsupportedOperationException("Do not call add(int, Object) on MySortedLinkedList");
	 }
}