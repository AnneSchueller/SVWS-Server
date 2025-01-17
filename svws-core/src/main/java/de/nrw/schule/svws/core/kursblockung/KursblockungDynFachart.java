package de.nrw.schule.svws.core.kursblockung;

import java.util.Random;

import de.nrw.schule.svws.core.data.gost.GostFach;
import de.nrw.schule.svws.core.types.gost.GostKursart;
import jakarta.validation.constraints.NotNull;

/** Eine Fachart ist eine Kombination aus Fach und Kursart. Kurse sind genau einer Fachart zugeordnet. Ein Schüler ist
 * mehreren Facharten zugeordnet (entsprechend der Anzahl seiner Fachwahlen). Ein Objekt dieser Klasse kennt alle seine
 * Kurse und speichert die Anzahl an SuS die der Fachart zugeordnet wurden, deren Fachwahl also erfüllt wurde.
 * 
 * @author Benjamin A. Bartsch */
public class KursblockungDynFachart {

	/** Ein {@link Random}-Objekt zur Steuerung des Zufalls über einen Anfangs-Seed. */
	private final @NotNull Random _random;

	/** Eine laufende Nummer der Fachart. */
	private final int nr;

	/** Referenz zum zugehörigen GOST-Fach. */
	private final @NotNull GostFach gostFach;

	/** Referenz zur zugehörigen GOST-Kursart. */
	private final @NotNull GostKursart gostKursart;

	/** Ein Array aller Kurse dieser Fachart. Das Array bleibt dynamisch sortiert, so dass im Array zunächst der Kurs
	 * mit der geringsten Schüleranzahl ist. */
	private @NotNull KursblockungDynKurs @NotNull [] kursArr;

	/** Die maximale Anzahl an Schülern, die dieser Fachart zugeordnet sein können. Also die Anzahl der Schüler, die
	 * diese Fachart gewählt haben. */
	private int schuelerMax;

	/** Die maximale Anzahl an Kursen, die dieser Fachart zugeordnet sein können. */
	private int kurseMax;

	/** Die aktuelle Anzahl an Schülern, die dieser Fachart zugeordnet sind. */
	private int schuelerAnzNow;

	/** Dem Statistik-Objekt wird eine Veränderung der Kursdifferenz mitgeteilt. */
	private final @NotNull KursblockungDynStatistik statistik;

	/** @param pRandom  Ein {@link Random}-Objekt zur Steuerung des Zufalls über einen Anfangs-Seed.
	 * @param pNr          Eine laufende Nummer (ID) für alle Facharten.
	 * @param pGostFach    Referenz zum zugehörigen GOST-Fach.
	 * @param pGostKursart Referenz zur zugehörigen GOST-Kursart.
	 * @param pStatistik   Dem Statistik-Objekt wird eine Veränderung der Kursdifferenz mitgeteilt. */
	public KursblockungDynFachart(@NotNull Random pRandom, int pNr, @NotNull GostFach pGostFach,
			@NotNull GostKursart pGostKursart, @NotNull KursblockungDynStatistik pStatistik) {
		_random = pRandom;
		nr = pNr;
		gostFach = pGostFach;
		gostKursart = pGostKursart;
		statistik = pStatistik;
		kursArr = new KursblockungDynKurs[0];
		kurseMax = 0;
		schuelerMax = 0;
		schuelerAnzNow = 0;
	}

	/** Durch das Überschreiben dieser Methode, liefert dieses Objekt eine automatische String-Darstellung,
	 * beispielsweise 'D;LK'. */
	@Override
	public @NotNull String toString() {
		return gostFach.kuerzel + ";" + gostKursart.kuerzel;
	}

	// ########################################
	// ################ GETTER ################
	// ########################################

	/** Liefert die Nummer dieser Fachart.
	 * 
	 * @return Die Nummer dieser Fachart. */
	int gibNr() {
		return nr;
	}

	/** Liefert die maximale Anzahl ({@link #schuelerMax}) an SuS, die dieser Fachart zugeordnet sein können. Das ist
	 * die Anzahl der Fachwahlen.
	 * 
	 * @return Die Anzahl der SuS, die diese Fachart gewählt haben. */
	int gibSchuelerMax() {
		return schuelerMax;
	}

	/** Liefert die aktuelle Anzahl ({@link #schuelerAnzNow}) an SuS, die dieser Fachart zugeordnet sind.
	 * 
	 * @return Die Anzahl der SuS, die diese Fachart aktuell zugeordnet sind. */
	int gibSchuelerZordnungen() {
		return schuelerAnzNow;
	}

	/** Liefert die Anzahl der Kurse die dieser Fachart zugeordnet sind.
	 * 
	 * @return Die Anzahl der Kurse die dieser Fachart zugeordnet sind. */
	int gibKurseMax() {
		return kurseMax;
	}

	/** Liefert die aktuell größte Kursdifferenz.
	 * 
	 * @return Die aktuell größte Kursdifferenz. */
	int gibKursdifferenz() {
		return kursArr[kursArr.length - 1].gibSchuelerAnzahl() - kursArr[0].gibSchuelerAnzahl();
	}

	/** Liefert das Array aller Kurse dieser Fachart.
	 * 
	 * @return Das Array aller Kurse dieser Fachart. */
	@NotNull
	KursblockungDynKurs @NotNull [] gibKurse() {
		return kursArr;
	}

	/** Liefert den Kurs mit der geringsten SuS-Anzahl, welcher in Schiene {@code pSchiene } vorkommt.
	 * 
	 * @param  pSchiene     Die Schiene, in der gesucht wird.
	 * @param  kursGesperrt Definiert, alle Kurse des S. die gesperrt sind und somit ignoriert werden sollen.
	 * @return              Der kleinste Kurs in der Schiene pSchiene, oder null. */
	KursblockungDynKurs gibKleinstenKursInSchiene(int pSchiene, @NotNull boolean[] kursGesperrt) {
		for (int i = 0; i < kursArr.length; i++) {

			// Überspringe gesperrte Kurse.
			@NotNull KursblockungDynKurs kurs = kursArr[i];
			if (kursGesperrt[kurs.gibInternalID()]) {
				continue;
			}

			// Suche passende Schiene.
			for (int c : kurs.gibSchienenLage()) {
				if (c == pSchiene) {
					return kurs;
				}
			}
		}
		return null;
	}

	/** Liefert TRUE, falls mindestens ein Kurs dieser Fachart ein Multikurs ist.
	 * 
	 * @return TRUE, falls mindestens ein Kurs dieser Fachart ein Multikurs ist. */
	boolean gibHatMultikurs() {
		for (@NotNull KursblockungDynKurs kurs : kursArr) {
			if (kurs.gibSchienenAnzahl() > 1) {
				return true;
			}
		}
		return false;
	}

	/** Liefert TRUE, falls mindestens ein Kurs dieser Fachart in Schiene c ist.
	 * 
	 * @param  pSchiene     Die Schiene, die angefragt wurde.
	 * @param  kursGesperrt Falls TRUE, muss dieser Kurs ignoriert werden.
	 * @return              TRUE, falls mindestens ein Kurs dieser Fachart in Schiene c ist. */
	boolean gibHatKursInSchiene(int pSchiene, @NotNull boolean[] kursGesperrt) {
		for (@NotNull KursblockungDynKurs kurs : kursArr) {
			if (kursGesperrt[kurs.gibInternalID()]) {
				continue;
			}
			if (kurs.gibIstInSchiene(pSchiene)) {
				return true;
			}
		}
		return false;
	}

	/** Liefert TRUE, falls mindestens ein Kurs dieser Fachart in Schiene c wandern darf.
	 * 
	 * @param  pSchiene     Die Schiene, die angefragt wurde.
	 * @param  kursGesperrt Falls TRUE, muss dieser Kurs ignoriert werden.
	 * @return              TRUE, falls mindestens ein Kurs dieser Fachart in Schiene c wandern darf. */
	public boolean gibHatKursMitFreierSchiene(int pSchiene, @NotNull boolean[] kursGesperrt) {
		for (@NotNull KursblockungDynKurs kurs : kursArr) {
			if (kursGesperrt[kurs.gibInternalID()]) {
				continue;
			}
			if (kurs.gibIstSchieneFrei(pSchiene)) {
				return true;
			}
		}
		return false;
	}

	// ########################################
	// ########## AKTIONEN / SETTER ###########
	// ########################################

	/** Ordnet alle Kurse der Fachart zu. Die Kurse haben noch keine SuS und sind somit automatisch sortiert.
	 * 
	 * @param pKursArr Alle Kurse der Fachart. */
	public void aktionSetKurse(@NotNull KursblockungDynKurs @NotNull [] pKursArr) {
		kursArr = pKursArr;
	}

	/** Erhöht die Anzahl ({@link #schuelerMax}) an SuS, die diese Fachart gewählt haben um 1. */
	public void aktionMaxSchuelerErhoehen() {
		schuelerMax++;
	}

	/** Erhöht die Anzahl ({@link #kurseMax}) an Kursen, die zu dieser Fachart gehören. */
	public void aktionMaxKurseErhoehen() {
		kurseMax++;
	}

	/** Muss aufgerufen werden, bevor die Schüleranzahl eines Kurses verändert wird. */
	public void aktionKursdifferenzEntfernen() {
		statistik.aktionKursdifferenzEntfernen(gibKursdifferenz());
	}

	/** Muss aufgerufen werden, nachdem die Schüleranzahl eines Kurses verändert wird. */
	public void aktionKursdifferenzHinzufuegen() {
		statistik.aktionKursdifferenzHinzufuegen(gibKursdifferenz());
	}

	/** Erhöht die Anzahl ({@link #schuelerAnzNow}) an Schülern, die dieser Fachart momentan zugeordnet sind um 1. Da
	 * ein (bestimmter) Kurs nun einen S. mehr hat, muss das Array einmalig von links nach rechts sortiert werden. */
	public void aktionSchuelerWurdeHinzugefuegt() {
		schuelerAnzNow++;
		// Ein Kurs hat +1 SuS --> Sortiere 'kursArr' von links nach rechts.
		// Beispiel 11[3]223
		for (int i = 1; i < kursArr.length; i++) {
			@NotNull KursblockungDynKurs kursL = kursArr[i - 1];
			@NotNull KursblockungDynKurs kursR = kursArr[i];
			boolean b1 = kursL.gibSchuelerAnzahl() > kursR.gibSchuelerAnzahl();
			boolean b2 = (kursL.gibSchuelerAnzahl() == kursR.gibSchuelerAnzahl()) && (kursL.gibDatenbankID() > kursR.gibDatenbankID());
			if (b1 || b2) {
				kursArr[i - 1] = kursR;
				kursArr[i] = kursL;
			}
		}
	}

	/** Verringert die Anzahl ({@link #schuelerAnzNow}) an SuS, die dieser Fachart momentan zugeordnet sind um 1. Da ein
	 * (bestimmter) Kurs nun einen S. weniger hat, muss das Array einmalig von rechts nach links sortiert werden. */
	public void aktionSchuelerWurdeEntfernt() {
		schuelerAnzNow--;
		// Ein Kurs hat -1 SuS --> Sortiere 'kursArr' von rechts nach links.
		// Beispiel 1122[1]3
		for (int i = kursArr.length - 1; i >= 1; i--) {
			@NotNull KursblockungDynKurs kursL = kursArr[i - 1];
			@NotNull KursblockungDynKurs kursR = kursArr[i];
			boolean b1 = kursL.gibSchuelerAnzahl() > kursR.gibSchuelerAnzahl();
			boolean b2 = (kursL.gibSchuelerAnzahl() == kursR.gibSchuelerAnzahl()) && (kursL.gibDatenbankID() > kursR.gibDatenbankID());
			if (b1 || b2) {
				kursArr[i - 1] = kursR;
				kursArr[i] = kursL;
			}
		}
	}

	/** Lässt einen zufälligen Kurs dieser Fachart in die angegebene Schiene wandern.
	 * 
	 * @param pSchiene Die Schiene, in die einer Kurs der Fachart wandern soll. */
	void aktionZufaelligerKursWandertNachSchiene(int pSchiene) {
		@NotNull int[] perm = KursblockungStatic.gibPermutation(_random, kursArr.length);

		for (int p = 0; p < perm.length; p++) {
			KursblockungDynKurs kurs = kursArr[perm[p]];
			if (kurs.gibIstSchieneFrei(pSchiene)) {
				kurs.aktionSetzeInSchiene(pSchiene);
				return;
			}
		}

		System.out.println("aktionZufaelligerKursWandertNachSchiene: THIS SHOULD NOT BE REACHED!!!");
	}

	/** Debug Ausgabe. Nur für Testzwecke.
	 * 
	 * @param schuelerArr Das Array mit den Schülerdaten. */
	void debug(@NotNull KursblockungDynSchueler @NotNull [] schuelerArr) {
		for (int i = 0; i < kursArr.length; i++) {
			kursArr[i].debug(schuelerArr);
		}
	}
	
	/**
	 * Liefert das zugehörige Fach-Objekt.
	 * 
	 * @return Das zugehörige Fach-Objekt.
	 */
	@NotNull GostFach gibFach() {
		return gostFach;
	}
	
	/**
	 * Liefert das zugehörige Kursart-Objekt.
	 * 
	 * @return Das zugehörige Kursart-Objekt.
	 */
	@NotNull GostKursart gibKursart() {
		return gostKursart;
	}
	
	

}
