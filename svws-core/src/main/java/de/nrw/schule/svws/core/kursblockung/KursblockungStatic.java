package de.nrw.schule.svws.core.kursblockung;

import java.util.Random;

import jakarta.validation.constraints.NotNull;

// TODO BAR KursblockungStatic --> adt??? 

/** Diese Klasse bietet statische Helfer-Methoden an.
 * 
 * @author Benjamin A. Bartsch */
public class KursblockungStatic {

	/** Erzeugt ein Array der Größe {@code n}, füllt es mit den Zahlen {@code 0 bis n-1} und permutiert das Array dann
	 * zufällig.
	 * 
	 * @param  pRandom Ein {@link Random}-Objekt zur Steuerung des Zufalls über einen Anfangs-Seed.
	 * @param  n       Die Größe des Arrays.
	 * @return         Eine Array-Referenz. */
	public static @NotNull int[] gibPermutation(@NotNull Random pRandom, int n) {
		@NotNull int[] temp = new int[n];
		for (int i = 0; i < n; i++) {
			temp[i] = i;
		}
		aktionPermutiere(pRandom, temp);
		return temp;
	}

	/** Permutiert das Array {@code perm} zufällig.
	 * 
	 * @param pRandom Ein {@link Random}-Objekt zur Steuerung des Zufalls über einen Anfangs-Seed.
	 * @param perm    Das zu permutierende Array. */
	public static void aktionPermutiere(@NotNull Random pRandom, @NotNull int[] perm) {
		int n = perm.length;
		for (int i1 = 0; i1 < n; i1++) {
			int i2 = pRandom.nextInt(n);
			int s1 = perm[i1];
			int s2 = perm[i2];
			perm[i1] = s2;
			perm[i2] = s1;
		}
	}


}
