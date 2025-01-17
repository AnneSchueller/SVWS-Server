import { RouteLocationNormalized, RouteLocationRaw, RouteParams } from "vue-router";
import { RouteNode } from "~/router/RouteNode";
import { routeLehrer, RouteLehrer } from "~/router/apps/RouteLehrer";
import { LehrerPersonaldatenProps } from "~/components/lehrer/personaldaten/SLehrerPersonaldatenProps";
import { BenutzerKompetenz, LehrerPersonaldaten, Schulform } from "@svws-nrw/svws-core-ts";

const SLehrerPersonaldaten = () => import("~/components/lehrer/personaldaten/SLehrerPersonaldaten.vue");

export class RouteLehrerPersonaldaten extends RouteNode<unknown, RouteLehrer> {

	public constructor() {
		super(Schulform.values(), [ BenutzerKompetenz.KEINE ], "lehrer_personaldaten", "personaldaten", SLehrerPersonaldaten);
		super.propHandler = (route) => this.getProps(route);
		super.text = "Personaldaten";
	}

	public async update(to: RouteNode<unknown, any>, to_params: RouteParams) : Promise<any> {
		if (routeLehrer.data.auswahl === undefined)
			return routeLehrer.getRoute(undefined);
		if (to_params.id instanceof Array)
			throw new Error("Fehler: Die Parameter der Route dürfen keine Arrays sein");
		const idLehrer = !to_params.id ? undefined : parseInt(to_params.id);
		if ((!routeLehrer.data.hatPersonaldaten) || (idLehrer !== routeLehrer.data.personaldaten.id))
			await routeLehrer.data.loadPersonaldaten();
	}

	public async leave(from: RouteNode<unknown, any>, from_params: RouteParams): Promise<void> {
		await routeLehrer.data.unloadPersonaldaten();
	}

	public getRoute(id: number) : RouteLocationRaw {
		return { name: this.name, params: { id: id }};
	}

	public getProps(to: RouteLocationNormalized): LehrerPersonaldatenProps {
		return {
			patch: routeLehrer.data.patchPersonaldaten,
			personaldaten: routeLehrer.data.hatPersonaldaten ? routeLehrer.data.personaldaten : new LehrerPersonaldaten(),
		};
	}

}

export const routeLehrerPersonaldaten = new RouteLehrerPersonaldaten();
