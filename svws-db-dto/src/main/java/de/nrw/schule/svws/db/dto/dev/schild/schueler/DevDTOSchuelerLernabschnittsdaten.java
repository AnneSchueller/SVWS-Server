package de.nrw.schule.svws.db.dto.dev.schild.schueler;

import de.nrw.schule.svws.db.DBEntityManager;
import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultMinusConverter;
import de.nrw.schule.svws.db.converter.current.BooleanPlusMinusDefaultPlusConverter;
import de.nrw.schule.svws.db.converter.current.DatumConverter;
import de.nrw.schule.svws.db.converter.current.NoteConverterFromInteger;
import de.nrw.schule.svws.db.converter.current.statkue.SchulgliederungKuerzelConverter;

import de.nrw.schule.svws.core.types.Note;
import de.nrw.schule.svws.core.types.schule.Schulgliederung;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultMinusConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultMinusConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultPlusConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.BooleanPlusMinusDefaultPlusConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.DatumConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.DatumConverterDeserializer;
import de.nrw.schule.svws.csv.converter.current.NoteConverterFromIntegerSerializer;
import de.nrw.schule.svws.csv.converter.current.NoteConverterFromIntegerDeserializer;
import de.nrw.schule.svws.csv.converter.current.statkue.SchulgliederungKuerzelConverterSerializer;
import de.nrw.schule.svws.csv.converter.current.statkue.SchulgliederungKuerzelConverterDeserializer;

/**
 * Diese Klasse dient als DTO für die Datenbanktabelle SchuelerLernabschnittsdaten.
 * Sie wurde automatisch per Skript generiert und sollte nicht verändert werden, 
 * da sie aufgrund von Änderungen am DB-Schema ggf. neu generiert und überschrieben wird.
 */
@Entity
@Cacheable(DBEntityManager.use_db_caching)
@Table(name = "SchuelerLernabschnittsdaten")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.all", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schueler_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schueler_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schueler_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schueler_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schuljahresabschnitts_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schuljahresabschnitts_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schuljahresabschnitts_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schuljahresabschnitts_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.wechselnr", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.WechselNr = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.wechselnr.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.WechselNr IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schulbesuchsjahre", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schulbesuchsjahre = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schulbesuchsjahre.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schulbesuchsjahre IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.hochrechnung", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Hochrechnung = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.hochrechnung.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Hochrechnung IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.semesterwertung", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SemesterWertung = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.semesterwertung.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SemesterWertung IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.pruefordnung", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.PruefOrdnung = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.pruefordnung.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.PruefOrdnung IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.klassen_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Klassen_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.klassen_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Klassen_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.tutor_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Tutor_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.tutor_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Tutor_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.verspaetet", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Verspaetet = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.verspaetet.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Verspaetet IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npv_fach_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPV_Fach_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npv_fach_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPV_Fach_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npv_notekrz", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPV_NoteKrz = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npv_notekrz.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPV_NoteKrz IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npv_datum", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPV_Datum = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npv_datum.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPV_Datum IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npaa_fach_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPAA_Fach_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npaa_fach_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPAA_Fach_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npaa_notekrz", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPAA_NoteKrz = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npaa_notekrz.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPAA_NoteKrz IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npaa_datum", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPAA_Datum = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npaa_datum.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPAA_Datum IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npbq_fach_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPBQ_Fach_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npbq_fach_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPBQ_Fach_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npbq_notekrz", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPBQ_NoteKrz = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npbq_notekrz.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPBQ_NoteKrz IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npbq_datum", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPBQ_Datum = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.npbq_datum.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.NPBQ_Datum IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.versetzungkrz", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.VersetzungKrz = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.versetzungkrz.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.VersetzungKrz IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschlussart", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AbschlussArt = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschlussart.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AbschlussArt IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschlistprognose", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AbschlIstPrognose = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschlistprognose.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AbschlIstPrognose IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.konferenzdatum", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Konferenzdatum = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.konferenzdatum.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Konferenzdatum IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zeugnisdatum", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ZeugnisDatum = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zeugnisdatum.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ZeugnisDatum IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schulgliederung", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schulgliederung = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schulgliederung.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schulgliederung IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.asdjahrgang", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ASDJahrgang = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.asdjahrgang.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ASDJahrgang IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.jahrgang_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Jahrgang_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.jahrgang_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Jahrgang_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.fachklasse_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Fachklasse_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.fachklasse_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Fachklasse_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schwerpunkt_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schwerpunkt_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schwerpunkt_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schwerpunkt_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zeugnisbem", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ZeugnisBem = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zeugnisbem.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ZeugnisBem IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schwerbehinderung", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schwerbehinderung = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.schwerbehinderung.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Schwerbehinderung IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.foerderschwerpunkt_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Foerderschwerpunkt_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.foerderschwerpunkt_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Foerderschwerpunkt_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.orgformkrz", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.OrgFormKrz = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.orgformkrz.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.OrgFormKrz IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.refpaed", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.RefPaed = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.refpaed.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.RefPaed IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.klassenart", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Klassenart = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.klassenart.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Klassenart IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sumfehlstd", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SumFehlStd = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sumfehlstd.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SumFehlStd IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sumfehlstdu", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SumFehlStdU = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sumfehlstdu.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SumFehlStdU IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.wiederholung", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Wiederholung = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.wiederholung.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Wiederholung IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.gesamtnote_gs", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Gesamtnote_GS = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.gesamtnote_gs.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Gesamtnote_GS IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.gesamtnote_nw", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Gesamtnote_NW = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.gesamtnote_nw.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Gesamtnote_NW IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.folgeklasse_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Folgeklasse_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.folgeklasse_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Folgeklasse_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.foerderschwerpunkt2_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Foerderschwerpunkt2_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.foerderschwerpunkt2_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Foerderschwerpunkt2_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschluss", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Abschluss = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschluss.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Abschluss IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschluss_b", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Abschluss_B = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.abschluss_b.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Abschluss_B IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.dsnote", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DSNote = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.dsnote.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DSNote IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.av_leist", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AV_Leist = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.av_leist.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AV_Leist IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.av_zuv", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AV_Zuv = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.av_zuv.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AV_Zuv IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.av_selbst", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AV_Selbst = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.av_selbst.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AV_Selbst IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sv_verant", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SV_Verant = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sv_verant.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SV_Verant IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sv_konfl", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SV_Konfl = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sv_konfl.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SV_Konfl IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sv_koop", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SV_Koop = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sv_koop.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.SV_Koop IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.moeglnpfaecher", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.MoeglNPFaecher = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.moeglnpfaecher.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.MoeglNPFaecher IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zertifikate", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Zertifikate = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zertifikate.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Zertifikate IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.datumfhr", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DatumFHR = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.datumfhr.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DatumFHR IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.pruefalgoergebnis", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.PruefAlgoErgebnis = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.pruefalgoergebnis.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.PruefAlgoErgebnis IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zeugnisart", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Zeugnisart = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zeugnisart.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Zeugnisart IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.datumvon", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DatumVon = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.datumvon.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DatumVon IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.datumbis", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DatumBis = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.datumbis.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.DatumBis IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.fehlstundengrenzwert", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.FehlstundenGrenzwert = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.fehlstundengrenzwert.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.FehlstundenGrenzwert IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sonderpaedagoge_id", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Sonderpaedagoge_ID = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.sonderpaedagoge_id.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Sonderpaedagoge_ID IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.fachpraktanteilausr", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.FachPraktAnteilAusr = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.fachpraktanteilausr.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.FachPraktAnteilAusr IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.bilingualerzweig", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.BilingualerZweig = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.bilingualerzweig.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.BilingualerZweig IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.aosf", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AOSF = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.aosf.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.AOSF IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.autist", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Autist = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.autist.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.Autist IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zieldifferenteslernen", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ZieldifferentesLernen = :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.zieldifferenteslernen.multiple", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ZieldifferentesLernen IN :value")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.primaryKeyQuery", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ID = ?1")
@NamedQuery(name="DevDTOSchuelerLernabschnittsdaten.all.migration", query="SELECT e FROM DevDTOSchuelerLernabschnittsdaten e WHERE e.ID IS NOT NULL")
@JsonPropertyOrder({"ID","Schueler_ID","Schuljahresabschnitts_ID","WechselNr","Schulbesuchsjahre","Hochrechnung","SemesterWertung","PruefOrdnung","Klassen_ID","Tutor_ID","Verspaetet","NPV_Fach_ID","NPV_NoteKrz","NPV_Datum","NPAA_Fach_ID","NPAA_NoteKrz","NPAA_Datum","NPBQ_Fach_ID","NPBQ_NoteKrz","NPBQ_Datum","VersetzungKrz","AbschlussArt","AbschlIstPrognose","Konferenzdatum","ZeugnisDatum","Schulgliederung","ASDJahrgang","Jahrgang_ID","Fachklasse_ID","Schwerpunkt_ID","ZeugnisBem","Schwerbehinderung","Foerderschwerpunkt_ID","OrgFormKrz","RefPaed","Klassenart","SumFehlStd","SumFehlStdU","Wiederholung","Gesamtnote_GS","Gesamtnote_NW","Folgeklasse_ID","Foerderschwerpunkt2_ID","Abschluss","Abschluss_B","DSNote","AV_Leist","AV_Zuv","AV_Selbst","SV_Verant","SV_Konfl","SV_Koop","MoeglNPFaecher","Zertifikate","DatumFHR","PruefAlgoErgebnis","Zeugnisart","DatumVon","DatumBis","FehlstundenGrenzwert","Sonderpaedagoge_ID","FachPraktAnteilAusr","BilingualerZweig","AOSF","Autist","ZieldifferentesLernen"})
public class DevDTOSchuelerLernabschnittsdaten {

	/** Eine eindeutige ID für den Lernabschnitt des Schülers */
	@Id
	@Column(name = "ID")
	@JsonProperty
	public Long ID;

	/** Die eindeutige ID des Schülers – verweist auf den Schülers */
	@Column(name = "Schueler_ID")
	@JsonProperty
	public Long Schueler_ID;

	/** ID des Schuljahresabschnittes aus der Tabelle Schuljahresabschnitte */
	@Column(name = "Schuljahresabschnitts_ID")
	@JsonProperty
	public Long Schuljahresabschnitts_ID;

	/** Wird für Wiederholungen im Laufenden Abschnitt genutzt NULL=aktueller Abschnitt 1=vor dem ersten Wechsel 2=vor dem zweiten Wechsel usw */
	@Column(name = "WechselNr")
	@JsonProperty
	public Integer WechselNr;

	/** Schulbesuchsjahre für den Lernabschnitt */
	@Column(name = "Schulbesuchsjahre")
	@JsonProperty
	public Integer Schulbesuchsjahre;

	/** Lernabschnitt ist Hochrechnung (nur noch BK) */
	@Column(name = "Hochrechnung")
	@JsonProperty
	public Integer Hochrechnung;

	/** Gewerteter Abschnitt (Ja/Nein) */
	@Column(name = "SemesterWertung")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultPlusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultPlusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultPlusConverterDeserializer.class)
	public Boolean SemesterWertung;

	/** Prüfungsordnung des Lernabschnitts */
	@Column(name = "PruefOrdnung")
	@JsonProperty
	public String PruefOrdnung;

	/** Klassen_ID des Schülers für den Lernabschnitt */
	@Column(name = "Klassen_ID")
	@JsonProperty
	public Long Klassen_ID;

	/** Die Lehrer-ID des Tutors, der dem Schüler zugeordnet ist, oder null falls keine Zuordnung vorgenommen wurde */
	@Column(name = "Tutor_ID")
	@JsonProperty
	public Long Tutor_ID;

	/** ID des Nachprüfungsfaches */
	@Column(name = "Verspaetet")
	@JsonProperty
	public Integer Verspaetet;

	/** TODO: Note der Nachprüfung */
	@Column(name = "NPV_Fach_ID")
	@JsonProperty
	public Long NPV_Fach_ID;

	/** TODO: Datum der Nachprüfung */
	@Column(name = "NPV_NoteKrz")
	@JsonProperty
	public String NPV_NoteKrz;

	/** TODO: BK: ID des Nachprüfungsfaches für den allgemein-bildenen Abschluss */
	@Column(name = "NPV_Datum")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String NPV_Datum;

	/** TODO: BK: Note des Nachprüfungsfaches für den allgemein-bildenen Abschluss */
	@Column(name = "NPAA_Fach_ID")
	@JsonProperty
	public Long NPAA_Fach_ID;

	/** TODO: BK: Datum der Nachprüfung für den allgemein-bildenen Abschluss */
	@Column(name = "NPAA_NoteKrz")
	@JsonProperty
	public String NPAA_NoteKrz;

	/** TODO: BK: dito für berufs-qualifizierende Nachprüfung */
	@Column(name = "NPAA_Datum")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String NPAA_Datum;

	/** TODO: BK: dito für berufs-qualifizierende Nachprüfung */
	@Column(name = "NPBQ_Fach_ID")
	@JsonProperty
	public Long NPBQ_Fach_ID;

	/** TODO: BK: dito für berufs-qualifizierende Nachprüfung */
	@Column(name = "NPBQ_NoteKrz")
	@JsonProperty
	public String NPBQ_NoteKrz;

	/** TODO: ID des Nachprüfungsfaches */
	@Column(name = "NPBQ_Datum")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String NPBQ_Datum;

	/** Kürzel des Versetungsvermerk */
	@Column(name = "VersetzungKrz")
	@JsonProperty
	public String VersetzungKrz;

	/** Art des Abschlusses */
	@Column(name = "AbschlussArt")
	@JsonProperty
	public Integer AbschlussArt;

	/** Gibt an ob Abschluss Prognose ist (GE, PS und SK) */
	@Column(name = "AbschlIstPrognose")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean AbschlIstPrognose;

	/** Konferenzdatum */
	@Column(name = "Konferenzdatum")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String Konferenzdatum;

	/** Zeugnisdatum */
	@Column(name = "ZeugnisDatum")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String ZeugnisDatum;

	/** ASD-Kürzel SGL */
	@Column(name = "ASDSchulgliederung")
	@JsonProperty
	@Convert(converter=SchulgliederungKuerzelConverter.class)
	@JsonSerialize(using=SchulgliederungKuerzelConverterSerializer.class)
	@JsonDeserialize(using=SchulgliederungKuerzelConverterDeserializer.class)
	public Schulgliederung Schulgliederung;

	/** ASD-Jahrgang kann alles über ID geregelt werden */
	@Column(name = "ASDJahrgang")
	@JsonProperty
	public String ASDJahrgang;

	/** ID des Jahrgangs der zum Report zugeordnet wird */
	@Column(name = "Jahrgang_ID")
	@JsonProperty
	public Long Jahrgang_ID;

	/** ID der Fachklasse (BK) */
	@Column(name = "Fachklasse_ID")
	@JsonProperty
	public Long Fachklasse_ID;

	/** ID des Schwerpunkts aus dem Katalog */
	@Column(name = "Schwerpunkt_ID")
	@JsonProperty
	public Long Schwerpunkt_ID;

	/** Text für Zeugnisbemerkung */
	@Column(name = "ZeugnisBem")
	@JsonProperty
	public String ZeugnisBem;

	/** Schwerbehinderung (Ja/Nein) */
	@Column(name = "Schwerbehinderung")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean Schwerbehinderung;

	/** ID Hauptförderschwerpunkt */
	@Column(name = "Foerderschwerpunkt_ID")
	@JsonProperty
	public Long Foerderschwerpunkt_ID;

	/** Kürzel der Organisationsform */
	@Column(name = "OrgFormKrz")
	@JsonProperty
	public String OrgFormKrz;

	/** TODO DEPRECATED: Reformpädagogik */
	@Column(name = "RefPaed")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean RefPaed;

	/** Klassenart */
	@Column(name = "Klassenart")
	@JsonProperty
	public String Klassenart;

	/** Summer der Fehlstunden */
	@Column(name = "SumFehlStd")
	@JsonProperty
	public Integer SumFehlStd;

	/** Summer der Fehlstunden unentschuldigt */
	@Column(name = "SumFehlStdU")
	@JsonProperty
	public Integer SumFehlStdU;

	/** Lernabschnitt wurde wiederholt (Ja/Nein) */
	@Column(name = "Wiederholung")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean Wiederholung;

	/** Lernbereichnote Gesellschaftswissenschaft oder Arbeitlehre HA10 */
	@Column(name = "Gesamtnote_GS")
	@JsonProperty
	@Convert(converter=NoteConverterFromInteger.class)
	@JsonSerialize(using=NoteConverterFromIntegerSerializer.class)
	@JsonDeserialize(using=NoteConverterFromIntegerDeserializer.class)
	public Note Gesamtnote_GS;

	/** Lernbereichnote Naturwissenschaft HA10 */
	@Column(name = "Gesamtnote_NW")
	@JsonProperty
	@Convert(converter=NoteConverterFromInteger.class)
	@JsonSerialize(using=NoteConverterFromIntegerSerializer.class)
	@JsonDeserialize(using=NoteConverterFromIntegerDeserializer.class)
	public Note Gesamtnote_NW;

	/** ID der Folgeklasse für den Lernabschnitt, sofern dieser vom Standard der Klassentabelle abweicht */
	@Column(name = "Folgeklasse_ID")
	@JsonProperty
	public Long Folgeklasse_ID;

	/** Weiterer Förderschwerpunkt ID */
	@Column(name = "Foerderschwerpunkt2_ID")
	@JsonProperty
	public Long Foerderschwerpunkt2_ID;

	/** allgemeinbildender Abschluss */
	@Column(name = "Abschluss")
	@JsonProperty
	public String Abschluss;

	/** berufsbezogener Abschnluss (BK) */
	@Column(name = "Abschluss_B")
	@JsonProperty
	public String Abschluss_B;

	/** Durchschnittsnote im betreffenden Abschnitt Ist allerdings in der Programmoberfläche nicht verfügbar Der Inhalt wird durch die Prüfungsalgorithmen gefüllt es ist eine Ausgabe in Reports möglich */
	@Column(name = "DSNote")
	@JsonProperty
	public String DSNote;

	/** DEPRECATED: Kopfnote */
	@Column(name = "AV_Leist")
	@JsonProperty
	public Integer AV_Leist;

	/** DEPRECATED: Kopfnote */
	@Column(name = "AV_Zuv")
	@JsonProperty
	public Integer AV_Zuv;

	/** DEPRECATED: Kopfnote */
	@Column(name = "AV_Selbst")
	@JsonProperty
	public Integer AV_Selbst;

	/** DEPRECATED: Kopfnote */
	@Column(name = "SV_Verant")
	@JsonProperty
	public Integer SV_Verant;

	/** DEPRECATED: Kopfnote */
	@Column(name = "SV_Konfl")
	@JsonProperty
	public Integer SV_Konfl;

	/** DEPRECATED: Kopfnote */
	@Column(name = "SV_Koop")
	@JsonProperty
	public Integer SV_Koop;

	/** Auflistung der mögllichen Nachprüfungsfächer kommagetrennt */
	@Column(name = "MoeglNPFaecher")
	@JsonProperty
	public String MoeglNPFaecher;

	/** DEPRECATED: Hier war mal geplant, Texte für Zertifikate einzugeben */
	@Column(name = "Zertifikate")
	@JsonProperty
	public String Zertifikate;

	/** Datum FHR */
	@Column(name = "DatumFHR")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String DatumFHR;

	/** Text für die Ergebnisse der Abschlussberechnungen */
	@Column(name = "PruefAlgoErgebnis")
	@JsonProperty
	public String PruefAlgoErgebnis;

	/** Angabe der Zeugnisart */
	@Column(name = "Zeugnisart")
	@JsonProperty
	public String Zeugnisart;

	/** Beginn Lernabschnitt */
	@Column(name = "DatumVon")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String DatumVon;

	/** Ende Lernabschnitt */
	@Column(name = "DatumBis")
	@JsonProperty
	@Convert(converter=DatumConverter.class)
	@JsonSerialize(using=DatumConverterSerializer.class)
	@JsonDeserialize(using=DatumConverterDeserializer.class)
	public String DatumBis;

	/** Grenzwert für Fehlstunden (BK Warnbriefe zur Entlassung) */
	@Column(name = "FehlstundenGrenzwert")
	@JsonProperty
	public Integer FehlstundenGrenzwert;

	/** Hier kann die ID einer Lehrkraft eingetragen werden die dann die Schüler als Förderpädagoge betreut und im Notenmodul hat */
	@Column(name = "Sonderpaedagoge_ID")
	@JsonProperty
	public Long Sonderpaedagoge_ID;

	/** Enthält die Angabe, ob die Fachpraktischen Anteile in Anlage B08 B09 B10 ausreichend sind für Versetzung (BK) */
	@Column(name = "FachPraktAnteilAusr")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultPlusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultPlusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultPlusConverterDeserializer.class)
	public Boolean FachPraktAnteilAusr;

	/** Sprache des Bilingualen Zweigs */
	@Column(name = "BilingualerZweig")
	@JsonProperty
	public String BilingualerZweig;

	/** Gibt an ob der Schüler ein AOSF hat */
	@Column(name = "AOSF")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean AOSF;

	/** Gibt an ob Autismuss vorliegt (Ja/Nein) */
	@Column(name = "Autist")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean Autist;

	/** Gibt an ob der Schüler zieldifferent unterrichtet wird */
	@Column(name = "ZieldifferentesLernen")
	@JsonProperty
	@Convert(converter=BooleanPlusMinusDefaultMinusConverter.class)
	@JsonSerialize(using=BooleanPlusMinusDefaultMinusConverterSerializer.class)
	@JsonDeserialize(using=BooleanPlusMinusDefaultMinusConverterDeserializer.class)
	public Boolean ZieldifferentesLernen;

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerLernabschnittsdaten ohne eine Initialisierung der Attribute.
	 */
	@SuppressWarnings("unused")
	private DevDTOSchuelerLernabschnittsdaten() {
	}

	/**
	 * Erstellt ein neues Objekt der Klasse DevDTOSchuelerLernabschnittsdaten ohne eine Initialisierung der Attribute.
	 * @param ID   der Wert für das Attribut ID
	 * @param Schueler_ID   der Wert für das Attribut Schueler_ID
	 * @param Schuljahresabschnitts_ID   der Wert für das Attribut Schuljahresabschnitts_ID
	 * @param FachPraktAnteilAusr   der Wert für das Attribut FachPraktAnteilAusr
	 */
	public DevDTOSchuelerLernabschnittsdaten(final Long ID, final Long Schueler_ID, final Long Schuljahresabschnitts_ID, final Boolean FachPraktAnteilAusr) {
		if (ID == null) { 
			throw new NullPointerException("ID must not be null");
		}
		this.ID = ID;
		if (Schueler_ID == null) { 
			throw new NullPointerException("Schueler_ID must not be null");
		}
		this.Schueler_ID = Schueler_ID;
		if (Schuljahresabschnitts_ID == null) { 
			throw new NullPointerException("Schuljahresabschnitts_ID must not be null");
		}
		this.Schuljahresabschnitts_ID = Schuljahresabschnitts_ID;
		if (FachPraktAnteilAusr == null) { 
			throw new NullPointerException("FachPraktAnteilAusr must not be null");
		}
		this.FachPraktAnteilAusr = FachPraktAnteilAusr;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevDTOSchuelerLernabschnittsdaten other = (DevDTOSchuelerLernabschnittsdaten) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}


	/**
	 * Konvertiert das Objekt in einen String. Dieser kann z.B. für Debug-Ausgaben genutzt werden.
	 *
	 * @return die String-Repräsentation des Objektes
	 */
	@Override
	public String toString() {
		return "DevDTOSchuelerLernabschnittsdaten(ID=" + this.ID + ", Schueler_ID=" + this.Schueler_ID + ", Schuljahresabschnitts_ID=" + this.Schuljahresabschnitts_ID + ", WechselNr=" + this.WechselNr + ", Schulbesuchsjahre=" + this.Schulbesuchsjahre + ", Hochrechnung=" + this.Hochrechnung + ", SemesterWertung=" + this.SemesterWertung + ", PruefOrdnung=" + this.PruefOrdnung + ", Klassen_ID=" + this.Klassen_ID + ", Tutor_ID=" + this.Tutor_ID + ", Verspaetet=" + this.Verspaetet + ", NPV_Fach_ID=" + this.NPV_Fach_ID + ", NPV_NoteKrz=" + this.NPV_NoteKrz + ", NPV_Datum=" + this.NPV_Datum + ", NPAA_Fach_ID=" + this.NPAA_Fach_ID + ", NPAA_NoteKrz=" + this.NPAA_NoteKrz + ", NPAA_Datum=" + this.NPAA_Datum + ", NPBQ_Fach_ID=" + this.NPBQ_Fach_ID + ", NPBQ_NoteKrz=" + this.NPBQ_NoteKrz + ", NPBQ_Datum=" + this.NPBQ_Datum + ", VersetzungKrz=" + this.VersetzungKrz + ", AbschlussArt=" + this.AbschlussArt + ", AbschlIstPrognose=" + this.AbschlIstPrognose + ", Konferenzdatum=" + this.Konferenzdatum + ", ZeugnisDatum=" + this.ZeugnisDatum + ", Schulgliederung=" + this.Schulgliederung + ", ASDJahrgang=" + this.ASDJahrgang + ", Jahrgang_ID=" + this.Jahrgang_ID + ", Fachklasse_ID=" + this.Fachklasse_ID + ", Schwerpunkt_ID=" + this.Schwerpunkt_ID + ", ZeugnisBem=" + this.ZeugnisBem + ", Schwerbehinderung=" + this.Schwerbehinderung + ", Foerderschwerpunkt_ID=" + this.Foerderschwerpunkt_ID + ", OrgFormKrz=" + this.OrgFormKrz + ", RefPaed=" + this.RefPaed + ", Klassenart=" + this.Klassenart + ", SumFehlStd=" + this.SumFehlStd + ", SumFehlStdU=" + this.SumFehlStdU + ", Wiederholung=" + this.Wiederholung + ", Gesamtnote_GS=" + this.Gesamtnote_GS + ", Gesamtnote_NW=" + this.Gesamtnote_NW + ", Folgeklasse_ID=" + this.Folgeklasse_ID + ", Foerderschwerpunkt2_ID=" + this.Foerderschwerpunkt2_ID + ", Abschluss=" + this.Abschluss + ", Abschluss_B=" + this.Abschluss_B + ", DSNote=" + this.DSNote + ", AV_Leist=" + this.AV_Leist + ", AV_Zuv=" + this.AV_Zuv + ", AV_Selbst=" + this.AV_Selbst + ", SV_Verant=" + this.SV_Verant + ", SV_Konfl=" + this.SV_Konfl + ", SV_Koop=" + this.SV_Koop + ", MoeglNPFaecher=" + this.MoeglNPFaecher + ", Zertifikate=" + this.Zertifikate + ", DatumFHR=" + this.DatumFHR + ", PruefAlgoErgebnis=" + this.PruefAlgoErgebnis + ", Zeugnisart=" + this.Zeugnisart + ", DatumVon=" + this.DatumVon + ", DatumBis=" + this.DatumBis + ", FehlstundenGrenzwert=" + this.FehlstundenGrenzwert + ", Sonderpaedagoge_ID=" + this.Sonderpaedagoge_ID + ", FachPraktAnteilAusr=" + this.FachPraktAnteilAusr + ", BilingualerZweig=" + this.BilingualerZweig + ", AOSF=" + this.AOSF + ", Autist=" + this.Autist + ", ZieldifferentesLernen=" + this.ZieldifferentesLernen + ")";
	}

}