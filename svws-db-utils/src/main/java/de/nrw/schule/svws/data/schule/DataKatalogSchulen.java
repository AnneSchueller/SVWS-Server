package de.nrw.schule.svws.data.schule;

import java.io.InputStream;
import java.util.List;

import de.nrw.schule.svws.base.CsvReader;
import de.nrw.schule.svws.core.data.schule.SchulenKatalogEintrag;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.db.utils.OperationError;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link SchulenKatalogEintrag}.
 */
public class DataKatalogSchulen extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link SchulenKatalogEintrag}.
	 */
	public DataKatalogSchulen() {
		super(null);
	}
	
	@Override
	public Response getAll() {
		List<SchulenKatalogEintrag> katalog = CsvReader.fromResource("daten/csv/schulver/Schulen.csv", SchulenKatalogEintrag.class);
    	if (katalog == null)
    		return OperationError.NOT_FOUND.getResponse();
        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(katalog).build();
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