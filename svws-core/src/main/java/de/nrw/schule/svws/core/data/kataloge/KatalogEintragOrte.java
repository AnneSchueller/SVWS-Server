package de.nrw.schule.svws.core.data.kataloge;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Diese Klasse ist eine DTO-Klasse für den PLZ-Ort-Katalog von IT.NRW. Der
 * Katalog wird in den Java-Resourcen unter "daten/csv/Orte.csv"
 * erwartet.
 */
@JsonPropertyOrder({"ID","PLZ","RegSchl","Ort","Sortierung","gueltigVon","gueltigBis"})
@XmlRootElement
@Schema(description="ein Eintrag in dem IT.NRW-Katalog der Orte.")
@TranspilerDTO
public class KatalogEintragOrte {

	/** Katalog von IT.NRW PLZ Ort: die ID des Katalog-Eintrags */
	@Schema(required = true, description = "der zugehörige Ort des Katalog-Eintrags", example="44012")
	public Long ID;

	/** Katalog von IT.NRW PLZ Ort: die Postleitzahl */
	@Schema(required = true, description = "die Postleitzahl", example="32001")
	public String PLZ;

    /** Katalog von IT.NRW PLZ Ort: der Regionalschlüssel */
    @Schema(required = true, description = "der Regionalschlüssel", example="05758012")
    public String RegSchl;

	/** Katalog von IT.NRW PLZ Ort: die Ortsbezeichnung */
	@Schema(required = true, description = "die Ortsbezeichnung", example="Herford")
	public String Ort;

    /** Katalog von IT.NRW PLZ Ort: eine Nummer für die Sortierreihenfolge der Katalog-Einträge */
    @Schema(required = true, description = "eine Nummer für die Sortierreihenfolge der Katalog-Einträge", example="1")
    public Integer Sortierung;

    /** Katalog von IT.NRW PLZ Ort: Gibt die Gültigkeit ab welchem Schuljahr an */
    @Schema(required = false, description = "gibt die Gültigkeit ab welchem Schuljahr an", example="null")
    public Integer gueltigVon;

    /** Katalog von IT.NRW PLZ Ort: Gibt die Gültigkeit bis zu welchem Schuljahr an */
    @Schema(required = false, description = "gibt die Gültigkeit bis zu welchem Schuljahr an", example="null")
    public Integer gueltigBis;

}