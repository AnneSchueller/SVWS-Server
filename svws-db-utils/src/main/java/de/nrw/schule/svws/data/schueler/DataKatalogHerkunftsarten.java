package de.nrw.schule.svws.data.schueler;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import de.nrw.schule.svws.core.data.schule.HerkunftsartKatalogEintrag;
import de.nrw.schule.svws.core.types.schueler.Herkunftsarten;
import de.nrw.schule.svws.data.DataManager;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link HerkunftsartKatalogEintrag}.
 */
public class DataKatalogHerkunftsarten extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link HerkunftsartKatalogEintrag}.
	 */
	public DataKatalogHerkunftsarten() {
		super(null);
	}
	
	@Override
	public Response getAll() {
		Vector<HerkunftsartKatalogEintrag> daten = new Vector<>();
		for (Herkunftsarten eintrag : Herkunftsarten.values())
			daten.addAll(Arrays.asList(eintrag.historie));
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
