package de.nrw.schule.svws.data.gost;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import de.nrw.schule.svws.core.data.gost.GostBlockungRegel;
import de.nrw.schule.svws.core.types.gost.GostKursart;
import de.nrw.schule.svws.core.types.kursblockung.GostKursblockungRegelParameterTyp;
import de.nrw.schule.svws.core.types.kursblockung.GostKursblockungRegelTyp;
import de.nrw.schule.svws.data.DataManager;
import de.nrw.schule.svws.data.JSONMapper;
import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockung;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungKurs;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungRegel;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungRegelParameter;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungSchiene;
import de.nrw.schule.svws.db.dto.current.gost.kursblockung.DTOGostBlockungZwischenergebnis;
import de.nrw.schule.svws.db.dto.current.schild.schueler.DTOSchueler;
import de.nrw.schule.svws.db.dto.current.svws.db.DTODBAutoInkremente;
import de.nrw.schule.svws.db.dto.current.views.gost.DTOViewGostSchuelerAbiturjahrgang;
import de.nrw.schule.svws.db.utils.OperationError;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Diese Klasse erweitert den abstrakten {@link DataManager} für den
 * Core-DTO {@link GostBlockungRegel}.
 */
public class DataGostBlockungRegel extends DataManager<Long> {

	/** Ein Mapper von den DB-DTOs der Blockungsregeln ({@link DTOGostBlockungRegel}, {@link DTOGostBlockungRegelParameter}) zu dem Core-Type {@link GostBlockungRegel}. */
	public static BiFunction<DTOGostBlockungRegel, List<DTOGostBlockungRegelParameter>, GostBlockungRegel> dtoMapper = (regel, params) -> {
		GostBlockungRegel daten = new GostBlockungRegel();
		daten.id = regel.ID;
		daten.typ = regel.Typ.typ;
		if ((params != null) && (params.size() > 0))
			daten.parameter.addAll(params.stream().sorted((a, b) -> Integer.compare(a.Nummer, b.Nummer)).map(r -> r.Parameter).collect(Collectors.toList()));
		return daten;
	};


	/**
	 * Erstellt einen neuen {@link DataManager} für den Core-DTO {@link GostBlockungRegel}.
	 * 
	 * @param conn   die Datenbank-Verbindung für den Datenbankzugriff
	 */
	public DataGostBlockungRegel(DBEntityManager conn) {
		super(conn);
	}
	
	@Override
	public Response getAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Response getList() {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public Response get(Long id) {
		GostUtils.pruefeSchuleMitGOSt(conn);
		// Bestimme die Regel der Blockung
		DTOGostBlockungRegel regel = conn.queryByKey(DTOGostBlockungRegel.class, id);
		if (regel == null)
			return OperationError.NOT_FOUND.getResponse();
		List<DTOGostBlockungRegelParameter> params = conn.queryNamed("DTOGostBlockungRegelParameter.regel_id", regel.ID, DTOGostBlockungRegelParameter.class);
		GostBlockungRegel daten = dtoMapper.apply(regel, params);
        return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
	}


	@Override
	public Response patch(Long id, InputStream is) {
    	Map<String, Object> map = JSONMapper.toMap(is);
    	if (map.size() <= 0) 
	    	return Response.status(Status.OK).build();
		try {
			conn.transactionBegin();
			GostUtils.pruefeSchuleMitGOSt(conn);
			// Bestimme die Regel der Blockung
			DTOGostBlockungRegel regel = conn.queryByKey(DTOGostBlockungRegel.class, id);
			if (regel == null)
				return OperationError.NOT_FOUND.getResponse();
	        // Prüfe, ob die Blockung nur das Vorlage-Ergebnis hat
	        DTOGostBlockung blockung = conn.queryByKey(DTOGostBlockung.class, regel.Blockung_ID);
	        DTOGostBlockungZwischenergebnis vorlage = DataGostBlockungsdaten.pruefeNurVorlageErgebnis(conn, blockung);
	        if (vorlage == null)
	        	throw OperationError.BAD_REQUEST.exception("Die Regel kann nicht angepasst werden, da bei der Blockungsdefinition schon berechnete Ergebnisse existieren.");
	    	for (Entry<String, Object> entry : map.entrySet()) {
	    		String key = entry.getKey();
	    		Object value = entry.getValue();
	    		switch (key) {
					case "id" -> {
						Long patch_id = JSONMapper.convertToLong(value, true);
						if ((patch_id == null) || (patch_id.longValue() != id.longValue()))
							throw OperationError.BAD_REQUEST.exception();
					}
	    			case "typ" -> {
						Integer patch_typ = JSONMapper.convertToInteger(value, true);
						if ((patch_typ == null) || (patch_typ != regel.Typ.typ))
							throw OperationError.BAD_REQUEST.exception();
	    			}
	    			case "parameter" -> {
	    				if (!(value instanceof List))
	    					throw OperationError.BAD_REQUEST.exception();
	    				@SuppressWarnings("unchecked")
						List<? extends Number> params = (List<? extends Number>)value;
	    				// Überprüfe zunächst die Anzahl der Parameter
	    				int pcount = regel.Typ.getParamCount();
	    				if (pcount != params.size())
	    					throw OperationError.BAD_REQUEST.exception();
	    				// Aktualisiere die Parameter
	    				for (int i = 0; i < pcount; i++) {
	    					// Bestimme Typ und Wert
	    					GostKursblockungRegelParameterTyp ptype = regel.Typ.getParamType(i);
	    					long pvalue = params.get(i).longValue();
	    					// Überprüfe den Parameter-Wert, ob dieser einen gültigen Wert für die Blockung enthält
	    					switch (ptype) {
								case KURSART -> {
									GostKursart kursart = GostKursart.fromIDorNull((int)pvalue);
									if (kursart == null)
										throw OperationError.BAD_REQUEST.exception();
								}
								case KURS_ID -> {
									DTOGostBlockungKurs kurs = conn.queryByKey(DTOGostBlockungKurs.class, pvalue);
									if ((kurs == null) || (kurs.Blockung_ID != regel.Blockung_ID))
										throw OperationError.BAD_REQUEST.exception();
								}
								case SCHIENEN_NR -> {
									List<DTOGostBlockungSchiene> dtos = conn.queryNamed("DTOGostBlockungSchiene.blockung_id", regel.Blockung_ID, DTOGostBlockungSchiene.class);
									if ((dtos == null) || (dtos.size() <= 0))
										throw OperationError.BAD_REQUEST.exception();
									Set<Integer> schienen = dtos.stream().map(s -> s.Nummer).collect(Collectors.toSet());
									if (!schienen.contains((int)pvalue))
										throw OperationError.BAD_REQUEST.exception();
								}
								case SCHUELER_ID -> {
									DTOSchueler schueler = conn.queryByKey(DTOSchueler.class, pvalue);
									if (schueler == null)
										throw OperationError.BAD_REQUEST.exception();
								}
								case BOOLEAN -> {
									if ((pvalue < 0) || (pvalue > 1))
										throw OperationError.BAD_REQUEST.exception();
								}
								default -> throw OperationError.BAD_REQUEST.exception();
	    					}
	    					// Aktualisiere den Parameter-Wert in der Datenbank, sofern er sich geändert hat
	    					DTOGostBlockungRegelParameter param = conn.queryByKey(DTOGostBlockungRegelParameter.class, id, i);
							if (param.Parameter != pvalue) {
								param.Parameter = pvalue;
								conn.transactionPersist(param);
							}
	    				}
	    			}
	    			default -> throw OperationError.BAD_REQUEST.exception();
	    		}
	    	}
	    	conn.transactionCommit();
		} catch (Exception e) {
			if (e instanceof WebApplicationException webAppException)
				return webAppException.getResponse();
			return OperationError.INTERNAL_SERVER_ERROR.getResponse();
		} finally {
			// Perform a rollback if necessary
			conn.transactionRollback();
		}
    	return Response.status(Status.OK).build();
	}


	/** 
     * Fügt eine Regel mit Default-Werten zu einer Blockung der Gymnasialen Oberstufe hinzu.
	 * 
     * @param idBlockung       die ID der Blockung
	 * @param idRegelTyp       die ID des Typs der Blockungsregel (siehe {@link GostKursblockungRegelTyp})
	 * @param regelParameter   die Parameter der Regel oder null, falls Default-Paramater verwendet werden sollen
	 * 
	 * @return Eine Response mit der neuen Regel 
	 */
	public Response addRegel(long idBlockung, int idRegelTyp, List<Long> regelParameter) {
		try {
			conn.transactionBegin();
			// Prüfe, ob die Schule eine gymnasiale Oberstufe hat
			GostUtils.pruefeSchuleMitGOSt(conn);
	        // Prüfe, ob die Blockung nur das Vorlage-Ergebnis hat
	        DTOGostBlockung blockung = conn.queryByKey(DTOGostBlockung.class, idBlockung);
	        DTOGostBlockungZwischenergebnis vorlage = DataGostBlockungsdaten.pruefeNurVorlageErgebnis(conn, blockung);
	        if (vorlage == null)
	        	throw OperationError.BAD_REQUEST.exception("Die Regel kann nicht hinzugefügt werden, da bei der Blockungsdefinition schon berechnete Ergebnisse existieren.");
			// Prüfe ob die ID des Typs korrekt ist
			GostKursblockungRegelTyp regelTyp = GostKursblockungRegelTyp.fromTyp(idRegelTyp);
			if (regelTyp == GostKursblockungRegelTyp.UNDEFINIERT)
				throw OperationError.CONFLICT.exception();
			// Bestimme die ID, für welche der Datensatz eingefügt wird
			DTODBAutoInkremente dbRegelID = conn.queryByKey(DTODBAutoInkremente.class, "Gost_Blockung_Regeln");
			long idRegel = dbRegelID == null ? 1 : dbRegelID.MaxID + 1;
			// Füge die Regel hinzu
	    	DTOGostBlockungRegel regel = new DTOGostBlockungRegel(idRegel, idBlockung, regelTyp);
	    	conn.transactionPersist(regel);
	    	GostBlockungRegel daten = new GostBlockungRegel();
	    	daten.id = idRegel;
	    	daten.typ = regelTyp.typ;
	    	if ((regelParameter != null) && (regelTyp.getParamCount() != regelParameter.size()))
				throw OperationError.CONFLICT.exception();
	    	// Füge Default-Parameter zu der Regel hinzu.
	    	for (int i = 0; i < regelTyp.getParamCount(); i++) {
	    		GostKursblockungRegelParameterTyp paramType = regelTyp.getParamType(i);
	    		long paramValue;
	    		if (regelParameter == null) {
		    		paramValue = switch (paramType) {
						case KURSART -> GostKursart.LK.id;
						case KURS_ID -> {
					    	List<DTOGostBlockungKurs> kurse = conn.queryNamed("DTOGostBlockungKurs.blockung_id", idBlockung, DTOGostBlockungKurs.class);
							if ((kurse == null) || (kurse.size() == 0))
								throw OperationError.NOT_FOUND.exception();
							yield kurse.get(0).ID;
						}
						case SCHIENEN_NR -> {
							Optional<Integer> minSchiene = conn.queryNamed("DTOGostBlockungSchiene.blockung_id", idBlockung, DTOGostBlockungSchiene.class).stream().map(s -> s.Nummer).min((a,b) -> Integer.compare(a, b));
							if (minSchiene.isEmpty())
								throw OperationError.NOT_FOUND.exception();
							yield minSchiene.get();
						}
						case SCHUELER_ID -> {
							List<DTOViewGostSchuelerAbiturjahrgang> schueler = conn.queryNamed("DTOViewGostSchuelerAbiturjahrgang.abiturjahr", blockung.Abi_Jahrgang, DTOViewGostSchuelerAbiturjahrgang.class);
							if ((schueler == null) || (schueler.size() == 0))
								throw OperationError.NOT_FOUND.exception();
							yield schueler.get(0).ID;
						}
						case BOOLEAN -> {
							yield 0L;
						}
		    		};
	    		} else {
					Long tmp = regelParameter.get(i);
					paramValue = tmp;
		    		switch (paramType) {
						case KURSART -> {
							if (GostKursart.fromIDorNull(tmp.intValue()) == null)
								throw OperationError.NOT_FOUND.exception();
						}
						case KURS_ID -> {
							DTOGostBlockungKurs kurs = conn.queryByKey(DTOGostBlockungKurs.class, tmp);
							if (kurs == null)
								throw OperationError.NOT_FOUND.exception();
						}
						case SCHIENEN_NR -> {
							List<DTOGostBlockungSchiene> schienen = conn.queryList("SELECT e FROM DTOGostBlockungSchiene e WHERE e.Blockung_ID = ?1 AND e.Nummer = ?2", 
									DTOGostBlockungSchiene.class, idBlockung, tmp);
							if (schienen.isEmpty())
								throw OperationError.NOT_FOUND.exception();
						}
						case SCHUELER_ID -> {
							List<DTOViewGostSchuelerAbiturjahrgang> schueler = conn.queryList("SELECT e FROM DTOViewGostSchuelerAbiturjahrgang e WHERE e.Abiturjahr = ?1 AND e.ID = ?2", 
									DTOViewGostSchuelerAbiturjahrgang.class, blockung.Abi_Jahrgang, tmp);
							if ((schueler == null) || (schueler.size() == 0))
								throw OperationError.NOT_FOUND.exception();
						}
						case BOOLEAN -> {
							if ((paramValue < 0) || (paramValue > 1))
								throw OperationError.NOT_FOUND.exception();
						}
		    		}
	    		}
	    		DTOGostBlockungRegelParameter param = new DTOGostBlockungRegelParameter(idRegel, i, paramValue);
	    		conn.transactionPersist(param);
	    		daten.parameter.add(paramValue);
	    	}
			conn.transactionCommit();
			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
		} catch (Exception exception) {
			conn.transactionRollback();
			if (exception instanceof IllegalArgumentException e)
				throw OperationError.NOT_FOUND.exception(e);
			if (exception instanceof WebApplicationException webex)
				return webex.getResponse();
			throw exception;
		}
	}


	/**
	 * Löscht eine Regel einer Blockung der Gymnasialen Oberstufe
	 * 
	 * @param id   die ID der Regel
	 * 
	 * @return die HTTP-Response, welchen den Erfolg der Lösch-Operation angibt.
	 */
	public Response delete(Long id) {
		try {
			// Prüfe, ob die Schule eine gymnasiale Oberstufe hat
			conn.transactionBegin();
			GostUtils.pruefeSchuleMitGOSt(conn);
			// Bestimme die Regel
			DTOGostBlockungRegel regel = conn.queryByKey(DTOGostBlockungRegel.class, id);
			if (regel == null)
	    		throw OperationError.NOT_FOUND.exception();
	        // Prüfe, ob die Blockung nur das Vorlage-Ergebnis hat
	        DTOGostBlockung blockung = conn.queryByKey(DTOGostBlockung.class, regel.Blockung_ID);
	        DTOGostBlockungZwischenergebnis vorlage = DataGostBlockungsdaten.pruefeNurVorlageErgebnis(conn, blockung);
	        if (vorlage == null)
	        	throw OperationError.BAD_REQUEST.exception("Die Regel kann nicht entfernt werden, da bei der Blockungsdefinition schon berechnete Ergebnisse existieren.");
			// Bestimme die Regel-Parameter (diese werden beim Entfernen der Regel automatisch mit entfernt.
			GostBlockungRegel daten = new GostBlockungRegel();
			daten.id = id;
	    	List<DTOGostBlockungRegelParameter> params = conn.queryNamed("DTOGostBlockungRegelParameter.regel_id", id, DTOGostBlockungRegelParameter.class);
	    	if ((params == null) || (params.size() <= 0))
	    		throw OperationError.NOT_FOUND.exception();
	    	params.sort((a,b) -> Integer.compare(a.Nummer, b.Nummer));
			for (DTOGostBlockungRegelParameter param : params)
				daten.parameter.add(param.Parameter);
			daten.typ = regel.Typ.typ;
			// Entferne die Regel
			conn.transactionRemove(regel);
			conn.transactionCommit();
			return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(daten).build();
		} catch (Exception exception) {
			conn.transactionRollback();
			if (exception instanceof WebApplicationException webex)
				return webex.getResponse();
			throw exception;
		}
	}


	/**
	 * Erstellt eine Liste von Core-Type-Blockungsregeln zu den angegebenen Datenbank-DTOs
	 * 
	 * @param regeln         eine Liste der Regeln (Datenbank-DTOs)
	 * @param parameter   eine Liste mit allen Parametern der Regeln
	 * 
	 * @return die Liste der Blockungsregeln
	 */
	public static List<GostBlockungRegel> getBlockungsregeln(List<DTOGostBlockungRegel> regeln, List<DTOGostBlockungRegelParameter> parameter) {
		if ((regeln == null) || (parameter == null) || (regeln.size() == 0))
			return Collections.emptyList();
		Map<Long, List<DTOGostBlockungRegelParameter>> mapParameter = parameter.stream().collect(Collectors.groupingBy(r -> r.Regel_ID));
		// Erzeuge die Liste der Core-Types
		List<GostBlockungRegel> result = new Vector<>();
		for (DTOGostBlockungRegel regel : regeln)
			result.add(dtoMapper.apply(regel, mapParameter.get(regel.ID)));
        return result;
	}


	/**
	 * Erstellt eine Liste von Blockungsregeln zu der angegebenen Blockungs-ID
	 * 
	 * @param idBlockung   die ID der Blockung
	 * 
	 * @return die Liste der Blockungsregeln
	 */
	public List<GostBlockungRegel> getBlockungsregeln(long idBlockung) {
		// Bestimme alle Regeln der Blockung
		List<DTOGostBlockungRegel> regeln = conn.queryNamed("DTOGostBlockungRegel.blockung_id", idBlockung, DTOGostBlockungRegel.class);
		// Bestimme die IDs dieser Regeln
		List<Long> regelIDs = regeln.stream().map(r -> r.ID).toList();
		// Bestimme die RegelParameter dieser Regeln
		List<DTOGostBlockungRegelParameter> parameter = conn
				.queryNamed("DTOGostBlockungRegelParameter.regel_id.multiple", regelIDs, DTOGostBlockungRegelParameter.class);
		return getBlockungsregeln(regeln, parameter);
	}
	
}
