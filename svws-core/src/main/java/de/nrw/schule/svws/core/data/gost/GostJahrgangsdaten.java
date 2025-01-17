package de.nrw.schule.svws.core.data.gost;

import java.util.Vector;

import jakarta.xml.bind.annotation.XmlRootElement;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse wird bei der Kommunikation über die Open-API-Schnittstelle verwendet.
 * Sie beschreibt die grundlegenden Daten eines Jahrgangs der gymnasialen Oberstufe.  
 */
@XmlRootElement(name = "GostJahrgangsdaten")
@Schema(description="Die Daten eines Jahrgangs der gymnasialen Oberstufe.")
@TranspilerDTO
public class GostJahrgangsdaten {

	/** Das Jahr, in welchem der Jahrgang Abitur machen wird oder -1 für die Vorlage für einen neuen Abiturjahrgang. */
	@Schema(required = false, description = "das Jahr, in welchem der Jahrgang Abitur machen wird oder -1 für die Vorlage für einen neuen Abiturjahrgang", example="2042")
	public int abiturjahr;
	
	/** Die aktuelle Jahrgangsstufe, welche dem Abiturjahrgang zugeordnet ist. */
	@Schema(required = false, description = "die aktuelle Jahrgangstufe, welche dem Abiturjahrgang zugeordnet ist", example="Q1")
	public String jahrgang;

	/** Die textuelle Bezeichnung für den Abiturjahrgang */
	@Schema(required = true, description = "die textuelle Bezeichnung für den Abiturjahrgang", example="Q1")
	public String bezeichnung;
	
	/** Gibt an, ob das Abitur für diesen Jahrgang berets abgeschlossen ist, d.h. die Schule sich bereits in einem späteren Schuljahr befindet. */
	@Schema(required = true, description = "gibt an, ob das Abitur für diesen Jahrgang bereits abgeschlossen ist, d.h. die Schule sich bereits in einem späteren Schuljahr befindet", example="false")
	public boolean istAbgeschlossen;
	
	/** Der derzeitige Beratungstext, welcher auf einem Ausdruck eines Schülerlaufbahnbogens für die 
	 * gymnasiale Oberstufe gedruckt wird. */
	@Schema(required = true, description = "Der derzeitige Beratungstext, welcher auf einem Ausdruck eines "
			+ "Schülerlaufbahnbogens für die gymnasiale Oberstufe gedruckt wird.", example="Wahlen zum Beginn der Q1.1")
	public String textBeratungsbogen;
	
	/** Der derzeitige Text, der beim Versenden einer Beratungsdatei per Mail verwendet wird. */
	@Schema(required = true, description = "Der derzeitige Text, der beim Versenden des Beartungsbogens per Mail verwendet wird.", 
			example="Bitte Senden Sie die Beratungsdatei ausgefüllt bis spätestens 13.4.2042 zurück. MfG Euer Jahrgangsstufenteam")
	public String textMailversand;
	
	/** Legt fest, ob ein Zusatzkurs in Geschichte angeboten wird. */
	@Schema(required = true, description = "Legt fest, ob ein Zusatzkurs in Geschichte angeboten wird.", example="true")
	public boolean hatZusatzkursGE = true;
	
	/** Das eindeutige Kürzel des Halbjahrs, zu dem ein Zusatzkurs in Geschichte beginnt. */
	@Schema(required = true, description = "Das eindeutige Kürzel des Halbjahrs, zu dem ein Zusatzkurs in Geschichte beginnt.", example="Q2.1")
	public String beginnZusatzkursGE;
	
	/** Legt fest, ob ein Zusatzkurs in Sozialwissenschaften angeboten wird. */
	@Schema(required = true, description = "Legt fest, ob ein Zusatzkurs in Sozialwissenschaften angeboten wird.", example="true")
	public boolean hatZusatzkursSW = true;
	
	/** Das eindeutige Kürzel des Halbjahrs, zu dem ein Zusatzkurs in Sozialwissenschaften beginnt. */
	@Schema(required = true, description = "Das eindeutige Kürzel des Halbjahrs, zu dem ein Zusatzkurs in Sozialwissenschaften beginnt.", example="Q2.1")
	public String beginnZusatzkursSW;

	/** Gibt an, ob für die jeweilige Halbjahre der Oberstufe bereits eine Blockung in den Leistungsdaten persistiert wurde (0 = EF.1, 1=EF.2, ...) */
	@ArraySchema(schema = @Schema(required = true, implementation = Boolean.class, description = "gibt an, ob für die jeweilige Halbjahre der Oberstufe bereits eine Blockung in den Leistungsdaten persistiert wurde (0 = EF.1, 1=EF.2, ...)"))
	public @NotNull boolean[] istBlockungFestgelegt = new boolean[6];
	
	/** Die Liste der Beratungslehrer für diesen Jahrgang der gymnasialen Oberstufe */
	@ArraySchema(schema = @Schema(implementation = GostBeratungslehrer.class))
	public final @NotNull Vector<@NotNull GostBeratungslehrer> beratungslehrer = new Vector<>();
}
