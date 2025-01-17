package de.nrw.schule.svws.db.dto.dev.schild.lehrer;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;


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

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle LehrerDatenschutz.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@IdClass(DevDTOLehrerDatenschutzPK.class)
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "LehrerDatenschutz")
@NamedQuery(name="DevDTOLehrerDatenschutz.all", query="SELECT e FROM DevDTOLehrerDatenschutz e")
@NamedQuery(name="DevDTOLehrerDatenschutz.lehrerid", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.LehrerID = :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.lehrerid.multiple", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.LehrerID IN :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.datenschutzid", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.DatenschutzID = :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.datenschutzid.multiple", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.DatenschutzID IN :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.status", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.Status = :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.status.multiple", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.Status IN :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.abgefragt", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.Abgefragt = :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.abgefragt.multiple", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.Abgefragt IN :value")
@NamedQuery(name="DevDTOLehrerDatenschutz.primaryKeyQuery", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.LehrerID = ?1 AND e.DatenschutzID = ?2")
@NamedQuery(name="DevDTOLehrerDatenschutz.all.migration", query="SELECT e FROM DevDTOLehrerDatenschutz e WHERE e.LehrerID IS NOT NULL AND e.DatenschutzID IS NOT NULL")
@JsonPropertyOrder({"LehrerID","DatenschutzID","Status","Abgefragt"})
public class DevDTOLehrerDatenschutz {

	/** LehrerID des Datenschutzeintrags */
	@Id
	@Column(name = "LehrerID")
	@JsonProperty
	public Long LehrerID;

	/** DatenschutzID des Eintrags */
	@Id
	@Column(name = "DatenschutzID")
	@JsonProperty
	public Long DatenschutzID;

	/** Status des Datenschutz-Eintrags (true/false) */
	@Column(name = "Status")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Status;

	/** Status der Abfrage Datenschutz-Eintrags (true/false) */
	@Column(name = "Abgefragt")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Abgefragt;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerDatenschutz ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOLehrerDatenschutz() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerDatenschutz ohne eine Initialisierung der Attribute.
	 * @param LehrerID   der Wert für das Attribut LehrerID
	 * @param DatenschutzID   der Wert für das Attribut DatenschutzID
	 * @param Status   der Wert für das Attribut Status
	 * @param Abgefragt   der Wert für das Attribut Abgefragt
	 */
	public DevDTOLehrerDatenschutz(final Long LehrerID, final Long DatenschutzID, final Boolean Status, final Boolean Abgefragt) {
		if (LehrerID == null) { 
			throw new NullPointerException("LehrerID must not be null");
		}
		this.LehrerID = LehrerID;
		if (DatenschutzID == null) { 
			throw new NullPointerException("DatenschutzID must not be null");
		}
		this.DatenschutzID = DatenschutzID;
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
		DevDTOLehrerDatenschutz other = (DevDTOLehrerDatenschutz) obj;
		if (LehrerID == null) {
			if (other.LehrerID != null)
				return false;
		} else if (!LehrerID.equals(other.LehrerID))
			return false;

		if (DatenschutzID == null) {
			if (other.DatenschutzID != null)
				return false;
		} else if (!DatenschutzID.equals(other.DatenschutzID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LehrerID == null) ? 0 : LehrerID.hashCode());

		result = prime * result + ((DatenschutzID == null) ? 0 : DatenschutzID.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DevDTOLehrerDatenschutz(LehrerID=" + this.LehrerID + ", DatenschutzID=" + this.DatenschutzID + ", Status=" + this.Status + ", Abgefragt=" + this.Abgefragt + ")";
	}

}