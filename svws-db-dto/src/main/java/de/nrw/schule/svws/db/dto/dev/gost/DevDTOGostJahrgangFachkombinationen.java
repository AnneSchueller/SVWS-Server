package de.nrw.schule.svws.db.dto.dev.gost;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;
import de.nrw.schule.svws.db.converter.current.gost.GostLaufbahnplanungFachkombinationTypConverter;

import de.nrw.schule.svws.core.types.gost.GostLaufbahnplanungFachkombinationTyp;


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
import de.nrw.schule.svws.csv.converter.current.gost.GostLaufbahnplanungFachkombinationTypConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.gost.GostLaufbahnplanungFachkombinationTypConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle Gost_Jahrgang_Fachkombinationen.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "Gost_Jahrgang_Fachkombinationen")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.all", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.id", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.ID = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.id.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.abi_jahrgang", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Abi_Jahrgang = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.abi_jahrgang.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Abi_Jahrgang IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.fach1_id", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Fach1_ID = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.fach1_id.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Fach1_ID IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.fach2_id", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Fach2_ID = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.fach2_id.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Fach2_ID IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.kursart1", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Kursart1 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.kursart1.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Kursart1 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.kursart2", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Kursart2 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.kursart2.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Kursart2 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.ef1", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.EF1 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.ef1.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.EF1 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.ef2", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.EF2 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.ef2.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.EF2 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q11", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q11 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q11.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q11 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q12", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q12 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q12.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q12 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q21", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q21 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q21.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q21 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q22", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q22 = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.q22.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Q22 IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.typ", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Typ = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.typ.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Typ IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.hinweistext", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Hinweistext = :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.hinweistext.multiple", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.Hinweistext IN :value")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.primaryKeyQuery", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOGostJahrgangFachkombinationen.all.migration", query="SELECT e FROM DevDTOGostJahrgangFachkombinationen e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Abi_Jahrgang","Fach1_ID","Fach2_ID","Kursart1","Kursart2","EF1","EF2","Q11","Q12","Q21","Q22","Typ","Hinweistext"})
public class DevDTOGostJahrgangFachkombinationen {

	/** ID des Datensatzes */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Schuljahr, in welchem der Jahrgang das Abitur macht */
	@Column(name = "Abi_Jahrgang")
	@JsonProperty
	public Integer Abi_Jahrgang;

	/** Die ID des ersten Faches der nicht möglichen Fächerkombination / eines Fächerprofils */
	@Column(name = "Fach1_ID")
	@JsonProperty
	public Long Fach1_ID;

	/** Die ID des zweiten Faches der nicht möglichen Fächerkombination / eines Fächerprofils */
	@Column(name = "Fach2_ID")
	@JsonProperty
	public Long Fach2_ID;

	/** Die Kursart des ersten Faches der nicht möglichen Fächerkombination / eines Fächerprofils */
	@Column(name = "Kursart1")
	@JsonProperty
	public String Kursart1;

	/** Die Kursart des zweiten Faches der nicht möglichen Fächerkombination / eines Fächerprofils */
	@Column(name = "Kursart2")
	@JsonProperty
	public String Kursart2;

	/** Gibt an, ob die Regel in EF.1 angewendet werden soll. */
	@Column(name = "EF1")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean EF1;

	/** Gibt an, ob die Regel in EF.2 angewendet werden soll. */
	@Column(name = "EF2")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean EF2;

	/** Gibt an, ob die Regel in Q1.1 angewendet werden soll. */
	@Column(name = "Q11")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Q11;

	/** Gibt an, ob die Regel in Q1.2 angewendet werden soll. */
	@Column(name = "Q12")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Q12;

	/** Gibt an, ob die Regel in Q2.1 angewendet werden soll. */
	@Column(name = "Q21")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Q21;

	/** Gibt an, ob die Regel in Q2.2 angewendet werden soll. */
	@Column(name = "Q22")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean Q22;

	/** Gibt an, ob es sich um eine nicht mögliche Fächerkombination (0) oder ein Fächerprofil handelt (1) */
	@Column(name = "Typ")
	@JsonProperty
	@Convert(converter=GostLaufbahnplanungFachkombinationTypConverter.class)
	@JsonSerialize(using=GostLaufbahnplanungFachkombinationTypConverterSerializer.class)
	@JsonDeserialize(using=GostLaufbahnplanungFachkombinationTypConverterDeserializer.class)
	public GostLaufbahnplanungFachkombinationTyp Typ;

	/** Hinweistext, der ausgegeben wird, wenn das Fachprofil / die nicht mögliche Fächerkombination nicht erfüllt wird. */
	@Column(name = "Hinweistext")
	@JsonProperty
	public String Hinweistext;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOGostJahrgangFachkombinationen ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOGostJahrgangFachkombinationen() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOGostJahrgangFachkombinationen ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Abi_Jahrgang   der Wert für das Attribut Abi_Jahrgang
	 * @param Fach1_ID   der Wert für das Attribut Fach1_ID
	 * @param Fach2_ID   der Wert für das Attribut Fach2_ID
	 * @param EF1   der Wert für das Attribut EF1
	 * @param EF2   der Wert für das Attribut EF2
	 * @param Q11   der Wert für das Attribut Q11
	 * @param Q12   der Wert für das Attribut Q12
	 * @param Q21   der Wert für das Attribut Q21
	 * @param Q22   der Wert für das Attribut Q22
	 * @param Typ   der Wert für das Attribut Typ
	 * @param Hinweistext   der Wert für das Attribut Hinweistext
	 */
	public DevDTOGostJahrgangFachkombinationen(final Long ID, final Integer Abi_Jahrgang, final Long Fach1_ID, final Long Fach2_ID, final Boolean EF1, final Boolean EF2, final Boolean Q11, final Boolean Q12, final Boolean Q21, final Boolean Q22, final GostLaufbahnplanungFachkombinationTyp Typ, final String Hinweistext) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Abi_Jahrgang == null) { 
			throw new NullPointerException("Abi_Jahrgang must not be null");
		}
		this.Abi_Jahrgang = Abi_Jahrgang;
		if (Fach1_ID == null) { 
			throw new NullPointerException("Fach1_ID must not be null");
		}
		this.Fach1_ID = Fach1_ID;
		if (Fach2_ID == null) { 
			throw new NullPointerException("Fach2_ID must not be null");
		}
		this.Fach2_ID = Fach2_ID;
		if (EF1 == null) { 
			throw new NullPointerException("EF1 must not be null");
		}
		this.EF1 = EF1;
		if (EF2 == null) { 
			throw new NullPointerException("EF2 must not be null");
		}
		this.EF2 = EF2;
		if (Q11 == null) { 
			throw new NullPointerException("Q11 must not be null");
		}
		this.Q11 = Q11;
		if (Q12 == null) { 
			throw new NullPointerException("Q12 must not be null");
		}
		this.Q12 = Q12;
		if (Q21 == null) { 
			throw new NullPointerException("Q21 must not be null");
		}
		this.Q21 = Q21;
		if (Q22 == null) { 
			throw new NullPointerException("Q22 must not be null");
		}
		this.Q22 = Q22;
		if (Typ == null) { 
			throw new NullPointerException("Typ must not be null");
		}
		this.Typ = Typ;
		if (Hinweistext == null) { 
			throw new NullPointerException("Hinweistext must not be null");
		}
		this.Hinweistext = Hinweistext;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOGostJahrgangFachkombinationen other = (DevDTOGostJahrgangFachkombinationen) obj;
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
		return "DevDTOGostJahrgangFachkombinationen(ID=" + this.ID + ", Abi_Jahrgang=" + this.Abi_Jahrgang + ", Fach1_ID=" + this.Fach1_ID + ", Fach2_ID=" + this.Fach2_ID + ", Kursart1=" + this.Kursart1 + ", Kursart2=" + this.Kursart2 + ", EF1=" + this.EF1 + ", EF2=" + this.EF2 + ", Q11=" + this.Q11 + ", Q12=" + this.Q12 + ", Q21=" + this.Q21 + ", Q22=" + this.Q22 + ", Typ=" + this.Typ + ", Hinweistext=" + this.Hinweistext + ")";
	}

}