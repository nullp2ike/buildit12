package cs.ut.web.se;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.Invoice;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import cs.ut.domain.WorksEngineer;
import cs.ut.domain.bean.PlantHireRequestDTO;
import cs.ut.domain.rest.PlantHireRequestResource;
import cs.ut.domain.rest.PlantResource;
import cs.ut.domain.rest.PlantResourceList;
import cs.ut.repository.PlantHireRequestRepository;
import cs.ut.repository.SiteEngineerRepository;
import cs.ut.util.RestHelper;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/se/phrs**")
@Controller
@RooWebScaffold(path = "se/phrs", formBackingObject = PlantHireRequest.class, delete = false)
public class PHRSEController {

	@Value("${supplierurl}")
	String supplierUrl;
	
	@Value("${webappurl}")
	String webAppUrl;
	
	@Autowired
	SiteEngineerRepository siteEngineerRepository;

	@RequestMapping(value = "find", method = RequestMethod.GET)
	public String searchPlants(Model model) {
		addDateTimeFormatPatterns(model);
		PlantHireRequestDTO phrDTO = new PlantHireRequestDTO();
		phrDTO.setSiteList(Site.findAllSites());
		model.addAttribute("plantDTO", phrDTO);
		return "se/phrs/find";
	}

	@RequestMapping(value = "select", method = RequestMethod.GET)
	public String displayPlants(@Valid PlantHireRequestDTO plant,
			Model model) {
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
		p.setSite(plant.getSite());
		addDateTimeFormatPatterns(model);
		model.addAttribute("plantDTO", p);
		return "se/phrs/available";
	}
	
	private void addDateTimeFormatPatterns(Model model) {
	model.addAttribute(
			"plantHRBean_startr_date_format",
			DateTimeFormat.patternForStyle(("S-"),
					LocaleContextHolder.getLocale()));
	model.addAttribute(
			"plantHRBean_endr_date_format",
			DateTimeFormat.patternForStyle(("S-"),
					LocaleContextHolder.getLocale()));
}

	@RequestMapping(value = "request", method = RequestMethod.POST)
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

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String siteEngUsername = authentication.getName();
		String password = authentication.getCredentials().toString();

		SiteEngineer se = siteEngineerRepository
				.findSiteEngineerByEmail(siteEngUsername);

		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		phrResource.setEndDate(plant.getEndDate());
		phrResource.setStartDate(plant.getStartDate());
		phrResource.setStatus(ApprovalStatus.PENDING_APPROVAL);
		phrResource.setPlantId(plant.getPlant());
		phrResource.setSite(Site.findSite((long) plant.getSite()));
		phrResource.setSiteEngineer(se);
		phrResource.setSupplier(Supplier.findAllSuppliers().get(0));
		phrResource.setTotalCost(plantResource.getPricePerDay().multiply(
				new BigDecimal(days)));

		String json = RestHelper.resourceToJson(phrResource);
		HttpEntity<String> requestEntity = new HttpEntity<String>(json,
				RestHelper.getHeaders(siteEngUsername, password));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PlantHireRequestResource> response = restTemplate
				.postForEntity(url, requestEntity,
						PlantHireRequestResource.class);
		String id = getIdFromURL(response.getBody().get_link("updatePHR")
				.getHref());
		return "redirect:/se/phrs/" + id;
	}

	private String getIdFromURL(String url) {
		String id = url.substring(url.lastIndexOf("/") + 1);
		return id;
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("planthirerequest", plantHireRequestRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "se/phrs/show";
    }

	@Autowired
    PlantHireRequestRepository plantHireRequestRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PlantHireRequest plantHireRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plantHireRequest);
            return "se/phrs/create";
        }
        uiModel.asMap().clear();
        plantHireRequestRepository.save(plantHireRequest);
        return "redirect:/se/phrs/" + encodeUrlPathSegment(plantHireRequest.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PlantHireRequest());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (siteEngineerRepository.count() == 0) {
            dependencies.add(new String[] { "siteengineer", "siteengineers" });
        }
        if (Site.countSites() == 0) {
            dependencies.add(new String[] { "site", "sites" });
        }
        if (Supplier.countSuppliers() == 0) {
            dependencies.add(new String[] { "supplier", "suppliers" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "se/phrs/create";
    }

	@RequestMapping(value ="list", method = RequestMethod.GET,produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("planthirerequests", plantHireRequestRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) plantHireRequestRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
        	Authentication authentication = 
        			SecurityContextHolder.getContext().getAuthentication();
        			String name = authentication.getName();
            uiModel.addAttribute("planthirerequests", plantHireRequestRepository.findRequestsBySiteEngineer(name));
        }
        addDateTimeFormatPatterns(uiModel);
        return "se/phrs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PlantHireRequest plantHireRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plantHireRequest);
            return "se/phrs/update";
        }
        uiModel.asMap().clear();
        plantHireRequestRepository.save(plantHireRequest);
        return "redirect:/se/phrs/" + encodeUrlPathSegment(plantHireRequest.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, plantHireRequestRepository.findOne(id));
        return "se/phrs/update";
    }

	void populateEditForm(Model uiModel, PlantHireRequest plantHireRequest) {
        uiModel.addAttribute("plantHireRequest", plantHireRequest);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("approvalstatuses", Arrays.asList(ApprovalStatus.values()));
        uiModel.addAttribute("invoices", Invoice.findAllInvoices());
        uiModel.addAttribute("sites", Site.findAllSites());
        uiModel.addAttribute("siteengineers", siteEngineerRepository.findAll());
        uiModel.addAttribute("suppliers", Supplier.findAllSuppliers());
        uiModel.addAttribute("worksengineers", WorksEngineer.findAllWorksEngineers());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
