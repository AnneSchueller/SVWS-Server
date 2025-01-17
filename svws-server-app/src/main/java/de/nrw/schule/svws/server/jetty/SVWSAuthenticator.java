package de.nrw.schule.svws.server.jetty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.Authentication.User;
import org.eclipse.jetty.util.security.Constraint;

/**
 * Implementiert eine Variante des {@link BasicAuthenticator} für den
 * SVWS-Server, der auch unauthorisierte Zugriff an den Login-Service
 * weiterleitet, wo diese geprüft werden.
 */
public class SVWSAuthenticator extends LoginAuthenticator {

	/**
	 * Erstellt den LoginAuthenticator für den SVWS-Server.
	 */
	public SVWSAuthenticator() {
	}
	
    @Override
    public String getAuthMethod() {
        return Constraint.__BASIC_AUTH;
    }

    @Override
    public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory) throws ServerAuthException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String auth = request.getHeader(HttpHeader.AUTHORIZATION.asString());
        String username = "";
        String password = "";
        if (auth != null) {
            int space = auth.indexOf(' ');
            if ((space > 0) && ("basic".equalsIgnoreCase(auth.substring(0, space)))) {
                auth = new String(Base64.getDecoder().decode(auth.substring(space + 1)), StandardCharsets.ISO_8859_1);
                int colon = auth.indexOf(':');
                if (colon > 0) {
                    username = auth.substring(0, colon);
                    password = auth.substring(colon + 1);
                }
            }
        }
        /* FIXME: Workaround. DAV-API muss im OPTIONS-Request den DAV-Header ausgeben. Zudem unterstützt DAV weitere HTTP-Methods.
        Die Option-Requests werden in der HttpServer-Klasse als Ausnahme behandelt und nicht weiter an das API durchgeleitet.
        Options-Requests und Header werde hier in dieser Klasse gesetzt. In Hinblick auf eine lose Kopplung ist die folgende Implementierung
        keine gute Lösung. Eine Alternativlösung muss diskutiert werden.
        */
        if("OPTIONS".equals(request.getMethod()) && request.getRequestURI().contains("/dav")){
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS, HEAD, PROPFIND, REPORT");
            response.setHeader("DAV", "addressbook, calendar-access");
        }
        //Workaround Ende

        UserIdentity user = login(username, password, request);
        if (user != null) {
            return new UserAuthentication(getAuthMethod(), user);
        }
        try {
            response.setHeader(HttpHeader.WWW_AUTHENTICATE.asString(), "basic realm=\"" + _loginService.getName() + '"');
			final int ACCESS_CONTROL_MAX_AGE_IN_SECONDS = 12 * 60 * 60;
			String origin = request.getHeader("Origin");
			response.setHeader("Vary", "Origin");
			response.setHeader("Access-Control-Allow-Origin", ((origin == null) || ("".equals(origin))) ? "*" : origin);
			response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS, HEAD");
			response.setIntHeader("Access-Control-Max-Age", ACCESS_CONTROL_MAX_AGE_IN_SECONDS);
			// An OPTIONS-Http-Request must be OK for CORS-Preflight-Requests
            response.sendError("OPTIONS".equals(request.getMethod()) ? HttpServletResponse.SC_OK : HttpServletResponse.SC_UNAUTHORIZED);
            return Authentication.SEND_CONTINUE;
        } catch (IOException e) {
            throw new ServerAuthException(e);
        }
    }

    @Override
    public boolean secureResponse(ServletRequest req, ServletResponse res, boolean mandatory, User validatedUser) throws ServerAuthException {
        return true;
    }
	
}
