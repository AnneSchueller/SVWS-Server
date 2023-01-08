import {
	JahrgangsListeEintrag,
	KlassenListeEintrag,
	KursListeEintrag,
	SchuelerListeEintrag,
	SchuelerStatus,
	Schulgliederung
} from "@svws-nrw/svws-core-ts";
import { App } from "../BaseApp";
import { BaseList } from "../BaseList";
import { reactive } from "vue";

/**
 * Das Interface für die Schülerfilterfunktion. Alle hier gesetzten Werte
 * stimmen sich aufeinander ab und zeigen nur noch kombinierte Ergebnisse
 */
interface Filter {
	jahrgang: JahrgangsListeEintrag | undefined;
	kurs: KursListeEintrag | undefined;
	klasse: KlassenListeEintrag | undefined;
	schulgliederung: Schulgliederung | undefined;
	status: Array<SchuelerStatus>;
}

export class ListSchueler extends BaseList<SchuelerListeEintrag, Filter> {
	protected _filter = reactive({
		jahrgang: undefined,
		kurs: undefined,
		klasse: undefined,
		schulgliederung: undefined,
		status: [ SchuelerStatus.AKTIV, SchuelerStatus.EXTERN ]
	}) as Filter;

	public constructor() {
		super();
	}

	public reset_filter() {
		this._filter.jahrgang = undefined;
		this._filter.kurs = undefined;
		this._filter.klasse = undefined;
		this._filter.schulgliederung = undefined;
		this._filter.status = [ SchuelerStatus.AKTIV, SchuelerStatus.EXTERN ];
	}

	/**
	 * Aktualisiert die Liste für die Schülerauswahl
	 *
	 * @returns {Promise<void>}
	 */
	public async update_list(): Promise<void> {
		await super._update_list(() =>
			App.api.getSchuelerFuerAbschnitt(App.schema, App.akt_abschnitt.id)
		);
		this.reset_filter();
		if (!this.ausgewaehlt) {
			this.ausgewaehlt = this.gefiltert[0]
		}
	}

	/**
	 * Diese Methode filtert die Daten der Auswahlliste und wird von dem
	 * AppState-Objekt dieser Klasse beim Laden des Katalogs automatisch aufgerufen.
	 *
	 * @returns Void
	 */
	protected filter_liste(): void {
		this._state.gefiltert = this.liste
			.filter(s => !this.filter.status.length || this.filter.status.map(s => s.bezeichnung).includes(s.status))
			.filter(s => !this.filter.jahrgang || s.jahrgang === this.filter.jahrgang.kuerzel)
			.filter(s => !this.filter.klasse || s.idKlasse === this.filter.klasse.id)
			.filter(s => !this.filter.kurs || s.kurse?.toArray(new Array<number>()).includes(this.filter.kurs.id))
			.filter(s => !this.filter.schulgliederung || s.schulgliederung === this.filter.schulgliederung.daten.kuerzel);
		if (this.ausgewaehlt && !this.gefiltert.includes(this.ausgewaehlt)) this.ausgewaehlt = this.gefiltert[0]
	}

	/**
	 * Aktualisiert die Liste der Blockungsergebnisse
	 *
	 * @returns {Promise<void>}
	 */
	public async on_select(): Promise<void> {
		if (!this._state.ausgewaehlt?.id) 
			return;
	}
}
