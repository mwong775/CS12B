class MyHashtable implements DictionaryInterface {

	protected class Entry {
		String key;
		Object value;

		Entry(String k, Object v) {
			key = k;
			value = v;
		}
	}

	protected int tableSize; // size of array (table)
	protected int size; // number of key/value pairs stored
	protected MyLinkedList[] table; // array of LinkedLists

	public MyHashtable(int tableSize) { // hashtable constructor
		this.tableSize = tableSize;
		size = 0;
		table = new MyLinkedList[tableSize];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() { // returns # of key/value pairs
		return size;
	}

	public Object put(String key, Object value) {
		int hashValue = key.hashCode(); // computing index w/ key
		int arrayIndex = Math.abs(hashValue) % tableSize;

		if(table[arrayIndex] == null) { // nothing stored w/ same key yet
			// create new MyLInkedList (bucket)
			table[arrayIndex] = new MyLinkedList();
			// Add new Entry for key/value pair
			table[arrayIndex].add(0, new Entry(key, value));
			// increment size
			size++;
		}
		else { // existing values w/ same key
			// linearly search thru bucket 
			for(int i = 0; i < table[arrayIndex].size(); i++) { // look for entry w/ same key
				if(key.equals(((Entry)table[arrayIndex].get(i)).key)) { // if same key 
					// save old value to return
					Object oldValue = ((Entry)table[arrayIndex].get(i)).value; // (get() = object. cast to Entry)
					// replace old value w/ new
					((Entry)table[arrayIndex].get(i)).value = value; 

					return oldValue;
				}
			}
			// if key not found, add new Entry, increment size
			table[arrayIndex].add(0, new Entry(key, value)); // no same keys - new Entry
			size++;
		}
		//added new Entry, return null
		return null;
	}

public Object get(String key) {
	int hashValue = key.hashCode(); // compute index w/ key
	int arrayIndex = Math.abs(hashValue) % tableSize;

	if(table[arrayIndex] == null) // nothing stored here yet
		return null;
	else {
		// linearly search thru bucket + compare keys
		for(int i = 0; i < table[arrayIndex].size(); i++) { // look for entry w/ same key
			if(key.equals(((Entry)table[arrayIndex].get(i)).key)) // same keys (get() = object. cast to Entry)
				return ((Entry)table[arrayIndex].get(i)).value;
		}
		// end of list with no matches
		return null; 
	}
}

public void remove(String key) {
	int hashValue = key.hashCode(); //compute index w/ key
	int arrayIndex = Math.abs(hashValue) % tableSize;

	if(table[arrayIndex] != null) { // bucket exists
		// linearly search for Entry w/ given key
		for(int i = 0; i < table[arrayIndex].size(); i++) { 
			if(key.equals(((Entry)table[arrayIndex].get(i)).key)) { // found entry using key
				// remove Entry, decrement size
				table[arrayIndex].remove(i);
				size--;
			}
		}
	}
}

public void clear() {
	table = new MyLinkedList[tableSize]; //set to new array
	size = 0; 
}

public String[] getKeys() {
	String[] keyArray = new String[size]; // size = number of key/value pairs
	int keysIndex = 0; // track placement of String keys
	// iterate thru hashtable
	for(int i = 0; i < tableSize; i++) {
		if(table[i] != null) { // if bucket exists
			// iterate thru bucket 
			for(int j = 0; j < table[i].size(); j++) {
				keyArray[keysIndex] = ((Entry)table[i].get(j)).key;
				keysIndex++;
			}
		}
	}
	return keyArray;
}



	// Returns the size of the biggest bucket (most collisions) in the hashtable. 
	public int biggestBucket() {
		int biggestBucket = 0; 
		for(int i = 0; i < table.length; i++) {
			// Loop through the table looking for non-null locations. 
			if (table[i] != null) {
				// If you find a non-null location, compare the bucket size against the largest
				// bucket size found so far. If the current bucket size is bigger, set biggestBucket
				// to this new size. 
				MyLinkedList bucket = table[i];
				if (biggestBucket < bucket.size())
					biggestBucket = bucket.size();
			}
		}
		return biggestBucket; // Return the size of the biggest bucket found. 
	}

	// Returns the average bucket length. Gives a sense of how many collisions are happening overall.
	public float averageBucket() {
		float bucketCount = 0; // Number of buckets (non-null table locations)
		float bucketSizeSum = 0; // Sum of the size of all buckets
		for(int i = 0; i < table.length; i++) {
			// Loop through the table 
			if (table[i] != null) {
				// For a non-null location, increment the bucketCount and add to the bucketSizeSum
				MyLinkedList bucket = table[i];
				bucketSizeSum += bucket.size();
				bucketCount++;
			}
		}

		// Divide bucketSizeSum by the number of buckets to get an average bucket length. 
		return bucketSizeSum/bucketCount; 
	}

	public String toString() {
		String s = "";
		for(int tableIndex = 0; tableIndex < tableSize; tableIndex++) {
			if (table[tableIndex] != null) {
				MyLinkedList bucket = table[tableIndex];
				for(int listIndex = 0; listIndex < bucket.size(); listIndex++) {
					Entry e = (Entry)bucket.get(listIndex);
					s = s + "key: " + e.key + ", value: " + e.value + "\n";
				}
			}
		}
		return s; 
	}
}