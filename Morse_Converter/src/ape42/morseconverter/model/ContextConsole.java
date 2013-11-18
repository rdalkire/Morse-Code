/**
 * 
 */
package ape42.morseconverter.model;

import java.util.Scanner;

/**
 * Command-line interaction with the translator.  Other kinds of UI such as 
 * Swing or Web would belong in other packages.
 * 
 * @author Dave Alkire
 */
public class ContextConsole implements Context {

	public static void main(String[] args) {
    	ContextConsole launcher = new ContextConsole();
    	System.out.printf( "Welcome to Dave's Morse Code Translator.  %n"
    			+ "Follow prompts, or H for help, or Q to quit.%n" );
    	launcher.runUntilDone();
    }
	Core core;
	boolean running;
    Scanner scanner = null;
	
	private State state;
	
	ContextConsole(){
	    running = true;
	    core = new Core();
		state = new StateMorseOrAlpha(core);
		scanner = new Scanner(System.in);
	}

    public Core getCore() {
        return core;
    }

	/**
	 * Takes input, does something with it, returns a response, and changes 
	 * state accordingly.
	 */
	public void process() {
		
		System.out.print( "\r\n" + state.getPrompt() + ":" );
		String input = null;
		input = scanner.nextLine();
		
		/* The "Q" is a special case, applicable for console apps but maybe not 
		 * for other contexts such as servlets. */
		if (input.equalsIgnoreCase("Q")){
			running = false;
			System.out.println("[finished]");
		}else{
			state.validateInput(input);
			
			if(state.isInputValid()){
				state.process(input, this);
				System.out.println( state.getResult() );
			}else{
				System.out.println(state.getValidationMessage());
			}
		}
	}

    void runUntilDone(){
    	
    	while(running){
    		process();
    		try {
    			Thread.sleep(100);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}
    }

    @Override
	public void setState(State state) {
		this.state = state;
	}

}
