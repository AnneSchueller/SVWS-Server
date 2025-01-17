package de.nrw.schule.svws.davapi.data.repos.kalender;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.nrw.schule.svws.core.data.kalender.Kalender;
import de.nrw.schule.svws.core.data.kalender.KalenderEintrag;
import de.nrw.schule.svws.davapi.data.CollectionRessourceQueryParameters;
import de.nrw.schule.svws.davapi.data.IKalenderRepository;

/**
 * Dummy Repository für prototypische Implementierung des
 * {@link IKalenderRepository}
 * 
 */
public class KalenderDummyRepository implements IKalenderRepository {

	private static final String DUMMY_KALENDER_ID = "dummy";

	/**
	 * Konstruktor zum Erstellen des Repositories
	 *
	 */
	public KalenderDummyRepository() {
		// empty default constructor
	}

	@Override
	public Optional<Kalender> getKalenderById(String kalenderId, CollectionRessourceQueryParameters params) {
		Kalender dummyKalender = new Kalender();
		dummyKalender.kalenderTyp = "GENERIERT";
		dummyKalender.beschreibung = "Ein Dummy-Kalender";
		dummyKalender.id = DUMMY_KALENDER_ID;
		dummyKalender.displayname = "Dummy-Kalender";

		KalenderEintrag eintrag123 = new KalenderEintrag();
		eintrag123.uid = "123";
		eintrag123.kalenderId = DUMMY_KALENDER_ID;
		eintrag123.version = UUID.randomUUID().toString();

		KalenderEintrag eintrag456 = new KalenderEintrag();
		eintrag456.uid = "456";
		eintrag456.kalenderId = DUMMY_KALENDER_ID;
		eintrag456.version = UUID.randomUUID().toString();

		dummyKalender.kalenderEintraege.add(eintrag123);
		dummyKalender.kalenderEintraege.add(eintrag456);

		return Optional.of(dummyKalender);
	}

	@Override
	public List<Kalender> getAvailableKalender(CollectionRessourceQueryParameters params) {
		Optional<Kalender> kalenderById = this.getKalenderById(DUMMY_KALENDER_ID, null);
		if (kalenderById.isPresent()) {
			Kalender dummyKalender = kalenderById.get();
			return List.of(dummyKalender);
		}
		return List.of();
	}

	@Override
	public List<String> getDeletedResourceUIDsSince(String kalenderId, Long syncTokenMillis) {
		return new ArrayList<>();
	}
}
