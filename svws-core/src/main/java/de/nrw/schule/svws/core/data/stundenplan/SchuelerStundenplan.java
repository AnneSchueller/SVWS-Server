package de.nrw.schule.svws.core.data.stundenplan;

import java.util.List;
import java.util.Vector;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie liefert die Informationen zu dem Stundenplan eines Schülers.  
 */
@XmlRootElement
@Schema(description="der Stundenplan eines Schülers.")
@TranspilerDTO
public class SchuelerStundenplan {

	/** Die ID des Stundenplans. */
	@Schema(required = true, description = "die ID des Stundenplans", example="815")
	public long idStundenplan = -1;

	/** Die textuelle Beschreibung des Stundenplans. */
	@Schema(required = false, description = "die textuelle Beschreibung des Stundenplans", example="Stundenplan zum Schuljahresanfang")
	public @NotNull String bezeichnungStundenplan = "";
	
	/** Die ID des Schuljahresabschnitts des Stundenplans. */
	@Schema(required = true, description = "die ID des Schuljahresabschnitts des Stundenplans", example="7")
	public long idSchuljahresabschnitt = -1;

	/** Das Zeitraster des Stundenplans. */
	@Schema(required = true, description = "das Zeitraster des Stundenplans")
	public @NotNull List<@NotNull StundenplanZeitraster> zeitraster = new Vector<>();

	/** Das Datum, ab dem der Stundenpland gültig ist. */
	@Schema(required = false, description = "das Datum, ab dem der Stundenpland gültig ist", example="1.1.1899")
	public @NotNull String gueltigAb = "";

	/** Das Datum, bis wann der Stundenplan gültig ist. */
	@Schema(required = false, description = "das Datum, bis wann der Stundenplan gültig ist", example="31.7.3218")
	public @NotNull String gueltigBis = "";
	
	/** Die ID des Schülers. */
	@Schema(required = true, description = "die ID des Schülers", example="4711")
	public long idSchueler = -1;
	
	/** Der Nachname des Schülers. */
	@Schema(required = false, description = "der Nachname des Schülers", example="Mustermann")
	public @NotNull String nachname = "";
	
	/** Der Vorname des Schülers. */
	@Schema(required = false, description = "der Vorname des Schülers", example="Max")
	public @NotNull String vorname = "";

	/** Die ID der Klasse des Schülers. */
	@Schema(required = true, description = "die ID der Klasse des Schülers", example="42")
	public long idKlasse = -1;

	/** Der Statistik-Jahrgang des Schülers. */
	@Schema(required = true, description = "der Statistik-Jahrgang des Schülers", example="EF")
	public @NotNull String jahrgang = "";

	/** Der Unterricht des Schülers. */
	@Schema(required = true, description = "der Unterricht des Schülers")
	public @NotNull List<@NotNull SchuelerStundenplanUnterricht> unterricht = new Vector<>();

}
