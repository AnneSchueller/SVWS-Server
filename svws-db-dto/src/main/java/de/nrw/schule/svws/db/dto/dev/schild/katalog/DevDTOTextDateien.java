package de.nrw.schule.svws.db.dto.dev.schild.katalog;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultPlusConverter;


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
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultPlusConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultPlusConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle K_Textdateien.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "K_Textdateien")
@NamedQuery(name="DevDTOTextDateien.all", query="SELECT e FROM DevDTOTextDateien e")
@NamedQuery(name="DevDTOTextDateien.id", query="SELECT e FROM DevDTOTextDateien e WHERE e.ID = :value")
@NamedQuery(name="DevDTOTextDateien.id.multiple", query="SELECT e FROM DevDTOTextDateien e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOTextDateien.bezeichnung", query="SELECT e FROM DevDTOTextDateien e WHERE e.Bezeichnung = :value")
@NamedQuery(name="DevDTOTextDateien.bezeichnung.multiple", query="SELECT e FROM DevDTOTextDateien e WHERE e.Bezeichnung IN :value")
@NamedQuery(name="DevDTOTextDateien.text_id", query="SELECT e FROM DevDTOTextDateien e WHERE e.Text_ID = :value")
@NamedQuery(name="DevDTOTextDateien.text_id.multiple", query="SELECT e FROM DevDTOTextDateien e WHERE e.Text_ID IN :value")
@NamedQuery(name="DevDTOTextDateien.text_body", query="SELECT e FROM DevDTOTextDateien e WHERE e.Text_Body = :value")
@NamedQuery(name="DevDTOTextDateien.text_body.multiple", query="SELECT e FROM DevDTOTextDateien e WHERE e.Text_Body IN :value")
@NamedQuery(name="DevDTOTextDateien.sichtbar", query="SELECT e FROM DevDTOTextDateien e WHERE e.Sichtbar = :value")
@NamedQuery(name="DevDTOTextDateien.sichtbar.multiple", query="SELECT e FROM DevDTOTextDateien e WHERE e.Sichtbar IN :value")
@NamedQuery(name="DevDTOTextDateien.sortierung", query="SELECT e FROM DevDTOTextDateien e WHERE e.Sortierung = :value")
@NamedQuery(name="DevDTOTextDateien.sortierung.multiple", query="SELECT e FROM DevDTOTextDateien e WHERE e.Sortierung IN :value")
@NamedQuery(name="DevDTOTextDateien.primaryKeyQuery", query="SELECT e FROM DevDTOTextDateien e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOTextDateien.all.migration", query="SELECT e FROM DevDTOTextDateien e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Bezeichnung","Text_ID","Text_Body","Sichtbar","Sortierung"})
public class DevDTOTextDateien {

	/** ID des Textes der zum Ersatz für txt-Dateien genutzt wird */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Bezeichnungdes Textes der zum Ersatz für txt-Dateien genutzt wird */
	@Column(name = "Bezeichnung")
	@JsonProperty
	public String Bezeichnung;

	/** TextID des Textes der zum Ersatz für txt-Dateien genutzt wird */
	@Column(name = "Text_ID")
	@JsonProperty
	public Long Text_ID;

	/** Text-Body des Textes der zum Ersatz für txt-Dateien genutzt wird */
	@Column(name = "Text_Body")
	@JsonProperty
	public String Text_Body;

	/** Sichbarkeit des Textes der zum Ersatz für txt-Dateien genutzt wird */
	@Column(name = "Sichtbar")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultPlusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultPlusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultPlusConverterDeserializer.class)
	public Boolean Sichtbar;

	/** Sortierung des Textes der zum Ersatz für txt-Dateien genutzt wird */
	@Column(name = "Sortierung")
	@JsonProperty
	public Integer Sortierung;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOTextDateien ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOTextDateien() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOTextDateien ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Text_ID   der Wert für das Attribut Text_ID
	 */
	public DevDTOTextDateien(final Long ID, final Long Text_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Text_ID == null) { 
			throw new NullPointerException("Text_ID must not be null");
		}
		this.Text_ID = Text_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOTextDateien other = (DevDTOTextDateien) obj;
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
		return "DevDTOTextDateien(ID=" + this.ID + ", Bezeichnung=" + this.Bezeichnung + ", Text_ID=" + this.Text_ID + ", Text_Body=" + this.Text_Body + ", Sichtbar=" + this.Sichtbar + ", Sortierung=" + this.Sortierung + ")";
	}

}