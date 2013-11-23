package cs.ut.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.bean.PlantHireRequestApproveDTO;
import cs.ut.domain.rest.PlantHireRequestResource;
import cs.ut.repository.PlantHireRequestRepository;

@RequestMapping("/planthirerequests/pending/**")
@Controller
public class PlantHireRequestApproveController {

	@Autowired
	PlantHireRequestRepository repository;
	
	@Value("${webappurl}")
	String webappurl;
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayInvoicesThatNeedApproval(ModelMap modelMap) {
		List<PlantHireRequest> phrList = repository.findRequestsByApprovalStatus(ApprovalStatus.PENDING_APPROVAL);
		PlantHireRequestApproveDTO phrAproveDTO = new PlantHireRequestApproveDTO();
		phrAproveDTO.setPhrList(phrList);
		modelMap.put("phrApproveDTO", phrAproveDTO);
		return "planthirerequests/pending/list";
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public String acceptOrRejectPO(@Valid PlantHireRequestApproveDTO phrAproveDTO, HttpServletRequest request, ModelMap modelMap) {
     	String selectedPHR = request.getParameter("radio");
     	String rejectComment = request.getParameter("rejectionReason");
    	String decision = request.getParameter("submit");;
		RestTemplate template = new RestTemplate();
    	if(decision.equals("Approve")){
    		String acceptUrl = webappurl + "/rest/phr/" + selectedPHR
			+ "/approve";
    		template.put(acceptUrl, PlantHireRequestResource.class);
    		
    	}else if(decision.equals("Reject")){
    		String rejectUrl = webappurl + "/rest/phr/" + selectedPHR
			+ "/reject?comment=" + rejectComment;
    		template.delete(rejectUrl, PlantHireRequestResource.class);
    	}else{
    		System.out.println("ERROR, this should not happen");
    	}
   
    	List<PlantHireRequest> phrList = repository.findRequestsByApprovalStatus(ApprovalStatus.PENDING_APPROVAL);
		phrAproveDTO.setPhrList(phrList);
		modelMap.put("phrApproveDTO", phrAproveDTO);
    	return "planthirerequests/pending/list";
    }
}
