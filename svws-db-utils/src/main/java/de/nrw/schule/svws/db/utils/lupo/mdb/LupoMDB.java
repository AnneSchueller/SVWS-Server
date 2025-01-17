package de.nrw.schule.svws.db.utils.lupo.mdb;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.DateTimeType;

import de.nrw.schule.svws.core.data.gost.AbiturFachbelegung;
import de.nrw.schule.svws.core.data.gost.AbiturFachbelegungHalbjahr;
import de.nrw.schule.svws.core.data.gost.Abiturdaten;
import de.nrw.schule.svws.core.data.gost.GostFach;
import de.nrw.schule.svws.core.data.gost.GostLeistungen;
import de.nrw.schule.svws.core.data.schueler.Sprachbelegung;
import de.nrw.schule.svws.core.data.schueler.Sprachendaten;
import de.nrw.schule.svws.core.logger.Logger;
import de.nrw.schule.svws.core.types.Note;
import de.nrw.schule.svws.core.types.SchuelerStatus;
import de.nrw.schule.svws.core.types.fach.ZulaessigesFach;
import de.nrw.schule.svws.core.types.gost.GostBesondereLernleistung;
import de.nrw.schule.svws.core.types.gost.GostHalbjahr;
import de.nrw.schule.svws.core.types.gost.GostKursart;
import de.nrw.schule.svws.core.types.gost.GostLaufbahnplanungFachkombinationTyp;
import de.nrw.schule.svws.core.types.schule.Schulform;
import de.nrw.schule.svws.db.Benutzer;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.gost.DTOGostJahrgangBeratungslehrer;
import de.nrw.schule.svws.db.dto.current.gost.DTOGostJahrgangFachkombinationen;
import de.nrw.schule.svws.db.dto.current.gost.DTOGostJahrgangFaecher;
import de.nrw.schule.svws.db.dto.current.gost.DTOGostJahrgangsdaten;
import de.nrw.schule.svws.db.dto.current.gost.DTOGostSchueler;
import de.nrw.schule.svws.db.dto.current.gost.DTOGostSchuelerFachbelegungen;
import de.nrw.schule.svws.db.dto.current.schild.faecher.DTOFach;
import de.nrw.schule.svws.db.dto.current.schild.gost.DTOFaecherNichtMoeglicheKombination;
import de.nrw.schule.svws.db.dto.current.schild.klassen.DTOKlassen;
import de.nrw.schule.svws.db.dto.current.schild.klassen.DTOKlassenLeitung;
import de.nrw.schule.svws.db.dto.current.schild.lehrer.DTOLehrer;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchueler;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchuelerLernabschnittsdaten;
import de.nrw.schule.svws.db.dto.current.schild.schule.DTOJahrgang;
import de.nrw.schule.svws.db.dto.current.schild.schule.DTOSchuljahresabschnitte;
import de.nrw.schule.svws.db.dto.current.svws.db.DTODBAutoInkremente;
import de.nrw.schule.svws.db.utils.data.Schule;
import de.nrw.schule.svws.db.utils.gost.GostSchueler;
import jakarta.persistence.TypedQuery;


/**
 * Diese Klasse bietet Methoden zum Zugriff auf LuPO-Dateien im
 * Access-MDB-Format. 
 */
public class LupoMDB {
	
	/** Der Logger für die Zugriff auf diese LuPO-Datei */
	public final Logger logger = new Logger();

	/** der Dateiname der LuPO-MDB-Datei */
	private final String filename;

	private List<ABPVersion> versionen = null;
	private List<ABPSchuldaten> schuldaten = null;
	private Map<String, ABPFachgruppen> fachgruppen = null;
	private Map<String, ABPFaecher> faecher = null;
	private List<ABPKursarten> kursarten = null;
	private List<ABPLehrer> lupoBenutzer = null;
	private List<ABPNichtMoeglAbiFachKombi> nichtMoeglicheKombinationen = null;
	private List<ABPSchueler> schueler = null;
	private List<ABPSchuelerFaecher> schuelerFaecher = null;
	private List<ABPSchuelerFaecherBasisSicherung> schuelerFaecherBasisSicherungen = null;
	private List<ABPSchuelerFaecherSicherung> schuelerFaecherSicherungen = null;
	private List<ABPSchuelerFehlermeldungen> fehlermeldungen = null;
	private List<ABPSchuelerSprachenfolge> schuelerSprachenfolge = null;
	
	/**
	 * Erzeugt eine neue Instanz für eine LuPO-MDB-Datenbankdatei. 
	 * Es findet zunächst kein lesender oder schreibender Zugriff auf die Datei statt.
	 * 
	 * @param filename   der Dateiname der LuPO-MDB-Datenbankdatei
	 */
	public LupoMDB(String filename) {
		if (filename == null)
			throw new NullPointerException("LuPO-MDB-Dateiname muss angegeben werden.");
		this.filename = filename;
	}
	
	
	/**
	 * Kopiert alle Daten von dem anderen LupoMDB-Objekt.
	 *  
	 * @param other   das andere LupoMDB-Objekt.
	 */
	public void copyFrom(LupoMDB other) {
		versionen = other.versionen;
		schuldaten = other.schuldaten;
		fachgruppen = other.fachgruppen;
		faecher = other.faecher;
		kursarten = other.kursarten;
		lupoBenutzer = other.lupoBenutzer;
		nichtMoeglicheKombinationen = other.nichtMoeglicheKombinationen;
		schueler = other.schueler;
		schuelerFaecher = other.schuelerFaecher;
		schuelerFaecherBasisSicherungen = other.schuelerFaecherBasisSicherungen;
		schuelerFaecherSicherungen = other.schuelerFaecherSicherungen;
		fehlermeldungen = other.fehlermeldungen;
		schuelerSprachenfolge = other.schuelerSprachenfolge;
	}


	/**
	 * Importiert die Daten aus einer bestehenden LuPO-Datei
	 *  
	 * @throws IOException   wird im Falle eines Fehlers beim Datenbank-Zugriff
	 *                       erzeugt.
	 */
	public void importFrom() throws IOException {
		try (Database db = DatabaseBuilder.open(new File(filename))) {
			db.setDateTimeType(DateTimeType.LOCAL_DATE_TIME);
			versionen = ABPVersion.read(db);
			schuldaten = ABPSchuldaten.read(db);
			fachgruppen = ABPFachgruppen.read(db);
			faecher = ABPFaecher.read(db);
			kursarten = ABPKursarten.read(db);
			lupoBenutzer = ABPLehrer.read(db);
			nichtMoeglicheKombinationen = ABPNichtMoeglAbiFachKombi.read(db);
			schueler = ABPSchueler.read(db);
			schuelerFaecher = ABPSchuelerFaecher.read(db);
			schuelerFaecherBasisSicherungen = ABPSchuelerFaecherBasisSicherung.read(db);
			schuelerFaecherSicherungen = ABPSchuelerFaecherSicherung.read(db);
			fehlermeldungen = ABPSchuelerFehlermeldungen.read(db);
			schuelerSprachenfolge = ABPSchuelerSprachenfolge.read(db);
		}
	}


	/**
	 * Exportiert die Daten in die angegebene LuPO-Datei
	 *
	 * @throws IOException   wird im Falle eines Fehlers beim Datenbank-Zugriff
	 *                       erzeugt.
	 */
	public void exportTo() throws IOException {
		logger.logLn("Schreibe LuPO-Daten in die Datei " + filename);
		logger.modifyIndent(2);
		try (Database db = new DatabaseBuilder(new File(filename))
				.setFileFormat(Database.FileFormat.V2000) 
				.putSummaryProperty("Title", DataType.TEXT, "db1")
				.putSummaryProperty("Author", DataType.TEXT, "SVWS-Server-Team")
				.putSummaryProperty("Company", DataType.TEXT, "NRW")
				.create()) {
			db.setDateTimeType(DateTimeType.LOCAL_DATE_TIME);
			ABPVersion.write(db, versionen);
			ABPSchuldaten.write(db, schuldaten);
			ABPFachgruppen.write(db, fachgruppen);
			ABPFaecher.write(db, faecher);
			ABPKursarten.write(db, kursarten);
			ABPLehrer.write(db, lupoBenutzer);
			ABPNichtMoeglAbiFachKombi.write(db, nichtMoeglicheKombinationen);
			ABPSchueler.write(db, schueler);
			ABPSchuelerFaecher.write(db, schuelerFaecher);
			ABPSchuelerFaecherBasisSicherung.write(db, schuelerFaecherBasisSicherungen);
			ABPSchuelerFaecherSicherung.write(db, schuelerFaecherSicherungen);
			ABPSchuelerFehlermeldungen.write(db, fehlermeldungen);
			ABPSchuelerSprachenfolge.write(db, schuelerSprachenfolge);
		}
		logger.logLn("Fertig!");
		logger.modifyIndent(-2);
	}
	

	/**
	 * Schreibt die Daten für eine leere LuPO-MDB in dieses Objekt
	 */
	public void getEmpty() {
		versionen = ABPVersion.getDefault();
		schuldaten = ABPSchuldaten.getDefault();
		fachgruppen = ABPFachgruppen.getDefault();
		faecher = ABPFaecher.getDefault();
		kursarten = ABPKursarten.getDefault();
		lupoBenutzer = ABPLehrer.getDefault();
		nichtMoeglicheKombinationen = ABPNichtMoeglAbiFachKombi.getDefault();
		schueler = ABPSchueler.getDefault();
		schuelerFaecher = ABPSchuelerFaecher.getDefault();
		schuelerFaecherBasisSicherungen = ABPSchuelerFaecherBasisSicherung.getDefault();
		schuelerFaecherSicherungen = ABPSchuelerFaecherSicherung.getDefault();
		fehlermeldungen = ABPSchuelerFehlermeldungen.getDefault();
		schuelerSprachenfolge = ABPSchuelerSprachenfolge.getDefault();
	}


	/**
	 * Schreibt die Daten für den übergebenen Jahrgang der Sekundarstufe II in dieses Objekt
	 *   
	 * @param user       der Datenbank-Benutzer für den SVWS-Datenbankzugriff (siehe {@link Benutzer})
	 * @param jahrgang   der Jahrgang, der in diese LuPO-MDB geschrieben werden soll ("EF", "Q1" oder "Q2").
	 */
	public void getFromLeistungsdaten(Benutzer user, final String jahrgang) {
		try (DBEntityManager conn = user.getEntityManager()) {
			Schule schule = Schule.query(conn);
			if ((jahrgang == null) || ((!"EF".equalsIgnoreCase(jahrgang)) && (!"Q1".equalsIgnoreCase(jahrgang)) && (!"Q2".equalsIgnoreCase(jahrgang)))) {
				logger.logLn("Ungültiger Jahrgang! Erzeuge Daten für eine leere LuPO-Datei...");
				logger.modifyIndent(2);
				getEmpty();
				logger.modifyIndent(-2);
				return;
			}
			logger.logLn("Lese Daten für den Jahrgang " + jahrgang + " aus der SVWS-Datenbank...");
			logger.modifyIndent(2);
			List<DTOFach> dtofaecher = conn.queryAll(DTOFach.class).stream()
					.sorted((f1,f2) -> f1.SortierungSekII == null ? -1 : f2.SortierungSekII == null ? 1 : f2.SortierungSekII - f1.SortierungSekII).collect(Collectors.toList());
			Map<Long, DTOFach> dtoFaecherMap = dtofaecher.stream().collect(Collectors.toMap(f -> f.ID, f -> f));
			List<DTOFaecherNichtMoeglicheKombination> dtoFaecherNichtMoeglicheKombination = conn.queryAll(DTOFaecherNichtMoeglicheKombination.class);
			TypedQuery<DTOSchueler> queryDtoSchueler = conn.query(
					"SELECT s FROM DTOSchueler s JOIN DTOSchuelerLernabschnittsdaten l ON "
					+ "s.ID IS NOT NULL AND s.ID = l.Schueler_ID AND s.Schuljahresabschnitts_ID = l.Schuljahresabschnitts_ID "
					+ "AND (s.Geloescht = null OR s.Geloescht = false) AND s.Status = :status AND l.ASDJahrgang = :jahrgang "
					+ "ORDER BY s.Nachname, s.Vorname", DTOSchueler.class
			);
			List<DTOSchueler> dtoSchueler = queryDtoSchueler
					.setParameter("status", SchuelerStatus.AKTIV)
					.setParameter("jahrgang", jahrgang)
					.getResultList();
			List<Long> schuelerIDs = dtoSchueler.stream().map(s -> s.ID).collect(Collectors.toList());
			Map<Long, GostLeistungen> gostLeistungen = GostSchueler.getLeistungsdaten(conn, schuelerIDs);
			Map<Long, DTOGostSchueler> dtoLupoSchueler = conn.queryAll(DTOGostSchueler.class).stream().collect(Collectors.toMap(s -> s.Schueler_ID, s -> s));
			Map<Long, DTOLehrer> mapLehrer = conn.queryAll(DTOLehrer.class).stream().collect(Collectors.toMap(l -> l.ID, l -> l));
			Map<Long, DTOKlassen> mapKlassen = conn.queryAll(DTOKlassen.class).stream().collect(Collectors.toMap(k -> k.ID, k -> k));
			TypedQuery<DTOSchuelerLernabschnittsdaten> queryDtoSchuelerLernabschnitte = conn.query(
					"SELECT l FROM DTOSchueler s JOIN DTOSchuelerLernabschnittsdaten l ON "
					+ "s.ID IN :value AND s.ID = l.Schueler_ID AND s.Schuljahresabschnitts_ID = l.Schuljahresabschnitts_ID", DTOSchuelerLernabschnittsdaten.class
			);
			Map<Long, DTOSchuelerLernabschnittsdaten> mapAktAbschnitte = queryDtoSchuelerLernabschnitte
					.setParameter("value", schuelerIDs)
					.getResultList().stream().collect(Collectors.toMap(l -> l.Schueler_ID, l -> l));
			versionen = ABPVersion.getDefault();
			schuldaten = ABPSchuldaten.get(schule, jahrgang);
			fachgruppen = ABPFachgruppen.getDefault();
			faecher = ABPFaecher.get(fachgruppen, dtofaecher, dtoFaecherMap);
			kursarten = ABPKursarten.getDefault();
			lupoBenutzer = ABPLehrer.getDefault();
			nichtMoeglicheKombinationen = ABPNichtMoeglAbiFachKombi.get(dtoFaecherNichtMoeglicheKombination, dtofaecher, dtoFaecherMap);
			schueler = ABPSchueler.get(dtoSchueler, mapAktAbschnitte, mapKlassen, mapLehrer, dtoLupoSchueler, gostLeistungen);
			schuelerFaecher = ABPSchuelerFaecher.get(faecher, fachgruppen, dtoSchueler, dtoLupoSchueler, gostLeistungen);
			schuelerFaecherBasisSicherungen = ABPSchuelerFaecherBasisSicherung.getDefault();
			schuelerFaecherSicherungen = ABPSchuelerFaecherSicherung.getDefault();
			fehlermeldungen = ABPSchuelerFehlermeldungen.getDefault();
			// TODO folgende Tabelle noch genutzt?
			schuelerSprachenfolge = ABPSchuelerSprachenfolge.getDefault();
			logger.logLn("Fertig!");
			logger.modifyIndent(-2);
		}
	}
	
	
	
	/**
	 * Schreibt die Jahrgangs-bezogenen Daten aus diesem Objekt in die zugehörigen LUPO-Tabellen 
	 * für den angegeben Jahrgang.
	 *   
	 * @param conn          die Datenbank-Verbindung
	 * @param abschnitt     der aktuelle Schuljahresabschnitt
	 * @param abiJahrgang   der Abitur-Jahrgang, für den die Informationen gesetzt werden sollen.
	 * @param klasse        die zugehörige Klasse des Jahrgangs, aus der ggf. Informationen zu den 
	 *                      Beratungslehrern entnommen werden können (für Default-Einträge).  
	 * @param dtoFaecher    die in der SVWS-DB definierten Fächer
	 * @param replace       gibt an, ob alte Daten für den Jahrgang der LuPO-Datei ersetzt werden 
	 *                      sollen, sofern sie bereits vorhanden sind.  
	 */
	private void setLUPOJahrgang(DBEntityManager conn, DTOSchuljahresabschnitte abschnitt, int abiJahrgang, String klasse, Map<String, DTOFach> dtoFaecher, boolean replace) {
		DTOGostJahrgangsdaten lupoJahrgaenge = conn.queryByKey(DTOGostJahrgangsdaten.class, abiJahrgang);
		if (lupoJahrgaenge != null) {
			logger.log("* Abiturjahrgang ist bereits angelegt");
			if (!replace) {
				logger.logLn(0, " - nichts zu tun...");
				return;
			}
			logger.logLn(0, " - ersetze die Daten...");
		}
		// Entferne zunächst alte jahrgangsbezogene Daten aus den LuPO-Tabellen
		conn.executeNativeDelete("DELETE FROM Gost_Jahrgangsdaten WHERE Abi_Jahrgang = " + abiJahrgang);
		conn.executeNativeDelete("DELETE FROM Gost_Jahrgang_Beratungslehrer WHERE Abi_Jahrgang = " + abiJahrgang);
		conn.executeNativeDelete("DELETE FROM Gost_Jahrgang_Faecher WHERE Abi_Jahrgang = " + abiJahrgang);
		conn.executeNativeDelete("DELETE FROM Gost_Jahrgang_Fachkombinationen WHERE Abi_Jahrgang = " + abiJahrgang);
		
		// Schreibe jahrgangsbezogene Daten in die LuPO-Tabelle
		logger.logLn("* Schreibe Jahrgangebezogene LuPO-Daten in die DB...");
		logger.modifyIndent(2);
		logger.logLn("- allgemeine Daten für den Jahrgang...");
		ABPSchuldaten abpSchule = schuldaten.get(0);
		DTOGostJahrgangsdaten lupoJahrgangsdaten = new DTOGostJahrgangsdaten(abiJahrgang);
		lupoJahrgangsdaten.TextBeratungsbogen = abpSchule.BeratungsText;
		lupoJahrgangsdaten.TextMailversand = abpSchule.MailText;
		lupoJahrgangsdaten.ZusatzkursGEVorhanden = abpSchule.ZusatzkursGeschichteVorhanden;
		lupoJahrgangsdaten.ZusatzkursGEErstesHalbjahr = abpSchule.ZusatzkursGeschichteBeginn.kuerzel;
		lupoJahrgangsdaten.ZusatzkursSWVorhanden = abpSchule.ZusatzkursSoWiVorhanden;
		lupoJahrgangsdaten.ZusatzkursSWErstesHalbjahr = abpSchule.ZusatzkursSoWiBeginn.kuerzel;
		conn.persist(lupoJahrgangsdaten);
		
		logger.logLn("- die Beratungslehrer anhand der Klassenlehrerinformationen...");
		List<DTOKlassen> klassen = conn.queryNamed("DTOKlassen.schuljahresabschnitts_id", abschnitt.ID, DTOKlassen.class)
				.stream().filter(kl -> kl.Klasse.equals(klasse)).collect(Collectors.toList());
    	List<Long> klassenIDs = klassen.stream().map(kl -> kl.ID).collect(Collectors.toList());
		Map<Long, List<DTOKlassenLeitung>> mapKlassenLeitung = conn.queryNamed("DTOKlassenLeitung.klassen_id.multiple", klassenIDs, DTOKlassenLeitung.class)
				.stream().collect(Collectors.groupingBy(kl -> kl.Klassen_ID));
		
		Map<Long, DTOLehrer> lehrer = conn.queryAll(DTOLehrer.class).stream().collect(Collectors.toMap(l -> l.ID, l -> l));
		logger.modifyIndent(2);
		for (DTOKlassen kl : klassen) {
			for (DTOKlassenLeitung kll : mapKlassenLeitung.get(kl.ID)) {
				logger.logLn("- Klassenlehrer der Klasse " + kl.Klasse + "...");
				DTOLehrer l = lehrer.get(kll.Lehrer_ID);
				if (l != null)
					conn.persist(new DTOGostJahrgangBeratungslehrer(abiJahrgang, l.ID));
			}
		}
		logger.modifyIndent(-2);
		
		logger.logLn("- die Fachinformationen für den Jahrgang");
		logger.modifyIndent(2);
		for (ABPFaecher abpFach : faecher.values()) {
			logger.log("- Fach " + abpFach.FachKrz + ": ");
			DTOFach dtoFach = dtoFaecher.get(abpFach.FachKrz);
			if (dtoFach == null) {
				logger.logLn(0, "FEHLER - Fach in der DB nicht definiert!");
				continue;
			}
			DTOGostJahrgangFaecher lupoFach = new DTOGostJahrgangFaecher(abiJahrgang, dtoFach.ID,
					abpFach.E1, abpFach.E2, abpFach.Q1, abpFach.Q2, abpFach.Q3, abpFach.Q4, abpFach.Abi_Moegl,
					abpFach.LK_Moegl);
			lupoFach.WochenstundenQPhase = dtoFach.WochenstundenQualifikationsphase;
			conn.persist(lupoFach);
			logger.logLn(0, "OK");
		}
		logger.modifyIndent(-2);
		
		logger.logLn("- die nicht möglichen Abitur-Fachkombinationen für den Jahrgang");
		logger.modifyIndent(2);
		if (nichtMoeglicheKombinationen.size() > 0) {
			// Bestimme die ID, für welche der Datensatz eingefügt wird
			DTODBAutoInkremente dbNmkID = conn.queryByKey(DTODBAutoInkremente.class, "Gost_Jahrgang_Fachkombinationen");
			long idNMK = dbNmkID == null ? 1 : dbNmkID.MaxID + 1;
			for (ABPNichtMoeglAbiFachKombi nmk : nichtMoeglicheKombinationen) {
				logger.log("- Fachkombination " + nmk.Fach1_Krz + " (" + nmk.Kursart1 + ") <-> " + nmk.Fach2_Krz + " (" + nmk.Kursart2 + "): ");
				DTOFach dtoFach1 = dtoFaecher.get(nmk.Fach1_Krz);
				if (dtoFach1 == null) {
					logger.logLn(0, "FEHLER - Fach 1 der Kombination in der DB nicht definiert!");
					continue;
				}
				DTOFach dtoFach2 = dtoFaecher.get(nmk.Fach2_Krz);
				if (dtoFach2 == null) {
					logger.logLn(0, "FEHLER - Fach 2 der Kombination in der DB nicht definiert!");
					continue;
				}
				String f1 = dtoFach1.Kuerzel + ((nmk.Kursart1 == null || "".equals(nmk.Kursart1)) ? "" : " als " + nmk.Kursart1);
				String f2 = dtoFach2.Kuerzel + ((nmk.Kursart2 == null || "".equals(nmk.Kursart2)) ? "" : " als " + nmk.Kursart2);
				String hinweistext = "+".equals(nmk.Typ)
					? ("" + f1 + " erfordert " + f2)
					: ("" + f1 + " erlaubt kein " + f2);
				GostLaufbahnplanungFachkombinationTyp typ = nmk.Typ == null ? GostLaufbahnplanungFachkombinationTyp.VERBOTEN : ("+".equals(nmk.Typ) ? GostLaufbahnplanungFachkombinationTyp.ERFORDERLICH : GostLaufbahnplanungFachkombinationTyp.VERBOTEN); 
				DTOGostJahrgangFachkombinationen lupoNMK = new DTOGostJahrgangFachkombinationen(idNMK++, abiJahrgang,
						dtoFach1.ID, dtoFach2.ID, !"Q1Q4".equals(nmk.Phase), !"Q1Q4".equals(nmk.Phase), true, true, true, true,  
						typ, hinweistext);
				lupoNMK.Abi_Jahrgang = abiJahrgang;
				lupoNMK.Kursart1 = nmk.Kursart1;
				lupoNMK.Kursart2 = nmk.Kursart2;
				conn.persist(lupoNMK);
				DTOGostJahrgangFachkombinationen lupoNMK2 = new DTOGostJahrgangFachkombinationen(idNMK++, abiJahrgang,
						dtoFach2.ID, dtoFach1.ID, !"Q1Q4".equals(nmk.Phase), !"Q1Q4".equals(nmk.Phase), true, true, true, true,  
						typ, hinweistext);
				lupoNMK2.Abi_Jahrgang = abiJahrgang;
				lupoNMK2.Kursart1 = nmk.Kursart2;
				lupoNMK2.Kursart2 = nmk.Kursart1;
				conn.persist(lupoNMK2);
				logger.logLn(0, "OK");
			}
		}
		logger.modifyIndent(-2);

		logger.modifyIndent(-2);
	}
	
	
	/**
	 * Schreibt die Daten aus diesem Objekt in die zugehörigen LUPO-Tabellen.
	 *   
	 * @param user       der Datenbank-Benutzer für den SVWS-Datenbankzugriff (siehe {@link Benutzer})
	 * @param replace    gibt an, ob alte Daten für den Jahrgang der LuPO-Datei ersetzt werden 
	 *                   sollen, sofern sie bereits vorhanden sind.  
	 */
	public void setLUPOTables(Benutzer user, boolean replace) {
		try (DBEntityManager conn = user.getEntityManager()) {
			Schule schule = Schule.query(conn);
			final Map<Long, DTOSchuljahresabschnitte> schuljahresabschnitte = conn.queryAll(DTOSchuljahresabschnitte.class).stream().collect(Collectors.toMap(a -> a.ID, a -> a));
			final Map<Long, DTOKlassen> klassen = conn.queryAll(DTOKlassen.class).stream().collect(Collectors.toMap(k -> k.ID, k -> k)); 
			DTOSchuljahresabschnitte dtoAbschnittSchule = schuljahresabschnitte.get(schule.dto.Schuljahresabschnitts_ID);
			
			logger.logLn("Informationen zu der LuPO-Datei...");
			logger.modifyIndent(2);
			logger.logLn("- Schulhalbjahr der SVWS-DB: " + dtoAbschnittSchule.Jahr + "." + schule.getHalbjahr());
			if (schuldaten.size() <= 0) {
				logger.logLn("- FEHLER: Fehlender Eintrag für die aktuelle Schule in den LuPO-Daten!");
				logger.modifyIndent(-2);
				return;
			}
			ABPSchuldaten abpSchule = schuldaten.get(0);
			logger.logLn("- Beratungshalbjahr der LuPO-Datei: " + abpSchule.Beratungshalbjahr);
			logger.modifyIndent(-2);
	
			logger.logLn("Vorbereitung...");
			logger.modifyIndent(2);
			logger.logLn("  - Erzeuge HashMap mit der Zuordnung der Fächer zum Schüler aus der LuPO-Tabelle...");
			HashMap<Integer, Vector<ABPSchuelerFaecher>> tmpSchuelerFaecher = new HashMap<>();
			for (ABPSchuelerFaecher abpSchuelerFaecher : schuelerFaecher) {
				Vector<ABPSchuelerFaecher> sf = tmpSchuelerFaecher.get(abpSchuelerFaecher.Schueler_ID);
				if (sf == null) {
					sf = new Vector<>();
					tmpSchuelerFaecher.put(abpSchuelerFaecher.Schueler_ID, sf);
				}
				sf.add(abpSchuelerFaecher);
			}
			logger.logLn("  - Bestimme die zu bearbeitende Schüler-Menge aus der LuPO-Datei...");
			List<Long> schuelerIDs = schueler.stream().filter(s -> s.Schild_ID != null).map(s -> (long)s.Schild_ID).collect(Collectors.toList());
			logger.logLn("  - Lese Schüler aus der DB ein, um diese mit den Daten der LuPO-Datei abzugleichen...");
			Map<Long, DTOSchueler> dtoSchuelerMap = conn.queryNamed("DTOSchueler.id.multiple", schuelerIDs, DTOSchueler.class).stream().collect(Collectors.toMap(s -> s.ID, s -> s));
			logger.logLn("  - Lese die aktuellen Lernabschnitt der Schüler aus der DB ein, um davon Daten mit den Daten aus der LuPO-Datei abzugleichen...");
			TypedQuery<DTOSchuelerLernabschnittsdaten> queryAktuelleLernabschnitte = 
					conn.query("SELECT l FROM DTOSchueler s JOIN DTOSchuelerLernabschnittsdaten l ON "
							+ "l.Schueler_ID = s.ID AND l.Schuljahresabschnitts_ID = s.Schuljahresabschnitts_ID AND l.Schueler_ID IN :value", DTOSchuelerLernabschnittsdaten.class);
			Map<Long, DTOSchuelerLernabschnittsdaten> dtoSchuelerAktAbschnittMap = queryAktuelleLernabschnitte
					.setParameter("value", schuelerIDs)
					.getResultList().stream().collect(Collectors.toMap(l -> l.Schueler_ID, l -> l));
			logger.logLn("  - Lese Jahrgänge aus der DB ein, um diese beim Abgleich mit den Daten der LuPO-Datei zu verwenden...");
			Map<Long, DTOJahrgang> dtoJahrgangMap = conn.queryAll(DTOJahrgang.class).stream().collect(Collectors.toMap(j -> j.ID, j -> j));
			logger.logLn("  - Lese Fächer aus der DB ein, um diese beim Abgleich mit den Daten der LuPO-Datei zu verwenden...");
			Map<String, DTOFach> dtoFaecher = conn.queryAll(DTOFach.class).stream().collect(Collectors.toMap(f -> f.Kuerzel, f -> f));
			HashSet<Integer> hatJahrgang = new HashSet<>(); // Zum Überprüfen, ob der Abiturjahrgang bereits angelegt wurde
			logger.modifyIndent(-2);
	
			logger.logLn("Prüfe Schülerdaten...");
			logger.modifyIndent(2);
			for (ABPSchueler abpSchueler : schueler) {
				// TODO Prüfe oder entferne evtl. vorhandene Einträge !!!
				logger.logLn("- Lese LuPO-Schüler " + abpSchueler.ID + " mit der DB-ID " + abpSchueler.Schild_ID + " ein...");
				logger.modifyIndent(2);
				DTOSchueler dtoSchueler = dtoSchuelerMap.get(abpSchueler.Schild_ID == null ? null : (long)abpSchueler.Schild_ID);
				if (dtoSchueler == null) {
					logger.logLn("- FEHLER: Der Schüler konnte nicht in der DB gefunden werden. Überspringe Schüler!");
					logger.modifyIndent(-2);
					continue;
				}
				DTOSchuljahresabschnitte dtoAbschnittSchueler = schuljahresabschnitte.get(dtoSchueler.Schuljahresabschnitts_ID);
				if (dtoAbschnittSchueler == null) {
					logger.logLn("- FEHLER: Der Schuljahresabschnitt des Schülers konnte nicht in der DB gefunden werden. Überspringe Schüler!");
					logger.modifyIndent(-2);
					continue;
				}
				DTOSchuelerLernabschnittsdaten dtoAktAbschnitt = dtoSchuelerAktAbschnittMap.get(dtoSchueler.ID);
				if (dtoAktAbschnitt == null) {
					logger.logLn("- FEHLER: Der Lernabschnitt des Schülers konnte nicht in der DB gefunden werden. Überspringe Schüler!");
					logger.modifyIndent(-2);
					continue;
				}
				
				logger.log("- Ermittle Abiturjahrgang: ");
				DTOJahrgang dtoJahrgang = dtoJahrgangMap.get(dtoAktAbschnitt.Jahrgang_ID);
				if (dtoJahrgang == null) {
					logger.logLn("FEHLER! Überspringe diesen Schüler!");
					logger.modifyIndent(-2);
					continue;
				}
				int restjahre = dtoJahrgang.AnzahlRestabschnitte / schule.dto.AnzahlAbschnitte;
				int abiJahrgang = dtoAbschnittSchueler.Jahr + restjahre;
				logger.logLn(0, "" + abiJahrgang);
				logger.log("- Prüfe, ob der Abiturjahrgang bereits vorgekommen ist: ");
				if (hatJahrgang.contains(abiJahrgang)) {
					logger.logLn(0, "Ja");
				} else {
					logger.logLn(0, "Nein");
					hatJahrgang.add(abiJahrgang);
					logger.modifyIndent(2);
					DTOKlassen klasse = klassen.get(dtoAktAbschnitt.Klassen_ID);
					setLUPOJahrgang(conn, dtoAbschnittSchule, abiJahrgang, klasse == null ? null : klasse.Klasse, dtoFaecher, replace);
					logger.modifyIndent(-2);
				}
	
				logger.logLn("- Schreibe Allgemeine Schüler-Daten in die DB... ");
				// TODO Alle Attribute prüfen, ob relevant in SVWS-DB
				DTOGostSchueler lupoSchueler = new DTOGostSchueler(dtoSchueler.ID, 
						(abpSchueler.Sportattest != null) || ("J".equals(abpSchueler.Sportattest)));
				lupoSchueler.DatumBeratung = abpSchueler.DatumBeratung == null ? null : abpSchueler.DatumBeratung.toLocalDate().toString();
				lupoSchueler.DatumRuecklauf = abpSchueler.DatumRuecklauf == null ? null : abpSchueler.DatumRuecklauf.toLocalDate().toString();
				lupoSchueler.Beratungslehrer_ID = null;  // TODO LehrerID aus abpSchueler.Beratungslehrer herausfinden?
				lupoSchueler.Kommentar = abpSchueler.Kommentar;
				lupoSchueler.PruefPhase = abpSchueler.PruefPhase;
				lupoSchueler.BesondereLernleistung_Art = abpSchueler.BLL_Art;
				lupoSchueler.BesondereLernleistung_Punkte = abpSchueler.BLL_Punkte;			
				conn.persist(lupoSchueler);
				
				logger.logLn("- Schreibe Fachbezogene Schüler-Daten in die DB... ");
				Vector<ABPSchuelerFaecher> abpSFaecher = tmpSchuelerFaecher.get(abpSchueler.ID);
				if (abpSFaecher != null) {
					logger.modifyIndent(2);
					for (ABPSchuelerFaecher abpSFach : abpSFaecher) {
						logger.log("- Fach " + abpSFach.FachKrz + ": ");
						DTOFach dtoFach = dtoFaecher.get(abpSFach.FachKrz);
						if (dtoFach == null) {
							logger.logLn(0, "FEHLER! Ignoriere das Fach beim Einlesen...");
							continue;
						}
						DTOGostSchuelerFachbelegungen lupoSFach = new DTOGostSchuelerFachbelegungen(dtoSchueler.ID,
								dtoFach.ID);
						lupoSFach.EF1_Kursart = abpSFach.Kursart_E1;
						lupoSFach.EF1_Punkte = abpSFach.Punkte_E1;
						lupoSFach.EF2_Kursart = abpSFach.Kursart_E2;
						lupoSFach.EF2_Punkte = abpSFach.Punkte_E2;
						lupoSFach.Q11_Kursart = abpSFach.Kursart_Q1;
						lupoSFach.Q11_Punkte = abpSFach.Punkte_Q1;
						lupoSFach.Q12_Kursart = abpSFach.Kursart_Q2;
						lupoSFach.Q12_Punkte = abpSFach.Punkte_Q2;
						lupoSFach.Q21_Kursart = abpSFach.Kursart_Q3;
						lupoSFach.Q21_Punkte = abpSFach.Punkte_Q3;
						lupoSFach.Q22_Kursart = abpSFach.Kursart_Q4;
						lupoSFach.Q22_Punkte = abpSFach.Punkte_Q4;
						lupoSFach.AbiturFach = abpSFach.AbiturFach;
						lupoSFach.Bemerkungen = abpSFach.Bemerkungen;
						lupoSFach.Markiert_Q1 = abpSFach.Markiert_Q1 != null && "J".equals(abpSFach.Markiert_Q1);
						lupoSFach.Markiert_Q2 = abpSFach.Markiert_Q2 != null && "J".equals(abpSFach.Markiert_Q2);
						lupoSFach.Markiert_Q3 = abpSFach.Markiert_Q3 != null && "J".equals(abpSFach.Markiert_Q3);
						lupoSFach.Markiert_Q4 = abpSFach.Markiert_Q4 != null && "J".equals(abpSFach.Markiert_Q4);
						lupoSFach.ergebnisAbiturpruefung = abpSFach.AbiPruefErgebnis;
						lupoSFach.hatMuendlichePflichtpruefung = abpSFach.MdlPflichtPruefung == null ? null : "J".equals(abpSFach.MdlPflichtPruefung);
						lupoSFach.ergebnisMuendlichePruefung = abpSFach.MdlPruefErgebnis;
						conn.persist(lupoSFach);
						logger.logLn(0, "OK");
					}
					logger.modifyIndent(-2);
				}
				logger.modifyIndent(-2);
			}
			logger.modifyIndent(-2);
		}
	}

	
	/**
	 * Ermittelt die Schulform, für welche die LuPO-Datei generiert wurde.
	 * 
	 * @return die Schulform 
	 */
	public Schulform retrieveSchulform() {
		if (schuldaten.size() != 1)
			return null;
		return Schulform.getByKuerzel(schuldaten.get(0).SchulformKrz);
	}
	

	/**
	 * Ermittelt die Liste der Fächer der gymnasialen Oberstufe aus der LuPO-Datei.
	 * Die Informationen können dabei gegegenüber Daten aus einer SVWS-DB 
	 * unvollständig sein!
	 * 
	 * @return die Liste der Fächer der gymnasialen Oberstufe
	 */
	public List<GostFach> retrieveGostFaecher() {
		Vector<GostFach> result = new Vector<>();
		for (ABPFaecher lupoFach : faecher.values()) {
			GostFach gostFach = new GostFach();
			gostFach.id = lupoFach.ID;
			gostFach.kuerzel = lupoFach.StatistikKrz;
			gostFach.kuerzelAnzeige = lupoFach.FachKrz;
			gostFach.bezeichnung = lupoFach.Bezeichnung;
			gostFach.sortierung = lupoFach.Sortierung;
			gostFach.istFremdsprache = lupoFach.IstSprache;
			gostFach.istFremdSpracheNeuEinsetzend = lupoFach.AlsNeueFSInSII;
			gostFach.biliSprache = lupoFach.Unterichtssprache;

			gostFach.istMoeglichAbiLK = lupoFach.LK_Moegl;
			gostFach.istMoeglichAbiGK = lupoFach.Abi_Moegl;
			gostFach.istMoeglichEF1 = lupoFach.E1;
			gostFach.istMoeglichEF2 = lupoFach.E2;
			gostFach.istMoeglichQ11 = lupoFach.Q1;
			gostFach.istMoeglichQ12 = lupoFach.Q2;
			gostFach.istMoeglichQ21 = lupoFach.Q3;
			gostFach.istMoeglichQ22 = lupoFach.Q4;
			
			gostFach.wochenstundenQualifikationsphase = lupoFach.Q_WStd != null ? lupoFach.Q_WStd : ("VX".equals(lupoFach.StatistikKrz) ? 2 : 3);

			ABPFaecher leitfach1 = faecher.get(lupoFach.Leitfach);
			ABPFaecher leitfach2 = faecher.get(lupoFach.Leitfach2);
			if (leitfach1 == null) {
				gostFach.projektKursLeitfach1ID = null;
				gostFach.projektKursLeitfach1Kuerzel = null;
			} else {
				gostFach.projektKursLeitfach1ID = (long) leitfach1.ID;
				gostFach.projektKursLeitfach1Kuerzel = leitfach1.FachKrz;				
			}
			if (leitfach2 == null) {
				gostFach.projektKursLeitfach2ID = null;
				gostFach.projektKursLeitfach2Kuerzel = null;
			} else {
				gostFach.projektKursLeitfach2ID = (long) leitfach2.ID;
				gostFach.projektKursLeitfach2Kuerzel = leitfach2.FachKrz;				
			}
			result.add(gostFach);
		}
		return result;
	}
	
	
	/**
	 * Ermittelt die Abiturdaten der Schüler aus der LuPO-MDB-Datei. Die 
	 * Informationen können dabei gegegenüber Daten aus einer SVWS-DB 
	 * unvollständig sein!
	 * 
	 * @return die Liste der Abiturdaten der Schüler
	 */
	public List<Abiturdaten> retrieveAbiturdaten() {
		HashMap<Long, Abiturdaten> alleAbiturdaten = new HashMap<>();
		Vector<Abiturdaten> result = new Vector<>();
		for (ABPSchueler lupoSchueler : schueler) {
			Abiturdaten abidaten = new Abiturdaten();
			abidaten.schuelerID = lupoSchueler.ID; 
			abidaten.abiturjahr = 2020; // fiktives Schuljahr (in der Lupo-MDB nicht vorhanden
			abidaten.schuljahrAbitur = 2021; // s.o.
			
			abidaten.bilingualeSprache = lupoSchueler.Bilingual;
			
			abidaten.besondereLernleistung = GostBesondereLernleistung.fromKuerzel(lupoSchueler.BLL_Art).kuerzel;
			abidaten.besondereLernleistungNotenKuerzel = Note.fromNotenpunkte(lupoSchueler.BLL_Punkte).kuerzel;
			try {
				abidaten.block1AnzahlKurse = Integer.parseInt(lupoSchueler.AnzK_Summe);
			} catch (@SuppressWarnings("unused") NumberFormatException e) {
				abidaten.block1AnzahlKurse = null;
			}
			abidaten.block1Zulassung = lupoSchueler.Zulassung;
			abidaten.sprachendaten = new Sprachendaten();
			abidaten.sprachendaten.schuelerID = abidaten.schuelerID;
			result.add(abidaten);
			alleAbiturdaten.put(abidaten.schuelerID, abidaten);
		}
		
		for (ABPSchuelerFaecher lupoSchuelerFach : schuelerFaecher) {
			long schueler_id = lupoSchuelerFach.Schueler_ID;
			Abiturdaten abidaten = alleAbiturdaten.get(schueler_id);
			if (abidaten == null)
				continue;
			
			ABPFaecher lupoFach = faecher.get(lupoSchuelerFach.FachKrz);
			if (lupoFach == null)
				continue; // ignoriere Belegungen, wo das Fach nicht korrekt definiert ist
			
			ZulaessigesFach zulFach = ZulaessigesFach.getByKuerzelASD(lupoFach.StatistikKrz);
			if (zulFach == null)
				continue; // ignoriere unzulässige Fächer
			
			AbiturFachbelegung fachbelegung = new AbiturFachbelegung();
			fachbelegung.fachID = lupoSchuelerFach.Fach_ID;
			fachbelegung.abiturFach = lupoSchuelerFach.AbiturFach;
			fachbelegung.istFSNeu = lupoFach.AlsNeueFSInSII;
			GostKursart fachKursart = 
					"PX".equals(lupoFach.StatistikKrz) ? GostKursart.PJK :  
					"VX".equals(lupoFach.StatistikKrz) ? GostKursart.VTF : GostKursart.GK;
			if (lupoSchuelerFach.Kursart_E1 != null) {
				fachbelegung.belegungen[0] = new AbiturFachbelegungHalbjahr(); 
				fachbelegung.belegungen[0].halbjahrKuerzel = GostHalbjahr.EF1.kuerzel;
				setFachbelegung(fachbelegung.belegungen[0], lupoSchuelerFach.Kursart_E1, fachKursart, lupoFach.Q_WStd, false);
				fachbelegung.letzteKursart = fachbelegung.belegungen[0].kursartKuerzel;
				fachbelegung.belegungen[0].biliSprache = lupoFach.Unterichtssprache;
				fachbelegung.belegungen[0].notenkuerzel = getNotenkuerzelFromLupoNotenpunkte(lupoSchuelerFach.Punkte_E1);
			}
			if (lupoSchuelerFach.Kursart_E2 != null) {
				fachbelegung.belegungen[1] = new AbiturFachbelegungHalbjahr(); 
				fachbelegung.belegungen[1].halbjahrKuerzel = GostHalbjahr.EF2.kuerzel;
				setFachbelegung(fachbelegung.belegungen[1], lupoSchuelerFach.Kursart_E2, fachKursart, lupoFach.Q_WStd, false);
				fachbelegung.letzteKursart = fachbelegung.belegungen[1].kursartKuerzel;
				fachbelegung.belegungen[1].biliSprache = lupoFach.Unterichtssprache;
				fachbelegung.belegungen[1].notenkuerzel = getNotenkuerzelFromLupoNotenpunkte(lupoSchuelerFach.Punkte_E2);
			}
			if (lupoSchuelerFach.Kursart_Q1 != null) {
				fachbelegung.belegungen[2] = new AbiturFachbelegungHalbjahr(); 
				fachbelegung.belegungen[2].halbjahrKuerzel = GostHalbjahr.Q11.kuerzel;
				setFachbelegung(fachbelegung.belegungen[2], lupoSchuelerFach.Kursart_Q1, fachKursart, lupoFach.Q_WStd, "J".equals(lupoSchuelerFach.Markiert_Q1));
				fachbelegung.letzteKursart = fachbelegung.belegungen[2].kursartKuerzel;
				fachbelegung.belegungen[2].biliSprache = lupoFach.Unterichtssprache;
				fachbelegung.belegungen[2].notenkuerzel = getNotenkuerzelFromLupoNotenpunkte(lupoSchuelerFach.Punkte_Q1);
			}
			if (lupoSchuelerFach.Kursart_Q2 != null) {
				fachbelegung.belegungen[3] = new AbiturFachbelegungHalbjahr(); 
				fachbelegung.belegungen[3].halbjahrKuerzel = GostHalbjahr.Q12.kuerzel;
				setFachbelegung(fachbelegung.belegungen[3], lupoSchuelerFach.Kursart_Q2, fachKursart, lupoFach.Q_WStd, "J".equals(lupoSchuelerFach.Markiert_Q2));
				fachbelegung.letzteKursart = fachbelegung.belegungen[3].kursartKuerzel;
				fachbelegung.belegungen[3].biliSprache = lupoFach.Unterichtssprache;
				fachbelegung.belegungen[3].notenkuerzel = getNotenkuerzelFromLupoNotenpunkte(lupoSchuelerFach.Punkte_Q2);
			}
			if (lupoSchuelerFach.Kursart_Q3 != null) {
				fachbelegung.belegungen[4] = new AbiturFachbelegungHalbjahr(); 
				fachbelegung.belegungen[4].halbjahrKuerzel = GostHalbjahr.Q21.kuerzel;
				setFachbelegung(fachbelegung.belegungen[4], lupoSchuelerFach.Kursart_Q3, fachKursart, lupoFach.Q_WStd, "J".equals(lupoSchuelerFach.Markiert_Q3));
				fachbelegung.letzteKursart = fachbelegung.belegungen[4].kursartKuerzel;
				fachbelegung.belegungen[4].biliSprache = lupoFach.Unterichtssprache;
				fachbelegung.belegungen[4].notenkuerzel = getNotenkuerzelFromLupoNotenpunkte(lupoSchuelerFach.Punkte_Q3);
			}
			if (lupoSchuelerFach.Kursart_Q4 != null) {
				fachbelegung.belegungen[5] = new AbiturFachbelegungHalbjahr(); 
				fachbelegung.belegungen[5].halbjahrKuerzel = GostHalbjahr.Q22.kuerzel;
				setFachbelegung(fachbelegung.belegungen[5], lupoSchuelerFach.Kursart_Q4, fachKursart, lupoFach.Q_WStd, "J".equals(lupoSchuelerFach.Markiert_Q4));
				fachbelegung.letzteKursart = fachbelegung.belegungen[5].kursartKuerzel;
				fachbelegung.belegungen[5].biliSprache = lupoFach.Unterichtssprache;
				fachbelegung.belegungen[5].notenkuerzel = getNotenkuerzelFromLupoNotenpunkte(lupoSchuelerFach.Punkte_Q4);
			}
			if (fachKursart == GostKursart.PJK) {
				abidaten.projektkursLeitfach1Kuerzel = lupoFach.Leitfach;
				abidaten.projektkursLeitfach2Kuerzel = lupoFach.Leitfach2;
			}
			if ((lupoSchuelerFach.FS_BeginnJg != null) && (lupoSchuelerFach.Sprachenfolge != null) && zulFach.daten.istFremdsprache) {
				Sprachbelegung sprachbelegung = new Sprachbelegung();
				sprachbelegung.sprache = zulFach.daten.kuerzel;
				try {
					sprachbelegung.reihenfolge = Integer.parseInt(lupoSchuelerFach.Sprachenfolge);
				} catch (@SuppressWarnings("unused") NumberFormatException e) {
					sprachbelegung.reihenfolge = -1;
				}
				sprachbelegung.belegungVonJahrgang = lupoSchuelerFach.FS_BeginnJg;
				if (sprachbelegung.belegungVonJahrgang.length() == 1)
					sprachbelegung.belegungVonJahrgang = "0" + sprachbelegung.belegungVonJahrgang;
                if ("10".equals(sprachbelegung.belegungVonJahrgang))
                    sprachbelegung.belegungVonJahrgang = "EF";
				sprachbelegung.belegungVonAbschnitt = 1;
				abidaten.sprachendaten.belegungen.add(sprachbelegung);
				// TODO LUPO-Import Sprachprüfungen einlesen
			}
			if ((lupoSchuelerFach.Kursart_E1 != null) || (lupoSchuelerFach.Kursart_E2 != null) ||
				(lupoSchuelerFach.Kursart_Q1 != null) || (lupoSchuelerFach.Kursart_Q2 != null) ||
				(lupoSchuelerFach.Kursart_Q3 != null) || (lupoSchuelerFach.Kursart_Q4 != null))
				abidaten.fachbelegungen.add(fachbelegung);
		}
		for (ABPSchuelerSprachenfolge lupoSchuelerSprachenfolge : schuelerSprachenfolge) {
			long schueler_id = lupoSchuelerSprachenfolge.Schueler_ID;
			Abiturdaten abidaten = alleAbiturdaten.get(schueler_id);
			if (abidaten == null)
				continue;

			ABPFaecher lupoFach = faecher.get(lupoSchuelerSprachenfolge.FachKrz);
			if (lupoFach == null)
				continue; // ignoriere Belegungen, wo das Fach nicht korrekt definiert ist
			
			ZulaessigesFach zulFach = ZulaessigesFach.getByKuerzelASD(lupoFach.StatistikKrz);
			if (zulFach == null)
				continue; // ignoriere unzulässige Fächer

			if ((lupoSchuelerSprachenfolge.JahrgangVon != null) && (lupoSchuelerSprachenfolge.Reihenfolge != null) && zulFach.daten.istFremdsprache) {
				Sprachbelegung sprachbelegung = new Sprachbelegung();
				sprachbelegung.sprache = zulFach.daten.kuerzel;
				try {
					sprachbelegung.reihenfolge = Integer.parseInt(lupoSchuelerSprachenfolge.Reihenfolge);
				} catch (@SuppressWarnings("unused") NumberFormatException e) {
					sprachbelegung.reihenfolge = -1;
				}
				sprachbelegung.belegungVonJahrgang = "" + lupoSchuelerSprachenfolge.JahrgangVon;
				if (sprachbelegung.belegungVonJahrgang.length() == 1)
					sprachbelegung.belegungVonJahrgang = "0" + sprachbelegung.belegungVonJahrgang;
                if ("10".equals(sprachbelegung.belegungVonJahrgang))
                    sprachbelegung.belegungVonJahrgang = "EF";
				sprachbelegung.belegungVonAbschnitt = lupoSchuelerSprachenfolge.AbschnittVon == null ? 1 : ((int)lupoSchuelerSprachenfolge.AbschnittVon);
				abidaten.sprachendaten.belegungen.add(sprachbelegung);
				// TODO LUPO-Import Sprachprüfungen einlesen
			}
		}
		return result;
	}
	
	
	// TODO Dokumentation
	private static String getNotenkuerzelFromLupoNotenpunkte(String lupoNotenpunkte) {
		if (lupoNotenpunkte == null)
			return null;
		switch (lupoNotenpunkte) {
			case "E1": return Note.E1_MIT_BESONDEREM_ERFOLG_TEILGENOMMEN.kuerzel;
			case "E2": return Note.E2_MIT_ERFOLG_TEILGENOMMEN.kuerzel;
			case "E3": return Note.E3_TEILGENOMMEN.kuerzel;
			case "00": case "0": return Note.UNGENUEGEND.kuerzel;
			case "01": case "1": return Note.MANGELHAFT_MINUS.kuerzel;
			case "02": case "2": return Note.MANGELHAFT.kuerzel;
			case "03": case "3": return Note.MANGELHAFT_PLUS.kuerzel;
			case "04": case "4": return Note.AUSREICHEND_MINUS.kuerzel;
			case "05": case "5": return Note.AUSREICHEND.kuerzel;
			case "06": case "6": return Note.AUSREICHEND_PLUS.kuerzel;
			case "07": case "7": return Note.BEFRIEDIGEND_MINUS.kuerzel;
			case "08": case "8": return Note.BEFRIEDIGEND.kuerzel;
			case "09": case "9": return Note.BEFRIEDIGEND_PLUS.kuerzel;
			case "10": return Note.GUT_MINUS.kuerzel;
			case "11": return Note.GUT.kuerzel;
			case "12": return Note.GUT_PLUS.kuerzel;
			case "13": return Note.SEHR_GUT_MINUS.kuerzel;
			case "14": return Note.SEHR_GUT.kuerzel;
			case "15": return Note.SEHR_GUT_PLUS.kuerzel;
		}
		return null;
	}
	

	// TODO Dokumentation
    private static void setFachbelegung(AbiturFachbelegungHalbjahr belegung, String belegungPlanungKursart, 
    		GostKursart fachKursart, Integer wochenstunden, boolean istInAbiwertung) {
		belegung.kursartKuerzel =
				"AT".equals(belegungPlanungKursart) ? "AT" : 
				"LK".equals(belegungPlanungKursart) ? "LK" :
				"ZK".equals(belegungPlanungKursart) ? "ZK" :
				fachKursart.toString(); 
		belegung.schriftlich = belegungPlanungKursart == null ? null :
			"LK".equals(belegungPlanungKursart) || "S".equals(belegungPlanungKursart);
		belegung.wochenstunden = 
				"LK".equals(belegungPlanungKursart) ? 5 : 
				wochenstunden != null ? wochenstunden : (fachKursart == GostKursart.VTF ? 2 : 3);
		belegung.block1gewertet = istInAbiwertung;
    }
	
}
