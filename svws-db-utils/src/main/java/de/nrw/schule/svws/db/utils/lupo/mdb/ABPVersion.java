package de.nrw.schule.svws.db.utils.lupo.mdb;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

/**
 * Diese Klasse wird für den Import der Tabelle ABP_Version aus einer LuPO-Datenbank 
 * im Access-Format genutzt. 
 */
public class ABPVersion {
	
	/** Datums-Formatierer für die Versionsangabe der LuPO-MDB-Datei */
	// private static DateTimeFormatter versionDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	/** Die Version der LuPO-MDB als Datum in der Form yyyyMMdd */
	public String Version = "20210307"; // versionDateFormatter.format(LocalDate.now());




	/**
	 * Liest alle Einträge der Tabelle "ABP_Version" aus der LuPO-Datei ein.
	 * 
	 * @param db   die Datenbank, aus der die Tabelle gelesen werden soll
	 * 
	 * @return die Liste der Schüler aus der LuPO-Datei
	 */
	public static List<ABPVersion> read(Database db) {
		try {
			List<ABPVersion> liste = new Vector<>();
			Table table = db.getTable("ABP_Version");
			for (Row r : table) {
				ABPVersion version = new ABPVersion();
				version.Version = r.getString("Version");
				liste.add(version);
			}
			return liste;
		} catch (@SuppressWarnings("unused") IOException e) {
			return Collections.emptyList();
		}
	}


	/**
	 * Schreibt die angegebenen Versionen in die übergebene Datenbank
	 * 
	 * @param db     die zu beschreibende Datenbank
	 * @param list   die Liste der zu schreibenden Versionen
	 */
	public static void write(Database db, List<ABPVersion> list) {
		try {
			Table table = new TableBuilder("ABP_Version")
			     .addColumn(new ColumnBuilder("Version", DataType.TEXT).setLengthInUnits(8))
			     .toTable(db);
			for (ABPVersion version: list) {
				table.addRow(
					version.Version
				);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Gibt den Standard-Eintrag für die Tabelle ABPVersion zurück.
	 * 
	 * @return der Standard-Eintrag für die Tabelle ABPVersion
	 */
	public static List<ABPVersion> getDefault() {
		List<ABPVersion> versionen = new Vector<>();
		versionen.add(new ABPVersion());
		return versionen;
	}


	@Override
	public String toString() {
		return "ABPVersion [Version=" + Version + "]";
	}

}
