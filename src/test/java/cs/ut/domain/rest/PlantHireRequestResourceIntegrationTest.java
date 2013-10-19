package cs.ut.domain.rest;
import static org.junit.Assert.assertTrue;

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
    }
    
    
	private long getIdFromLocation(URI location) {
		String locationStr = location.toString();
		String id = locationStr.substring(locationStr.lastIndexOf("/")+1);
		return Long.parseLong(id);
	}
    
    private void setRequiredTables(){
    	sE = new SiteEngineer();
    	sE.setFirstName("FirstName");
    	sE.setLastName("LastName");
    	s = new Site();
    	s.setName("SiteName");
    	sup = new Supplier();
    	sup.setName("SupplierName");
    	sE.persist();
    	sE.flush();
    	s.persist();
    	s.flush();
    	sup.flush();
    	sup.persist();
    }
    
    /*
    private long setPlantHireRequest(int totalCost){
    	PlantHireRequest phr = new PlantHireRequest();
    	phr.setEndDate(new Date());
    	phr.setStartDate(new Date());
    	phr.setPlantId(1);
    	phr.setSite(s);
    	phr.setSiteEngineer(sE);
    	phr.setStatus(ApprovalStatus.PENDING);
    	phr.setSupplier(sup);
    	phr.setTotalCost(new BigDecimal(totalCost));
    	phr.persist();
    	phr.flush();
    	return phr.getId();
    }
    */
    
    private long makePlantHireRequest(){    	
    	PlantHireRequestResource phrResource = new PlantHireRequestResource();
    	phrResource.setTotalCost(new BigDecimal(3));
    	phrResource.setSite(s);
    	phrResource.setEndDate(new Date());
    	phrResource.setPlantId(0);
    	phrResource.setSiteEngineer(sE);
    	phrResource.setStartDate(new Date());
    	phrResource.setSupplier(sup);
    	phrResource.setStatus(ApprovalStatus.PENDING);
    	
    	WebResource webResource = client.resource(app_url + "/rest/phr");
    	
    	ClientResponse postResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, phrResource);
    	return getIdFromLocation(postResponse.getLocation());
    }
    
    @Test
    public void testCreatePHR(){
    	setRequiredTables();
    	
    	PlantHireRequestResource phrResource = new PlantHireRequestResource();
    	phrResource.setTotalCost(new BigDecimal(3));
    	phrResource.setSite(s);
    	phrResource.setEndDate(new Date());
    	phrResource.setPlantId(0);
    	phrResource.setSiteEngineer(sE);
    	phrResource.setStartDate(new Date());
    	phrResource.setSupplier(sup);
    	phrResource.setStatus(ApprovalStatus.PENDING);
    	
    	WebResource webResource = client.resource(app_url + "/rest/phr");
    	
    	ClientResponse postResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, phrResource);
    	assertTrue(postResponse.getStatus() == Status.CREATED.getStatusCode());
    	long id = getIdFromLocation(postResponse.getLocation());
    	assertTrue(PlantHireRequest.findPlantHireRequest(id).getTotalCost().compareTo(new BigDecimal(3)) == 0);
    	assertTrue(PlantHireRequest.findPlantHireRequest(id).getSite().getName().equals("SiteName"));
    }
    
    @Test
    public void testUpdatePHR(){
    	setRequiredTables();
    	long phrId = makePlantHireRequest();
    	
    	PlantHireRequestResource phrResource = new PlantHireRequestResource();
    	phrResource.setTotalCost(new BigDecimal(3));
    	phrResource.setSite(s);
    	phrResource.setEndDate(new Date());
    	phrResource.setPlantId(1);
    	phrResource.setSiteEngineer(sE);
    	phrResource.setStartDate(new Date());
    	phrResource.setSupplier(sup);
    	phrResource.setStatus(ApprovalStatus.APPROVED);
    	
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + String.valueOf(phrId));
    	ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, phrResource);
    	assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.APPROVED));
    }
    
    @Test 
    public void testCancelPHR(){
    	setRequiredTables();
    	long phrId = makePlantHireRequest();
    	PlantHireRequestResourceStatus phrResource = new PlantHireRequestResourceStatus();
    	phrResource.setStatus(ApprovalStatus.CANCELED);
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + String.valueOf(phrId) + "/cancel");
    	ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, phrResource);
    	assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.CANCELED));
    }
    
    @Test
    public void testGetStatusPHR(){
    	setRequiredTables();
    	long phrId = makePlantHireRequest();
    	WebResource webResource = client.resource(app_url + "/rest/phr/" + String.valueOf(phrId) + "/status");
      	ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
    	assertTrue(response.getStatus() == Status.OK.getStatusCode());
    	PlantHireRequestResourceStatus status = response.getEntity(PlantHireRequestResourceStatus.class);
    	assertTrue(PlantHireRequest.findPlantHireRequest(phrId).getStatus().equals(ApprovalStatus.PENDING));
    	
    }
}
