package de.nrw.schule.svws.db.dto.dev.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;
import de.nrw.schule.svws.db.converter.current.SprachreferenzniveauConverter;

import de.nrw.schule.svws.core.types.fach.Sprachreferenzniveau;


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
import de.nrw.schule.svws.csv.converter.current.Boolean01ConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.Boolean01ConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.SprachreferenzniveauConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.SprachreferenzniveauConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerSprachenfolge.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerSprachenfolge")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.all", query="SELECT e FROM DevDTOSchuelerSprachenfolge e")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.id", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.id.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.schueler_id", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.Schueler_ID = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.schueler_id.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.sprache", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.Sprache = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.sprache.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.Sprache IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.reihenfolgenr", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ReihenfolgeNr = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.reihenfolgenr.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ReihenfolgeNr IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.asdjahrgangvon", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ASDJahrgangVon = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.asdjahrgangvon.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ASDJahrgangVon IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.asdjahrgangbis", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ASDJahrgangBis = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.asdjahrgangbis.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ASDJahrgangBis IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.abschnittvon", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.AbschnittVon = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.abschnittvon.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.AbschnittVon IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.abschnittbis", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.AbschnittBis = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.abschnittbis.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.AbschnittBis IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.referenzniveau", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.Referenzniveau = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.referenzniveau.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.Referenzniveau IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.kleineslatinumerreicht", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.KleinesLatinumErreicht = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.kleineslatinumerreicht.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.KleinesLatinumErreicht IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.latinumerreicht", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.LatinumErreicht = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.latinumerreicht.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.LatinumErreicht IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.graecumerreicht", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.GraecumErreicht = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.graecumerreicht.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.GraecumErreicht IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.hebraicumerreicht", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.HebraicumErreicht = :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.hebraicumerreicht.multiple", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.HebraicumErreicht IN :value")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchuelerSprachenfolge.all.migration", query="SELECT e FROM DevDTOSchuelerSprachenfolge e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Schueler_ID","Sprache","ReihenfolgeNr","ASDJahrgangVon","ASDJahrgangBis","AbschnittVon","AbschnittBis","Referenzniveau","KleinesLatinumErreicht","LatinumErreicht","GraecumErreicht","HebraicumErreicht"})
public class DevDTOSchuelerSprachenfolge {

	/** ID des Sprachenfolgeeintrags */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** SchülerID des Sprachenfolgeeintrags */
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** Atomares Sprachkürzel aus StatKue_SVWS_ZulaessigeFaecher */
	@Column(name = "Sprache")
	@JsonProperty
	public String Sprache;

	/** Reihenfolge Nummer des Sprachenfolgeeintrags */
	@Column(name = "ReihenfolgeNr")
	@JsonProperty
	public Integer ReihenfolgeNr;

	/** ASDJahrgang Beginn des Sprachenfolgeeintrags */
	@Column(name = "ASDJahrgangVon")
	@JsonProperty
	public String ASDJahrgangVon;

	/** ASDJahrgang Ende des Sprachenfolgeeintrags */
	@Column(name = "ASDJahrgangBis")
	@JsonProperty
	public String ASDJahrgangBis;

	/** Abschnitt Beginn des Sprachenfolgeeintrags */
	@Column(name = "AbschnittVon")
	@JsonProperty
	public Integer AbschnittVon;

	/** Abschnitt Ende des Sprachenfolgeeintrags */
	@Column(name = "AbschnittBis")
	@JsonProperty
	public Integer AbschnittBis;

	/** Referenzniveau GeR des Sprachenfolgeeintrags */
	@Column(name = "Referenzniveau")
	@JsonProperty
	@Convert(converter=SprachreferenzniveauConverter.class)
	@JsonSerialize(using=SprachreferenzniveauConverterSerializer.class)
	@JsonDeserialize(using=SprachreferenzniveauConverterDeserializer.class)
	public Sprachreferenzniveau Referenzniveau;

	/** Gibt an, ob der Schüler das kleine Latinum erreicht hat */
	@Column(name = "KleinesLatinumErreicht")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean KleinesLatinumErreicht;

	/** Gibt an, ob der Schüler das Latinum erreicht hat */
	@Column(name = "LatinumErreicht")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean LatinumErreicht;

	/** Gibt an, ob der Schüler das Graecum erreicht hat */
	@Column(name = "GraecumErreicht")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean GraecumErreicht;

	/** Gibt an, ob der Schüler das Hebraicum erreicht hat */
	@Column(name = "HebraicumErreicht")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean HebraicumErreicht;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerSprachenfolge ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerSprachenfolge() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerSprachenfolge ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 * @param Sprache   der Wert für das Attribut Sprache
	 */
	public DevDTOSchuelerSprachenfolge(final Long ID, final Long Schueler_ID, final String Sprache) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Schueler_ID == null) { 
			throw new NullPointerException("Schueler_ID must not be null");
		}
		this.Schueler_ID = Schueler_ID;
		if (Sprache == null) { 
			throw new NullPointerException("Sprache must not be null");
		}
		this.Sprache = Sprache;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerSprachenfolge other = (DevDTOSchuelerSprachenfolge) obj;
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
		return "DevDTOSchuelerSprachenfolge(ID=" + this.ID + ", Schueler_ID=" + this.Schueler_ID + ", Sprache=" + this.Sprache + ", ReihenfolgeNr=" + this.ReihenfolgeNr + ", ASDJahrgangVon=" + this.ASDJahrgangVon + ", ASDJahrgangBis=" + this.ASDJahrgangBis + ", AbschnittVon=" + this.AbschnittVon + ", AbschnittBis=" + this.AbschnittBis + ", Referenzniveau=" + this.Referenzniveau + ", KleinesLatinumErreicht=" + this.KleinesLatinumErreicht + ", LatinumErreicht=" + this.LatinumErreicht + ", GraecumErreicht=" + this.GraecumErreicht + ", HebraicumErreicht=" + this.HebraicumErreicht + ")";
	}

}