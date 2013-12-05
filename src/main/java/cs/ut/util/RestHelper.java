package cs.ut.util;

import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.http.HttpHeaders;

import com.sun.jersey.core.util.Base64;

public class RestHelper {
	
	public static HttpHeaders getHeaders(String username, String password) {
		
		String auth = username + ":" + password;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays
				.asList(org.springframework.http.MediaType.APPLICATION_JSON));
		byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic "
				+ new String(encodedAuthorisation));
		return headers;
	}
	
	public static String resourceToJson(Object resource) {
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

}
