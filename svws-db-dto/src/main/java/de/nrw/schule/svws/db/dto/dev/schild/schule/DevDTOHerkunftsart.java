package de.nrw.schule.svws.db.dto.dev.schild.schule;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle Herkunftsart.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Herkunftsart")
@NamedQuery(name="DevDTOHerkunftsart.all", query="SELECT e FROM DevDTOHerkunftsart e")
@NamedQuery(name="DevDTOHerkunftsart.id", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.ID = :value")
@NamedQuery(name="DevDTOHerkunftsart.id.multiple", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOHerkunftsart.kuerzel", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.Kuerzel = :value")
@NamedQuery(name="DevDTOHerkunftsart.kuerzel.multiple", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.Kuerzel IN :value")
@NamedQuery(name="DevDTOHerkunftsart.gueltigvon", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.gueltigVon = :value")
@NamedQuery(name="DevDTOHerkunftsart.gueltigvon.multiple", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.gueltigVon IN :value")
@NamedQuery(name="DevDTOHerkunftsart.gueltigbis", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.gueltigBis = :value")
@NamedQuery(name="DevDTOHerkunftsart.gueltigbis.multiple", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.gueltigBis IN :value")
@NamedQuery(name="DevDTOHerkunftsart.primaryKeyQuery", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOHerkunftsart.all.migration", query="SELECT e FROM DevDTOHerkunftsart e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Kuerzel","gueltigVon","gueltigBis"})
public class DevDTOHerkunftsart {

	/** Die ID der Herkunftsart */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Das Kürzel der Herkunftsart */
	@Column(name = "Kuerzel")
	@JsonProperty
	public String Kuerzel;

	/** Gibt die Gültigkeit ab welchem Schuljahr an */
	@Column(name = "gueltigVon")
	@JsonProperty
	public Integer gueltigVon;

	/** Gibt die Gültigkeit bis zu welchem Schuljahr an */
	@Column(name = "gueltigBis")
	@JsonProperty
	public Integer gueltigBis;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOHerkunftsart ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOHerkunftsart() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOHerkunftsart ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Kuerzel   der Wert für das Attribut Kuerzel
	 */
	public DevDTOHerkunftsart(final Long ID, final String Kuerzel) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Kuerzel == null) { 
			throw new NullPointerException("Kuerzel must not be null");
		}
		this.Kuerzel = Kuerzel;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOHerkunftsart other = (DevDTOHerkunftsart) obj;
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
		return "DevDTOHerkunftsart(ID=" + this.ID + ", Kuerzel=" + this.Kuerzel + ", gueltigVon=" + this.gueltigVon + ", gueltigBis=" + this.gueltigBis + ")";
	}

}