package ape42.morseconverter.model;


/**
 * This is for when the user wants to enter text to be translated into morse 
 *  code.
 * @author Dave Alkire
 */
public class StateTextInput implements State {

    private CharSequence result;
    private boolean valid;
    private Core core;
    
	public StateTextInput(Core core) {
        this.core = core;
    }

    @Override
	public CharSequence getHelp() {
		return "Enter a line of text to be translated to morse code.  \r\n"
		        + "Only letters and numbers - "
		        + "everything else will be ignored.\r\n"
		        + "Or type 'M' only to switch to morse-input.";
	}

	@Override
	public CharSequence getPrompt() {
		return "Text";
	}

	/** (This one doesn't really apply) */
	@Override
	public CharSequence getValidationMessage() {
		return "Some normal text please.";
	}

	@Override
	public CharSequence getResult() {
		return result;
	}

	@Override
	public boolean isInputValid() {
		return valid;
	}

	@Override
	public void process(String input, Context context) {
	    
	    if(Core.isHlpQry(input)){
            result = getHelp();
        }else if("M".trim().equalsIgnoreCase(input)){
            result = "Switching to morse input.";
            State newState = new StateMorseInput(core);
            newState.setResult( result );
            context.setState( newState );
        }else{
            StringBuilder stringBuild = new StringBuilder();
            String[] words = input.toUpperCase().split("\\s");
            for(String word: words){
                char[] chars = word.toCharArray();
                for(char ch: chars){
                    String key = Character.toString(ch);
                    String mCode = core.getAlphaToMorse().get(key);
                    if( mCode != null) stringBuild.append( mCode + " " );
                }
                stringBuild.append(" ");
            }
            result = stringBuild;
        }
	}

	/* (not really applicable) */
	@Override
	public void validateInput(String input) {
	    valid = !( "".equals(input.trim()) || input == null );
	}

    @Override
    public void setResult(CharSequence result) {
        this.result = result;
    }

}
