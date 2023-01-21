package de.nrw.schule.svws.db.dto.dev.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultMinusConverter;
import de.nrw.schule.svws.db.converter.current.DatumConverter;


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
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultMinusConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultMinusConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.DatumConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.DatumConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle Schueler_AllgAdr.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Schueler_AllgAdr")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.all", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.id", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.id.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.schueler_id", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Schueler_ID = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.schueler_id.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.adresse_id", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Adresse_ID = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.adresse_id.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Adresse_ID IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.vertragsart_id", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Vertragsart_ID = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.vertragsart_id.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Vertragsart_ID IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.vertragsbeginn", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Vertragsbeginn = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.vertragsbeginn.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Vertragsbeginn IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.vertragsende", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Vertragsende = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.vertragsende.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Vertragsende IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.ausbilder", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Ausbilder = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.ausbilder.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Ausbilder IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.allgadranschreiben", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.AllgAdrAnschreiben = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.allgadranschreiben.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.AllgAdrAnschreiben IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.praktikum", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Praktikum = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.praktikum.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Praktikum IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.sortierung", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Sortierung = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.sortierung.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Sortierung IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.ansprechpartner_id", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Ansprechpartner_ID = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.ansprechpartner_id.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Ansprechpartner_ID IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.betreuungslehrer_id", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Betreuungslehrer_ID = :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.betreuungslehrer_id.multiple", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.Betreuungslehrer_ID IN :value")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchuelerAllgemeineAdresse.all.migration", query="SELECT e FROM DevDTOSchuelerAllgemeineAdresse e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Schueler_ID","Adresse_ID","Vertragsart_ID","Vertragsbeginn","Vertragsende","Ausbilder","AllgAdrAnschreiben","Praktikum","Sortierung","Ansprechpartner_ID","Betreuungslehrer_ID"})
public class DevDTOSchuelerAllgemeineAdresse {

	/** ID des Betriebeeintrags beim Schüler */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** SchülerID des Betriebeeintrags beim Schüler */
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** AdressID des Betriebeeintrags beim Schüler */
	@Column(name = "Adresse_ID")
	@JsonProperty
	public Long Adresse_ID;

	/** VertragsArtID des Betriebeeintrags beim Schüler */
	@Column(name = "Vertragsart_ID")
	@JsonProperty
	public Long Vertragsart_ID;

	/** Datum Vertragsbeginn des Betriebeeintrags beim Schüler */
	@Column(name = "Vertragsbeginn")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String Vertragsbeginn;

	/** Datum des Vertragsende des Betriebeeintrags beim Schüler */
	@Column(name = "Vertragsende")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String Vertragsende;

	/** Ausbildername des Betriebeeintrags beim Schüler */
	@Column(name = "Ausbilder")
	@JsonProperty
	public String Ausbilder;

	/** Betrieb erhält Anschreiben Ja/Nein */
	@Column(name = "AllgAdrAnschreiben")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean AllgAdrAnschreiben;

	/** Gibt an ob es ein Praktikum ist beim Betriebeeintrags beim Schüler */
	@Column(name = "Praktikum")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean Praktikum;

	/** Sortierung des Betriebeeintrags beim Schüler */
	@Column(name = "Sortierung")
	@JsonProperty
	public Integer Sortierung;

	/** AnsprechpartnerID des Betriebeeintrags beim Schüler */
	@Column(name = "Ansprechpartner_ID")
	@JsonProperty
	public Long Ansprechpartner_ID;

	/** BetreuungslehrerID des Betriebeeintrags beim Schüler */
	@Column(name = "Betreuungslehrer_ID")
	@JsonProperty
	public Long Betreuungslehrer_ID;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerAllgemeineAdresse ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerAllgemeineAdresse() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerAllgemeineAdresse ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 * @param Adresse_ID   der Wert für das Attribut Adresse_ID
	 */
	public DevDTOSchuelerAllgemeineAdresse(final Long ID, final Long Schueler_ID, final Long Adresse_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Schueler_ID == null) { 
			throw new NullPointerException("Schueler_ID must not be null");
		}
		this.Schueler_ID = Schueler_ID;
		if (Adresse_ID == null) { 
			throw new NullPointerException("Adresse_ID must not be null");
		}
		this.Adresse_ID = Adresse_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerAllgemeineAdresse other = (DevDTOSchuelerAllgemeineAdresse) obj;
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
		return "DevDTOSchuelerAllgemeineAdresse(ID=" + this.ID + ", Schueler_ID=" + this.Schueler_ID + ", Adresse_ID=" + this.Adresse_ID + ", Vertragsart_ID=" + this.Vertragsart_ID + ", Vertragsbeginn=" + this.Vertragsbeginn + ", Vertragsende=" + this.Vertragsende + ", Ausbilder=" + this.Ausbilder + ", AllgAdrAnschreiben=" + this.AllgAdrAnschreiben + ", Praktikum=" + this.Praktikum + ", Sortierung=" + this.Sortierung + ", Ansprechpartner_ID=" + this.Ansprechpartner_ID + ", Betreuungslehrer_ID=" + this.Betreuungslehrer_ID + ")";
	}

}