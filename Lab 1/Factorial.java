 class Factorial {
	 public static int fact(int n) {
		 // We still need to fill in the factorial function.
		 // This won't currently compile because we haven't put a return value in here yet.
		 
		 if (n == 0) {
			 return 1;
		 }
		 else if (n > 0) {
			 return n * fact(n - 1);
		 } else {
			 return 0; //Not a valid return value for factorial
		 }
	 }

	 public static void main(String[] args) {
		 System.out.println("number of arguments: " + args.length);
		 if (args.length == 0) {
			 // Since args.length == 0, there are no arguments, so we'll just print 1! (factorial of one)
			 System.out.println(fact(1));
		 }
		 else {
			 /*
   				If the length isn't 0, then an argument was passed in - for now we're just printing the argument.
   				Next class we'll change the code to compute the factorial of this argument.
   				We're using Integer.parseInt() because the command line arguments are Strings. To get ready to pass
   				the command line argument to the fact() function, we need to turn it into a String.
			  */
			 int arg = Integer.parseInt(args[0]);
			 System.out.println(fact(arg));
		 }
	 }
 }
