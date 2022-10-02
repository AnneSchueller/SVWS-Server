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
 * Diese Klasse beinhaltet die Schema-Definition für die Tabelle Gost_Blockung.
 */
public class Tabelle_Gost_Blockung extends SchemaTabelle {

	/** Die Definition der Tabellenspalte ID */
	public SchemaTabelleSpalte col_ID = add("ID", SchemaDatentypen.BIGINT, true)
		.setNotNull()
		.setJavaComment("Kursblockung der Gymnasialen Oberstufe: ID der Blockung (generiert)");

	/** Die Definition der Tabellenspalte Name */
	public SchemaTabelleSpalte col_Name = add("Name", SchemaDatentypen.VARCHAR, false).setDatenlaenge(255)
		.setDefault("Neue Blockung")
		.setNotNull()
		.setJavaComment("Kursblockung der Gymnasialen Oberstufe: Textuelle Bezeichnung der Blockung");

	/** Die Definition der Tabellenspalte Abi_Jahrgang */
	public SchemaTabelleSpalte col_Abi_Jahrgang = add("Abi_Jahrgang", SchemaDatentypen.INT, false)
		.setNotNull()
		.setJavaComment("Kursblockung der Gymnasialen Oberstufe: Der Abiturjahrgang, dem die Blockung zugeordnet ist");

	/** Die Definition der Tabellenspalte Halbjahr */
	public SchemaTabelleSpalte col_Halbjahr = add("Halbjahr", SchemaDatentypen.INT, false)
		.setNotNull()
		.setConverter("GOStHalbjahrConverter")
		.setJavaComment("Kursblockung der Gymnasialen Oberstufe: Das Halbjahr, welchem die Kursblockung zugeordnet ist (0=EF.1, 1=EF.2, 2=Q1.1, 3=Q1.2, 4=Q2.1, 5=Q2.2)");

	/** Die Definition der Tabellenspalte Ergebnis_ID */
	public SchemaTabelleSpalte col_Ergebnis_ID = add("Ergebnis_ID", SchemaDatentypen.BIGINT, false)
		.setJavaComment("Kursblockung der Gymnasialen Oberstufe: Die ID des als aktiv ausgewählten Zwischenergebnisses, sofern eines definiert ist - ansonsten null");


	/** Die Definition des Fremdschlüssels Gost_Blockung_Abi_Jahrgang_FK */
	public SchemaTabelleFremdschluessel fk_Gost_Blockung_Abi_Jahrgang_FK = addForeignKey(
			"Gost_Blockung_Abi_Jahrgang_FK", 
			/* OnUpdate: */ SchemaFremdschluesselAktionen.CASCADE, 
			/* OnDelete: */ SchemaFremdschluesselAktionen.CASCADE, 
			new Pair<>(col_Abi_Jahrgang, Schema.tab_Gost_Jahrgangsdaten.col_Abi_Jahrgang)
		);


	/**
	 * Erstellt die Schema-Defintion für die Tabelle Gost_Blockung.
	 */
	public Tabelle_Gost_Blockung() {
		super("Gost_Blockung", SchemaRevisionen.REV_7);
		setMigrate(false);
		setImportExport(true);
		setPKAutoIncrement();
		setJavaSubPackage("gost.kursblockung");
		setJavaClassName("DTOGostBlockung");
		setJavaComment("Tabelle für die Definition einer Kursblockung der gymnasialen Oberstufe");
	}

}
