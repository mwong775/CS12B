public class BinarySearchTree implements BSTInterface {

	//BSTNode inner class
	protected class BSTNode {
		String item;
		BSTNode left;
		BSTNode right;

		BSTNode(String s) {
			item = s;
			left = null;
			right = null;
		}
	}
	//BST fields + constructor
	protected BSTNode root;

	public BinarySearchTree() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void makeEmpty() {
		root = null;
	}

	public MyQueue inOrder() {
		MyQueue q = new MyQueue();
		inOrderRecursive(root, q);
		return q;
	}

	public MyQueue preOrder() {
		MyQueue q = new MyQueue();
		preOrderRecursive(root, q);
		return q;
	}

	public MyQueue postOrder() {
		MyQueue q = new MyQueue();
		postOrderRecursive(root, q);
		return q;
	}

	public boolean contains(String s) {
		return recursiveSearch(root, s);
	}

	public void put(String s) {
		root = recursiveInsert(root, s);
	}

	public void delete(String s) {
		root = recursiveRemove(root, s);
	}

	// TODO: Fill this in and call it from contains()
	protected boolean recursiveSearch(BSTNode node, String s) {
		if(node == null) // not found
			return false;
		else if(node.item.equals(s)) // match
			return true;
		else if(s.compareTo(node.item) < 0) //before in ABC -> left subtree
			return recursiveSearch(node.left, s);
		else //if(s.compareTo(node.item) > 0) // after -> right subtree
			return recursiveSearch(node.right, s); 
	}

	// TODO: Fill this in and call it from put()
	protected BSTNode recursiveInsert(BSTNode node, String s){
		if(node == null)
			return new BSTNode(s);
		else {
			if(s.compareTo(node.item) < 0)
				node.left = recursiveInsert(node.left, s); // before in ABC order = left
			else if(s.compareTo(node.item) > 0)
				node.right = recursiveInsert(node.right, s); // after = right 
			//else: already in BST - return node
			return node;
		}
	}

	// TODO: Fill this in and call it from delete()
	protected BSTNode recursiveRemove(BSTNode node, String s) {
		if(node != null) {
			if(s.compareTo(node.item) < 0) 
				node.left = recursiveRemove(node.left, s);
			else if(s.compareTo(node.item) > 0)
				node.right = recursiveRemove(node.right, s);
			else if(s.equals(node.item))
				node = deleteNode(node);
		}
		return node;
	}
	
	// TODO: Fill this in and call it from recursiveRemove()
	protected BSTNode deleteNode(BSTNode node) {

		// Case 1: no children
		if(node.left == null && node.right == null)
			node = null;

		// Case 2: only left child
		else if(node.right == null)
			node = node.left;

		// Case 3: only right child
		else if(node.left == null)
			node = node.right;

		// Case 4: both children
		else {
			// get smallest item of largest group/branch (right)
			node.item = getSmallest(node.right);
			// recursively remove the retrieved smallest item from right subtree
			node.right = recursiveRemove(node.right, node.item);
		}
		return node;
	}

	// TODO: Fill this in and call it from deleteNode()
	protected String getSmallest(BSTNode node) {
		String smallest = node.item;
		while(node.left != null) {
			smallest = node.left.item;
			node = node.left;
		}
		return smallest;
	}


	// TODO: Fill this in and call it from inOrder()
	protected void inOrderRecursive(BSTNode node, MyQueue queue) {
		if(node != null) {
			inOrderRecursive(node.left, queue);
			queue.enqueue(node.item);
			inOrderRecursive(node.right, queue);
		}
	}


	// TODO: Fill this in and call it from preOrder()
	protected void preOrderRecursive(BSTNode node, MyQueue queue) {
		if(node != null) {
			queue.enqueue(node.item);
			preOrderRecursive(node.left, queue);
			preOrderRecursive(node.right, queue);
		}
	}

	// TODO: Fill this in and call it from postOrder()
	protected void postOrderRecursive(BSTNode node, MyQueue queue) {
		if(node != null) {
			postOrderRecursive(node.left, queue);
			postOrderRecursive(node.right, queue);
			queue.enqueue(node.item);
		}
	}

	// Prints out the tree structure, using indenting to show the different levels of the tree
	public void printTreeStructure() { 
		printTree(0, root);
	}

	// Recursive helper for printTreeStructure()
	protected void printTree(int depth, BSTNode node) {
		indent(depth);
		if (node != null) {
	    	System.out.println(node.item);
	    	printTree(depth + 1, node.left);
	    	printTree(depth + 1, node.right);
	 	} 
	 	else {
	  		System.out.println("null");
	  	}
	}

	// Indents with with spaces 
	protected void indent(int depth) {
		for(int i = 0; i < depth; i++)
			System.out.print("  "); // Indents two spaces for each unit of depth
	}


	// Extra Credit 
	public void balanceBST() {
		MyQueue values = inOrder();
		MyQueue temp = new MyQueue();

		// dequeue() elements into temp + count
		int count = 0;
		while(!values.isEmpty()) {
			Object item = values.dequeue();
			temp.enqueue(item);
			count++;
		}
		String[] array = new String[count];
		for(int i = 0; i < array.length; i++) {
			array[i] = (String)temp.dequeue();
		}
		makeEmpty();
		root = balanceRecursive(array, 0, array.length);
	}
	// TODO: If doing the extra credit, fill this in and call it from balanceBST()
	protected BSTNode balanceRecursive(String[] array, int first, int last) {
		// base case - single item
		if(first == last)
			return null;
		// middle index to obtain middle String
		int midIndex = (first + last) / 2;
		// get middle String
		String mid = array[midIndex];
		// make new BSTNode
		BSTNode node = new BSTNode(mid);
		// left, right children = recursive calls
		node.left = balanceRecursive(array, first, midIndex);
		node.right = balanceRecursive(array, midIndex + 1, last);
		return node;
	} 
}