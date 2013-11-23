// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.Invoice;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.PlantHireRequestController;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import cs.ut.domain.WorksEngineer;
import cs.ut.repository.PlantHireRequestRepository;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect PlantHireRequestController_Roo_Controller {
    
    @Autowired
    PlantHireRequestRepository PlantHireRequestController.plantHireRequestRepository;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String PlantHireRequestController.create(@Valid PlantHireRequest plantHireRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plantHireRequest);
            return "planthirerequests/create";
        }
        uiModel.asMap().clear();
        plantHireRequestRepository.save(plantHireRequest);
        return "redirect:/planthirerequests/" + encodeUrlPathSegment(plantHireRequest.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String PlantHireRequestController.createForm(Model uiModel) {
        populateEditForm(uiModel, new PlantHireRequest());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (SiteEngineer.countSiteEngineers() == 0) {
            dependencies.add(new String[] { "siteengineer", "siteengineers" });
        }
        if (Site.countSites() == 0) {
            dependencies.add(new String[] { "site", "sites" });
        }
        if (Supplier.countSuppliers() == 0) {
            dependencies.add(new String[] { "supplier", "suppliers" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "planthirerequests/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String PlantHireRequestController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("planthirerequest", plantHireRequestRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "planthirerequests/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String PlantHireRequestController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("planthirerequests", plantHireRequestRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) plantHireRequestRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("planthirerequests", plantHireRequestRepository.findAll());
        }
        addDateTimeFormatPatterns(uiModel);
        return "planthirerequests/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String PlantHireRequestController.update(@Valid PlantHireRequest plantHireRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plantHireRequest);
            return "planthirerequests/update";
        }
        uiModel.asMap().clear();
        plantHireRequestRepository.save(plantHireRequest);
        return "redirect:/planthirerequests/" + encodeUrlPathSegment(plantHireRequest.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String PlantHireRequestController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, plantHireRequestRepository.findOne(id));
        return "planthirerequests/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String PlantHireRequestController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PlantHireRequest plantHireRequest = plantHireRequestRepository.findOne(id);
        plantHireRequestRepository.delete(plantHireRequest);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/planthirerequests";
    }
    
    void PlantHireRequestController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("plantHireRequest_startdate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("plantHireRequest_enddate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    void PlantHireRequestController.populateEditForm(Model uiModel, PlantHireRequest plantHireRequest) {
        uiModel.addAttribute("plantHireRequest", plantHireRequest);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("approvalstatuses", Arrays.asList(ApprovalStatus.values()));
        uiModel.addAttribute("invoices", Invoice.findAllInvoices());
        uiModel.addAttribute("sites", Site.findAllSites());
        uiModel.addAttribute("siteengineers", SiteEngineer.findAllSiteEngineers());
        uiModel.addAttribute("suppliers", Supplier.findAllSuppliers());
        uiModel.addAttribute("worksengineers", WorksEngineer.findAllWorksEngineers());
    }
    
    String PlantHireRequestController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
