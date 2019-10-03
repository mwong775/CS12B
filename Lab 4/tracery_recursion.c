#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <assert.h>

//UNCOMMENT THIS ONCE YOU HAVE IMPLEMENTED LIST
#include "list.h"
#include "helpers.h"


//UNCOMMENT THIS ONCE YOU HAVE IMPLEMENTED RULE
#include "rule.h"

struct string{
  char* characters;
  int length;
};

int main(int argc, char** argv){


   
     //LIST TEST CODE
     List* list = make_list();
     List* reversed = make_list();
     for (int ii = 0; ii < argc; ii++){
     add(list,list->size,copy_string(argv[ii]));
     add(reversed,0,copy_string(argv[ii]));
     }
  
     printf("The list size is %d\n",list->size);
  
     for (int ii = 0; ii < argc; ii++){
     printf("%s\n", (char*) get(list,ii));
     }
  
     for (int ii = 0; ii < argc; ii++){
     printf("%s\n", (char*) get(reversed,ii));
     }
  
     free_list(list);
     free_list(reversed);
  
  
  

  
    //GRAMMAR TEST CODE
    
    List* grammar = read_grammar("grammar-story.txt");
    print_grammar(grammar);
    
  


  
  
   
    //* BONUS TODO 
  
  
   //List* grammar = read_grammar("grammar-story.txt");
   if (argc == 1){
   srand(time(NULL));
   }
   else{
   srand(atoi(argv[0]));
   }
  
   char* expansion = expand("#origin#",grammar);
   printf("%s\n",expansion);
   free(expansion);
   for (int ii = grammar->size-1 ; ii >= 0; ii--){
   free_rule( (Rule*) get(grammar,ii));
   set(grammar,ii,NULL);
   }
  free_list(grammar);
   //* BONUS TODO */
   
  
  return 0;
}

