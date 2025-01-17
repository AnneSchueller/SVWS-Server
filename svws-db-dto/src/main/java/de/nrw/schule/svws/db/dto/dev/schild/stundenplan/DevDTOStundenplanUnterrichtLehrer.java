package de.nrw.schule.svws.db.dto.dev.schild.stundenplan;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle Stundenplan_UnterrichtLehrer.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Stundenplan_UnterrichtLehrer")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.all", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.id", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.ID = :value")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.id.multiple", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.unterricht_id", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.Unterricht_ID = :value")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.unterricht_id.multiple", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.Unterricht_ID IN :value")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.lehrer_id", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.Lehrer_ID = :value")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.lehrer_id.multiple", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.Lehrer_ID IN :value")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.primaryKeyQuery", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOStundenplanUnterrichtLehrer.all.migration", query="SELECT e FROM DevDTOStundenplanUnterrichtLehrer e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Unterricht_ID","Lehrer_ID"})
public class DevDTOStundenplanUnterrichtLehrer {

	/** Die eindeutige ID für die Zuordnung des Lehrer zum Unterricht */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Die ID des Unterricht-Eintrages im Stundenplan */
	@Column(name = "Unterricht_ID")
	@JsonProperty
	public Long Unterricht_ID;

	/** Die ID der unterrichtenden Lehrers. Im Falle von Team-Teaching werden für eine Unterricht-ID einfach mehrere Datensätze erzeugt */
	@Column(name = "Lehrer_ID")
	@JsonProperty
	public Long Lehrer_ID;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOStundenplanUnterrichtLehrer ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOStundenplanUnterrichtLehrer() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOStundenplanUnterrichtLehrer ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Unterricht_ID   der Wert für das Attribut Unterricht_ID
	 * @param Lehrer_ID   der Wert für das Attribut Lehrer_ID
	 */
	public DevDTOStundenplanUnterrichtLehrer(final Long ID, final Long Unterricht_ID, final Long Lehrer_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Unterricht_ID == null) { 
			throw new NullPointerException("Unterricht_ID must not be null");
		}
		this.Unterricht_ID = Unterricht_ID;
		if (Lehrer_ID == null) { 
			throw new NullPointerException("Lehrer_ID must not be null");
		}
		this.Lehrer_ID = Lehrer_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOStundenplanUnterrichtLehrer other = (DevDTOStundenplanUnterrichtLehrer) obj;
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
		return "DevDTOStundenplanUnterrichtLehrer(ID=" + this.ID + ", Unterricht_ID=" + this.Unterricht_ID + ", Lehrer_ID=" + this.Lehrer_ID + ")";
	}

}