package de.nrw.schule.svws.core.data.lehrer;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie beschreibt eine Auswahl von Daten eines Lehrereintrags aus einer Liste.  
 */
@XmlRootElement
@Schema(description="ein Eintrag eines Lehrers in der Lehrerliste.")
@TranspilerDTO
public class LehrerListeEintrag {

	/** Die ID des Lehrers. */
	@Schema(required = true, description = "die ID des Lehrers", example="4711")
	public long id;
	
	/** Das Kürzel des Lehrers. */
	@Schema(required = true, description = "das Kürzel des Lehrers", example="MUS")
	public @NotNull String kuerzel = "";
	
	/** Ggf. ein akademischer Grad des Lehrers. */
	@Schema(required = false, description = "Ggf. ein akademischer Grad des Lehrers.", example="Dr.")
	public String titel;

	/** Der Nachname des Lehrers. */
	@Schema(required = true, description = "der Nachname des Lehrers", example="Mustermann")
	public @NotNull String nachname = "";
	
	/** Der Vorname des Lehrers. */
	@Schema(required = true, description = "der Vorname des Lehrers", example="Max")
	public @NotNull String vorname = "";
	
	/** Der Personaltyp des Lehrerlisten-Eintrags. */
	@Schema(required = true, description = "der Personaltyp des Lehrerlisten-Eintrags", example="LEHRKRAFT")
	public @NotNull String personTyp = "";
	
	/** Die Sortierreihenfolge des Lehrerlisten-Eintrags. */
	@Schema(required = true, description = "die Sortierreihenfolge des Lehrerlisten-Eintrags", example="1")
	public int sortierung;
	
	/** Gibt an, ob der Eintrag in der Anwendung sichtbar sein soll oder nicht. */
	@Schema(required = true, description = "gibt an, ob der Eintrag in der Anwendung sichtbar sein soll oder nicht", example="true")
	public boolean istSichtbar;
	
	/** Gibt an, ob der Eintrag in der Anwendung änderbar sein soll oder nicht. */
	@Schema(required = true, description = "gibt an, ob der Eintrag in der Anwendung änderbar sein soll oder nicht", example="true")
	public boolean istAenderbar;
	
	/** Gibt an, ob der Eintrag für die Schulstatistik relevant ist oder nicht. */
	@Schema(required = true, description = "gibt an, ob der Eintrag für die Schulstatistik relevant ist oder nicht", example="true")
	public boolean istRelevantFuerStatistik;
	
}
