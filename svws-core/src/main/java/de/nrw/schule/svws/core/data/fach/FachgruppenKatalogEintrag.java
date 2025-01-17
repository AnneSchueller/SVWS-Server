package de.nrw.schule.svws.core.data.fach;

import java.util.List;
import java.util.Vector;

import de.nrw.schule.svws.core.data.RGBFarbe;
import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import de.nrw.schule.svws.core.types.schule.Schulform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Die Klasse ist ein Core-DTO für den den Katalog der Fächergruppen und die
 * Information für welche Schulformen diese zulässig sind.
 */
@XmlRootElement
@Schema(description="ein Eintrag in dem Katalog der Fächergruppen.")
@TranspilerDTO
public class FachgruppenKatalogEintrag {

	/** Die ID des Katalog-Eintrags. */
	@Schema(required = true, description = "die ID des Katalog-Eintrags", example="10")
	public long id = -1;

    /** Die Nummer für den Fachbereich, sofern festgelegt, ansonsten null. */
    @Schema(required = false, description = "die Nummer für den Fachbereich, sofern festgelegt, ansonsten null", example="8")
    public Integer nummer = null;

    /** Die alte Fachgruppen-ID, welche in Schild_NRW 2.x verwendet wurde. */
    @Schema(required = false, description = "die alte Fachgruppen-ID, welche in Schild_NRW 2.x verwendet wurde", example="200")
    public Integer idSchild = null;

	/** Die Bezeichnung der Fachgruppe */
	@Schema(required = true, description = "die Bezeichnung der Fachgruppe", example="Naturwissenschaften")
	public @NotNull String bezeichnung = "";
	
	/** Das Kürzel der Fachgruppe */
	@Schema(required = true, description = "das Kürzel der Fachgruppe", example="NW")
	public @NotNull String kuerzel = "";
	
    /** Die Farbe, welche der Fachgruppe zugeordnet wurde */
    @Schema(required = true, description = "die Farbe, welche der Fachgruppe zugeordnet wurde.", example="{ \"red\": 141, \"green\": 180, \"blue\": 227 }")
    public @NotNull RGBFarbe farbe = new RGBFarbe();

    /** Die Kürzel der Schulformen, bei welchen die Fachgruppe vorkommt. */
    @Schema(required = true, description = "die Kürzel der Schulformen, bei welchen die Fachgruppe vorkommt")
    public @NotNull List<@NotNull String> schulformen = new Vector<>();

    /** Ein Zahlwert, welche eine Sortier-Reihenfolge der Fachgruppen angibt (aus Schild 2.x). */
    @Schema(required = false, description = "ein Zahlwert, welche eine Sortier-Reihenfolge der Fachgruppen angibt (aus Schild 2.x)", example="10")
    public @NotNull Integer sortierung = 0;

	/** Gibt an, ob die Fachgruppe für die Unterteilung auf Zeugnissen genutzt wird oder nicht */
	@Schema(required = true, description = "gibt an, ob die Fachgruppe für die Unterteilung auf Zeugnissen genutzt wird oder nicht", example="true")
	public boolean fuerZeugnis = false; 
	
	/** Gibt an, in welchem Schuljahr der Eintrag einführt wurde. Ist kein Schuljahr bekannt, so ist null gesetzt. */
	@Schema(required = false, description = "gibt an, in welchem der Eintrag einführt wurde. Ist kein Schuljahr bekannt, so ist null gesetzt", example="null")
	public Integer gueltigVon = null;

	/** Gibt an, bis zu welchem Schuljahr der Eintrag gültig ist. Ist kein Schuljahr bekannt, so ist null gesetzt. */
	@Schema(required = false, description = "gibt an, bis zu welchem der Eintrag gültig ist. Ist kein Schuljahr bekannt, so ist null gesetzt", example="null")
	public Integer gueltigBis = null;


	/**
	 * Erstellt einen Eintrag mit Standardwerten
	 */
	public FachgruppenKatalogEintrag() {
	}


	/**
	 * Erstellt einen Eintrag mit den angegebenen Werten
	 * 
	 * @param id            die ID
	 * @param nummer        die Nummer für den Fachbereich, sofern festgelegt, ansonsten null
	 * @param idSchild      die alte Fachgruppen-ID, welche in Schild_NRW 2.x verwendet wurde
	 * @param bezeichnung   die Bezeichnung der Fachgruppe
	 * @param kuerzel       das Kürzel der Fachgruppe 
	 * @param farbe         die Farbe, welche der Fachgruppe zugeordnet wurde
	 * @param schulformen   die Kürzel der Schulformen, bei welchen die Fachgruppe vorkommt
	 * @param sortierung    ein Zahlwert, welche eine Sortier-Reihenfolge der Fachgruppen angibt (aus Schild 2.x)
	 * @param fuerZeugnis   gibt an, ob die Fachgruppe für die Unterteilung auf Zeugnissen genutzt wird oder nicht 
	 * @param gueltigVon    das Schuljahr, wann der Eintrag eingeführt wurde oder null, falls es nicht bekannt ist und 
	 *                      "schon immer gültig war"
	 * @param gueltigBis    das Schuljahr, bis zu welchem der Eintrag gültig ist
	 */	
	public FachgruppenKatalogEintrag(long id, Integer nummer, Integer idSchild, @NotNull String bezeichnung, @NotNull String kuerzel,
	        @NotNull RGBFarbe farbe, @NotNull List<@NotNull Schulform> schulformen, @NotNull Integer sortierung,
	        boolean fuerZeugnis, Integer gueltigVon, Integer gueltigBis) {
		this.id = id;
		this.nummer = nummer;
        this.idSchild = idSchild;
		this.bezeichnung = bezeichnung;
		this.kuerzel = kuerzel;
		// TODO farbe
		this.farbe = farbe;
        for (@NotNull Schulform schulform : schulformen)
            this.schulformen.add(schulform.daten.kuerzel);
		this.sortierung = sortierung;
		this.fuerZeugnis = fuerZeugnis;
		this.gueltigVon = gueltigVon;
		this.gueltigBis = gueltigBis;
	}

}
