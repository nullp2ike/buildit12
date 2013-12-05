package cs.ut.domain.rest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import cs.ut.util.RestHelper;

@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
@RooIntegrationTest(entity = PlantHireRequestResource.class)
public class PlantsQueryViaRestTest {
	
	@Value("${supplierurl}")
	String supplierurl;
	
	@Test
	public void testGetPlantsFromSupplier(){
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				RestHelper.getHeaders("user", "password"));

		RestTemplate template = new RestTemplate();
		ResponseEntity<PlantResourceList> response = template.exchange(
				supplierurl + "/rest/plant/", HttpMethod.GET, requestEntity,
				PlantResourceList.class);

		assertTrue(response.getStatusCode().value() == 200);
		assertTrue(response.getBody().getListOfPlantResources().size() > 0);
	}
}
