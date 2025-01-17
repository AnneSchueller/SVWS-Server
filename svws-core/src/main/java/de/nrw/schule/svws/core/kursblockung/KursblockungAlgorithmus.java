package de.nrw.schule.svws.core.kursblockung;

import java.util.Random;
import java.util.Vector;

import de.nrw.schule.svws.core.Service;
import de.nrw.schule.svws.core.logger.LogLevel;
import de.nrw.schule.svws.core.utils.gost.GostBlockungsdatenManager;
import de.nrw.schule.svws.core.utils.gost.GostBlockungsergebnisManager;
import jakarta.validation.constraints.NotNull;

/** Dieser Service wandelt die Eingabedaten {@link GostBlockungsdatenManager} in dynamische Blockungsdaten
 * {@link KursblockungDynDaten} um, startet dann den Kursblockungsalgorithmus, welcher die Blockungsdaten manipuliert
 * und wandelt zuletzt {@link KursblockungDynDaten} in die Ausgabedaten {@link GostBlockungsergebnisManager} um. Der
 * Service überschreitet dabei nicht die Zeit, die in {@link GostBlockungsdatenManager#getMaxTimeMillis()} festgelegt
 * wurde.
 * 
 * @author Benjamin A. Bartsch */
public class KursblockungAlgorithmus
		extends Service<@NotNull GostBlockungsdatenManager, @NotNull Vector<@NotNull GostBlockungsergebnisManager>> {

	@Override
	public @NotNull Vector<@NotNull GostBlockungsergebnisManager> handle(@NotNull GostBlockungsdatenManager pInput) {
		// Logger-Einrückung (relativ +4).
		logger.modifyIndent(+4);

		// Random-Objekt erzeugen (Größter Integer Wert in TypeScript --> 9007199254740991L).
		long seed = new Random().nextLong();
		@NotNull Random random = new Random(seed);
		logger.log(LogLevel.APP, "Erster nextInt() Aufruf liefert " + seed);

		// Konvertierung von 'KursblockungInput' zu 'KursblockungDynamischeDaten'.
		@NotNull KursblockungDynDaten dynDaten = new KursblockungDynDaten(random, logger, pInput);
		long zeitBedarf = dynDaten.gibBlockungszeitMillis();
		long zeitEndeGesamt = System.currentTimeMillis() + zeitBedarf;

		// Vorbereitung der Rückgabe an die GUI.
		@NotNull Vector<@NotNull GostBlockungsergebnisManager> kursblockungOutputs = new Vector<>();

		@NotNull KursblockungAlgorithmusK @NotNull [] algorithmenK = new KursblockungAlgorithmusK @NotNull [] {
				// Alle Algorithmen zur Verteilung von Kursen auf ihre Schienen ...
				new KursblockungAlgorithmusKSchnellW(random, logger, dynDaten),
				new KursblockungAlgorithmusKFachwahlmatrix(random, logger, dynDaten),
				new KursblockungAlgorithmusKMatching(random, logger, dynDaten),
				new KursblockungAlgorithmusKSchuelervorschlag(random, logger, dynDaten),
				new KursblockungAlgorithmusKOptimiereBest(random, logger, dynDaten),
				// ... Ende der K-Algorithmen.
		};

		@NotNull KursblockungAlgorithmusS @NotNull [] algorithmenS = new KursblockungAlgorithmusS @NotNull [] {
				// Alle Algorithmen zur Verteilung von SuS auf ihre Kurse ...
				new KursblockungAlgorithmusSSchnellW(random, logger, dynDaten),
				new KursblockungAlgorithmusSZufaellig(random, logger, dynDaten),
				new KursblockungAlgorithmusSMatching(random, logger, dynDaten),
				new KursblockungAlgorithmusSMatchingW(random, logger, dynDaten),
				// ... Ende der S-Algorithmen.
		};

		// Hauptschleife: Jeder Algorithmus-K erhält stetig mehr Zeit.
		long zeitProK = 100l; // weniger als 100 führt zu unnützen Ergebnissen
		do {
			// Jeder Algorithmus-K wird ausprobiert.
			for (int iK = 0; iK < algorithmenK.length; iK++) {

				// Ggf. läuft ein AlgorithmusK auch erneut, falls er schneller als seine Maximalzeit war.
				long zeitEndeK = System.currentTimeMillis() + zeitProK;
				do {
					// System.out.println("Zeit " + zeitProK + " Algorithmus " + iK);
					verwendeAlgorithmusK(algorithmenK[iK], zeitEndeK, dynDaten, algorithmenS, kursblockungOutputs, pInput);
				} while (System.currentTimeMillis() < zeitEndeK);

				// Zeit abgelaufen?
				if (System.currentTimeMillis() + zeitProK > zeitEndeGesamt)
					break;

			}
			// Nächster Durchgang hat mehr Zeit.
			zeitProK *= 2;
		} while (System.currentTimeMillis() < zeitEndeGesamt);

		// Logger-Einrückung (relativ -4)
		logger.modifyIndent(-4);

		// Rückgabe aller Blockungsergebnisse.
		return kursblockungOutputs;
	}

	private static void verwendeAlgorithmusK(@NotNull KursblockungAlgorithmusK kursblockungAlgorithmusK, long zeitEndeK,
			@NotNull KursblockungDynDaten dynDaten, @NotNull KursblockungAlgorithmusS @NotNull [] algorithmenS,
			@NotNull Vector<@NotNull GostBlockungsergebnisManager> outputs, @NotNull GostBlockungsdatenManager pInput) {

		// Verteilung der Kurse.
		kursblockungAlgorithmusK.berechne(zeitEndeK);
		// dynDaten.gibStatistik().debug("berechne:" );

		// Verteilung der SuS (nur die beste Verteilung bleibt im Zustand K).
		dynDaten.aktionZustandSpeichernK();

		for (int iS = 0; iS < algorithmenS.length; iS++) {
			// Verteilung der SuS
			algorithmenS[iS].berechne();

			// Bessere SuS-Verteilung gefunden?
			if (dynDaten.gibCompareZustandK_NW_KD_FW() > 0)
				dynDaten.aktionZustandSpeichernK();
		}

		// Bestes Ergebnis laden (Zustand K).
		dynDaten.aktionZustandLadenK();

		// Gibt es einen neuen besten globalen Zustand?
		if (dynDaten.gibCompareZustandG_NW_KD_FW() > 0) {
			// dynDaten.debug();
			// dynDaten.gibStatistik().debug("*" + kursblockungAlgorithmusK);
			dynDaten.aktionZustandSpeichernG();
		}

		// Aktuellen Stand der Blockung speichern.
		@NotNull GostBlockungsergebnisManager out = dynDaten.gibErzeugtesKursblockungOutput(pInput, outputs.size() + 1);
		outputs.add(out);
	}

}
