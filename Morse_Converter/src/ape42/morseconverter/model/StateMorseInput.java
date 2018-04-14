package ape42.morseconverter.model;

import java.util.Set;

/**
 * User types dots and dashes.
 * 
 * @author Dave Alkire
 */
public class StateMorseInput implements State {

    private static final String CMD_TEXT = "T";
    private CharSequence result;
    private Core core;

    public StateMorseInput(Core core) {
        this.core = core;
    }

    @Override
    public CharSequence getHelp() {
        StringBuilder returnval =  new StringBuilder(
                "Enter the dots (.) and dashes(-), and one space to separate "
                + "each letter, \r\nand two spaces to separate each word. "
                + "\r\nOr type'T' to switch to text input. \r\n");
        
        Set<String> morseKeys = core.getMorseToAlpha().keySet();
        int indx = 1;
        for( String mKey: morseKeys){
            returnval.append( 
                    core.getMorseToAlpha().get(mKey)+" "+ mKey);
            
            if(indx % 6 == 0){
                returnval.append("\r\n");
            }else{
                returnval.append(", ");
            }
            indx++;
        }
        
        return returnval;
    }

    @Override
    public CharSequence getPrompt() {
        return "Morse";
    }

    @Override
    public CharSequence getResult() {
        return result;
    }

    @Override
    public CharSequence getValidationMessage() {
        return "Morse code please:  dots, dashes and spaces.";
    }

    @Override
    public boolean isValidCommand(String input) {
        return CMD_TEXT.equalsIgnoreCase(input);
    }

    @Override
    public boolean isValidData(String input) {
        return input.matches("[\\.\\-\\s]+");
    }

    @Override
    public void processCommand( String input ) {
        if(CMD_TEXT.trim().equalsIgnoreCase(input)){
            result = "Switching to text input.";
            State newState = new StateTextInput( core );
            newState.setResult(result);
            core.getContext().setState( newState );
        }
    }

    @Override
    public void processData(String input) {

        StringBuilder strBld = new StringBuilder();
        String[] mWords = input.split("\\s{2}");
        for(String mWord: mWords){
            String[] mCodes = mWord.split("\\s");
            for(String mCode: mCodes){
                String ltr = core.getMorseToAlpha().get(mCode);
                if(ltr == null){
                    strBld.append("(?)");
                }else{
                    strBld.append(ltr);
                }

            }
            strBld.append(" ");
        }
        result = strBld;
        
    }

    @Override
    public void setResult(CharSequence result) {
        this.result = result;
    }

}
