package de.nrw.schule.svws.data.lehrer;

import java.io.InputStream;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import de.nrw.schule.svws.core.data.lehrer.LehrerListeEintrag;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.schild.lehrer.DTOLehrer;
import de.nrw.schule.svws.db.utils.OperationError;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link LehrerListeEintrag}.
 */
public class DataLehrerliste extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link LehrerListeEintrag}.
	 * 
	 * @param conn   die Datenbank-Verbindung für den Datenbankzugriff
	 */
	public DataLehrerliste(DBEntityManager conn) {
		super(conn);
	}
	
	/**
	 * Lambda-Ausdruck zum Umwandeln eines Datenbank-DTOs {@link DTOLehrer} in einen Core-DTO {@link LehrerListeEintrag}.  
	 */
	private Function<DTOLehrer, LehrerListeEintrag> dtoMapper = (DTOLehrer l) -> {
		LehrerListeEintrag eintrag = new LehrerListeEintrag();
		eintrag.id = l.ID;
		eintrag.kuerzel = l.Kuerzel;
		eintrag.titel = l.Titel == null ? "" : l.Titel;
		eintrag.nachname = l.Nachname;
		eintrag.vorname = l.Vorname;
		eintrag.personTyp = l.PersonTyp.kuerzel;
		eintrag.sortierung = l.Sortierung;
		eintrag.istSichtbar = l.Sichtbar;
		eintrag.istAenderbar = l.Aenderbar;
		eintrag.istRelevantFuerStatistik = l.statistikRelevant;
		return eintrag;
	};
	
	/**
	 * Lambda-Ausdruck zum Vergleichen/Sortieren der Core-DTOs {@link LehrerListeEintrag}.  
	 */
	private Comparator<LehrerListeEintrag> dataComparator = (a,b) -> {
		Collator collator = Collator.getInstance(Locale.GERMAN);
		if ((a.kuerzel == null) && (b.kuerzel != null))
			return -1;
		else if ((a.kuerzel != null) && (b.kuerzel == null))
			return 1;
		else if ((a.kuerzel == null) && (b.kuerzel == null))
			return 0;
		int result = collator.compare(a.kuerzel, b.kuerzel);
		if (result == 0) {
    		if ((a.nachname == null) && (b.nachname != null))
    			return -1;
    		else if ((a.nachname != null) && (b.nachname == null))
    			return 1;
    		else if ((a.nachname == null) && (b.nachname == null))
    			return 0;
    		result = collator.compare(a.nachname, b.nachname);
		}
		if (result == 0) {
    		if ((a.vorname == null) && (b.vorname != null))
    			return -1;
    		else if ((a.vorname != null) && (b.vorname == null))
    			return 1;
    		else if ((a.vorname == null) && (b.vorname == null))
    			return 0;
    		result = collator.compare(a.vorname, b.vorname);
		}
		return result;    		
	};

	@Override
	public Response getAll() {
    	List<DTOLehrer> lehrer = conn.queryAll(DTOLehrer.class);
    	if (lehrer == null)
    		return OperationError.NOT_FOUND.getResponse();
    	List<LehrerListeEintrag> daten = lehrer.stream().map(dtoMapper).sorted(dataComparator).collect(Collectors.toList());
        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
	}

	@Override
	public Response getList() {
		List<DTOLehrer> lehrer = conn.queryNamed("DTOLehrer.sichtbar", true, DTOLehrer.class);
    	if (lehrer == null)
    		return OperationError.NOT_FOUND.getResponse();
    	List<LehrerListeEintrag> daten = lehrer.stream().map(dtoMapper).sorted(dataComparator).collect(Collectors.toList());
        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
	}

	@Override
	public Response get(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Response patch(Long id, InputStream is) {
		throw new UnsupportedOperationException();
	}

}
