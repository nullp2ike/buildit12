package cs.ut.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cs.ut.domain.bean.PlantDTO;
import cs.ut.domain.rest.PlantResource;
import cs.ut.domain.rest.PlantResourceList;
import cs.ut.util.LoadProperties;

@RequestMapping("/planthirerequests/queryPlant/**")
@Controller
public class PlantQueryController {

	@RequestMapping(method = RequestMethod.GET)
	public String searchPlants(ModelMap modelMap) {
		addDateTimeFormatPatterns(modelMap);
		modelMap.put("plantquery", new PlantDTO());
		return "planthirerequests/queryPlant/search";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String displayPlants(@Valid PlantDTO plant, ModelMap modelMap) {
		LoadProperties prop = new LoadProperties();
		Client client = new Client();
		String app_url = prop.loadProperty("supplierurl");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String startDate = formatter.format(plant.getStartDate());
		String endDate = formatter.format(plant.getEndDate());
		WebResource webResource = client.resource(app_url + "/rest/plant/" + "?startDate=" + startDate + "&endDate=" + endDate);

		ClientResponse getResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);

		PlantResourceList plantList = getResponse
				.getEntity(PlantResourceList.class);

		List<PlantResource> plants = plantList.getListOfPlantResources();
		
		PlantDTO p = new PlantDTO();
		p.setPlantList(plants);
		modelMap.put("plantDTO", p);
		return "planthirerequests/queryPlant/result";
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public String createPlantHireRequest(@Valid PlantDTO plant, ModelMap modelMap, @PathVariable("id") Long id) {
		
		return "/planthirerequests?page=1&size=10";
	}

	private void addDateTimeFormatPatterns(ModelMap modelMap) {
		modelMap.put(
				"plantHRBean_startr_date_format",
				DateTimeFormat.patternForStyle("MM",
						LocaleContextHolder.getLocale()));
		modelMap.put(
				"plantHRBean_endr_date_format",
				DateTimeFormat.patternForStyle("MM",
						LocaleContextHolder.getLocale()));
	}
}
