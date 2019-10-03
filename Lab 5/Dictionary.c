#include <stdlib.h>
#include <stdio.h>
#include "Dictionary.h"
#include "list.h"
#include <string.h>

typedef struct DictionaryObj {
   int tableSize;
   int size;
   List** table;
} DictionaryObj;

typedef struct EntryObj {
   char* key;
   char* value;
} EntryObj;

// allows EntryObj* to be called Entry in this file
typedef struct EntryObj* Entry;

/*
 *
 * YOUR FUNCTION IMPLEMENTATIONS GO BELOW HERE
 *
*/
Entry newEntry(char* key, char* value) {
   Entry e = calloc(1, sizeof(Entry)); 
   e->key = key;
   e->value = value;
   return e;
}

void freeEntry(Entry* pE) {
   if((*pE) != NULL) {
      free(*pE);
      *pE = NULL;
   } 
}

Dictionary newDictionary(int tableSize) {
   Dictionary D = malloc(sizeof(DictionaryObj));
   D->table = calloc(tableSize, sizeof(List*));
   D->tableSize = tableSize;
   D->size = 0;
}

void freeDictionary(Dictionary* pD) {
   for(int i = 0; i < (*pD)->tableSize; i++) { //loop thru table for buckets
      if((*pD)->table[i] != NULL) { // if bucket exists
         free_list((*pD)->table[i]); //free bucket (list)
      }
   }

   if((*pD)->table != NULL) {
   free((*pD)->table);
   (*pD)->table = NULL;
   }
   if((*pD) != NULL) {
   free(*pD);
   *pD = NULL;
   }
}      

int isEmpty(Dictionary D) {
   if(D->size == 0) 
      return 1;
   else
      return 0;
} 
 
int size(Dictionary D) {
   return D->size;
}

void insert(Dictionary D, char* key, char* value) {
   int arrayIndex = hash(D, key); // compute index

   if(D->table[arrayIndex] == NULL) {
      D->table[arrayIndex] = make_list(); //creates new list (bucket)
      add(D->table[arrayIndex], 0, newEntry(key, value)); // add new Entry
      D->size++;
   }
   else { // existing values w/ same key
      for(int i = 0; i < D->table[arrayIndex]->size; i++) { // look for entry w/ same key
         Entry e = (Entry)get(D->table[arrayIndex], i);
         if(strcmp(e->key, key) == 0) { // if same keys - replace value
            // replace old value w/ new
            e->value = value;
            break;
         }
         else if(i = size(D) - 1) { // at last Entry & no matches - create new Entry
            add(D->table[arrayIndex], 0, newEntry(key, value));
            D->size++;
         }
      }
   }
}

char* lookup(Dictionary D, char* key) {
   int arrayIndex = hash(D, key); // compute index

   if(D->table[arrayIndex] == NULL) // nothing stored yet
      return NULL;
   else {
      for(int i = 0; i < D->table[arrayIndex]->size; i++) {
         Entry e = get(D->table[arrayIndex], i);
         if(strcmp(e->key, key) == 0) // same key - return value
            return e->value;
      }
      // end of list - no matches
      return NULL;
   }
}

void delete(Dictionary D, char* key) {
    int arrayIndex = hash(D, key); // compute index

    if(D->table[arrayIndex] != NULL) { // existing bucket
      for(int i = 0; i < D->table[arrayIndex]->size; i++) {
          Entry e = (Entry)get(D->table[arrayIndex], i);
         if(strcmp(e->key, key) == 0) {
            //remove Entry, decrement size
            freeEntry(&e);
            remove_node(D->table[arrayIndex], i);
            D->size--;
         }
      }
    }
}

void makeEmpty(Dictionary D) {
   // free all entries
   for(int i = 0; i < D->tableSize; i++) { //loop thru table for buckets
      if(D->table[i] != NULL) {
         for(int j = 0; j < D->table[i]->size; j++) { // loop thru buckets for entries
            Entry e = (Entry)get(D->table[i], j);
            // free Entries + remove
               freeEntry(&e);
               remove_node(D->table[i], j);
         }
      }
   }
   D->size = 0;
}

void printDictionary(FILE* out, Dictionary D) {
   for(int i = 0; i < D->tableSize; i++) { //loop thru table for buckets
      if(D->table[i] != NULL) {
         for(int j = 0; j < D->table[i]->size; j++) { // loop thru buckets for entries
            Entry e = (Entry)get(D->table[i], j);
               fprintf(out, "%s %s\n", e->key, e->value);
         }
      }
   }
}
/*
 *
 * YOUR FUNCTION IMPLEMENTATIONS GO ABOVE HERE
 *
*/

/*
 * YOUR CODE GOES ABOVE THIS COMMENT
 * DO NOT ALTER THESE FUNCTIONS
 * THESE ARE THE THREE FUNCTIONS THAT WILL ALLOW YOU TO CONVERT 
 * A STRING INTO A VALID ARRAY INDEX
 * YOU WILL ONLY NEED TO CALL hash(Dictionary D, char* key)
*/

// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
   int sizeInBits = 8*sizeof(unsigned int);
   shift = shift & (sizeInBits - 1);
   if ( shift == 0 ) {
      return value;
   }
   return (value << shift) | (value >> (sizeInBits - shift));
}

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) { 
   unsigned int result = 0xBAE86554;
   while (*input) { 
      result ^= *input++;
      result = rotate_left(result, 5);
   }
   return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(Dictionary D, char* key){
   return pre_hash(key) % D->tableSize;
}