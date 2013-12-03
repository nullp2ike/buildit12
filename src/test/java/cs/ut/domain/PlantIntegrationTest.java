package cs.ut.domain;

import org.springframework.roo.addon.test.RooIntegrationTest;

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
