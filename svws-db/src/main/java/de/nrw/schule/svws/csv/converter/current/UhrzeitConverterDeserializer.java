package de.nrw.schule.svws.csv.converter.current;

import java.io.IOException;
import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import de.nrw.schule.svws.db.converter.current.UhrzeitConverter;

/**
 * Diese Klasse ist ein Deserialisierer für Uhrzeitwerte. Sie deserialisiert die 
 * Datenbankdarstellung als Timestamp in eine Uhrzeit als Zeichenkette nach ISO-8601.
 */
public class UhrzeitConverterDeserializer extends StdDeserializer<String> {

	private static final long serialVersionUID = 1997235870466231273L;

	/**
	 * Erzeugt einen neuen Deserialisierer
	 */
	public UhrzeitConverterDeserializer() {
		super(String.class);
	}
	
	/**
	 * Erzeugt einen neuen Deserialisierer unter Angabe der {@link Class}
	 * 
	 * @param t   das Klassen-Objekt
	 */
	protected UhrzeitConverterDeserializer(Class<String> t) {
		super(t);
	}

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return UhrzeitConverter.instance.convertToEntityAttribute(Timestamp.valueOf(p.getText()));
		} catch (@SuppressWarnings("unused") IllegalArgumentException e) {
			return null;
		}
	}
	
}
