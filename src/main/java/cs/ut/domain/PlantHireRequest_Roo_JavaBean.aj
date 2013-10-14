// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain;

import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import java.math.BigDecimal;
import java.util.Date;

privileged aspect PlantHireRequest_Roo_JavaBean {
    
    public Date PlantHireRequest.getStartDate() {
        return this.startDate;
    }
    
    public void PlantHireRequest.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date PlantHireRequest.getEndDate() {
        return this.endDate;
    }
    
    public void PlantHireRequest.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public BigDecimal PlantHireRequest.getTotalCost() {
        return this.totalCost;
    }
    
    public void PlantHireRequest.setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
    
    public SiteEngineer PlantHireRequest.getSiteEngineer() {
        return this.siteEngineer;
    }
    
    public void PlantHireRequest.setSiteEngineer(SiteEngineer siteEngineer) {
        this.siteEngineer = siteEngineer;
    }
    
    public int PlantHireRequest.getPlantId() {
        return this.plantId;
    }
    
    public void PlantHireRequest.setPlantId(int plantId) {
        this.plantId = plantId;
    }
    
    public Site PlantHireRequest.getSite() {
        return this.site;
    }
    
    public void PlantHireRequest.setSite(Site site) {
        this.site = site;
    }
    
    public Supplier PlantHireRequest.getSupplier() {
        return this.supplier;
    }
    
    public void PlantHireRequest.setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
}
