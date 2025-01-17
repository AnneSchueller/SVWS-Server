package de.nrw.schule.svws.db.dto.current.gost.klausurplanung;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.UhrzeitConverter;


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
import de.nrw.schule.svws.csv.converter.current.UhrzeitConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.UhrzeitConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle Gost_Klausuren_Kursklausuren.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Gost_Klausuren_Kursklausuren")
@NamedQuery(name="DTOGostKlausurenKursklausuren.all", query="SELECT e FROM DTOGostKlausurenKursklausuren e")
@NamedQuery(name="DTOGostKlausurenKursklausuren.id", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.ID = :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.id.multiple", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.ID IN :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.vorgabe_id", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Vorgabe_ID = :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.vorgabe_id.multiple", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Vorgabe_ID IN :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.kurs_id", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Kurs_ID = :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.kurs_id.multiple", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Kurs_ID IN :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.termin_id", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Termin_ID = :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.termin_id.multiple", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Termin_ID IN :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.startzeit", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Startzeit = :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.startzeit.multiple", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.Startzeit IN :value")
@NamedQuery(name="DTOGostKlausurenKursklausuren.primaryKeyQuery", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.ID = ?1")
@NamedQuery(name="DTOGostKlausurenKursklausuren.all.migration", query="SELECT e FROM DTOGostKlausurenKursklausuren e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Vorgabe_ID","Kurs_ID","Termin_ID","Startzeit"})
public class DTOGostKlausurenKursklausuren {

	/** ID der Kursklausur (generiert) */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** ID der Klausurvorgaben */
	@Column(name = "Vorgabe_ID")
	@JsonProperty
	public Long Vorgabe_ID;

	/** Kurs_ID der Klausur */
	@Column(name = "Kurs_ID")
	@JsonProperty
	public Long Kurs_ID;

	/** ID des Klausurtermins */
	@Column(name = "Termin_ID")
	@JsonProperty
	public Long Termin_ID;

	/** Startzeit der Klausur, wenn abweichend von Startzeit der Klausur-Schiene */
	@Column(name = "Startzeit")
	@JsonProperty
	@Convert(converter=UhrzeitConverter.class)
	@JsonSerialize(using=UhrzeitConverterSerializer.class)
	@JsonDeserialize(using=UhrzeitConverterDeserializer.class)
	public String Startzeit;

	/**
	 * Erstellt ein neues Objekt der Klasse DTOGostKlausurenKursklausuren ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DTOGostKlausurenKursklausuren() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DTOGostKlausurenKursklausuren ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Vorgabe_ID   der Wert für das Attribut Vorgabe_ID
	 * @param Kurs_ID   der Wert für das Attribut Kurs_ID
	 */
	public DTOGostKlausurenKursklausuren(final Long ID, final Long Vorgabe_ID, final Long Kurs_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Vorgabe_ID == null) { 
			throw new NullPointerException("Vorgabe_ID must not be null");
		}
		this.Vorgabe_ID = Vorgabe_ID;
		if (Kurs_ID == null) { 
			throw new NullPointerException("Kurs_ID must not be null");
		}
		this.Kurs_ID = Kurs_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOGostKlausurenKursklausuren other = (DTOGostKlausurenKursklausuren) obj;
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
		return "DTOGostKlausurenKursklausuren(ID=" + this.ID + ", Vorgabe_ID=" + this.Vorgabe_ID + ", Kurs_ID=" + this.Kurs_ID + ", Termin_ID=" + this.Termin_ID + ", Startzeit=" + this.Startzeit + ")";
	}

}