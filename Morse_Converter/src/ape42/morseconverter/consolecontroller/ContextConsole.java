/**
 * 
 */
package ape42.morseconverter.consolecontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ape42.morseconverter.model.Context;
import ape42.morseconverter.model.Core;
import ape42.morseconverter.model.State;
import ape42.morseconverter.model.StateMorseOrAlpha;

/**
 * Command-line interaction with the translator.  Other kinds of UI such as 
 * Swing or Web would belong in other packages.
 * 
 * @author Dave Alkire
 */
public class ContextConsole implements Context {
    
    private static final String CXT_CMD_QUIT = "Q";
    
    public static void main(String[] args) {
        ContextConsole launcher = new ContextConsole();
        System.out.printf( "Welcome to Dave's Morse Code Translator.  %n"
                + "Follow prompts, or H for help, or Q to quit.%n" );
        launcher.runUntilDone();
    }
    private List<String> contextCommands;
    private Core core;
    private boolean running;
    private Scanner scanner = null;
    private State state;
    
    
    public ContextConsole(){
        running = true;
        core = new Core(this);
        contextCommands = new ArrayList<String>();
        contextCommands.add(CXT_CMD_QUIT);
        state = new StateMorseOrAlpha(core);
        scanner = new Scanner(System.in);
    }

    public Core getCore() {
        return core;
    }

    @Override
    public State getState() {
        return state;
    }

    private boolean isValidContextCommand(String input) {
        return contextCommands.contains(input.toUpperCase());
    }

    /**
     * Takes input, does something with it, returns a response, and the state 
     * gets changed accordingly.
     */
    public void process() {
        
        System.out.print( "\r\n" + state.getPrompt() + ":" );
        String input = null;
        input = scanner.nextLine();
        
        // console-dependent decision
        if(isValidContextCommand(input)){
            processContextCommand(input);
        } else if(  core.isValidCommand(input) ){
            core.processCommand(input);
            // Note when this is done, the state will probably have changed 
            System.out.println( state.getResult() );
        }else if( state.isValidData(input) ){
            state.processData(input);
            System.out.println( state.getResult() );
        }else{     // Input is invalid.
            System.out.println( state.getValidationMessage() );
        }
    }

    private void processContextCommand(String input) {
        if ( CXT_CMD_QUIT.equalsIgnoreCase(input) ){
            running = false;
            System.out.println("[finished]");
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
