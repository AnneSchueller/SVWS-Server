package de.nrw.schule.svws.db.dto.dev.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;
import de.nrw.schule.svws.db.converter.current.NoteConverterFromInteger;
import de.nrw.schule.svws.db.converter.current.SprachpruefungniveauConverter;
import de.nrw.schule.svws.db.converter.current.SprachreferenzniveauConverter;

import de.nrw.schule.svws.core.types.Note;
import de.nrw.schule.svws.core.types.fach.Sprachpruefungniveau;
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
import de.nrw.schule.svws.csv.converter.current.NoteConverterFromIntegerSerializer;
import de.nrw.schule.svws.csv.converter.current.NoteConverterFromIntegerDeserializer;
import de.nrw.schule.svws.csv.converter.current.SprachpruefungniveauConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.SprachpruefungniveauConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.SprachreferenzniveauConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.SprachreferenzniveauConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerSprachpruefungen.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerSprachpruefungen")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.all", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.id", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.id.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.schueler_id", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Schueler_ID = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.schueler_id.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.sprache", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Sprache = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.sprache.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Sprache IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.asdjahrgang", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ASDJahrgang = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.asdjahrgang.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ASDJahrgang IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.anspruchsniveau", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Anspruchsniveau = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.anspruchsniveau.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Anspruchsniveau IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.pruefungsdatum", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Pruefungsdatum = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.pruefungsdatum.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Pruefungsdatum IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.ersetztesprache", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ErsetzteSprache = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.ersetztesprache.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ErsetzteSprache IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.isthsupruefung", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.IstHSUPruefung = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.isthsupruefung.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.IstHSUPruefung IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.istfeststellungspruefung", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.IstFeststellungspruefung = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.istfeststellungspruefung.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.IstFeststellungspruefung IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannerstepflichtfremdspracheersetzen", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannErstePflichtfremdspracheErsetzen = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannerstepflichtfremdspracheersetzen.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannErstePflichtfremdspracheErsetzen IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannzweitepflichtfremdspracheersetzen", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannZweitePflichtfremdspracheErsetzen = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannzweitepflichtfremdspracheersetzen.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannZweitePflichtfremdspracheErsetzen IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannwahlpflichtfremdspracheersetzen", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannWahlpflichtfremdspracheErsetzen = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannwahlpflichtfremdspracheersetzen.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannWahlpflichtfremdspracheErsetzen IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannbelegungalsfortgefuehrtespracheerlauben", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannBelegungAlsFortgefuehrteSpracheErlauben = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.kannbelegungalsfortgefuehrtespracheerlauben.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.KannBelegungAlsFortgefuehrteSpracheErlauben IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.referenzniveau", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Referenzniveau = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.referenzniveau.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.Referenzniveau IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.notepruefung", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.NotePruefung = :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.notepruefung.multiple", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.NotePruefung IN :value")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchuelerSprachpruefungen.all.migration", query="SELECT e FROM DevDTOSchuelerSprachpruefungen e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Schueler_ID","Sprache","ASDJahrgang","Anspruchsniveau","Pruefungsdatum","ErsetzteSprache","IstHSUPruefung","IstFeststellungspruefung","KannErstePflichtfremdspracheErsetzen","KannZweitePflichtfremdspracheErsetzen","KannWahlpflichtfremdspracheErsetzen","KannBelegungAlsFortgefuehrteSpracheErlauben","Referenzniveau","NotePruefung"})
public class DevDTOSchuelerSprachpruefungen {

	/** ID des Sprachprüfungseintrags */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** ID des Schülers des Sprachprüfungseintrags */
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** Atomares Sprachkürzel aus StatKue_SVWS_ZulaessigeFaecher */
	@Column(name = "Sprache")
	@JsonProperty
	public String Sprache;

	/** ASDJahrgangsbezeichnung, in der die Sprachprüfung abgelegt wurde */
	@Column(name = "ASDJahrgang")
	@JsonProperty
	public String ASDJahrgang;

	/** Das Anspruchsniveau der Sprachprüfung (angelehnt an einen entsprechenden Schulabschluss) */
	@Column(name = "Anspruchsniveau_ID")
	@JsonProperty
	@Convert(converter=SprachpruefungniveauConverter.class)
	@JsonSerialize(using=SprachpruefungniveauConverterSerializer.class)
	@JsonDeserialize(using=SprachpruefungniveauConverterDeserializer.class)
	public Sprachpruefungniveau Anspruchsniveau;

	/** Datum der Sprachprüfung */
	@Column(name = "Pruefungsdatum")
	@JsonProperty
	public String Pruefungsdatum;

	/** Atomares Sprachkürzel aus StatKue_SVWS_ZulaessigeFaecher für die ersetzte Sprache */
	@Column(name = "ErsetzteSprache")
	@JsonProperty
	public String ErsetzteSprache;

	/** Gibt an, dass die Prüfung eine Prüfung in der Herkunftssprache ist (BASS 13-61 Nr. 2). Entspricht dem Eintrag P in Schild 2 */
	@Column(name = "IstHSUPruefung")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean IstHSUPruefung;

	/** Gibt an, ob die Prüfung eine Sprachfeststellungsprüfung ist (BASS 13-61 Nr. 1). Entspricht N in Schild 2 */
	@Column(name = "IstFeststellungspruefung")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean IstFeststellungspruefung;

	/** Gibt an, ob die Sprachprüfung an die Stelle der ersten Pflichtfremdsprache treten kann */
	@Column(name = "KannErstePflichtfremdspracheErsetzen")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean KannErstePflichtfremdspracheErsetzen;

	/** Gibt an, ob die Sprachprüfung an die Stelle der zweiten Pflichtfremdsprache treten kann */
	@Column(name = "KannZweitePflichtfremdspracheErsetzen")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean KannZweitePflichtfremdspracheErsetzen;

	/** Gibt an, ob die Sprachprüfung an die Stelle einer Wahlpflichtfremdsprache der Klassen 05-07 treten kann */
	@Column(name = "KannWahlpflichtfremdspracheErsetzen")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean KannWahlpflichtfremdspracheErsetzen;

	/** Gibt an, ob die Sprachprüfung nachweist, dass die Sprache als fortgeführte Fremdsprache in der Oberstufe belegt werden kann (BASS 13-61 Nr. 1 Abs. 11) */
	@Column(name = "KannBelegungAlsFortgefuehrteSpracheErlauben")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean KannBelegungAlsFortgefuehrteSpracheErlauben;

	/** Das Sprachreferenzniveau der Sprachprüfung gemäß GeR */
	@Column(name = "Referenzniveau")
	@JsonProperty
	@Convert(converter=SprachreferenzniveauConverter.class)
	@JsonSerialize(using=SprachreferenzniveauConverterSerializer.class)
	@JsonDeserialize(using=SprachreferenzniveauConverterDeserializer.class)
	public Sprachreferenzniveau Referenzniveau;

	/** Note der Sprachprüfung, die herangezogen werden kann, falls die Note der Sprachprüfung an die Stelle einer Fremdsprachennote tritt */
	@Column(name = "NotePruefung")
	@JsonProperty
	@Convert(converter=NoteConverterFromInteger.class)
	@JsonSerialize(using=NoteConverterFromIntegerSerializer.class)
	@JsonDeserialize(using=NoteConverterFromIntegerDeserializer.class)
	public Note NotePruefung;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerSprachpruefungen ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerSprachpruefungen() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerSprachpruefungen ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 * @param Sprache   der Wert für das Attribut Sprache
	 */
	public DevDTOSchuelerSprachpruefungen(final Long ID, final Long Schueler_ID, final String Sprache) {
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
		DevDTOSchuelerSprachpruefungen other = (DevDTOSchuelerSprachpruefungen) obj;
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
		return "DevDTOSchuelerSprachpruefungen(ID=" + this.ID + ", Schueler_ID=" + this.Schueler_ID + ", Sprache=" + this.Sprache + ", ASDJahrgang=" + this.ASDJahrgang + ", Anspruchsniveau=" + this.Anspruchsniveau + ", Pruefungsdatum=" + this.Pruefungsdatum + ", ErsetzteSprache=" + this.ErsetzteSprache + ", IstHSUPruefung=" + this.IstHSUPruefung + ", IstFeststellungspruefung=" + this.IstFeststellungspruefung + ", KannErstePflichtfremdspracheErsetzen=" + this.KannErstePflichtfremdspracheErsetzen + ", KannZweitePflichtfremdspracheErsetzen=" + this.KannZweitePflichtfremdspracheErsetzen + ", KannWahlpflichtfremdspracheErsetzen=" + this.KannWahlpflichtfremdspracheErsetzen + ", KannBelegungAlsFortgefuehrteSpracheErlauben=" + this.KannBelegungAlsFortgefuehrteSpracheErlauben + ", Referenzniveau=" + this.Referenzniveau + ", NotePruefung=" + this.NotePruefung + ")";
	}

}