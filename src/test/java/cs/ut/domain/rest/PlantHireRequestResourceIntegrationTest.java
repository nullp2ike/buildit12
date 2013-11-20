package cs.ut.domain.rest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.HireRequestStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;

@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@RooIntegrationTest(entity = PlantHireRequestResource.class)
public class PlantHireRequestResourceIntegrationTest{

	Client client;
	SiteEngineer sE;
	Supplier sup;
	Site s;
	
	@Value("${supplierurl}")
	String supplierurl;
	
	@Value("${webappurl}")
	String webappurl;

	@Before
	public void setUp() {
		client = Client.create();
		setRequiredTables("Create", "Supplier1", "FirstName1", "LastName1");
	}

	private void setRequiredTables(String siteName, String supplierName,
			String engFirst, String engLast) {
		sE = new SiteEngineer();
		sE.setFirstName(engFirst);
		sE.setLastName(engLast);
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

	private long setPlantHireRequest(int plantId, int totalCost, ApprovalStatus status) {
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

		WebResource webResource = client.resource(webappurl + "/rest/phr/");

		ClientResponse clientResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, phrResource);
		assertTrue(clientResponse.getStatus() == Status.CREATED.getStatusCode());
		long id = getIdFromLocation(clientResponse.getLocation());
		assertTrue(PlantHireRequest.findPlantHireRequest(id).getTotalCost()
				.compareTo(new BigDecimal(3)) == 0);
		assertTrue(PlantHireRequest.findPlantHireRequest(id).getSite()
				.getName().equals("Create"));
		PlantHireRequestResource poResource = clientResponse
				.getEntity(PlantHireRequestResource.class);
		assertTrue(poResource.get_links().size() == 4);
	}

	// OK
	@Test
	public void testRejectPHR() {
		long phrId = setPlantHireRequest(1, 2, ApprovalStatus.PENDING_APPROVAL);
		WebResource webResource = client.resource(webappurl + "/rest/phr/"
				+ phrId + "/reject?comment=declined");
		ClientResponse clientResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
		assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
	}

	@Test
	public void testApprovePHR() {
		WebResource plantResource = client.resource(supplierurl + "/rest/plant/");
		ClientResponse cR = plantResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		PlantResourceList plantResourceList = cR
				.getEntity(PlantResourceList.class);
		
		long plantId = plantResourceList.getListOfPlantResources().get(0).getIdentifier();
		
		long phrId = setPlantHireRequest((int)plantId, 2, ApprovalStatus.PENDING_APPROVAL);
		
		WebResource webResource = client.resource(webappurl + "/rest/phr/"
				+ phrId + "/approve");
		
		ClientResponse clientResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).put(ClientResponse.class);
		assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
		PurchaseOrderResource poResource = clientResponse
				.getEntity(PurchaseOrderResource.class);
		assertTrue(poResource.getStatus().equals(HireRequestStatus.PENDING_CONFIRMATION));
		
		
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

		WebResource webResource = client.resource(webappurl + "/rest/phr/"
				+ phrId);
		ClientResponse clientResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, phrResource);
		assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getTotalCost()
				.intValue() == 100);
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus()
				.equals(ApprovalStatus.PENDING_APPROVAL));
		PlantHireRequestResource poResource = clientResponse
				.getEntity(PlantHireRequestResource.class);
		assertTrue(poResource.get_links().size() == 4);
	}

	@Test
	public void testCancelPHR() {
		long phrId = setPlantHireRequest(1, 100, ApprovalStatus.PENDING_APPROVAL);
		WebResource webResource = client.resource(webappurl + "/rest/phr/"
				+ phrId + "/cancel");
		ClientResponse clientResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
		assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus()
				.equals(ApprovalStatus.CANCELED));
	}

	@Test
	public void testGetPHR() {
		long phrId = setPlantHireRequest(1, 100, ApprovalStatus.PENDING_APPROVAL);
		WebResource webResource = client.resource(webappurl + "/rest/phr/"
				+ phrId);
		ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
		assertTrue(response.getStatus() == Status.OK.getStatusCode());
		PlantHireRequestResource phrRequestResource = response
				.getEntity(PlantHireRequestResource.class);
		phrRequestResource.getStatus().equals(ApprovalStatus.PENDING_APPROVAL);
		assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus()
				.equals(ApprovalStatus.PENDING_APPROVAL));
		assertTrue(phrRequestResource.get_links().size() == 4);
	}

	@Test
	public void testAutomaticPOGeneration() {
		//Get some existing plant id
		WebResource plantResource = client.resource(supplierurl + "/rest/plant/");
		ClientResponse clientResponse = plantResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		PlantResourceList plantResourceList = clientResponse
				.getEntity(PlantResourceList.class);
		
		long plantId = plantResourceList.getListOfPlantResources().get(0).getIdentifier();
		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setTotalCost(new BigDecimal(3));
		phrResource.setSite(s);
		phrResource.setEndDate(new Date());
		phrResource.setPlantId((int)plantId);
		phrResource.setSiteEngineer(sE);
		phrResource.setStartDate(new Date());
		phrResource.setSupplier(sup);
		phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);
		
		//Create Plant Hire Request
		WebResource webResource = client.resource(webappurl + "/rest/phr/");
		ClientResponse clientResponseCreate = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, phrResource);
		assertTrue(clientResponseCreate.getStatus() == Status.CREATED.getStatusCode());
		
		PlantHireRequestResource poResource = clientResponseCreate
				.getEntity(PlantHireRequestResource.class);
		Link acceptLink = poResource.get_link("approvePHR");
		String acceptUrl = acceptLink.getHref();		
		
		//Accept Plant Hire Request
		WebResource webResourceAccept = client.resource(acceptUrl);
		ClientResponse clientResponseAccept = webResourceAccept
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class);
		assertTrue(clientResponseAccept.getStatus() == Status.OK.getStatusCode());
		
		PurchaseOrderResource phrResourceAccept = clientResponseAccept
				.getEntity(PurchaseOrderResource.class);
		String rentitPOUrl = phrResourceAccept.get_link("getPO").getHref();
		WebResource webResourcePO = client.resource(rentitPOUrl);
		ClientResponse clientResponseGetPO = webResourcePO
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		assertTrue(clientResponseGetPO.getStatus() == Status.OK.getStatusCode());
		PurchaseOrderResource phrResourceRentitPO = clientResponseGetPO
				.getEntity(PurchaseOrderResource.class);
		assertTrue(phrResourceRentitPO.getStatus().equals(HireRequestStatus.PENDING_CONFIRMATION));
	}

	private long getIdFromLocation(URI location) {
		String locationStr = location.toString();
		String id = locationStr.substring(locationStr.lastIndexOf("/") + 1);
		return Long.parseLong(id);
	}
}
