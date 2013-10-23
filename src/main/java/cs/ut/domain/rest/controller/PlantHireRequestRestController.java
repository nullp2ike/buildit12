package cs.ut.domain.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.rest.PlantHireRequestResource;
import cs.ut.domain.rest.PlantHireRequestResourceAssembler;
import cs.ut.util.ExtendedLink;

@Controller
@RequestMapping("/rest/phr/")
public class PlantHireRequestRestController {

	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<PlantHireRequestResource> createPHR(
			@RequestBody PlantHireRequestResource res) {

		PlantHireRequest phr = new PlantHireRequest();
		phr.setEndDate(res.getEndDate());
		phr.setPlantId(res.getPlantId());
		phr.setSite(res.getSite());
		phr.setSiteEngineer(res.getSiteEngineer());
		phr.setStartDate(res.getStartDate());
		phr.setSupplier(res.getSupplier());
		phr.setTotalCost(res.getTotalCost());
		phr.setStatus(res.getStatus());
		phr.persist();
		
		PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
		PlantHireRequestResource resource = assembler.toResource(phr);
	
		try {
			addMethodLink(phr, resource, "approvePHR", "PUT");
			addMethodLink(phr, resource, "rejectPHR", "DELETE");
			addMethodLinkWithResource(phr, resource, "updatePHR", "PUT");
			addMethodLink(phr, resource, "cancelPHR", "DELETE");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(phr.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<PlantHireRequestResource> response = new ResponseEntity<>(resource, headers,
				HttpStatus.CREATED);
		return response;
	}
	
	
	//OK
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}/reject")
	public ResponseEntity<Void> rejectPHR(@PathVariable("id") Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		ResponseEntity<Void> response;
		if (phr.getStatus().equals(ApprovalStatus.PENDING_APPROVAL)) {
			phr.setStatus(ApprovalStatus.REJECTED);
			phr.persist();		
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}
	
	
	//OK
	@RequestMapping(method = RequestMethod.PUT, value = "{id}/approve")
	public ResponseEntity<Void> approvePHR(@PathVariable("id") Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		ResponseEntity<Void> response;
		if (phr.getStatus().equals(ApprovalStatus.PENDING_APPROVAL)) {
			phr.setStatus(ApprovalStatus.APPROVED);
			phr.persist();
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}

	//OK
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<PlantHireRequestResource> updatePHR(
			@PathVariable Long id, @RequestBody PlantHireRequestResource res) {
		ResponseEntity<PlantHireRequestResource> response;
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		
		if (phr.getStatus().equals(ApprovalStatus.PENDING_APPROVAL)) {
			phr.setEndDate(res.getEndDate());
			phr.setPlantId(res.getPlantId());
			phr.setSite(res.getSite());
			phr.setSiteEngineer(res.getSiteEngineer());
			phr.setStartDate(res.getStartDate());
			phr.setSupplier(res.getSupplier());
			phr.setTotalCost(res.getTotalCost());
			phr.persist();
			
			PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
			PlantHireRequestResource resource = assembler.toResource(phr);
		
			try {
				addMethodLink(phr, resource, "approvePHR", "PUT");
				addMethodLink(phr, resource, "rejectPHR", "DELETE");
				addMethodLinkWithResource(phr, resource, "updatePHR", "PUT");
				addMethodLink(phr, resource, "cancelPHR", "DELETE");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			
			response = new ResponseEntity<>(resource, HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}

	
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}/cancel")
	public ResponseEntity<Void> cancelPHR(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		ResponseEntity<Void> response;
		if (phr.getStatus().equals(ApprovalStatus.PENDING_APPROVAL)) {
			phr.setStatus(ApprovalStatus.CANCELED);
			phr.persist();
			response = new ResponseEntity<>(HttpStatus.OK);
		}else{
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}		
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<PlantHireRequestResource> getPHR(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		PlantHireRequestResource resource = new PlantHireRequestResource();
		PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
		resource = assembler.toResource(phr);

		switch (phr.getStatus()) {
		case PENDING_APPROVAL:
			
			try {
				addMethodLink(phr, resource, "approvePHR", "PUT");
				addMethodLink(phr, resource, "rejectPHR", "DELETE");
				addMethodLinkWithResource(phr, resource, "updatePHR", "PUT");
				addMethodLink(phr, resource, "cancelPHR", "DELETE");
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		default:
			break;
		}

		ResponseEntity<PlantHireRequestResource> response = new ResponseEntity<>(
				resource, HttpStatus.OK);
		return response;
	}

	private void addMethodLink(PlantHireRequest phr,
			PlantHireRequestResource resource, String action, String method)
			throws NoSuchMethodException {
		Method methodLink = PlantHireRequestRestController.class.getMethod(
				action, Long.class);
		String link = linkTo(methodLink, phr.getId()).toUri().toString();
		resource.add(new ExtendedLink(link, action, method));
	}
	
	private void addMethodLinkWithResource(PlantHireRequest phr,
			PlantHireRequestResource resource, String action,  String method) throws NoSuchMethodException {
		Method methodLink = PlantHireRequestRestController.class.getMethod(
				action, Long.class, PlantHireRequestResource.class);
		String link = linkTo(methodLink, phr.getId()).toUri()
				.toString();
		resource.add(new ExtendedLink(link, action, method));
	}

}
