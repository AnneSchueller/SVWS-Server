package de.nrw.schule.svws.db.utils.app;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import de.nrw.schule.svws.base.shell.CommandLineException;
import de.nrw.schule.svws.base.shell.CommandLineOption;
import de.nrw.schule.svws.base.shell.CommandLineParser;
import de.nrw.schule.svws.config.SVWSKonfiguration;
import de.nrw.schule.svws.core.logger.LogConsumerConsole;
import de.nrw.schule.svws.core.logger.Logger;
import de.nrw.schule.svws.db.DBConfig;
import de.nrw.schule.svws.db.DBDriver;
import de.nrw.schule.svws.db.utils.schema.DBMigrationManager;



/**
 * Diese Klasse stellt eine Kommandozeilen-Anwendung zum Migrieren einer Schild2-LegacyDB
 * in ein SVWS-Server-Schema zur Verfügung.
 */
public class MigrateDB {

	/// Der Parser für die Kommandozeile
	private static CommandLineParser cmdLine;

	/// Der Logger
	private static Logger logger = new Logger();
	
	
	
	/**
	 * Stellt eine einfache Frage auf der Kommandozeile, welche mit Ja oder Nein
	 * auf dem Scanner beantwortet werden kann.
	 * 
	 * @param text   die Frage
	 * @param scan   der Scanner
	 * 
	 * @return die Antwort - true oder false
	 */
	private static boolean konsoleFrageJaNein(String text, Scanner scan) {
		boolean antwort = false;
		System.out.print(text);
		boolean habeAntwort = false;
		while (!habeAntwort) {
			String input = scan.nextLine();
			if (input == null)
				continue;
			habeAntwort = true;
			switch (input.toUpperCase()) {
				case "JA":
				case "J":
				case "YES":
				case "Y":
					antwort = true;
					break;
				case "NEIN":
				case "N":
				case "NO":
					antwort = false;
					break;
				default:
					habeAntwort = false;
			}
		}
		return antwort;
	}

	
	/**
	 * Liest von der Kommandozeile den Namen der CVS-Datei ein, die die Schemaspezifikation enthält. Außerdem 
	 * wird der Pfad zu dem Ordner eingelesen, in dem der generierte Code abgelegt wird. Anschließend wird die 
	 * Codegenerierung angestoßen.
	 *   
	 * @param args  die Optionen für die Codegenerierung, @see options
	 */
	public static void main(String[] args) {
		logger.addConsumer(new LogConsumerConsole());
		
		// Lese die Kommandozeilenparameter ein
		cmdLine = new CommandLineParser(args);
		try {
			cmdLine.addOption(new CommandLineOption("r", "revision", true, "Gibt die maximale Revision an, bis zu der die migrierte DB maximal aktualsiert wird (Default: -1 für so weit wie möglich)")); 
			cmdLine.addOption(new CommandLineOption("j", "ja", false, "Beantwortet den Hinweise auf das notwendige Löschen der Ziel-DB automatisch mit \"Ja\""));
			cmdLine.addOption(new CommandLineOption("d", "developerMode", false, "Führt den Import im Developer-Mode durch. Dies bedeutet, dass die Datenbank nur für Testzwecke geeignet ist und entsprechend gekennzeichnet wird."));
			cmdLine.addOption(new CommandLineOption("n", "schulNummer", true, "Für Schildzentral-Quell-DBs: Gibt die Schulnummer an, für welche Daten aus der Quelldatenbank migriert werden sollen.")); 
			cmdLine.addOption(new CommandLineOption("cp", "configPath", true, "Gibt den Pfad zu der SVWS-Konfigurationsdatei an, wenn diese nicht an einem Standardort liegt."));
			cmdLine.addOption(new CommandLineOption("sd", "srcDrv", true, "Der Treiber für die Quell-DB (\"MDB\", \"MSSQL\", \"MYSQL\", \"MARIA_DB\" oder \"SQLITE\")"));	
			cmdLine.addOption(new CommandLineOption("sl", "srcLoc", true, "Der Ort, wo die Quell-DB zu finden ist (Der Pfad einer Datei oder der Ort im Netzwerk, z.B. \"localhost\" )"));	
			cmdLine.addOption(new CommandLineOption("ss", "srcDB", true, "Der Schema-Name für die Quell-DB (bei \\\"MDB\\\" und \\\"SQLITE\\\" nicht benötigt)"));	
			cmdLine.addOption(new CommandLineOption("su", "srcUser", true, "Der DB-Benutzer für die Quell-DB"));	
			cmdLine.addOption(new CommandLineOption("sp", "srcPwd", true, "Das DB-Kennwort für die Quell-DB"));	
			cmdLine.addOption(new CommandLineOption("td", "tgtDrv", true, "Der Treiber für die Ziel-DB (\"MDB\", \"MSSQL\", \"MYSQL\", \"MARIA_DB\" oder \"SQLITE\")"));
			cmdLine.addOption(new CommandLineOption("tl", "tgtLoc", true, "Der Ort, wo die Ziel-DB zu finden ist (Der Pfad einer Datei oder der Ort im Netzwerk, z.B. \"localhost\" )"));
			cmdLine.addOption(new CommandLineOption("ts", "tgtDB", true, "Der Schema-Name für die Ziel-DB (bei \\\"MDB\\\" und \\\"SQLITE\\\" nicht benötigt)"));	
			cmdLine.addOption(new CommandLineOption("tu", "tgtUser", true, "Der DB-Benutzer für die Ziel-DB"));
			cmdLine.addOption(new CommandLineOption("tp", "tgtPwd", true, "Das DB-Kennwort für die Ziel-DB"));
			cmdLine.addOption(new CommandLineOption("tr", "tgtRootPwd", true, "Das DB-Root-Kennwort für die Ziel-DB (nur bei \"MSSQL\", \"MYSQL\", \"MARIA_DB\")"));
	    
			try (Scanner scan = new Scanner(System.in)) {
				// Frage ggf. nach, ob die Quelldatenbank aufgeräumt werden soll
				boolean hinweis_akzpetiert = cmdLine.isSet("j");
				if (!hinweis_akzpetiert) {
					hinweis_akzpetiert = konsoleFrageJaNein("Die Zieldatenbank muss für eine problemlose Migration neu angelegt werden. Dabei gehen alle Daten in der Ziel-Datenbank verloren. Fortfahren? (J/N) ", scan);
					if (!hinweis_akzpetiert) {
						cmdLine.printOptionsAndExit(2, "Die Zieldatenbank muss für eine problemlose Migration neu angelegt werden. Breche die Migration ab.");
					}
				}
				scan.close();
			}
			
			// Lade die Konfigurationsdatei für den Datenbankzugriff
			SVWSKonfiguration.getFrom(cmdLine.getValue("cp", null));
				
			int maxUpdateRevision = NumberUtils.toInt(cmdLine.getValue("r", "-1"), -1);
			boolean devMode = cmdLine.isSet("d");
			
			Integer schulNr = NumberUtils.toInt(cmdLine.getValue("n", "-1"), -1);
			if (schulNr == -1)
				schulNr = null;
			
		    // Lese die Optionen für die Quell-Datenbank ein
		    DBDriver srcDrv = DBDriver.fromString(cmdLine.getValue("sd", "MDB"));
		    if (srcDrv == null)
		    	cmdLine.printOptionsAndExit(3, "Fehlerhafte Angabe bei dem Treiber der Quell-DB");
			String srcLoc = cmdLine.getValue("sl", "SchILD2000n.mdb");
			String srcDB = cmdLine.getValue("ss", "PUBLIC");
			String srcUser = cmdLine.getValue("su", "svwsadmin");
			String srcPwd = cmdLine.getValue("sp", "svwsadmin");
			DBConfig srcConfig = new DBConfig(srcDrv, srcLoc, srcDB, false, srcUser, srcPwd, true, false);
	
		    // Lese die Optionen für die Ziel-Datenbank ein
		    DBDriver tgtDrv = DBDriver.fromString(cmdLine.getValue("td", "MARIA_DB"));
		    if (tgtDrv == null)
		    	cmdLine.printOptionsAndExit(4, "Fehlerhafte Angabe bei dem Treiber der Ziel-DB");
			String tgtLoc = cmdLine.getValue("tl", "localhost");
			String tgtDB = cmdLine.getValue("ts", "svwsschema");
			String tgtUser = cmdLine.getValue("tu", "svwsadmin");
			String tgtPwd = cmdLine.getValue("tp", "svwsadmin");
			String tgtRootPwd = cmdLine.getValue("tr", "svwsadmin");
			DBConfig tgtConfig = new DBConfig(tgtDrv, tgtLoc, tgtDB, false, tgtUser, tgtPwd, true, true);
	
			// Führe die Migration mithilfe des Schema-Managers durch.
			DBMigrationManager.migrate(srcConfig, tgtConfig, tgtRootPwd, maxUpdateRevision, devMode, schulNr, logger);
		} catch (CommandLineException e) {
			cmdLine.printOptionsAndExit(1, e.getMessage());
		}		
	}

}
