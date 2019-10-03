#include <stdlib.h>
#include "list.h"
#include <stdio.h>
#include <assert.h>

Node* make_node(void* data, Node* next){
	/* 
	* TODO 2
	*/ 
	// allocate space for Node 
	Node* new_node = (Node*)malloc(sizeof(Node));

	// set member of new Node*
	new_node->data = data;
	new_node->next = next;

	return new_node; // replace this
	/* 
	* TODO 2
	*/ 
}

List* make_list(){
	/* 
	* TODO 2
	*/ 
	// allocate space for List
	List* list = (List*)malloc(sizeof(List));

	// sets members of new List
	list->size = 0;
	list->head = NULL;

	return list; // replace this
	/* 
	* TODO 2
	*/ 
}

void free_node(Node* node){
	
	/* 
	* TODO 2
	*/ 
	// frees data contained by Node, then Node itself
	if(node->data != NULL) {
		free(node->data);
		node->data = NULL;
	}

	if(node != NULL) {
		free(node);
		node = NULL;
	}

	/* 
	* TODO 2
	*/ 
}

void free_list(List* list) {
	
	/* 
	* TODO 2
	*/ 

	// iterate thru each Node* in list + frees, then free List itself
	Node* current = list->head;
	for(int i = 0; i < list->size; i++) {
		//printf("freeing item: %s\n", (char*)get(list, i));
		free_node(current);
		current = current->next;
	}

	if(list != NULL) {
		//printf("freeing list!\n");
		free(list);
		list = NULL;
	}

	/* 
	* TODO 2
	*/ 
}

void add(List* list, int index, void* data) {
	
	/* 
	* TODO 2
	*/ 
	// assert/exception for out of bounds
	assert(!(index > list->size || index < 0) && "Index was out of bounds");

	if(index == 0) { // adding to front of list
		Node* n = make_node(data, list->head);
		list->head = n;
	}
	else if(index == list->size) { // adding to end of list
		Node* current = list->head;
		while(current->next != NULL) {
			current = current->next;
		}
		current->next = make_node(data, NULL);
	}
	else { // adding to middle of list
		Node* current = list-> head;
		for(int i = 0; i < index - 1; i++) {
			current = current->next;
		}
		Node* newNode = make_node(data, current->next);
		current->next = newNode;
	}
	list->size++;

	/* 
	* TODO 2
	*/ 
}

void* get(List* list, int index){
	/* 
	* TODO 2
	*/ 
	// iterate to correct Node and return data
	assert(!(index > list->size || index < 0) && "Index was out of bounds");
	Node* current = list->head;
	for(int i = 0; i < index; i++) {
		current = current->next;
	}
	
	return current->data; // replace this
	/* 
	* TODO 2
	*/ 
}

void set(List* list, int index, void* data) {
	
	/* 
	* TODO 2
	*/ 
	// iterate to correct Node and replace data
	assert(!(index > list->size || index < 0) && "Index was out of bounds");
	Node* current = list->head;
	for(int i = 0; i < index; i++) {
		current = current->next;
	}
	current->data = data;
	/* 
	* TODO 2
	*/ 
}
