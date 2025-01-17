import { GostHalbjahr, GostJahrgangsdaten, GostBlockungsdaten, GostBlockungListeneintrag, GostBlockungsdatenManager, GostBlockungsergebnisListeneintrag, List } from "@svws-nrw/svws-core-ts";
import { ApiStatus } from "~/components/ApiStatus";

export interface GostKursplanungAuswahlProps {
	setHalbjahr: (value: GostHalbjahr) => Promise<void>;
	halbjahr: GostHalbjahr;
	jahrgangsdaten: GostJahrgangsdaten | undefined;
	// ... zusätzlich für die Blockungsauswahl
	patchBlockung: (data: Partial<GostBlockungsdaten>, idBlockung: number) => Promise<boolean>;
	addBlockung: () => Promise<void>;
	removeBlockung: () => Promise<void>;
	setAuswahlBlockung: (auswahl: GostBlockungListeneintrag | undefined) => Promise<void>;
	auswahlBlockung: GostBlockungListeneintrag | undefined;
	mapBlockungen: Map<number, GostBlockungListeneintrag>;
	apiStatus: ApiStatus;
	// ... zusätzlich für die Ergebnisauswahl
	getDatenmanager: () => GostBlockungsdatenManager;
	rechneGostBlockung: () => Promise<List<number>>;
	removeErgebnis: (idErgebnis: number) => Promise<void>;
	removeErgebnisse: (ergebnisse: GostBlockungsergebnisListeneintrag[]) => Promise<void>;
	ergebnisZuNeueBlockung: (idErgebnis: number) => Promise<void>;
	setAuswahlErgebnis: (value: GostBlockungsergebnisListeneintrag | undefined) => Promise<void>;
	hatBlockung: boolean;
	auswahlErgebnis: GostBlockungsergebnisListeneintrag | undefined;
}