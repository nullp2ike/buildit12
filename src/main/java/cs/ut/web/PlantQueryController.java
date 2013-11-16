package cs.ut.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import cs.ut.domain.bean.PlantDTO;
import cs.ut.domain.rest.PlantHireRequestResource;
import cs.ut.domain.rest.PlantResource;
import cs.ut.domain.rest.PlantResourceList;

@RequestMapping("/planthirerequests/queryPlant/**")
@Controller
public class PlantQueryController {
	
	@Value("${supplierurl}")
	String supplierUrl;
	
	@Value("${webappurl}")
	String webAppUrl;

	@RequestMapping(method = RequestMethod.GET)
	public String searchPlants(ModelMap modelMap) {
		addDateTimeFormatPatterns(modelMap);
		modelMap.put("plantquery", new PlantDTO());
		return "planthirerequests/queryPlant/search";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String displayPlants(@Valid PlantDTO plant, ModelMap modelMap) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String startDate = formatter.format(plant.getStartDate());
		String endDate = formatter.format(plant.getEndDate());
		
		String url = supplierUrl + "/rest/plant/" + "?startDate=" + startDate + "&endDate=" + endDate;
		
		RestTemplate restTemplate = new RestTemplate();
		PlantResourceList plantList = restTemplate.getForObject(url, PlantResourceList.class);
		List<PlantResource> plants = plantList.getListOfPlantResources();
		
		PlantDTO p = new PlantDTO();
		p.setEndDate(plant.getEndDate());
		p.setStartDate(plant.getStartDate());
		p.setPlantList(plants);
		addDateTimeFormatPatterns(modelMap);
		modelMap.put("plantDTO", p);
		return "planthirerequests/queryPlant/result";
	}
	
	@RequestMapping(value = "result", method = RequestMethod.POST)
	public String createPlantHireRequest(@Valid PlantDTO plant, ModelMap modelMap) {
		String url = webAppUrl + "/rest/phr/";
		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setEndDate(new Date());
		phrResource.setStartDate(new Date());
		phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);
		phrResource.setPlantId(plant.getChosenPlantId());
		phrResource.setSite(Site.findAllSites().get(0));
		phrResource.setSiteEngineer(SiteEngineer.findAllSiteEngineers().get(0));
		phrResource.setSupplier(Supplier.findAllSuppliers().get(0));
		phrResource.setTotalCost(new BigDecimal(1));
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, phrResource, String.class);
		List<PlantHireRequest> phrList = PlantHireRequest.findAllPlantHireRequests();
		long id = phrList.get(phrList.size() - 1).getId();
		return "redirect:/planthirerequests/" + id;
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
