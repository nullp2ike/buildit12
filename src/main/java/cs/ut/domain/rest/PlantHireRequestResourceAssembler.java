package cs.ut.domain.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.rest.controller.PlantHireRequestRestController;

public class PlantHireRequestResourceAssembler extends ResourceAssemblerSupport<PlantHireRequest, PlantHireRequestResource>{
	
	public PlantHireRequestResourceAssembler () {
		super(PlantHireRequestRestController.class,PlantHireRequestResource.class);
		}

	@Override
	public PlantHireRequestResource toResource(PlantHireRequest phr) {
		PlantHireRequestResource phrResource = createResourceWithId(phr.getId(), phr);
		phrResource.setEndDate(phr.getEndDate());
		phrResource.setStartDate(phr.getStartDate());
		phrResource.setTotalCost(phr.getTotalCost());
		phrResource.setPlantId(phr.getPlantId());
		phrResource.setSite(phr.getSite());
		phrResource.setSiteEngineer(phr.getSiteEngineer());
		phrResource.setSupplier(phr.getSupplier());
		phrResource.setStatus(phr.getStatus());
		return phrResource;
	}

}
