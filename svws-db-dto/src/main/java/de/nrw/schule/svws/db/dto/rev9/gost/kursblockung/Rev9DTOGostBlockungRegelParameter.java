package de.nrw.schule.svws.db.dto.rev9.gost.kursblockung;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle Gost_Blockung_Regelparameter.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@IdClass(Rev9DTOGostBlockungRegelParameterPK.class)
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Gost_Blockung_Regelparameter")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.all", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.regel_id", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Regel_ID = :value")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.regel_id.multiple", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Regel_ID IN :value")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.nummer", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Nummer = :value")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.nummer.multiple", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Nummer IN :value")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.parameter", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Parameter = :value")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.parameter.multiple", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Parameter IN :value")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.primaryKeyQuery", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Regel_ID = ?1 AND e.Nummer = ?2")
@NamedQuery(name="Rev9DTOGostBlockungRegelParameter.all.migration", query="SELECT e FROM Rev9DTOGostBlockungRegelParameter e WHERE e.Regel_ID IS NOT NULL AND e.Nummer IS NOT NULL")
@JsonPropertyOrder({"Regel_ID","Nummer","Parameter"})
public class Rev9DTOGostBlockungRegelParameter {

	/** ID des Regel-Parameters */
	@Id
	@Column(name = "Regel_ID")
	@JsonProperty
	public Long Regel_ID;

	/** Die Nummer des Parameters der Regel, beginnend bei 1 */
	@Id
	@Column(name = "Nummer")
	@JsonProperty
	public Integer Nummer;

	/** Der Wert des Parameters der Regel (hängt vom Typ der Regel ab) */
	@Column(name = "Parameter")
	@JsonProperty
	public Long Parameter;

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOGostBlockungRegelParameter ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private Rev9DTOGostBlockungRegelParameter() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOGostBlockungRegelParameter ohne eine Initialisierung der Attribute.
	 * @param Regel_ID   der Wert für das Attribut Regel_ID
	 * @param Nummer   der Wert für das Attribut Nummer
	 * @param Parameter   der Wert für das Attribut Parameter
	 */
	public Rev9DTOGostBlockungRegelParameter(final Long Regel_ID, final Integer Nummer, final Long Parameter) {
		if (Regel_ID == null) { 
			throw new NullPointerException("Regel_ID must not be null");
		}
		this.Regel_ID = Regel_ID;
		if (Nummer == null) { 
			throw new NullPointerException("Nummer must not be null");
		}
		this.Nummer = Nummer;
		if (Parameter == null) { 
			throw new NullPointerException("Parameter must not be null");
		}
		this.Parameter = Parameter;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rev9DTOGostBlockungRegelParameter other = (Rev9DTOGostBlockungRegelParameter) obj;
		if (Regel_ID == null) {
			if (other.Regel_ID != null)
				return false;
		} else if (!Regel_ID.equals(other.Regel_ID))
			return false;

		if (Nummer == null) {
			if (other.Nummer != null)
				return false;
		} else if (!Nummer.equals(other.Nummer))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Regel_ID == null) ? 0 : Regel_ID.hashCode());

		result = prime * result + ((Nummer == null) ? 0 : Nummer.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "Rev9DTOGostBlockungRegelParameter(Regel_ID=" + this.Regel_ID + ", Nummer=" + this.Nummer + ", Parameter=" + this.Parameter + ")";
	}

}