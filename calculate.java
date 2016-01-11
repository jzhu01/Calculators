import java.util.*;
import java.io.*;


/** 
 *  Shows use of StreamTokenizer.
 *  @author Jennifer Zhu
 *  @version CSC 212, 10/21/15
 */
public class Calculate {
	/** Stack to hold the integers to be multiplied */
	public static Stack<Object> stack;
	
	/** Queue to hold integers */
	public static LinkedList<Object> outputQueue;
	
    /** Pattern that matches on words */
    public static final String WORD = "[a-zA-Z]*\\b";

    /** Pattern that matches on arithmetic symbols */
    public static final String SYMBOL = "[^\\w]";

    /**
     * method will set a precedence level for each operator and compare
     * @param o1 - stands for 1st operator being passed 
     * @return  - will return the precedence level
     */
    public static int getPrecedence(char o1) {
    	int temp = 0;
    	if (o1 == '+' || o1 == '-'){
    		temp = 2;
    	} else if (o1 == '*' || o1 == '/') {
    		temp = 3;
    	} else if (o1 == '^') {
    		temp = 4;
    	}
    	return temp;
    }
    
    /** Run short test */
    public static void main(String[] args) {
	if (args.length==0) {
	    System.err.println("Usage:  java ShuntYardStub <expr>");
	} else {
	    Scanner input = new Scanner(new StringReader(args[0]));
	    
	    input.useDelimiter
		("(\\s+"                             // whitespace
		 +"|(?<=[a-zA-Z])(?=[^a-zA-Z])"      // word->non-word
		 +"|(?<=[^a-zA-Z])(?=[a-zA-Z])"      // non-word->word
		 +"|(?<=[^0-9\\056])(?=[0-9\\056])"  // non-number->number
		 +"|(?<=[0-9\\056])(?=[^0-9\\056])"  // number->non-number
		 +"|(?<=[^\\w])(?=[^\\w]))");        // symbol->symbol
	    
	    //initialize the stack
	    stack = new Stack<Object>();
	    
	    // initialize the queue
	    outputQueue = new LinkedList<Object>();
	    
	    // initialize the mark
	    ListIterator<Object> mark;
	    mark = outputQueue.listIterator();
	    
	    String equation = args[0];
	    //System.out.println("Your equation is: " +args[0]);
	    
	    //While loop to scan through the code

	    while (input.hasNext()) {
	    	if (input.hasNextDouble()) {
	    		outputQueue.addLast(input.nextDouble());
	    		//System.out.println("Output Queue is: "+outputQueue);
	    	} else if (input.hasNext(SYMBOL)) {
	    		char temp = input.next().charAt(0);
	    		//System.out.println("Character is:" +temp); 
	    		
	    		if (temp == '(') {
	    			stack.push(temp);
	    			//System.out.println("Intger stack looks like: "+integerStack);
	    			
	    		} else if ( temp == ')') {
	    			//System.out.println("Integer Stack looks like: "+integerStack);
			    	try {
			    		while ((Character) stack.peek() != '(') {
			    			outputQueue.addLast(stack.pop());
			    			//System.out.println("Output Queue looks like: "+outputQueue);
			    		}
			    		stack.pop();
			    		//System.out.println("Integer Stack looks like: "+integerStack);
			    	} catch (EmptyStackException e) {
			    		System.out.println("You seem to have mismatched parentheses...Please try again");
			    		System.exit(0);
			    	}
			    
	    		} else {
	    			if (stack.isEmpty()) {
	    				stack.push(temp);
		    		} else if ((Character) stack.peek() == '(' || (Character) stack.peek() == ')') {
		    			stack.push(temp);
		    		} else {
		    			//System.out.println("Precedence is: "+getPrecedence(temp));
		    			//System.out.println("Precedence is: "+getPrecedence((Character)integerStack.peek()));
		    			if ( getPrecedence(temp) <= getPrecedence((Character) stack.peek())) {
		    				outputQueue.addLast(stack.pop());
		    				stack.push(temp);
		    				//System.out.println("The output Queue looks like: "+outputQueue);
		    				//System.out.println("The integer Stack looks like: "+integerStack);
		    			} else {
		    				stack.push(temp);
		    				//System.out.println("Integer stack is: "+integerStack);
		    			}
		    		}
	    		}
	    	}
	    }
	    while (!stack.isEmpty()) {
	    		outputQueue.addLast(stack.pop());
	    }
	   
	    //System.out.println("Your stack is: "+integerStack);
	    //System.out.println("Your queue is: "+outputQueue);
	    while (!outputQueue.isEmpty()) {
	    	try {
	    	if (outputQueue.peek() instanceof Double ){
	    		stack.push(outputQueue.removeFirst());
	    	} else {
	    		double number1 = (Double) stack.pop();
	    		double number2 = (Double) stack.pop();
	    		if ((Character) outputQueue.peek() == '+'){
	    			double result = number1 + number2;
	    			//System.out.println("Result after addition: "+result);
	    			stack.push(result);
	    			outputQueue.removeFirst();
	    		} else if ((Character) outputQueue.peek() == '-') {
	    			double result = number2 - number1;
	    			//System.out.println("Result after subtraction: "+result);
	    			stack.push(result);
	    			outputQueue.removeFirst();
	    		} else if ((Character) outputQueue.peek() == '*') {
	    			double result = number2 * number1;
	    			//System.out.println("Result after multiplication: "+result);
	    			stack.push(result);
	    			outputQueue.removeFirst();
	    		} else if ((Character) outputQueue.peek() == '/') {
	    			double result = number2 / number1;
	    			//System.out.println("Result after division: "+result);
	    			stack.push(result);
	    			outputQueue.removeFirst();
	    		}  else if ((Character) outputQueue.peek() == '^') {
	    			double result = (double) Math.pow(number2, number1);
	    			//System.out.println("Result after exponentiation: "+result);
	    			stack.push(result);
	    			outputQueue.removeFirst();
	    		}
	    	}
	    } catch (EmptyStackException e) {
	    	System.out.println("You seem to have mismatched parenthesis...Please try again.");
	    	System.exit(0);
	    }
	    }
	    System.out.println("Your Output is: "+stack);
	   
    	}
    }
}


