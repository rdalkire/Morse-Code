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

	boolean isValidData(String input);

	void processData(String input);
	
    void setResult(CharSequence result);
	boolean isValidCommand(String input);
	void processCommand(String input);

}
