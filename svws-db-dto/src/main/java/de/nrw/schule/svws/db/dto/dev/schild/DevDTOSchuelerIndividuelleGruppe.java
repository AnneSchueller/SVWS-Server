package de.nrw.schule.svws.db.dto.dev.schild;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerListe.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerListe")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.all", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.id", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.id.multiple", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.bezeichnung", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.Bezeichnung = :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.bezeichnung.multiple", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.Bezeichnung IN :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.erzeuger", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.Erzeuger = :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.erzeuger.multiple", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.Erzeuger IN :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.privat", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.Privat = :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.privat.multiple", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.Privat IN :value")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchuelerIndividuelleGruppe.all.migration", query="SELECT e FROM DevDTOSchuelerIndividuelleGruppe e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Bezeichnung","Erzeuger","Privat"})
public class DevDTOSchuelerIndividuelleGruppe {

	/** ID der individuellen Schülerliste */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Bezeichnung der individuellen Schülerliste */
	@Column(name = "Bezeichnung")
	@JsonProperty
	public String Bezeichnung;

	/** UserID Erzeuger der individuellen Schülerliste */
	@Column(name = "Erzeuger")
	@JsonProperty
	public String Erzeuger;

	/** Schülerliste Privat oder Öffentlich */
	@Column(name = "Privat")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultPlusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultPlusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultPlusConverterDeserializer.class)
	public Boolean Privat;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerIndividuelleGruppe ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerIndividuelleGruppe() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerIndividuelleGruppe ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Bezeichnung   der Wert für das Attribut Bezeichnung
	 */
	public DevDTOSchuelerIndividuelleGruppe(final Long ID, final String Bezeichnung) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Bezeichnung == null) { 
			throw new NullPointerException("Bezeichnung must not be null");
		}
		this.Bezeichnung = Bezeichnung;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerIndividuelleGruppe other = (DevDTOSchuelerIndividuelleGruppe) obj;
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
		return "DevDTOSchuelerIndividuelleGruppe(ID=" + this.ID + ", Bezeichnung=" + this.Bezeichnung + ", Erzeuger=" + this.Erzeuger + ", Privat=" + this.Privat + ")";
	}

}