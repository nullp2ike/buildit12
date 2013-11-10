package cs.ut.domain;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import cs.ut.domain.soap.client.PlantResource;
import cs.ut.domain.soap.client.PlantResourceList;
import cs.ut.domain.soap.client.PlantSOAPService;
import cs.ut.domain.soap.client.po.PurchaseOrderSOAPService;

@RooIntegrationTest(entity = PurchaseOrder.class)
public class PurchaseOrderIntegrationTest {
	
	@Autowired
	PurchaseOrderSOAPService poService;

	@Autowired
	PlantSOAPService catalogue;
	
    @Test
    public void testMarkerMethod() {
    }
    
    @Test
    public void testCreatePurchaseOrderViaSoap() throws DatatypeConfigurationException{
    	
    	XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 11, 04, 0, 0, 0, 0, 0);
		XMLGregorianCalendar endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 11, 11, 0, 0, 0, 0, 0);
    	
		PlantResourceList availablePlants = catalogue.getAvailablePlants(startDate, endDate);
    	List<PlantResource> availablePlantList = availablePlants.getListOfPlantResources();
    	int sizeBefore = availablePlantList.size();
    	long plantId =  availablePlantList.get(0).getIdentifier();
		poService.createPurchaseOrder(plantId, startDate, endDate);
		
		PlantResourceList availablePlantsAfter = catalogue.getAvailablePlants(startDate, endDate);
    	List<PlantResource> availablePlantListAfter = availablePlantsAfter.getListOfPlantResources();
    	int sizeAfter = availablePlantListAfter.size();
    	
    	assertTrue(sizeBefore > sizeAfter);
    }
}
