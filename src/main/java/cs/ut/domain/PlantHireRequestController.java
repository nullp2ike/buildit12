package cs.ut.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cs.ut.domain.bean.PlantDTO;
import cs.ut.domain.rest.PlantResource;
import cs.ut.domain.rest.PlantResourceList;
import cs.ut.domain.rest.PurchaseOrderResource;
import cs.ut.util.LoadProperties;

@RequestMapping("/planthirerequests")
@Controller
@RooWebScaffold(path = "planthirerequests", formBackingObject = PlantHireRequest.class)
public class PlantHireRequestController {

	@RequestMapping(value = "/planthirerequests/queryPlant", method = RequestMethod.GET)
	public String queryPlant(ModelMap model) {
		System.out.println("controller");
		// addDateTimeFormatPatterns(model);
		model.addAttribute("plantquery", new PlantDTO());
		return "planthirerequests/queryPlant/search";
	}

	/*
	 * void addDateTimeFormatPatterns(ModelMap modelMap) { modelMap.put(
	 * "plantHRBean_startr_date_format", DateTimeFormat.patternForStyle("MM",
	 * LocaleContextHolder.getLocale())); modelMap.put(
	 * "plantHRBean_endr_date_format", DateTimeFormat.patternForStyle("MM",
	 * LocaleContextHolder.getLocale())); }
	 */

	@RequestMapping(value = "/planthirerequests/queryPlant", method = RequestMethod.POST)
	public String displayPlant(@Valid PlantDTO plant, ModelMap model,
			HttpServletRequest request) throws DatatypeConfigurationException {

		LoadProperties prop = new LoadProperties();
		Client client = new Client();
		String app_url = prop.loadProperty("supplierurl");
		WebResource webResource = client.resource(app_url + "/rest/plant/");
		 
    	ClientResponse getResponse = webResource.type(MediaType.APPLICATION_XML)
    			.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
    	
    	PlantResourceList plantList = getResponse.getEntity(PlantResourceList.class);
    	
		List<PlantResource> plants = plantList.getListOfPlantResources();
		/*
		for (PlantResource plantResource : plants) {
			System.out.println(plantResource.getPlantName());
			if (plantResource.getPlantName().equalsIgnoreCase(plant.getName()))

			{
				GregorianCalendar start, end;

				PurchaseOrderResource poResource = new PurchaseOrderResource();
				poResource.setPlantResource(plantResource);
				//start = plant.getStartDate().toGregorianCalendar();
				//end = plant.getEndDate().toGregorianCalendar();
				int days = daysBetween(start.getTime(), end.getTime());
				int priceperday = plantResource.getPricePerDay().intValue();
				int totalCost = days * priceperday;
				BigDecimal total = new BigDecimal(totalCost);
				poResource.setTotalCost(total);
				String startDate = plant.getStartDate().toString();
				XMLGregorianCalendar xgc = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(startDate);
				poResource.setEndDate(xgc);
				poResource.setStartDate(xgc);
				plantPort.createPurchaseOrder(poResource);

				// plantPort.createPurchaseOrder(arg0);
			}
		}
		*/
		model.addAttribute("name", plants.get(0).getDescription());
		//model.addAttribute("startDate", plant.getStartDate());
		//model.addAttribute("endDate", plant.getEndDate());
		System.out.println("this is the result mapping");
		return "planthirerequests/queryPlant/result";

	}

	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}
