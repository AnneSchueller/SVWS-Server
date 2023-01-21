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
 * Diese Klasse dient als DTO für die Datenbanktabelle Nationalitaeten_Keys.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Nationalitaeten_Keys")
@NamedQuery(name="DevDTONationalitaetenKeys.all", query="SELECT e FROM DevDTONationalitaetenKeys e")
@NamedQuery(name="DevDTONationalitaetenKeys.destatiscode", query="SELECT e FROM DevDTONationalitaetenKeys e WHERE e.DEStatisCode = :value")
@NamedQuery(name="DevDTONationalitaetenKeys.destatiscode.multiple", query="SELECT e FROM DevDTONationalitaetenKeys e WHERE e.DEStatisCode IN :value")
@NamedQuery(name="DevDTONationalitaetenKeys.primaryKeyQuery", query="SELECT e FROM DevDTONationalitaetenKeys e WHERE e.DEStatisCode = ?1")
@NamedQuery(name="DevDTONationalitaetenKeys.all.migration", query="SELECT e FROM DevDTONationalitaetenKeys e WHERE e.DEStatisCode IS NOT NULL")
@JsonPropertyOrder({"DEStatisCode"})
public class DevDTONationalitaetenKeys {

	/** Der dreistellige Länder-Code des statistischen Bundesamtes (DESTATIS) */
	@Id
	@Column(name = "DEStatisCode")
	@JsonProperty
	public String DEStatisCode;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTONationalitaetenKeys ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTONationalitaetenKeys() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTONationalitaetenKeys ohne eine Initialisierung der Attribute.
	 * @param DEStatisCode   der Wert für das Attribut DEStatisCode
	 */
	public DevDTONationalitaetenKeys(final String DEStatisCode) {
		if (DEStatisCode == null) { 
			throw new NullPointerException("DEStatisCode must not be null");
		}
		this.DEStatisCode = DEStatisCode;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTONationalitaetenKeys other = (DevDTONationalitaetenKeys) obj;
		if (DEStatisCode == null) {
			if (other.DEStatisCode != null)
				return false;
		} else if (!DEStatisCode.equals(other.DEStatisCode))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DEStatisCode == null) ? 0 : DEStatisCode.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DevDTONationalitaetenKeys(DEStatisCode=" + this.DEStatisCode + ")";
	}

}