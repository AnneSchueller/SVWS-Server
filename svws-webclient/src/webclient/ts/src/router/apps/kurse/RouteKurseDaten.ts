import { RouteNode } from "~/router/RouteNode";
import { RouteKurse, routeKurse } from "~/router/apps/RouteKurse";
import { KursListeEintrag } from "@svws-nrw/svws-core-ts";
import { DataKurs } from "~/apps/kurse/DataKurs";
import { RouteLocationNormalized, RouteParams } from "vue-router";

export class RouteDataKurseDaten {
	item: KursListeEintrag | undefined = undefined;
	daten: DataKurs = new DataKurs();
}

const SKursDaten = () => import("~/components/kurse/daten/SKursDaten.vue");

export class RouteKurseDaten extends RouteNode<RouteDataKurseDaten, RouteKurse> {

	public constructor() {
		super("kurse_daten", "daten", SKursDaten, new RouteDataKurseDaten());
		super.propHandler = (route) => this.getProps(route);
		super.text = "Daten";
	}

	public async update(to: RouteNode<unknown, any>, to_params: RouteParams) {
		if (to_params.id === undefined) {
			this.onSelect(undefined);
		} else {
			const id = parseInt(to_params.id as string);
			this.onSelect(this.parent!.liste.liste.find(s => s.id === id));
		}
	}

	protected onSelect(item?: KursListeEintrag) {
		if (item === this.data.item)
			return;
		if (item === undefined) {
			this.data.item = undefined;
			this.data.daten.unselect();

		} else {
			this.data.item = item;
			this.data.daten.select(this.data.item);
		}
	}

	public getProps(to: RouteLocationNormalized): Record<string, any> {
		return {
			...routeKurse.getProps(to),
			data: this.data.daten
		};
	}

}

export const routeKurseDaten = new RouteKurseDaten();

