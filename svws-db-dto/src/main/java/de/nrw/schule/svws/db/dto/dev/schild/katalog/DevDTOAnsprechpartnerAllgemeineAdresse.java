package de.nrw.schule.svws.db.dto.dev.schild.katalog;

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
 * Diese Klasse dient als DTO für die Datenbanktabelle AllgAdrAnsprechpartner.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "AllgAdrAnsprechpartner")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.all", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.id", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.ID = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.id.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.adresse_id", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Adresse_ID = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.adresse_id.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Adresse_ID IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.name", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Name = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.name.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Name IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.vorname", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Vorname = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.vorname.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Vorname IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.anrede", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Anrede = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.anrede.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Anrede IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.telefon", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Telefon = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.telefon.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Telefon IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.email", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Email = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.email.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Email IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.abteilung", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Abteilung = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.abteilung.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Abteilung IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.titel", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Titel = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.titel.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.Titel IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.gu_id", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.GU_ID = :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.gu_id.multiple", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.GU_ID IN :value")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.primaryKeyQuery", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOAnsprechpartnerAllgemeineAdresse.all.migration", query="SELECT e FROM DevDTOAnsprechpartnerAllgemeineAdresse e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Adresse_ID","Name","Vorname","Anrede","Telefon","Email","Abteilung","Titel","GU_ID"})
public class DevDTOAnsprechpartnerAllgemeineAdresse {

	/** ID des Ansprechpartners der Tabelle AllgAdresse (Betriebe) */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** ID des Betriebs (der Adresse) aus der Tabelle AllgAdresse */
	@Column(name = "Adresse_ID")
	@JsonProperty
	public Long Adresse_ID;

	/** Name des Ansprechpartners im Betrieb PAuswG vom 21.6.2019 §5 Abs. 2 */
	@Column(name = "Name")
	@JsonProperty
	public String Name;

	/** Vorname des Ansprechpartners im Betrieb PAuswG vom 21.6.2019 §5 Abs. 2 */
	@Column(name = "Vorname")
	@JsonProperty
	public String Vorname;

	/** Anrede des Ansprechpartners im Betrieb */
	@Column(name = "Anrede")
	@JsonProperty
	public String Anrede;

	/** Telefonnummer des Ansprechpartners im Betrieb */
	@Column(name = "Telefon")
	@JsonProperty
	public String Telefon;

	/** Email-Adresse des Ansprechpartners im Betrieb */
	@Column(name = "Email")
	@JsonProperty
	public String Email;

	/** ggf Abteilung des Ansprechpartners im Betrieb */
	@Column(name = "Abteilung")
	@JsonProperty
	public String Abteilung;

	/** Titel des Ansprechpartners im Betrieb */
	@Column(name = "Titel")
	@JsonProperty
	public String Titel;

	/** GU_ID des Ansprechpartners im Betrieb */
	@Column(name = "GU_ID")
	@JsonProperty
	public String GU_ID;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOAnsprechpartnerAllgemeineAdresse ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOAnsprechpartnerAllgemeineAdresse() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOAnsprechpartnerAllgemeineAdresse ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Adresse_ID   der Wert für das Attribut Adresse_ID
	 */
	public DevDTOAnsprechpartnerAllgemeineAdresse(final Long ID, final Long Adresse_ID) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
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
		DevDTOAnsprechpartnerAllgemeineAdresse other = (DevDTOAnsprechpartnerAllgemeineAdresse) obj;
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
		return "DevDTOAnsprechpartnerAllgemeineAdresse(ID=" + this.ID + ", Adresse_ID=" + this.Adresse_ID + ", Name=" + this.Name + ", Vorname=" + this.Vorname + ", Anrede=" + this.Anrede + ", Telefon=" + this.Telefon + ", Email=" + this.Email + ", Abteilung=" + this.Abteilung + ", Titel=" + this.Titel + ", GU_ID=" + this.GU_ID + ")";
	}

}