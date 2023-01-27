import { ZulaessigesFach, GostStatistikFachwahl } from "@svws-nrw/svws-core-ts";
import { UserConfigKeys } from "~/utils/userconfig/keys";

import { App } from "../BaseApp";

import { mainApp } from "../Main";

/**
 * Diese Klasse enthält den Abiturjahrgangsspezifischen Teil der gymnasialen
 * Oberstufe des SVWS-Client-Applikation. Beim Erstellen der
 * SVWS-Client-Applikation wird ein Objekt dieser Klasse erzeugt und steht unter
 * "this.$app.apps.gost" allen Vue-Komponenten zur Verfügung. Hierdurch ist eine
 * Kommunikation mit der Open-API-Schnittstelle möglich, ohne das die Daten über
 * mehrere Komponenten hinweg aktualisiert werden müssen.
 */
export class Gost extends App {

	/**
	 * Initialisiert die Klasse und holt die relevanten Daten vom Server
	 *
	 * @returns {Promise<void>}
	 */
	public async init(): Promise<void> {
		try {
			const sortierung = await App.api.getClientConfigUserKey(App.schema, 'SVWS-Client', 'gost.kursansicht.sortierung') as UserConfigKeys['gost.kursansicht.sortierung'] | undefined;
			mainApp.config.user_config.set('gost.kursansicht.sortierung', sortierung || 'kursart');
		} catch (e) {
			console.log(e)
		}
	}

	public getBgColor(row: GostStatistikFachwahl): string {
		return ZulaessigesFach.getByKuerzelASD(row.kuerzelStatistik).getHMTLFarbeRGBA(1.0);
	}
}
