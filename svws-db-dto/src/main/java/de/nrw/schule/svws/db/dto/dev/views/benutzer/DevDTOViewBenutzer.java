package de.nrw.schule.svws.db.dto.dev.views.benutzer;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.Boolean01Converter;


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

/**
 * Diese Klasse dient als DTO für die Datenbank-View V_Benutzer.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "V_Benutzer")
@NamedQuery(name="DevDTOViewBenutzer.all", query="SELECT e FROM DevDTOViewBenutzer e")
@NamedQuery(name="DevDTOViewBenutzer.id", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.ID = :value")
@NamedQuery(name="DevDTOViewBenutzer.id.multiple", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOViewBenutzer.anzeigename", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.AnzeigeName = :value")
@NamedQuery(name="DevDTOViewBenutzer.anzeigename.multiple", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.AnzeigeName IN :value")
@NamedQuery(name="DevDTOViewBenutzer.benutzername", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.Benutzername = :value")
@NamedQuery(name="DevDTOViewBenutzer.benutzername.multiple", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.Benutzername IN :value")
@NamedQuery(name="DevDTOViewBenutzer.passwordhash", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.PasswordHash = :value")
@NamedQuery(name="DevDTOViewBenutzer.passwordhash.multiple", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.PasswordHash IN :value")
@NamedQuery(name="DevDTOViewBenutzer.istadmin", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.IstAdmin = :value")
@NamedQuery(name="DevDTOViewBenutzer.istadmin.multiple", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.IstAdmin IN :value")
@NamedQuery(name="DevDTOViewBenutzer.primaryKeyQuery", query="SELECT e FROM DevDTOViewBenutzer e WHERE e.ID = ?1")
@JsonPropertyOrder({"ID","AnzeigeName","Benutzername","PasswordHash","IstAdmin"})
public class DevDTOViewBenutzer {

	/** Die eindeutige ID des Benutzers */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Der Anzeige-Name des Benutzers (z.B. Max Mustermann) */
	@Column(name = "AnzeigeName")
	@JsonProperty
	public String AnzeigeName;

	/** Der Anmeldename des Benutzers (z.B. max.mustermann) */
	@Column(name = "Benutzername")
	@JsonProperty
	public String Benutzername;

	/** Der bcrypt-Password-Hash zur Überprüfung des Benutzer-Kennwortes */
	@Column(name = "PasswordHash")
	@JsonProperty
	public String PasswordHash;

	/** Gibt an, ob es sich um einen administrativen Benutzer handelt oder nicht */
	@Column(name = "IstAdmin")
	@JsonProperty
	@Convert(converter=Boolean01Converter.class)
	@JsonSerialize(using=Boolean01ConverterSerializer.class)
	@JsonDeserialize(using=Boolean01ConverterDeserializer.class)
	public Boolean IstAdmin;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOViewBenutzer ohne eine Initialisierung der Attribute.
	 */
	private DevDTOViewBenutzer() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOViewBenutzer other = (DevDTOViewBenutzer) obj;
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
		return "DevDTOViewBenutzer(ID=" + this.ID + ", AnzeigeName=" + this.AnzeigeName + ", Benutzername=" + this.Benutzername + ", PasswordHash=" + this.PasswordHash + ", IstAdmin=" + this.IstAdmin + ")";
	}

}