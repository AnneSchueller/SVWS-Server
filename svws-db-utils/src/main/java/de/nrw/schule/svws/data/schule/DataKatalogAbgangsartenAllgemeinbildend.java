package de.nrw.schule.svws.data.schule;

import java.io.InputStream;

import de.nrw.schule.svws.core.data.schule.AbgangsartKatalog;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.json.JsonDaten;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link AbgangsartKatalog}.
 */
public class DataKatalogAbgangsartenAllgemeinbildend extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link AbgangsartKatalog}.
	 */
	public DataKatalogAbgangsartenAllgemeinbildend() {
		super(null);
	}
	
	@Override
	public Response getAll() {
		AbgangsartKatalog daten = JsonDaten.abgangsartenManager.getKatalogAllgemeinbildend();
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
