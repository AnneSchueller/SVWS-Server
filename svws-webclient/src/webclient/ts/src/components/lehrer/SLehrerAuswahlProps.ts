import { LehrerListeEintrag, Schuljahresabschnitt } from "@svws-nrw/svws-core-ts";

export interface LehrerAuswahlProps {
		auswahl: LehrerListeEintrag | undefined;
		mapLehrer: Map<number, LehrerListeEintrag>;
		setLehrer: (value: LehrerListeEintrag | undefined) => Promise<void>;
		abschnitte: Map<number, Schuljahresabschnitt>;
		aktAbschnitt: Schuljahresabschnitt;
		setAbschnitt: (abschnitt: Schuljahresabschnitt) => void;
	}