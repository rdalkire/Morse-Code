package ape42.morseconverter.model;

/**
 * So that there can be various kinds of contexts, not just the text console.
 * 
 * @author Dave Alkire
 */
public interface Context {
	void setState(State state);
}
