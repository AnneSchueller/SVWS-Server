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
 * Diese Klasse dient als DTO für die Datenbanktabelle Stundenplan_Raeume.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Stundenplan_Raeume")
@NamedQuery(name="DevDTOStundenplanRaum.all", query="SELECT e FROM DevDTOStundenplanRaum e")
@NamedQuery(name="DevDTOStundenplanRaum.id", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.ID = :value")
@NamedQuery(name="DevDTOStundenplanRaum.id.multiple", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOStundenplanRaum.stundenplan_id", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Stundenplan_ID = :value")
@NamedQuery(name="DevDTOStundenplanRaum.stundenplan_id.multiple", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Stundenplan_ID IN :value")
@NamedQuery(name="DevDTOStundenplanRaum.kuerzel", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Kuerzel = :value")
@NamedQuery(name="DevDTOStundenplanRaum.kuerzel.multiple", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Kuerzel IN :value")
@NamedQuery(name="DevDTOStundenplanRaum.beschreibung", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Beschreibung = :value")
@NamedQuery(name="DevDTOStundenplanRaum.beschreibung.multiple", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Beschreibung IN :value")
@NamedQuery(name="DevDTOStundenplanRaum.groesse", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Groesse = :value")
@NamedQuery(name="DevDTOStundenplanRaum.groesse.multiple", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.Groesse IN :value")
@NamedQuery(name="DevDTOStundenplanRaum.primaryKeyQuery", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOStundenplanRaum.all.migration", query="SELECT e FROM DevDTOStundenplanRaum e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Stundenplan_ID","Kuerzel","Beschreibung","Groesse"})
public class DevDTOStundenplanRaum {

	/** Die ID identifiziert einen Raumeintrag für einen Stundenplan eindeutig - hat keinen Bezug zur ID der Katalog-Tabelle */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Die ID des Stundenplans, dem dieser Raumeintrag zugeordnet wird */
	@Column(name = "Stundenplan_ID")
	@JsonProperty
	public Long Stundenplan_ID;

	/** Das Kürzel des Raums */
	@Column(name = "Kuerzel")
	@JsonProperty
	public String Kuerzel;

	/** Gegebenenfalls eine ausführlichere Beschreibung des Raumes */
	@Column(name = "Beschreibung")
	@JsonProperty
	public String Beschreibung;

	/** Die Größe des Raumes, d.h. wie viele Schüler hier max. Platz haben */
	@Column(name = "Groesse")
	@JsonProperty
	public Integer Groesse;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOStundenplanRaum ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOStundenplanRaum() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOStundenplanRaum ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Stundenplan_ID   der Wert für das Attribut Stundenplan_ID
	 * @param Kuerzel   der Wert für das Attribut Kuerzel
	 * @param Beschreibung   der Wert für das Attribut Beschreibung
	 * @param Groesse   der Wert für das Attribut Groesse
	 */
	public DevDTOStundenplanRaum(final Long ID, final Long Stundenplan_ID, final String Kuerzel, final String Beschreibung, final Integer Groesse) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Stundenplan_ID == null) { 
			throw new NullPointerException("Stundenplan_ID must not be null");
		}
		this.Stundenplan_ID = Stundenplan_ID;
		if (Kuerzel == null) { 
			throw new NullPointerException("Kuerzel must not be null");
		}
		this.Kuerzel = Kuerzel;
		if (Beschreibung == null) { 
			throw new NullPointerException("Beschreibung must not be null");
		}
		this.Beschreibung = Beschreibung;
		if (Groesse == null) { 
			throw new NullPointerException("Groesse must not be null");
		}
		this.Groesse = Groesse;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOStundenplanRaum other = (DevDTOStundenplanRaum) obj;
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
		return "DevDTOStundenplanRaum(ID=" + this.ID + ", Stundenplan_ID=" + this.Stundenplan_ID + ", Kuerzel=" + this.Kuerzel + ", Beschreibung=" + this.Beschreibung + ", Groesse=" + this.Groesse + ")";
	}

}