package de.nrw.schule.svws.db.dto.dev.schild;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle SchildFilter.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchildFilter")
@NamedQuery(name="DevDTOSchildAuswahlFilter.all", query="SELECT e FROM DevDTOSchildAuswahlFilter e")
@NamedQuery(name="DevDTOSchildAuswahlFilter.id", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.id.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.art", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Art = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.art.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Art IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.name", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Name = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.name.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Name IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.beschreibung", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Beschreibung = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.beschreibung.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Beschreibung IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.tabellen", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Tabellen = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.tabellen.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Tabellen IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.zusatztabellen", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.ZusatzTabellen = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.zusatztabellen.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.ZusatzTabellen IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.bedingung", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Bedingung = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.bedingung.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.Bedingung IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.bedingungklartext", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.BedingungKlartext = :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.bedingungklartext.multiple", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.BedingungKlartext IN :value")
@NamedQuery(name="DevDTOSchildAuswahlFilter.primaryKeyQuery", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchildAuswahlFilter.all.migration", query="SELECT e FROM DevDTOSchildAuswahlFilter e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Art","Name","Beschreibung","Tabellen","ZusatzTabellen","Bedingung","BedingungKlartext"})
public class DevDTOSchildAuswahlFilter {

	/** ID des gespeicherten Filters */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Art des Filters */
	@Column(name = "Art")
	@JsonProperty
	public String Art;

	/** Bezeichnender Kurztext des Filters */
	@Column(name = "Name")
	@JsonProperty
	public String Name;

	/** Beschreibung zum Filter */
	@Column(name = "Beschreibung")
	@JsonProperty
	public String Beschreibung;

	/** Tabellen die im Filter vorkommen */
	@Column(name = "Tabellen")
	@JsonProperty
	public String Tabellen;

	/** Zusätzliche Tabellen die im Filtervorkommen */
	@Column(name = "ZusatzTabellen")
	@JsonProperty
	public String ZusatzTabellen;

	/** SQL-Text des Filters */
	@Column(name = "Bedingung")
	@JsonProperty
	public String Bedingung;

	/** Klartext der bedingung */
	@Column(name = "BedingungKlartext")
	@JsonProperty
	public String BedingungKlartext;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchildAuswahlFilter ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchildAuswahlFilter() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchildAuswahlFilter ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Name   der Wert für das Attribut Name
	 */
	public DevDTOSchildAuswahlFilter(final Long ID, final String Name) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Name == null) { 
			throw new NullPointerException("Name must not be null");
		}
		this.Name = Name;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchildAuswahlFilter other = (DevDTOSchildAuswahlFilter) obj;
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
		return "DevDTOSchildAuswahlFilter(ID=" + this.ID + ", Art=" + this.Art + ", Name=" + this.Name + ", Beschreibung=" + this.Beschreibung + ", Tabellen=" + this.Tabellen + ", ZusatzTabellen=" + this.ZusatzTabellen + ", Bedingung=" + this.Bedingung + ", BedingungKlartext=" + this.BedingungKlartext + ")";
	}

}