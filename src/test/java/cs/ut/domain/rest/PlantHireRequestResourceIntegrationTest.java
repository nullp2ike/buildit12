package cs.ut.domain.rest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.Base64;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.HireRequestStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import cs.ut.security.Assignments;
import cs.ut.security.Authorities;
import cs.ut.security.Users;

@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@RooIntegrationTest(entity = PlantHireRequestResource.class)
public class PlantHireRequestResourceIntegrationTest {

	Client client;
	SiteEngineer sE;
	Supplier sup;
	Site s;
	Users user;
	Assignments assignments;
	Authorities authorities;
	String password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
	String username = "siteengineer@buildit.com";

	@Value("${supplierurl}")
	String supplierurl;

	@Value("${webappurl}")
	String webappurl;

	@Before
	public void setUp() {
		client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(username, password));
		setRequiredTables("Create", "Supplier1", "FirstName1", "LastName1");
		setUsers();
	}

	private static HttpHeaders getHeaders(String auth) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays
				.asList(org.springframework.http.MediaType.APPLICATION_JSON));
		byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic "
				+ new String(encodedAuthorisation));
		return headers;
	}

	private void setUsers() {
		Users u = new Users();
		u.setEnabled(true);
		u.setPassword(password);
		u.setUsername(username);
		u.persist();
		Authorities auth = new Authorities();
		auth.setAuthority("ROLE_SITE_ENGINEER");
		auth.persist();
		Assignments a = new Assignments();
		a.setAuthority(auth);
		a.setUserBuildit(u);
		a.persist();
	}

	private void setRequiredTables(String siteName, String supplierName,
			String engFirst, String engLast) {
		sE = new SiteEngineer();
		sE.setFirstName(engFirst);
		sE.setLastName(engLast);
		sE.setEmail("se@test.com");
		s = new Site();
		s.setName(siteName);
		sup = new Supplier();
		sup.setName(supplierName);
		sE.persist();
		sE.flush();
		s.persist();
		s.flush();
		sup.flush();
		sup.persist();
	}

	private long setPlantHireRequest(int plantId, int totalCost,
			ApprovalStatus status) {
		PlantHireRequest phr = new PlantHireRequest();
		phr.setEndDate(new Date());
		phr.setStartDate(new Date());
		phr.setPlantId(plantId);
		phr.setSite(s);
		phr.setSiteEngineer(sE);
		phr.setStatus(status);
		phr.setSupplier(sup);
		phr.setTotalCost(new BigDecimal(totalCost));
		phr.persist();
		phr.flush();
		return phr.getId();
	}

	// OK
	@Test
	public void testCreatePHR() {
		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setTotalCost(new BigDecimal(3));
		phrResource.setSite(s);
		phrResource.setEndDate(new Date());
		phrResource.setPlantId(269);
		phrResource.setSiteEngineer(sE);
		phrResource.setStartDate(new Date());
		phrResource.setSupplier(sup);
		phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);

		String json = resourceToJson(phrResource);
		HttpEntity<String> requestEntity = new HttpEntity<String>(json,
				getHeaders(username + ":" + "password"));
		
		RestTemplate template = new RestTemplate();
		ResponseEntity<PlantHireRequestResource> clientResponse = template.postForEntity(webappurl + "/rest/phr/", requestEntity, PlantHireRequestResource.class);
		
		long id = getIdFromLocation(clientResponse.getHeaders().getLocation());
		assertTrue(PlantHireRequest.findPlantHireRequest(id).getTotalCost()
				.compareTo(new BigDecimal(3)) == 0);
		assertTrue(PlantHireRequest.findPlantHireRequest(id).getSite()
				.getName().equals("Create"));
		assertTrue(clientResponse.getBody().get_links().size() == 4);
	}

	// OK
	@Test
	public void testRejectPHR() {
		long phrId = setPlantHireRequest(1, 2, ApprovalStatus.PENDING_APPROVAL);
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				getHeaders(username + ":" + "password"));
		RestTemplate template = new RestTemplate();
		ResponseEntity<PlantHireRequestResource> response = template.exchange(
				webappurl + "/rest/phr/" + phrId + "/reject?comment=declined", HttpMethod.DELETE,
				requestEntity, PlantHireRequestResource.class);
		assertTrue(response.getStatusCode().value() == Status.OK.getStatusCode());
	}

	@Test
	public void testApprovePHR() {
		RestTemplate template = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				getHeaders(username + ":" + "password"));
		ResponseEntity<PlantResourceList> response = template.exchange(
				supplierurl + "/rest/plant/", HttpMethod.GET,
				requestEntity, PlantResourceList.class);
		
		PlantResourceList pRL= response.getBody();
		long plantId = pRL.getListOfPlantResources().get(0)
				.getIdentifier();

		long phrId = setPlantHireRequest((int) plantId, 2,
				ApprovalStatus.PENDING_APPROVAL);
		
		ResponseEntity<PurchaseOrderResource> response2 = template.exchange(
				webappurl + "/rest/phr/" + phrId + "/approve", HttpMethod.PUT,
				requestEntity, PurchaseOrderResource.class);

		assertTrue(response2.getStatusCode().value() == Status.OK.getStatusCode());
		assertTrue(response2.getBody().getStatus().equals(
				HireRequestStatus.PENDING_CONFIRMATION));

	}

	// OK
	@Test
	public void testUpdatePHR() {
		long phrId = setPlantHireRequest(1, 2, ApprovalStatus.PENDING_APPROVAL);

		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setTotalCost(new BigDecimal(100));
		phrResource.setSite(s);
		phrResource.setEndDate(new Date());
		phrResource.setPlantId(1);
		phrResource.setSiteEngineer(sE);
		phrResource.setStartDate(new Date());
		phrResource.setSupplier(sup);
		phrResource.setStatus(ApprovalStatus.APPROVED);
		
		String json = resourceToJson(phrResource);
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(json,
				getHeaders(username + ":" + "password"));
		RestTemplate template = new RestTemplate();
		ResponseEntity<PlantHireRequestResource> response = template.exchange(
				webappurl + "/rest/phr/" + phrId, HttpMethod.PUT,
				requestEntity, PlantHireRequestResource.class);

		assertTrue(response.getStatusCode().value() == Status.OK.getStatusCode());
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getTotalCost()
				.intValue() == 100);
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus()
				.equals(ApprovalStatus.PENDING_APPROVAL));
		assertTrue(response.getBody().get_links().size() == 4);
	}

	@Test
	public void testCancelPHR() {
		long phrId = setPlantHireRequest(1, 100,
				ApprovalStatus.PENDING_APPROVAL);
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				getHeaders(username + ":" + "password"));
		RestTemplate template = new RestTemplate();
		ResponseEntity<Void> response = template.exchange(webappurl + "/rest/phr/" + phrId + "/cancel", HttpMethod.DELETE, requestEntity, Void.class);

		assertTrue(response.getStatusCode().value() == Status.OK.getStatusCode());
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus()
				.equals(ApprovalStatus.CANCELED));
	}

	@Test
	public void testGetPHR() {
		long phrId = setPlantHireRequest(1, 100,
				ApprovalStatus.PENDING_APPROVAL);
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				getHeaders(username + ":" + "password"));
		RestTemplate template = new RestTemplate();
		ResponseEntity<PlantHireRequestResource> response = template.exchange(
				webappurl + "/rest/phr/" + phrId, HttpMethod.GET,
				requestEntity, PlantHireRequestResource.class);
		
		assertTrue(response.getStatusCode().value() == Status.OK.getStatusCode());
		PlantHireRequestResource phrRequestResource = response.getBody();
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus()
				.equals(ApprovalStatus.PENDING_APPROVAL));
		assertTrue(phrRequestResource.get_links().size() == 4);
		
	}

	@Test
	public void testAutomaticPOGeneration() {
		// Get some existing plant id
		RestTemplate template = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				getHeaders(username + ":" + "password"));
		ResponseEntity<PlantResourceList> response = template.exchange(
				supplierurl + "/rest/plant/", HttpMethod.GET,
				requestEntity, PlantResourceList.class);
		long plantId = response.getBody().getListOfPlantResources().get(0)
				.getIdentifier();
		
		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setTotalCost(new BigDecimal(3));
		phrResource.setSite(s);
		phrResource.setEndDate(new Date());
		phrResource.setPlantId((int) plantId);
		phrResource.setSiteEngineer(sE);
		phrResource.setStartDate(new Date());
		phrResource.setSupplier(sup);
		phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);
		
		// Create Plant Hire Request
		String json = resourceToJson(phrResource);
		HttpEntity<String> requestEntityCreate = new HttpEntity<String>(json,
				getHeaders(username + ":" + "password"));
		ResponseEntity<PlantHireRequestResource> clientResponse = template.postForEntity(webappurl + "/rest/phr/", requestEntityCreate, PlantHireRequestResource.class);
		assertTrue(clientResponse.getStatusCode().value() == Status.CREATED
				.getStatusCode());

		Link acceptLink = clientResponse.getBody().get_link("approvePHR");
		String acceptUrl = acceptLink.getHref();

		// Accept Plant Hire Request
		
		ResponseEntity<PurchaseOrderResource> responseAccept = template.exchange(acceptUrl, HttpMethod.PUT,
				requestEntity, PurchaseOrderResource.class);
		assertTrue(responseAccept.getStatusCode().value() == Status.OK
				.getStatusCode());
		String rentitPOUrl = responseAccept.getBody().get_link("getPO").getHref();
		
		ResponseEntity<PurchaseOrderResource> responsePO = template.exchange(rentitPOUrl, HttpMethod.GET,
				requestEntity, PurchaseOrderResource.class);
		assertTrue(responsePO.getStatusCode().value() == Status.OK.getStatusCode());
		assertTrue(responsePO.getBody().getStatus().equals(
				HireRequestStatus.PENDING_CONFIRMATION));
	}

	private long getIdFromLocation(URI location) {
		String locationStr = location.toString();
		String id = locationStr.substring(locationStr.lastIndexOf("/") + 1);
		return Long.parseLong(id);
	}
	
	private String resourceToJson(PlantHireRequestResource phrResource) {
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(phrResource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
