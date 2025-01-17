package de.nrw.schule.svws.csv.converter.current.statkue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import de.nrw.schule.svws.core.types.schule.Schulgliederung;

/**
 * Diese Klasse ist ein Deserialisierer und deserialisiert den CoreType 
 * Schulgliederung in das Kürzel der amtlichen Schulstatistik.
 */
public class SchulgliederungKuerzelConverterDeserializer extends StdDeserializer<Schulgliederung> {

	private static final long serialVersionUID = -3520968291156156611L;

	/**
	 * Erzeugt einen neuen Deserialisierer
	 */
	public SchulgliederungKuerzelConverterDeserializer() {
		super(Schulgliederung.class);
	}
	
	/**
	 * Erzeugt einen neuen Deserialisierer unter Angabe der {@link Class}
	 * 
	 * @param t   das Klassen-Objekt
	 */
	protected SchulgliederungKuerzelConverterDeserializer(Class<Schulgliederung> t) {
		super(t);
	}

	@Override
	public Schulgliederung deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return Schulgliederung.getByKuerzel(p.getText());
	}
	
}
