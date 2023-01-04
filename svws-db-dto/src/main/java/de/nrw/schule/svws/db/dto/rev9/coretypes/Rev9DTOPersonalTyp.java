package de.nrw.schule.svws.db.dto.rev9.coretypes;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle PersonalTypen.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "PersonalTypen")
@NamedQuery(name="Rev9DTOPersonalTyp.all", query="SELECT e FROM Rev9DTOPersonalTyp e")
@NamedQuery(name="Rev9DTOPersonalTyp.id", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.ID = :value")
@NamedQuery(name="Rev9DTOPersonalTyp.id.multiple", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.ID IN :value")
@NamedQuery(name="Rev9DTOPersonalTyp.kuerzel", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.Kuerzel = :value")
@NamedQuery(name="Rev9DTOPersonalTyp.kuerzel.multiple", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.Kuerzel IN :value")
@NamedQuery(name="Rev9DTOPersonalTyp.bezeichnung", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.Bezeichnung = :value")
@NamedQuery(name="Rev9DTOPersonalTyp.bezeichnung.multiple", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.Bezeichnung IN :value")
@NamedQuery(name="Rev9DTOPersonalTyp.gueltigvon", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.gueltigVon = :value")
@NamedQuery(name="Rev9DTOPersonalTyp.gueltigvon.multiple", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.gueltigVon IN :value")
@NamedQuery(name="Rev9DTOPersonalTyp.gueltigbis", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.gueltigBis = :value")
@NamedQuery(name="Rev9DTOPersonalTyp.gueltigbis.multiple", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.gueltigBis IN :value")
@NamedQuery(name="Rev9DTOPersonalTyp.primaryKeyQuery", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.ID = ?1")
@NamedQuery(name="Rev9DTOPersonalTyp.all.migration", query="SELECT e FROM Rev9DTOPersonalTyp e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Kuerzel","Bezeichnung","gueltigVon","gueltigBis"})
public class Rev9DTOPersonalTyp {

	/** ID des Personal-Typs */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Das Kürzel des Personal-Typs */
	@Column(name = "Kuerzel")
	@JsonProperty
	public String Kuerzel;

	/** Die (Lang-)Bezeichnung des Personal-Typs */
	@Column(name = "Bezeichnung")
	@JsonProperty
	public String Bezeichnung;

	/** Der Datensatz ist gültig ab dem Schuljahr */
	@Column(name = "gueltigVon")
	@JsonProperty
	public Integer gueltigVon;

	/** Der Datensatz ist gültig bis zu dem Schuljahr */
	@Column(name = "gueltigBis")
	@JsonProperty
	public Integer gueltigBis;

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOPersonalTyp ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private Rev9DTOPersonalTyp() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOPersonalTyp ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 */
	public Rev9DTOPersonalTyp(final Long ID) {
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
		Rev9DTOPersonalTyp other = (Rev9DTOPersonalTyp) obj;
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
		return "Rev9DTOPersonalTyp(ID=" + this.ID + ", Kuerzel=" + this.Kuerzel + ", Bezeichnung=" + this.Bezeichnung + ", gueltigVon=" + this.gueltigVon + ", gueltigBis=" + this.gueltigBis + ")";
	}

}