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
 * Diese Klasse dient als DTO für die Datenbanktabelle LehrerAnrechnung.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "LehrerAnrechnung")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.all", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.id", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.ID = :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.id.multiple", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.abschnitt_id", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.Abschnitt_ID = :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.abschnitt_id.multiple", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.Abschnitt_ID IN :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.anrechnungsgrundkrz", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.AnrechnungsgrundKrz = :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.anrechnungsgrundkrz.multiple", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.AnrechnungsgrundKrz IN :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.anrechnungstd", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.AnrechnungStd = :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.anrechnungstd.multiple", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.AnrechnungStd IN :value")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.primaryKeyQuery", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOLehrerAnrechnungsstunde.all.migration", query="SELECT e FROM DevDTOLehrerAnrechnungsstunde e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Abschnitt_ID","AnrechnungsgrundKrz","AnrechnungStd"})
public class DevDTOLehrerAnrechnungsstunde {

	/** ID für den Eintrag für die Anrechnungsstunden */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** ID der Lehrerabschnittsdaten */
	@Column(name = "Abschnitt_ID")
	@JsonProperty
	public Long Abschnitt_ID;

	/** Anrechnungsstundentext  für die Anrechnungsstunden */
	@Column(name = "AnrechnungsgrundKrz")
	@JsonProperty
	public String AnrechnungsgrundKrz;

	/** Zahl der Anrechnungsstunden für die Anrechnungsstunden */
	@Column(name = "AnrechnungStd")
	@JsonProperty
	public Double AnrechnungStd;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerAnrechnungsstunde ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOLehrerAnrechnungsstunde() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOLehrerAnrechnungsstunde ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Abschnitt_ID   der Wert für das Attribut Abschnitt_ID
	 */
	public DevDTOLehrerAnrechnungsstunde(final Long ID, final Long Abschnitt_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Abschnitt_ID == null) { 
			throw new NullPointerException("Abschnitt_ID must not be null");
		}
		this.Abschnitt_ID = Abschnitt_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOLehrerAnrechnungsstunde other = (DevDTOLehrerAnrechnungsstunde) obj;
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
		return "DevDTOLehrerAnrechnungsstunde(ID=" + this.ID + ", Abschnitt_ID=" + this.Abschnitt_ID + ", AnrechnungsgrundKrz=" + this.AnrechnungsgrundKrz + ", AnrechnungStd=" + this.AnrechnungStd + ")";
	}

}