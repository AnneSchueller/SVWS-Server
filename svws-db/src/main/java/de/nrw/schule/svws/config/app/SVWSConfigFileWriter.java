package de.nrw.schule.svws.config.app;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;

import de.nrw.schule.svws.base.shell.CommandLineOption;
import de.nrw.schule.svws.base.shell.CommandLineParser;
import de.nrw.schule.svws.config.SVWSKonfiguration;
import de.nrw.schule.svws.db.DBDriver;

/**
 * Diese Klasse stellt ein Kommandozeilenprogramm zum Erzeugen einer SVWS-Konfiguration mit Default-Einstellungen
 * zur Verfügung.
 */
public class SVWSConfigFileWriter {

	/// Der Parser für die Kommandozeile
	private static CommandLineParser cmdLine;
	
	
	/**
	 * Die Main-Methode zum Starten des Kommandozeilenprogramms zum Erzeugen einer SVWS-Konfiguration mit 
	 * Default-Einstellungen.
	 * 
	 * @param args   enthält die Kommandozeilen-Parameter zur Angabe von Konfigurationsoptionen 
	 */
	public static void main(String[] args) {
		
		cmdLine = new CommandLineParser(args);
		try {
			cmdLine.addOption(new CommandLineOption("o", "output", true, "Der Dateiname mit Pfad von der anzulegenden Konfigurationsdatei"));		
			cmdLine.addOption(new CommandLineOption("c", "clientpath", true, "Der Pfad, in welchem der Server die Dateien für den Webclient sucht"));		
			cmdLine.addOption(new CommandLineOption("l", "loggingpath", true, "Der Pfad, in welchem der Server Log-Dateien anlegt"));		
			cmdLine.addOption(new CommandLineOption("t", "temppath", true, "Der Pfad, in welchem temporäre Dateien angelegt werden"));		
			cmdLine.addOption(new CommandLineOption("k", "keystorepath", true, "Der Pfad, in welchem der Server den Keystore für die TLS-Verbindung sucht"));
			cmdLine.addOption(new CommandLineOption("w", "keystorepassword", true, "Das Kennwort, welches für den Keystore für die TLS-Verbindung genutzt wird"));
			cmdLine.addOption(new CommandLineOption("d", "dbms", true, "Das DBMS, welches vom SVWS-Server verwendet wird"));
			cmdLine.addOption(new CommandLineOption("h", "dbhost", true, "Der Name des Hosts, auf welchem der Datenbank-Server betrieben wird (Default: localhost)."));
			cmdLine.addOption(new CommandLineOption("j", "dbport", true, "Der Port des Datenbank-Server (Default: 3306 für MariaDB/MySQL, 1433 für MS SQL Server)."));
			cmdLine.addOption(new CommandLineOption("n", "noschema", false, "Gibt an, das kein Schema-Eintrag in der Konfiguration gespeichert werden soll."));
			cmdLine.addOption(new CommandLineOption("s", "schema", true, "Der Name des Datenbankschemas"));
			cmdLine.addOption(new CommandLineOption("u", "user", true, "Der Benutzername für den Administrative Zugriff auf das Datenbankschema"));
			cmdLine.addOption(new CommandLineOption("p", "password", true, "Das zum Benutzernamen gehörende Kennwort"));
	    	
		    // Lese die Kommandozeilenparameter ein und setze ggf. Default-Werte, falls diese nicht angegeben sind. 
		    String outputFile = cmdLine.getValue("o", "svwsconfig.json");
			String clientPath = cmdLine.getValue("c");
			String loggingPath = cmdLine.getValue("l");
			String tempPath = cmdLine.getValue("t");
			String keystorePath = cmdLine.getValue("k");
			String keystorePassword = cmdLine.getValue("w");
			DBDriver dbms = DBDriver.fromString(cmdLine.getValue("d", ""));
			String dbLocation = cmdLine.getValue("h", "localhost");
			String dbPortStr = cmdLine.getValue("j");
			int dbPort = NumberUtils.toInt(dbPortStr, ((dbms != null) && dbms.equals(DBDriver.MSSQL)) ? 1433 : 3306);
			boolean noSchema = cmdLine.isSet("n");
			String schema = cmdLine.getValue("s");
			String schemaUser = cmdLine.getValue("u");
			String schemaPassword = cmdLine.getValue("p");

			// Erstelle die Default-Konfiguration
			SVWSKonfiguration config = SVWSKonfiguration.getDefault(clientPath, loggingPath, tempPath, keystorePath, keystorePassword, 
					dbms, dbLocation, dbPort, noSchema, schema, schemaUser, schemaPassword);
			
			// Schreibe die Konfiguration in die angegeben Datei
			config.setFilename(outputFile);
			SVWSKonfiguration.write();
		} catch (NumberFormatException | IOException e) {
			cmdLine.printOptionsAndExit(1, e.getMessage());
		}
	}

}
