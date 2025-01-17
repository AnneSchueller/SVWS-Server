package de.nrw.schule.svws.core.data.erzieher;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie beschreibt eine Auswahl von Daten eines Erziehereintrags aus einer Liste.  
 */
@XmlRootElement
@Schema(description="ein Eintrag einer Erziehers in der Erzieherliste.")
@TranspilerDTO
public class ErzieherListeEintrag {

	/** Die "Partner"-ID des Erziehers mit der Nummer des Erziehers in der DB angehangen (1 oder 2), welche diesem Erzieher im gleichen DB-Datensatz zugeordnet ist und die gleichen Addressdaten, etc. sich teil. */
	@Schema(required = true, description = "die ID des weiteren Erziehers mit der Nummer des Erziehers in der DB angehangen (1 oder 2), welcher diesem Erzieher im gleichen DB-Datensatz zugeordnet ist und die gleichen Addressdaten, etc. sich teil.", example="4711")
	public long id;
	
	/** Die ID des Schülers, welchem der Erzieher zugeordnet ist. */
	@Schema(required = true, description = "die ID des Schülers, welchem der Erzieher zugeordnet ist", example="4712")
	public long idSchueler;
	
	/** Die ID der Art des Erziehereintrages */
	@Schema(required = true, description = "Die ID der Art des Erziehereintrages", example="56")
	public Long idErzieherArt;

	/** Die Anrede des Erziehers. */
	@Schema(required = true, description = "Anrede des Erziehers", example="Herr")
	public String anrede;

	/** Der Name des Erziehers. */
	@Schema(required = true, description = "Der Name des Erziehers", example="Müller")
	public String name;

	/** Der Vorname des Erziehers. */
	@Schema(required = true, description = "Der Vorname des Erziehers", example="Max")
	public String vorname;

	/** Die E-Mailadresse des  Erziehers. */
	@Schema(required = true, description = "Die Email-Adresse des Erziehers", example="max@test.de")
	public String email;
	
	
}
