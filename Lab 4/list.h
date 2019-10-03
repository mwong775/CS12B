#ifndef CMPS12B_LIST
#define CMPS12B_LIST

typedef struct node_type{
	/*
	* TODO 1
	*/
	void* data;
	struct node_type* next;
	/*
	* TODO 1
	*/
	
} Node;

typedef struct {
	
	/*
	* TODO 1
	*/
	int size;
	struct node_type* head;
	/*
	* TODO 1
	*/
} List;

Node* make_node(void* data, Node* next); // constructor for Node
List* make_list(); // constructor for List

void free_node(Node* node); // destructor for Node
void free_list(List* list); // destructor for List

void add(List* list, int index, void* data); // add method for List
void* get(List* list, int index); // get method for List
void set(List* list, int index, void* data);


#endif

