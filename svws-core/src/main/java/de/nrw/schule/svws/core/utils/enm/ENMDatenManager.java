package de.nrw.schule.svws.core.utils.enm;

import java.util.HashMap;
import java.util.List;

import de.nrw.schule.svws.core.data.enm.ENMDaten;
import de.nrw.schule.svws.core.data.enm.ENMFach;
import de.nrw.schule.svws.core.data.enm.ENMFoerderschwerpunkt;
import de.nrw.schule.svws.core.data.enm.ENMJahrgang;
import de.nrw.schule.svws.core.data.enm.ENMKlasse;
import de.nrw.schule.svws.core.data.enm.ENMLehrer;
import de.nrw.schule.svws.core.data.enm.ENMLeistung;
import de.nrw.schule.svws.core.data.enm.ENMLerngruppe;
import de.nrw.schule.svws.core.data.enm.ENMNote;
import de.nrw.schule.svws.core.data.enm.ENMSchueler;
import de.nrw.schule.svws.core.types.Geschlecht;
import de.nrw.schule.svws.core.types.Note;
import de.nrw.schule.svws.core.types.schueler.Foerderschwerpunkt;
import de.nrw.schule.svws.core.types.schule.Schulform;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse dien dem Verwalten von ENM-Daten (siehe auch {@link ENMDaten}).
 */
public class ENMDatenManager {

	/** Die ENM-Daten, die von diesem Daten-Manager verwaltet werden. */
	public final @NotNull ENMDaten daten;

	
	/** Temporäre Map für das Befüllen der ENMLehrer-Vektors.*/
	private @NotNull HashMap<@NotNull Long, @NotNull ENMLehrer> mapLehrer = new HashMap<>();

	/** Temporäre Map für das Befüllen des ENMSchueler-Vektors.*/
	private @NotNull HashMap<@NotNull Long, @NotNull ENMSchueler> mapSchueler = new HashMap<>();

	/** Temporäre Map für das Befüllen des ENMFach-Vektors.*/
	private @NotNull HashMap<@NotNull Long, @NotNull ENMFach> mapFaecher = new HashMap<>();

    /** Temporäre Map für das Befüllen des ENMFach-Vektors.*/
    private @NotNull HashMap<@NotNull String, @NotNull ENMFach> mapFaecherByKuerzel = new HashMap<>();

	/** Temporäre Map für das Befüllen des ENMJahrgang-Vektors.*/
	private @NotNull HashMap<@NotNull Long, @NotNull ENMJahrgang> mapJahrgaenge = new HashMap<>();

	/** Temporäre Map für das Befüllen des ENMKlasse-Vektors.*/
	private @NotNull HashMap<@NotNull Long, @NotNull ENMKlasse> mapKlassen = new HashMap<>();

	/** Zählt die Id der Lerngruppe hoch. */
	private long lerngruppenIDZaehler = 1;
	
	/** Temporäre Map für die Lerngruppen. */
	private @NotNull HashMap<@NotNull String, @NotNull ENMLerngruppe> mapLerngruppen = new HashMap<>();
	

	/**
	 * Erzeugt einen neuen ENM-Daten-Manager mit leeren ENM-Daten.
	 * 
	 * @param lehrerID   die ID des Lehrers für welchen die ENM-Daten erzeugt werden oder null für alle Lehrer 
	 */
	public ENMDatenManager(Long lehrerID) {
		this.daten = new ENMDaten();
		this.daten.lehrerID = lehrerID;
	}
	
	
	/**
	 * Erzeugt einen neuen ENM-Daten-Manager für die übergebenen Daten.
	 * 
	 * @param daten   die ENM-Daten
	 */
	public ENMDatenManager(@NotNull ENMDaten daten) {
		this.daten = daten;
	}


	/**
	 * Setzt die grundlegenden Daten zur Schule und zu dem Schuljahresabschnitts für welchen
	 * die ENM-Daten generiert wurden.
	 * 
	 * @param schulnummer                 die Schulnummer
	 * @param schuljahr                   das Schuljahr 
	 * @param anzahlAbschnitte            die Anzahl der Abschnitte an der Schule (2: Halbjahrsmodus, 4: Quartalsmodus)
	 * @param abschnitt                   die Nummer des Abschnittes im Schuljahr
	 * @param publicKey                   der öffentlichen Schlüssel, welcher für die Verschlüsselung und den 
	 *                                    Rückversand der Datei genutzt werden soll
	 * @param fehlstundenEingabe          gibt an, ob die Fehlstunden-Eingabe durch das externe Notenmodul erlaubt ist 
	 *                                    oder nicht
	 * @param fehlstundenSIFachbezogen    gibt an, ob die Fehlstunden für die Sekundarstufe I fachbezogen eingetragen 
	 *                                    werden oder nicht
	 * @param fehlstundenSIIFachbezogen   gibt an, ob die Fehlstunden für die Sekundarstufe II fachbezogen eingetragen 
	 *                                    werden oder nicht
	 * @param schulform                   das Kürzel der Schulform der Schule
	 * @param mailadresse                 gibt die Mailadresse an, an welche die verschlüsselte Datei zurückgesendet werden soll (z.B. mail@schule.nrw.de)
	 */
	public void setSchuldaten(int schulnummer, int schuljahr, int anzahlAbschnitte, int abschnitt, String publicKey,
			boolean fehlstundenEingabe, boolean fehlstundenSIFachbezogen, boolean fehlstundenSIIFachbezogen,
			@NotNull String schulform, String mailadresse) {
		daten.schulnummer = schulnummer;
		daten.schuljahr = schuljahr;
		daten.anzahlAbschnitte = anzahlAbschnitte;
		daten.aktuellerAbschnitt = abschnitt;
		daten.publicKey = publicKey;
		daten.fehlstundenEingabe = fehlstundenEingabe;
		daten.fehlstundenSIFachbezogen = fehlstundenSIFachbezogen;
		daten.fehlstundenSIIFachbezogen = fehlstundenSIIFachbezogen;
		daten.schulform = schulform;
		daten.mailadresse = mailadresse;		
	}


	/**
	 * Fügt alle Noten des Core-Type {@link Note} zu dem Noten-Katalog der ENM-Datei hinzu.
	 */
	public void addNoten() {
		if (daten.noten.size() > 0)
			return;
		@NotNull Note@NotNull[] noten = Note.values();
    	for (int i = 0; i < noten.length; i++) {
    		@NotNull Note note = noten[i]; 
    		@NotNull ENMNote enmNote = new ENMNote();
    		enmNote.id = note.id;
    		enmNote.kuerzel = note.kuerzel;
    		enmNote.notenpunkte = note.notenpunkte;
    		enmNote.text = note.text;
    		daten.noten.add(enmNote);
    	}		
	}


	/**
	 * Fügt alle Förderschwerpunkte des Core-Type {@link Foerderschwerpunkt} zu dem 
	 * Förderschwerpunkt-Katalog der ENM-Datei hinzu.
	 * 
	 * @param schulform   die Schulform, für welche die zulässigen Förderschwerpunkte 
	 *                    zurückgegeben werden
	 */
	public void addFoerderschwerpunkte(@NotNull Schulform schulform) {
		if (daten.foerderschwerpunkte.size() > 0)
			return;
		@NotNull List<@NotNull Foerderschwerpunkt> foerderschwerpunkte = Foerderschwerpunkt.get(schulform);
    	for (int i = 0; i < foerderschwerpunkte.size(); i++) {
    		Foerderschwerpunkt foerderschwerpunkt = foerderschwerpunkte.get(i);
    		ENMFoerderschwerpunkt enmFoerderschwerpunkt = new ENMFoerderschwerpunkt();
    		enmFoerderschwerpunkt.id = foerderschwerpunkt.daten.id;
    		enmFoerderschwerpunkt.kuerzel = foerderschwerpunkt.daten.kuerzel;
    		enmFoerderschwerpunkt.beschreibung = foerderschwerpunkt.daten.beschreibung;
    		daten.foerderschwerpunkte.add(enmFoerderschwerpunkt);
    	}		
	}


	/**
	 * Fügt einen Lehrer hinzu und überprüft dabei, ob der Lehrer schon in der Liste vorhanden ist.
	 * 
	 * @param id            die eindeutige ID des Lehrers
	 * @param kuerzel       das Kürzel des Lehrers
	 * @param nachname      der Nachname des Lehrers
	 * @param vorname       der Vorname des Lehrers
	 * @param geschlecht        das Geschlecht des Lehrers
	 * @param eMailDienstlich   die Dienst-Email-Adresse des Lehrers
	 * 
	 * @return true, falls der Lehrer hinzugefügt wurde, ansonsten false  
	 */
	public boolean addLehrer(long id, String kuerzel, String nachname, String vorname, @NotNull Geschlecht geschlecht, String eMailDienstlich) {
		if (mapLehrer.get(id) != null) 
			return false;		
		@NotNull ENMLehrer enmLehrer = new ENMLehrer();
    	enmLehrer.id = id;
    	enmLehrer.kuerzel = kuerzel;
		enmLehrer.nachname = nachname;
		enmLehrer.vorname = vorname;
		enmLehrer.geschlecht = geschlecht.kuerzel;
		enmLehrer.eMailDienstlich = eMailDienstlich;
		daten.lehrer.add(enmLehrer);
    	mapLehrer.put(id, enmLehrer);
    	return true;
	}
	
	
	/**
	 * Fügt einen Schueler hinzu und überprüft dabei, ob der Schueler schon in der Liste vorhanden ist.
	 * 
	 * @param id                  die ID des Schülers in der SVWS-DB
	 * @param jahrgangID          die ID des aktuellen Jahrgangs, in dem sich der Schüler befindet
	 * @param klasseID            die ID der aktuellen Klasse, in der sich der Schüler befindet
	 * @param nachname            der Nachname des Schülers (z.B. Mustermann)
	 * @param vorname             der Vorname des Schülers (z.B. Max)
	 * @param geschlecht          das Geschlecht des Schülers
	 * @param bilingualeSprache   gibt an, ob sich der Schüler aktuell im bilingualen Bildungsgang befindet 
	 *                            (wenn ja, z.B. F) oder nicht (null)
	 * @param istZieldifferent    gibt an, ob der Schüler Ziel-different unterrichtet wird
	 * @param istDaZFoerderung    gibt an, ob der Schüler Deutsch-Förderung mit Deutsch als Zweitsprache (DaZ) 
	 *                            bekommt (Seiteneinsteiger, z.B. Flüchtlingskinder)
	 * 
	 * @return true, falls der Schueler hinzugefügt wurde, ansonsten false  
	 */
	public boolean addSchueler(long id, long jahrgangID, long klasseID, String nachname, String vorname, @NotNull Geschlecht geschlecht,  
			                   String bilingualeSprache, boolean istZieldifferent, boolean istDaZFoerderung) {
		if (mapSchueler.get(id) != null) 
			return false;	
		@NotNull ENMSchueler enmSchueler = new ENMSchueler();
		enmSchueler.id = id; 
		enmSchueler.jahrgangID = jahrgangID;
		enmSchueler.klasseID = klasseID;
		enmSchueler.nachname = nachname;
		enmSchueler.vorname = vorname;
		enmSchueler.geschlecht = geschlecht.kuerzel;
		enmSchueler.bilingualeSprache = bilingualeSprache;
		enmSchueler.istZieldifferent = istZieldifferent;
		enmSchueler.istDaZFoerderung = istDaZFoerderung;
		daten.schueler.add(enmSchueler);
    	mapSchueler.put(id, enmSchueler);
    	return true;
	}

	
	/**
	 * Fügt ein Fach hinzu und überprüft dabei, ob das Fach schon in der Liste vorhanden ist.
	 * 
	 * @param id                die eindeutige ID des Faches
	 * @param kuerzel           das Kürzel des Faches, wie es im Rahmen der amtlichen Schulstatistik verwendet wird. (z.B. D)
	 * @param kuerzelAnzeige    das Kürzel des Faches, wie es im Rahmen der Schule benannt wird und angezeigt werden soll. (z.B. D)
	 * @param sortierung        die Reihenfolge des Faches bei der Sortierung der Fächer. (z.B. 37)
	 * @param istFremdsprache   gibt an, ob es sich bei dem Fach um eine Fremdsprache handelt oder nicht
	 * 
	 * @return true, falls das Fach hinzugefügt wurde, ansonsten false  
	 */
	public boolean addFach(long id, @NotNull String kuerzel, @NotNull String kuerzelAnzeige, int sortierung, boolean istFremdsprache) {
		if (mapFaecher.get(id) != null) 
			return false;		
		@NotNull ENMFach enmFach = new ENMFach();
		enmFach.id = id;
		enmFach.kuerzel = kuerzel;
		enmFach.kuerzelAnzeige = kuerzelAnzeige;
		enmFach.sortierung = sortierung;
		enmFach.istFremdsprache = istFremdsprache;
		daten.faecher.add(enmFach);
    	mapFaecher.put(id, enmFach);
    	mapFaecherByKuerzel.put(kuerzelAnzeige, enmFach);
    	return true;
	}
	
	
	/**
	 * Fügt einen Jahrgang hinzu und überprüft dabei, ob der Jahrgang schon in der Liste vorhanden ist.
	 * 
	 * @param id                die eindeutige ID des Jahrganges
	 * @param kuerzel           das Kürzel des Jahrgangs, wie es im Rahmen der amtlichen Schulstatistik verwendet wird. (z.B. EF)
	 * @param kuerzelAnzeige    das Kürzel des Jahrgangs, wie es im Rahmen der Schule benannt wird und angezeigt werden soll. (z.B. EF)
	 * @param beschreibung      die textuelle Bezeichnung des Jahrgangs. (z.B. Einführungsphase)
	 * @param stufe             die Stufe des Jahrgangs. (z.B. PR, SI, nur Berufskolleg: SII, Berufskolleg Anlage D und GOSt: SII-1, SII-2, SII-3)
	 * @param sortierung        die Reihenfolge des Jahrgangs bei der Sortierung der Jahrgänge. (z.B. 8)
	 * 
	 * @return true, falls der Jahrgang hinzugefügt wurde, ansonsten false  
	 */
	public boolean addJahrgang(long id, String kuerzel, String kuerzelAnzeige, String beschreibung, String stufe, int sortierung) {
		if (mapJahrgaenge.get(id) != null) 
			return false;		
		@NotNull ENMJahrgang enmJahrgang = new ENMJahrgang();
		enmJahrgang.id = id;
		enmJahrgang.kuerzel = kuerzel;
		enmJahrgang.kuerzelAnzeige = kuerzelAnzeige;
		enmJahrgang.beschreibung = beschreibung;
		enmJahrgang.stufe = stufe;
		enmJahrgang.sortierung = sortierung;
		daten.jahrgaenge.add(enmJahrgang);
    	mapJahrgaenge.put(id, enmJahrgang);
    	return true;
	}
	
	
	/**
	 * Fügt eine Klasse hinzu und überprüft dabei, ob die Klasse schon in der Liste vorhanden ist.
	 * 
	 * @param id                die eindeutige ID der Klasse
	 * @param kuerzel           das Kürzel der Klasse, wie es im Rahmen der amtlichen Schulstatistik verwendet wird. (z.B. EF)
	 * @param kuerzelAnzeige    das Kürzel der Klasse, wie es im Rahmen der Schule benannt wird und angezeigt werden soll. (z.B. EF)
	 * @param sortierung        die Reihenfolge der Klasse bei der Sortierung der Klassen. (z.B. 8)
	 * 
	 * @return true, falls die Klasse hinzugefügt wurde, ansonsten false  
	 */
	public boolean addKlasse(long id, String kuerzel, String kuerzelAnzeige, int sortierung) {
		if (mapKlassen.get(id) != null) 
			return false;		
		@NotNull ENMKlasse enmKlasse = new ENMKlasse();
		enmKlasse.id = id;
		enmKlasse.kuerzel = kuerzel;
		enmKlasse.kuerzelAnzeige = kuerzelAnzeige;
		enmKlasse.sortierung = sortierung;
		daten.klassen.add(enmKlasse);
    	mapKlassen.put(id, enmKlasse);
    	return true;
	}
	
	
	/**
	 * Liefert das ENM-Lehrer-Objekt für die angegebene Lehrer-ID zurück,
	 * sofern die Lehrer über die Methode {@link ENMDatenManager#addLehrer(long, String, String, String, Geschlecht, String)}
	 * hinzugefügt wurden.
	 * 
	 * @param id   die ID des Lehrers
	 * 
	 * @return das ENM-Lehrer-Objekt
	 */
	public ENMLehrer getLehrer(long id) {
		return mapLehrer.get(id);
	}
	

	/**
	 * Liefert das ENM-Schüler-Objekt für die angegebene Schüler-ID zurück,
	 * sofern die Schüler über die Methode {@link ENMDatenManager#addSchueler(long, long, long, String, String, Geschlecht, String, boolean, boolean)}
	 * hinzugefügt wurden.
	 * 
	 * @param id   die ID des Schülers
	 * 
	 * @return das ENM-Schüler-Objekt
	 */
	public ENMSchueler getSchueler(long id) {
		return mapSchueler.get(id);
	}

		
	/**
	 * Liefert das ENM-Fächer-Objekt für die angegebene Fächer-ID zurück,
	 * sofern die Fächer über die Methode {@link ENMDatenManager#addFach(long, String, String, int, boolean)}
	 * hinzugefügt wurden.
	 * 
	 * @param id   die ID des Faches
	 * 
	 * @return das ENM-Fächer-Objekt
	 */
	public ENMFach getFach(long id) {
		return mapFaecher.get(id);
	}

		
    /**
     * Liefert das ENM-Fächer-Objekt für das angegebene Fächer-Kürzel zurück,
     * sofern die Fächer über die Methode {@link ENMDatenManager#addFach(long, String, String, int, boolean)}
     * hinzugefügt wurden.
     * 
     * @param kuerzel   das Kürzel des Faches
     * 
     * @return das ENM-Fächer-Objekt
     */
    public ENMFach getFachByKuerzel(@NotNull String kuerzel) {
        return mapFaecherByKuerzel.get(kuerzel);
    }

        
	/**
	 * Liefert das ENM-Jahrgänge-Objekt für die angegebene Jahrgangs-ID zurück,
	 * sofern die Jahrgänge über die Methode {@link ENMDatenManager#addJahrgang(long, String, String, String, String, int)}
	 * hinzugefügt wurden.
	 * 
	 * @param id   die ID des Jahrgangs
	 * 
	 * @return das ENM-Jahrgänge-Objekt
	 */
	public ENMJahrgang getJahrgang(long id) {
		return mapJahrgaenge.get(id);
	}

		
	/**
	 * Liefert das ENM-Klassen-Objekt für die angegebene Klassen-ID zurück,
	 * sofern die Klassen über die Methode {@link ENMDatenManager#addKlasse(long, String, String, int)}
	 * hinzugefügt wurden.
	 * 
	 * @param id   die ID der Klasse
	 * 
	 * @return das ENM-Klassen-Objekt
	 */
	public ENMKlasse getKlasse(long id) {
		return mapKlassen.get(id);
	}

		
	/**
	 * Fügt eine neue Lerngruppe mit den angegebenen Parametern hinzu, falls sie noch nicht existiert. Die strID ist dabei
	 * eine temporäre ID, die nur bei der Erstellung von ENMLerngruppen auf Serverseite genutzt wird.
	 *  
	 * @param strID               die temporäre ID der Lerngruppe, um festzustellen, ob es diese Lerngruppe bereits gibt.
	 * @param kID                 die ID der Lerngruppe (Klasse oder Kurs) in der SVWS-DB
	 * @param fachID              die ID des Faches der Lerngruppe.
	 * @param kursartID           gibt die ID der Kursart an. Ist dieser Wert null, so handelt es sich um Klassen-Unterricht
	 * @param bezeichnung         die Bezeichnung der Lerngruppe (z.B. D-GK4)
	 * @param kursartKuerzel      das Kürzel der (allgemeinen) Kursart (z.B. GK)
	 * @param bilingualeSprache   das einstellige Kürzel der bilingualen Sprache, sofern es sich um eine bilinguale 
	 *                            Lerngruppe handelt. (z.B. F)
	 * @param wochenstunden       die Anzahl der Wochenstunden, falls es sich um einen Kurs handelt.
	 */
	public void addLerngruppe(@NotNull String strID, long kID, long fachID, Integer kursartID, String bezeichnung, 
						String kursartKuerzel, String bilingualeSprache, int wochenstunden) {
		if (mapLerngruppen.get(strID) != null)
			return;
		@NotNull ENMLerngruppe lerngruppe = new ENMLerngruppe();
		lerngruppe.id = lerngruppenIDZaehler++;
		lerngruppe.kID = kID;
		lerngruppe.fachID = fachID;
		lerngruppe.kursartID = kursartID;
		lerngruppe.bezeichnung = bezeichnung;
		lerngruppe.kursartKuerzel = kursartKuerzel;
		lerngruppe.bilingualeSprache = bilingualeSprache;
		lerngruppe.wochenstunden = wochenstunden;
		mapLerngruppen.put(strID, lerngruppe);
		daten.lerngruppen.add(lerngruppe);
	}
	
	
	/**
	 * Liefert die Lerngruppe mit der übergebenen (temporären) ID zurück.
	 * 
	 * @param strID   die temporäre ID der Lerngruppe, um festzustellen, ob es diese Lerngruppe bereits gibt.
	 * 
	 * @return die Lerngruppe
	 */
	public ENMLerngruppe getLerngruppe(@NotNull String strID) {
		return mapLerngruppen.get(strID);
	}

	
	
	
	/**
	 * Fügt die Klassenlehrer zu der List der Klassenlehrer bei einem Schüler hinzu
	 * 
	 * @param schueler           der Schüler
	 * @param klassenlehrerIDs   die IDs der Klassenlehrer
	 */
	public void addSchuelerKlassenlehrer(@NotNull ENMSchueler schueler, long... klassenlehrerIDs) {
		// TODO
	}

	/**
	 * Fügt eine Sprache mit den übergebenen Informationen zu der Sprachenfolge eines Schülers hinzu.
	 * 
	 * @param schueler               der Schüler
	 * @param sprache                das Kürzel der Sprache, bereinigt von dem Jahrgang, in dem die Sprache eingesetzt hat
	 * @param fachID                 die ID des Faches
	 * @param fachKuerzel            das Kürzel des Faches
	 * @param reihenfolge            die Reihenfolge des Faches in der Sprachenfolge (Beispiel 1)
	 * @param belegungVonJahrgang    die Information, ab welchem Jahrgang die Sprache belegt wurde (Beispiel 5)
	 * @param belegungVonAbschnitt   die Information, ab welchem Abschnitt in dem Jahrgang die Sprache belegt wurde (Beispiel 1)
	 * @param belegungBisJahrgang    die Information, bis zu welchem Jahrgang die Sprache belegt wurde (Beispiel 12), sofern die Sprache bereits abgeschlossen ist
	 * @param belegungBisAbschnitt   die Information, bis zu welchem Abschnitt in dem Jahrgang die Sprache belegt wurde (Beispiel 2), sofern die Sprache bereits abgeschlossen ist
	 * @param referenzniveau         die Bezeichnung des Sprachreferenzniveaus, welches bisher erreicht wurde (z.B. B2/C1)
	 * @param belegungSekI           die Mindest-Dauer der Belegung in der Sekundarstufe I gemäß den Stufen im Core-Type SprachBelegungSekI (z.B. 0, 2, 4, 6)
	 */
	public void addSchuelerSprachenfolge(@NotNull ENMSchueler schueler, String sprache, long fachID, String fachKuerzel, int reihenfolge,
			int belegungVonJahrgang, int belegungVonAbschnitt, Integer belegungBisJahrgang, Integer belegungBisAbschnitt,
			String referenzniveau, Integer belegungSekI) {
		// TODO
	}

	/**
	 * Fügt die Leistungsdaten mit den übergebenen Informationen zu den Leistungsdaten eines Schülers hinzu
	 * 
	 * @param schueler                    der Schüler
	 * @param leistungID                  die ID der Leistungsdaten des Schülers in der SVWS-DB (z.B. 307956)
	 * @param lerngruppenID               die eindeutige ID der Lerngruppe, der der Schüler zugeordnet ist. 
	 *                                    (Klasse oder Kurs wird erst in der Lerngruppe unterschieden!)
	 * @param note                        das Kürzel der Note, die vergeben wurde
	 * @param tsNote                      der Zeitstempe der letzten Änderung an der Note
	 * @param istSchriftlich              gibt an, ob das Fach schriftlich belegt wurde oder nicht
	 * @param abiturfach                  gibt an, ob es sich um ein Abitufach handelt (1,2,3 oder 4) oder nicht (null)
	 * @param fehlstundenGesamt           gibt die Anzahl der gesamten Fehlstunden an, sofern diese fachbezogen ermittel werden
	 * @param tsFehlstundenGesamt         der Zeitstempel der letzten Änderung an den gesamten Fehlstunden, sofern 
	 *                                    diese fachbezogen ermittel werden
	 * @param fehlstundenUnentschuldigt   gibt die Anzahl der unentschuldigten Fehlstunden an, sofern diese fachbezogen ermittel werden
	 * @param tsFehlstundenUnentschuldigt der Zeitstempel der letzten Änderung an den unentschuldigten Fehlstunden, 
	 *                                    sofern diese fachbezogen ermittel werden
	 * @param fachbezogeneBemerkungen     die fachbezogenen Bemerkungen bzw. das Thema bei Projektkursen
	 * @param tsFachbezogeneBemerkungen   der Zeitstempel der letzten Änderung an den fachbezogenen Bemerkungen
	 * @param neueZuweisungKursart        die Kurszuweisung, die auf dem Zeugnis erscheinen soll für den nächsten Kursabschnitt 
	 *                                    (z.B. E oder G-Kurs, z.B. an der Gesamtschule)
	 * @param istGemahnt                  gibt an, ob ein Fach gemahnt wurde oder nicht
	 * @param tsIstGemahnt                der Zeitstempel der letzten Änderung an der Angabe, ob ein Fach gemahnt wurde oder nicht 
	 * @param mahndatum                   das Mahndatum bei erfolgter Mahnung
	 */
	public void addSchuelerLeistungsdaten(@NotNull ENMSchueler schueler, long leistungID, long lerngruppenID, String note, 
				String tsNote, boolean istSchriftlich, Integer abiturfach, Integer fehlstundenGesamt, String tsFehlstundenGesamt, 
				Integer fehlstundenUnentschuldigt, String tsFehlstundenUnentschuldigt, String fachbezogeneBemerkungen,  
				String tsFachbezogeneBemerkungen, String neueZuweisungKursart, boolean istGemahnt, String tsIstGemahnt, String mahndatum) {
		@NotNull ENMLeistung enmLeistung = new ENMLeistung();
		enmLeistung.id = leistungID;
		enmLeistung.lerngruppenID = lerngruppenID;
		enmLeistung.note = note;
		enmLeistung.tsNote = tsNote;
		enmLeistung.istSchriftlich = istSchriftlich;
		enmLeistung.abiturfach = abiturfach;
		enmLeistung.fehlstundenGesamt = fehlstundenGesamt;
		enmLeistung.tsFehlstundenGesamt = tsFehlstundenGesamt; 
		enmLeistung.fehlstundenUnentschuldigt = fehlstundenUnentschuldigt;
		enmLeistung.tsFehlstundenUnentschuldigt = tsFehlstundenUnentschuldigt; 
		enmLeistung.fachbezogeneBemerkungen = fachbezogeneBemerkungen;
		enmLeistung.tsFachbezogeneBemerkungen = tsFachbezogeneBemerkungen;
		enmLeistung.neueZuweisungKursart = neueZuweisungKursart;
		enmLeistung.istGemahnt = istGemahnt;
		enmLeistung.tsIstGemahnt = tsIstGemahnt;
		enmLeistung.mahndatum = mahndatum;
		schueler.leistungsdaten.add(enmLeistung);
	}

	
	
	/**
	 * Fügt die Teilleistung mit den übergebenen Angaben zu übergebenen Leistungsdaten 
	 * eines Schülers hinzu.  
	 *
	 * @param leistung       die Leistungsdaten eines Schülers
	 * @param id             die ID der Teilleistung
	 * @param artID          die ID der Art von Teileistungen
	 * @param datum          das Datum, welches dem Erbringen der Teilleistung zuzuordnen ist (z.B. Klausurdatum)
	 * @param bemerkung      ggf. eine Bemerkung zu der Teilleistung 
	 * @param notenKuerzel   das Notenkürzel, welches der Teilleistung zuzuordnen ist.
	 */
	public void addSchuelerTeilleistung(@NotNull ENMLeistung leistung, long id, long artID, String datum, String bemerkung, String notenKuerzel) {
		// TODO Hinzufügen der Teilleistung - Implementierung ähnlich wie das Hinzufügen der Leistungen bei dem ENM-Schüler 
	}

	
}
