// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain.rest;

import cs.ut.domain.rest.PlantResource;
import cs.ut.domain.rest.PlantResourceList;
import java.util.List;

privileged aspect PlantResourceList_Roo_JavaBean {
    
    public List<PlantResource> PlantResourceList.getListOfPlantResources() {
        return this.listOfPlantResources;
    }
    
    public void PlantResourceList.setListOfPlantResources(List<PlantResource> listOfPlantResources) {
        this.listOfPlantResources = listOfPlantResources;
    }
    
}
