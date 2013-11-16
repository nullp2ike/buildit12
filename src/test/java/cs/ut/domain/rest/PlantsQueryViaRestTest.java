package cs.ut.domain.rest;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@RooIntegrationTest(entity = PlantHireRequestResource.class)
public class PlantsQueryViaRestTest extends
		AbstractJUnit4SpringContextTests {

	Client client;
	
	@Value("${supplierurl}")
	String supplierurl;
	
	@Before
	public void setUp() {
		
		client = Client.create();
	}
	
	@Test
	public void testGetPlantsFromSupplier(){
		WebResource webResource = client.resource(supplierurl + "/rest/plant/");
		ClientResponse clientResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.get(ClientResponse.class);
		assertTrue(clientResponse.getStatus() == Status.OK.getStatusCode());
		PlantResourceList plantResourceList = clientResponse
				.getEntity(PlantResourceList.class);
		assertTrue(plantResourceList.getListOfPlantResources().size() > 0);
	}

}
