package de.nrw.schule.svws.db.dto.rev9.gost.klausurplanung;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;
import de.nrw.schule.svws.db.converter.current.gost.GOStHalbjahrConverter;

import de.nrw.schule.svws.core.types.gost.GostHalbjahr;


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
import de.nrw.schule.svws.csv.converter.current.gost.GOStHalbjahrConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.gost.GOStHalbjahrConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle Gost_Klausuren_Vorgaben.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Gost_Klausuren_Vorgaben")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.all", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.id", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.ID = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.id.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.ID IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.abi_jahrgang", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Abi_Jahrgang = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.abi_jahrgang.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Abi_Jahrgang IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.halbjahr", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Halbjahr = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.halbjahr.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Halbjahr IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.fach_id", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Fach_ID = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.fach_id.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Fach_ID IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.kursartallg", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.KursartAllg = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.kursartallg.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.KursartAllg IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.quartal", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Quartal = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.quartal.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Quartal IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.dauer", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Dauer = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.dauer.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Dauer IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.auswahlzeit", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Auswahlzeit = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.auswahlzeit.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Auswahlzeit IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.istmdlpruefung", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.IstMdlPruefung = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.istmdlpruefung.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.IstMdlPruefung IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.bemerkungen", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Bemerkungen = :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.bemerkungen.multiple", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.Bemerkungen IN :value")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.primaryKeyQuery", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.ID = ?1")
@NamedQuery(name="Rev9DTOGostKlausurenVorgaben.all.migration", query="SELECT e FROM Rev9DTOGostKlausurenVorgaben e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Abi_Jahrgang","Halbjahr","Fach_ID","KursartAllg","Quartal","Dauer","Auswahlzeit","IstMdlPruefung","Bemerkungen"})
public class Rev9DTOGostKlausurenVorgaben {

	/** ID der Klausurvorgaben (generiert) */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Der Abiturjahrgang, dem die Klausurvorgabe zugeordnet ist */
	@Column(name = "Abi_Jahrgang")
	@JsonProperty
	public Integer Abi_Jahrgang;

	/** Das Halbjahr, welchem die Klausurvorgabe zugeordnet ist (0=EF.1, 1=EF.2, 2=Q1.1, 3=Q1.2, 4=Q2.1, 5=Q2.2) */
	@Column(name = "Halbjahr")
	@JsonProperty
	@Convert(converter=GOStHalbjahrConverter.class)
	@JsonSerialize(using=GOStHalbjahrConverterSerializer.class)
	@JsonDeserialize(using=GOStHalbjahrConverterDeserializer.class)
	public GostHalbjahr Halbjahr;

	/** Fach_ID der Klausurvorgaben */
	@Column(name = "Fach_ID")
	@JsonProperty
	public Long Fach_ID;

	/** Allgemeine Kursart des Klausur-Kurses */
	@Column(name = "KursartAllg")
	@JsonProperty
	public String KursartAllg;

	/** Das Quartal, in dem die Klausur geschrieben wird. */
	@Column(name = "Quartal")
	@JsonProperty
	public Integer Quartal;

	/** Das Dauer der Klausur/Prüfung in Minuten */
	@Column(name = "Dauer")
	@JsonProperty
	public Integer Dauer;

	/** Das Dauer der Auswahlzeit in Minuten */
	@Column(name = "Auswahlzeit")
	@JsonProperty
	public Integer Auswahlzeit;

	/** Gibt an, ob es sich um eine mündliche Prüfunge handelt oder nicht: 1 - true, 0 - false. */
	@Column(name = "IstMdlPruefung")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean IstMdlPruefung;

	/** Text für Bemerkungen zur Klausurvorlage */
	@Column(name = "Bemerkungen")
	@JsonProperty
	public String Bemerkungen;

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOGostKlausurenVorgaben ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private Rev9DTOGostKlausurenVorgaben() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOGostKlausurenVorgaben ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Abi_Jahrgang   der Wert für das Attribut Abi_Jahrgang
	 * @param Halbjahr   der Wert für das Attribut Halbjahr
	 * @param Fach_ID   der Wert für das Attribut Fach_ID
	 * @param KursartAllg   der Wert für das Attribut KursartAllg
	 * @param Quartal   der Wert für das Attribut Quartal
	 * @param Dauer   der Wert für das Attribut Dauer
	 * @param Auswahlzeit   der Wert für das Attribut Auswahlzeit
	 * @param IstMdlPruefung   der Wert für das Attribut IstMdlPruefung
	 */
	public Rev9DTOGostKlausurenVorgaben(final Long ID, final Integer Abi_Jahrgang, final GostHalbjahr Halbjahr, final Long Fach_ID, final String KursartAllg, final Integer Quartal, final Integer Dauer, final Integer Auswahlzeit, final Boolean IstMdlPruefung) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Abi_Jahrgang == null) { 
			throw new NullPointerException("Abi_Jahrgang must not be null");
		}
		this.Abi_Jahrgang = Abi_Jahrgang;
		if (Halbjahr == null) { 
			throw new NullPointerException("Halbjahr must not be null");
		}
		this.Halbjahr = Halbjahr;
		if (Fach_ID == null) { 
			throw new NullPointerException("Fach_ID must not be null");
		}
		this.Fach_ID = Fach_ID;
		if (KursartAllg == null) { 
			throw new NullPointerException("KursartAllg must not be null");
		}
		this.KursartAllg = KursartAllg;
		if (Quartal == null) { 
			throw new NullPointerException("Quartal must not be null");
		}
		this.Quartal = Quartal;
		if (Dauer == null) { 
			throw new NullPointerException("Dauer must not be null");
		}
		this.Dauer = Dauer;
		if (Auswahlzeit == null) { 
			throw new NullPointerException("Auswahlzeit must not be null");
		}
		this.Auswahlzeit = Auswahlzeit;
		if (IstMdlPruefung == null) { 
			throw new NullPointerException("IstMdlPruefung must not be null");
		}
		this.IstMdlPruefung = IstMdlPruefung;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rev9DTOGostKlausurenVorgaben other = (Rev9DTOGostKlausurenVorgaben) obj;
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
		return "Rev9DTOGostKlausurenVorgaben(ID=" + this.ID + ", Abi_Jahrgang=" + this.Abi_Jahrgang + ", Halbjahr=" + this.Halbjahr + ", Fach_ID=" + this.Fach_ID + ", KursartAllg=" + this.KursartAllg + ", Quartal=" + this.Quartal + ", Dauer=" + this.Dauer + ", Auswahlzeit=" + this.Auswahlzeit + ", IstMdlPruefung=" + this.IstMdlPruefung + ", Bemerkungen=" + this.Bemerkungen + ")";
	}

}