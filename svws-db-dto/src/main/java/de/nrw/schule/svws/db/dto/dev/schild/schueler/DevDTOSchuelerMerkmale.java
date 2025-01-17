package de.nrw.schule.svws.db.dto.dev.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.DatumConverter;


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
import de.nrw.schule.svws.csv.converter.current.DatumConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.DatumConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerMerkmale.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerMerkmale")
@NamedQuery(name="DevDTOSchuelerMerkmale.all", query="SELECT e FROM DevDTOSchuelerMerkmale e")
@NamedQuery(name="DevDTOSchuelerMerkmale.id", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.id.multiple", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.schueler_id", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.Schueler_ID = :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.schueler_id.multiple", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.kurztext", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.Kurztext = :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.kurztext.multiple", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.Kurztext IN :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.datumvon", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.DatumVon = :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.datumvon.multiple", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.DatumVon IN :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.datumbis", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.DatumBis = :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.datumbis.multiple", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.DatumBis IN :value")
@NamedQuery(name="DevDTOSchuelerMerkmale.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchuelerMerkmale.all.migration", query="SELECT e FROM DevDTOSchuelerMerkmale e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Schueler_ID","Kurztext","DatumVon","DatumBis"})
public class DevDTOSchuelerMerkmale {

	/** ID des Eintrag bei besondere Merkmale zum Schüler */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Schüler-ID des Eintrag bei besondere Merkmale zum Schüler */
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** Kurztext des Merkmals des Eintrag bei besondere Merkmale zum Schüler */
	@Column(name = "Kurztext")
	@JsonProperty
	public String Kurztext;

	/** Datum Beginn des Eintrag bei besondere Merkmale zum Schüler */
	@Column(name = "DatumVon")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String DatumVon;

	/** Datum Ende des Eintrag bei besondere Merkmale zum Schüler */
	@Column(name = "DatumBis")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String DatumBis;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerMerkmale ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerMerkmale() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerMerkmale ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 */
	public DevDTOSchuelerMerkmale(final Long ID, final Long Schueler_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Schueler_ID == null) { 
			throw new NullPointerException("Schueler_ID must not be null");
		}
		this.Schueler_ID = Schueler_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerMerkmale other = (DevDTOSchuelerMerkmale) obj;
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
		return "DevDTOSchuelerMerkmale(ID=" + this.ID + ", Schueler_ID=" + this.Schueler_ID + ", Kurztext=" + this.Kurztext + ", DatumVon=" + this.DatumVon + ", DatumBis=" + this.DatumBis + ")";
	}

}