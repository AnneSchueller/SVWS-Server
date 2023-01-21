package de.nrw.schule.svws.db.dto.dev.schild.berufskolleg;

import de.nrw.schule.svws.db.DBEntityManager;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerZuweisungen.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@IdClass(DevDTOSchuelerZuweisungPK.class)
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerZuweisungen")
@NamedQuery(name="DevDTOSchuelerZuweisung.all", query="SELECT e FROM DevDTOSchuelerZuweisung e")
@NamedQuery(name="DevDTOSchuelerZuweisung.abschnitt_id", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Abschnitt_ID = :value")
@NamedQuery(name="DevDTOSchuelerZuweisung.abschnitt_id.multiple", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Abschnitt_ID IN :value")
@NamedQuery(name="DevDTOSchuelerZuweisung.fach_id", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Fach_ID = :value")
@NamedQuery(name="DevDTOSchuelerZuweisung.fach_id.multiple", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Fach_ID IN :value")
@NamedQuery(name="DevDTOSchuelerZuweisung.kursart", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Kursart = :value")
@NamedQuery(name="DevDTOSchuelerZuweisung.kursart.multiple", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Kursart IN :value")
@NamedQuery(name="DevDTOSchuelerZuweisung.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Abschnitt_ID = ?1 AND e.Fach_ID = ?2")
@NamedQuery(name="DevDTOSchuelerZuweisung.all.migration", query="SELECT e FROM DevDTOSchuelerZuweisung e WHERE e.Abschnitt_ID IS NOT NULL AND e.Fach_ID IS NOT NULL")
@JsonPropertyOrder({"Abschnitt_ID","Fach_ID","Kursart"})
public class DevDTOSchuelerZuweisung {

	/** LernabschnittsID  der Zuweisung (E G Kurse GE und PS SK) */
	@Id
	@Column(name = "Abschnitt_ID")
	@JsonProperty
	public Long Abschnitt_ID;

	/** FachID der Zuweisung */
	@Id
	@Column(name = "Fach_ID")
	@JsonProperty
	public Long Fach_ID;

	/** Kursart der Zuweisung */
	@Column(name = "Kursart")
	@JsonProperty
	public String Kursart;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerZuweisung ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerZuweisung() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerZuweisung ohne eine Initialisierung der Attribute.
	 * @param Abschnitt_ID   der Wert für das Attribut Abschnitt_ID
	 * @param Fach_ID   der Wert für das Attribut Fach_ID
	 */
	public DevDTOSchuelerZuweisung(final Long Abschnitt_ID, final Long Fach_ID) {
		if (Abschnitt_ID == null) { 
			throw new NullPointerException("Abschnitt_ID must not be null");
		}
		this.Abschnitt_ID = Abschnitt_ID;
		if (Fach_ID == null) { 
			throw new NullPointerException("Fach_ID must not be null");
		}
		this.Fach_ID = Fach_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerZuweisung other = (DevDTOSchuelerZuweisung) obj;
		if (Abschnitt_ID == null) {
			if (other.Abschnitt_ID != null)
				return false;
		} else if (!Abschnitt_ID.equals(other.Abschnitt_ID))
			return false;

		if (Fach_ID == null) {
			if (other.Fach_ID != null)
				return false;
		} else if (!Fach_ID.equals(other.Fach_ID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Abschnitt_ID == null) ? 0 : Abschnitt_ID.hashCode());

		result = prime * result + ((Fach_ID == null) ? 0 : Fach_ID.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DevDTOSchuelerZuweisung(Abschnitt_ID=" + this.Abschnitt_ID + ", Fach_ID=" + this.Fach_ID + ", Kursart=" + this.Kursart + ")";
	}

}