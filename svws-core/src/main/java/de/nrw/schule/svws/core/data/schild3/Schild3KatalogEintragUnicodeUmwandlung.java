package de.nrw.schule.svws.core.data.schild3;

import de.nrw.schule.svws.core.transpiler.TranspilerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Diese Klasse ist eine DTO-Klasse für den Schild3-Katalog Unicode-Umwandlung.
 */
@XmlRootElement
@Schema(description="Schild3-Katalog Unicode-Umwandlung")
@TranspilerDTO
public class Schild3KatalogEintragUnicodeUmwandlung {

    /** ID für den Primärschlüssel der Tabelle UnicodeUmwandlung */
    @Schema(required = false, description = "ID für den Primärschlüssel der Tabelle UnicodeUmwandlung")
    public Long ID;

    /** Unicodezeichen das umgewandelt werden muss */
    @Schema(required = false, description = "Unicodezeichen das umgewandelt werden muss")
    public String Unicodezeichen;

    /** Ersatzzeichen für das Unicodezeichen */
    @Schema(required = false, description = "Ersatzzeichen für das Unicodezeichen")
    public String Ersatzzeichen;

    /** Unicodezeichen in Dezimaldarstellung */
    @Schema(required = false, description = "Unicodezeichen in Dezimaldarstellung")
    public String DecimalZeichen;

    /** Ersatzzeichen in Dezimaldarstellung (bei zwei Zeichen mit + getrennt) */
    @Schema(required = false, description = "Ersatzzeichen in Dezimaldarstellung (bei zwei Zeichen mit + getrennt)")
    public String DecimalErsatzzeichen;

    /** Hexdarstellung des Unicodezeichen das gewandelt werden muss */
    @Schema(required = false, description = "Hexdarstellung des Unicodezeichen das gewandelt werden muss")
    public String Hexzeichen;

    /** Hexdarstellung des Ersatzzeichens das gewandelt werden muss (bei zwei Zeichen mit + getrennt) */
    @Schema(required = false, description = "Hexdarstellung des Ersatzzeichens das gewandelt werden muss (bei zwei Zeichen mit + getrennt)")
    public String HexErsatzzeichen;

}
