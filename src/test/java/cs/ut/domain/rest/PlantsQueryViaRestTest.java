package cs.ut.domain.rest;

import static org.junit.Assert.assertTrue;

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

import cs.ut.util.LoadProperties;

@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@RooIntegrationTest(entity = PlantHireRequestResource.class)
public class PlantsQueryViaRestTest extends
		AbstractJUnit4SpringContextTests {

	Client client;
	String supplier_url;
	
	@Before
	public void setUp() {
		
		client = Client.create();
		LoadProperties props = new LoadProperties();
		supplier_url = props.loadProperty("supplierurl");
	}
	
	@Test
	public void testGetPlantsFromSupplier(){
		WebResource webResource = client.resource(supplier_url + "/rest/plant/");
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
