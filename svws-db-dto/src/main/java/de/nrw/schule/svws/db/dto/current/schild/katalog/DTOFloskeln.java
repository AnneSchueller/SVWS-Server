package de.nrw.schule.svws.db.dto.current.schild.katalog;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle Floskeln.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Floskeln")
@NamedQuery(name="DTOFloskeln.all", query="SELECT e FROM DTOFloskeln e")
@NamedQuery(name="DTOFloskeln.kuerzel", query="SELECT e FROM DTOFloskeln e WHERE e.Kuerzel = :value")
@NamedQuery(name="DTOFloskeln.kuerzel.multiple", query="SELECT e FROM DTOFloskeln e WHERE e.Kuerzel IN :value")
@NamedQuery(name="DTOFloskeln.floskeltext", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelText = :value")
@NamedQuery(name="DTOFloskeln.floskeltext.multiple", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelText IN :value")
@NamedQuery(name="DTOFloskeln.floskelgruppe", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelGruppe = :value")
@NamedQuery(name="DTOFloskeln.floskelgruppe.multiple", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelGruppe IN :value")
@NamedQuery(name="DTOFloskeln.floskelfach", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelFach = :value")
@NamedQuery(name="DTOFloskeln.floskelfach.multiple", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelFach IN :value")
@NamedQuery(name="DTOFloskeln.floskelniveau", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelNiveau = :value")
@NamedQuery(name="DTOFloskeln.floskelniveau.multiple", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelNiveau IN :value")
@NamedQuery(name="DTOFloskeln.floskeljahrgang", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelJahrgang = :value")
@NamedQuery(name="DTOFloskeln.floskeljahrgang.multiple", query="SELECT e FROM DTOFloskeln e WHERE e.FloskelJahrgang IN :value")
@NamedQuery(name="DTOFloskeln.primaryKeyQuery", query="SELECT e FROM DTOFloskeln e WHERE e.Kuerzel = ?1")
@NamedQuery(name="DTOFloskeln.all.migration", query="SELECT e FROM DTOFloskeln e WHERE e.Kuerzel IS NOT NULL")
@JsonPropertyOrder({"Kuerzel","FloskelText","FloskelGruppe","FloskelFach","FloskelNiveau","FloskelJahrgang"})
public class DTOFloskeln {

	/** Kürzel für die Floskel wird beim Import automatisch vergeben */
	@Id
	@Column(name = "Kuerzel")
	@JsonProperty
	public String Kuerzel;

	/** Text der Floskel */
	@Column(name = "FloskelText")
	@JsonProperty
	public String FloskelText;

	/** Gruppe der Floskel */
	@Column(name = "FloskelGruppe")
	@JsonProperty
	public String FloskelGruppe;

	/** Fach bei Fachfloskeln */
	@Column(name = "FloskelFach")
	@JsonProperty
	public String FloskelFach;

	/** Niveau bei Fachfloskeln */
	@Column(name = "FloskelNiveau")
	@JsonProperty
	public String FloskelNiveau;

	/** Jahrgang bei Fachfloskeln */
	@Column(name = "FloskelJahrgang")
	@JsonProperty
	public String FloskelJahrgang;

	/**
	 * Erstellt ein neues Objekt der Klasse DTOFloskeln ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DTOFloskeln() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DTOFloskeln ohne eine Initialisierung der Attribute.
	 * @param Kuerzel   der Wert für das Attribut Kuerzel
	 * @param FloskelText   der Wert für das Attribut FloskelText
	 */
	public DTOFloskeln(final String Kuerzel, final String FloskelText) {
		if (Kuerzel == null) { 
			throw new NullPointerException("Kuerzel must not be null");
		}
		this.Kuerzel = Kuerzel;
		if (FloskelText == null) { 
			throw new NullPointerException("FloskelText must not be null");
		}
		this.FloskelText = FloskelText;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOFloskeln other = (DTOFloskeln) obj;
		if (Kuerzel == null) {
			if (other.Kuerzel != null)
				return false;
		} else if (!Kuerzel.equals(other.Kuerzel))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Kuerzel == null) ? 0 : Kuerzel.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DTOFloskeln(Kuerzel=" + this.Kuerzel + ", FloskelText=" + this.FloskelText + ", FloskelGruppe=" + this.FloskelGruppe + ", FloskelFach=" + this.FloskelFach + ", FloskelNiveau=" + this.FloskelNiveau + ", FloskelJahrgang=" + this.FloskelJahrgang + ")";
	}

}