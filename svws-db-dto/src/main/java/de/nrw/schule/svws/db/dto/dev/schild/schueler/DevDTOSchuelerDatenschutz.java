package de.nrw.schule.svws.db.dto.dev.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;
import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultMinusConverter;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.nrw.schule.svws.csv.converter.current.Boolean01ConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.Boolean01ConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultMinusConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultMinusConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerDatenschutz.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@IdClass(DevDTOSchuelerDatenschutzPK.class)
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerDatenschutz")
@NamedQuery(name="DevDTOSchuelerDatenschutz.all", query="SELECT e FROM DevDTOSchuelerDatenschutz e")
@NamedQuery(name="DevDTOSchuelerDatenschutz.schueler_id", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Schueler_ID = :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.schueler_id.multiple", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.datenschutz_id", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Datenschutz_ID = :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.datenschutz_id.multiple", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Datenschutz_ID IN :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.status", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Status = :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.status.multiple", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Status IN :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.abgefragt", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Abgefragt = :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.abgefragt.multiple", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Abgefragt IN :value")
@NamedQuery(name="DevDTOSchuelerDatenschutz.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Schueler_ID = ?1 AND e.Datenschutz_ID = ?2")
@NamedQuery(name="DevDTOSchuelerDatenschutz.all.migration", query="SELECT e FROM DevDTOSchuelerDatenschutz e WHERE e.Schueler_ID IS NOT NULL AND e.Datenschutz_ID IS NOT NULL")
@JsonPropertyOrder({"Schueler_ID","Datenschutz_ID","Status","Abgefragt"})
public class DevDTOSchuelerDatenschutz {

	/** Fremdschlüssel auf Tabelle Schueler */
	@Id
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** Fremdschlüssel auf den Katalog der DSGVO-Merkmale */
	@Id
	@Column(name = "Datenschutz_ID")
	@JsonProperty
	public Long Datenschutz_ID;

	/** Gibt an ob eine Zustimmung zum Merkmal vorliegt. */
	@Column(name = "Status")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean Status;

	/** Status der Abfrage Datenschutz-Eintrags (true/false) */
	@Column(name = "Abgefragt")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Abgefragt;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerDatenschutz ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerDatenschutz() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerDatenschutz ohne eine Initialisierung der Attribute.
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 * @param Datenschutz_ID   der Wert für das Attribut Datenschutz_ID
	 * @param Status   der Wert für das Attribut Status
	 * @param Abgefragt   der Wert für das Attribut Abgefragt
	 */
	public DevDTOSchuelerDatenschutz(final Long Schueler_ID, final Long Datenschutz_ID, final Boolean Status, final Boolean Abgefragt) {
		if (Schueler_ID == null) { 
			throw new NullPointerException("Schueler_ID must not be null");
		}
		this.Schueler_ID = Schueler_ID;
		if (Datenschutz_ID == null) { 
			throw new NullPointerException("Datenschutz_ID must not be null");
		}
		this.Datenschutz_ID = Datenschutz_ID;
		if (Status == null) { 
			throw new NullPointerException("Status must not be null");
		}
		this.Status = Status;
		if (Abgefragt == null) { 
			throw new NullPointerException("Abgefragt must not be null");
		}
		this.Abgefragt = Abgefragt;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerDatenschutz other = (DevDTOSchuelerDatenschutz) obj;
		if (Schueler_ID == null) {
			if (other.Schueler_ID != null)
				return false;
		} else if (!Schueler_ID.equals(other.Schueler_ID))
			return false;

		if (Datenschutz_ID == null) {
			if (other.Datenschutz_ID != null)
				return false;
		} else if (!Datenschutz_ID.equals(other.Datenschutz_ID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Schueler_ID == null) ? 0 : Schueler_ID.hashCode());

		result = prime * result + ((Datenschutz_ID == null) ? 0 : Datenschutz_ID.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DevDTOSchuelerDatenschutz(Schueler_ID=" + this.Schueler_ID + ", Datenschutz_ID=" + this.Datenschutz_ID + ", Status=" + this.Status + ", Abgefragt=" + this.Abgefragt + ")";
	}

}