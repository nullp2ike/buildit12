// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain.rest;

import cs.ut.domain.rest.PlantResource;
import java.math.BigDecimal;

privileged aspect PlantResource_Roo_JavaBean {
    
    public long PlantResource.getIdentifier() {
        return this.identifier;
    }
    
    public void PlantResource.setIdentifier(long identifier) {
        this.identifier = identifier;
    }
    
    public String PlantResource.getPlantName() {
        return this.plantName;
    }
    
    public void PlantResource.setPlantName(String plantName) {
        this.plantName = plantName;
    }
    
    public BigDecimal PlantResource.getPricePerDay() {
        return this.pricePerDay;
    }
    
    public void PlantResource.setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    
    public String PlantResource.getDescription() {
        return this.description;
    }
    
    public void PlantResource.setDescription(String description) {
        this.description = description;
    }
    
}
