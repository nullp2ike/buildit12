package cs.ut.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.bean.InvoiceApproveDTO;
import cs.ut.domain.bean.PlantHireRequestDTO;
import cs.ut.repository.PlantHireRequestRepository;

@RequestMapping("/invoices/approve/**")
@Controller
public class InvoiceApproveController {

	@Autowired
	PlantHireRequestRepository repository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayInvoicesThatNeedApproval(ModelMap modelMap) {
		List<PlantHireRequest> phrList = repository.findRequestsThatNeedApproval();
		InvoiceApproveDTO invDTO = new InvoiceApproveDTO();
		invDTO.setPhrList(phrList);
		modelMap.put("invDTO", invDTO);
		return "invoices/approve/list";
	}
}
