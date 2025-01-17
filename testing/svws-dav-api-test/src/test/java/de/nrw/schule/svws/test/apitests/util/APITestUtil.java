package de.nrw.schule.svws.test.apitests.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class APITestUtil {

	public static String readStringFromResourceFile(String resourceName, Object o) {
		try (InputStream is = o.getClass().getClassLoader().getResourceAsStream(resourceName)) {
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			fail(e);
			return null;
		}
	}
}
