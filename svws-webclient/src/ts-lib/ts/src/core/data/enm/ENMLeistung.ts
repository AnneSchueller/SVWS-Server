import { JavaObject, cast_java_lang_Object } from '../../../java/lang/JavaObject';
import { JavaInteger, cast_java_lang_Integer } from '../../../java/lang/JavaInteger';
import { ENMTeilleistung, cast_de_nrw_schule_svws_core_data_enm_ENMTeilleistung } from '../../../core/data/enm/ENMTeilleistung';
import { JavaString, cast_java_lang_String } from '../../../java/lang/JavaString';
import { JavaBoolean, cast_java_lang_Boolean } from '../../../java/lang/JavaBoolean';
import { Vector, cast_java_util_Vector } from '../../../java/util/Vector';

export class ENMLeistung extends JavaObject {

	/**
	 * Die ID der Leistungsdaten des Schülers in der SVWS-DB (z.B. 307956) 
	 */
	public id : number = 0;

	/**
	 * Die eindeutige ID der Lerngruppe, der der Schüler zugeordnet ist. (Klasse oder Kurs wird erst 
	 *  in der Lerngruppe unterschieden!) 
	 */
	public lerngruppenID : number = 0;

	/**
	 * Das Kürzel der Note, die vergeben wurde. 
	 */
	public note : string | null = null;

	/**
	 * Der Zeitstempel der letzten Änderung an der erteilten Note 
	 */
	public tsNote : string | null = null;

	/**
	 * Gibt bei Oberstufenkursen an, ob das Fach schriftlich belegt wurde oder nicht. 
	 */
	public istSchriftlich : boolean | null = null;

	/**
	 * Gibt an, ob es sich um ein Abiturfach handelt (1,2,3 oder 4) oder nicht (null) 
	 */
	public abiturfach : number | null = null;

	/**
	 * Gibt die Anzahl der gesamten Fehlstunden an, sofern diese fachbezogen ermittelt werden. 
	 */
	public fehlstundenGesamt : number | null = null;

	/**
	 * Der Zeitstempel der letzten Änderung an Anzahl der gesamten Fehlstunden an, sofern diese fachbezogen ermittelt werden 
	 */
	public tsFehlstundenGesamt : string | null = null;

	/**
	 * Gibt die Anzahl der unentschuldigten Fehlstunden an, sofern diese fachbezogen ermittelt werden. 
	 */
	public fehlstundenUnentschuldigt : number | null = null;

	/**
	 * Der Zeitstempel der letzten Änderung an Anzahl der unentschuldigten Fehlstunden an, sofern diese fachbezogen ermittelt werden 
	 */
	public tsFehlstundenUnentschuldigt : string | null = null;

	/**
	 * Die fachbezogenen Bemerkungen bzw. das Thema bei Projektkursen 
	 */
	public fachbezogeneBemerkungen : string | null = null;

	/**
	 * Der Zeitstempel der letzten Änderung an Anzahl den fachbezogenen Bemerkungen bzw. dem Thema bei Projektkursen 
	 */
	public tsFachbezogeneBemerkungen : string | null = null;

	/**
	 * Die Kurszuweisung, die auf dem Zeugnis erscheinen soll für den nächsten Kursabschnitt (z.B. E oder G-Kurs, z.B. an der Gesamtschule) 
	 */
	public neueZuweisungKursart : string | null = null;

	/**
	 * Gibt an, ob ein Fach gemahnt wurde oder nicht. 
	 */
	public istGemahnt : boolean | null = null;

	/**
	 * Der Zeitstempel, wann gesetzt wurde, ob die Leistung gemahnt wurde 
	 */
	public tsIstGemahnt : string | null = null;

	/**
	 * Das Mahndatum bei erfolgter Mahnung. 
	 */
	public mahndatum : string | null = null;

	/**
	 * Die Teilleistungen, sofern welche vordefiniert sind. 
	 */
	public teilleistungen : Vector<ENMTeilleistung> = new Vector();


	public constructor() {
		super();
	}

	isTranspiledInstanceOf(name : string): boolean {
		return ['de.nrw.schule.svws.core.data.enm.ENMLeistung'].includes(name);
	}

	public static transpilerFromJSON(json : string): ENMLeistung {
		const obj = JSON.parse(json);
		const result = new ENMLeistung();
		if (typeof obj.id === "undefined")
			 throw new Error('invalid json format, missing attribute id');
		result.id = obj.id;
		if (typeof obj.lerngruppenID === "undefined")
			 throw new Error('invalid json format, missing attribute lerngruppenID');
		result.lerngruppenID = obj.lerngruppenID;
		result.note = typeof obj.note === "undefined" ? null : obj.note === null ? null : obj.note;
		result.tsNote = typeof obj.tsNote === "undefined" ? null : obj.tsNote === null ? null : obj.tsNote;
		result.istSchriftlich = typeof obj.istSchriftlich === "undefined" ? null : obj.istSchriftlich === null ? null : obj.istSchriftlich;
		result.abiturfach = typeof obj.abiturfach === "undefined" ? null : obj.abiturfach === null ? null : obj.abiturfach;
		result.fehlstundenGesamt = typeof obj.fehlstundenGesamt === "undefined" ? null : obj.fehlstundenGesamt === null ? null : obj.fehlstundenGesamt;
		result.tsFehlstundenGesamt = typeof obj.tsFehlstundenGesamt === "undefined" ? null : obj.tsFehlstundenGesamt === null ? null : obj.tsFehlstundenGesamt;
		result.fehlstundenUnentschuldigt = typeof obj.fehlstundenUnentschuldigt === "undefined" ? null : obj.fehlstundenUnentschuldigt === null ? null : obj.fehlstundenUnentschuldigt;
		result.tsFehlstundenUnentschuldigt = typeof obj.tsFehlstundenUnentschuldigt === "undefined" ? null : obj.tsFehlstundenUnentschuldigt === null ? null : obj.tsFehlstundenUnentschuldigt;
		result.fachbezogeneBemerkungen = typeof obj.fachbezogeneBemerkungen === "undefined" ? null : obj.fachbezogeneBemerkungen === null ? null : obj.fachbezogeneBemerkungen;
		result.tsFachbezogeneBemerkungen = typeof obj.tsFachbezogeneBemerkungen === "undefined" ? null : obj.tsFachbezogeneBemerkungen === null ? null : obj.tsFachbezogeneBemerkungen;
		result.neueZuweisungKursart = typeof obj.neueZuweisungKursart === "undefined" ? null : obj.neueZuweisungKursart === null ? null : obj.neueZuweisungKursart;
		result.istGemahnt = typeof obj.istGemahnt === "undefined" ? null : obj.istGemahnt === null ? null : obj.istGemahnt;
		result.tsIstGemahnt = typeof obj.tsIstGemahnt === "undefined" ? null : obj.tsIstGemahnt === null ? null : obj.tsIstGemahnt;
		result.mahndatum = typeof obj.mahndatum === "undefined" ? null : obj.mahndatum === null ? null : obj.mahndatum;
		if (!!obj.teilleistungen) {
			for (let elem of obj.teilleistungen) {
				result.teilleistungen?.add(ENMTeilleistung.transpilerFromJSON(JSON.stringify(elem)));
			}
		}
		return result;
	}

	public static transpilerToJSON(obj : ENMLeistung) : string {
		let result = '{';
		result += '"id" : ' + obj.id + ',';
		result += '"lerngruppenID" : ' + obj.lerngruppenID + ',';
		result += '"note" : ' + ((!obj.note) ? 'null' : '"' + obj.note + '"') + ',';
		result += '"tsNote" : ' + ((!obj.tsNote) ? 'null' : '"' + obj.tsNote + '"') + ',';
		result += '"istSchriftlich" : ' + ((!obj.istSchriftlich) ? 'null' : obj.istSchriftlich) + ',';
		result += '"abiturfach" : ' + ((!obj.abiturfach) ? 'null' : obj.abiturfach) + ',';
		result += '"fehlstundenGesamt" : ' + ((!obj.fehlstundenGesamt) ? 'null' : obj.fehlstundenGesamt) + ',';
		result += '"tsFehlstundenGesamt" : ' + ((!obj.tsFehlstundenGesamt) ? 'null' : '"' + obj.tsFehlstundenGesamt + '"') + ',';
		result += '"fehlstundenUnentschuldigt" : ' + ((!obj.fehlstundenUnentschuldigt) ? 'null' : obj.fehlstundenUnentschuldigt) + ',';
		result += '"tsFehlstundenUnentschuldigt" : ' + ((!obj.tsFehlstundenUnentschuldigt) ? 'null' : '"' + obj.tsFehlstundenUnentschuldigt + '"') + ',';
		result += '"fachbezogeneBemerkungen" : ' + ((!obj.fachbezogeneBemerkungen) ? 'null' : '"' + obj.fachbezogeneBemerkungen + '"') + ',';
		result += '"tsFachbezogeneBemerkungen" : ' + ((!obj.tsFachbezogeneBemerkungen) ? 'null' : '"' + obj.tsFachbezogeneBemerkungen + '"') + ',';
		result += '"neueZuweisungKursart" : ' + ((!obj.neueZuweisungKursart) ? 'null' : '"' + obj.neueZuweisungKursart + '"') + ',';
		result += '"istGemahnt" : ' + ((!obj.istGemahnt) ? 'null' : obj.istGemahnt) + ',';
		result += '"tsIstGemahnt" : ' + ((!obj.tsIstGemahnt) ? 'null' : '"' + obj.tsIstGemahnt + '"') + ',';
		result += '"mahndatum" : ' + ((!obj.mahndatum) ? 'null' : '"' + obj.mahndatum + '"') + ',';
		if (!obj.teilleistungen) {
			result += '"teilleistungen" : []';
		} else {
			result += '"teilleistungen" : [ ';
			for (let i : number = 0; i < obj.teilleistungen.size(); i++) {
				let elem = obj.teilleistungen.get(i);
				result += ENMTeilleistung.transpilerToJSON(elem);
				if (i < obj.teilleistungen.size() - 1)
					result += ',';
			}
			result += ' ]' + ',';
		}
		result = result.slice(0, -1);
		result += '}';
		return result;
	}

	public static transpilerToJSONPatch(obj : Partial<ENMLeistung>) : string {
		let result = '{';
		if (typeof obj.id !== "undefined") {
			result += '"id" : ' + obj.id + ',';
		}
		if (typeof obj.lerngruppenID !== "undefined") {
			result += '"lerngruppenID" : ' + obj.lerngruppenID + ',';
		}
		if (typeof obj.note !== "undefined") {
			result += '"note" : ' + ((!obj.note) ? 'null' : '"' + obj.note + '"') + ',';
		}
		if (typeof obj.tsNote !== "undefined") {
			result += '"tsNote" : ' + ((!obj.tsNote) ? 'null' : '"' + obj.tsNote + '"') + ',';
		}
		if (typeof obj.istSchriftlich !== "undefined") {
			result += '"istSchriftlich" : ' + ((!obj.istSchriftlich) ? 'null' : obj.istSchriftlich) + ',';
		}
		if (typeof obj.abiturfach !== "undefined") {
			result += '"abiturfach" : ' + ((!obj.abiturfach) ? 'null' : obj.abiturfach) + ',';
		}
		if (typeof obj.fehlstundenGesamt !== "undefined") {
			result += '"fehlstundenGesamt" : ' + ((!obj.fehlstundenGesamt) ? 'null' : obj.fehlstundenGesamt) + ',';
		}
		if (typeof obj.tsFehlstundenGesamt !== "undefined") {
			result += '"tsFehlstundenGesamt" : ' + ((!obj.tsFehlstundenGesamt) ? 'null' : '"' + obj.tsFehlstundenGesamt + '"') + ',';
		}
		if (typeof obj.fehlstundenUnentschuldigt !== "undefined") {
			result += '"fehlstundenUnentschuldigt" : ' + ((!obj.fehlstundenUnentschuldigt) ? 'null' : obj.fehlstundenUnentschuldigt) + ',';
		}
		if (typeof obj.tsFehlstundenUnentschuldigt !== "undefined") {
			result += '"tsFehlstundenUnentschuldigt" : ' + ((!obj.tsFehlstundenUnentschuldigt) ? 'null' : '"' + obj.tsFehlstundenUnentschuldigt + '"') + ',';
		}
		if (typeof obj.fachbezogeneBemerkungen !== "undefined") {
			result += '"fachbezogeneBemerkungen" : ' + ((!obj.fachbezogeneBemerkungen) ? 'null' : '"' + obj.fachbezogeneBemerkungen + '"') + ',';
		}
		if (typeof obj.tsFachbezogeneBemerkungen !== "undefined") {
			result += '"tsFachbezogeneBemerkungen" : ' + ((!obj.tsFachbezogeneBemerkungen) ? 'null' : '"' + obj.tsFachbezogeneBemerkungen + '"') + ',';
		}
		if (typeof obj.neueZuweisungKursart !== "undefined") {
			result += '"neueZuweisungKursart" : ' + ((!obj.neueZuweisungKursart) ? 'null' : '"' + obj.neueZuweisungKursart + '"') + ',';
		}
		if (typeof obj.istGemahnt !== "undefined") {
			result += '"istGemahnt" : ' + ((!obj.istGemahnt) ? 'null' : obj.istGemahnt) + ',';
		}
		if (typeof obj.tsIstGemahnt !== "undefined") {
			result += '"tsIstGemahnt" : ' + ((!obj.tsIstGemahnt) ? 'null' : '"' + obj.tsIstGemahnt + '"') + ',';
		}
		if (typeof obj.mahndatum !== "undefined") {
			result += '"mahndatum" : ' + ((!obj.mahndatum) ? 'null' : '"' + obj.mahndatum + '"') + ',';
		}
		if (typeof obj.teilleistungen !== "undefined") {
			if (!obj.teilleistungen) {
				result += '"teilleistungen" : []';
			} else {
				result += '"teilleistungen" : [ ';
				for (let i : number = 0; i < obj.teilleistungen.size(); i++) {
					let elem = obj.teilleistungen.get(i);
					result += ENMTeilleistung.transpilerToJSON(elem);
					if (i < obj.teilleistungen.size() - 1)
						result += ',';
				}
				result += ' ]' + ',';
			}
		}
		result = result.slice(0, -1);
		result += '}';
		return result;
	}

}

export function cast_de_nrw_schule_svws_core_data_enm_ENMLeistung(obj : unknown) : ENMLeistung {
	return obj as ENMLeistung;
}
