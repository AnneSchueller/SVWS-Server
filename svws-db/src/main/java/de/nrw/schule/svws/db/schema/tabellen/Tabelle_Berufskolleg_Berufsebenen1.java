package de.nrw.schule.svws.db.schema.tabellen;

import de.nrw.schule.svws.db.schema.SchemaDatentypen;
import de.nrw.schule.svws.db.schema.SchemaRevisionen;
import de.nrw.schule.svws.db.schema.SchemaTabelle;
import de.nrw.schule.svws.db.schema.SchemaTabelleSpalte;

/**
 * Diese Klasse beinhaltet die Schema-Definition für die Tabelle Berufskolleg_Berufsebenen1.
 */
public class Tabelle_Berufskolleg_Berufsebenen1 extends SchemaTabelle {

	/** Die Definition der Tabellenspalte ID */
	public SchemaTabelleSpalte col_ID = add("ID", SchemaDatentypen.BIGINT, true)
		.setNotNull()
		.setJavaComment("ID der Berufsebene ");

	/** Die Definition der Tabellenspalte Kuerzel */
	public SchemaTabelleSpalte col_Kuerzel = add("Kuerzel", SchemaDatentypen.VARCHAR, false).setDatenlaenge(10)
		.setNotNull()
		.setJavaComment("Kürzel der Berufsebene");

	/** Die Definition der Tabellenspalte Bezeichnung */
	public SchemaTabelleSpalte col_Bezeichnung = add("Bezeichnung", SchemaDatentypen.VARCHAR, false).setDatenlaenge(255)
		.setNotNull()
		.setJavaComment("Bezeichnung der Berufsebene");

	/** Die Definition der Tabellenspalte gueltigVon */
	public SchemaTabelleSpalte col_gueltigVon = add("gueltigVon", SchemaDatentypen.INT, false)
		.setJavaComment("Gibt die Gültigkeit ab welchem Schuljahr an");

	/** Die Definition der Tabellenspalte gueltigBis */
	public SchemaTabelleSpalte col_gueltigBis = add("gueltigBis", SchemaDatentypen.INT, false)
		.setJavaComment("Gibt die Gültigkeit bis zu welchem Schuljahr an");


	/**
	 * Erstellt die Schema-Defintion für die Tabelle Berufskolleg_Berufsebenen1.
	 */
	public Tabelle_Berufskolleg_Berufsebenen1() {
		super("Berufskolleg_Berufsebenen1", SchemaRevisionen.REV_0);
		setMigrate(false);
		setImportExport(false);
		setJavaSubPackage("schild.schule");
		setJavaClassName("DTOBerufskollegBerufsebenen1");
		setJavaComment("Informationen Berufsebenen der Ebene 1 des Berufskollegs");
	}

}