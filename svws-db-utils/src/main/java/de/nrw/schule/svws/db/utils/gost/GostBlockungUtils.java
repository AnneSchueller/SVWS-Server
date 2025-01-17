package de.nrw.schule.svws.db.utils.gost;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import de.nrw.schule.svws.base.kurs42.Kurs42Import;
import de.nrw.schule.svws.core.adt.Pair;
import de.nrw.schule.svws.core.data.gost.GostBlockungKurs;
import de.nrw.schule.svws.core.data.gost.GostBlockungKursLehrer;
import de.nrw.schule.svws.core.data.gost.GostBlockungRegel;
import de.nrw.schule.svws.core.data.gost.GostBlockungSchiene;
import de.nrw.schule.svws.core.logger.Logger;
import de.nrw.schule.svws.core.types.gost.GostKursart;
import de.nrw.schule.svws.core.types.kursblockung.GostKursblockungRegelParameterTyp;
import de.nrw.schule.svws.core.types.kursblockung.GostKursblockungRegelTyp;
import de.nrw.schule.svws.core.types.schule.Schulform;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockung;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungKurs;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungKurslehrer;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungRegel;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungRegelParameter;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungSchiene;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungZwischenergebnis;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungZwischenergebnisKursSchiene;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungZwischenergebnisKursSchueler;
import de.nrw.schule.svws.db.dto.current.schild.faecher.DTOFach;
import de.nrw.schule.svws.db.dto.current.schild.lehrer.DTOLehrer;
import de.nrw.schule.svws.db.dto.current.schild.schule.DTOEigeneSchule;
import de.nrw.schule.svws.db.dto.current.svws.db.DTODBAutoInkremente;
import de.nrw.schule.svws.db.schema.Schema;
import de.nrw.schule.svws.db.schema.SchemaTabelle;

/**
 * Diese Klasse stellt Hilfs-Methoden rund um Blockungen der gymnasialen Oberstufe zur Verfügung. 
 */
public class GostBlockungUtils {

	/** Funktion zum Bestimmen der nächsten freien ID bei einer Tabelle mit Autoinkrement */
	private static BiFunction<DBEntityManager, SchemaTabelle, Long> getNextID = (DBEntityManager conn, SchemaTabelle tab) -> {
		DTODBAutoInkremente dbID = conn.queryByKey(DTODBAutoInkremente.class, tab.name());
		return dbID == null ? 1 : dbID.MaxID + 1;
	};


	/**
	 * Importiert eine Kurs-42-Blockung in das Datenbank-Schema, welches durch die übergebene
	 * Verbindung festgelegt ist.
	 *    
	 * @param conn     die Datenbank-Verbindung.
	 * @param logger   der Logger für Rückmeldungen zum Import-Prozess
	 * @param path     der Pfad, wo sich die Kurs-42-Import-Dateien befinden
	 * 
	 * @return true im Erfolgsfall und false im Fehlerfall 
	 * 
	 * @throws IOException   Falls der Zugriff auf die Kurs42-Dateien fehlschlägt 
	 */
	public static boolean importKurs42(DBEntityManager conn, Logger logger, Path path) throws IOException {
		// Lese zunächst die Informationen zur Schule aus der SVWS-Datenbank und überprüfe die Schulform
		logger.logLn("-> Lese Informationen zu der Schule ein...");
		logger.modifyIndent(2);
		DTOEigeneSchule schule = conn.querySingle(DTOEigeneSchule.class);
		if (schule == null) {
			logger.logLn("[Fehler] - Konnte die Informationen zur Schule nicht aus der Datenbank lesen");
			return false;
		}
		Schulform schulform = schule.Schulform;
		if ((schulform == null) || (schulform.daten == null) || (!schulform.daten.hatGymOb)) {
			logger.logLn("[Fehler] - Die Schulform hat keine gymnasiale Oberstufe oder konnte nicht bestimmt werden");
			return false;
		}
		logger.logLn("[OK]");
		logger.modifyIndent(-2);
		// Bestimme nun die Lehrkräfte, die an der Schule tätig sind.
		logger.logLn("-> Bestimme die Lehrkräfte der Schule aus der Datenbank...");
		logger.modifyIndent(2);
		List<DTOLehrer> listeLehrer = conn.queryAll(DTOLehrer.class);
		if ((listeLehrer == null) || (listeLehrer.size() == 0)) {
			logger.logLn("[Fehler] - Konnte die Liste der Lehrer nicht einlesen");
			return false;
		}
		Map<String, Long> mapLehrer = listeLehrer.stream().collect(Collectors.toMap(l -> l.Kuerzel, l -> l.ID)); 
		logger.logLn("[OK]");
		logger.modifyIndent(-2);
		// Bestimme die Fächer, die an der Schule vorhanden sind.
		logger.logLn("-> Bestimme die Fächer der Schule aus der Datenbank...");
		logger.modifyIndent(2);
		List<DTOFach> listeFaecher = conn.queryNamed("DTOFach.istoberstufenfach", true, DTOFach.class); 
		if ((listeFaecher == null) || (listeFaecher.size() == 0)) {
			logger.logLn("[Fehler] - Konnte die Liste der Fächer der Oberstufe nicht einlesen");
			return false;
		}
		Map<Long, String> mapFaecher = listeFaecher.stream().collect(Collectors.toMap(f -> f.ID, f -> f.Kuerzel)); 
		logger.logLn("[OK]");
		logger.modifyIndent(-2);
		// Lese nun die Daten von Kurs 42 aus dem angegeben Pfad ein
		logger.logLn("-> Lese die Kurs 42 - Export - Textdateien ein...");
		logger.modifyIndent(2);
		Kurs42Import k42 = null;
		try {
			k42 = new Kurs42Import(path, schule.SchulNr, schule.AnzahlAbschnitte == 4, mapLehrer);		
		} catch (IOException e) {
			logger.logLn("[Fehler] - Fehler beim Einlesen des Kurs42-Text-Exports: ");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.logLn(sw.toString());
			return false;
		}
		logger.logLn("[OK]");
		logger.modifyIndent(-2);
		try {
			logger.logLn("-> Schreibe die Blockungsdaten...");
			logger.modifyIndent(2);
			conn.transactionBegin();
			// Bestimme zunächst die IDs für die neue Blockung, die Schienen, die Kurse, die Regeln und das Ergebnis
			Long blockungID = getNextID.apply(conn, Schema.tab_Gost_Blockung);
			Long schienenID = getNextID.apply(conn, Schema.tab_Gost_Blockung_Schienen);
			Long kursID = getNextID.apply(conn, Schema.tab_Gost_Blockung_Kurse);
			Long regelID = getNextID.apply(conn, Schema.tab_Gost_Blockung_Regeln);
			Long ergebnisID = getNextID.apply(conn, Schema.tab_Gost_Blockung_Zwischenergebnisse);
			// Lege die Blockung und ein zugehöriges Ergebnis an
			DTOGostBlockung blockung = new DTOGostBlockung(blockungID, k42.name + " (import)", k42.abiturjahrgang, k42.halbjahr, false);
			DTOGostBlockungZwischenergebnis erg = new DTOGostBlockungZwischenergebnis(ergebnisID, blockungID, false, false);
			conn.transactionPersist(blockung);
			conn.transactionPersist(erg);
			// Lege die Schienen an
			for (GostBlockungSchiene schiene : k42.schienen) {
				conn.transactionPersist(new DTOGostBlockungSchiene(schienenID + schiene.id, 
						blockungID, schiene.nummer, schiene.bezeichnung, schiene.wochenstunden));
			}
			// Lege die Kurse an
			for (GostBlockungKurs kurs : k42.kurse) {
				// Prüfe, ob die Fach ID in Ordnung ist
				if (mapFaecher.get(kurs.fach_id) == null) {
					conn.transactionRollback();
					logger.logLn("[Fehler] - Fach-ID " + kurs.fach_id + " ist in der Datenbank nicht bekannt");
					return false;
				}
				conn.transactionPersist(new DTOGostBlockungKurs(kursID + kurs.id, blockungID, kurs.fach_id, 
						GostKursart.fromID(kurs.kursart), kurs.nummer, kurs.istKoopKurs, kurs.anzahlSchienen, kurs.wochenstunden));
				for (GostBlockungKursLehrer lehrer : kurs.lehrer)
					conn.transactionPersist(new DTOGostBlockungKurslehrer(kursID + kurs.id,
							lehrer.id, lehrer.reihenfolge, lehrer.wochenstunden));
			}
			// Lege die Regeln an
			for (GostBlockungRegel regel : k42.regeln) {
				GostKursblockungRegelTyp typ = GostKursblockungRegelTyp.fromTyp(regel.typ); 
				conn.transactionPersist(new DTOGostBlockungRegel(regelID + regel.id, blockungID, typ));
				for (int i = 0; i < regel.parameter.size(); i++) {
					Long param = regel.parameter.get(i);
					GostKursblockungRegelParameterTyp paramTyp = typ.getParamType(i);
					param = switch (paramTyp) {
						case KURSART -> param;
						case SCHIENEN_NR -> param;
						case KURS_ID -> kursID + param;
						case SCHUELER_ID -> param;
						default -> param;
					};
					conn.transactionPersist(new DTOGostBlockungRegelParameter(regelID + regel.id, i, param));
				}
			}
			// Schreibe die Kurs-Schienen-Zuordnungen
			for (Pair<Long, Long> zuordnung : k42.zuordnung_kurs_schiene)
				conn.transactionPersist(new DTOGostBlockungZwischenergebnisKursSchiene(ergebnisID, 
						kursID + zuordnung.a, schienenID + zuordnung.b));
			// Schreibe die Kurs-Schüler-Zuordnungen
			for (Pair<Long, Long> zuordnung : k42.zuordnung_kurs_schueler)
				conn.transactionPersist(new DTOGostBlockungZwischenergebnisKursSchueler(ergebnisID, 
						kursID + zuordnung.a, zuordnung.b));
			if (!conn.transactionCommit()) {
				logger.logLn("[Fehler]");
				logger.modifyIndent(-2);
				return false;
			}
			logger.logLn("[OK]");
			logger.modifyIndent(-2);
			return true;
		} catch (@SuppressWarnings("unused") Exception exception) {
			return false;
		} finally {
			conn.transactionRollback();
		}
	}
	
}
