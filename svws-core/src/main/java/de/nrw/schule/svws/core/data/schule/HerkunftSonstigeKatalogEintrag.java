package de.nrw.schule.svws.core.data.schule;

import java.util.List;
import java.util.Vector;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import de.nrw.schule.svws.core.types.schule.Schulform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie liefert die Werte für den Katalog der Sonstigen Herkünfte.  
 */
@XmlRootElement
@Schema(description="ein Eintrag in dem Katalog der Sonstigen Herkünfte.")
@TranspilerDTO
public class HerkunftSonstigeKatalogEintrag {

	/** Die ID des Katalog-Eintrags. */
	@Schema(required = true, description = "die ID des Katalog-Eintrags", example="4711")
	public long id = -1;

	/** Das Kürzel der sonstigen Herkunft, welches im Rahmen der amtlichen Schulstatistik verwendet wird */
	@Schema(required = true, description = "das Kürzel der Sonstigen Herkunft, welches im Rahmen der amtlichen Schulstatistik verwendet wird", example="UN")
	public @NotNull String kuerzel = "";

	/** Die Kürzel der Schulformen, bei welchen die sonstige Herkunft vorkommen kann. */
	@Schema(required = true, description = "die Kürzel der Schulformen, bei welchen die sonstige Herkunft vorkommen kann")
	public @NotNull List<@NotNull String> schulformen = new Vector<>();
	
	/** Die textuelle Beschreibung der sonstigen Herkunft. */
	@Schema(required = true, description = "die textuelle Beschreibung der sonstigen Herkunft", example="Herkunft noch unbekannt (nur Gliederung A12, A13)")
	public @NotNull String beschreibung = "";

	/** Gibt an, in welchem Schuljahr der Eintrag einführt wurde. Ist kein Schuljahr bekannt, so ist null gesetzt. */
	@Schema(required = false, description = "gibt an, in welchem der Eintrag einführt wurde. Ist kein Schuljahr bekannt, so ist null gesetzt", example="null")
	public Integer gueltigVon = null;

	/** Gibt an, bis zu welchem Schuljahr der Eintrag gültig ist. Ist kein Schulgliederung bekannt, so ist null gesetzt. */
	@Schema(required = false, description = "gibt an, bis zu welchem der Eintrag gültig ist. Ist kein Schuljahr bekannt, so ist null gesetzt", example="2025")
	public Integer gueltigBis = null;


	/**
	 * Erstellt einen Eintrag mit Standardwerten
	 */
	public HerkunftSonstigeKatalogEintrag() {
	}


	/**
	 * Erstellt einen Eintrag mit den angegebenen Werten
	 * 
	 * @param id              die ID
	 * @param kuerzel         das Kürzel 
	 * @param schulformen     die Kürzel der Schulformen, bei welchen die sonstige Herkunft vorkommen kann
	 * @param beschreibung    die textuelle Beschreibung der sonstigen Herkunft
	 * @param gueltigVon      das Schuljahr, wann der Eintrag eingeführt wurde oder null, falls es nicht bekannt ist und "schon immer gültig war"
	 * @param gueltigBis      das Schuljahr, bis zu welchem der Eintrag gültig ist
	 */
	public HerkunftSonstigeKatalogEintrag(long id, @NotNull String kuerzel,  
			@NotNull List<@NotNull Schulform> schulformen, @NotNull String beschreibung, 
			Integer gueltigVon, Integer gueltigBis) {
		this.id = id;
		this.kuerzel = kuerzel;
		for (@NotNull Schulform schulform : schulformen)
			this.schulformen.add(schulform.daten.kuerzel);
		this.beschreibung = beschreibung;
		this.gueltigVon = gueltigVon;
		this.gueltigBis = gueltigBis;
	}

}
