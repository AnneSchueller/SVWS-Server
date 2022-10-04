package de.nrw.schule.svws.core.kursblockung.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;
import java.util.Vector;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.nrw.schule.svws.core.adt.set.AVLSet;
import de.nrw.schule.svws.core.data.kursblockung.SchuelerblockungInput;
import de.nrw.schule.svws.core.data.kursblockung.SchuelerblockungInputFachwahl;
import de.nrw.schule.svws.core.data.kursblockung.SchuelerblockungInputKurs;
import de.nrw.schule.svws.core.data.kursblockung.SchuelerblockungOutput;
import de.nrw.schule.svws.core.data.kursblockung.SchuelerblockungOutputFachwahlZuKurs;
import de.nrw.schule.svws.core.kursblockung.SchuelerblockungAlgorithmus;
import de.nrw.schule.svws.logger.LogData;
import de.nrw.schule.svws.logger.LogLevel;
import de.nrw.schule.svws.logger.Logger;
import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse testet den {@link SchuelerblockungAlgorithmus}.
 */
@DisplayName("Diese Klasse testet den {@link SchuelerblockungAlgorithmus}.")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class SchuelerblockungTests {

	private static final long _SEED = 1L;
	private static final int _ANZAHL_AN_ZUFALLSTESTS = 1000;

	/** Initialisiert den Test. Die Methode ist leer. */
	@BeforeAll
	static void setup() {
	}

	/** Testet den Schülerblockungs-Algorithmus mit randomisierten Daten. */
	@Test
	@DisplayName("Testet den Schülerblockungs-Algorithmus mit randomisierten Daten.")
	void testeSchuelerblockungAlgorithmusZufaellig() {
		Random lRandom = new Random(_SEED);
		for (int i = 0; i < _ANZAHL_AN_ZUFALLSTESTS; i++)
			testeSchuelerblockAlgorithmusZufaelligEinMal(i, lRandom, false);
	}

	private void testeSchuelerblockAlgorithmusZufaelligEinMal(int pRunde, Random pRandom, boolean pMitMultikursen) {

		// Der Kursblockungsalgorithmus ist ein Service.
		SchuelerblockungAlgorithmus alg = new SchuelerblockungAlgorithmus();

		// Logger vom Service übernehmen
		Logger log = alg.getLogger();

		// Consumer triggert 'fail', wenn etwas kritisches geloggt wurde.
		log.addConsumer(new Consumer<LogData>() {
			@Override
			public void accept(LogData t) {
				if (t.getLevel().compareTo(LogLevel.APP) != 0)
					fail(t.getText());
			}
		});

		// Erzeugen von zufälligen Eingabedaten
		SchuelerblockungInput in = new SchuelerblockungInput();
		// in.schuelerID = pRunde; // irrelevant

		int nFachwahlen = 1 + pRandom.nextInt(15); // 1 bis 15 Fachwahlen
		int nFachwahlenDavonMulti = pRandom.nextInt(4); // 0 bis 4 Multikurse
		if (nFachwahlenDavonMulti > nFachwahlen)
			nFachwahlenDavonMulti = pRandom.nextInt(nFachwahlen);
		int nKurse = 0; // Zähler für die Kurse.
		int nSchienen = nFachwahlen + nFachwahlenDavonMulti;
		in.schienen = nSchienen;
		AVLSet<String> setFachart = new AVLSet<>();
		// System.out.println("nFachwahlen = " + nFachwahlen);
		// System.out.println("nFachwahlenDavonMulti = " + nFachwahlenDavonMulti);

		// Erzeuge Fachwahlen.
		int startSchiene = 1;
		for (int i = 0; i < nFachwahlen; i++) {
			SchuelerblockungInputFachwahl fachwahl = new SchuelerblockungInputFachwahl();
			fachwahl.id = i; // Die ID der Fachwahl muss pro Schüler eindeutig sein.
			fachwahl.fach = pRandom.nextLong(30); // Es gibt ca. 30 verschiedene Fächer.
			fachwahl.kursart = pRandom.nextLong(5); // Es gibt ca. 5 verschiedene Kursarten.
			fachwahl.representation = "Fachwahl Nr. " + i;
			in.fachwahlen.add(fachwahl);

			String sFachart = fachwahl.fach + ";" + fachwahl.kursart;
			while (setFachart.contains(sFachart)) {
				fachwahl.fach = pRandom.nextLong(30);
				fachwahl.kursart = pRandom.nextLong(5);
				sFachart = fachwahl.fach + ";" + fachwahl.kursart;
			}

			// Erzeuge die zugehörigen Kurse der Fachwahl.
			int nKurseDieserFachwahl = pRandom.nextInt(6) + 1; // 1 bis 6 Kurse erzeugen.
			for (int j = 0; j < nKurseDieserFachwahl; j++) {
				SchuelerblockungInputKurs kurs = new SchuelerblockungInputKurs();
				kurs.id = nKurse;
				kurs.anzahlSuS = pRandom.nextInt(35); // Bis zu 34 SuS pro Kurs.
				kurs.representation = "Kurs Nr. " + kurs.id;
				kurs.fach = fachwahl.fach; // Fach gehört zur obigen Fachwahl.
				kurs.kursart = fachwahl.kursart; // Kursart gehört zur obigen Fachwahl.
				if (j == 0) {
					if (i < nFachwahlenDavonMulti) {
						kurs.schienen = new int[] { startSchiene, startSchiene + 1 };
						startSchiene += 2;
					} else {
						kurs.schienen = new int[] { startSchiene };
						startSchiene += 1;
					}
				} else {
					int s1 = pRandom.nextInt(nSchienen) + 1; // Schienen sind 1-indiziert.
					if (i < nFachwahlenDavonMulti) {
						int s2 = pRandom.nextInt(nSchienen) + 1; // Schienen sind 1-indiziert.
						while (s1 == s2)
							s2 = pRandom.nextInt(nSchienen) + 1; // Schienen sind 1-indiziert.
						kurs.schienen = new int[] { s1, s2 };
					} else {
						kurs.schienen = new int[] { s1 };
					}
				}
				in.kurse.add(kurs);
				nKurse++;
			}
		}

		// ##################################################
		// ##################################################
		// Algorithmus bereechnet und liefert genau ein Ergebnis.
		SchuelerblockungOutput out = alg.handle(in);
		// ##################################################
		// ##################################################

		// Überprüfung potentieller Fehler.
		for (LogData t : alg.getLog().getLogData())
			if (t.getLevel().compareTo(LogLevel.APP) != 0)
				fail(t.getText());

		// Kein Blockungsergebnis vorhanden?
		if (out == null) {
			fail("SchuelerblockungOutput == null");
			return;
		}

		// Keine Fachwahlen vorhanden?
		@NotNull Vector<@NotNull SchuelerblockungOutputFachwahlZuKurs> fachwahlen = out.fachwahlenZuKurs;
		if (fachwahlen == null) {
			fail("SchuelerblockungOutput.fachwahlenZuKurs == null");
			return;
		}

		// Nicht die richtigen Anzahl an Fachwahlen?
		if (fachwahlen.size() != nFachwahlen) {
			fail("SchuelerblockungOutput.fachwahlenZuKurs.size() != " + nFachwahlen);
			return;
		}

		// Wurde wirklich jeder Fachwahl etwas zugeordnet?
		for (int i = 0; i < fachwahlen.size(); i++) {
			@NotNull SchuelerblockungOutputFachwahlZuKurs fachwahlZuKurs = fachwahlen.get(i);
			if (fachwahlZuKurs == null) {
				fail("fachwahl == null");
				return;
			}
			if (fachwahlZuKurs.kurs < 0) {
				fail("fachwahl.kurs < 0, dabei ist eine optimale Verteilung möglich!");
				return;
			}
			if (fachwahlZuKurs.fachwahl != in.fachwahlen.get(i).id) {
				fail("Eingabe-Fachwahl-ID != Ausgabe-Fachwahl-ID");
				return;
			}
		}

	}

}
