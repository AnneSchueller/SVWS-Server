package de.nrw.schule.svws.db.schema;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import de.nrw.schule.svws.db.schema.revisionen.Revision1Updates;
import de.nrw.schule.svws.db.schema.revisionen.Revision2Updates;
import de.nrw.schule.svws.db.schema.revisionen.Revision3Updates;
import de.nrw.schule.svws.db.schema.revisionen.Revision4Updates;
import de.nrw.schule.svws.db.schema.revisionen.Revision6Updates;
import de.nrw.schule.svws.db.schema.revisionen.RevisionNoUpdates;

/**
 * Diese Klasse enthält eine Aufzählung zu den unterschiedlichen Revisionen,
 * in welchem sich ein SVWS-Datenbankschema befinden kann.
 */
public enum SchemaRevisionen {

	/** 
	 * Dummy Revision, welche anzeigt dass keine Revision definiert ist (z.B. zum kennzeichnen, 
	 * dass ein Datensatz noch nicht veraltet ist) 
	 */
	UNDEFINED(-1, "2022-09-29"),
	
	/** 
	 * Erste Version der SVWS-Datenbank. Das Schema wurde von der letzten Schild-NRW Version 2.x übernommen 
	 */
	REV_0(0, "2022-09-29"),
	
	/**
	 * Korrekturen an aus Schild2 importierten Daten, bevor weitere Fremdschlüssel mit Revision 2 ergänzt werden. 
	 * Außerdem: Hinzufügen der Tabelle SchildKursSchueler (Erstellen der Tabelle) für den schnellen Zugriff auf die 
	 * Schüler-Zuordnung zu Kursen.
	 */
	REV_1(1, "2022-09-29"),
	
	/**
	 * Hinzufügen weitere Fremdschlüssel, um die referentielle Integrität in zukünftigen Revisionen zu verbessern.
	 * Erstellen der Trigger zur automatischen Aktualisierung der Tabelle SchildKursSchueler bei Änderung 
	 * der Leistungsdaten eines Schülers.
	 * Außerdem wird die Tabelle mit den Daten aus den Leistungsdaten eines Schülers initial befüllt. 
	 */
	REV_2(2, "2022-09-29"),
	
	/**
	 * Befüllen der Foreign-Keys auf die Tabelle K_Ort in den Tabellen K_AllgAdresse, K_Lehrer, Schueler, SchuelerErzAdr
	 */
	REV_3(3, "2022-09-29"),
	
	/**
	 * Tabellen für die Laufbahnplanung in der gymnasialen Oberstufe hinzugefügt.
	 */
	REV_4(4, "2022-09-29"),
	
	/**
	 * Tabellen für Stundenpläne mit Unterrichts- und Pausenzeiten
	 */
	REV_5(5, "2022-09-29"),
	
	/**
	 * Erstellen von allgemein nutzbaren Views und Tabellen für das ENM
	 */
	REV_6(6, "2022-09-29"),
	
	/**
	 * Tabellen für die Kursblockung in der gymnasialen Oberstufe hinzugefügt
	 */
	REV_7(7, "2022-09-29"),
	
	/**
	 * Tabellen für DavRessourcen und Sammlungen
	 */
	REV_8(8, "2022-09-29"),
	
	/**
	 * Tabellen für Klausurplanung
	 */
	REV_9(9, "2022-12-30");


	/** 
	 * Die die größte Revisionsnummer an, die in dieser Enumeration definiert wurde und 
	 * bis zu welcher alle Schema-Revision als stabil gelten und ab Version 1.0 des SVWS-Servers 
	 * nicht mehr verändert werden. 
	 */
	public static final SchemaRevisionen maxRevision = REV_9;
	
	/**
	 * Gibt die größte Revisions-Nummer an, welche in diese Enumeration definiert wurde.
	 * Dies dient dazu Revisionen als Entwickler-Revisionen zu kennzeichnen, die noch nicht
	 * stabil sind. Dieser Wert ist also größer oder gleich {@link SchemaRevisionen#maxRevision}.
	 */
	public static final SchemaRevisionen maxDeveloperRevision = REV_9;

	/** Eine Map, welche von der Revisionsnummer auf das Objekt der Aufzählung abbildet. */
	private static Map<Long, SchemaRevisionen> _mapByNumber = null; 
	

	/** Die Nummer der Schema-Revision */
	public final long revision;
	
	/** Das Datum, wann diese Revision eingeführt wurde */
	public final LocalDate date; 

	/** Das Objekt mit den Update-Befehlen für diese Revision */
	private SchemaRevisionUpdateSQL updater;


	/**
	 * Erstellt eine neue Revision mit der angegebenen Nummer und dem
	 * Datum der Definition für diese Revision.
	 * 
	 * @param revision   die Revisionsnummer
	 * @param date       das Datum, wann diese Revision eingeführt wurde
	 */
	private SchemaRevisionen(long revision, String date) {
		this.revision = revision;
		this.date = LocalDate.parse(date);
		this.updater = null;
	}
	
	
	/**
	 * Erstellt die Map für die Abbildung der Revisionsnummer auf das Objekt der Aufzählung.
	 * 
	 * @return die Map für die Abbildung
	 */
	private static Map<Long, SchemaRevisionen> getMapByNumber() {
		if (_mapByNumber == null)
			_mapByNumber = Arrays.stream(SchemaRevisionen.values()).collect(Collectors.toMap(r -> r.revision, r -> r));
		return _mapByNumber;
	}
	
	/**
	 * Bestimmt das zu der übergebenen Revisionsnummer gehörende Objekt der Aufzählung. 
	 * 
	 * @param revision   die Revisionsnummer
	 * 
	 * @return das Objekt der Aufzählung
	 */
	public static SchemaRevisionen get(long revision) {
		return getMapByNumber().get(revision);
	}
	

	/**
     * Gibt ein Objekt mit den Update-Befehlen für diese Revision zurück.
	 * 
	 * @return das Objekt für die Updates zu dieser Revision
	 */
	public final SchemaRevisionUpdateSQL getUpdater() {
	    if (updater == null) {
	        updater = switch(this) {
	            case REV_1 -> new Revision1Updates();
                case REV_2 -> new Revision2Updates();
                case REV_3 -> new Revision3Updates();
                case REV_4 -> new Revision4Updates();
                case REV_6 -> new Revision6Updates();
                default -> new RevisionNoUpdates(this);
	        };
	    }
	    return updater;
	}

}
