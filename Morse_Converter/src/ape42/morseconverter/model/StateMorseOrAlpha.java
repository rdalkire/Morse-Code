package ape42.morseconverter.model;


/**
 * User indicates whether they want to enter morse code or text.
 * @author Dave Alkire
 */
public class StateMorseOrAlpha implements State {
	
	private static final String CMD_MORSE = "M";
	private static final String CMD_TEXT = "T";
	private CharSequence result;
    private Core core;
	
	public StateMorseOrAlpha(Core core) {
	    this.core = core;
    }

    @Override
	public CharSequence getHelp() {
		return "Type T if you want to translate Text into Morse Code, \r\n"
				+ "or M if you want to translate Morse Code into alpha-numeric";
	}

	@Override
	public CharSequence getPrompt() {
		return "(T)ext-to-Morse, or (M)orse-to-Text";
	}

	@Override
	public CharSequence getResult() {
		return result;
	}

	@Override
	public CharSequence getValidationMessage() {
		return "Only T or M please.";
	}

	
	/**
	 * In this case processData is non-op.  In this state we're processing
	 * commands only, not data.
	 * 
	 * @see ape42.morseconverter.model.State#processData(java.lang.String)
	 */
	@Override
	public void processData(String input) { }

    @Override
    public void setResult(CharSequence result) {
        this.result = result;
    }

	@Override
	public boolean isValidCommand(String input) {
		return CMD_TEXT.equalsIgnoreCase(input)||
				CMD_MORSE.equalsIgnoreCase(input);
	}

	@Override
	public void processCommand(String input) {
		if(CMD_TEXT.equalsIgnoreCase(input)){
			result = "Now in Text-to-Morse mode.";
			State newState = new StateTextInput(core);
			newState.setResult(result);
			core.getContext().setState(newState);
		}else{
			result = "Now in Morse-to-Text mode.";
			State newState = new StateMorseInput(core);
			newState.setResult(result);
			core.getContext().setState(newState );
		}
	}

	@Override
	public boolean isValidData(String input) {
		return false; // Only commands are processed in this state, not data.
	}
	
}
