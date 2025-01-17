package de.nrw.schule.svws.base.shell;

/**
 * Dies Ausfzählung spezifiziert den genauen Typ der CommandLineEcxeption
 */
public enum CommandLineExceptionType {
	
	/** Gibt an, dass die Option in der Kommandozeile unbekannt ist. */
	UNKNOWN_OPTION("The command line option is unknown."),
	
	/** Gibt an, dass die kurze Bezeichnung für die Kommandozeilen-Option bereits verwendet wurde. */
	SHORT_TAG_ALREADY_DEFINED("The short tag of the command line option was already defined."),
	
	/** Gibt an, dass die lange Bezeichnung für die Kommandozeilen-Option bereits verwendet wurde. */
	LONG_TAG_ALREADY_DEFINED("The long tag of the command line option was already defined.");
	
	
	/// Die Nachricht, die bei der Exception angezeigt wird
	private String message;
	
	
	/**
	 * Erzeugt einen neuen CommandLineEcxeption-Typ mit der angegebenen Nachricht.
	 * 
	 * @param message   die Nachricht, die bei diesem Typ angezeigt werden soll
	 */
	private CommandLineExceptionType(String message) {
		this.message = message;
	}
	
	
	/**
	 * Gibt die anzuzeigende Nachricht zurpck.
	 * 
	 * @return die anzuzeigende Nachricht
	 */
	public String getMessage() {
		return message;
	}
	
}
