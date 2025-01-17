package de.nrw.schule.svws.data.gost;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import de.nrw.schule.svws.core.data.schueler.SchuelerListeEintrag;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.data.schueler.DataSchuelerliste;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchueler;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchuelerLernabschnittsdaten;
import de.nrw.schule.svws.db.dto.current.views.gost.DTOViewGostSchuelerAbiturjahrgang;
import de.nrw.schule.svws.db.utils.OperationError;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link SchuelerListeEintrag} für die Schüler eines Abiturjahrgangs 
 * der gymnasialen Oberstufe.
 */
public class DataGostJahrgangSchuelerliste extends DataManager<Integer> {

	/** der Abiturjahrgang */
	protected Integer abijahrgang;
	
	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link SchuelerListeEintrag}.
	 * 
	 * @param conn          die Datenbank-Verbindung für den Datenbankzugriff
	 * @param abijahrgang   der Abiturjahrgang 
	 */
	public DataGostJahrgangSchuelerliste(DBEntityManager conn, Integer abijahrgang) {
		super(conn);
		this.abijahrgang = abijahrgang;
	}

	
	/**
	 * Ermittelt die Schüler des Abiturjahrgangs
	 *  
	 * @return die Liste der Schüler
	 * 
	 * @throws WebApplicationException   für den Fall, dass keine Schüler für den Abiturjahrgang bestimmt werden konnten
	 */
	List<DTOSchueler> getSchuelerDTOs() throws WebApplicationException {
		if (abijahrgang == null)
			throw OperationError.NOT_FOUND.exception();
		List<DTOViewGostSchuelerAbiturjahrgang> schuelerAbijahrgang = conn.queryNamed(
			"DTOViewGostSchuelerAbiturjahrgang.abiturjahr", abijahrgang, DTOViewGostSchuelerAbiturjahrgang.class);
		if (schuelerAbijahrgang == null)
			throw OperationError.NOT_FOUND.exception();
		if (schuelerAbijahrgang.size() == 0)
			return new Vector<>();
		List<Long> schuelerIDs = schuelerAbijahrgang.stream().map(s -> s.ID).collect(Collectors.toList());
		List<DTOSchueler> schuelerListe = conn.queryNamed("DTOSchueler.id.multiple", schuelerIDs, DTOSchueler.class);
		if ((schuelerListe == null) || (schuelerListe.size() == 0))
			throw OperationError.NOT_FOUND.exception();
		return schuelerListe;
	}
	

	@Override
	public Response getAll() {
		GostUtils.pruefeSchuleMitGOSt(conn);

    	// Bestimme alle Schüler-IDs für den Abiturjahrgang der Blockung
		List<DTOSchueler> schuelerListe = getSchuelerDTOs();
		if (schuelerListe.size() == 0) {
			List<SchuelerListeEintrag> daten = new Vector<>();
			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
		}
		List<Long> schuelerIDs = schuelerListe.stream().map(s -> s.ID).toList();

    	// Bestimme die aktuellen Lernabschnitte für die Schüler
		TypedQuery<DTOSchuelerLernabschnittsdaten> queryDtoSchuelerLernabschnitte = conn.query(
				"SELECT l FROM DTOSchueler s JOIN DTOSchuelerLernabschnittsdaten l ON "
				+ "s.ID IN :value AND s.ID = l.Schueler_ID AND s.Schuljahresabschnitts_ID = l.Schuljahresabschnitts_ID AND l.WechselNr IS NULL", DTOSchuelerLernabschnittsdaten.class
		);
		Map<Long, DTOSchuelerLernabschnittsdaten> mapAktAbschnitte = queryDtoSchuelerLernabschnitte
				.setParameter("value", schuelerIDs)
				.getResultList().stream().collect(Collectors.toMap(l -> l.Schueler_ID, l -> l));

		// Erstelle die Schülerliste
    	List<SchuelerListeEintrag> daten = schuelerListe.stream()
        		.map(s -> DataSchuelerliste.erstelleSchuelerlistenEintrag(s, mapAktAbschnitte.get(s.ID)))
        		.sorted(DataSchuelerliste.dataComparator)
    	    	.collect(Collectors.toList());
        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
	}

	@Override
	public Response getList() {
		return this.getAll();
	}

	@Override
	public Response get(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Response patch(Integer id, InputStream is) {
		throw new UnsupportedOperationException();
	}
	
}
