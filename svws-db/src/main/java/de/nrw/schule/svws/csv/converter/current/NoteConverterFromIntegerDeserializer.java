package de.nrw.schule.svws.csv.converter.current;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import de.nrw.schule.svws.core.types.Note;
import de.nrw.schule.svws.db.converter.current.NoteConverterFromInteger;

/**
 * Diese Klasse ist ein Deserialisierer für Noten. Sie deserialisiert
 * die Datenbankdarstellung als Integer, welche eine Note ohne Tendenz 
 * repräsentiert (1-6), in einen Wert der Aufzählung {@link Note}.
 */
public class NoteConverterFromIntegerDeserializer extends StdDeserializer<Note> {

	private static final long serialVersionUID = 7505421606549933149L;

	/**
	 * Erzeugt einen neuen Deerialisierer
	 */
	public NoteConverterFromIntegerDeserializer() {
		super(Note.class);
	}
	
	/**
	 * Erzeugt einen neuen Deserialisierer unter Angabe der {@link Class}
	 * 
	 * @param t   das Klassen-Objekt
	 */
	protected NoteConverterFromIntegerDeserializer(Class<Note> t) {
		super(t);
	}

	@Override
	public Note deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			return NoteConverterFromInteger.instance.convertToEntityAttribute(p.getIntValue());
		} catch (@SuppressWarnings("unused") IOException e) {
			return null;
		}
	}
	
}
