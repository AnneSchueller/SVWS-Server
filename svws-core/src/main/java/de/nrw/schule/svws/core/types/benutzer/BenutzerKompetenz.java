package de.nrw.schule.svws.core.types.benutzer;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import de.nrw.schule.svws.core.data.benutzer.BenutzerKompetenzKatalogEintrag;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse stellt die Core-Types als Aufzählung für die Benutzerkompetenzen 
 * zur Verfügung.
 */
public enum BenutzerKompetenz {
	
	/** Es werden keinerlei Kompetenzen benötigt. */
    KEINE(new BenutzerKompetenzKatalogEintrag(-2, BenutzerKompetenzGruppe.KEINE, "keine")),
    
    /** Es werden Admin-Rechte benötigt. */
    ADMIN(new BenutzerKompetenzKatalogEintrag(-1, BenutzerKompetenzGruppe.ADMIN, "admin")),
    
    /** Es werden Rechte zum Ansehen der Schüler Individualdaten benötigt. */
    SCHUELER_INDIVIDUALDATEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	11, BenutzerKompetenzGruppe.SCHUELER_INDIVIDUALDATEN, "Ansehen"
    )),
    
    /** Es werden Rechte zum Ändern der Schüler Individualdaten benötigt. */
    SCHUELER_INDIVIDUALDATEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	12, BenutzerKompetenzGruppe.SCHUELER_INDIVIDUALDATEN, "Ändern"
    )),
    
    /** Es werden Rechte zum Löschen der Schüler Individualdaten benötigt. */
    SCHUELER_INDIVIDUALDATEN_LOESCHEN(new BenutzerKompetenzKatalogEintrag(
    	13, BenutzerKompetenzGruppe.SCHUELER_INDIVIDUALDATEN, "Löschen"
    )),
    
    /** Es werden Rechte zum Ändern der Schüler Vermerke benötigt. */
    SCHUELER_INDIVIDUALDATEN_VERMERKE_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	14, BenutzerKompetenzGruppe.SCHUELER_INDIVIDUALDATEN, "Vermerke ändern"
    )),

    /** Es werden Rechte zum Ändern der Schüler KAoA-Daten benötigt. */
    SCHUELER_INDIVIDUALDATEN_KAOA_DATEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	15, BenutzerKompetenzGruppe.SCHUELER_INDIVIDUALDATEN, "KAoA-Daten ändern"
    )),
    
    /** Es werden Rechte zum Ändern der Einwilligungen zu einem Schüler benötigt. */
    SCHUELER_INDIVIDUALDATEN_EINWILLIGUNGEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	16, BenutzerKompetenzGruppe.SCHUELER_INDIVIDUALDATEN, "Einwilligungen ändern (DSGVO, Lernplattformen)"
    )),
    
    /** Es werden Rechte zum Ansehen der Schüler Leistungsdaten benötigt. */
    SCHUELER_LEISTUNGSDATEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	21, BenutzerKompetenzGruppe.SCHUELER_LEISTUNGSDATEN, "Ansehen"
    )),
    
    /** Es werden Rechte zum funktionsbezogenen Ändern der Schüler Leistungsdaten benötigt. */
    SCHUELER_LEISTUNGSDATEN_FUNKTIONSBEZOGEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	22, BenutzerKompetenzGruppe.SCHUELER_LEISTUNGSDATEN, "Funktionsbezogen ändern"
    )),
    
    /** Es werden Rechte zum generellen Ändern der Schüler Leistungsdaten benötigt. */
    SCHUELER_LEISTUNGSDATEN_ALLE_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	23, BenutzerKompetenzGruppe.SCHUELER_LEISTUNGSDATEN, "Alle ändern"
    )),
    
    /** Es werden Rechte zum Drucken der Berichte benötigt. */
    BERICHTE_ALLE_FORMULARE_DRUCKEN(new BenutzerKompetenzKatalogEintrag(
    	31, BenutzerKompetenzGruppe.BERICHTE, "Alle Formulare drucken"
    )),
    
    /** Es werden Rechte zum Drucken der Standardberichte benötigt. */
    BERICHTE_STANDARDFORMULARE_DRUCKEN(new BenutzerKompetenzKatalogEintrag(
    	32, BenutzerKompetenzGruppe.BERICHTE, "Standard-Formulare drucken"
    )),
    
    /** Es werden Rechte zum Ändern der Berichte benötigt. */
    BERICHTE_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	33, BenutzerKompetenzGruppe.BERICHTE, "Ändern"
    )),
    
    /** Es werden Rechte zum Löschen der Berichte benötigt. */
    BERICHTE_LOESCHEN(new BenutzerKompetenzKatalogEintrag(
    	34, BenutzerKompetenzGruppe.BERICHTE, "Löschen"
    )),
    
    /** Es werden Rechte zum Importieren von Daten benötigt. */
    IMPORT_EXPORT_DATEN_IMPORTIEREN(new BenutzerKompetenzKatalogEintrag(
    	41, BenutzerKompetenzGruppe.IMPORT_EXPORT, "Daten importieren"
    )),
    
    /** Es werden Rechte zum Exportieren von Schülerdaten benötigt. */
    IMPORT_EXPORT_SCHUELERDATEN_EXPORTIEREN(new BenutzerKompetenzKatalogEintrag(
    	42, BenutzerKompetenzGruppe.IMPORT_EXPORT, "Schülerdaten exportieren"
    )),
    
    /** Es werden Rechte zum Exportieren von Lehrerdaten benötigt. */
    IMPORT_EXPORT_LEHRERDATEN_EXPORTIEREN(new BenutzerKompetenzKatalogEintrag(
    	43, BenutzerKompetenzGruppe.IMPORT_EXPORT, "Lehrerdaten exportieren"
    )),
    
    /** Es werden Rechte zum Nutzen der SchILD-NRW-Schnittstelle benötigt. */
    IMPORT_EXPORT_SCHNITTSTELLE_SCHILD_NRW(new BenutzerKompetenzKatalogEintrag(
    	44, BenutzerKompetenzGruppe.IMPORT_EXPORT, "Schnittstelle SchILD-NRW verwenden"
    )),

    /** Es werden Rechte zum Ausführen des Access-DB-Exports benötigt. */
    IMPORT_EXPORT_ACCESS_DB(new BenutzerKompetenzKatalogEintrag(
    	45, BenutzerKompetenzGruppe.IMPORT_EXPORT, "Access-DB-Export durchführen"
    )),

    /** Es werden Rechte zum Export über die XML-Schnittstellen benötigt. */
    IMPORT_EXPORT_XML(new BenutzerKompetenzKatalogEintrag(
    	46, BenutzerKompetenzGruppe.IMPORT_EXPORT, "Export über XML-Schnittstellen"
    )),
    
    /** Es werden Rechte zum Ansehen der Schulbezogenen Daten benötigt. */
    SCHULBEZOGENE_DATEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	61, BenutzerKompetenzGruppe.SCHULBEZOGENE_DATEN, "Ansehen"
    )),
    
    /** Es werden Rechte zum Ändern der Schulbezogenen Daten benötigt. */
    SCHULBEZOGENE_DATEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	62, BenutzerKompetenzGruppe.SCHULBEZOGENE_DATEN, "Ändern"
    )),
    
    /** Es werden Rechte benötigt um ein Backup durchzuführen. */
    EXTRAS_BACKUP_DURCHFUEHREN(new BenutzerKompetenzKatalogEintrag(
    	71, BenutzerKompetenzGruppe.EXTRAS, "Backup durchführen"
    )),
    
    /** Es werden Rechte zum Wiederherstellen von gelöschten Schülerdaten benötigt. */
    EXTRAS_GELOESCHTE_DATEN_ZURUECKHOLEN(new BenutzerKompetenzKatalogEintrag(
    	72, BenutzerKompetenzGruppe.EXTRAS, "Gelöschte Daten zurückholen"
    )),
    
    /** Es werden Rechte zum Ändern der Farben für Fachgruppen benötigt. */
    EXTRAS_FARBEN_FUER_FACHGRUPPEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	73, BenutzerKompetenzGruppe.EXTRAS, "Farben für Fachgruppen ändern"
    )),
    
    /** Es werden Rechte Import von Daten aus Kurs42 benötigt. */
    EXTRAS_DATEN_AUS_KURS42_IMPORTIEREN(new BenutzerKompetenzKatalogEintrag(
    	74, BenutzerKompetenzGruppe.EXTRAS, "Daten aus Kurs42 importieren"
    )),

    /** Es werden Rechte zum Bearbeiten von Personengruppen benötigt. */
    EXTRAS_DATEN_PERSONENGRUPPEN_BEARBEITEN(new BenutzerKompetenzKatalogEintrag(
    	75, BenutzerKompetenzGruppe.EXTRAS, "Personengruppen bearbeiten"
    )),
    
    /** Es werden Rechte zum Ansehen von Katalogen benötigt. */
    KATALOG_EINTRAEGE_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	81, BenutzerKompetenzGruppe.KATALOG_EINTRAEGE, "Ansehen"
    )),
    
    /** Es werden Rechte zum Ändern von Katalogen benötigt. */
    KATALOG_EINTRAEGE_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	82, BenutzerKompetenzGruppe.KATALOG_EINTRAEGE, "Ändern"
    )),
    
    /** Es werden Rechte zum Löschen von Katalogen benötigt. */
    KATALOG_EINTRAEGE_LOESCHEN(new BenutzerKompetenzKatalogEintrag(
    	83, BenutzerKompetenzGruppe.KATALOG_EINTRAEGE, "Löschen"
    )),
    
    /** Es werden Rechte zum Ansehen von Lehrerdaten benötigt. */
    LEHRERDATEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	91, BenutzerKompetenzGruppe.LEHRERDATEN, "Ansehen"
    )),
    
    /** Es werden Rechte zum Ändern von Lehrerdaten benötigt. */
    LEHRERDATEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	92, BenutzerKompetenzGruppe.LEHRERDATEN, "Ändern"
    )),
    
    /** Es werden Rechte zum Löschen von Lehrerdaten benötigt. */
    LEHRERDATEN_LOESCHEN(new BenutzerKompetenzKatalogEintrag(
    	93, BenutzerKompetenzGruppe.LEHRERDATEN, "Löschen"
    )),
    
    /** Es werden Rechte zum Ansehen von Lehrerdetaildaten benötigt. */
    LEHRERDATEN_DETAILDATEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	94, BenutzerKompetenzGruppe.LEHRERDATEN, "Detaildaten ansehen"
    )),
    
    /** Es werden Rechte zum Ändern von Lehrerdetaildaten benötigt. */
    LEHRERDATEN_DETAILDATEN_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	95, BenutzerKompetenzGruppe.LEHRERDATEN, "Detaildaten ändern"
    )),

    /** Es werden Rechte zum Ansehen von Daten des Verfahrens Schulpflichtverletzung benötigt. */
    SCHULPFLICHTVERLETZUNG_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	101, BenutzerKompetenzGruppe.SCHULPFLICHTVERLETZUNG, "Ansehen"
    )),
    
    /** Es werden Rechte zum Ändern von Daten des Verfahrens Schulpflichtverletzung benötigt. */
    SCHULPFLICHTVERLETZUNG_AENDERN(new BenutzerKompetenzKatalogEintrag(
    	102, BenutzerKompetenzGruppe.SCHULPFLICHTVERLETZUNG, "Ändern"
    )),

    /** Es werden Rechte zum Löschen von Daten des Verfahrens Schulpflichtverletzung benötigt. */
    SCHULPFLICHTVERLETZUNG_LOESCHEN(new BenutzerKompetenzKatalogEintrag(
    	103, BenutzerKompetenzGruppe.SCHULPFLICHTVERLETZUNG, "Löschen"
    )),
    
    /** Es werden Rechte zum Ansehen von Stundenplänen (allgemein) benötigt. */
    STUNDENPLAN_ALLGEMEIN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	111, BenutzerKompetenzGruppe.STUNDENPLANUNG, "Stundenplan ansehen (allgemein)"
    )),
    
    /** Es werden Rechte zum Ansehen von Stundenplänen (funktionsbezogen) benötigt. */
    STUNDENPLAN_FUNKTIONSBEZOGEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	112, BenutzerKompetenzGruppe.STUNDENPLANUNG, "Stundneplan ansehen (funktionsbezogen)"
    )),
    
    /** Es werden Rechte zum Importieren von Stundenplänen benötigt. */
    STUNDENPLAN_IMPORT(new BenutzerKompetenzKatalogEintrag(
    	113, BenutzerKompetenzGruppe.STUNDENPLANUNG, "Stundenpläne importieren"
    )),
    
    /** Es werden Rechte zum Exportieren von Stundenplänen benötigt. */
    STUNDENPLAN_EXPORT(new BenutzerKompetenzKatalogEintrag(
    	114, BenutzerKompetenzGruppe.STUNDENPLANUNG, "Stundenpläne exportieren"
    )),
    
    /** Es werden Rechte zum Erstellen von Stundenplänen benötigt. */
    STUNDENPLAN_ERSTELLEN(new BenutzerKompetenzKatalogEintrag(
    	115, BenutzerKompetenzGruppe.STUNDENPLANUNG, "Stundenpläne erstellen"
    )),
    
    /** Es werden Rechte zum Aktivieren von Stundenplänen benötigt. */
    STUNDENPLAN_AKTIVIEREN(new BenutzerKompetenzKatalogEintrag(
    	116, BenutzerKompetenzGruppe.STUNDENPLANUNG, "Stundenpläne aktivieren"
    )),
    
    /** Es werden Rechte zur Administration des Notenmoduls benötigt. */
    NOTENMODUL_ADMINISTRATION(new BenutzerKompetenzKatalogEintrag(
    	131, BenutzerKompetenzGruppe.NOTENMODUL, "Administration Notenmodul"
    )),
    
    /** Es werden Rechte zur Änderung von Noten im Notenmodul (allgemein) benötigt. */
    NOTENMODUL_NOTEN_AENDERN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
    	132, BenutzerKompetenzGruppe.NOTENMODUL, "Noten ändern (allgemein)"
    )),
    /** Es werden Rechte zur Änderung von Noten im Notenmodul (funktionsbezogen) benötigt. */
    NOTENMODUL_NOTEN_AENDERN_FUNKTION(new BenutzerKompetenzKatalogEintrag(
    	133, BenutzerKompetenzGruppe.NOTENMODUL, "Noten ändern (funktionsbezogen)"
    )), 
    
    /** Es werden Rechte zum Ansehen von Noten im Notenmodul (allgemein) benötigt. */
    NOTENMODUL_NOTEN_ANSEHEN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
    	134, BenutzerKompetenzGruppe.NOTENMODUL, "Noten ansehen (allgemein)"
    )),
    /** Es werden Rechte zum Ansehen von Noten im Notenmodul (funktionsbezogen) benötigt. */
    NOTENMODUL_NOTEN_ANSEHEN_FUNKTION(new BenutzerKompetenzKatalogEintrag(
    	135, BenutzerKompetenzGruppe.NOTENMODUL, "Noten ansehen (funktionsbezogen)"
    )), 
    
    /** Es werden Rechte zur Administration der Datenbanken (Schema ertsellen/migrieren) benötigt. DB_Root-Rechte erforderlich. */
    DATENBANK_SCHEMA_ERSTELLEN(new BenutzerKompetenzKatalogEintrag(
    	141, BenutzerKompetenzGruppe.DATENBANK, "Schema erstellen und migrieren"
    )),
    
    /** Es werden Rechte zum Import von SQLite-Backups benötigt. DB_Root-Rechte erforderlich.*/
    DATENBANK_SQLITE_IMPORT(new BenutzerKompetenzKatalogEintrag(
    	142, BenutzerKompetenzGruppe.DATENBANK, "SQLite importieren (Backup einspielen)"
    )),
    /** Es werden Rechte zum Export von SQLite-Backups benötigt. */
    DATENBANK_SQLITE_EXPORT(new BenutzerKompetenzKatalogEintrag(
    	143, BenutzerKompetenzGruppe.DATENBANK, "SQLite exportieren (Backup erstellen)"
    )),   

    /** Es werden Rechte zur Durchführung der Laufbahnplanung (allgemein) benötigt. */
    OBERSTUFE_LAUFBAHNPLANUNG_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        161, BenutzerKompetenzGruppe.OBERSTUFE_LAUFBAHNPLANUNG, "Laufbahnplanung (allgemein)"
    )),

    /** Es werden Rechte zur Durchführung der Laufbahnplanung (stufenbezogen) benötigt. */
    OBERSTUFE_LAUFBAHNPLANUNG_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        162, BenutzerKompetenzGruppe.OBERSTUFE_LAUFBAHNPLANUNG, "Laufbahnplanung (funktionsbezogen)"
    )),

    /** Es werden Rechte zum Import von Laufbahnpdaten aus LuPO benötigt. */
    OBERSTUFE_LUPO_IMPORT(new BenutzerKompetenzKatalogEintrag(
        163, BenutzerKompetenzGruppe.OBERSTUFE_LAUFBAHNPLANUNG, "Laufbahnplanung aus LuPO importieren"
    )),

    /** Es werden Rechte zur Kursverwaltung - Blocken (allgemein) benötigt. */
    OBERSTUFE_KURSPLANUNG_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        171, BenutzerKompetenzGruppe.OBERSTUFE_KURSPLANUNG, "Kursverwaltung - Blocken (allgemein)"
    )),

    /** Es werden Rechte zur Kursverwaltung - Blocken (stufenbezogen) benötigt. */
    OBERSTUFE_KURSPLANUNG_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        172, BenutzerKompetenzGruppe.OBERSTUFE_KURSPLANUNG, "Kursverwaltung - Blocken (funktionsbezogen)"
    )),

    /** Es werden Rechte zur Aktivierung einer Blockung benötigt. */
    OBERSTUFE_KURSPLANUNG_BLOCKUNG_AKTIVIEREN(new BenutzerKompetenzKatalogEintrag(
        173, BenutzerKompetenzGruppe.OBERSTUFE_KURSPLANUNG, "Kursverwaltung - Blockung aktivieren"
    )),

    /** Es werden Rechte zur Bearbeitung einer Klausurplanung benötigt. */
    OBERSTUFE_KLAUSURPLANUNG_AENDERN(new BenutzerKompetenzKatalogEintrag(
        181, BenutzerKompetenzGruppe.OBERSTUFE_KLAUSURPLANUNG, "Klausurplanung ändern"
    )),

    /** Es werden Rechte zum Ansehen einer Klausurplanung (allgemein) benötigt. */
    OBERSTUFE_KLAUSURPLANUNG_ANSEHEN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        182, BenutzerKompetenzGruppe.OBERSTUFE_KLAUSURPLANUNG, "Klausurplanung ansehen (allgemein)"
    )),

    /** Es werden Rechte zum Ansehen einer Klausurplanung (funktionsbezogen) benötigt. */
    OBERSTUFE_KLAUSURPLANUNG_ANSEHEN_FUNKTION(new BenutzerKompetenzKatalogEintrag(
        183, BenutzerKompetenzGruppe.OBERSTUFE_KLAUSURPLANUNG, "Klausurplanung ansehen (funktionsbezogen)"
    )),

    /** Berechtigung zum Ansehen von Abiturdaten der Oberstufe (allgemein). */
    ABITUR_ANSEHEN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        191, BenutzerKompetenzGruppe.ABITUR, "Abiturdaten ansehen (allgemein)"
    )),

    /** Berechtigung zum Ansehen von Abiturdaten der Oberstufe (funktionsbezogen). */
    ABITUR_ANSEHEN_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        192, BenutzerKompetenzGruppe.ABITUR, "Abiturdaten ansehen (funktionsbezogen)"
    )),

    /** Berechtigung zum Ändern aller Daten zum Abitur (allgemein). */
    ABITUR_AENDERN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        193, BenutzerKompetenzGruppe.ABITUR, "Abiturdaten ändern (allgemein)"
    )),

    /** Berechtigung zum Ändern aller Daten zum Abitur (funktionsbezogen). */
    ABITUR_AENDERN_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        194, BenutzerKompetenzGruppe.ABITUR, "Abiturdaten ändern (funktionsbezogen)"
    )),

    /** Berechtigung zum Eingeben von Prüfungsergebnissen (allgemein). */
    ABITUR_EINGABE_ERGEBNISSE_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        195, BenutzerKompetenzGruppe.ABITUR, "Prüfungsergebnisse eingeben (allgemein)"
    )),

    /** Berechtigung zum Eingeben von Prüfungsergebnissen (funktionsbezogen). */
    ABITUR_EINGABE_ERGEBNISSE_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        196, BenutzerKompetenzGruppe.ABITUR, "Prüfungsergebnisse eingeben (funktionsbezogen)"
    )),

    /** Es werden Rechte zum Ansehen der Adressdaten eines von Erziehungsberechtigten benötigt*/
    ADRESSDATEN_ERZIEHER_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	201, BenutzerKompetenzGruppe.CARDDAV, "Ansehen"
    )), 

    /** Allgemeine Berechtigung für das Einsehen von Adressdaten über die CardDav API */
    ADRESSDATEN_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
    	202, BenutzerKompetenzGruppe.CARDDAV, "Ansehen"
    )),

    /** Allgemeine Berechtigung für den Zugriff auf die CalDav API */
    KALENDER_ANSEHEN(new BenutzerKompetenzKatalogEintrag(
		301, BenutzerKompetenzGruppe.CALDAV, "Ansehen"
	)), 

    /** Berechtigung für den Besitz und das Bearbeiten eines eigenen Kalenders über die CalDav API. */
    EIGENEN_KALENDER_BEARBEITEN(
		new BenutzerKompetenzKatalogEintrag(302, BenutzerKompetenzGruppe.CALDAV, "Bearbeiten"
	)), 

    /** Allgemeine Berechtigung zum Ansehen generierter Kalender abhängig von der Funktion des Nutzers */
    KALENDER_FUNKTIONSBEZOGEN_ANSEHEN(
		new BenutzerKompetenzKatalogEintrag(303, BenutzerKompetenzGruppe.CALDAV, "Ansehen"
	)),

    /** Berechtigung zum Ansehen von Abschlussdaten der Sekundarstufe I (allgemein). */
    ABSCHLUSS_SEKI_ANSEHEN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        401, BenutzerKompetenzGruppe.ABSCHLUSS_SEKI, "Abschlussdaten ansehen (allgemein)"
    )),

    /** Berechtigung zum Ansehen von Abschlussdaten der Sekundarstufe I (funktionsbezogen). */
    ABSCHLUSS_SEKI_ANSEHEN_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        402, BenutzerKompetenzGruppe.ABSCHLUSS_SEKI, "Abschlussdaten ansehen (funktionsbezogen)"
    )),

    /** Berechtigung zum Ändern aller Abschlussdaten der Sekundarstufe I (allgemein). */
    ABSCHLUSS_SEKI_AENDERN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        403, BenutzerKompetenzGruppe.ABSCHLUSS_SEKI, "Abschlussdaten ändern (allgemein)"
    )),

    /** Berechtigung zum Ändern aller Abschlussdaten der Sekundarstufe I (funktionsbezogen). */
    ABSCHLUSS_SEKI_AENDERN_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        404, BenutzerKompetenzGruppe.ABSCHLUSS_SEKI, "Abschlussdaten ändern (funktionsbezogen)"
    )),

    /** Berechtigung zum Eingeben von Prüfungsergebnissen (allgemein). */
    ABSCHLUSS_SEKI_EINGABE_ERGEBNISSE_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        405, BenutzerKompetenzGruppe.ABSCHLUSS_SEKI, "Prüfungsergebnisse eingeben (allgemein)"
    )),

    /** Berechtigung zum Eingeben von Prüfungsergebnissen (funktionsbezogen). */
    ABSCHLUSS_SEKI_EINGABE_ERGEBNISSE_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        406, BenutzerKompetenzGruppe.ABSCHLUSS_SEKI, "Prüfungsergebnisse eingeben (funktionsbezogen)"
    )),

    /** Berechtigung zum Ansehen von Abschlussdaten an einer berufsbildenden Schule (allgemein). */
    ABSCHLUSS_BK_ANSEHEN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        501, BenutzerKompetenzGruppe.ABSCHLUSS_BK, "Abschlussdaten ansehen (allgemein)"
    )),

    /** Berechtigung zum Ansehen von Abschlussdaten an einer berufsbildenden Schule (funktionsbezogen). */
    ABSCHLUSS_BK_ANSEHEN_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        502, BenutzerKompetenzGruppe.ABSCHLUSS_BK, "Abschlussdaten ansehen (funktionsbezogen)"
    )),

    /** Berechtigung zum Ändern aller Abschlussdaten an einer berufsbildenden Schule (allgemein). */
    ABSCHLUSS_BK_AENDERN_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        503, BenutzerKompetenzGruppe.ABSCHLUSS_BK, "Abschlussdaten ändern (allgemein)"
    )),

    /** Berechtigung zum Ändern aller Abschlussdaten an einer berufsbildenden Schule (funktionsbezogen). */
    ABSCHLUSS_BK_AENDERN_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        504, BenutzerKompetenzGruppe.ABSCHLUSS_BK, "Abschlussdaten ändern (funktionsbezogen)"
    )),

    /** Berechtigung zum Eingeben von Prüfungsergebnissen (allgemein). */
    ABSCHLUSS_BK_EINGABE_ERGEBNISSE_ALLGEMEIN(new BenutzerKompetenzKatalogEintrag(
        505, BenutzerKompetenzGruppe.ABSCHLUSS_BK, "Prüfungsergebnisse eingeben (allgemein)"
    )),

    /** Berechtigung zum Eingeben von Prüfungsergebnissen (funktionsbezogen). */
    ABSCHLUSS_BK_EINGABE_ERGEBNISSE_FUNKTIONSBEZOGEN(new BenutzerKompetenzKatalogEintrag(
        506, BenutzerKompetenzGruppe.ABSCHLUSS_BK, "Prüfungsergebnisse eingeben (funktionsbezogen)"
    ));


	/** Die Version dieses Core-Types, um beim Datenbank Update-Process die Version des Core-Types feststellen zu können. */
	public static long VERSION = 1;	

	/** Die Daten der Benutzerkompetenz */
    public final @NotNull BenutzerKompetenzKatalogEintrag daten;


	/** Eine HashMap zum schnellen Zugriff auf ein Aufzählungobjekt anhand der ID der Benutzerkompetenz */
	private static final @NotNull HashMap<@NotNull Long, @NotNull BenutzerKompetenz> _mapID = new HashMap<>();
	
	/** Eine HashMap zum schnellen Zugriff auf die Benutzer-Kompetenzen anhand der Benutzer-Kompetenz-Gruppe*/
	private static final @NotNull HashMap<@NotNull BenutzerKompetenzGruppe, @NotNull List<@NotNull BenutzerKompetenz>> _mapGruppenZuordnung = new HashMap<>();


    /**
     * Erzeugt eine neue Benutzerkompetenz für die Aufzählung.
     *
     * @param id                  die ID der Benutzerkompetenz
     * @param bezeichnungGruppe   die Bezeichnung der Gruppe von Kompetenzen zu dieser diese Benutzerkompetenz gehört
     * @param bezeichnung         die Bezeichnung der Benutzerkompetenz
     */
    private BenutzerKompetenz(@NotNull BenutzerKompetenzKatalogEintrag daten) {
        this.daten = daten;
    }


	/**
	 * Gibt eine Map von den IDs der Benutzerkompetenzen auf die zugehörigen Benutzerkompetenzen
	 * zurück. Sollte diese noch nicht initialisiert sein, so wird sie initielisiert.
	 *    
	 * @return die Map von den IDs der Benutzerkompetenzen auf die zugehörigen Benutzerkompetenzen
	 */
	private static @NotNull HashMap<@NotNull Long, @NotNull BenutzerKompetenz> getMapID() {
		if (_mapID.size() == 0)
			for (@NotNull BenutzerKompetenz p : BenutzerKompetenz.values())
				_mapID.put(p.daten.id, p);
		return _mapID;
	}
	

    /**
     * Gibt eine Map von den Benutzerkompetenzen-Gruppen auf die zugehörigen Benutzerkompetenzen
     * zurück. Sollte diese noch nicht initialisiert sein, so wird sie initialisiert.
     *    
     * @return die Map von den Benutzerkompetenzen-Gruppen auf die zugehörigen Benutzerkompetenzen
     */
    private static @NotNull HashMap<@NotNull BenutzerKompetenzGruppe, @NotNull List<@NotNull BenutzerKompetenz>> getMapGruppenZuordnung() {
        if (_mapGruppenZuordnung.size() == 0) {
            for (@NotNull BenutzerKompetenzGruppe g : BenutzerKompetenzGruppe.values())
                _mapGruppenZuordnung.put(g, new Vector<>());
            for (@NotNull BenutzerKompetenz p : BenutzerKompetenz.values()) {
                BenutzerKompetenzGruppe gruppe = BenutzerKompetenzGruppe.getByID(p.daten.gruppe_id);
                if (gruppe == null)
                    gruppe = BenutzerKompetenzGruppe.KEINE;
                List<@NotNull BenutzerKompetenz> liste = _mapGruppenZuordnung.get(gruppe);
                if (liste != null)
                    liste.add(p);
            }
        }
        return _mapGruppenZuordnung;
    }
    

    /** 
     * Gibt die Benutzerkompetenz anhand der übergebenen ID zurück. 
     * 
     * @param id    die ID der Benutzerkompetenz
     *  
     * @return die Benutzerkompetenz oder null, falls die ID fehlerhaft ist
     */
    public static BenutzerKompetenz getByID(long id) {
    	return getMapID().get(id);
    }


    /**
     * Gibt die Liste aller Benutzerkompetenzen zurück, welche der übergebenen Gruppe
     * zugeordnet sind.
     *  
     * @param gruppe   die Benutzerkompetenz-Gruppe
     * 
     * @return die Liste der Benutzerkompetenzen
     */
    public static @NotNull List<@NotNull BenutzerKompetenz> getKompetenzen(@NotNull BenutzerKompetenzGruppe gruppe) {
        List<@NotNull BenutzerKompetenz> liste = getMapGruppenZuordnung().get(gruppe);
        if (liste == null)
            return new Vector<>();
        return liste;
    }

}
