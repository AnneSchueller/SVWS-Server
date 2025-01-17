package de.nrw.schule.svws.db.dto.dev.schild.lehrer;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle LehrerLehramtFachr.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@IdClass(DevDTOLehrerLehramtFachrichtungPK.class)
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "LehrerLehramtFachr")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.all", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.lehrer_id", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.Lehrer_ID = :value")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.lehrer_id.multiple", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.Lehrer_ID IN :value")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.fachrkrz", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.FachrKrz = :value")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.fachrkrz.multiple", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.FachrKrz IN :value")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.fachranerkennungkrz", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.FachrAnerkennungKrz = :value")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.fachranerkennungkrz.multiple", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.FachrAnerkennungKrz IN :value")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.primaryKeyQuery", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.Lehrer_ID = ?1 AND e.FachrKrz = ?2")
@NamedQuery(name="DevDTOLehrerLehramtFachrichtung.all.migration", query="SELECT e FROM DevDTOLehrerLehramtFachrichtung e WHERE e.Lehrer_ID IS NOT NULL AND e.FachrKrz IS NOT NULL")
@JsonPropertyOrder({"Lehrer_ID","FachrKrz","FachrAnerkennungKrz"})
public class DevDTOLehrerLehramtFachrichtung {

	/** LehrerID zu der die Fachrichtung gehört */
	@Id
	@Column(name = "Lehrer_ID")
	@JsonProperty
	public Long Lehrer_ID;

	/** Fachrichtungskürzel */
	@Id
	@Column(name = "FachrKrz")
	@JsonProperty
	public String FachrKrz;

	/** FachrichtungAnerkennungskürzel */
	@Column(name = "FachrAnerkennungKrz")
	@JsonProperty
	public String FachrAnerkennungKrz;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerLehramtFachrichtung ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOLehrerLehramtFachrichtung() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerLehramtFachrichtung ohne eine Initialisierung der Attribute.
	 * @param Lehrer_ID   der Wert für das Attribut Lehrer_ID
	 */
	public DevDTOLehrerLehramtFachrichtung(final Long Lehrer_ID) {
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
		DevDTOLehrerLehramtFachrichtung other = (DevDTOLehrerLehramtFachrichtung) obj;
		if (Lehrer_ID == null) {
			if (other.Lehrer_ID != null)
				return false;
		} else if (!Lehrer_ID.equals(other.Lehrer_ID))
			return false;

		if (FachrKrz == null) {
			if (other.FachrKrz != null)
				return false;
		} else if (!FachrKrz.equals(other.FachrKrz))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Lehrer_ID == null) ? 0 : Lehrer_ID.hashCode());

		result = prime * result + ((FachrKrz == null) ? 0 : FachrKrz.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DevDTOLehrerLehramtFachrichtung(Lehrer_ID=" + this.Lehrer_ID + ", FachrKrz=" + this.FachrKrz + ", FachrAnerkennungKrz=" + this.FachrAnerkennungKrz + ")";
	}

}