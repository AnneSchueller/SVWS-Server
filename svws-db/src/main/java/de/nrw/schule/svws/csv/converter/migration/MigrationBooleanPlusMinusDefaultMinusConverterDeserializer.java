package de.nrw.schule.svws.csv.converter.migration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultMinusConverter;

/**
 * Diese Klasse ist ein Deserialisierer und deserialisiert die Datenbankdarstellung 
 * als String,  - (false) oder + (true) sein kann, in einen Java-String-Wert.
 * Dabei wird der Defult auf - (false) gesetzt.
 */
public class MigrationBooleanPlusMinusDefaultMinusConverterDeserializer extends StdDeserializer<Boolean> {

	private static final long serialVersionUID = -1327227762966985248L;

	/**
	 * Erzeugt einen neuen Deerialisierer
	 */
	public MigrationBooleanPlusMinusDefaultMinusConverterDeserializer() {
		super(Boolean.class);
	}
	
	/**
	 * Erzeugt einen neuen Deserialisierer unter Angabe der {@link Class}
	 * 
	 * @param t   das Klassen-Objekt
	 */
	protected MigrationBooleanPlusMinusDefaultMinusConverterDeserializer(Class<Boolean> t) {
		super(t);
	}

	@Override
	public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return BooleanPlusMinusDefaultMinusConverter.instance.convertToEntityAttribute(p.getText());
	}
	
}
