package cs.ut.domain;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cs.ut.domain.soap.client.PlantResource;
import cs.ut.domain.soap.client.PlantResourceList;
import cs.ut.domain.soap.client.PlantSOAPService;
import cs.ut.domain.soap.client.PlantSOAPServiceService;

@RooIntegrationTest(entity = Plant.class)
public class PlantIntegrationTest {

    @Test
    public void testGetAllPlants() {
    	
    	PlantSOAPService catalog = new
    	PlantSOAPServiceService().getPlantSOAPServicePort();
    	PlantResourceList plants = catalog.getAllPlants();
    	
    	List<PlantResource> plantResList = plants.getListOfPlantResources();
    	assertTrue(plantResList.get(0).getPlantName() != null);
    }
}
