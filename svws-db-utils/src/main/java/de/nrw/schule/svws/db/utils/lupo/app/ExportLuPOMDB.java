package de.nrw.schule.svws.db.utils.lupo.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import de.nrw.schule.svws.base.shell.CommandLineException;
import de.nrw.schule.svws.base.shell.CommandLineOption;
import de.nrw.schule.svws.base.shell.CommandLineParser;
import de.nrw.schule.svws.config.SVWSKonfiguration;
import de.nrw.schule.svws.core.logger.LogConsumerConsole;
import de.nrw.schule.svws.core.logger.Logger;
import de.nrw.schule.svws.db.Benutzer;
import de.nrw.schule.svws.db.DBConfig;
import de.nrw.schule.svws.db.DBDriver;
import de.nrw.schule.svws.db.utils.lupo.mdb.LupoMDB;

/**
 * Diese Klasse stellt eine Kommandozeilen-Anwendung zum Erstellen einer neuen 
 * leeren LuPO-MDB-Datenbank aus einem SVWS-Schema zur Verfügung.
 */
public class ExportLuPOMDB {
	

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
	 * 
	 *   
	 * @param args  die Optionen für die Codegenerierung, @see options
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.addConsumer(new LogConsumerConsole());
		
		// Lese die Kommandozeilenparameter ein
		cmdLine = new CommandLineParser(args);
		try {
			cmdLine.addOption(new CommandLineOption("j", "ja", false, "Beantwortet alle Fragen beim Import automatisch mit \"Ja\""));
			cmdLine.addOption(new CommandLineOption("k", "jahrgang", true, "Der Jahrgang, für den die LuPO-Datei erzeugt werden soll."));
			cmdLine.addOption(new CommandLineOption("f", "file", true, "Der vollständige Dateiname, wo die LuPO-Datei erzeugt wird"));
			cmdLine.addOption(new CommandLineOption("cp", "configPath", true, "Gibt den Pfad zu der SVWS-Konfigurationsdatei an, wenn diese nicht an einem Standardort liegt."));
			cmdLine.addOption(new CommandLineOption("sd", "srcDrv", true, "Der Treiber für die Quell-DB (\"MDB\", \"MSSQL\", \"MYSQL\", \"MARIA_DB\" oder \"SQLITE\")"));	
			cmdLine.addOption(new CommandLineOption("sl", "srcLoc", true, "Der Ort, wo die Quell-DB zu finden ist (Der Pfad einer Datei oder der Ort im Netzwerk, z.B. \"localhost\" )"));	
			cmdLine.addOption(new CommandLineOption("ss", "srcDB", true, "Der Schema-Name für die Quell-DB (bei \\\"MDB\\\" und \\\"SQLITE\\\" nicht benötigt)"));	
			cmdLine.addOption(new CommandLineOption("su", "srcUser", true, "Der DB-Benutzer für die Quell-DB"));	
			cmdLine.addOption(new CommandLineOption("sp", "srcPwd", true, "Das DB-Kennwort für die Quell-DB"));	
			
		    // Lese den Namen für der LuPO-Datei ein und öffne die Datei
			String lupofilename = cmdLine.getValue("f", "Laufbahnplanung.lup");
			if (Files.exists(Paths.get(lupofilename))) {
				try (Scanner scan = new Scanner(System.in)) {
					// Frage ggf. nach, ob die bestehende LuPO-Datei gelöscht und überschrieben werden soll
					boolean antwort_ja = cmdLine.isSet("j");
					if (!antwort_ja) {
						antwort_ja = konsoleFrageJaNein("Die LuPO-Datei \"" + lupofilename + "\" wird überschrieben. Dabei gehen alle Daten darin verloren. Fortfahren? (J/N) ", scan);
						if (!antwort_ja) {
							cmdLine.printOptionsAndExit(2, "Die LuPO-Datei darf nicht überschrieben werden und kann daher nicht neu erzeugt werden. Breche ab.");
							scan.close();
							System.exit(1);
						}
					}
					scan.close();
				}
			}

			// Lade die Konfigurationsdatei für den Datenbankzugriff
			logger.logLn("Lese SVWS Konfiguration ein...");
			SVWSKonfiguration.getFrom(cmdLine.getValue("cp", null));
				
		    // Lese die Optionen für die Quell-Datenbank ein
		    DBDriver srcDrv = DBDriver.fromString(cmdLine.getValue("sd", "MARIA_DB"));
		    if (srcDrv == null)
		    	cmdLine.printOptionsAndExit(3, "Fehlerhafte Angabe bei dem Treiber der Quell-DB");
			String srcLoc = cmdLine.getValue("sl", "localhost");
			String srcDB = cmdLine.getValue("ss", "svwsdb");
			String srcUser = cmdLine.getValue("su", "svwsadmin");
			String srcPwd = cmdLine.getValue("sp", "svwsadmin");
			DBConfig srcConfig = new DBConfig(srcDrv, srcLoc, srcDB, false, srcUser, srcPwd, true, false);
			Benutzer user = Benutzer.create(srcConfig);

			String jahrgang = cmdLine.getValue("k", "Q2");
			
			// Schreibe die Daten für die neue LuPO-Datei
			LupoMDB lupoMDB = new LupoMDB(lupofilename);
			lupoMDB.logger.copyConsumer(logger);
			lupoMDB.getFromLeistungsdaten(user, jahrgang);
			lupoMDB.exportTo();
		} catch (CommandLineException e) {
			cmdLine.printOptionsAndExit(1, e.getMessage());
		} catch (IOException e) {
			cmdLine.printOptionsAndExit(2, e.getMessage());
		}
	}
	
}
