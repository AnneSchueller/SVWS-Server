import { JavaObject, cast_java_lang_Object } from '../../../java/lang/JavaObject';
import { JavaString, cast_java_lang_String } from '../../../java/lang/JavaString';
import { Vector, cast_java_util_Vector } from '../../../java/util/Vector';

export class SchuleAbschnitte extends JavaObject {

	public anzahlAbschnitte : number = 0;

	public abschnittBez : String = "Halbjahr";

	public bezAbschnitte : Vector<String> = new Vector();


	public constructor() {
		super();
	}

	isTranspiledInstanceOf(name : string): boolean {
		return ['de.nrw.schule.svws.core.data.schule.SchuleAbschnitte'].includes(name);
	}

	public static transpilerFromJSON(json : string): SchuleAbschnitte {
		const obj = JSON.parse(json);
		const result = new SchuleAbschnitte();
		if (typeof obj.anzahlAbschnitte === "undefined")
			 throw new Error('invalid json format, missing attribute anzahlAbschnitte');
		result.anzahlAbschnitte = obj.anzahlAbschnitte;
		if (typeof obj.abschnittBez === "undefined")
			 throw new Error('invalid json format, missing attribute abschnittBez');
		result.abschnittBez = String(obj.abschnittBez);
		if (!!obj.bezAbschnitte) {
			for (let elem of obj.bezAbschnitte) {
				result.bezAbschnitte?.add(String(elem));
			}
		}
		return result;
	}

	public static transpilerToJSON(obj : SchuleAbschnitte) : string {
		let result = '{';
		result += '"anzahlAbschnitte" : ' + obj.anzahlAbschnitte + ',';
		result += '"abschnittBez" : ' + '"' + obj.abschnittBez.valueOf() + '"' + ',';
		if (!obj.bezAbschnitte) {
			result += '"bezAbschnitte" : []';
		} else {
			result += '"bezAbschnitte" : [ ';
			for (let i : number = 0; i < obj.bezAbschnitte.size(); i++) {
				let elem = obj.bezAbschnitte.get(i);
				result += '"' + elem + '"';
				if (i < obj.bezAbschnitte.size() - 1)
					result += ',';
			}
			result += ' ]' + ',';
		}
		result = result.slice(0, -1);
		result += '}';
		return result;
	}

	public static transpilerToJSONPatch(obj : Partial<SchuleAbschnitte>) : string {
		let result = '{';
		if (typeof obj.anzahlAbschnitte !== "undefined") {
			result += '"anzahlAbschnitte" : ' + obj.anzahlAbschnitte + ',';
		}
		if (typeof obj.abschnittBez !== "undefined") {
			result += '"abschnittBez" : ' + '"' + obj.abschnittBez.valueOf() + '"' + ',';
		}
		if (typeof obj.bezAbschnitte !== "undefined") {
			if (!obj.bezAbschnitte) {
				result += '"bezAbschnitte" : []';
			} else {
				result += '"bezAbschnitte" : [ ';
				for (let i : number = 0; i < obj.bezAbschnitte.size(); i++) {
					let elem = obj.bezAbschnitte.get(i);
					result += '"' + elem + '"';
					if (i < obj.bezAbschnitte.size() - 1)
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

export function cast_de_nrw_schule_svws_core_data_schule_SchuleAbschnitte(obj : unknown) : SchuleAbschnitte {
	return obj as SchuleAbschnitte;
}
