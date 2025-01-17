import { GostJahrgang, JahrgangsListeEintrag, Schuljahresabschnitt } from "@svws-nrw/svws-core-ts";
import { ApiStatus } from "../ApiStatus";

export interface GostAuswahlProps {
	auswahl: GostJahrgang | undefined;
	mapAbiturjahrgaenge: Map<number, GostJahrgang>;
	mapJahrgaengeOhneAbiJahrgang: Map<number, JahrgangsListeEintrag>;
	abschnitte: Map<number, Schuljahresabschnitt>;
	aktAbschnitt: Schuljahresabschnitt;
	apiStatus: ApiStatus;
	setAbschnitt: (abschnitt: Schuljahresabschnitt) => void;
	addAbiturjahrgang: (idJahrgang: number) => Promise<void>;
	setAbiturjahrgang: (abiturjahrgang: GostJahrgang) => Promise<void>
}