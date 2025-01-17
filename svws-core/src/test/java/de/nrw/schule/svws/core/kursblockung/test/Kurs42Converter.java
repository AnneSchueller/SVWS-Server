package de.nrw.schule.svws.core.kursblockung.test;

import java.util.HashMap;
import java.util.LinkedList;

import de.nrw.schule.svws.base.CsvReader;
import de.nrw.schule.svws.base.kurs42.Kurs42DataBlockplan;
import de.nrw.schule.svws.base.kurs42.Kurs42DataFachwahlen;
import de.nrw.schule.svws.base.kurs42.Kurs42DataKurse;
import de.nrw.schule.svws.base.kurs42.Kurs42DataSchueler;
import de.nrw.schule.svws.core.data.gost.GostBlockungKurs;
import de.nrw.schule.svws.core.data.gost.GostBlockungRegel;
import de.nrw.schule.svws.core.data.gost.GostBlockungSchiene;
import de.nrw.schule.svws.core.data.gost.GostBlockungsdaten;
import de.nrw.schule.svws.core.data.gost.GostFach;
import de.nrw.schule.svws.core.data.gost.GostFachwahl;
import de.nrw.schule.svws.core.data.schueler.Schueler;
import de.nrw.schule.svws.core.exceptions.DeveloperNotificationException;
import de.nrw.schule.svws.core.logger.Logger;
import de.nrw.schule.svws.core.types.gost.GostKursart;
import de.nrw.schule.svws.core.types.kursblockung.GostKursblockungRegelTyp;
import de.nrw.schule.svws.core.utils.gost.GostBlockungsdatenManager;
import de.nrw.schule.svws.core.utils.gost.GostFaecherManager;

/** Eine Klasse zum Einlesen von exportierten Kurs42-Textdateien mit direkter Umwandlung in das Eingabeobjekt
 * {@linkplain GostBlockungsdatenManager} für die Kursblockung.
 * 
 * @author Benjamin A. Bartsch */
public class Kurs42Converter {

	/** Der Logger für Warnungen und Fehlermeldungen. */
	private final Logger _logger;

	/** Die konvertierten Daten. Diese können an einen Blockungsalgorithmus weitergereicht werden. */
	private final GostBlockungsdatenManager _manager;

	/** Der Konstruktor wandelt einen Pfad {@code location} mit exportierten Kurs42-Textdateien in ein Objekt der Klasse
	 * {@linkplain GostBlockungsdatenManager} um. Es werden die folgenden Dateien eingelesen:<br>
	 * {@code 'Schueler.txt', 'Kurse.txt', 'Fachwahlen.txt' und 'Blockplan.txt'}
	 * 
	 * @param pLogger           Der logger.
	 * @param pPfad             Der Pfad der Kurs42-Exportdateien.
	 * @param pMaxTimeMillis    Die maximale Blockungszeit in Millisekunden.
	 * @param pFixiereAlleKurse Falls true, dann wird die aktuelle Kurslage fixiert. */
	public Kurs42Converter(Logger pLogger, String pPfad, long pMaxTimeMillis, boolean pFixiereAlleKurse) {
		this._logger = pLogger;

		HashMap<String, GostFach> mapFaecher = new HashMap<>();
		HashMap<String, GostKursart> mapKursarten = new HashMap<>();
		HashMap<String, GostBlockungKurs> mapKurse = new HashMap<>();
		HashMap<String, Schueler> mapSchueler = new HashMap<>();
		HashMap<String, GostFachwahl> mapFachwahlen = new HashMap<>();
		HashMap<String, LinkedList<GostBlockungKurs>> mapFachart = new HashMap<>();
		HashMap<Long, GostBlockungRegel> mapRegeln = new HashMap<>();

		// Einlesen der Schüler-Objekte
		for (Kurs42DataSchueler k42schueler : CsvReader.fromResource(pPfad + "Schueler.txt", Kurs42DataSchueler.class)) {

			// Doppelter Schülername?
			String sKey = getKeySchueler(k42schueler);
			if (mapSchueler.containsKey(sKey))
				throw new DeveloperNotificationException("Kurs42-Schueler-Inkonsistenz: Schüler (" + sKey + ") existiert doppelt.");

			Schueler gSchueler = new Schueler();
			gSchueler.id = mapSchueler.size() + 1;
			gSchueler.vorname = k42schueler.Vorname;
			gSchueler.nachname = k42schueler.Name;
			gSchueler.geschlecht = k42schueler.Geschlecht;
			mapSchueler.put(sKey, gSchueler);
		}

		// Einlesen der Kurs-Objekte & Fächer & Kursarten
		for (Kurs42DataKurse k42kurs : CsvReader.fromResource(pPfad + "Kurse.txt", Kurs42DataKurse.class)) {

			// Doppelter Kursname?
			String sKursname = k42kurs.Name;
			if (mapKurse.containsKey(sKursname))
				throw new DeveloperNotificationException("Kurs42-Kurse-Inkonsistenz: Kurs '" + sKursname + "' existiert doppelt.");

			// Neues Fach? --> Map
			String sFachKuerzel = k42kurs.Fach;
			if (!mapFaecher.containsKey(sFachKuerzel)) {
				GostFach gFach = new GostFach();
				gFach.id = mapFaecher.size();
				gFach.kuerzel = sFachKuerzel;
				mapFaecher.put(sFachKuerzel, gFach);
			}

			// Neue Kursart? --> Map
			String sKursartKuerzel = convertKursart(k42kurs.Kursart);
			if (!mapKursarten.containsKey(sKursartKuerzel)) {
				GostKursart gKursart = GostKursart.fromKuerzel(sKursartKuerzel);
				if (gKursart == null)
					throw new DeveloperNotificationException("GostKursart.fromKuerzel(" + sKursartKuerzel + ") == null");
				mapKursarten.put(sKursartKuerzel, gKursart);
			}

			// Neue Fachart? --> Map
			String sFachart = sFachKuerzel + ";"+sKursartKuerzel;
			if (mapFachart.containsKey(sFachart) == false)
				mapFachart.put(sFachart, new LinkedList<>());
			
			// Neuen Kurs erzeugen. Dem Map und Vector hinzufügen.
			GostBlockungKurs gKurs = new GostBlockungKurs();
			mapFachart.get(sFachart).addLast(gKurs);
			gKurs.id = mapKurse.size();
			gKurs.nummer = mapFachart.get(sFachart).size();
			// System.out.println("Kursname "+sKursname+" --> "+gKurs.id);
			gKurs.fach_id = mapFaecher.get(sFachKuerzel).id;
			gKurs.kursart = mapKursarten.get(sKursartKuerzel).id;
			gKurs.anzahlSchienen = k42kurs.Schienenzahl;
			mapKurse.put(sKursname, gKurs);
		}

		// Einlesen der Fachwahl-Objekte
		for (Kurs42DataFachwahlen k42fachwahl : CsvReader.fromResource(pPfad + "Fachwahlen.txt", Kurs42DataFachwahlen.class)) {
			// Schüler unbekannt?
			String sSchueler = getKeySchueler(k42fachwahl);
			if (!mapSchueler.containsKey(sSchueler))
				throw new DeveloperNotificationException("Kurs42-Fachwahlen-Inkonsistenz: Schüler (" + sSchueler + ") unbekannt!");

			// Neues Fach? --> Map
			String sFachKuerzel = k42fachwahl.Fachkrz;
			if (!mapFaecher.containsKey(sFachKuerzel)) {
				GostFach gFach = new GostFach();
				gFach.id = mapFaecher.size();
				gFach.kuerzel = sFachKuerzel;
				mapFaecher.put(sFachKuerzel, gFach);
			}

			// Neue Kursart? --> Map
			String sKursartKuerzel = convertKursart(k42fachwahl.Kursart);
			if (!mapKursarten.containsKey(sKursartKuerzel)) {
				GostKursart gKursart = GostKursart.fromKuerzel(sKursartKuerzel);
				if (gKursart == null)
					throw new NullPointerException("GostKursart.fromKuerzel(" + sKursartKuerzel + ") == null");
				mapKursarten.put(sKursartKuerzel, gKursart);
			}

			// Schüler hat doppelte Fachwahl?
			String sFachwahl = sSchueler + ";" + sFachKuerzel + ";" + sKursartKuerzel;
			if (mapFachwahlen.containsKey(sFachwahl))
				throw new DeveloperNotificationException("Kurs42-Fachwahlen: Schüler (" + sSchueler + ") hat die Fachwahl (" + sFachKuerzel + ";"+ sKursartKuerzel + ") doppelt!");

			// Fachwahl erzeugen
			GostFachwahl gFachwahl = new GostFachwahl();
			gFachwahl.fachID = mapFaecher.get(sFachKuerzel).id;
			gFachwahl.kursartID = mapKursarten.get(sKursartKuerzel).id;
			gFachwahl.schuelerID = mapSchueler.get(sSchueler).id;
			mapFachwahlen.put(sFachwahl, gFachwahl);
		}

		// Einlesen der Lage der Kurse. Bestimmung der Schienenanzahl
		int schienenAnzahl = 1;
		for (Kurs42DataBlockplan k42blockplan : CsvReader.fromResource(pPfad + "Blockplan.txt", Kurs42DataBlockplan.class)) {

			// Kurs unbekannt?
			String sKursname = k42blockplan.Kursbezeichnung;
			if (!mapKurse.containsKey(sKursname))
				throw new DeveloperNotificationException("Kurs42-Blockplan-Inkonsistenz: Kurs (" + sKursname + ") existiert nicht in 'Kurse.txt'.");

			// Schienenanzahl erhöhen?
			int gSchiene = k42blockplan.Schiene + 1;
			schienenAnzahl = Math.max(schienenAnzahl, gSchiene);

			// Regel 2 - Kursfixierung?
			if (pFixiereAlleKurse) {
				GostBlockungRegel gRegel = new GostBlockungRegel();
				gRegel.id = mapRegeln.size() + 1;
				gRegel.typ = GostKursblockungRegelTyp.KURS_FIXIERE_IN_SCHIENE.typ;
				gRegel.parameter.add(mapKurse.get(sKursname).id);
				gRegel.parameter.add(Long.valueOf(gSchiene));
				mapRegeln.put(gRegel.id, gRegel);
			}
		}

		// Temporäre Daten --> Manager
		GostBlockungsdaten gDaten = new GostBlockungsdaten();
		gDaten.id = 1L; // Pseudo-ID
		gDaten.kurse.addAll(mapKurse.values());
		gDaten.regeln.addAll(mapRegeln.values());
		gDaten.schueler.addAll(mapSchueler.values());
		gDaten.fachwahlen.addAll(mapFachwahlen.values());
		for (int i = 1; i <= schienenAnzahl; i++) {
			GostBlockungSchiene gSchiene = new GostBlockungSchiene();
			gSchiene.id = i; // Pseudo-ID
			gSchiene.nummer = i;
			gSchiene.bezeichnung = "Schiene " + i;
			gDaten.schienen.add(gSchiene);
		}

		GostFaecherManager fManager = new GostFaecherManager();
		fManager.addAll(mapFaecher.values());

		this._manager = new GostBlockungsdatenManager(gDaten, fManager);
		this._manager.setMaxTimeMillis(pMaxTimeMillis);
		this._manager.setID(1);

		this._logger.modifyIndent(-4);
	}

	/** Diese Methode liefert das zuvor im Konstruktor erzeugte Objekt vom Typ {@link GostBlockungsdatenManager},
	 * welches aus den Kurs42-Textdateien konvertiert wurde.
	 * 
	 * @return Liefert das aus den Kurs42-Textdateien konvertierte Objekt. */
	public GostBlockungsdatenManager gibKursblockungInput() {
		return _manager;
	}

	/** Erzeugt aus den vier Attributen (Name, Vorname, GebDat und Geschlecht) des Objektes {@link Kurs42DataSchueler}
	 * einen eindeutigen Schlüssel.
	 * 
	 * @return Ein eindeutiger Schlüssel um einen Schüler zu identifizieren. */
	private static String getKeySchueler(Kurs42DataSchueler s) {
		return s.Name + ";" + s.Vorname + ";" + s.GebDat + ";" + s.Geschlecht;
	}

	/** Erzeugt aus den vier Attributen (Name, Vorname, GebDat und Geschlecht) des Objektes {@link Kurs42DataFachwahlen}
	 * einen eindeutigen Schlüssel.
	 * 
	 * @return Ein eindeutiger Schlüssel um einen Schüler zu identifizieren. */
	private static String getKeySchueler(Kurs42DataFachwahlen f) {
		return f.Name + ";" + f.Vorname + ";" + f.GebDat + ";" + f.Geschlecht;
	}

	/** Wandelt ggf. den String {@code kursart} um. LK1 und LK2 werden zu LK umgewandelt und GKM, GKS, AB3 und AB4
	 * werden zu GK umgewandelt. Dies ist nötig, da Kurs42 in der Datei {@codeFachwahlen.txt} die Kursart in dieser Form
	 * speichert.
	 * 
	 * @param  kursart die Kursart
	 * @return         Der String {@code kursart} umgewandelt, z.B. 'LK1' zu 'LK'. */
	static String convertKursart(String kursart) {
		if (kursart.equals("LK1")) {
			return "LK";
		}
		if (kursart.equals("LK2")) {
			return "LK";
		}
		if (kursart.equals("GKM")) {
			return "GK";
		}
		if (kursart.equals("GKS")) {
			return "GK";
		}
		if (kursart.equals("AB3")) {
			return "GK";
		}
		if (kursart.equals("AB4")) {
			return "GK";
		}
		return kursart;
	}

}
