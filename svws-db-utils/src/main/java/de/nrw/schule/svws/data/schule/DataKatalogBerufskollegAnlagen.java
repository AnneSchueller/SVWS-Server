package de.nrw.schule.svws.data.schule;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import de.nrw.schule.svws.core.data.schule.BerufskollegAnlageKatalogEintrag;
import de.nrw.schule.svws.core.types.schule.BerufskollegAnlage;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.db.DBEntityManager;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link BerufskollegAnlageKatalogEintrag}.
 */
public class DataKatalogBerufskollegAnlagen extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link BerufskollegAnlageKatalogEintrag}.
	 * 
	 * @param conn   die Datenbank-Verbindung für den Datenbankzugriff
	 */
	public DataKatalogBerufskollegAnlagen(DBEntityManager conn) {
		super(conn);
	}
	
	@Override
	public Response getAll() {
		Vector<BerufskollegAnlageKatalogEintrag> daten = new Vector<>();
		for (BerufskollegAnlage anlagen : BerufskollegAnlage.values())
			daten.addAll(Arrays.asList(anlagen.historie));
        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
	}

	@Override
	public Response getList() {
		return this.getAll();
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
