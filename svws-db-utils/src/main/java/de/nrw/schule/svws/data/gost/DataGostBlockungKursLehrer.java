package de.nrw.schule.svws.data.gost;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import de.nrw.schule.svws.core.data.gost.GostBlockungKursLehrer;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.data.JSONMapper;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungKurs;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungKurslehrer;
import de.nrw.schule.svws.db.dto.current.schild.lehrer.DTOLehrer;
import de.nrw.schule.svws.db.dto.current.schild.schule.DTOEigeneSchule;
import de.nrw.schule.svws.db.utils.OperationError;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link GostBlockungKursLehrer}.
 */
public class DataGostBlockungKursLehrer extends DataManager<Long> {

	private final long idKurs;
	
	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link GostBlockungKursLehrer}.
	 * 
	 * @param conn     die Datenbank-Verbindung für den Datenbankzugriff
	 * @param idKurs   die ID des Kurses
	 */
	public DataGostBlockungKursLehrer(DBEntityManager conn, long idKurs) {
		super(conn);
		this.idKurs = idKurs;
	}
	
	@Override
	public Response getAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Response getList() {
		throw new UnsupportedOperationException();
	}
	
	
	private static GostBlockungKursLehrer dtoMapper(DTOEigeneSchule schule, DTOGostBlockungKurslehrer dto, DTOLehrer lehrer) {
		GostBlockungKursLehrer daten = new GostBlockungKursLehrer();
		daten.id = dto.Lehrer_ID;
		daten.kuerzel = lehrer.Kuerzel;
		daten.vorname = lehrer.Vorname;
		daten.nachname = lehrer.Nachname;
		daten.reihenfolge = dto.Reihenfolge;
		daten.wochenstunden = dto.Wochenstunden;
		daten.istExtern = (lehrer.StammschulNr != null) || (!lehrer.StammschulNr.equals("" + schule.SchulNr));
		return daten;
	}
	

	private DTOGostBlockungKurslehrer getKurslehrer(Long idLehrer) {
		DTOGostBlockungKurs kurs = conn.queryByKey(DTOGostBlockungKurs.class, idKurs);
		if (kurs == null)
			throw OperationError.NOT_FOUND.exception();
		if (idLehrer == null) {
			List<DTOGostBlockungKurslehrer> tmp = conn.queryNamed("DTOGostBlockungKurslehrer.blockung_kurs_id", idKurs, DTOGostBlockungKurslehrer.class);
			if (tmp.size() == 0)
				throw OperationError.NOT_FOUND.exception();
			if (tmp.size() > 1)
				throw OperationError.CONFLICT.exception();
			return tmp.get(0);
		}
		DTOGostBlockungKurslehrer kurslehrer = conn.queryByKey(DTOGostBlockungKurslehrer.class, idKurs, idLehrer);
		if (kurslehrer == null)
			throw OperationError.NOT_FOUND.exception();
		return kurslehrer;
	}
	
	
	@Override
	public Response get(Long idLehrer) {
		try {
			conn.transactionBegin();
			DTOEigeneSchule schule = GostUtils.pruefeSchuleMitGOSt(conn);
			DTOGostBlockungKurslehrer kurslehrer = getKurslehrer(idLehrer);
			DTOLehrer lehrer = conn.queryByKey(DTOLehrer.class, kurslehrer.Lehrer_ID);
			if (lehrer == null)
				return OperationError.NOT_FOUND.getResponse();
			GostBlockungKursLehrer daten = dtoMapper(schule, kurslehrer, lehrer);
	    	conn.transactionCommit();
	        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
		} catch (Exception e) {
			if (e instanceof WebApplicationException webAppException)
				return webAppException.getResponse();
			return OperationError.INTERNAL_SERVER_ERROR.getResponse();
		} finally {
			conn.transactionRollback();
		}
	}



	@Override
	public Response patch(Long idLehrer, InputStream is) {
    	Map<String, Object> map = JSONMapper.toMap(is);
    	if (map.size() <= 0) 
	    	return Response.status(Status.OK).build();
		try {
			conn.transactionBegin();
			DTOGostBlockungKurslehrer kurslehrer = getKurslehrer(idLehrer);
	    	for (Entry<String, Object> entry : map.entrySet()) {
	    		String key = entry.getKey();
	    		Object value = entry.getValue();
	    		switch (key) {
					case "id" -> {
						Long patch_id = JSONMapper.convertToLong(value, true);
						if ((patch_id == null) || (patch_id.longValue() != kurslehrer.Lehrer_ID.longValue()))
							throw OperationError.BAD_REQUEST.exception();
					}
	    			case "kuerzel" -> throw OperationError.BAD_REQUEST.exception();
	    			case "vorname" -> throw OperationError.BAD_REQUEST.exception();
	    			case "nachname" -> throw OperationError.BAD_REQUEST.exception();
	    			case "reihenfolge" -> {
	    				int tmp = JSONMapper.convertToInteger(value, false);
	    				Set<Integer> reihenfolgen = conn.queryNamed("DTOGostBlockungKurslehrer.blockung_kurs_id", idKurs, DTOGostBlockungKurslehrer.class).stream()
	    					.map(kl -> kl.Reihenfolge).filter(kl -> kl != null).collect(Collectors.toSet());
	    				if (reihenfolgen.contains(tmp))
	    					throw OperationError.CONFLICT.exception();
	    				kurslehrer.Reihenfolge = tmp; 
	    			}
	    			case "wochenstunden" -> {
	    				kurslehrer.Wochenstunden = JSONMapper.convertToInteger(value, false);
	    				if ((kurslehrer.Wochenstunden < 1) || (kurslehrer.Wochenstunden > 40)) 
	    					throw OperationError.BAD_REQUEST.exception();
	    			}
	    			case "istExtern" -> throw OperationError.BAD_REQUEST.exception();
	    			default -> throw OperationError.BAD_REQUEST.exception();
	    		}
	    	}
	    	conn.transactionPersist(kurslehrer);
	    	conn.transactionCommit();
		} catch (Exception e) {
			if (e instanceof WebApplicationException webAppException)
				return webAppException.getResponse();
			return OperationError.INTERNAL_SERVER_ERROR.getResponse();
		} finally {
			// Perform a rollback if necessary
			conn.transactionRollback();
		}
    	return Response.status(Status.NO_CONTENT).build();
	}


	/** 
     * Fügt einen weiteren lehrer zu dem Kurs einer Blockung der Gymnasialen Oberstufe hinzu.
	 * 
     * @param idLehrer   die ID des Lehrers
	 * 
	 * @return Eine Response mit dem neuen {@link GostBlockungKursLehrer}-Objekt
	 */
	public Response addKurslehrer(long idLehrer) {
		try {
			conn.transactionBegin();
			// Prüfe, ob die Schule eine gymnasiale Oberstufe hat
			DTOEigeneSchule schule = GostUtils.pruefeSchuleMitGOSt(conn);
			// Prüfe, ob ein Kurs mit der ID für eine Blockung existiert
			DTOGostBlockungKurs kurs = conn.queryByKey(DTOGostBlockungKurs.class, idKurs);
			if (kurs == null)
				throw OperationError.NOT_FOUND.exception();
			// Prüfe, ob ein Lehrer mit der ID existiert
			DTOLehrer lehrer = conn.queryByKey(DTOLehrer.class, idLehrer);
			if (lehrer == null)
				return OperationError.NOT_FOUND.getResponse();
			// Prüfe, ob der Lehrer bereits dem Kurs zugeordnet ist, das darf nicht der Fall sein
			DTOGostBlockungKurslehrer kurslehrer = conn.queryByKey(DTOGostBlockungKurslehrer.class, idKurs, idLehrer);
			if (kurslehrer != null)
				throw OperationError.CONFLICT.exception();
			// Bestimme den niedrigsten Wert für Reihenfolge, der noch nicht genutzt ist und setze diese als Default
			Set<Integer> reihenfolgen = conn.queryNamed("DTOGostBlockungKurslehrer.blockung_kurs_id", idKurs, DTOGostBlockungKurslehrer.class).stream()
				.map(kl -> kl.Reihenfolge).filter(kl -> kl != null).collect(Collectors.toSet());
			int min;
			for (min = 1; true; min++)
				if (!reihenfolgen.contains(min))
					break;
			kurslehrer = new DTOGostBlockungKurslehrer(idKurs, idLehrer, min, kurs.Wochenstunden);
			conn.transactionPersist(kurslehrer);
			conn.transactionCommit();
			GostBlockungKursLehrer daten = dtoMapper(schule, kurslehrer, lehrer);
			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
		} catch (Exception exception) {
			conn.transactionRollback();
			if (exception instanceof IllegalArgumentException e)
				throw OperationError.NOT_FOUND.exception();
			if (exception instanceof WebApplicationException webex)
				return webex.getResponse();
			throw exception;
		}
	}

	
	/** 
     * Entfernt den Lehrer als Kurslehrer bei dem Kurs einer Blockung der 
     * Gymnasialen Oberstufe.
	 * 
     * @param idLehrer   die ID des Lehrers
	 * 
	 * @return die HTTP-Response, welchen den Erfolg der Lösch-Operation angibt. 
	 */
	public Response deleteKurslehrer(long idLehrer) {
		try {
			conn.transactionBegin();
			GostUtils.pruefeSchuleMitGOSt(conn);
			// Prüfe, ob die Schule eine gymnasiale Oberstufe hat
			GostUtils.pruefeSchuleMitGOSt(conn);
			// Prüfe, ob ein Kurs mit der ID für eine Blockung existiert
			DTOGostBlockungKurs kurs = conn.queryByKey(DTOGostBlockungKurs.class, idKurs);
			if (kurs == null)
				throw OperationError.NOT_FOUND.exception();
			// Prüfe, ob der Lehrer überhaupt dem Kurs zugeordnet ist, das muss hier der Fall sein
			DTOGostBlockungKurslehrer kurslehrer = conn.queryByKey(DTOGostBlockungKurslehrer.class, idKurs, idLehrer);
			if (kurslehrer == null)
				throw OperationError.BAD_REQUEST.exception();
			if (!conn.transactionRemove(kurslehrer))
				throw OperationError.INTERNAL_SERVER_ERROR.exception();
			if (!conn.transactionCommit())
				throw OperationError.INTERNAL_SERVER_ERROR.exception();
			return Response.status(Status.NO_CONTENT).type(MediaType.APPLICATION_JSON).build();
		} catch (Exception exception) {
			conn.transactionRollback();
			if (exception instanceof IllegalArgumentException e)
				throw OperationError.NOT_FOUND.exception();
			if (exception instanceof WebApplicationException webex)
				return webex.getResponse();
			throw exception;
		}
	}

}
