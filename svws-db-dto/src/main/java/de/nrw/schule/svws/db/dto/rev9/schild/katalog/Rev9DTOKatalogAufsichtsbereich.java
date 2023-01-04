package de.nrw.schule.svws.db.dto.rev9.schild.katalog;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle Katalog_Aufsichtsbereich.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Katalog_Aufsichtsbereich")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.all", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.id", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.ID = :value")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.id.multiple", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.ID IN :value")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.kuerzel", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.Kuerzel = :value")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.kuerzel.multiple", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.Kuerzel IN :value")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.beschreibung", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.Beschreibung = :value")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.beschreibung.multiple", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.Beschreibung IN :value")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.primaryKeyQuery", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.ID = ?1")
@NamedQuery(name="Rev9DTOKatalogAufsichtsbereich.all.migration", query="SELECT e FROM Rev9DTOKatalogAufsichtsbereich e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Kuerzel","Beschreibung"})
public class Rev9DTOKatalogAufsichtsbereich {

	/** Die ID identifiziert einen Aufsichtsbereich eindeutig */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Die Kurzbezeichnung des Aufsichtsbereichs */
	@Column(name = "Kuerzel")
	@JsonProperty
	public String Kuerzel;

	/** Gegebenenfalls eine ausführlichere Beschreibung des Aufsichtsbereichs */
	@Column(name = "Beschreibung")
	@JsonProperty
	public String Beschreibung;

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOKatalogAufsichtsbereich ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private Rev9DTOKatalogAufsichtsbereich() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOKatalogAufsichtsbereich ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Kuerzel   der Wert für das Attribut Kuerzel
	 * @param Beschreibung   der Wert für das Attribut Beschreibung
	 */
	public Rev9DTOKatalogAufsichtsbereich(final Long ID, final String Kuerzel, final String Beschreibung) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Kuerzel == null) { 
			throw new NullPointerException("Kuerzel must not be null");
		}
		this.Kuerzel = Kuerzel;
		if (Beschreibung == null) { 
			throw new NullPointerException("Beschreibung must not be null");
		}
		this.Beschreibung = Beschreibung;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rev9DTOKatalogAufsichtsbereich other = (Rev9DTOKatalogAufsichtsbereich) obj;
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
		return "Rev9DTOKatalogAufsichtsbereich(ID=" + this.ID + ", Kuerzel=" + this.Kuerzel + ", Beschreibung=" + this.Beschreibung + ")";
	}

}