package de.nrw.schule.svws.db.converter.current;

import de.nrw.schule.svws.db.converter.DBAttributeConverter;
import jakarta.persistence.Converter;

/**
 * Diese Klasse dient dem Konvertieren von Wahrheitswerten in Java (Boolean)
 * zu einer Stringdarstellung in der Datenbank, bei der die - false repräsentiert
 * und die + true.
 * Sie ist abgeleitet von der Basisklasse {@link DBAttributeConverter}, welche
 * die grundlegende Funktionalität von Konvertern zur Verfügung stellt. Dort muss
 * der Konverter auch in der Methode {@link DBAttributeConverter#getByClass} 
 * registriert werden. 
 */
@Converter
public class BooleanPlusMinusConverter extends DBAttributeConverter<Boolean, String> {

	/** Die Instanz des Konverters */
	public final static BooleanPlusMinusConverter instance = new BooleanPlusMinusConverter();
	
	@Override
	public String convertToDatabaseColumn(Boolean value) {
		if (value == null)
			return null;
		return value ? "+" : "-";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return "+".equals(dbData);
	}

	@Override
	public Class<Boolean> getResultType() {
		return Boolean.class;
	}

	@Override
	public Class<String> getDBType() {
		return String.class;
	}

}
