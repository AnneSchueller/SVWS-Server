package de.nrw.schule.svws.core.data.kataloge;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie beschreibt, wie die Daten für die standardisierten Katalogeinträge übergeben werden.  
 */
@XmlRootElement
@Schema(description="ein Eintrag in einem standardisierten Katalog.")
@TranspilerDTO
public class KatalogEintrag {

	/** Die ID des Katalog-Eintrags. */
	@Schema(required = true, description = "die ID des Katalog-Eintrags", example="4711")
	public long id;

	/** Das Kürzel des Eintrags. */
	@Schema(required = true, description = "das Kürzel des Eintrags", example="XY")
	public String kuerzel;
	
	/** Die textuelle Beschreibung des Katalog-Eintrags. */
	@Schema(required = true, description = "die textuelle Beschreibung des Katalog-Eintrags", example="Hier steht eine textuelle Beschreibung des Katalog-Eintrags")
	public String text;

	/** Gibt an, ob der Eintrag in der Anwendung sichtbar sein soll oder nicht. */
	@Schema(required = true, description = "gibt an, ob der Eintrag in der Anwendung sichtbar sein soll oder nicht", example="true")
	public boolean istSichtbar;
	
	/** Gibt an, ob der Eintrag in der Anwendung änderbar sein soll oder nicht. */
	@Schema(required = true, description = "gibt an, ob der Eintrag in der Anwendung änderbar sein soll oder nicht", example="true")
	public boolean istAenderbar;
	
}
