package de.nrw.schule.svws.transpiler;


/**
 * The exception class used by the transpiler for all errors resulting in
 * an unsuccessful transpiler run.   
 */
public class TranspilerException extends RuntimeException {

	/** The UID of this exception */
	private static final long serialVersionUID = -7503170144071053867L;

	
	/**
	 * Create a new transpiler error exception with the specified message
	 * 
	 * @param message   the message of the transpiler error
	 */
	public TranspilerException(String message) {
		super(message);
	}

}
