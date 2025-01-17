package de.nrw.schule.svws.core.data.schueler;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie beschreibt besondere Merkmale für die Statistik in Bezug auf den Schulbesuch eines Schülers.  
 */
@XmlRootElement
@Schema(description="Das besondere Merkmale für die Statistik in Bezug auf den Schulbesuch des Schülers.")
@TranspilerDTO
public class SchuelerSchulbesuchMerkmal {

	/** Die ID des besonderen Merkmals für die Statistik. */
	@Schema(required = true, description = "die ID des besonderen Merkmals für die Statistik", example="3")
	public long id;
	
	/** Das Datum, ab dem das Merkmal vorliegt. */
	@Schema(required = false, description = "das Datum, ab dem das Merkmal vorliegt", example="2007-08-01")
	public String datumVon;

	/** Das Datum, bis wann das Merkmal vorliegt. */
	@Schema(required = false, description = "das Datum, bis wann das Merkmal vorliegt", example="2008-07-31")
	public String datumBis;

}
