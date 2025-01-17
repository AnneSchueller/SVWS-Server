package de.nrw.schule.svws.data.schule;

import java.io.InputStream;
import java.util.Vector;

import de.nrw.schule.svws.core.data.schule.NotenKatalogEintrag;
import de.nrw.schule.svws.core.types.Note;
import de.nrw.schule.svws.data.DataManager;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link NotenKatalogEintrag}.
 */
public class DataKatalogNoten extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link NotenKatalogEintrag}.
	 */
	public DataKatalogNoten() {
		super(null);
	}
	
	@Override
	public Response getAll() {
		Vector<NotenKatalogEintrag> daten = new Vector<>();
		for (Note note : Note.values())
			daten.add(note.getKatalogEintrag());
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
