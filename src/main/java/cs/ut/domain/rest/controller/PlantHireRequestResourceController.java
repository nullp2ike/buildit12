package cs.ut.domain.rest.controller;

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

import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.rest.PlantHireRequestResource;
import cs.ut.domain.rest.PlantHireRequestResourceAssembler;
import cs.ut.domain.rest.PlantHireRequestResourceStatus;

@Controller
@RequestMapping("/rest")
public class PlantHireRequestResourceController {
	
	@RequestMapping(method = RequestMethod.POST, value="/phr")
	public ResponseEntity<Void> createPlantHireRequestResource(@RequestBody PlantHireRequestResource res) {
		
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
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(phr.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<>(headers, HttpStatus.CREATED);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/phr/{id}")
	public ResponseEntity<Void> createPlantHireRequestResource(@PathVariable Long id, @RequestBody PlantHireRequestResource res) {
		
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
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(phr.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<>(headers, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/phr/{id}/cancel")
	public ResponseEntity<Void> cancelPlantHireRequestResource(@PathVariable Long id, @RequestBody PlantHireRequestResourceStatus res) {
		
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		phr.setStatus(res.getStatus());
		phr.persist();
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().pathSegment(phr.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<>(headers, HttpStatus.OK);
		return response;	
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/phr/{id}/status")
	public ResponseEntity<PlantHireRequestResourceStatus> cancelPlantHireRequestResource(@PathVariable Long id) {
		
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		PlantHireRequestResourceStatus phrStatus = new PlantHireRequestResourceStatus();
		PlantHireRequestResourceAssembler assembler = new PlantHireRequestResourceAssembler();
		phrStatus = assembler.getPlantHireRequestStatus(phr);
		ResponseEntity<PlantHireRequestResourceStatus> response = new ResponseEntity<>(phrStatus, HttpStatus.OK);
		return response;	
	}
	
	

}
