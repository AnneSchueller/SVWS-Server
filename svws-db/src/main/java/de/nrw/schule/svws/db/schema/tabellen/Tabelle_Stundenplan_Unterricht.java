package de.nrw.schule.svws.db.schema.tabellen;

import de.nrw.schule.svws.core.adt.Pair;
import de.nrw.schule.svws.db.schema.Schema;
import de.nrw.schule.svws.db.schema.SchemaDatentypen;
import de.nrw.schule.svws.db.schema.SchemaFremdschluesselAktionen;
import de.nrw.schule.svws.db.schema.SchemaRevisionen;
import de.nrw.schule.svws.db.schema.SchemaTabelle;
import de.nrw.schule.svws.db.schema.SchemaTabelleFremdschluessel;
import de.nrw.schule.svws.db.schema.SchemaTabelleUniqueIndex;
import de.nrw.schule.svws.db.schema.SchemaTabelleSpalte;

/**
 * Diese Klasse beinhaltet die Schema-Definition für die Tabelle Stundenplan_Unterricht.
 */
public class Tabelle_Stundenplan_Unterricht extends SchemaTabelle {

	/** Die Definition der Tabellenspalte ID */
	public SchemaTabelleSpalte col_ID = add("ID", SchemaDatentypen.BIGINT, true)
		.setNotNull()
		.setJavaComment("Die eindeutige ID für diese Zuordnung des Untericht-Eintrages zu einem Stundenplan");

	/** Die Definition der Tabellenspalte Zeitraster_ID */
	public SchemaTabelleSpalte col_Zeitraster_ID = add("Zeitraster_ID", SchemaDatentypen.BIGINT, false)
		.setNotNull()
		.setJavaComment("Die ID des belegten Zeitraster-Eintrags");

	/** Die Definition der Tabellenspalte Wochentyp */
	public SchemaTabelleSpalte col_Wochentyp = add("Wochentyp", SchemaDatentypen.INT, false)
		.setDefault("0")
		.setNotNull()
		.setJavaComment("Gibt an, ob es sich um einen Eintrag für jede Woche handelt (0) oder ob es sich um einen unterschiedlichen (!) Eintrag für eine A- bzw. B-Wochen (1 bzw. 2) handelt");

	/** Die Definition der Tabellenspalte Klasse_ID */
	public SchemaTabelleSpalte col_Klasse_ID = add("Klasse_ID", SchemaDatentypen.BIGINT, false)
		.setJavaComment("Die Klasse, in welcher der Unterricht stattfindet. Bei Kursen entfällt dieser Eintrag und es ist eine ID des Kurses gesetzt");

	/** Die Definition der Tabellenspalte Kurs_ID */
	public SchemaTabelleSpalte col_Kurs_ID = add("Kurs_ID", SchemaDatentypen.BIGINT, false)
		.setJavaComment("Die ID des Kurses, falls der Unterricht nicht im Klassenverband stattfindet");

	/** Die Definition der Tabellenspalte Fach_ID */
	public SchemaTabelleSpalte col_Fach_ID = add("Fach_ID", SchemaDatentypen.BIGINT, false)
		.setNotNull()
		.setJavaComment("Die ID des Faches, in dem der Unterricht stattfindet");


	/** Die Definition des Fremdschlüssels Stundenplan_Unterricht_Klassen_FK */
	public SchemaTabelleFremdschluessel fk_Stundenplan_Unterricht_Klassen_FK = addForeignKey(
			"Stundenplan_Unterricht_Klassen_FK", 
			/* OnUpdate: */ SchemaFremdschluesselAktionen.CASCADE, 
			/* OnDelete: */ SchemaFremdschluesselAktionen.CASCADE, 
			new Pair<>(col_Klasse_ID, Schema.tab_Klassen.col_ID)
		);

	/** Die Definition des Fremdschlüssels Stundenplan_Unterricht_EigeneSchule_Faecher_FK */
	public SchemaTabelleFremdschluessel fk_Stundenplan_Unterricht_EigeneSchule_Faecher_FK = addForeignKey(
			"Stundenplan_Unterricht_EigeneSchule_Faecher_FK", 
			/* OnUpdate: */ SchemaFremdschluesselAktionen.CASCADE, 
			/* OnDelete: */ SchemaFremdschluesselAktionen.CASCADE, 
			new Pair<>(col_Fach_ID, Schema.tab_EigeneSchule_Faecher.col_ID)
		);

	/** Die Definition des Fremdschlüssels Stundenplan_Unterricht_Kurse_FK */
	public SchemaTabelleFremdschluessel fk_Stundenplan_Unterricht_Kurse_FK = addForeignKey(
			"Stundenplan_Unterricht_Kurse_FK", 
			/* OnUpdate: */ SchemaFremdschluesselAktionen.CASCADE, 
			/* OnDelete: */ SchemaFremdschluesselAktionen.CASCADE, 
			new Pair<>(col_Kurs_ID, Schema.tab_Kurse.col_ID)
		);

	/** Die Definition des Fremdschlüssels Stundenplan_Unterricht_Stundenplan_Zeitraster_FK */
	public SchemaTabelleFremdschluessel fk_Stundenplan_Unterricht_Stundenplan_Zeitraster_FK = addForeignKey(
			"Stundenplan_Unterricht_Stundenplan_Zeitraster_FK", 
			/* OnUpdate: */ SchemaFremdschluesselAktionen.CASCADE, 
			/* OnDelete: */ SchemaFremdschluesselAktionen.CASCADE, 
			new Pair<>(col_Zeitraster_ID, Schema.tab_Stundenplan_Zeitraster.col_ID)
		);


	/** Die Definition des Unique-Index Stundenplan_Unterricht_UC1 */
	public SchemaTabelleUniqueIndex unique_Stundenplan_Unterricht_UC1 = addUniqueIndex("Stundenplan_Unterricht_UC1", 
			col_Kurs_ID, 
			col_Klasse_ID, 
			col_Zeitraster_ID
		);


	/**
	 * Erstellt die Schema-Defintion für die Tabelle Stundenplan_Unterricht.
	 */
	public Tabelle_Stundenplan_Unterricht() {
		super("Stundenplan_Unterricht", SchemaRevisionen.REV_5);
		setMigrate(false);
		setImportExport(true);
		setPKAutoIncrement();
		setJavaSubPackage("schild.stundenplan");
		setJavaClassName("DTOStundenplanUnterricht");
		setJavaComment("Enthält die Zuordnung der Unterrichte (Klasse, Kurs, Fach) zu einem Zeitraster-Eintrag. Über das Zeitraster ist diese Zuordnung auch immer eindeutig einem Stundenplan zugeordnet. Die Zuordnung von Lehrern und Räumen erfolgt über die Tabellen Stundenplan_UnterrichtLehrer und Stundenplan_UnterrichtRaum");
	}

}
