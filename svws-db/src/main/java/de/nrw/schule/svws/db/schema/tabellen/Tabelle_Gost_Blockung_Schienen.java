package de.nrw.schule.svws.db.schema.tabellen;

import de.nrw.schule.svws.core.adt.Pair;
import de.nrw.schule.svws.db.schema.Schema;
import de.nrw.schule.svws.db.schema.SchemaDatentypen;
import de.nrw.schule.svws.db.schema.SchemaFremdschluesselAktionen;
import de.nrw.schule.svws.db.schema.SchemaRevisionen;
import de.nrw.schule.svws.db.schema.SchemaTabelle;
import de.nrw.schule.svws.db.schema.SchemaTabelleFremdschluessel;
import de.nrw.schule.svws.db.schema.SchemaTabelleSpalte;

/**
 * Diese Klasse beinhaltet die Schema-Definition für die Tabelle Gost_Blockung_Schienen.
 */
public class Tabelle_Gost_Blockung_Schienen extends SchemaTabelle {

	/** Die Definition der Tabellenspalte ID */
	public SchemaTabelleSpalte col_ID = add("ID", SchemaDatentypen.BIGINT, true)
		.setNotNull()
		.setJavaComment("ID der Schiene in der Blockung (generiert)");

	/** Die Definition der Tabellenspalte Blockung_ID */
	public SchemaTabelleSpalte col_Blockung_ID = add("Blockung_ID", SchemaDatentypen.BIGINT, false)
		.setNotNull()
		.setJavaComment("ID der Blockung");

	/** Die Definition der Tabellenspalte Nummer */
	public SchemaTabelleSpalte col_Nummer = add("Nummer", SchemaDatentypen.INT, false)
		.setNotNull()
		.setJavaComment("Die Nummer der Schiene, beginnend bei 1");

	/** Die Definition der Tabellenspalte Bezeichnung */
	public SchemaTabelleSpalte col_Bezeichnung = add("Bezeichnung", SchemaDatentypen.VARCHAR, false).setDatenlaenge(255)
		.setDefault("Schiene")
		.setNotNull()
		.setJavaComment("Bezeichnung der Schiene (z.B. LK Schiene 1)");

	/** Die Definition der Tabellenspalte Wochenstunden */
	public SchemaTabelleSpalte col_Wochenstunden = add("Wochenstunden", SchemaDatentypen.INT, false)
		.setDefault("3")
		.setNotNull()
		.setJavaComment("Die Anzahl der Wochenstunden, welche der Schiene zugeordnet sind");


	/** Die Definition des Fremdschlüssels Gost_Blockung_Schienen_Blockung_FK */
	public SchemaTabelleFremdschluessel fk_Gost_Blockung_Schienen_Blockung_FK = addForeignKey(
			"Gost_Blockung_Schienen_Blockung_FK", 
			/* OnUpdate: */ SchemaFremdschluesselAktionen.CASCADE, 
			/* OnDelete: */ SchemaFremdschluesselAktionen.CASCADE, 
			new Pair<>(col_Blockung_ID, Schema.tab_Gost_Blockung.col_ID)
		);


	/**
	 * Erstellt die Schema-Defintion für die Tabelle Gost_Blockung_Schienen.
	 */
	public Tabelle_Gost_Blockung_Schienen() {
		super("Gost_Blockung_Schienen", SchemaRevisionen.REV_7);
		setMigrate(false);
		setImportExport(true);
		setPKAutoIncrement();
		setJavaSubPackage("gost.kursblockung");
		setJavaClassName("DTOGostBlockungSchiene");
		setJavaComment("Tabelle für die Schienen, welche einer Kursblockung der gymnasialen Oberstufe zugeordnet sind");
	}

}
