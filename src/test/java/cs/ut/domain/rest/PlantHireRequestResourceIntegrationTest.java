package cs.ut.domain.rest;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.LoadTestProperties;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;

@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@RooIntegrationTest(entity = PlantHireRequestResource.class)
public class PlantHireRequestResourceIntegrationTest extends AbstractJUnit4SpringContextTests {

	Client client;
	SiteEngineer sE;
	Supplier sup;
	Site s;
	String app_url;
	
    @Before
    public void setUp(){
    	client = Client.create();
    	LoadTestProperties props = new LoadTestProperties();
    	app_url = props.loadProperty("webappurl");
    	setRequiredTables("Create", "Supplier1", "FirstName1", "LastName1");
    }
    
    private void setRequiredTables(String siteName, String supplierName, String engFirst, String engLast){
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
    
    private long setPlantHireRequest(int totalCost){
    	PlantHireRequest phr = new PlantHireRequest();
    	phr.setEndDate(new Date());
    	phr.setStartDate(new Date());
    	phr.setPlantId(1);
    	phr.setSite(s);
    	phr.setSiteEngineer(sE);
    	phr.setStatus(ApprovalStatus.PENDING_APPROVAL);
    	phr.setSupplier(sup);
    	phr.setTotalCost(new BigDecimal(totalCost));
    	phr.persist();
    	phr.flush();
    	return phr.getId();
    }
    
    @Test
    public void testCreatePHR(){    	
    	PlantHireRequestResource phrResource = new PlantHireRequestResource();
    	phrResource.setTotalCost(new BigDecimal(3));
    	phrResource.setSite(s);
    	phrResource.setEndDate(new Date());
    	phrResource.setPlantId(1);
    	phrResource.setSiteEngineer(sE);
    	phrResource.setStartDate(new Date());
    	phrResource.setSupplier(sup);
    	phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);
    	
    	WebResource webResource = client.resource(app_url + "/rest/phr");
    	
    	ClientResponse postResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, phrResource);
    	assertTrue(postResponse.getStatus() == Status.CREATED.getStatusCode());
    	long id = getIdFromLocation(postResponse.getLocation());
    	assertTrue(PlantHireRequest.findPlantHireRequest(id).getTotalCost().compareTo(new BigDecimal(3)) == 0);
    	assertTrue(PlantHireRequest.findPlantHireRequest(id).getSite().getName().equals("Create"));
    }
    
    @Test
    public void testUpdatePHR(){
    	long phrId = setPlantHireRequest(2);
    	
    	PlantHireRequestResource phrResource = new PlantHireRequestResource();
    	phrResource.setTotalCost(new BigDecimal(3));
    	phrResource.setSite(s);
    	phrResource.setEndDate(new Date());
    	phrResource.setPlantId(1);
    	phrResource.setSiteEngineer(sE);
    	phrResource.setStartDate(new Date());
    	phrResource.setSupplier(sup);
    	phrResource.setStatus(ApprovalStatus.APPROVED);
    	
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + phrId);
    	ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, phrResource);
    	assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.APPROVED));
    }
    
    @Test 
    public void testCancelPHR(){
    	long phrId = setPlantHireRequest(100);
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + phrId + "/cancel");
    	ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
    	assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.CANCELED));
    }
    
    @Test
    public void testGetStatusPHR(){
    	long phrId = setPlantHireRequest(100);
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + phrId + "/status");
      	ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
    	assertTrue(response.getStatus() == Status.OK.getStatusCode());
    	PlantHireRequestResourceStatus status = response.getEntity(PlantHireRequestResourceStatus.class);
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.PENDING_APPROVAL));
    }
    
    @Test
    public void testGetPHR(){
    	long phrId = setPlantHireRequest(100);
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + phrId);
      	ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
    	assertTrue(response.getStatus() == Status.OK.getStatusCode());
    	PlantHireRequestResourceStatus status = response.getEntity(PlantHireRequestResourceStatus.class);
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.PENDING_APPROVAL));
    }
    
	private long getIdFromLocation(URI location) {
		String locationStr = location.toString();
		String id = locationStr.substring(locationStr.lastIndexOf("/")+1);
		return Long.parseLong(id);
	}
}
