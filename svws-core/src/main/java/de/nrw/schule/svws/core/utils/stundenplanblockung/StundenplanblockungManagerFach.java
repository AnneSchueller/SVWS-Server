package de.nrw.schule.svws.core.utils.stundenplanblockung;

import java.util.Vector;
import jakarta.validation.constraints.NotNull;

/**
 * 
 * @author Benjamin A. Bartsch
 */
public class StundenplanblockungManagerFach {

	/** Die Datenbank-ID des Faches. */
	long _id;

	/** Das Kürzel des Faches. Beispielsweise 'D', 'E' oder 'M'. */
	@NotNull String _kuerzel = "";

	/** Alle Lerngruppen in denen das Fach vertreten ist. */
	@NotNull Vector<StundenplanblockungManagerLerngruppe> _menge_gr = new Vector<>();

	/**
	 * Erzeugt ein neues Fach.
	 * 
	 * @param pFachID   Die Datenbank-ID des Faches.
	 * @param pKuerzel  Das Kürzel des Faches.
	 */
	public StundenplanblockungManagerFach(long pFachID, @NotNull String pKuerzel) {
		_id = pFachID;
		_kuerzel = pKuerzel;
	}
	
	/**
	 * Liefert die Datenbank-ID des Faches.
	 * 
	 * @return Die Datenbank-ID des Faches.
	 */
	public long getID() {
		return _id;
	}

}
