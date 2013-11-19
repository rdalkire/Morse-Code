package ape42.morseconverter.model;


/**
 * This is for when the user wants to enter text to be translated into morse 
 *  code.
 * @author Dave Alkire
 */
public class StateTextInput implements State {

    private static final String CMD_MORSE = "M";
	private CharSequence result;
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
	public boolean isValidData(String input) {
		return !( "".equals(input.trim()) || input == null ); 
	}

	@Override
	public void processData(String input) {
	    
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

    @Override
    public void setResult(CharSequence result) {
        this.result = result;
    }

	@Override
	public boolean isValidCommand(String input) {
		return CMD_MORSE.trim().equalsIgnoreCase(input);
	}

	@Override
	public void processCommand(String input) {
		if(CMD_MORSE.trim().equalsIgnoreCase(input)){
			result = "Switching to morse input.";
			State newState = new StateMorseInput(core);
			newState.setResult( result );
			core.getContext().setState( newState );
		}
	}

}
