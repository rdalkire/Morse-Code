package ape42.morseconverter.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Common core functionality for the Morse Code app, regardless of context. 
 * @author Dave Alkire
 */
public class Core {

    private Map<String, String> alphaToMorse;
    private Map<String, String> morseToAlpha;
    private Context context;
    
    public static boolean isHlpQry(String input) {
        return "H".equalsIgnoreCase(input);
    }

    public Core(Context context){

        this.setContext(context);
        
        alphaToMorse = new LinkedHashMap<String, String>();
        alphaToMorse.put("A", ".-");
        alphaToMorse.put("B", "-...");
        alphaToMorse.put("C", "-.-.");
        alphaToMorse.put("D", "-..");
        alphaToMorse.put("E", ".");
        alphaToMorse.put("F", "..-.");
        alphaToMorse.put("G", "--.");
        alphaToMorse.put("H", "....");
        alphaToMorse.put("I", "..");
        alphaToMorse.put("J", ".---");
        alphaToMorse.put("K", "-.-");
        alphaToMorse.put("L", ".-..");
        alphaToMorse.put("M", "--");
        alphaToMorse.put("N", "-.");
        alphaToMorse.put("O", "---");
        alphaToMorse.put("P", ".--.");
        alphaToMorse.put("Q", "--.-");
        alphaToMorse.put("R", ".-.");
        alphaToMorse.put("S", "...");
        alphaToMorse.put("T", "-");
        alphaToMorse.put("U", "..-");
        alphaToMorse.put("V", "...-");
        alphaToMorse.put("W", ".--");
        alphaToMorse.put("X", "-..-");
        alphaToMorse.put("Y", "-.--");
        alphaToMorse.put("Z", "--..");
        
        alphaToMorse.put("1", ".----");
        alphaToMorse.put("2", "..---");
        alphaToMorse.put("3", "...--");
        alphaToMorse.put("4", "....-");
        alphaToMorse.put("5", ".....");
        alphaToMorse.put("6", "-....");
        alphaToMorse.put("7", "--...");
        alphaToMorse.put("8", "---..");
        alphaToMorse.put("9", "----.");
        alphaToMorse.put("0", "-----");
        
        morseToAlpha = new LinkedHashMap<String, String>();
        for( String alphKey: alphaToMorse.keySet() ){
            morseToAlpha.put(alphaToMorse.get(alphKey), alphKey );
        }
        
    }

    public Map<String, String> getAlphaToMorse(){
        return alphaToMorse;
    }
    public Map<String, String> getMorseToAlpha(){
        return morseToAlpha;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isValidCommand(String input) {
        return isHlpQry(input) || context.getState().isValidCommand(input); 
    }

    public void processCommand(String input) {
        State state = context.getState();
        if ( isHlpQry(input) ){
            state.setResult(state.getHelp());
        }else{
            state.processCommand(input);
        }
    }

}
