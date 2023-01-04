package de.nrw.schule.svws.db.dto.rev9.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
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
import de.nrw.schule.svws.csv.converter.current.DatumConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.DatumConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerVermerke.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerVermerke")
@NamedQuery(name="Rev9DTOSchuelerVermerke.all", query="SELECT e FROM Rev9DTOSchuelerVermerke e")
@NamedQuery(name="Rev9DTOSchuelerVermerke.id", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.ID = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.id.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.ID IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.schueler_id", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.Schueler_ID = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.schueler_id.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.vermerkart_id", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.VermerkArt_ID = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.vermerkart_id.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.VermerkArt_ID IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.datum", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.Datum = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.datum.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.Datum IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.bemerkung", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.Bemerkung = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.bemerkung.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.Bemerkung IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.angelegtvon", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.AngelegtVon = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.angelegtvon.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.AngelegtVon IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.geaendertvon", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.GeaendertVon = :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.geaendertvon.multiple", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.GeaendertVon IN :value")
@NamedQuery(name="Rev9DTOSchuelerVermerke.primaryKeyQuery", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.ID = ?1")
@NamedQuery(name="Rev9DTOSchuelerVermerke.all.migration", query="SELECT e FROM Rev9DTOSchuelerVermerke e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Schueler_ID","VermerkArt_ID","Datum","Bemerkung","AngelegtVon","GeaendertVon"})
public class Rev9DTOSchuelerVermerke {

	/** ID des Vermerkeintrages beim Schüler */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** SchülerID des Vermerkeintrages beim Schüler */
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** Art des Vermerkeintrages beim Schüler */
	@Column(name = "VermerkArt_ID")
	@JsonProperty
	public Long VermerkArt_ID;

	/** Datum des Vermerkeintrages beim Schüler */
	@Column(name = "Datum")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String Datum;

	/** Bemerkung des Vermerkeintrages beim Schüler */
	@Column(name = "Bemerkung")
	@JsonProperty
	public String Bemerkung;

	/** Angelegt von User des Vermerkeintrages beim Schüler */
	@Column(name = "AngelegtVon")
	@JsonProperty
	public String AngelegtVon;

	/** Geändert von User des Vermerkeintrages beim Schüler */
	@Column(name = "GeaendertVon")
	@JsonProperty
	public String GeaendertVon;

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOSchuelerVermerke ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private Rev9DTOSchuelerVermerke() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse Rev9DTOSchuelerVermerke ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 */
	public Rev9DTOSchuelerVermerke(final Long ID, final Long Schueler_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Schueler_ID == null) { 
			throw new NullPointerException("Schueler_ID must not be null");
		}
		this.Schueler_ID = Schueler_ID;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rev9DTOSchuelerVermerke other = (Rev9DTOSchuelerVermerke) obj;
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
		return "Rev9DTOSchuelerVermerke(ID=" + this.ID + ", Schueler_ID=" + this.Schueler_ID + ", VermerkArt_ID=" + this.VermerkArt_ID + ", Datum=" + this.Datum + ", Bemerkung=" + this.Bemerkung + ", AngelegtVon=" + this.AngelegtVon + ", GeaendertVon=" + this.GeaendertVon + ")";
	}

}