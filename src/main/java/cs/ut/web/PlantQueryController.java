package cs.ut.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
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
import cs.ut.domain.bean.PlantHireRequestDTO;
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
		PlantHireRequestDTO phrDTO = new PlantHireRequestDTO();
		phrDTO.setSiteList(Site.findAllSites());
		phrDTO.setEngList(SiteEngineer.findAllSiteEngineers());
		modelMap.put("plantDTO", phrDTO);
		return "planthirerequests/queryPlant/search";
	}

	@RequestMapping(value = "pick", method = RequestMethod.GET)
	public String displayPlants(@Valid PlantHireRequestDTO plant,
			ModelMap modelMap) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String startDate = formatter.format(plant.getStartDate());
		String endDate = formatter.format(plant.getEndDate());

		String url = supplierUrl + "/rest/plant/" + "?startDate=" + startDate
				+ "&endDate=" + endDate;

		RestTemplate restTemplate = new RestTemplate();
		PlantResourceList plantList = restTemplate.getForObject(url,
				PlantResourceList.class);
		List<PlantResource> plants = plantList.getListOfPlantResources();

		PlantHireRequestDTO p = new PlantHireRequestDTO();
		p.setEndDate(plant.getEndDate());
		p.setStartDate(plant.getStartDate());
		p.setPlantList(plants);
		p.setEngineer(plant.getEngineer());
		p.setSite(plant.getSite());
		addDateTimeFormatPatterns(modelMap);
		modelMap.put("plantDTO", p);
		return "planthirerequests/queryPlant/result";
	}

	@RequestMapping(value = "result", method = RequestMethod.POST)
	public String createPlantHireRequest(@Valid PlantHireRequestDTO plant,
			ModelMap modelMap) {

		String plantRequestUrl = supplierUrl + "/rest/plant/"
				+ plant.getPlant();
		RestTemplate template = new RestTemplate();
		PlantResource plantResource = template.getForObject(plantRequestUrl,
				PlantResource.class);

		DateTime startDate = new DateTime(plant.getStartDate());
		DateTime endDate = new DateTime(plant.getEndDate());

		Days d = Days.daysBetween(startDate, endDate);
		int days = d.getDays();

		String url = webAppUrl + "/rest/phr/";
		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setEndDate(plant.getEndDate());
		phrResource.setStartDate(plant.getStartDate());
		phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);
		phrResource.setPlantId(plant.getPlant());
		phrResource.setSite(Site.findSite((long) plant.getSite()));
		phrResource.setSiteEngineer(SiteEngineer.findSiteEngineer((long) plant
				.getEngineer()));
		phrResource.setSupplier(Supplier.findAllSuppliers().get(0));
		phrResource.setTotalCost(plantResource.getPricePerDay().multiply(
				new BigDecimal(days)));

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForEntity(url,
				phrResource, String.class);
		List<PlantHireRequest> phrList = PlantHireRequest
				.findAllPlantHireRequests();
		long id = phrList.get(phrList.size() - 1).getId();
		return "redirect:/planthirerequests/" + id;
	}

	private void addDateTimeFormatPatterns(ModelMap modelMap) {
		modelMap.put(
				"plantHRBean_startr_date_format",
				DateTimeFormat.patternForStyle(("S-"),
						LocaleContextHolder.getLocale()));
		modelMap.put(
				"plantHRBean_endr_date_format",
				DateTimeFormat.patternForStyle(("S-"),
						LocaleContextHolder.getLocale()));
	}
}
