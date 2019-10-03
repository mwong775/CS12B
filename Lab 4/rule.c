
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "list.h"
#include "rule.h"
#include "helpers.h"

Rule* make_rule(char* key){
  /*
    TODO 3
  */
  //allocate space for Rule, put key
  Rule* new_rule = (Rule*)malloc(sizeof(Rule));
  new_rule->key = key;
  //initialize expansions to empty list, return pointer
  new_rule->expansions = make_list();
  return new_rule; // replace this
  /*
    TODO 3
  */
}

void free_rule(Rule* rule){
  /*
    TODO 3
  */
  if(rule->key != NULL) {
    free(rule->key);
    rule->key = NULL;
  }

  if(rule->expansions != NULL) {
    free_list(rule->expansions);   
  }

    if(rule != NULL) {
    free(rule);
    rule = NULL;
  }
	
  /*
    TODO 3
  */
	
}


List* read_grammar(char* filename){
   
  /*
   * TODO 4A
   */ 
  //Construct a new List* called grammar that we will fill up in the following code
  List* grammar = make_list();
  /* 
   * TODO 4A
   */
  FILE* input_file = fopen(filename,"r");
  char buffer[1000];
  
  int number_of_expansions = 0;
  int buffer_index = 0;

  int number_of_rules = 0;
  for (char current = fgetc(input_file); current != EOF; current = fgetc(input_file)){
    if (current == ':'){
      
	  
      char* key = calloc(buffer_index+1,sizeof(char));
      memcpy(key,buffer,buffer_index);
      /*
       * TODO 4B
       */ 
	   
      //Construct a new Rule* and add it to grammar 
      Rule* rule = make_rule(key);
      add(grammar, number_of_rules, rule);
      number_of_rules++;
      // new rule = no expansions yet
      number_of_expansions = 0;

      /*
       * TODO 4B
       */ 
      buffer_index = 0;
    }
    else if (current == ',' || current == '\n'){
      
      char* expansion = calloc(buffer_index+1,sizeof(char));      
      memcpy(expansion,buffer,buffer_index);
		
      /*
       * TODO 4C
       */ 

      //Get the last Rule* inserted into grammar and add expansion to it 
      Rule* lastRule = (Rule*) get(grammar, number_of_rules - 1);
      add(lastRule->expansions, number_of_expansions, expansion);
      number_of_expansions++;
      
      /*
       * TODO 4C
       */ 
      buffer_index = 0;
		 
    }
    else {
      buffer[buffer_index] = current;
      buffer_index++;
    }
  }
  fclose(input_file);

  
  /*
   * TODO 4D
   */ 
  return grammar; // replace this to return the grammar we just filled up
  /*
   * TODO 4D
   */ 
}



char* expand(char* text, List* grammar){

  /*
   * BONUS TODO
   */
  // split text to expand, List* to contain pieces
	List* splitText = split(text, "#");
  List* expansions = make_list();

  // iterate over elements of split text
  for(int i = 0; i < splitText->size; i++) {
    // even - add to exp list
    if(i % 2 == 0) {
      char* string =(char*)get(splitText, i);
      add(expansions, i, copy_string(string));
    }
    else{ // odd - find Rule* equal to element
      for(int j = 0; j < grammar->size; j++) { 
        Rule* current_rule = (Rule*)get(grammar,j);

        if(strcmp((char*)get(splitText, i), current_rule->key) == 0) {
          char* random = get_random(current_rule->expansions); // pick random expansion
          add(expansions, i, expand(random, grammar)); // recursive call
        }
      }
    }
  }
  // join char* List* into new string
  char* joinedString = join(expansions);
  // free List* (splitText)
  free_list(splitText);
  // free List* of expansions
  free_list(expansions);

  return joinedString;
  /*
   * BONUS TODO
   */
}

//Iterates through a grammar list and prints out all of the rules
void print_grammar(List* grammar){
  
  for (int ii = 0; ii < grammar->size; ii++){
    Rule* rule = get(grammar,ii);
    for (int jj = 0; jj < rule->expansions->size; jj++){
      printf("A potential expansion of rule '%s' is '%s'\n",rule->key, (char*) get(rule->expansions,jj));
    }
  }
  
}
