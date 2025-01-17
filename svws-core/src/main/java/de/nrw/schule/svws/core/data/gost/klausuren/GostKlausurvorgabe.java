package de.nrw.schule.svws.core.data.gost.klausuren;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie liefert die Informationen zu einer Gost-Klausurvorgabe.  
 */
@XmlRootElement
@Schema(description="die Informationen zu einer Gost-Klausurvorgabe.")
@TranspilerDTO
public class GostKlausurvorgabe {

	/** Die ID der Klausurvorgabe. */
	@Schema(required = true, description = "die ID des Stundenplans", example="815")
	public long idVorgabe = -1;

	/** Das Jahr, in welchem der Jahrgang Abitur machen wird, -1 für die Vorlage. */
	@Schema(required = true, description = "das Jahr, in welchem der Jahrgang Abitur machen wird", example="2025")
	public int abiJahrgang = -1;

	/** Das Gost-Halbjahr, in dem die Klausurg geschrieben wird. */
	@Schema(required = true, description = "das Gost-Halbjahr, in dem die Klausurg geschrieben wird", example="3")
	public int halbjahr = -1;

	/** Das Quartal, in welchem die Klausur gechrieben wird. */
	@Schema(required = false, description = "das Quartal, in welchem die Klausur gechrieben wird", example="1")
	public int quartal = -1;
	
	/** Die ID des Faches. */
	@Schema(required = false, description = "die ID des Faches", example="2242")
	public long idFach = -1;
	
	/** Das Kürzel einer verallgemeinerten Kursart. */
	@Schema(required = true, description = "das Kürzel einer verallgemeinerten Kursart", example="GK")
	public @NotNull String kursartAllg = "";

	/** Die Dauer der Klausur in Minuten. */
	@Schema(required = false, description = "die Dauer der Klausur in Minuten", example="135")
	public int dauer = -1;
	
	/** Die Auswahlzeit in Minuten, sofern vorhanden. */
	@Schema(required = false, description = "die Auswahlzeit in Minuten, sofern vorhanden", example="30")
	public int auswahlzeit = -1;
	
	/** Die Information, ob es sich um eine mündliche Prüfung handelt. */
	@Schema(required = false, description = "die Information, ob es sich um eine mündliche Prüfung handelt", example="false")
	public boolean istMdlPruefung = false;
	
	/** Die Information, ob Audioequipment nötig ist, z.B. für Klasuren mit Hörverstehensanteilen. */
	@Schema(required = false, description = "die Information, ob Audioequipment nötig ist, z.B. für Klasuren mit Hörverstehensanteilen", example="false")
	public boolean istAudioNotwendig = false;
	
	/** Die Information, ob Videoequipment nötig ist, z.B. für Klasuren mit Videoanalyse. */
	@Schema(required = false, description = "die Information, ob Videoequipment nötig ist, z.B. für Klasuren mit Videoanalyse", example="false")
	public boolean istVideoNotwendig = false;
	
	/** Die textuelle Bemerkung zur Klausurvorgabe, sofern vorhanden. */
	@Schema(required = false, description = "die textuelle Bemerkung zur Klausurvorgabe, sofern vorhanden", example="Zentrale Vergleichsklausur")
	public String bemerkungVorgabe = null;


}
