import { KlassenListeEintrag, List, Schuljahresabschnitt } from "@svws-nrw/svws-core-ts";

export interface KlassenAuswahlProps {
		auswahl: KlassenListeEintrag | undefined,
		listKlassen: List<KlassenListeEintrag>;
		abschnitte: List<Schuljahresabschnitt>;
		aktAbschnitt: Schuljahresabschnitt;
		setAbschnitt: (abschnitt: Schuljahresabschnitt) => void;
		setKlasse: (value: KlassenListeEintrag) =>  Promise<void>;
	}