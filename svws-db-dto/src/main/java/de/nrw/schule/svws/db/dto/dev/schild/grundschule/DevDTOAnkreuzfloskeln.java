package de.nrw.schule.svws.db.dto.dev.schild.grundschule;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultPlusConverter;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultPlusConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultPlusConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle K_Ankreuzfloskeln.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "K_Ankreuzfloskeln")
@NamedQuery(name="DevDTOAnkreuzfloskeln.all", query="SELECT e FROM DevDTOAnkreuzfloskeln e")
@NamedQuery(name="DevDTOAnkreuzfloskeln.id", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.ID = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.id.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.fach_id", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Fach_ID = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.fach_id.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Fach_ID IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.istasv", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.IstASV = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.istasv.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.IstASV IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.jahrgang", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Jahrgang = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.jahrgang.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Jahrgang IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.gliederung", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Gliederung = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.gliederung.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Gliederung IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.floskeltext", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.FloskelText = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.floskeltext.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.FloskelText IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.sortierung", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Sortierung = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.sortierung.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Sortierung IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.fachsortierung", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.FachSortierung = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.fachsortierung.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.FachSortierung IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.abschnitt", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Abschnitt = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.abschnitt.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Abschnitt IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.sichtbar", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Sichtbar = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.sichtbar.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Sichtbar IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.aktiv", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Aktiv = :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.aktiv.multiple", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.Aktiv IN :value")
@NamedQuery(name="DevDTOAnkreuzfloskeln.primaryKeyQuery", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOAnkreuzfloskeln.all.migration", query="SELECT e FROM DevDTOAnkreuzfloskeln e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Fach_ID","IstASV","Jahrgang","Gliederung","FloskelText","Sortierung","FachSortierung","Abschnitt","Sichtbar","Aktiv"})
public class DevDTOAnkreuzfloskeln {

	/** ID der Ankreuzfloskel */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** FachID zu der die Ankreuzfloskel gehört, null für individuelle Ankreuzfloskeln bzw. siehe Spalte IstASV */
	@Column(name = "Fach_ID")
	@JsonProperty
	public Long Fach_ID;

	/** Gibt an, falls die Fach_ID null ist, ob es sich bei der Ankreuzfloskel um eine Floskel zum Arbeits- und Sozialverhalten handelt (1) oder nicht (0). */
	@Column(name = "IstASV")
	@JsonProperty
	public Integer IstASV;

	/** Jahrgang zu der die Ankreuzfloskel gehört */
	@Column(name = "Jahrgang")
	@JsonProperty
	public String Jahrgang;

	/** Schulgliederung zu der die Ankreuzkompetenz gehört (nur wichtig bei BK) */
	@Column(name = "Gliederung")
	@JsonProperty
	public String Gliederung;

	/** Text der Ankreuzfloskel */
	@Column(name = "FloskelText")
	@JsonProperty
	public String FloskelText;

	/** Sortierung der Ankreuzfloskel */
	@Column(name = "Sortierung")
	@JsonProperty
	public Integer Sortierung;

	/** Sortierung des Faches der Ankreuzfloskel */
	@Column(name = "FachSortierung")
	@JsonProperty
	public Integer FachSortierung;

	/** Wird in welchen Abschnitten verwendet 1Hj 2HJ beide */
	@Column(name = "Abschnitt")
	@JsonProperty
	public Integer Abschnitt;

	/** Sichtbarkeit der Ankreuzfloskel */
	@Column(name = "Sichtbar")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultPlusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultPlusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultPlusConverterDeserializer.class)
	public Boolean Sichtbar;

	/** Gibt an ob die Ankreuzfloskel aktiv ist */
	@Column(name = "Aktiv")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultPlusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultPlusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultPlusConverterDeserializer.class)
	public Boolean Aktiv;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOAnkreuzfloskeln ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOAnkreuzfloskeln() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOAnkreuzfloskeln ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param IstASV   der Wert für das Attribut IstASV
	 * @param Jahrgang   der Wert für das Attribut Jahrgang
	 * @param FloskelText   der Wert für das Attribut FloskelText
	 */
	public DevDTOAnkreuzfloskeln(final Long ID, final Integer IstASV, final String Jahrgang, final String FloskelText) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (IstASV == null) { 
			throw new NullPointerException("IstASV must not be null");
		}
		this.IstASV = IstASV;
		if (Jahrgang == null) { 
			throw new NullPointerException("Jahrgang must not be null");
		}
		this.Jahrgang = Jahrgang;
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
		DevDTOAnkreuzfloskeln other = (DevDTOAnkreuzfloskeln) obj;
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
		return "DevDTOAnkreuzfloskeln(ID=" + this.ID + ", Fach_ID=" + this.Fach_ID + ", IstASV=" + this.IstASV + ", Jahrgang=" + this.Jahrgang + ", Gliederung=" + this.Gliederung + ", FloskelText=" + this.FloskelText + ", Sortierung=" + this.Sortierung + ", FachSortierung=" + this.FachSortierung + ", Abschnitt=" + this.Abschnitt + ", Sichtbar=" + this.Sichtbar + ", Aktiv=" + this.Aktiv + ")";
	}

}