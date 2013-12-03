package cs.ut.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cs.ut.domain.Invoice;
import cs.ut.domain.InvoiceStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.bean.InvoiceApproveDTO;
import cs.ut.repository.PlantHireRequestRepository;

@RequestMapping("/invoices/approve/**")
@Controller
public class InvoiceApproveController {

	@Autowired
	PlantHireRequestRepository repository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayInvoicesThatNeedApproval(ModelMap modelMap) {
		List<PlantHireRequest> phrList = repository.findRequestsByInvoiceStatus(InvoiceStatus.NEEDS_APPROVAL);
		InvoiceApproveDTO invDTO = new InvoiceApproveDTO();
		invDTO.setPhrList(phrList);
		modelMap.put("invDTO", invDTO);
		return "invoices/approve/list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String createPlantHireRequest(@Valid InvoiceApproveDTO invDTO,
			ModelMap modelMap, HttpServletRequest request) {
		String selectedPHR = request.getParameter("radio");
		long phrId = Long.parseLong(selectedPHR);
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(phrId);
		Invoice inv = phr.getInvoice();
		inv.setStatus(InvoiceStatus.APPROVED);
		inv.merge();
		List<PlantHireRequest> phrList = repository.findRequestsByInvoiceStatus(InvoiceStatus.NEEDS_APPROVAL);

		invDTO.setPhrList(phrList);
		modelMap.put("invDTO", invDTO);
		return "invoices/approve/list";
	}
}
