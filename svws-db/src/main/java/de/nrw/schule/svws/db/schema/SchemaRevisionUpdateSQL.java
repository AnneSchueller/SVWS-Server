package de.nrw.schule.svws.db.schema;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import de.nrw.schule.svws.db.DBDriver;

/**
 * Diese Klasse ist die Basisklasse für die Definition von
 * SQL-Befehlen, welche bei einem Update auf die zugehörige
 * SVWS-Datenbank-Revision ausgeführt werden.
 */
public class SchemaRevisionUpdateSQL {

	/** Die Revision der die Befehle zugeordnet sind */
	private final SchemaRevisionen _revision;

	/** Eine Liste mit den erläuternden Kommentaren der SQL-Befehle */
	private final Vector<String> _kommentare = new Vector<>();
	
	/** Eine Liste mit den durch den SQL-Befehl betroffen Tabellen. */
	private final Vector<SchemaTabelle> _tabellen = new Vector<>();
	
	/** Der Vektor mit den SQL-Befehlen in Bezug aud das jeweilige DBMS */
	private final LinkedHashMap<DBDriver, Vector<String>> _sql = new LinkedHashMap<>();


	/**
	 * Erzeugt eine neue Instanz von SQL-Befehlen für Updates
	 * auf die angebene Revision
	 *  
	 * @param revision   die Revision, für welche die Update-Befehle
	 *                   definiert wurden.
	 */
	protected SchemaRevisionUpdateSQL(SchemaRevisionen revision) {
		this._revision = revision;
	}


	/**
	 * Gibt die Revision zurück, welcher die SQL-Befehle zugeordnet sind.
	 * 
	 * @return diw SQL-Befehle
	 */
	public SchemaRevisionen revision() {
		return _revision;
	}

	/**
	 * Gibt die Liste der SQL-Befehle für das angegebene DBMS zurück.
	 * 
	 * @param dbms   das DBMS
	 * 
	 * @return die Liste der SQL-Befehle oder null, falls keine vorhanden sind.
	 */
	public List<String> getSQL(DBDriver dbms) {
		var liste = _sql.get(dbms);
		return liste == null ? Collections.emptyList() : liste;
	}

	/**
	 * Fügt der Liste der SQL-Befehle für das angegebene DBMS
	 * einen weiteren Befehl hinzu.
	 *  
	 * @param dbms   das DBMS
	 * @param sql    der SQL-Befehl
	 */
	private void add(DBDriver dbms, String sql) {
		var liste = _sql.get(dbms);
		if (liste == null) {
			liste = new Vector<>();
			_sql.put(dbms, liste);
		}
		liste.add(sql);
	}

	/**
	 * Fügt den übergebenen SQL-Befehl einheitlich für alle DBMS hinzu.
	 * 
	 * @param kommentar   die Bezeichnung/Kurz-Erläuterung des SQL-Befehls
	 * @param sql         der SQL-Befehl
	 * @param tabellen    die Tabellen, welche durch den SQL-Befehle gelesen oder beschrieben werden.
	 */
	public void add(String kommentar, String sql, SchemaTabelle... tabellen) {
		add(kommentar, sql, null, null, tabellen);
	}
	
	/**
	 * Fügt den übergebenen SQL-Befehl allen DBMS hinzu,
	 * außer dem spezifizierten DBMS. Diesem wird der
	 * danach angegeben Befehl hinzugefügt. 
	 * 
	 * @param kommentar   die Bezeichnung/Kurz-Erläuterung des SQL-Befehls
	 * @param sql         der SQL-Befehl
	 * @param dbms2       das DBMS mit einer Variante
	 * @param sql2        der SQL-Befehl für die Variante
	 * @param tabellen    die Tabellen, welche durch den SQL-Befehle gelesen oder beschrieben werden.
	 */
	public void add(String kommentar, String sql, DBDriver dbms2, String sql2, SchemaTabelle... tabellen) {
		add(kommentar, sql, dbms2, sql2, null, null, tabellen);
	}

	/**
	 * Fügt den übergebenen SQL-Befehl allen DBMS hinzu,
	 * außer den beiden spezifizierten DBMS. Diesen wird 
	 * jeweils der nach dem DBMS angegebene Befehl hinzugefügt. 
	 * 
	 * @param kommentar   der erläuternde Kommentar zum SQL-Befehl
	 * @param sql         der SQL-Befehl
	 * @param dbms2       das erste DBMS mit einer Variante
	 * @param sql2        der SQL-Befehl für die erste Variante
	 * @param dbms3       das zweite DBMS mit einer Variante
	 * @param sql3        der SQL-Befehl für die zweite Variante
	 * @param tabellen    die Tabellen, welche durch den SQL-Befehle gelesen oder beschrieben werden.
	 */
	public void add(String kommentar, String sql, DBDriver dbms2, String sql2, DBDriver dbms3, String sql3, SchemaTabelle... tabellen) {
		_kommentare.add(kommentar);
		_tabellen.addAll(Arrays.asList(tabellen));
		for (DBDriver dbms : DBDriver.values())
			add(dbms, (dbms == dbms2) ? sql2 : (dbms == dbms3) ? sql3 : sql);
	}

	/**
	 * Gibt die Anzahl der SQL-Befehle zurück.
	 * 
	 * @return die Anzahl der SQL-Befehle
	 */
	public int size() {
		return _kommentare.size();
	}

	/**
	 * Gibt den Kommentar zum i-ten SQL-Befehl zurück.
	 * 
	 * @param i   der Index für welchen der Kommentar zum SQL-Befehl 
	 *            zurückgegeben werden soll. 
	 * 
	 * @return der Kommentar
	 */
	public String getKommentar(int i) {
		if ((i < 0) || (i >= _kommentare.size()))
			throw new ArrayIndexOutOfBoundsException();
		return _kommentare.get(i);
	}

	/**
	 * Die Tabellen des Schemas, welche durch den SQL-Befehle 
	 * gelesen oder beschrieben werden.
	 * 
	 * @return die Liste der Tabellen
	 */
	public List<SchemaTabelle> tabellen() {
		return this._tabellen;
	}

	/**
	 * Gibt den i-ten SQL-Befehl für das angegebene DBMS zurück.
	 * 
	 * @param dbms  das DBMS
	 * @param i     der Index des SQL-Befehls 
	 * 
	 * @return der Kommentar
	 */
	public String getSQL(DBDriver dbms, int i) {
		if ((i < 0) || (i >= _kommentare.size()))
			throw new ArrayIndexOutOfBoundsException();
		var liste = getSQL(dbms);
		if (liste == null)
			throw new ArrayIndexOutOfBoundsException();
		return _sql.get(dbms).get(i);
	}

}

