package de.nrw.schule.svws.core.types.lehrer;

import java.util.HashMap;

import de.nrw.schule.svws.core.data.lehrer.LehrerKatalogBeschaeftigungsartEintrag;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Aufzählung stellt einen Core-Type für die Beschäftigungsarten für
 * Lehrer an der Schule zur Verfügung.
 *  
 * Core-Types dienen als grundlegende abstrakte Datentypen sowohl für die Core-Algorithmen
 * als auch für die OpenAPI-Schnittstelle.
 */
public enum LehrerBeschaeftigungsart {


	/** Beschaeftigungsart 'Vollzeit' eines Lehrers */
	V(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(1, "V", "Vollzeit", null, null)
	}),

	/** Beschaeftigungsart 'Teilzeit' eines Lehrers */
	T(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(2, "T", "Teilzeit", null, null)
	}),

	/** Beschaeftigungsart 'Altersteilzeit (Beschäftigungsphase)' eines Lehrers */
	AT(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(3, "AT", "Altersteilzeit (Beschäftigungsphase)", null, null)
	}),

	/** Beschaeftigungsart 'Altersteilzeit, vorm. teilzeitbeschäftigt (Verzichtsphase Altersermäßigung)' eines Lehrers */
	TA(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(4, "TA", "Altersteilzeit, vorm. teilzeitbeschäftigt (Verzichtsphase Altersermäßigung)", null, null)
	}),

	/** Beschaeftigungsart 'Altersteilzeit, vorm. vollzeitbeschäftigt (Verzichtsphase Altersermäßigung)' eines Lehrers */
	VA(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(5, "VA", "Altersteilzeit, vorm. vollzeitbeschäftigt (Verzichtsphase Altersermäßigung)", null, null)
	}),

	/** Beschaeftigungsart 'Sabbatjahr' eines Lehrers */
	TS(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(6, "TS", "Sabbatjahr", null, null)
	}),

	/** Beschaeftigungsart 'Nebenberufliche Beschäftigtigung' eines Lehrers */
	SB(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(8, "SB", "Nebenberufliche Beschäftigtigung", null, null)
	}),

	/** Beschaeftigungsart 'Geringfügige Beschäftigtigung' eines Lehrers */
	GB(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(9, "GB", "Geringfügige Beschäftigtigung", null, null)
	}),

	/** Beschaeftigungsart 'Studierende' eines Lehrers */
	ST(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(11, "ST", "Studierende", null, null)
	}),

	/** Beschaeftigungsart 'Beamter, nebenamtlich (nicht hauptamtlich im Schuldienst)' eines Lehrers */
	NA(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(12, "NA", "Beamter, nebenamtlich (nicht hauptamtlich im Schuldienst)", null, null)
	}),

	/** Beschaeftigungsart 'Gestellungsvertrag' eines Lehrers */
	G(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(13, "G", "Gestellungsvertrag", null, null)
	}),

	/** Beschaeftigungsart 'Unentgeltlich Beschäftigte' eines Lehrers */
	X(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(15, "X", "Unentgeltlich Beschäftigte", null, null)
	}),

	/** Beschaeftigungsart 'Beamte auf Widerruf (LAA) in Teilzeit' eines Lehrers */
	WT(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(16, "WT", "Beamte auf Widerruf (LAA) in Teilzeit", null, null)
	}),

	/** Beschaeftigungsart 'Beamte auf Widerruf (LAA) in Vollzeit' eines Lehrers */
	WV(new LehrerKatalogBeschaeftigungsartEintrag[]{
		new LehrerKatalogBeschaeftigungsartEintrag(17, "WV", "Beamte auf Widerruf (LAA) in Vollzeit", null, null)
	});


	/** Die Version dieses Core-Types, um beim Datenbank Update-Process die Version des Core-Types feststellen zu können. */
	public static long VERSION = 1;	
	
	/** Der aktuellen Daten der Beschäftigungsart, wenn keine Beschränkung der Gültigkeit vorliegen - sonst null */
	public final @NotNull LehrerKatalogBeschaeftigungsartEintrag daten;
	
	/** Die Historie mit den Einträgen der Beschäftigungsart */
	public final @NotNull LehrerKatalogBeschaeftigungsartEintrag@NotNull[] historie;	

	/** Eine Hashmap mit allen Beschäftigungsarten, welche ihrer ID zugeordnet sind. */
	private static final @NotNull HashMap<@NotNull Long, LehrerBeschaeftigungsart> _artenByID = new HashMap<>();

	/** Eine Hashmap mit allen Beschäftigungsarten, welche dem Kürzel bzw. ASD-Schlüssel zugeordnet sind. */
	private static final @NotNull HashMap<@NotNull String, LehrerBeschaeftigungsart> _artenByKuerzel = new HashMap<>();	
	

	/**
	 * Erzeugt eine neue Beschäftigungsart in der Aufzählung.
	 * 
	 * @param historie   die Historie der Beschäftigungsart, welches ein Array von {@link LehrerKatalogBeschaeftigungsartEintrag} ist  
	 */
	private LehrerBeschaeftigungsart(@NotNull LehrerKatalogBeschaeftigungsartEintrag@NotNull[] historie) {
		this.historie = historie;
		// TODO Prüfe korrekte Reihenfolge der Einträge und sortiere so, dass Eintrag 0 im Array der älteste Eintrag ist 
		this.daten = historie[historie.length - 1];
	}

	
	/**
	 * Gibt eine Map von den IDs der Beschäftigungsarten auf die zugehörigen Beschäftigungsarten
	 * zurück. Sollte diese noch nicht initialisiert sein, so wird sie initielisiert.
	 *    
	 * @return die Map von den IDs der Beschäftigungsarten auf die zugehörigen Beschäftigungsarten
	 */
	private static @NotNull HashMap<@NotNull Long, LehrerBeschaeftigungsart> getMapArtenByID() {
		if (_artenByID.size() == 0)
			for (LehrerBeschaeftigungsart l : LehrerBeschaeftigungsart.values())
				_artenByID.put(l.daten.id, l);				
		return _artenByID;
	}

	
	/**
	 * Gibt eine Map von den Kürzeln der Beschäftigungsarten auf die zugehörigen Beschäftigungsarten
	 * zurück. Sollte diese noch nicht initialisiert sein, so wird sie initielisiert.
	 *    
	 * @return die Map von den Kürzeln der Beschäftigungsarten auf die zugehörigen Beschäftigungsarten
	 */
	private static @NotNull HashMap<@NotNull String, LehrerBeschaeftigungsart> getMapArtenByKuerzel() {
		if (_artenByKuerzel.size() == 0)
			for (LehrerBeschaeftigungsart l : LehrerBeschaeftigungsart.values())
				_artenByKuerzel.put(l.daten.kuerzel, l);				
		return _artenByKuerzel;
	}
	

	/**
	 * Gibt die Beschäftigungsart anhand der angegebenen ID zurück.
	 * 
	 * @param id   die ID der Beschäftigungsart
	 * 
	 * @return die Beschäftigungsart oder null, falls die ID ungültig ist
	 */
	public static LehrerBeschaeftigungsart getByID(long id) {
		return getMapArtenByID().get(id);
	}


	/**
	 * Gibt die Beschäftigungsart anhand des angegebenen Kürzels zurück.
	 * 
	 * @param kuerzel   das Kürzel der Beschäftigungsart
	 * 
	 * @return die Beschäftigungsart oder null, falls das Kürzel ungültig ist
	 */
	public static LehrerBeschaeftigungsart getByKuerzel(String kuerzel) {
		return getMapArtenByKuerzel().get(kuerzel);
	}

}
