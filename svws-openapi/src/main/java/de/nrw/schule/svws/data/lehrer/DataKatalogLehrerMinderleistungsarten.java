package de.nrw.schule.svws.data.lehrer;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import de.nrw.schule.svws.core.data.lehrer.LehrerKatalogMinderleistungsartEintrag;
import de.nrw.schule.svws.core.types.statkue.LehrerMinderleistungArt;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.db.DBEntityManager;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link LehrerKatalogMinderleistungsartEintrag}.
 */
public class DataKatalogLehrerMinderleistungsarten extends DataManager<Long> {

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link LehrerKatalogMinderleistungsartEintrag}.
	 * 
	 * @param conn   die Datenbank-Verbindung für den Datenbankzugriff
	 */
	public DataKatalogLehrerMinderleistungsarten(DBEntityManager conn) {
		super(conn);
	}
	
	@Override
	public Response getAll() {
		Vector<LehrerKatalogMinderleistungsartEintrag> daten = new Vector<>();
		for (LehrerMinderleistungArt status : LehrerMinderleistungArt.values())
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
