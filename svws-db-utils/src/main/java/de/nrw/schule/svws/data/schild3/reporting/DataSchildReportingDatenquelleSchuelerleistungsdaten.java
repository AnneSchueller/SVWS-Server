package de.nrw.schule.svws.data.schild3.reporting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import de.nrw.schule.svws.core.data.schild3.SchildReportingSchuelerleistungsdaten;
import de.nrw.schule.svws.core.types.schild3.SchildReportingAttributTyp;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.schild.faecher.DTOFach;
import de.nrw.schule.svws.db.dto.current.schild.kurse.DTOKurs;
import de.nrw.schule.svws.db.dto.current.schild.lehrer.DTOLehrer;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchuelerLeistungsdaten;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchuelerLernabschnittsdaten;
import de.nrw.schule.svws.db.utils.OperationError;

/**
 * Die Definition der Schild-Reporting-Datenquelle "Schuelerleistungsdaten"
 */
public class DataSchildReportingDatenquelleSchuelerleistungsdaten extends DataSchildReportingDatenquelle {

    /**
     * Erstelle eine die Datenquelle Schuelerleistungsdaten
     */
    DataSchildReportingDatenquelleSchuelerleistungsdaten() {
        super(SchildReportingSchuelerleistungsdaten.class);
        this.setMaster("abschnittID", "Schuelerlernabschnitte", "id", SchildReportingAttributTyp.INT);
        // Beispiel für die Einschränkung auf Schulformen: this.restrictTo(Schulform.GY, Schulform.GE);
    }


    @Override
    List<? extends Object> getDatenInteger(DBEntityManager conn, List<Long> params) {
        // Prüfe, ob die Lernabschnittsdaten in der DB vorhanden sind
        Map<Long, DTOSchuelerLernabschnittsdaten> abschnitte = conn
                .queryNamed("DTOSchuelerLernabschnittsdaten.id.multiple", params, DTOSchuelerLernabschnittsdaten.class)
                .stream().collect(Collectors.toMap(a -> a.ID, a -> a));
        for (Long abschnittID : params)
            if (abschnitte.get(abschnittID) == null)
                throw OperationError.NOT_FOUND.exception("Parameter der Abfrage ungültig: Ein Schülerlernabschnitt mit der ID " + abschnittID + " existiert nicht.");
        // Aggregiere die benötigten Daten aus der Datenbank
        List<DTOSchuelerLeistungsdaten> leistungsdaten = conn.queryNamed("DTOSchuelerLeistungsdaten.abschnitt_id.multiple", params, DTOSchuelerLeistungsdaten.class);
        if (leistungsdaten == null)
            return null;
        if (leistungsdaten.size() == 0)
        	return new Vector<SchildReportingSchuelerleistungsdaten>();
        List<Long> idFaecher = leistungsdaten.stream().filter(l -> l.Fach_ID != null).map(l -> l.Fach_ID).distinct().toList();
        Map<Long, DTOFach> mapFaecher = (idFaecher.size() == 0) ? Collections.emptyMap()
        		: conn.queryNamed("DTOFach.id.multiple", idFaecher, DTOFach.class)
        			.stream().collect(Collectors.toMap(f -> f.ID, f -> f));
        List<Long> idLehrer = leistungsdaten.stream().filter(l -> l.Fachlehrer_ID != null).map(l -> l.Fachlehrer_ID).distinct().toList();
        Map<Long, DTOLehrer> mapLehrer = (idLehrer.size() == 0) ? Collections.emptyMap()
        		: conn.queryNamed("DTOLehrer.id.multiple", idLehrer, DTOLehrer.class)
        			.stream().collect(Collectors.toMap(l -> l.ID, l -> l));
        List<Long> idKurse = leistungsdaten.stream().filter(l -> l.Kurs_ID != null).map(l -> l.Kurs_ID).distinct().toList();
        Map<Long, DTOKurs> mapKurse = (idKurse.size() == 0) ? Collections.emptyMap()
        		: conn.queryNamed("DTOKurs.id.multiple", idKurse, DTOKurs.class)
        			.stream().collect(Collectors.toMap(k -> k.ID, k -> k));
        // Erzeuge die Core-DTOs für das Ergebnis der Datenquelle
        Vector<SchildReportingSchuelerleistungsdaten> result = new Vector<>();
        for (DTOSchuelerLeistungsdaten dto : leistungsdaten) {
        	DTOFach dtoFach = mapFaecher.get(dto.Fach_ID);
            if (dtoFach == null)
                throw OperationError.INTERNAL_SERVER_ERROR.exception("Daten inkonsistent: Fach mit der ID " + dto.Fach_ID + " konnte nicht für die Leistungsdaten mit der ID " + dto.ID + " gefunden werden.");
            String lehrerKuerzel = null;
            if (dto.Fachlehrer_ID != null) {
	        	DTOLehrer dtoLehrer = mapLehrer.get(dto.Fachlehrer_ID);
	            if (dtoLehrer == null)
	                throw OperationError.INTERNAL_SERVER_ERROR.exception("Daten inkonsistent: Fachlehrer mit der ID " + dto.Fachlehrer_ID + " konnte nicht für die Leistungsdaten mit der ID " + dto.ID + " gefunden werden.");
	            lehrerKuerzel = dtoLehrer.Kuerzel;
            }
        	DTOKurs dtoKurs = mapKurse.get(dto.Kurs_ID);
            SchildReportingSchuelerleistungsdaten data = new SchildReportingSchuelerleistungsdaten();
            data.id = dto.ID;
            data.abschnittID = dto.Abschnitt_ID;
            data.fachID = dto.Fach_ID;
            data.fachKuerzel = dtoFach.Kuerzel;
            data.fach = dtoFach.Bezeichnung;
            data.lehrerID = dto.Fachlehrer_ID;
            data.lehrerKuerzel = lehrerKuerzel;
            data.kursID = dto.Kurs_ID;
            data.kurs = dtoKurs == null ? "" : dtoKurs.KurzBez;
            data.kursart = dto.Kursart;
            data.kursartAllg = dtoKurs == null ? dto.KursartAllg : dtoKurs.KursartAllg;
            data.note = dto.NotenKrz.text;
            data.noteKuerzel = dto.NotenKrz.kuerzel;
            data.notePunkte = dto.NotenKrz.notenpunkte;
			data.sortierungAllg = dtoFach.SortierungAllg;
			data.sortierungSekII = dtoFach.SortierungSekII;
            result.add(data);
        }
		result.sort(Comparator.comparing(ld -> ld.sortierungAllg));

        // Geben die Ergebnis-Liste mit den Core-DTOs zurück
        return result;
    }

}
