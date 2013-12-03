package cs.ut.domain;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import cs.ut.domain.soap.client.PlantResource;
import cs.ut.domain.soap.client.PlantResourceList;
import cs.ut.domain.soap.client.PlantSOAPService;
import cs.ut.domain.soap.client.PlantSOAPServiceService;

@RooIntegrationTest(entity = Plant.class)
public class PlantIntegrationTest {
	

//	@Test
//	public void testGetAllPlants() {
//
//		PlantSOAPService catalog = new PlantSOAPServiceService()
//				.getPlantSOAPServicePort();
//		PlantResourceList plants = catalog.getAllPlants();
//
//		List<PlantResource> plantResList = plants.getListOfPlantResources();
//		assertTrue(plantResList.get(0).getPlantName() != null);
//	}

//	@Test
//	public void testGetAvailablePlants() throws DatatypeConfigurationException {
//		PlantSOAPService catalog = new PlantSOAPServiceService()
//				.getPlantSOAPServicePort();
//		XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 11, 04, 0, 0, 0, 0, 0);
//		XMLGregorianCalendar endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 11, 11, 0, 0, 0, 0, 0);
//		PlantResourceList availablePlants = catalog.getAvailablePlants(startDate, endDate);
//		PlantResourceList allPlants = catalog.getAllPlants();
//
//		List<PlantResource> availablePlantsList = availablePlants.getListOfPlantResources();
//		List<PlantResource> allPlantList = allPlants.getListOfPlantResources();
//		assertTrue(availablePlantsList.size() > 0);
//		assertTrue(allPlantList.size() > 0);
//		assertTrue(allPlantList.size() > availablePlantsList.size());
//	}
}
