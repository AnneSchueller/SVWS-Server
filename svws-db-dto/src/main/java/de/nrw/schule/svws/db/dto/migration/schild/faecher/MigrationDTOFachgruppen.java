package de.nrw.schule.svws.db.dto.migration.schild.faecher;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.migration.MigrationBoolean01Converter;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.nrw.schule.svws.csv.converter.migration.MigrationBoolean01ConverterSerializer;
import de.nrw.schule.svws.csv.converter.migration.MigrationBoolean01ConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle Fachgruppen.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Fachgruppen")
@NamedQuery(name="MigrationDTOFachgruppen.all", query="SELECT e FROM MigrationDTOFachgruppen e")
@NamedQuery(name="MigrationDTOFachgruppen.id", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.ID = :value")
@NamedQuery(name="MigrationDTOFachgruppen.id.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.ID IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.fachbereich", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.Fachbereich = :value")
@NamedQuery(name="MigrationDTOFachgruppen.fachbereich.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.Fachbereich IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.schildfgid", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.SchildFgID = :value")
@NamedQuery(name="MigrationDTOFachgruppen.schildfgid.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.SchildFgID IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.fg_bezeichnung", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FG_Bezeichnung = :value")
@NamedQuery(name="MigrationDTOFachgruppen.fg_bezeichnung.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FG_Bezeichnung IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.fg_kuerzel", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FG_Kuerzel = :value")
@NamedQuery(name="MigrationDTOFachgruppen.fg_kuerzel.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FG_Kuerzel IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.schulformen", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.Schulformen = :value")
@NamedQuery(name="MigrationDTOFachgruppen.schulformen.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.Schulformen IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.farber", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FarbeR = :value")
@NamedQuery(name="MigrationDTOFachgruppen.farber.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FarbeR IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.farbeg", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FarbeG = :value")
@NamedQuery(name="MigrationDTOFachgruppen.farbeg.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FarbeG IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.farbeb", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FarbeB = :value")
@NamedQuery(name="MigrationDTOFachgruppen.farbeb.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FarbeB IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.sortierung", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.Sortierung = :value")
@NamedQuery(name="MigrationDTOFachgruppen.sortierung.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.Sortierung IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.fuerzeugnis", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FuerZeugnis = :value")
@NamedQuery(name="MigrationDTOFachgruppen.fuerzeugnis.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.FuerZeugnis IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.gueltigvon", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.gueltigVon = :value")
@NamedQuery(name="MigrationDTOFachgruppen.gueltigvon.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.gueltigVon IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.gueltigbis", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.gueltigBis = :value")
@NamedQuery(name="MigrationDTOFachgruppen.gueltigbis.multiple", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.gueltigBis IN :value")
@NamedQuery(name="MigrationDTOFachgruppen.primaryKeyQuery", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.ID = ?1")
@NamedQuery(name="MigrationDTOFachgruppen.all.migration", query="SELECT e FROM MigrationDTOFachgruppen e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Fachbereich","SchildFgID","FG_Bezeichnung","FG_Kuerzel","Schulformen","FarbeR","FarbeG","FarbeB","Sortierung","FuerZeugnis","gueltigVon","gueltigBis"})
public class MigrationDTOFachgruppen {

	/** ID des Fachgruppen-Core-Type, welcher auch ein Mapping zu den Fachgruppen von SchildNRW und Lupo bereitstellt */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Fachbereich (Nr) der Fachgruppe aus Lupo */
	@Column(name = "Fachbereich")
	@JsonProperty
	public Integer Fachbereich;

	/** Fachgruppen ID aus SchildNRW */
	@Column(name = "SchildFgID")
	@JsonProperty
	public Long SchildFgID;

	/** Bezeichnung der Fachgruppe */
	@Column(name = "FG_Bezeichnung")
	@JsonProperty
	public String FG_Bezeichnung;

	/** Kürzel der Fachgruppe */
	@Column(name = "FG_Kuerzel")
	@JsonProperty
	public String FG_Kuerzel;

	/** Gibt an in welchen Schulformen die Fachgruppe zulässig ist */
	@Column(name = "Schulformen")
	@JsonProperty
	public String Schulformen;

	/** Default-Fachgruppenfarbe (Rot-Wert) */
	@Column(name = "FarbeR")
	@JsonProperty
	public Integer FarbeR;

	/** Default-Fachgruppenfarbe (Grün-Wert) */
	@Column(name = "FarbeG")
	@JsonProperty
	public Integer FarbeG;

	/** Default-Fachgruppenfarbe (Blau-Wert) */
	@Column(name = "FarbeB")
	@JsonProperty
	public Integer FarbeB;

	/** Eine Standard-Sortierreihenfolge für die Fachgruppen */
	@Column(name = "Sortierung")
	@JsonProperty
	public Integer Sortierung;

	/** Gibt an, ob die Fachgruppe für Unterteilungen auf Zeugnissen verwendet wird */
	@Column(name = "FuerZeugnis")
	@JsonProperty
	@Convert(converter=MigrationBoolean01Converter.class)
	@JsonSerialize(using=MigrationBoolean01ConverterSerializer.class)
	@JsonDeserialize(using=MigrationBoolean01ConverterDeserializer.class)
	public Boolean FuerZeugnis;

	/** Das Schuljahr, ab dem die Fachgruppe eingeführt wurde */
	@Column(name = "gueltigVon")
	@JsonProperty
	public Integer gueltigVon;

	/** Das Schuljahr, bis wann die Fachgruppe gültig ist */
	@Column(name = "gueltigBis")
	@JsonProperty
	public Integer gueltigBis;

	/**
	 * Erstellt ein neues Objekt der Klasse MigrationDTOFachgruppen ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private MigrationDTOFachgruppen() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse MigrationDTOFachgruppen ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param FG_Bezeichnung   der Wert für das Attribut FG_Bezeichnung
	 * @param FuerZeugnis   der Wert für das Attribut FuerZeugnis
	 */
	public MigrationDTOFachgruppen(final Long ID, final String FG_Bezeichnung, final Boolean FuerZeugnis) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (FG_Bezeichnung == null) { 
			throw new NullPointerException("FG_Bezeichnung must not be null");
		}
		this.FG_Bezeichnung = FG_Bezeichnung;
		if (FuerZeugnis == null) { 
			throw new NullPointerException("FuerZeugnis must not be null");
		}
		this.FuerZeugnis = FuerZeugnis;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MigrationDTOFachgruppen other = (MigrationDTOFachgruppen) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "MigrationDTOFachgruppen(ID=" + this.ID + ", Fachbereich=" + this.Fachbereich + ", SchildFgID=" + this.SchildFgID + ", FG_Bezeichnung=" + this.FG_Bezeichnung + ", FG_Kuerzel=" + this.FG_Kuerzel + ", Schulformen=" + this.Schulformen + ", FarbeR=" + this.FarbeR + ", FarbeG=" + this.FarbeG + ", FarbeB=" + this.FarbeB + ", Sortierung=" + this.Sortierung + ", FuerZeugnis=" + this.FuerZeugnis + ", gueltigVon=" + this.gueltigVon + ", gueltigBis=" + this.gueltigBis + ")";
	}

}