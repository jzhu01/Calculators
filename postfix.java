import java.util.*;
import java.io.*;

/** 
 *  Shows use of StreamTokenizer.
 *  @author Jennifer Zhu
 *  @version CSC 212, 10/21/15
 */
public class PostFix {
	/** Stack to hold the integers to be multiplied */
	public static Stack<Object> integerStack;
	
    /** Pattern that matches on words */
    public static final String WORD = "[a-zA-Z]*\\b";

    /** Pattern that matches on arithmetic symbols */
    public static final String SYMBOL = "[^\\w]";

    /** Run short test */
    public static void main(String[] args) {
	if (args.length==0) {
	    System.err.println("Usage:  java ShuntYardStub <expr>");
	} else {
	    Scanner input = new Scanner(new StringReader(args[0]));
	    
	    // Below is a complicated regular expression that will split the
	    // input string at various boundaries.
	    
	    input.useDelimiter
		("(\\s+"                             // whitespace
		 +"|(?<=[a-zA-Z])(?=[^a-zA-Z])"      // word->non-word
		 +"|(?<=[^a-zA-Z])(?=[a-zA-Z])"      // non-word->word
		 +"|(?<=[^0-9\\056])(?=[0-9\\056])"  // non-number->number
		 +"|(?<=[0-9\\056])(?=[^0-9\\056])"  // number->non-number
		 +"|(?<=[^\\w])(?=[^\\w]))");        // symbol->symbol
	    
	    //initialize the stack
	    integerStack = new Stack<Object>();
	    
	    //count
	    int count = 0;
	    
	    while(input.hasNext()) {
	    	if (input.hasNextDouble()) {
	    		double temp = input.nextDouble();
	    		integerStack.push(temp);
	    		System.out.println("Stack looks like: "+integerStack);
	    	} else {
	    		System.out.println(count);
	    		System.out.println("Your item is: "+input.next());
	    		count++;
	    	}
	}
	    System.out.println("Your result is: "+integerStack);
    }
    }
}
