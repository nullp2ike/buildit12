package cs.ut.domain.rest;

import cs.ut.domain.PlantHireRequest;

public class PlantHireRequestResourceAssembler {
	
	public PlantHireRequestResourceStatus getPlantHireRequestStatus(PlantHireRequest phr){
		PlantHireRequestResourceStatus phrStatus = new PlantHireRequestResourceStatus();
		phrStatus.setStatus(phr.getStatus());
		return phrStatus;
		
	}

}
