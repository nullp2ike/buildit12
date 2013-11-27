// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain;

import cs.ut.domain.WorksEngineer;
import cs.ut.domain.WorksEngineerController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect WorksEngineerController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String WorksEngineerController.create(@Valid WorksEngineer worksEngineer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, worksEngineer);
            return "worksengineers/create";
        }
        uiModel.asMap().clear();
        worksEngineer.persist();
        return "redirect:/worksengineers/" + encodeUrlPathSegment(worksEngineer.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String WorksEngineerController.createForm(Model uiModel) {
        populateEditForm(uiModel, new WorksEngineer());
        return "worksengineers/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String WorksEngineerController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("worksengineer", WorksEngineer.findWorksEngineer(id));
        uiModel.addAttribute("itemId", id);
        return "worksengineers/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String WorksEngineerController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("worksengineers", WorksEngineer.findWorksEngineerEntries(firstResult, sizeNo));
            float nrOfPages = (float) WorksEngineer.countWorksEngineers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("worksengineers", WorksEngineer.findAllWorksEngineers());
        }
        return "worksengineers/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String WorksEngineerController.update(@Valid WorksEngineer worksEngineer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, worksEngineer);
            return "worksengineers/update";
        }
        uiModel.asMap().clear();
        worksEngineer.merge();
        return "redirect:/worksengineers/" + encodeUrlPathSegment(worksEngineer.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String WorksEngineerController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, WorksEngineer.findWorksEngineer(id));
        return "worksengineers/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String WorksEngineerController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WorksEngineer worksEngineer = WorksEngineer.findWorksEngineer(id);
        worksEngineer.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/worksengineers";
    }
    
    void WorksEngineerController.populateEditForm(Model uiModel, WorksEngineer worksEngineer) {
        uiModel.addAttribute("worksEngineer", worksEngineer);
    }
    
    String WorksEngineerController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
