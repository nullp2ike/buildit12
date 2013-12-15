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
import cs.ut.domain.bean.PHRSelectDTO;
import cs.ut.repository.PlantHireRequestRepository;

@RequestMapping("/invoices/approve/**")
@Controller
public class InvoiceApproveController {


	@Autowired
	PlantHireRequestRepository repository;
	
//	@Value("${email.rentit}")
//	String rentitEmail;
//	
//	@Value("${email.username}")
//	String builditEmail;
//	
//	@Value("${email.port}")
//	String emailPort;
//	
//	@Value("${email.host}")
//	String emailHost;
//	
//	@Value("${email.password}")
//	String emailPassword;
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayInvoicesThatNeedApproval(ModelMap modelMap) {
		List<PlantHireRequest> phrList = repository.findRequestsByInvoiceStatus(InvoiceStatus.NEEDS_APPROVAL);
		PHRSelectDTO invDTO = new PHRSelectDTO();
		invDTO.setPhrList(phrList);
		modelMap.put("invDTO", invDTO);
		return "invoices/approve/list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String approveInvoice(@Valid PHRSelectDTO invDTO,
			ModelMap modelMap, HttpServletRequest request) {
		String selectedPHR = request.getParameter("radio");
		long phrId = Long.parseLong(selectedPHR);
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(phrId);
		Invoice inv = phr.getInvoice();
		inv.setStatus(InvoiceStatus.APPROVED);
		inv.setIsPaid(true);			//normally there would be some pay method here
		inv.merge();
		
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setFrom(builditEmail);
//		mailMessage.setTo(rentitEmail);
//		mailMessage.setSentDate(new Date());
//		mailMessage.setSubject("The payment has been made for PO: " + inv.getPurchaseOrderId());
//		mailMessage.setText("url: " + inv.getPurchaseOrderHRef() + ", total cost: " + phr.getTotalCost());
//		sender.setPort(Integer.parseInt(emailPort));
//		sender.setHost(emailHost);
//		sender.setPassword(emailPassword);
//		sender.setUsername(builditEmail);
//		sender.send(mailMessage);

		List<PlantHireRequest> phrList = repository.findRequestsByInvoiceStatus(InvoiceStatus.NEEDS_APPROVAL);

		invDTO.setPhrList(phrList);
		modelMap.put("invDTO", invDTO);
		return "invoices/approve/list";
	}
}
