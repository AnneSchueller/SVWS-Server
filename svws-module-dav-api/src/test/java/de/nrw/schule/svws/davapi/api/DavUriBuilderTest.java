package de.nrw.schule.svws.davapi.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Diese Klasse enthält die Testroutinen für die Generierung von URIs von
 * Ressourcen für das DAV-API.
 */
public class DavUriBuilderTest {

	/**
	 * Erstellt einen DavUriParameter zum Testen
	 * 
	 * @return das gemockte DavURIParameter Objekt
	 */
	private static DavUriParameter createParameter() {
		DavUriParameter parameter = new DavUriParameter();
		parameter.setResourceCollectionId("AAA");
		parameter.setBenutzerId("BBB");
		parameter.setSchema("SSS");
		parameter.setResourceId("KKK");
		return parameter;
	}

	/**
	 * Testet die Methode {@link DavUriBuilder#getAddressbookUri(DavUriParameter)}
	 */
	@Test
	void getAddressbookUri_returnsExpectedUri() {
		String result = DavUriBuilder.getAddressbookUri(createParameter());
		assertEquals("/db/SSS/dav/adressbuecher/AAA", result);
	}

	/**
	 * Testet die Methode
	 * {@link DavUriBuilder#getAddressbookCollectionUri(DavUriParameter)}
	 */
	@Test
	void getAddressbookCollectionUri_returnsExpectedUri() {
		String result = DavUriBuilder.getAddressbookCollectionUri(createParameter());
		assertEquals("/db/SSS/dav/adressbuecher", result);
	}

	/**
	 * Testet die Methode {@link DavUriBuilder#getAddressEntryUri(DavUriParameter)}
	 */
	@Test
	void getContactUri_returnsExpectedUri() {
		String result = DavUriBuilder.getAddressEntryUri(createParameter());
		assertEquals("/db/SSS/dav/adressbuecher/AAA/KKK.vcf", result);
	}

	/**
	 * Testet die Methode {@link DavUriBuilder#getPrincipalUri(DavUriParameter)}
	 */
	@Test
	void getPrincipalUri_returnsExpectedUri() {
		String result = DavUriBuilder.getPrincipalUri(createParameter());
		assertEquals("/db/SSS/dav/benutzer/BBB", result);
	}

}
