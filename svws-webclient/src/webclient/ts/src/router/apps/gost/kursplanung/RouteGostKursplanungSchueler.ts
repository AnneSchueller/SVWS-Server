import { RouteNode } from "~/router/RouteNode";
import { routeGost } from "~/router/apps/RouteGost";
import { BenutzerKompetenz, GostHalbjahr, Schulform } from "@svws-nrw/svws-core-ts";
import { RouteLocationNormalized, RouteLocationRaw, RouteParams } from "vue-router";
import { RouteGostKursplanung, routeGostKursplanung } from "../RouteGostKursplanung";
import { RouteManager } from "~/router/RouteManager";
import { routeSchuelerLaufbahnplanung } from "~/router/apps/schueler/RouteSchuelerLaufbahnplanung";
import { routeSchuelerIndividualdaten } from "~/router/apps/schueler/RouteSchuelerIndividualdaten";
import { api } from "~/router/Api";
import { GostUmwahlansichtProps } from "~/components/gost/kursplanung/SCardGostUmwahlansichtProps";
import { KursplanungSchuelerAuswahlProps } from "~/components/gost/kursplanung/SGostKursplanungSchuelerAuswahlProps";

const SCardGostUmwahlansicht = () => import("~/components/gost/kursplanung/SCardGostUmwahlansicht.vue");
const SGostKursplanungSchuelerAuswahl = () => import("~/components/gost/kursplanung/SGostKursplanungSchuelerAuswahl.vue");

export class RouteGostKursplanungSchueler extends RouteNode<unknown, RouteGostKursplanung> {

	public constructor() {
		super(Schulform.getMitGymOb(), [ BenutzerKompetenz.KEINE ], "gost.kursplanung.schueler", "schueler/:idschueler(\\d+)?", SCardGostUmwahlansicht);
		super.propHandler = (route) => this.getProps(route);
		super.setView("gost_kursplanung_schueler_auswahl", SGostKursplanungSchuelerAuswahl, (route) => this.getAuswahlProps(route));
		super.text = "Kursplanung - Schüler";
		this.isHidden = (params?: RouteParams) => {
			return this.checkHidden(params);
		}
	}

	public checkHidden(params?: RouteParams) {
		const abiturjahr = params?.abiturjahr === undefined ? undefined : parseInt(params.abiturjahr as string);
		return (abiturjahr === undefined) || (abiturjahr === -1);
	}

	public async beforeEach(to: RouteNode<unknown, any>, to_params: RouteParams, from: RouteNode<unknown, any> | undefined, from_params: RouteParams): Promise<any> {
		if (to_params.abiturjahr instanceof Array || to_params.halbjahr instanceof Array || to_params.idblockung instanceof Array || to_params.idergebnis instanceof Array)
			throw new Error("Fehler: Die Parameter dürfen keine Arrays sein");
		const abiturjahr = to_params.abiturjahr === undefined ? undefined : parseInt(to_params.abiturjahr);
		const halbjahr = (to_params.halbjahr === undefined) ? undefined : GostHalbjahr.fromID(parseInt(to_params.halbjahr)) || undefined;
		const idBlockung = to_params.idblockung === undefined ? undefined : parseInt(to_params.idblockung);
		const idErgebnis = to_params.idergebnis === undefined ? undefined : parseInt(to_params.idergebnis);
		if ((abiturjahr === undefined) || (routeGost.data.auswahl.value !== undefined) && (abiturjahr !== routeGost.data.auswahl.value.abiturjahr))
			return { name: routeGost.name, params: { } };
		if ((halbjahr === undefined) || (idBlockung === undefined))
			return routeGostKursplanung.getRouteHalbjahr(abiturjahr, (halbjahr === undefined) ? GostHalbjahr.EF1.id : halbjahr.id);
		if (idErgebnis === undefined)
			return routeGostKursplanung.getRouteBlockung(abiturjahr, halbjahr.id, idBlockung);
		return true;
	}

	public async enter(to: RouteNode<unknown, any>, to_params: RouteParams) : Promise<any> {
		if (to_params.abiturjahr instanceof Array || to_params.halbjahr instanceof Array || to_params.idblockung instanceof Array || to_params.idergebnis instanceof Array)
			throw new Error("Fehler: Die Parameter dürfen keine Arrays sein");
		const abiturjahr = to_params.abiturjahr === undefined ? undefined : parseInt(to_params.abiturjahr);
		const halbjahr = (to_params.halbjahr === undefined) ? undefined : GostHalbjahr.fromID(parseInt(to_params.halbjahr)) || undefined;
		const idBlockung = to_params.idblockung === undefined ? undefined : parseInt(to_params.idblockung);
		const idErgebnis = to_params.idergebnis === undefined ? undefined : parseInt(to_params.idergebnis);
		if ((abiturjahr === undefined) || (halbjahr === undefined) || (idBlockung === undefined) || (idErgebnis === undefined))
			throw new Error("Fehler: Abiturjahr, Halbjahr und ID der Blockung und des Ergebnisses müssen als Parameter der Route an dieser Stelle vorhanden sein.");
	}

	public async update(to: RouteNode<unknown, any>, to_params: RouteParams) : Promise<any> {
		if (to_params.abiturjahr instanceof Array || to_params.halbjahr instanceof Array || to_params.idblockung instanceof Array || to_params.idergebnis instanceof Array || to_params.idschueler instanceof Array)
			throw new Error("Fehler: Die Parameter dürfen keine Arrays sein");
		// Prüfe nochmals Abiturjahrgang, Halbjahr und ID der Blockung und des Ergebnisses
		const abiturjahr = to_params.abiturjahr === undefined ? undefined : parseInt(to_params.abiturjahr);
		const halbjahr = (to_params.halbjahr === undefined) ? undefined : GostHalbjahr.fromID(parseInt(to_params.halbjahr)) || undefined;
		const idBlockung = to_params.idblockung === undefined ? undefined : parseInt(to_params.idblockung);
		const idErgebnis = to_params.idergebnis === undefined ? undefined : parseInt(to_params.idergebnis);
		if ((abiturjahr === undefined) || (halbjahr === undefined))
			throw new Error("Fehler: Abiturjahr und Halbjahr müssen als Parameter der Route an dieser Stelle vorhanden sein.");
		if ((abiturjahr !== routeGostKursplanung.data.abiturjahr) || (halbjahr !== routeGostKursplanung.data.halbjahr) || (idBlockung === undefined))
			return routeGostKursplanung.getRouteHalbjahr(abiturjahr, halbjahr.id);
		if (idBlockung !== routeGostKursplanung.data.auswahlBlockung.id)
			return routeGostKursplanung.getRouteBlockung(abiturjahr, halbjahr.id, idBlockung);
		if (idErgebnis !== routeGostKursplanung.data.auswahlErgebnis.id) {
			if (idErgebnis === undefined)
				routeGostKursplanung.getRouteBlockung(abiturjahr, halbjahr.id, idBlockung);
			else
				routeGostKursplanung.getRouteErgebnis(abiturjahr, halbjahr.id, idBlockung, idErgebnis);
		}
		const idSchueler = to_params.idschueler === undefined ? undefined : parseInt(to_params.idschueler);
		// ... wurde die ID des Schülers auf undefined setzt, so prüfe, ob die Schülerliste leer ist und wähle ggf. das erste Element aus
		if (idSchueler === undefined) {
			if (routeGostKursplanung.data.mapSchueler.size > 0) {
				const schueler = routeGostKursplanung.data.mapSchueler.values().next().value;
				return this.getRoute(abiturjahr, halbjahr.id, idBlockung, idErgebnis, schueler?.id);
			}
			return;
		}
		// ... wurde die ID des Schülers verändert, merke diesen Schüler
		if ((!routeGostKursplanung.data.hatSchueler) || (routeGostKursplanung.data.auswahlSchueler.id !== idSchueler)) {
			// Setze den neu ausgewählten Schüler-Eintrag
			const schuelerEintrag = routeGostKursplanung.data.mapSchueler.get(idSchueler);
			if (schuelerEintrag === undefined)
				throw new Error("Programmierfehler: Ein Eintrag für die Schüler-ID als Parameter der Route muss an dieser Stelle vorhanden sein.");
			await routeGostKursplanung.data.setAuswahlSchueler(schuelerEintrag);
		}
	}

	public getRoute(abiturjahr: number | undefined, halbjahr: number | undefined, idblockung: number | undefined, idergebnis: number | undefined, idschueler: number | undefined) : RouteLocationRaw {
		if ((abiturjahr === undefined) || (halbjahr === undefined) || (idblockung === undefined) || (idergebnis === undefined))
			throw new Error("Abiturjahr, Halbjahr und die ID der Blockung und des Ergebnisses müssen für diese Route definiert sein.");
		if (idschueler === undefined)
			return { name: this.name, params: { abiturjahr: abiturjahr, halbjahr: halbjahr, idblockung: idblockung, idergebnis: idergebnis }};
		return { name: this.name, params: { abiturjahr: abiturjahr, halbjahr: halbjahr, idblockung: idblockung, idergebnis: idergebnis, idschueler : idschueler }};
	}

	gotoSchuelerIndividualdaten = async (idSchueler: number) => {
		await RouteManager.doRoute(routeSchuelerIndividualdaten.getRoute(idSchueler));
	}

	gotoLaufbahnplanung = async (idSchueler: number) => {
		await RouteManager.doRoute(routeSchuelerLaufbahnplanung.getRoute(idSchueler));
	}

	public getAuswahlProps(to: RouteLocationNormalized): KursplanungSchuelerAuswahlProps {
		return {
			setSchueler: routeGostKursplanung.data.gotoSchueler,
			getErgebnismanager: () => routeGostKursplanung.data.ergebnismanager,
			schueler: routeGostKursplanung.data.auswahlSchueler,
			schuelerFilter: routeGostKursplanung.data.schuelerFilter,
			faecherManager: routeGost.data.faecherManager.value
		}
	}

	public getProps(to: RouteLocationNormalized): GostUmwahlansichtProps {
		return {
			addRegel: routeGostKursplanung.data.addRegel,
			removeRegel: routeGostKursplanung.data.removeRegel,
			updateKursSchuelerZuordnung: routeGostKursplanung.data.updateKursSchuelerZuordnung,
			removeKursSchuelerZuordnung: routeGostKursplanung.data.removeKursSchuelerZuordnung,
			autoKursSchuelerZuordnung:  routeGostKursplanung.data.autoKursSchuelerZuordnung,
			gotoSchueler: this.gotoSchuelerIndividualdaten,
			gotoLaufbahnplanung: this.gotoLaufbahnplanung,
			getDatenmanager: () => routeGostKursplanung.data.datenmanager,
			getErgebnismanager: () => routeGostKursplanung.data.ergebnismanager,
			schueler: routeGostKursplanung.data.auswahlSchueler,
			apiStatus: api.status,
		}
	}

}

export const routeGostKursplanungSchueler = new RouteGostKursplanungSchueler();
