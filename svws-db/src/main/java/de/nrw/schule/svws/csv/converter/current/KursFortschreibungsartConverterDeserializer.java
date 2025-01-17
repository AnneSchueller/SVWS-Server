package de.nrw.schule.svws.csv.converter.current;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import de.nrw.schule.svws.core.types.KursFortschreibungsart;
import de.nrw.schule.svws.db.converter.current.KursFortschreibungsartConverter;

/**
 * Diese Klasse ist ein Deserialisierer für die Kurs-Fortschriebungsart. Sie deserialisiert 
 * die Datenbankdarstellung als String in einen Wert der Aufzählung {@link KursFortschreibungsart}.
 */
public class KursFortschreibungsartConverterDeserializer extends StdDeserializer<KursFortschreibungsart> {

	private static final long serialVersionUID = 1871702485070815240L;

	/**
	 * Erzeugt einen neuen Deerialisierer
	 */
	public KursFortschreibungsartConverterDeserializer() {
		super(KursFortschreibungsart.class);
	}
	
	/**
	 * Erzeugt einen neuen Deserialisierer unter Angabe der {@link Class}
	 * 
	 * @param t   das Klassen-Objekt
	 */
	protected KursFortschreibungsartConverterDeserializer(Class<KursFortschreibungsart> t) {
		super(t);
	}

	@Override
	public KursFortschreibungsart deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return KursFortschreibungsartConverter.instance.convertToEntityAttribute(p.getText());
	}
	
}
