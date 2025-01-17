package de.nrw.schule.svws.db.dto.dev.gost.klausurplanung;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle Gost_Klausuren_Raeume.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Gost_Klausuren_Raeume")
@NamedQuery(name="DevDTOGostKlausurenRaeume.all", query="SELECT e FROM DevDTOGostKlausurenRaeume e")
@NamedQuery(name="DevDTOGostKlausurenRaeume.id", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.ID = :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.id.multiple", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.termin_id", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.Termin_ID = :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.termin_id.multiple", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.Termin_ID IN :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.katalog_raum_id", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.Katalog_Raum_ID = :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.katalog_raum_id.multiple", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.Katalog_Raum_ID IN :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.bemerkungen", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.Bemerkungen = :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.bemerkungen.multiple", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.Bemerkungen IN :value")
@NamedQuery(name="DevDTOGostKlausurenRaeume.primaryKeyQuery", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOGostKlausurenRaeume.all.migration", query="SELECT e FROM DevDTOGostKlausurenRaeume e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Termin_ID","Katalog_Raum_ID","Bemerkungen"})
public class DevDTOGostKlausurenRaeume {

	/** ID des Klausurraums (generiert) */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** ID des Termins */
	@Column(name = "Termin_ID")
	@JsonProperty
	public Long Termin_ID;

	/** ID des Raums aus der Tabelle Katalog_Raeume */
	@Column(name = "Katalog_Raum_ID")
	@JsonProperty
	public Long Katalog_Raum_ID;

	/** Text für Bemerkungen zum Klausurraum */
	@Column(name = "Bemerkungen")
	@JsonProperty
	public String Bemerkungen;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOGostKlausurenRaeume ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOGostKlausurenRaeume() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOGostKlausurenRaeume ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 */
	public DevDTOGostKlausurenRaeume(final Long ID) {
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
		DevDTOGostKlausurenRaeume other = (DevDTOGostKlausurenRaeume) obj;
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
		return "DevDTOGostKlausurenRaeume(ID=" + this.ID + ", Termin_ID=" + this.Termin_ID + ", Katalog_Raum_ID=" + this.Katalog_Raum_ID + ", Bemerkungen=" + this.Bemerkungen + ")";
	}

}