/**
 * 
 */
package ape42.morseconverter.model;

/**
 * Ensures consistency per mode.
 * 
 * @author Dave Alkire
 */
public interface State {

	CharSequence getHelp();
	CharSequence getPrompt();
	CharSequence getValidationMessage();
	CharSequence getResult();

	boolean isInputValid();

	void process(String input, Context context);

	void validateInput(String input);
    void setResult(CharSequence result);

}
