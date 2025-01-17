package de.nrw.schule.svws.core.data.schule;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import de.nrw.schule.svws.core.types.schule.Schulform;
import de.nrw.schule.svws.core.types.schule.Schulgliederung;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie liefert das Paar einer Kombination aus Schulform und Schulgliederung.  
 */
@XmlRootElement
@Schema(description="eine Kombination aus Schulform und Schulgliederung.")
@TranspilerDTO
public class SchulformSchulgliederung {

	/** Das Kürzel der Schulform */
	@Schema(required = true, description = "das Kürzel der Schulform", example="BK")
	public @NotNull String schulform = "GY";
	
	/** Das Kürzel der Schulgliederung bzw. des Bildungsganges. Null, falls alle Gliederungen der Schulform gemeint sind. */
	@Schema(required = true, description = "das Kürzel der Schulgliederung bzw. des Bildungsganges", example="A01")
	public String gliederung = null;


	/**
	 * Erstellt ein Objekt mit Standardwerten
	 */
	public SchulformSchulgliederung() {
	}


	/**
	 * Erstellt ein Objekt mit den angegebenen Werten
	 * 
	 * @param schulform      die Schulform
	 * @param gliederung     die Schulgliederung oder null 
	 */
	public SchulformSchulgliederung(@NotNull Schulform schulform, Schulgliederung gliederung) {
		this.schulform = schulform.daten.kuerzel;
		this.gliederung = (gliederung == null) ? null : gliederung.daten.kuerzel;
	}

}
