package cs.ut.web;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.core.util.Base64;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.bean.PlantHireRequestApproveDTO;
import cs.ut.domain.rest.PlantHireRequestResource;
import cs.ut.domain.rest.PurchaseOrderResource;
import cs.ut.repository.PlantHireRequestRepository;

@RequestMapping("/planthirerequests/pending/**")
@Controller
public class PlantHireRequestApproveController {

	@Autowired
	PlantHireRequestRepository repository;
	
	@Value("${webappurl}")
	String webappurl;
	
	@Value("${supplierurl}")
	String supplierurl;
	
	@RequestMapping(method = RequestMethod.GET)
	public String displayInvoicesThatNeedApproval(ModelMap modelMap) {
		List<PlantHireRequest> phrList = repository.findRequestsByApprovalStatus(ApprovalStatus.PENDING_APPROVAL);
		PlantHireRequestApproveDTO phrAproveDTO = new PlantHireRequestApproveDTO();
		phrAproveDTO.setPhrList(phrList);
		phrAproveDTO.setSupplierUrl(supplierurl);
		modelMap.put("phrApproveDTO", phrAproveDTO);
		return "planthirerequests/pending/list";
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public String acceptOrRejectPO(@Valid PlantHireRequestApproveDTO phrAproveDTO, HttpServletRequest request, ModelMap modelMap) {
     	String selectedPHR = request.getParameter("radio");
     	String rejectComment = request.getParameter("rejectionReason");
    	String decision = request.getParameter("submit");;
		RestTemplate template = new RestTemplate();
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		String worksEngUsername = authentication.getName();
		String password = authentication.getCredentials().toString();
		HttpEntity<String> requestEntity = new HttpEntity<String>(getHeaders(worksEngUsername + ":" + password));
    	if(decision.equals("Approve")){
    		String acceptUrl = webappurl + "/rest/phr/" + selectedPHR + "/approve";
    		template.exchange(
    				acceptUrl, HttpMethod.PUT, requestEntity, PurchaseOrderResource.class);
    	}else if(decision.equals("Reject")){
    		String rejectUrl = webappurl + "/rest/phr/" + selectedPHR
			+ "/reject?comment=" + rejectComment;
    		template.exchange(
    				rejectUrl, HttpMethod.DELETE,
    				requestEntity, PlantHireRequestResource.class);
    	}else{
    		System.out.println("ERROR, this should not happen");
    	}
   
    	List<PlantHireRequest> phrList = repository.findRequestsByApprovalStatus(ApprovalStatus.PENDING_APPROVAL);
		phrAproveDTO.setPhrList(phrList);
		phrAproveDTO.setSupplierUrl(supplierurl);
		modelMap.put("phrApproveDTO", phrAproveDTO);
    	return "redirect:/planthirerequests/pending/list";
    }
	
	private static HttpHeaders getHeaders(String auth) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays
				.asList(org.springframework.http.MediaType.APPLICATION_JSON));
		byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
		headers.add("Authorization", "Basic "
				+ new String(encodedAuthorisation));
		return headers;
	}
}
