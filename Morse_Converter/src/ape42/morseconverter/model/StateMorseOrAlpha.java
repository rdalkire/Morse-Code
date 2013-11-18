package ape42.morseconverter.model;


/**
 * User indicates whether they want to enter morse code or text.
 * @author Dave Alkire
 */
public class StateMorseOrAlpha implements State {
	
	private CharSequence result;
	private boolean isInputvalid;
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

	@Override
	public boolean isInputValid() {
		return isInputvalid;
	}

	@Override
	public void process(String input, Context context) {
		if(Core.isHlpQry(input)){
			result = getHelp();
		}else if("T".equalsIgnoreCase(input)){
			result = "Now in Text-to-Morse mode.";
			State newState = new StateTextInput(core);
			newState.setResult(result);
			context.setState(newState);
		}else{
			result = "Now in Morse-to-Text mode.";
			State newState = new StateMorseInput(core);
			newState.setResult(result);
			context.setState(newState );
		}
		
	}

	@Override
	public void validateInput(String input) {
		isInputvalid= "T".equalsIgnoreCase(input)||"M".equalsIgnoreCase(input)||
				Core.isHlpQry(input);
	}

    @Override
    public void setResult(CharSequence result) {
        this.result = result;
    }
	
}
