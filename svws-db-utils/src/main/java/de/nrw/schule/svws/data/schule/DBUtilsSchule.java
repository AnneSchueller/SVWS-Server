package de.nrw.schule.svws.data.schule;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.dto.current.schild.schule.DTOEigeneSchule;
import de.nrw.schule.svws.db.utils.OperationError;
import jakarta.ws.rs.WebApplicationException;

/**
 * Diese Klasse beinhaltet wiederverwendbare Hilfsmethoden
 * zu der Schule in Bezug auf den Datenbank-Zugriff. 
 */
public class DBUtilsSchule {

	/**
	 * Ermittelt die Daten der eigenen Schule.
	 *
	 * @param conn   die aktuelle Datenbankverbindung 
	 * 
	 * @return das Datenbank-DTO der Schule
	 * 
	 * @throws WebApplicationException    falls keine Schule definiert ist 
	 */
	public static DTOEigeneSchule get(final DBEntityManager conn) throws WebApplicationException {
		DTOEigeneSchule schule = conn.querySingle(DTOEigeneSchule.class);
		if (schule == null)
			throw OperationError.NOT_FOUND.exception("In der Datenbank wurde keine Schule definiert");
		return schule;
	}


}
