package de.nrw.schule.svws.core.kursblockung;

import java.util.Random;

import de.nrw.schule.svws.core.logger.Logger;
import jakarta.validation.constraints.NotNull;

/**
 * Dieser Kursverteilungs-Algorithmus verteilt die Kurse auf ihre Schienen, indem es die Kurslage zufällig verändert,
 * dann die SuS verteilt und dabei versucht die Bewertung (Nichtwahlen; Kursdifferenz) zu minimieren. Dabei wird der
 * übergebene Schülerverteilungs-Algorithmus verwendet.
 * 
 * @author Benjamin A. Bartsch
 */
public class KursblockungAlgorithmusKmitS extends KursblockungAlgorithmusK {

	/**
	 * Die Anzahl an Runden ohne Verbesserung, bevor es zum Abbruch kommt.
	 */
	private static final int MAX_RUNDEN_IN_FOLGE_OHNE_VERBESSERUNG = 2000;

	private final @NotNull KursblockungAlgorithmusS algorithmusS;

	/**
	 * Im Konstruktor kann die Klasse die jeweiligen Datenstrukturen aufbauen. Kurse dürfen in diese Methode noch nicht
	 * auf Schienen verteilt werden.
	 * 
	 * @param pRandom       Ein {@link Random}-Objekt zur Steuerung des Zufalls über einen Anfangs-Seed.
	 * @param pLogger       Logger für Benutzerhinweise, Warnungen und Fehler.
	 * @param pDynDat       Die dynamischen Blockungsdaten.
	 * @param pAlgorithmusS Der Algorithmus zum Verteilen der SuS.
	 */
	public KursblockungAlgorithmusKmitS(@NotNull Random pRandom, @NotNull Logger pLogger,
			@NotNull KursblockungDynDaten pDynDat, @NotNull KursblockungAlgorithmusS pAlgorithmusS) {
		super(pRandom, pLogger, pDynDat);
		algorithmusS = pAlgorithmusS;
	}

	/**
	 * Der Algorithmus entfernt zunächst alle SuS aus ihren Kursen. Anschließend werden die Kurse zufällig verteilt.
	 * Anschließend verändert der Algorithmus die Lage eines zufälligen Kurses. Falls sich die Bewertung verschlechtert,
	 * wird die Veränderung rückgängig gemacht.
	 */
	@Override
	public void berechne(long pMaxTimeMillis) {
		// Keine Kursverteilung, wenn es keine freien Kurse gibt.
		if (dynDaten.gibKurseDieFreiSindAnzahl() == 0) {
			return;
		}

		// Startzeit speichern.
		long timeStart = System.currentTimeMillis();

		// Entferne SuS aus den Kursen (vorsichtshalber wegen alter Berechnungen).
		dynDaten.aktionSchuelerAusAllenKursenEntfernen();

		// Verteile die Kurse beim ersten Start zufällig.
		dynDaten.aktionKurseFreieZufaelligVerteilen();

		// Speicherung des Start-Zustandes.
		dynDaten.aktionZustandSpeichernK();

		// Optimiere die Kurse. Bricht ab, wenn die Zeit vorbei ist, oder mehrfach keine Verbesserung erfolgt.
		int countKeineVerbesserung = 0;
		do {
			countKeineVerbesserung = verteileKurse(pMaxTimeMillis) ? 0 : countKeineVerbesserung + 1;
		} while ((countKeineVerbesserung < MAX_RUNDEN_IN_FOLGE_OHNE_VERBESSERUNG)
				&& (System.currentTimeMillis() - timeStart < pMaxTimeMillis));
	}

	/**
	 * Die Lage einiger Kurse wird verändert. Falls sich die Bewertung verschlechtert, wird die Veränderung rückgängig
	 * gemacht.
	 * 
	 * @param pMaxTimeMillis Die maximale Blockungszeit für den S-Algorithmus.
	 * @return TRUE, falls es zur Verbesserung kam.
	 */
	private boolean verteileKurse(long pMaxTimeMillis) {
		// Ein 1-* Kurse wandern zufällig in eine andere Schiene.
		do {
			// Entferne SuS, sonst dürfen Kurse nicht die Schiene wechseln.
			dynDaten.aktionSchuelerAusAllenKursenEntfernen();

			// Einen Kurs zufällige verteilen.
			dynDaten.aktionKursVerteilenEinenZufaelligenFreien();

			// SuS verteilen.
			algorithmusS.berechne();

			// Vergleiche mit vorheriger BewertungK.
			int cmp = dynDaten.gibCompareZustandK_NW_KD_FW();

			// Besser? --> Speichern.
			if (cmp > 0) {
				dynDaten.aktionZustandSpeichernK();
				return true;
			}
		} while (_random.nextBoolean());

		// Schlechter
		dynDaten.aktionZustandLadenK();
		return false;
	}

}
