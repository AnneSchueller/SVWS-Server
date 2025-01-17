package de.nrw.schule.svws.davapi.api;

import java.io.IOException;

import de.nrw.schule.svws.davapi.model.dav.Collection;
import de.nrw.schule.svws.davapi.model.dav.CurrentUserPrincipal;
import de.nrw.schule.svws.davapi.model.dav.CurrentUserPrivilegeSet;
import de.nrw.schule.svws.davapi.model.dav.Multistatus;
import de.nrw.schule.svws.davapi.model.dav.Principal;
import de.nrw.schule.svws.davapi.model.dav.Prop;
import de.nrw.schule.svws.davapi.model.dav.Propfind;
import de.nrw.schule.svws.davapi.model.dav.Resourcetype;
import de.nrw.schule.svws.davapi.model.dav.Response;
import de.nrw.schule.svws.davapi.model.dav.cal.CalendarHomeSet;
import de.nrw.schule.svws.davapi.util.XmlUnmarshallingUtil;
import jakarta.servlet.ServletInputStream;

/**
 * Dispatcher-Klasse für die Verarbeitung von Requests auf das DAV-API mittels
 * der HTTP-Methode PROPFIND auf die Ressource Adressbuch.
 */
public class PropfindDavRootDispatcher extends DavDispatcher {

	/** URI-Parameter zum Erzeugen von URIs für dieses Adressbuch */
	private DavUriParameter uriParameter;

	/**
	 * Konstruktor für einen neuen Dispatcher mit Repository und den gegebenen
	 * UriParametern
	 *
	 * @param uriParameter die UriParameter zum Erstellen von URIs
	 */
	public PropfindDavRootDispatcher(DavUriParameter uriParameter) {
		this.uriParameter = uriParameter;
	}

	/**
	 * Verarbeitet ein XML-Request aus dem gegebenen InputStream für die Sammlung
	 * von Ressourcen
	 * 
	 * @param inputStream der InputStream, welcher den XML-Request enthält
	 * @return Das Objekt zur Repräsentation der XML-Antwort
	 * @throws IOException bei der Verarbeitung des InputStreams/XML-Unmarshalling
	 */
	public Object dispatchCollection(ServletInputStream inputStream) throws IOException {
		Propfind propfind = XmlUnmarshallingUtil.unmarshal(inputStream, Propfind.class);

		Multistatus ms = new Multistatus();
		ms.getResponse().add(this.generateResponseRootLevel(propfind.getProp()));
		ms.getResponse().add(this.generateResponseAddressbookCollectionLevel(propfind.getProp()));
		if (propfind.getProp().getCalendarHomeSet() != null) {
			ms.getResponse().add(this.generateResponseCalendarCollectionLevel(propfind.getProp()));
		}
		return ms;
	}

	/**
	 * Erzeugt eine Antwort auf dem Root-Level unserer Dav-Schnittstelle abhängig
	 * von den angefragten Properties
	 * 
	 * @param propRequested die angefragten Properties
	 * @return eine Response mit den gefundenen und nicht-gefundenen Properties
	 */
	private Response generateResponseRootLevel(Prop propRequested) {
		DynamicPropUtil dynamicPropUtil = new DynamicPropUtil(propRequested);
		Prop prop200 = new Prop();

		if (dynamicPropUtil.getIsFieldRequested(Resourcetype.class)) {
			Resourcetype resourcetype = new Resourcetype();
			resourcetype.setCollection(new Collection());
			// auch wenn das root objekt eigentlich nur eine Sammlung mit den darunter
			// liegenden Sammlungen für Kalender und Adressbücher ist, benötigt thunderbird
			// hier das Principal objekt, da dieser Ordner quasi dem User gehört.
			// Ansonsten würde Thunderbird das Calendar-Home-Set nicht interpretieren,
			// vgl. Kommentar an #583
			resourcetype.setPrincipal(new Principal());
			prop200.setResourcetype(resourcetype);
		}

		if (dynamicPropUtil.getIsFieldRequested(CurrentUserPrincipal.class)) {
			CurrentUserPrincipal principal = new CurrentUserPrincipal();
			principal.getHref().add(DavUriBuilder.getPrincipalUri(this.uriParameter));
			prop200.setCurrentUserPrincipal(principal);
		}

		if (dynamicPropUtil.getIsFieldRequested(CurrentUserPrivilegeSet.class)) {
			prop200.setCurrentUserPrivilegeSet(getReadOnlyPrivilegeSet());
		}

		if (dynamicPropUtil.getIsFieldRequested(CalendarHomeSet.class)) {
			// Kalenderanfragen auf unserer Wurzel benötigen den Link zur Kalendersammlung
			// unter db/schema/dav/kalender
			CalendarHomeSet calendarHomeSet = new CalendarHomeSet();
			calendarHomeSet.getHref().add(DavUriBuilder.getCalendarCollectionUri(uriParameter));
			prop200.setCalendarHomeSet(calendarHomeSet);
		}

		Response response = createResponse(propRequested, prop200);
		response.getHref().add(DavUriBuilder.getCardDavRootUri(this.uriParameter));
		return response;
	}

	/**
	 * Erzeugt ein Antwortobjekt auf dem Level, welches die Adressbücher enthält,
	 * abhängig von den angefragten Properties
	 * 
	 * @param propRequested die angefragten Properties
	 * @return ein Response-Objekt mit den gefundenen und nicht gefundenen
	 *         Properties
	 */
	private Response generateResponseAddressbookCollectionLevel(Prop propRequested) {
		DynamicPropUtil dynamicPropUtil = new DynamicPropUtil(propRequested);
		Prop prop200 = new Prop();

		if (dynamicPropUtil.getIsFieldRequested(Resourcetype.class)) {
			Resourcetype resourcetype = new Resourcetype();
			resourcetype.setCollection(new Collection());
			prop200.setResourcetype(resourcetype);
		}

		if (dynamicPropUtil.getIsFieldRequested(CurrentUserPrincipal.class)) {
			CurrentUserPrincipal principal = new CurrentUserPrincipal();
			principal.getHref().add(DavUriBuilder.getPrincipalUri(uriParameter));
			prop200.setCurrentUserPrincipal(principal);
		}

		if (dynamicPropUtil.getIsFieldRequested(CurrentUserPrivilegeSet.class)) {
			prop200.setCurrentUserPrivilegeSet(getReadOnlyPrivilegeSet());
		}

		Response response = createResponse(propRequested, prop200);
		response.getHref().add(DavUriBuilder.getAddressbookCollectionUri(uriParameter));
		return response;
	}

	/**
	 * 
	 * Erzeugt ein Antwortobjekt auf dem Level, welches die Kalender enthält,
	 * abhängig von den angefragten Properties
	 * 
	 * @param propRequested die angefragten Properties
	 * @return ein Response-Objekt mit den gefundenen und nicht gefundenen
	 *         Properties
	 * 
	 */
	private Response generateResponseCalendarCollectionLevel(Prop propRequested) {
		// thunderbird interpretiert diesen teil der Antwort einfach mal nicht
		DynamicPropUtil dynamicPropUtil = new DynamicPropUtil(propRequested);
		Prop prop200 = new Prop();

		if (dynamicPropUtil.getIsFieldRequested(Resourcetype.class)) {
			Resourcetype resourcetype = new Resourcetype();
			resourcetype.setCollection(new Collection());
			prop200.setResourcetype(resourcetype);
		}

		if (dynamicPropUtil.getIsFieldRequested(CurrentUserPrincipal.class)) {
			CurrentUserPrincipal principal = new CurrentUserPrincipal();
			principal.getHref().add(DavUriBuilder.getPrincipalUri(uriParameter));
			prop200.setCurrentUserPrincipal(principal);
		}

		if (dynamicPropUtil.getIsFieldRequested(CurrentUserPrivilegeSet.class)) {
			prop200.setCurrentUserPrivilegeSet(getReadOnlyPrivilegeSet());
		}

		if (dynamicPropUtil.getIsFieldRequested(CalendarHomeSet.class)) {
			CalendarHomeSet calendarHomeSet = new CalendarHomeSet();
			calendarHomeSet.getHref().add(DavUriBuilder.getCalendarCollectionUri(uriParameter));
			prop200.setCalendarHomeSet(calendarHomeSet);
		}

		Response response = createResponse(propRequested, prop200);
		response.getHref().add(DavUriBuilder.getCalendarCollectionUri(uriParameter));
		return response;
	}
}
