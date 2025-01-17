package de.nrw.schule.svws.csv.converter.current;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import de.nrw.schule.svws.core.types.schule.Verkehrssprache;
import de.nrw.schule.svws.db.converter.current.VerkehrssprachenConverter;

/**
 * Diese Klasse ist ein Deserialisierer für Nationalitäten. Sie deserialisiert
 * die Datenbankdarstellung als String in einen Wert der Aufzählung {@link Verkehrssprache}.
 */
public class VerkehrssprachenConverterDeserializer extends StdDeserializer<Verkehrssprache> {

	private static final long serialVersionUID = -5130396859369645143L;

	/**
	 * Erzeugt einen neuen Deserialisierer
	 */
	public VerkehrssprachenConverterDeserializer() {
		super(Verkehrssprache.class);
	}

	/**
	 * Erzeugt einen neuen Deserialisierer unter Angabe der {@link Class}
	 *
	 * @param t   das Klassen-Objekt
	 */
	protected VerkehrssprachenConverterDeserializer(Class<Verkehrssprache> t) {
		super(t);
	}

	@Override
	public Verkehrssprache deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return VerkehrssprachenConverter.instance.convertToEntityAttribute(p.getText());
	}
	
}
