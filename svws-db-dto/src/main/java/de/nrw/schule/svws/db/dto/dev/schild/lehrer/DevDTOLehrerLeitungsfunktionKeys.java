package de.nrw.schule.svws.db.dto.dev.schild.lehrer;

import de.nrw.schule.svws.db.DBEntityManager;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
 * Diese Klasse dient als DTO für die Datenbanktabelle LehrerLeitungsfunktion_Keys.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "LehrerLeitungsfunktion_Keys")
@NamedQuery(name="DevDTOLehrerLeitungsfunktionKeys.all", query="SELECT e FROM DevDTOLehrerLeitungsfunktionKeys e")
@NamedQuery(name="DevDTOLehrerLeitungsfunktionKeys.id", query="SELECT e FROM DevDTOLehrerLeitungsfunktionKeys e WHERE e.ID = :value")
@NamedQuery(name="DevDTOLehrerLeitungsfunktionKeys.id.multiple", query="SELECT e FROM DevDTOLehrerLeitungsfunktionKeys e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOLehrerLeitungsfunktionKeys.primaryKeyQuery", query="SELECT e FROM DevDTOLehrerLeitungsfunktionKeys e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOLehrerLeitungsfunktionKeys.all.migration", query="SELECT e FROM DevDTOLehrerLeitungsfunktionKeys e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID"})
public class DevDTOLehrerLeitungsfunktionKeys {

	/** ID der Leitungsfunktion */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerLeitungsfunktionKeys ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOLehrerLeitungsfunktionKeys() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerLeitungsfunktionKeys ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 */
	public DevDTOLehrerLeitungsfunktionKeys(final Long ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOLehrerLeitungsfunktionKeys other = (DevDTOLehrerLeitungsfunktionKeys) obj;
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
		return "DevDTOLehrerLeitungsfunktionKeys(ID=" + this.ID + ")";
	}

}