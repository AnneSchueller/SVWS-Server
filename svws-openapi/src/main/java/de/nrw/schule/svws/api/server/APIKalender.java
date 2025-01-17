package de.nrw.schule.svws.api.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import de.nrw.schule.svws.api.OpenAPIApplication;
import de.nrw.schule.svws.core.logger.LogConsumerConsole;
import de.nrw.schule.svws.core.logger.LogLevel;
import de.nrw.schule.svws.core.logger.Logger;
import de.nrw.schule.svws.core.types.benutzer.BenutzerKompetenz;
import de.nrw.schule.svws.davapi.api.DavExtendedHttpStatus;
import de.nrw.schule.svws.davapi.api.DavUriBuilder;
import de.nrw.schule.svws.davapi.api.DavUriParameter;
import de.nrw.schule.svws.davapi.api.DeleteRessourceDispatcher;
import de.nrw.schule.svws.davapi.api.PROPFIND;
import de.nrw.schule.svws.davapi.api.PropfindCalendarDispatcher;
import de.nrw.schule.svws.davapi.api.PutCalendarDispatcher;
import de.nrw.schule.svws.davapi.api.REPORT;
import de.nrw.schule.svws.davapi.api.ReportCalendarDispatcher;
import de.nrw.schule.svws.davapi.data.IDavRepository;
import de.nrw.schule.svws.davapi.data.IKalenderEintragRepository;
import de.nrw.schule.svws.davapi.data.IKalenderRepository;
import de.nrw.schule.svws.davapi.data.repos.dav.DavRepository;
import de.nrw.schule.svws.davapi.data.repos.kalender.KalenderRepository;
import de.nrw.schule.svws.davapi.model.dav.Error;
import de.nrw.schule.svws.davapi.model.dav.Multistatus;
import de.nrw.schule.svws.db.DBEntityManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.EntityTag;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Die Klasse spezifiziert die CardDAV-API-Schnittstelle für den Zugriff auf
 * Kalender und Kalendereinträge.
 */
@Path(DavUriBuilder.DAV_BASE_URI_PATTERN)
@Tag(name = "Server")
public class APIKalender {

	/**
	 * Logger für diese Klasse
	 */
	private static final Logger logger = createLogger();

	/**
	 * Die CardDAV-API Methode zur Abfrage von Eigenschaften der
	 * Ressource-Collection Kalender
	 *
	 * @param schema  Das Datenbankschema, auf welches die Abfrage ausgeführt werden
	 *                soll
	 * @param request Die Informationen zur HTTP-Anfrage
	 * @return Ergebnisobjekt vom Typ {@link Multistatus} oder {@link Error}
	 */
	@PROPFIND
	@Path(DavUriBuilder.CARD_DAV_REL_URI_PATTERN_CALENDAR_COLLECTION)
	@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_XML)
	public Response propfindOnCalendarCollection(@PathParam("schema") String schema,
			@Context HttpServletRequest request) {
		try (DBEntityManager conn = OpenAPIApplication.getDBConnection(request, BenutzerKompetenz.ADRESSDATEN_ANSEHEN);
				InputStream inputStream = getInputStream(request)) {
			PropfindCalendarDispatcher dispatcher = createPropfindCalendarDispatcher(conn);
			Object result = dispatcher.dispatch(inputStream, "");
			return buildResponse(result);
		} catch (IOException e) {
			StringWriter out = new StringWriter();
			PrintWriter pw = new PrintWriter(out);
			e.printStackTrace(pw);
			logger.log(LogLevel.ERROR, "Bei der Anfrage ist folgende IOException aufgetreten:" + pw.toString());
			return buildBadRequest(e);
		}
	}

	/**
	 * Die CardDAV-API Methode zur Abfrage von Eigenschaften der Ressource Kalender
	 *
	 * @param schema     Das Datenbankschema, auf welches die Abfrage ausgeführt
	 *                   werden soll
	 * @param kalenderId Id des Kalenders, für den die Eigenschaften abfragt werden
	 * @param request    Die Informationen zur HTTP-Anfrage
	 * @return Ergebnisobjekt vom Typ {@link Multistatus} oder {@link Error}
	 */
	@PROPFIND
	@Path(DavUriBuilder.CARD_DAV_REL_URI_PATTERN_CALENDAR)
	@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_XML)
	public Response propfindOnCalendar(@PathParam("schema") String schema,
			@PathParam("resourceCollectionId") String kalenderId, @Context HttpServletRequest request) {
		try (DBEntityManager conn = OpenAPIApplication.getDBConnection(request, BenutzerKompetenz.ADRESSDATEN_ANSEHEN);
				InputStream inputStream = getInputStream(request)) {
			PropfindCalendarDispatcher dispatcher = createPropfindCalendarDispatcher(conn);
			Object result = dispatcher.dispatch(inputStream, kalenderId);
			return buildResponse(result);
		} catch (IOException e) {
			e.printStackTrace();
			return buildBadRequest(e);
		}
	}

	/**
	 * Die CardDAV-API Methode zur Abfrage von Informationen der Ressource Kalender
	 *
	 * @param schema     Das Datenbankschema, auf welches die Abfrage ausgeführt
	 *                   werden soll
	 * @param kalenderId Id des Kalenders, für den die Informationen abfragt werden
	 * @param request    Die Informationen zur HTTP-Anfrage
	 * @return Ergebnisobjekt vom Typ {@link Multistatus} oder
	 *         {@link de.nrw.schule.svws.davapi.model.dav.Error}
	 */
	@REPORT
	@Path(DavUriBuilder.CARD_DAV_REL_URI_PATTERN_CALENDAR)
	@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_XML)
	public Response reportOnCalendar(@PathParam("schema") String schema,
			@PathParam("resourceCollectionId") String kalenderId, @Context HttpServletRequest request) {
		try (DBEntityManager conn = OpenAPIApplication.getDBConnection(request, BenutzerKompetenz.ADRESSDATEN_ANSEHEN);
				InputStream inputStream = getInputStream(request)) {
			ReportCalendarDispatcher dispatcher = createReportCalendarDispatcher(conn);
			Object result = dispatcher.dispatch(inputStream, kalenderId);
			return buildResponse(result);
		} catch (IOException e) {
			e.printStackTrace();
			return buildBadRequest(e);
		}
	}

	/**
	 * Die CardDAV-API Methode zur Anlage eines neuen Kalendereintrags oder zur
	 * Änderung eines bestehenden Kalendereintrags
	 *
	 * @param schema             Das Datenbankschema, auf welches die Abfrage
	 *                           ausgeführt werden soll
	 * @param kalenderId         Id des Kalenders, für den die Informationen abfragt
	 *                           werden
	 * @param request            Die Informationen zur HTTP-Anfrage
	 * @param kalenderEintragUId die UID des Kalendereintrags, auf den geschrieben
	 *                           werden soll
	 * @param ifNonMatchHeader   Header mit ETag für PUT einer neuen Ressource
	 * @param ifMatchHeader      Header mit ETag für PUT auf vorhandene Ressource
	 * @return Ergebnisobjekt vom Typ {@link Multistatus} oder
	 *         {@link de.nrw.schule.svws.davapi.model.dav.Error}
	 */
	@PUT
	@Path(DavUriBuilder.CARD_DAV_REL_URI_PATTERN_CALENDAR_ENTRY)
	@Consumes({ "Text/Calendar", MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_XML)
	public Response putOnCalendar(@PathParam("schema") String schema,
			@PathParam("resourceCollectionId") String kalenderId, @PathParam("resourceId") String kalenderEintragUId,
			@HeaderParam("If-None-Match") String ifNonMatchHeader, @HeaderParam("If-Match") String ifMatchHeader,
			@Context HttpServletRequest request) {

		try (DBEntityManager conn = OpenAPIApplication.getDBConnection(request, BenutzerKompetenz.ADRESSDATEN_ANSEHEN);
				InputStream inputStream = getInputStream(request)) {
			PutCalendarDispatcher dispatcher = createPutCalendarDispatcher(conn);

			if (ifNonMatchHeader != null && "*".equals(ifNonMatchHeader)) {
				// Es soll ein neuer Termin erstellt werden,
				// also nicht etwa (versehentlich) ein Termin mit der angegebenen Id
				// überschrieben werden.
				Object eTagResult = dispatcher.dispatchCreate(inputStream, kalenderId, kalenderEintragUId);
				if (eTagResult instanceof Error) {
					return buildResponse(eTagResult);
				}
				// Response vorbereiten
				// Header "ETag" (MD5-Checksumme) und HTTP-Statuscode 201 (Created)
				// zurückliefern
				return buildCreatedResponse((EntityTag) eTagResult);
			}

			if (ifMatchHeader != null && !ifMatchHeader.isBlank()) {
				// Update des Kalendereintrags durchführen
				Object eTagResult = dispatcher.dispatchUpdate(inputStream, kalenderId, kalenderEintragUId,
						ifMatchHeader);
				if (eTagResult instanceof Error) {
					return buildResponse(eTagResult);
				}
				// Response vorbereiten
				// Header "ETag" (neue MD5-Checksumme) und HTTP-Statuscode 204 (No Content)
				// zurückliefern
				return buildNoContentResponse((EntityTag) eTagResult);
			}

			return buildBadRequest(new BadRequestException("Ungültige Anfrage"));

		} catch (IOException e) {
			e.printStackTrace();
			return buildBadRequest(e);
		}
	}

	/**
	 * API-Methode für Löschen von Kalendereinträgen
	 * 
	 * @param schema             das Schema der Datenbank
	 * @param kalenderId         die ID des Kalenders
	 * @param kalenderEintragUID die UID der zu löschenden Ressource
	 * @param ifMatchHeader      der ifMatchHeader, welcher die dem CLient bekannte
	 *                           Version angibt
	 * @param request            die Informationen zur HTTP-Anfrage
	 * @return den Status 204 NO CONTENT bei erfolgreichem Löschen, ansonsten ein
	 *         Multistatus mit Fehlermeldung
	 */
	@DELETE
	@Path(DavUriBuilder.CARD_DAV_REL_URI_PATTERN_CALENDAR_ENTRY)
	@Produces(MediaType.TEXT_XML)
	public Response deleteOnCalendar(@PathParam("schema") String schema,
			@PathParam("resourceCollectionId") String kalenderId, @PathParam("resourceId") String kalenderEintragUID,
			@HeaderParam("If-Match") String ifMatchHeader, @Context HttpServletRequest request) {
		try (DBEntityManager dbConnection = OpenAPIApplication.getDBConnection(request,
				BenutzerKompetenz.EIGENEN_KALENDER_BEARBEITEN)) {
			DeleteRessourceDispatcher dispatcher = createDeleteOnDavRessourceDispatcher(dbConnection, schema);
			Optional<Multistatus> dispatched = dispatcher.dispatch(kalenderId, kalenderEintragUID, ifMatchHeader);
			if (dispatched.isPresent()) {
				// ein Ergebnis zeigt an, dass der Request nicht erfolgreich war, warum steht im
				// Multistatus
				return buildResponse(dispatched);
			} 
			// kein ergebnis zeigt erfolgreiches löschen an
			return Response.status(Response.Status.NO_CONTENT).type(MediaType.TEXT_PLAIN).build();
		} catch (Exception e) {
			e.printStackTrace();
			// atm nur bad request, eventuell noch andere zufügen
			return buildBadRequest(e);
		}
	}

	/**
	 * API-Methode zum Löschen einer Kalender-Ressourcensammlung
	 * 
	 * @param schema     das Schema der Datenbank
	 * @param kalenderId die ID des zu löschenden Kalenders
	 * @param request    Informationen zum HTTP-Request
	 * @return den Status 204 NO CONTENT bei erfolgtem Löschen, ansonsten ein
	 *         Multistatus mit Fehlermeldungen
	 */
	@DELETE
	@Path(DavUriBuilder.CARD_DAV_REL_URI_PATTERN_CALENDAR)
	@Produces(MediaType.TEXT_XML)
	public Response deleteOnCalendar(@PathParam("schema") String schema,
			@PathParam("resourceCollectionId") String kalenderId, @Context HttpServletRequest request) {
		try (DBEntityManager dbConnection = OpenAPIApplication.getDBConnection(request,
				BenutzerKompetenz.EIGENEN_KALENDER_BEARBEITEN)) {
			DeleteRessourceDispatcher dispatcher = createDeleteOnDavRessourceDispatcher(dbConnection, schema);
			Optional<Multistatus> dispatched = dispatcher.dispatch(kalenderId);
			if (dispatched.isPresent()) {
				// ein Ergebnis zeigt an, dass der Request nicht erfolgreich war, warum steht im
				// Multistatus
				return buildResponse(dispatched);
			}
			// kein ergebnis zeigt erfolgreiches löschen an
			return Response.status(Response.Status.NO_CONTENT).type(MediaType.TEXT_PLAIN).build();
		} catch (Exception e) {
			// atm nur bad request, eventuell noch andere zufügen
			return buildBadRequest(e);
		}
	}

	/**
	 * erstellt einen Delete-Dispatcher für die Connection
	 * 
	 * @param conn   die Datenbankverbindung
	 * @param schema das Schema für die URI-Parameter
	 * @return den Delete Dispatcher
	 */
	private static DeleteRessourceDispatcher createDeleteOnDavRessourceDispatcher(DBEntityManager conn, String schema) {
		IDavRepository davRepository = new DavRepository(conn);
		DavUriParameter uriParameter = new DavUriParameter();
		uriParameter.setSchema(schema);
		return new DeleteRessourceDispatcher(davRepository, uriParameter);
	}

	/**
	 * Generiert ein Response-Objekt in Abhängigkeit des Typs der zurückzugebenden
	 * Objektklasse.
	 *
	 * @param result Objektklasse, entspricht dem Ergebnis der Anfrage. In
	 *               Abhängigkeit des Typs (z.B. {@link Multistatus}, {@link Error})
	 *               wird das Response-Objekt inkl. passendem HTTP-Status-Code
	 *               generiert.
	 * @return Response Objekt
	 */
	private static Response buildResponse(Object result) {
		if (result instanceof Multistatus) {
			return Response.status(DavExtendedHttpStatus.MULTISTATUS).type(MediaType.TEXT_XML).entity(result).build();
		} else if (result instanceof Error) {
			return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_XML).entity(result).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).build();
		}
	}

	/**
	 * Generiert ein Response-Objekt mit dem Statuscode 201 (Created) und einem
	 * eTag-Header
	 *
	 * @param eTag Objektklasse für das eTag({@link EntityTag}
	 * @return Response Objekt
	 */
	private static Response buildCreatedResponse(EntityTag eTag) {
		return Response.status(Response.Status.CREATED).header("ETag", eTag.getValue()).build();
	}

	/**
	 * Generiert ein Response-Objekt mit dem Statuscode 204 (No Content) und einem
	 * eTag-Header
	 *
	 * @param eTag Objektklasse für das eTag({@link EntityTag}
	 * @return Response Objekt
	 */
	private static Response buildNoContentResponse(EntityTag eTag) {
		return Response.status(Response.Status.NO_CONTENT).header("ETag", eTag.getValue()).build();
	}

	/**
	 * Generiert ein Response-Objekt mit dem Status BAD_REQUEST und der gegebenen
	 * ErrorMessage
	 *
	 * @param e the Exception
	 * @return Response Objekt
	 */
	private static Response buildBadRequest(Exception e) {
		return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
	}

	/**
	 * Erzeugt einen PropfindCalendarDispatcher mit der gegebenen
	 * Datenbankverbindung.
	 *
	 * @param conn die Datenbankverbindung
	 * @return ein parametrierter Dispatcher
	 */
	private static PropfindCalendarDispatcher createPropfindCalendarDispatcher(DBEntityManager conn) {
		IKalenderRepository repository = createKalenderRepository(conn);
		DavUriParameter uriParameter = new DavUriParameter();
		uriParameter.setSchema(conn.getDBSchema());
		uriParameter.setBenutzerId(String.valueOf(conn.getUser().getId()));
		return new PropfindCalendarDispatcher(repository, uriParameter);
	}

	/**
	 * Erzeugt einen ReportCalendarDispatcher mit der gegebenen Datenbankverbindung
	 *
	 * @param conn die Datenbankverbindung
	 * @return ein parametrierter Dispatcher
	 */
	private static ReportCalendarDispatcher createReportCalendarDispatcher(DBEntityManager conn) {
		IKalenderRepository repository = createKalenderRepository(conn);
		DavUriParameter uriParameter = new DavUriParameter();
		uriParameter.setSchema(conn.getDBSchema());
		uriParameter.setBenutzerId(String.valueOf(conn.getUser().getId()));
		return new ReportCalendarDispatcher(repository, uriParameter);
	}

	/**
	 * Erzeugt einen PutCalendarDispatcher mit der gegebenen Datenbankverbindung.
	 *
	 * @param conn die Datenbankverbindung
	 * @return ein parametrierter Dispatcher
	 */
	private static PutCalendarDispatcher createPutCalendarDispatcher(DBEntityManager conn) {
		IKalenderEintragRepository eintragRepository = createKalenderEintragRepository(conn);
		DavUriParameter uriParameter = new DavUriParameter();
		uriParameter.setSchema(conn.getDBSchema());
		uriParameter.setBenutzerId(String.valueOf(conn.getUser().getId()));
		return new PutCalendarDispatcher(eintragRepository, uriParameter);
	}

	/**
	 * Erzeugt ein neues {@link de.nrw.schule.svws.davapi.data.IKalenderRepository}
	 * mit der gegebenen Datenbankverbindung.
	 *
	 * @param conn die Datenbankverbindung
	 * @return eine Implementierung von
	 *         {@link de.nrw.schule.svws.davapi.data.IKalenderRepository} für den
	 *         Zugriff auf Kalender.
	 */
	private static IKalenderRepository createKalenderRepository(DBEntityManager conn) {
		return new KalenderRepository(conn);
	}

	/**
	 * Erzeugt ein neues
	 * {@link de.nrw.schule.svws.davapi.data.IKalenderEintragRepository} mit der
	 * gegebenen Datenbankverbindung.
	 *
	 * @param conn die Datenbankverbindung
	 * @return eine Implementierung von
	 *         {@link de.nrw.schule.svws.davapi.data.IKalenderEintragRepository} für
	 *         den Zugriff auf Kalender.
	 */
	private static IKalenderEintragRepository createKalenderEintragRepository(DBEntityManager conn) {
		return new KalenderRepository(conn);
	}

	/**
	 * Debug Option, damit Requests nach Insomnia übertragen werden können
	 */
	private static final boolean LOG_INPUTSTREAM = true;

	/**
	 * Loggt abhängig von {@link #LOG_INPUTSTREAM} den Informationen sowie
	 * Inputstream des Requests
	 * 
	 * @param request der request
	 * @return ein ungelesener Inputstream des Requests
	 * @throws IOException
	 */
	private static InputStream getInputStream(HttpServletRequest request) throws IOException {
		InputStream result = request.getInputStream();
		if (LOG_INPUTSTREAM) {
			String input = new String(result.readAllBytes(), StandardCharsets.UTF_8);
			logger.log(LogLevel.WARNING, request.toString());
			logger.log(LogLevel.WARNING, input);
			result = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		}
		return result;
	}

	private static Logger createLogger() {
		Logger logger = new Logger();
		logger.addConsumer(new LogConsumerConsole(true, false));
		return logger;
	}
}
