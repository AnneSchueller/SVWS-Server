package de.nrw.schule.svws.core.adt.map;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Vector;

import jakarta.validation.constraints.NotNull;

/**
 * Diese Klasse implementiert eine Sub-Map für einen AVL-Baum der Klasse {@link AVLMap}. Fast alle Methodenaufrufe
 * werden zusammen mit dem {@link AVLMapIntervall} dieser {@link AVLMapSubMap} an die {@link AVLMap} delegiert.
 * 
 * @author Benjamin A. Bartsch
 * @author Thomas Bachran
 * 
 * @param <K> Der Typ der Schlüssel-Werte.
 * @param <V> Der Typ der zugeordneten Werte.
 */
public class AVLMapSubMap<@NotNull K, @NotNull V> implements NavigableMap<@NotNull K, @NotNull V> {

	/**
	 * Die {@link AVLMap} auf der diese Sup-Map operiert.
	 */
	private final @NotNull AVLMap<@NotNull K, @NotNull V> _par;

	/**
	 * Das {@link AVLMapIntervall} auf das sich diese Sub-Map bezieht.
	 */
	private final @NotNull AVLMapIntervall<@NotNull K> _iv;

	/**
	 * Falls TRUE wird die {@link AVLMap} aufsteigend, andernfalls absteigend interpretiert.
	 */
	private boolean _asc;

	/**
	 * Erstellt eine neue Sub-Map relativ zur übergebenen {@link AVLMap}.
	 * 
	 * @param parent    Die {@link AVLMap} auf der diese Sup-Map operiert.
	 * @param intervall Das {@link AVLMapIntervall} auf das sich diese Sub-Map bezieht.
	 * @param asc       Falls TRUE wird die {@link AVLMap} aufsteigend, andernfalls absteigend interpretiert.
	 */
	AVLMapSubMap(@NotNull AVLMap<@NotNull K, @NotNull V> parent, @NotNull AVLMapIntervall<@NotNull K> intervall,
			boolean asc) {
		_par = parent;
		_iv = intervall;
		_asc = asc;
	}

	// ########################################################################
	// ############################# PUBLIC ###################################
	// ########################################################################

	@Override
	public @NotNull String toString() {
		String s = "";
		for (Entry<K, V> e : entrySet())
			s += (s.length() == 0 ? "" : ", ") + e;
		return "Entries = [" + s + "], iv = " + _iv + ", asc = " + _asc;
	}

	@Override
	public boolean equals(@NotNull Object o) {
		if (o == this)
			return true;

		if (o instanceof Map<?, ?> == false)
			return false;

		Map<?, ?> mapO = (Map<?, ?>) o;

		if (mapO.size() != size())
			return false;

		// Da SIZE identisch ist, reicht es die KEYS in dieser Map
		// mit dem Mapping in mapO zu überprüfen.
		for (@NotNull
		Entry<@NotNull K, @NotNull V> e : entrySet())
			if (e.getValue().equals(mapO.get(e.getKey())) == false)
				return false;

		return true;
	}

	@Override
	public int hashCode() { // code adapted TreeMap
		int h = 0;
		for (Entry<K, V> entry : entrySet())
			h += entry.hashCode();
		return h;
	}

	@Override
	public @NotNull Comparator<@NotNull K> comparator() {
		return _par.bcGetComparator(_iv);
	}

	@Override
	public @NotNull K firstKey() {
		return _asc ? _par.bcGetFirstKeyOrException(_iv) : _par.bcGetLastKeyOrException(_iv);
	}

	@Override
	public @NotNull K lastKey() {
		return _asc ? _par.bcGetLastKeyOrException(_iv) : _par.bcGetFirstKeyOrException(_iv);
	}

	@Override
	public @NotNull Set<@NotNull K> keySet() {
		return new AVLMapSubKeySet<>(this);
	}

	@Override
	public @NotNull Collection<@NotNull V> values() {
		return new AVLMapSubCollection<>(this);
	}

	@Override
	public @NotNull Set<@NotNull Entry<@NotNull K, @NotNull V>> entrySet() {
		return new AVLMapSubEntrySet<>(this);
	}

	@Override
	public int size() {
		return _par.bcGetSize(_iv);
	}

	@Override
	public boolean isEmpty() {
		return _par.bcIsEmpty(_iv);
	}

	@Override
	public boolean containsKey(@NotNull Object key) {
		return _par.bcContainsKey(key, _iv);
	}

	@Override
	public boolean containsValue(@NotNull Object value) {
		return _par.bcContainsValue(value, _iv);
	}

	@Override
	public V get(@NotNull Object key) { // return NULL erlaubt.
		return _par.bcGetValueOfKeyOrNull(key, _iv);
	}

	@Override
	public V put(@NotNull K key, @NotNull V value) { // return NULL erlaubt.
		return _par.bcAddEntryReturnOldValueOrNull(key, value, _iv);
	}

	@Override
	public V remove(@NotNull Object key) { // return NULL erlaubt.
		return _par.bcRemoveKeyReturnOldValueOrNull(key, _iv);
	}

	@Override
	public void putAll(@NotNull Map<? extends @NotNull K, ? extends @NotNull V> map) {
		_par.bcAddAllEntriesOfMap(map, _iv);
	}

	@Override
	public void clear() {
		Iterator<Entry<K, V>> iter = this.bcGetSubEntrySetIterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
	}

	@Override
	public Entry<@NotNull K, @NotNull V> lowerEntry(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetLowerEntryOrNull(key, _iv) : _par.bcGetHigherEntryOrNull(key, _iv);
	}

	@Override
	public K lowerKey(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetLowerKeyOrNull(key, _iv) : _par.bcGetHigherKeyOrNull(key, _iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> floorEntry(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetFloorEntryOrNull(key, _iv) : _par.bcGetCeilingEntryOrNull(key, _iv);
	}

	@Override
	public K floorKey(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetFloorKeyOrNull(key, _iv) : _par.bcGetCeilingKeyOrNull(key, _iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> ceilingEntry(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetCeilingEntryOrNull(key, _iv) : _par.bcGetFloorEntryOrNull(key, _iv);
	}

	@Override
	public K ceilingKey(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetCeilingKeyOrNull(key, _iv) : _par.bcGetFloorKeyOrNull(key, _iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> higherEntry(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetHigherEntryOrNull(key, _iv) : _par.bcGetLowerEntryOrNull(key, _iv);
	}

	@Override
	public K higherKey(@NotNull K key) { // return NULL erlaubt.
		return _asc ? _par.bcGetHigherKeyOrNull(key, _iv) : _par.bcGetLowerKeyOrNull(key, _iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> firstEntry() { // return NULL erlaubt.
		return _asc ? _par.bcGetFirstEntryOrNull(_iv) : _par.bcGetLastEntryOrNull(_iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> lastEntry() { // return NULL erlaubt.
		return _asc ? _par.bcGetLastEntryOrNull(_iv) : _par.bcGetFirstEntryOrNull(_iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> pollFirstEntry() { // return NULL erlaubt.
		return _asc ? _par.bcPollFirstEntryOrNull(_iv) : _par.bcPollLastEntryOrNull(_iv);
	}

	@Override
	public Entry<@NotNull K, @NotNull V> pollLastEntry() { // return NULL erlaubt.
		return _asc ? _par.bcPollLastEntryOrNull(_iv) : _par.bcPollFirstEntryOrNull(_iv);
	}

	@Override
	public @NotNull NavigableMap<@NotNull K, @NotNull V> descendingMap() {
		return new AVLMapSubMap<>(_par, _iv, !_asc);
	}

	@Override
	public @NotNull NavigableSet<@NotNull K> navigableKeySet() {
		return new AVLMapSubKeySet<>(this);
	}

	@Override
	public @NotNull NavigableSet<@NotNull K> descendingKeySet() {
		return new AVLMapSubKeySet<>(new AVLMapSubMap<>(_par, _iv, !_asc));
	}

	@Override
	public @NotNull NavigableMap<@NotNull K, @NotNull V> subMap(@NotNull K fromKey, boolean fromInclusive,
			@NotNull K toKey, boolean toInclusive) {
		return _createMap(fromKey, fromInclusive, toKey, toInclusive, _asc);
	}

	@Override
	public @NotNull NavigableMap<@NotNull K, @NotNull V> headMap(@NotNull K toKey, boolean inclusive) {
		return _createMap(_iv.from, _iv.fromInc, toKey, inclusive, _asc);
	}

	@Override
	public @NotNull NavigableMap<@NotNull K, @NotNull V> tailMap(@NotNull K fromKey, boolean inclusive) {
		return _createMap(fromKey, inclusive, _iv.to, _iv.toInc, _asc);
	}

	@Override
	public @NotNull SortedMap<@NotNull K, @NotNull V> subMap(@NotNull K fromKey, @NotNull K toKey) {
		return _createMap(fromKey, true, toKey, false, _asc);
	}

	@Override
	public @NotNull SortedMap<@NotNull K, @NotNull V> headMap(@NotNull K toKey) {
		return _createMap(_iv.from, _iv.fromInc, toKey, false, _asc);
	}

	@Override
	public @NotNull SortedMap<@NotNull K, @NotNull V> tailMap(@NotNull K fromKey) {
		return _createMap(fromKey, true, _iv.to, _iv.toInc, _asc);
	}

	// ########################################################################
	// ######################### PROTECTED ####################################
	// ########################################################################

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#add(Object)}. Fügt einen Schlüssel (Key) dieser Datenstruktur hinzu.
	 * 
	 * @param e Der einzufügende Schlüssel (Key).
	 * 
	 * @return TRUE, falls der Schlüssel (Key) noch nicht existierte, sonst FALSE.
	 */
	boolean bcAddKey(@NotNull K e) {
		return _par.bcAddKey(e, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#addAll(Collection)}. Fügt alle Schlüssel (Keys) der Collection dieser
	 * Datenstruktur hinzu.
	 * 
	 * @param c Die Collection mit den einzufügenden Schlüsseln (Keys).
	 * 
	 * @return TRUE, falls mindestens ein Schlüssel (Key) noch nicht existierte und somit hinzugefügt wurde.
	 */
	boolean bcAddAllKeys(@NotNull Collection<? extends @NotNull K> c) {
		return _par.bcAddAllKeys(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#add(java.util.Map.Entry)}. Fügt ein Entry der Datenstruktur hinzu.
	 * 
	 * @param e Das einzufügende Entry.
	 * 
	 * @return TRUE, falls das Entry (e.getKey(), e.getValue()) neu war und somit hinzugefügt wurde.
	 */
	boolean bcAddEntryReturnBool(@NotNull Entry<@NotNull K, @NotNull V> e) {
		return _par.bcAddEntryReturnBool(e, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#addAll(Collection)}. Fügt alle Entries der Collection dieser
	 * Datenstruktur hinzu.
	 * 
	 * @param c Die Collection mit den einzufügenden Entries.
	 * 
	 * @return TRUE, falls mindestens ein Entry neu war und somit hinzugefügt wurde.
	 */
	boolean bcAddAllEntries(@NotNull Collection<? extends @NotNull Entry<@NotNull K, @NotNull V>> c) {
		return _par.bcAddAllEntries(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#containsAll(Collection)}. Überprüft, ob alle Schlüssel (Keys) der
	 * Collection in dieser Datenstruktur existieren.
	 * 
	 * @param c Die Collection mit allen Schlüsseln (Keys) welche überprüft werden sollen.
	 * 
	 * @return TRUE, falls alle Schlüssel (Keys) der Collection in dieser Datenstruktur existieren.
	 */
	boolean bcContainsAllKeys(@NotNull Collection<@NotNull ?> c) {
		return _par.bcContainsAllKeys(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#contains(Object)}. Überprüft, ob das übergebene Entry in dieser
	 * Datenstruktur existiert.
	 * 
	 * @param o Das Entry (Schlüssel-Wert-Paar) nach dem gesucht wird.
	 * 
	 * @return TRUE, falls das übergebene Entry bereits in dieser Datenstruktur existiert.
	 */
	boolean bcContainsEntry(@NotNull Object o) {
		return _par.bcContainsEntry(o, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#containsAll(Collection)}. Überprüft, ob alle Entries der Collection
	 * in dieser Datenstruktur existieren.
	 * 
	 * @param c Die Collection mit den Entries welche überprüft werden sollen.
	 * 
	 * @return TRUE, falls alle Entries in dieser Datenstruktur existieren.
	 */
	boolean bcContainsAllEntries(@NotNull Collection<@NotNull ?> c) {
		return _par.bcContainsAllEntries(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubCollection#containsAll(Collection)}. Überprüft, ob alle Werte (Values) aus
	 * der Collection in dieser Datenstruktur vorkommen. Diese Methode sollte NICHT verwendet werden, da sie
	 * quadratische Laufzeit hat.
	 * 
	 * @param c Die Collection deren Werte (Values) überprüft werden sollen.
	 * 
	 * @return TRUE, falls alle Werte (Values) der Collection in dieser Datenstruktur existieren.
	 * 
	 */
	boolean bcContainsAllValues(@NotNull Collection<@NotNull ?> c) {
		return _par.bcContainsAllValues(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#remove(Object)}. Entfernt einen Schlüssel (Key) aus dieser
	 * Datenstruktur.
	 * 
	 * @param o Der Schlüssel (Key) der entfernt werden soll.
	 * 
	 * @return TRUE, falls der Schlüssel existierte und somit entfernt wurde.
	 */
	boolean bcRemoveKeyReturnBool(@NotNull Object o) {
		return _par.bcRemoveKeyReturnBool(o, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#removeAll(Collection)}. Entfernt alle Schlüssel (Keys) aus dieser
	 * Datenstruktur.
	 * 
	 * @param c Die Collection mit allen Schlüsseln (Keys) die entfernt werden sollen.
	 * 
	 * @return TRUE, falls mindestens ein Schlüssel (Key) entfernt wurde.
	 */
	boolean bcRemoveAllKeys(@NotNull Collection<@NotNull ?> c) {
		return _par.bcRemoveAllKeys(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#remove(Object)}. Entfernt das Entry aus dieser Datenstruktur.
	 * 
	 * @param o Das Entry, welches entfernt werden soll.
	 * 
	 * @return TRUE, falls das Entry in der Datenstruktur existierte und somit entfernt wurde.
	 */
	boolean bcRemoveEntry(@NotNull Object o) {
		return _par.bcRemoveEntry(o, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#removeAll(Collection)}. Entfernt alle Entries der Collection aus
	 * dieser Datenstruktur.
	 * 
	 * @param c Die Collection mit den Entries, welche entfernt werden sollen.
	 * 
	 * @return TRUE, falls mindestens ein Entry entfernt wurde.
	 */
	boolean bcRemoveAllEntries(@NotNull Collection<@NotNull ?> c) {
		return _par.bcRemoveAllEntries(c, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#pollFirst()}. Entfernt und liefert den ersten Schlüssel (Key) dieser
	 * Datenstruktur. Dabei wird beachtet, ob diese Sub-Map aufsteigend oder absteigend zu interpretieren ist.
	 * 
	 * @return Entfernt und liefert den ersten Schlüssel (Key) dieser Datenstruktur falls vorhanden, andernfalls NULL.
	 */
	K bcPollFirstKeyOrNull() {
		return _asc ? _par.bcPollFirstKeyOrNull(_iv) : _par.bcPollLastKeyOrNull(_iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#pollLast()}. Entfernt und liefert den letzten Schlüssel (Key) dieser
	 * Datenstruktur. Dabei wird beachtet, ob diese Sub-Map aufsteigend oder absteigend zu interpretieren ist.
	 * 
	 * @return Entfernt und liefert den letzten Schlüssel (Key) dieser Datenstruktur falls vorhanden, andernfalls NULL.
	 */
	K bcPollLastKeyOrNull() {
		return _asc ? _par.bcPollLastKeyOrNull(_iv) : _par.bcPollFirstKeyOrNull(_iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#retainAll(Collection)}. Entfernt alle Schlüssel (Keys) aus dieser
	 * Datenstruktur, außer sie sind in der Collection enthalten.
	 * 
	 * @param c Die Collection deren Schlüssel (Keys) nicht entfernt werden dürfen.
	 * 
	 * @return TRUE, falls mindestens ein Schlüssel (Key) entfernt wurde.
	 */
	@SuppressWarnings("unchecked")
	boolean bcRetainAllKeys(@NotNull Collection<@NotNull ?> c) {
		// Wandle die Collection in eine Map um, damit der Zugriff schnell ist.
		// Dies ist erlaubt, da die Schlüssel eine natürliche Ordnung aufweisen.
		@NotNull
		AVLMap<@NotNull K, @NotNull K> mapRetain = new AVLMap<>();
		for (@NotNull
		Object obj : c) {
			@NotNull
			K key = (@NotNull K) obj;
			mapRetain.put(key, key);
		}

		// Iteriere und lösche falls nötig...
		boolean changed = false;
		Iterator<K> iterOfKeys = bcGetSubKeySetIterator();
		while (iterOfKeys.hasNext()) {
			K key = iterOfKeys.next();
			if (mapRetain.containsKey(key) == false) {
				iterOfKeys.remove();
				changed = true;
			}
		}
		return changed;
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#retainAll(Collection)}. Entfernt alle Entries aus dieser
	 * Datenstruktur, außer sie sind in der Collection enthalten.
	 * 
	 * @param c Die Collection deren Entries nicht entfernt werden dürfen.
	 * 
	 * @return TRUE, falls mindestens ein Entry entfernt wurde.
	 */
	@SuppressWarnings("unchecked")
	boolean bcRetainAllEntries(@NotNull Collection<@NotNull ?> c) {
		// Vorsicht: c könnte verschiedene Entries mit dem selben KEY haben.

		// Sammle alle Entries, die bleiben sollen.
		@NotNull
		AVLMap<@NotNull K, @NotNull V> mapSave = new AVLMap<>();

		@NotNull
		Set<@NotNull Entry<@NotNull K, @NotNull V>> setSave = mapSave.entrySet();
		for (@NotNull
		Object o : c)
			if (_par.bcContainsEntry(o, _iv))
				setSave.add((@NotNull Entry<@NotNull K, @NotNull V>) o);

		// Iteriere und lösche falls nötig...
		boolean changed = false;
		Iterator<Entry<K, V>> iterOfEntries = bcGetSubEntrySetIterator();
		while (iterOfEntries.hasNext())
			if (setSave.contains(iterOfEntries.next()) == false) {
				iterOfEntries.remove();
				changed = true;
			}

		return changed;
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubCollectionIterator} und {@link AVLMapSubKeySetIterator}. Liefert das erste
	 * Entry als {@link AVLMapNode}, um über diese Datenstruktur zu iterieren. Dabei wird beachtet, ob diese Sub-Map
	 * aufsteigend oder absteigend zu interpretieren ist.
	 * 
	 * @return Das erste Entry als {@link AVLMapNode} dieser Datenstruktur.
	 */
	AVLMapNode<@NotNull K, @NotNull V> bcGetFirstEntryAsNode() { // return NULL erlaubt.
		return _asc ? _par.bcGetFirstEntryOrNull(_iv) : _par.bcGetLastEntryOrNull(_iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubCollectionIterator} und {@link AVLMapSubKeySetIterator}. Liefert das nächste
	 * Entry relativ zu einem übergebenen Entry. Dabei wird beachtet, ob diese Sub-Map aufsteigend oder absteigend zu
	 * interpretieren ist.
	 * 
	 * @param node Das Entry dessen Nachfolger verlangt wird.
	 * 
	 * @return Das nächste Entry relativ zu einem übergebenen Entry.
	 */
	AVLMapNode<@NotNull K, @NotNull V> bcGetNextEntryOrNull(@NotNull AVLMapNode<@NotNull K, @NotNull V> node) {
		return _asc ? _par.bcGetNextEntryOrNull(node, _iv) : _par.bcGetPrevEntryOrNull(node, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#floor(Object)}. Liefert den größten Schlüssel (Key) welcher kleiner
	 * oder gleich dem übergebenen Schlüssel (Key) ist. Somit der selbe Schlüssel (Key) falls vorhanden, andernfalls den
	 * Vorgänger-Schlüssel (Key) falls vorhanden, andernfalls NULL. Dabei wird beachtet, ob diese Sub-Map aufsteigend
	 * oder absteigend zu interpretieren ist.
	 * 
	 * @param e Der Schlüssel (Key) der gesucht wird bzw. sein Vorgänger-Schlüssel.
	 * 
	 * @return Den selben Schlüssel (Key) falls vorhanden, andernfalls sein Vorgänger-Schlüssel falls vorhanden,
	 *         andernfalls NULL.
	 */
	K bcGetFloorKeyOrNull(@NotNull K e) {
		return _asc ? _par.bcGetFloorKeyOrNull(e, _iv) : _par.bcGetCeilingKeyOrNull(e, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#ceiling(Object)}. Liefert den kleinsten Schlüssel (Key) welcher größer
	 * oder gleich dem übergebenen Schlüssel (Key) ist. Somit der selbe Schlüssel (Key) falls vorhanden, andernfalls
	 * sein Nachfolger-Schlüssel (Key) falls vorhanden, andernfalls NULL. Dabei wird beachtet, ob diese Sub-Map
	 * aufsteigend oder absteigend zu interpretieren ist.
	 * 
	 * @param e Der Schlüssel (Key) der gesucht wird bzw. sein Nachfolger-Schlüssel.
	 * 
	 * @return Den selben Schlüssel (Key) falls vorhanden, andernfalls sein Nachfolger-Schlüssel falls vorhanden,
	 *         andernfalls NULL.
	 */
	K bcGetCeilingKeyOrNull(@NotNull K e) {
		return _asc ? _par.bcGetCeilingKeyOrNull(e, _iv) : _par.bcGetFloorKeyOrNull(e, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#lower(Object)}. Liefert den größten Schlüssel (Key) welcher kleiner
	 * ist als der übergebene Schlüssel (Key), somit den Vorgänger-Schlüssel des Schlüssels (Key). Dabei wird beachtet,
	 * ob diese Sub-Map aufsteigend oder absteigend zu interpretieren ist.
	 * 
	 * @param e Der Schlüssel (Key) dessen Vorgänger gesucht wird.
	 * 
	 * @return Den Vorgänger-Schlüssel des übergebenen Schlüssels (Key) falls vorhanden, sonst NULL.
	 */
	K bcGetLowerKeyOrNull(@NotNull K e) {
		return _asc ? _par.bcGetLowerKeyOrNull(e, _iv) : _par.bcGetHigherKeyOrNull(e, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#higher(Object)}. Liefert den kleinsten Schlüssel (Key) welcher größer
	 * ist als der übergebene Schlüssel (Key), somit den Nachfolger-Schlüssel des übergebenen Schlüssels (Key). Dabei
	 * wird beachtet, ob diese Sub-Map aufsteigend oder absteigend zu interpretieren ist.
	 * 
	 * @param e Der Schlüssel (Key) dessen Nachfolger-Schlüssel gesucht wird.
	 * 
	 * @return Den Nachfolger-Schlüssel des übergebenen Schlüssels (Key) falls vorhanden, sonst NULL.
	 */
	K bcGetHigherKeyOrNull(@NotNull K e) {
		return _asc ? _par.bcGetHigherKeyOrNull(e, _iv) : _par.bcGetLowerKeyOrNull(e, _iv);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#toArray()} und {@link AVLMapSubKeySet#toArray(Object[])}. Liefert
	 * einen {@link Vector} der alle Schlüssel (Keys) dieser Sub-Map beinhaltet.
	 * 
	 * @return Ein {@link Vector} der alle Schlüssel (Keys) dieser Sub-Map beinhaltet.
	 */
	@NotNull
	Vector<K> bcGetVectorOfKeys() {
		Vector<K> v = new Vector<>();
		Iterator<K> iter = navigableKeySet().iterator();
		while (iter.hasNext())
			v.add(iter.next());
		return v;
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubCollection#toArray()} und {@link AVLMapSubCollection#toArray(Object[])}.
	 * Liefert einen {@link Vector} der alle Werte (Values) dieser Sub-Map beinhaltet.
	 * 
	 * @return Ein {@link Vector} der alle Werte (Values) dieser Sub-Map beinhaltet.
	 */
	@NotNull
	Vector<V> bcGetVectorOfValues() {
		Vector<V> v = new Vector<>();
		Iterator<V> iter = values().iterator();
		while (iter.hasNext())
			v.add(iter.next());
		return v;
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#toArray()} und {@link AVLMapSubEntrySet#toArray(Object[])}. Liefert
	 * einen {@link Vector} der alle Entries dieser Sub-Map beinhaltet.
	 * 
	 * @return Ein {@link Vector} der alle Entries dieser Sub-Map beinhaltet.
	 */
	@NotNull
	Vector<Entry<K, V>> bcGetVectorOfEntries() {
		Vector<Entry<K, V>> v = new Vector<>();
		Iterator<Entry<K, V>> iter = entrySet().iterator();
		while (iter.hasNext())
			v.add(iter.next());
		return v;
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubKeySet#iterator()}. Liefert einen {@link Iterator} von Schlüsseln (Keys)
	 * relativ zu dieser Sub-Map.
	 * 
	 * @return Einen {@link Iterator} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	Iterator<@NotNull K> bcGetSubKeySetIterator() {
		return new AVLMapSubKeySetIterator<>(this);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubCollection#iterator()}. Liefert einen {@link Iterator} von Werten (Values)
	 * relativ zu dieser Sub-Map.
	 * 
	 * @return Einen {@link Iterator} von Werten (Values) relativ zu dieser Sub-Map.
	 */
	@NotNull
	Iterator<@NotNull V> bcGetSubCollectionIterator() {
		return new AVLMapSubCollectionIterator<>(this);
	}

	/**
	 * Wird aufgerufen von {@link AVLMapSubEntrySet#iterator()}. Liefert einen {@link Iterator} von Entries relativ zu
	 * dieser Sub-Map.
	 * 
	 * @return Ein {@link Iterator} von Entries relativ zu dieser Sub-Map.
	 */
	@NotNull
	Iterator<@NotNull Entry<@NotNull K, @NotNull V>> bcGetSubEntrySetIterator() {
		return new AVLMapSubEntrySetIterator<>(this);
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#descendingSet()}. Liefert ein {@link NavigableSet} von Schlüsseln
	 * (Keys) relativ zu dieser <strong>absteigenden</strong> Sub-Map.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser <strong>absteigenden</strong> Sub-Map.
	 */
	@NotNull
	NavigableSet<@NotNull K> bcGetSubKeySetDescending() {
		return new AVLMapSubKeySet<>(new AVLMapSubMap<>(_par, _iv, !_asc));
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#descendingIterator()}. Liefert einen {@link Iterator} von
	 * Schlüsseln (Keys) relativ zu dieser <strong>absteigenden</strong> Sub-Map.
	 * 
	 * @return Ein {@link Iterator} von Schlüsseln (Keys) relativ zu dieser <strong>absteigenden</strong> Sub-Map.
	 */
	@NotNull
	Iterator<@NotNull K> bcGetSubKeySetDescendingIterator() {
		return new AVLMapSubKeySetIterator<>(new AVLMapSubMap<>(_par, _iv, !_asc));
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#subSet(Object, boolean, Object, boolean)}. Liefert ein
	 * {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 * 
	 * @param fromElement   Die linke (von) Intervallsgrenze.
	 * @param fromInclusive Gibt an, ob die linke (von) Intervallsgrenze inklusive ist.
	 * @param toElement     Die rechte (bis) Intervallsgrenze.
	 * @param toInclusive   Gibt an, ob die rechte (bis) Intervallsgrenze inklusive ist.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	NavigableSet<@NotNull K> bcGetSubKeySet(@NotNull K fromElement, boolean fromInclusive, @NotNull K toElement,
			boolean toInclusive) {
		return _createSet(fromElement, fromInclusive, toElement, toInclusive, _asc);
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#headSet(Object, boolean)}. Liefert ein {@link NavigableSet} von
	 * Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 * 
	 * @param toElement Die rechte (bis) Intervallsgrenze.
	 * @param inclusive Gibt an, ob die rechte (bis) Intervallsgrenze inklusive ist.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	NavigableSet<@NotNull K> bcGetSubKeyHeadSet(@NotNull K toElement, boolean inclusive) {
		return _createSet(_iv.from, _iv.fromInc, toElement, inclusive, _asc);
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#tailSet(Object, boolean)}. Liefert ein {@link NavigableSet} von
	 * Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 * 
	 * @param fromElement Die linke (von) Intervallsgrenze.
	 * @param inclusive   Gibt an, ob die linke (von) Intervallsgrenze inklusive ist.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	NavigableSet<@NotNull K> bcGetSubKeyTailSet(@NotNull K fromElement, boolean inclusive) {
		return _createSet(fromElement, inclusive, _iv.to, _iv.toInc, _asc);
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#subSet(Object, Object)}. Liefert ein {@link NavigableSet} von
	 * Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 * 
	 * Äquivalent zu {@link #bcGetSubKeySet(Object, boolean, Object, boolean)} mit den Werten (fromElement, true,
	 * toElement, false).
	 * 
	 * @param fromElement Die linke (von) Intervallsgrenze <strong>inklusive</strong>.
	 * @param toElement   Die rechte (bis) Intervallsgrenze <strong>exklusive</strong>.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	SortedSet<@NotNull K> bcGetSubKeySet(@NotNull K fromElement, @NotNull K toElement) {
		return _createSet(fromElement, true, toElement, false, _asc);
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#headSet(Object)}. Liefert ein {@link NavigableSet} von Schlüsseln
	 * (Keys) relativ zu dieser Sub-Map.
	 * 
	 * Äquivalent zu {@link #bcGetSubKeyHeadSet(Object, boolean)} mit den Werten (toElement, false).
	 * 
	 * @param toElement Die rechte (bis) Intervallsgrenze <strong>exklusive</strong>.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	SortedSet<@NotNull K> bcGetSubKeyHeadSet(@NotNull K toElement) {
		return _createSet(_iv.from, _iv.fromInc, toElement, false, _asc);
	}

	/**
	 * Wird aufgerufen und von {@link AVLMapSubKeySet#tailSet(Object)}. Liefert ein {@link NavigableSet} von Schlüsseln
	 * (Keys) relativ zu dieser Sub-Map.
	 * 
	 * Äquivalent zu {@link #bcGetSubKeyTailSet(Object, boolean)} mit den Werten (fromElement, true).
	 * 
	 * @param fromElement Die linke (von) Intervallsgrenze <strong>inklusive</strong>.
	 * 
	 * @return Ein {@link NavigableSet} von Schlüsseln (Keys) relativ zu dieser Sub-Map.
	 */
	@NotNull
	SortedSet<@NotNull K> bcGetSubKeyTailSet(@NotNull K fromElement) {
		return _createSet(fromElement, true, _iv.to, _iv.toInc, _asc);
	}

	// ########################################################################
	// ########################## PRIVATE #####################################
	// ########################################################################

	private @NotNull AVLMapSubMap<@NotNull K, @NotNull V> _createMap(@NotNull K from, boolean fromInc, @NotNull K to,
			boolean toInc, boolean asc) {
		if (_par.bcCheckOutOfIntervall(from, fromInc, _iv))
			throw new IllegalArgumentException("FROM-KEY " + from + "/" + fromInc + " nicht in " + _iv);
		if (_par.bcCheckOutOfIntervall(to, toInc, _iv))
			throw new IllegalArgumentException("TO-KEY " + to + "/" + toInc + " nicht in " + _iv);

		return new AVLMapSubMap<>(_par, new AVLMapIntervall<>(from, fromInc, to, toInc), asc);
	}

	private @NotNull AVLMapSubKeySet<@NotNull K, @NotNull V> _createSet(@NotNull K from, boolean fromInc, @NotNull K to,
			boolean toInc, boolean asc) {
		return new AVLMapSubKeySet<>(_createMap(from, fromInc, to, toInc, asc));
	}

}
