package de.nrw.schule.svws.db.dto.dev.schule;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle KAoA_Berufsfeld_Keys.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "KAoA_Berufsfeld_Keys")
@NamedQuery(name="DevDTOKAoABerufsfeldKeys.all", query="SELECT e FROM DevDTOKAoABerufsfeldKeys e")
@NamedQuery(name="DevDTOKAoABerufsfeldKeys.id", query="SELECT e FROM DevDTOKAoABerufsfeldKeys e WHERE e.ID = :value")
@NamedQuery(name="DevDTOKAoABerufsfeldKeys.id.multiple", query="SELECT e FROM DevDTOKAoABerufsfeldKeys e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOKAoABerufsfeldKeys.primaryKeyQuery", query="SELECT e FROM DevDTOKAoABerufsfeldKeys e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOKAoABerufsfeldKeys.all.migration", query="SELECT e FROM DevDTOKAoABerufsfeldKeys e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID"})
public class DevDTOKAoABerufsfeldKeys {

	/** Die eindeutige ID für das Berufsfeld */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOKAoABerufsfeldKeys ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOKAoABerufsfeldKeys() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOKAoABerufsfeldKeys ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 */
	public DevDTOKAoABerufsfeldKeys(final Long ID) {
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
		DevDTOKAoABerufsfeldKeys other = (DevDTOKAoABerufsfeldKeys) obj;
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
		return "DevDTOKAoABerufsfeldKeys(ID=" + this.ID + ")";
	}

}