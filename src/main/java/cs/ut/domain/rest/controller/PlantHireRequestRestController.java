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
import cs.ut.domain.rest.PlantHireRequestResourceStatus;
import cs.ut.util.ExtendedLink;

@Controller
@RequestMapping("/rest")
public class PlantHireRequestRestController {

	@RequestMapping(method = RequestMethod.POST, value = "/phr")
	public ResponseEntity<Void> createPlantHireRequestResource(
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
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(phr.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<>(headers,
				HttpStatus.CREATED);
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/phr/{id}")
	public ResponseEntity<Void> createPlantHireRequestResource(
			@PathVariable Long id, @RequestBody PlantHireRequestResource res) {

		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		phr.setEndDate(res.getEndDate());
		phr.setPlantId(res.getPlantId());
		phr.setSite(res.getSite());
		phr.setSiteEngineer(res.getSiteEngineer());
		phr.setStartDate(res.getStartDate());
		phr.setSupplier(res.getSupplier());
		phr.setTotalCost(res.getTotalCost());
		phr.setStatus(res.getStatus());
		phr.persist();
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(phr.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<>(headers,
				HttpStatus.OK);
		return response;
	}

	/*
	 * @RequestMapping(method = RequestMethod.DELETE, value =
	 * "/phr/{id}/cancel") public ResponseEntity<Void>
	 * cancelPlantHireRequestResource(
	 * 
	 * @PathVariable Long id,
	 * 
	 * @RequestBody PlantHireRequestResourceStatus res) {
	 * 
	 * PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
	 * phr.setStatus(res.getStatus()); phr.persist(); HttpHeaders headers = new
	 * HttpHeaders(); URI location =
	 * ServletUriComponentsBuilder.fromCurrentRequestUri()
	 * .pathSegment(phr.getId().toString()).build().toUri();
	 * headers.setLocation(location); ResponseEntity<Void> response = new
	 * ResponseEntity<>(headers, HttpStatus.OK); return response; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/phr/{id}/status")
	public ResponseEntity<PlantHireRequestResourceStatus> getPHRStatus(
			@PathVariable Long id) {

		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		PlantHireRequestResourceStatus phrStatus = new PlantHireRequestResourceStatus();
		PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
		phrStatus = assembler.getPlantHireRequestStatus(phr);
		ResponseEntity<PlantHireRequestResourceStatus> response = new ResponseEntity<>(
				phrStatus, HttpStatus.OK);
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/phr/{id}/cancel")
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

	@RequestMapping(method = RequestMethod.GET, value = "/phr/{id}")
	public ResponseEntity<PlantHireRequestResource> getPHR(@PathVariable Long id) {
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		PlantHireRequestResource phrResource = new PlantHireRequestResource();
		PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
		phrResource = assembler.toResource(phr);

		switch (phr.getStatus()) {
		case PENDING_APPROVAL:
			try {
				addMethodLink(phr, phrResource, "cancelPHR", "DELETE");
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		ResponseEntity<PlantHireRequestResource> response = new ResponseEntity<>(
				phrResource, HttpStatus.OK);
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

}
