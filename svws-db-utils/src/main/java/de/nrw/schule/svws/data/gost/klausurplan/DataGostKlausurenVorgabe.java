package de.nrw.schule.svws.data.gost.klausurplan;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.function.Function;

import de.nrw.schule.svws.core.data.gost.klausuren.GostKlausurtermin;
import de.nrw.schule.svws.core.data.gost.klausuren.GostKlausurvorgabe;
import de.nrw.schule.svws.core.types.gost.GostHalbjahr;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.data.JSONMapper;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.gost.klausurplanung.DTOGostKlausurenVorgaben;
import de.nrw.schule.svws.db.dto.current.svws.db.DTODBAutoInkremente;
import de.nrw.schule.svws.db.utils.OperationError;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den Core-DTO
 * {@link GostKlausurvorgabe}.
 */
public class DataGostKlausurenVorgabe extends DataManager<Long> {

	private int _abiturjahr;

	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO
	 * {@link GostKlausurtermin}.
	 * 
	 * @param conn       die Datenbank-Verbindung für den Datenbankzugriff
	 * @param abiturjahr das Jahr, in welchem der Jahrgang Abitur machen wird
	 */
	public DataGostKlausurenVorgabe(DBEntityManager conn, int abiturjahr) {
		super(conn);
		_abiturjahr = abiturjahr;
	}

	@Override
	public Response getAll() {
		return this.getList();
	}

	/**
	 * Lambda-Ausdruck zum Umwandeln eines Datenbank-DTOs {@link DTOGostKlausurenVorgaben} in
	 * einen Core-DTO {@link GostKlausurvorgabe}.
	 */
	private Function<DTOGostKlausurenVorgaben, GostKlausurvorgabe> dtoMapper = (DTOGostKlausurenVorgaben z) -> {
		GostKlausurvorgabe daten = new GostKlausurvorgabe();
		daten.idVorgabe = z.ID;
		daten.abiJahrgang = z.Abi_Jahrgang;
		daten.idFach = z.Fach_ID;
		daten.kursartAllg = z.KursartAllg;
		daten.halbjahr = z.Halbjahr.id;
		daten.quartal = z.Quartal;
		daten.bemerkungVorgabe = z.Bemerkungen;
		daten.auswahlzeit = z.Auswahlzeit;
		daten.dauer = z.Dauer;
		daten.istAudioNotwendig = z.IstAudioNotwendig;
		daten.istVideoNotwendig = z.IstVideoNotwendig;
		daten.istMdlPruefung = z.IstMdlPruefung;
		return daten;
	};

	/**
	 * Gibt die Liste der Klausurvorgaben einer Jahrgangsstufe im übergebenen
	 * Gost-Halbjahr zurück.
	 * 
	 * @param halbjahr das Gost-Halbjahr
	 * 
	 * @return die Liste der Kursklausuren
	 */
	private List<GostKlausurvorgabe> getKlausurvorgaben(int halbjahr) {
		List<DTOGostKlausurenVorgaben> vorgaben = conn.query("SELECT v FROM DTOGostKlausurenVorgaben v WHERE v.Abi_Jahrgang = :jgid AND v.Halbjahr = :hj", DTOGostKlausurenVorgaben.class)
				.setParameter("jgid", _abiturjahr).setParameter("hj", GostHalbjahr.fromID(halbjahr)).getResultList();
		List<GostKlausurvorgabe> daten = new Vector<>();
		for (DTOGostKlausurenVorgaben v : vorgaben)
			daten.add(dtoMapper.apply(v));
		return daten;
	}

	@Override
	public Response get(Long halbjahr) {
		// Kursklausuren für einen Abiturjahrgang in einem Gost-Halbjahr
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(this.getKlausurvorgaben(halbjahr.intValue())).build();
	}

	@Override
	public Response patch(Long id, InputStream is) {
    	Map<String, Object> map = JSONMapper.toMap(is);
    	if (map.size() > 0) {
    		try {
    			conn.transactionBegin();
    			DTOGostKlausurenVorgaben vorgabe = conn.queryByKey(DTOGostKlausurenVorgaben.class, id);
		    	if (vorgabe == null)
		    		throw OperationError.NOT_FOUND.exception();
		    	for (Entry<String, Object> entry : map.entrySet()) {
		    		String key = entry.getKey();
		    		Object value = entry.getValue();
		    		switch (key) {
						case "idVorgabe" -> {
							Long patch_id = JSONMapper.convertToLong(value, true);
							if ((patch_id == null) || (patch_id.longValue() != id.longValue()))
								throw OperationError.BAD_REQUEST.exception();
						}
						case "abiJahrgang" -> {
							int patch_abijahr = JSONMapper.convertToInteger(value, false);
							if ((patch_abijahr != vorgabe.Abi_Jahrgang))
								throw OperationError.BAD_REQUEST.exception();
						}
						case "halbjahr" -> {
							int patch_halbjahr = JSONMapper.convertToInteger(value, false);
							if ((patch_halbjahr != vorgabe.Halbjahr.id))
								throw OperationError.BAD_REQUEST.exception();
						}
						case "quartal" -> vorgabe.Quartal = JSONMapper.convertToInteger(value, false);
						case "idFach" -> vorgabe.Fach_ID = JSONMapper.convertToLong(value, false);
						case "kursartAllg" -> vorgabe.KursartAllg = JSONMapper.convertToString(value, false, false);
						case "dauer" -> vorgabe.Dauer = JSONMapper.convertToInteger(value, false);
						case "auswahlzeit" -> vorgabe.Auswahlzeit = JSONMapper.convertToInteger(value, false);
						case "istMdlPruefung" -> vorgabe.IstMdlPruefung = JSONMapper.convertToBoolean(value, false);
						case "istAudioNotwendig" -> vorgabe.IstAudioNotwendig = JSONMapper.convertToBoolean(value, false);
						case "istVideoNotwendig" -> vorgabe.IstVideoNotwendig = JSONMapper.convertToBoolean(value, false);
						case "bemerkungVorgabe" -> vorgabe.Bemerkungen = JSONMapper.convertToString(value, true, true);
		    			
		    			default -> throw OperationError.BAD_REQUEST.exception();
		    		}
		    	}
		    	conn.transactionPersist(vorgabe);
		    	conn.transactionCommit();
    		} catch (Exception e) {
    			if (e instanceof WebApplicationException webAppException)
    				return webAppException.getResponse();
				return OperationError.INTERNAL_SERVER_ERROR.getResponse();
    		} finally {
    			// Perform a rollback if necessary
    			conn.transactionRollback();
    		}
    	}
    	return Response.status(Status.OK).build();
	}
	
	@Override
	public Response getList() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Erstellt eine neue Gost-Klausurvorgabe *
	 * 
	 * @param is       Das JSON-Objekt mit den Daten
	 * 
	 * @return Eine Response mit der neuen Gost-Klausurvorgabe
	 */
	public Response create(InputStream is) {
		DTOGostKlausurenVorgaben vorgabe = null;
		try {
			conn.transactionBegin();
			// Bestimme die ID des neuen Klausurtermins
			DTODBAutoInkremente lastID = conn.queryByKey(DTODBAutoInkremente.class, "Gost_Klausuren_Vorgaben");
			Long ID = lastID == null ? 1 : lastID.MaxID + 1;
			
			 int abi_Jahrgang = -1;
			 GostHalbjahr halbjahr = GostHalbjahr.EF1;
			 int quartal = -1;
			 long fach_ID = -1;
			 String kursartAllg = "";
			 int dauer = 0;
			 int auswahlzeit = 0;
			 boolean istMdlPruefung = false;
			 boolean istAudioNotwendig = false;
			 boolean istVideoNotwendig = false;
			 String bemerkungen = null;
			
			Map<String, Object> map = JSONMapper.toMap(is);
			if (map.size() > 0) {
				for (Entry<String, Object> entry : map.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					switch (key) {
					case "abiJahrgang" -> abi_Jahrgang = JSONMapper.convertToInteger(value, false);
					case "halbjahr" -> halbjahr = GostHalbjahr.fromID(JSONMapper.convertToInteger(value, false));
					case "quartal" -> quartal = JSONMapper.convertToInteger(value, false);
					case "idFach" -> fach_ID = JSONMapper.convertToLong(value, false);
					case "kursartAllg" -> kursartAllg = JSONMapper.convertToString(value, false, false);
					case "dauer" -> dauer = JSONMapper.convertToInteger(value, false);
					case "auswahlzeit" -> auswahlzeit = JSONMapper.convertToInteger(value, false);
					case "istMdlPruefung" -> istMdlPruefung = JSONMapper.convertToBoolean(value, false);
					case "istAudioNotwendig" -> istAudioNotwendig = JSONMapper.convertToBoolean(value, false);
					case "istVideoNotwendig" -> istVideoNotwendig = JSONMapper.convertToBoolean(value, false);
					case "bemerkungVorgabe" -> bemerkungen = JSONMapper.convertToString(value, true, true);
					case "idVorgabe" -> key = key;
					default -> throw OperationError.BAD_REQUEST.exception();
					}
				}
			}	
			vorgabe = new DTOGostKlausurenVorgaben(ID, abi_Jahrgang, halbjahr, quartal, fach_ID, kursartAllg, dauer, auswahlzeit, istMdlPruefung, istAudioNotwendig, istVideoNotwendig);
			vorgabe.Bemerkungen = bemerkungen;
			conn.transactionPersist(vorgabe);
			if (!conn.transactionCommit())
				return OperationError.CONFLICT.getResponse("Datenbankfehler beim Persistieren der Gost-Klausurvorgabe");
		} catch (Exception e) {
			if (e instanceof WebApplicationException webApplicationException)
				return webApplicationException.getResponse();
			return OperationError.INTERNAL_SERVER_ERROR.getResponse();
		} finally {
			conn.transactionRollback();
		}

		GostKlausurvorgabe daten = dtoMapper.apply(vorgabe);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();

	}
	
	/**
	 * Löscht eine Gost-Klausurvorgabe *
	 * 
	 * @param id 	die ID der zu löschenden Klausurvorgabe 
	 * 
	 * @return die Response
	 */
	public Response delete(Long id) {
		// TODO use transaction
		// Bestimme den Termin
		DTOGostKlausurenVorgaben vorgabe = conn.queryByKey(DTOGostKlausurenVorgaben.class, id);
		if (vorgabe == null)
			return OperationError.NOT_FOUND.getResponse();
		// Entferne den Termin
		conn.remove(vorgabe);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(id).build();
	}

}
