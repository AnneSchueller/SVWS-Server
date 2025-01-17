package de.nrw.schule.svws.core.data.gost;

import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** Diese Klasse ist die Core-DTO für ein Ergebnis einer Kursblockung */
@XmlRootElement
@Schema(description = "Informationen zu dem Ergebnis einer Blockung der gymnasialen Oberstufe.")
@JsonPropertyOrder({ "id", "blockungID", "name", "gostHalbjahr", "istMarkiert", "istVorlage", "bewertung" })
@TranspilerDTO
public class GostBlockungsergebnisListeneintrag {

	/** Die ID des Zwischenergebnisses der Blockung */
	public long id = -1;

	/** Die ID der Blockung */
	public long blockungID = -1;

	/** Der Name der Blockung */
	public @NotNull String name = "Blockung";

	/** Das Halbjahr, welchem die Kursblockung zugeordnet ist (0=EF.1, 1=EF.2, 2=Q1.1, 3=Q1.2, 4=Q2.1, 5=Q2.2) */
	public int gostHalbjahr = 0;

	/** Gibt an, ob dieses Ergebnis markiert wurde. Dies kann verwendet werden, um besonders geeignete
	 * Blockungsergebnisse hervorzuheben. */
	public boolean istMarkiert = false;

	/** Gibt an, ob dieses Ergebnis als Vorlage für eine Blockungsdefinition dient. Eine Vorlage wird
	 * mit der Definition automatisch angepasst in Bezug auf die Kurs- und Schienenanzahl mit angepasst, 
	 * obwohl dies bei berechneten Ergebnissen nicht vorgesehen ist. Jede Blockungsdefinition hat
	 * genau eine Vorlage zugeordnet. */
	public boolean istVorlage = false; 

	/** Die Informationen zur aktuellen Bewertung des Blockungsergebnisses */
	public @NotNull GostBlockungsergebnisBewertung bewertung = new GostBlockungsergebnisBewertung();

}
