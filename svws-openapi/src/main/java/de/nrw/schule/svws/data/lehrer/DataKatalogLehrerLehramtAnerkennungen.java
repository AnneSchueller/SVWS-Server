package de.nrw.schule.svws.data.lehrer;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import de.nrw.schule.svws.core.data.lehrer.LehrerKatalogLehramtAnerkennungEintrag;
import de.nrw.schule.svws.core.types.statkue.LehrerLehramtAnerkennung;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.db.DBEntityManager;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link LehrerKatalogLehramtAnerkennungEintrag}.
 */
public class DataKatalogLehrerLehramtAnerkennungen extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link LehrerKatalogLehramtAnerkennungEintrag}.
	 * 
	 * @param conn   die Datenbank-Verbindung für den Datenbankzugriff
	 */
	public DataKatalogLehrerLehramtAnerkennungen(DBEntityManager conn) {
		super(conn);
	}
	
	@Override
	public Response getAll() {
		Vector<LehrerKatalogLehramtAnerkennungEintrag> daten = new Vector<>();
		for (LehrerLehramtAnerkennung status : LehrerLehramtAnerkennung.values())
			daten.addAll(Arrays.asList(status.historie));
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
