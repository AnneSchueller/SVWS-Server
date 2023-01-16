import { computed, WritableComputedRef } from "vue";
import { RouteLocationNormalized } from "vue-router";
import { RouteApp } from "~/router/RouteApp";
import { RouteNodeListView } from "~/router/RouteNodeListView";
import { routeSchuleBenutzer } from "~/router/apps/RouteSchuleBenutzer";
import { routeSchuleBenutzergruppe } from "~/router/apps/RouteSchuleBenutzergruppe";
import { ListNone } from "~/apps/ListNone";

const SSchuleAuswahl = () => import("~/components/schule/SSchuleAuswahl.vue")
const SSchuleApp = () => import("~/components/schule/SSchuleApp.vue")

export class RouteSchule extends RouteNodeListView<ListNone, unknown, unknown, RouteApp> {

	protected defaultChildNode = undefined;

	public constructor() {
		super("schule", "/schule", SSchuleAuswahl, SSchuleApp);
		super.propHandler = (route) => this.getProps(route);
		super.text = "Schule";
		super.setView("liste", SSchuleAuswahl, (route) => this.getNoProps(route));
		super.children = [
		];
		super.menu = [
			// TODO { title: "Schule bearbeiten", value: "schule_bearbeiten" },
			// TODO { title: "Einstellungen", value: "einstellungen" },
			// TODO { title: "Datenaustausch", value: "datenaustausch" },
			// TODO { title: "Datensicherung", value: "datensicherung" },
			// TODO { title: "Schuljahreswechsel", value: "schuljahreswechsel" },
			// TODO { title: "Werkzeuge", value: "werkzeuge" },
			routeSchuleBenutzer,
			routeSchuleBenutzergruppe
			// TODO { title: "Hilfe", value: "hilfe" }
		];
	}

	protected getAuswahlComputedProperty(): WritableComputedRef<undefined> {
		// TODO
		return computed({ get(): undefined { return undefined; }, set(value: undefined) { }});
	}

	public getProps(to: RouteLocationNormalized): Record<string, any> {
		// TODO
		return { };
	}

}

export const routeSchule = new RouteSchule();
