// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cs.ut.domain;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.domain.PlantHireRequestDataOnDemand;
import cs.ut.domain.Site;
import cs.ut.domain.SiteDataOnDemand;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.SiteEngineerDataOnDemand;
import cs.ut.domain.Supplier;
import cs.ut.domain.SupplierDataOnDemand;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect PlantHireRequestDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PlantHireRequestDataOnDemand: @Component;
    
    private Random PlantHireRequestDataOnDemand.rnd = new SecureRandom();
    
    private List<PlantHireRequest> PlantHireRequestDataOnDemand.data;
    
    @Autowired
    SiteDataOnDemand PlantHireRequestDataOnDemand.siteDataOnDemand;
    
    @Autowired
    SiteEngineerDataOnDemand PlantHireRequestDataOnDemand.siteEngineerDataOnDemand;
    
    @Autowired
    SupplierDataOnDemand PlantHireRequestDataOnDemand.supplierDataOnDemand;
    
    public PlantHireRequest PlantHireRequestDataOnDemand.getNewTransientPlantHireRequest(int index) {
        PlantHireRequest obj = new PlantHireRequest();
        setComment(obj, index);
        setEndDate(obj, index);
        setPlantId(obj, index);
        setSite(obj, index);
        setSiteEngineer(obj, index);
        setStartDate(obj, index);
        setStatus(obj, index);
        setSupplier(obj, index);
        setTotalCost(obj, index);
        return obj;
    }
    
    public void PlantHireRequestDataOnDemand.setComment(PlantHireRequest obj, int index) {
        String comment = "comment_" + index;
        obj.setComment(comment);
    }
    
    public void PlantHireRequestDataOnDemand.setEndDate(PlantHireRequest obj, int index) {
        Date endDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setEndDate(endDate);
    }
    
    public void PlantHireRequestDataOnDemand.setPlantId(PlantHireRequest obj, int index) {
        int plantId = index;
        obj.setPlantId(plantId);
    }
    
    public void PlantHireRequestDataOnDemand.setSite(PlantHireRequest obj, int index) {
        Site site = siteDataOnDemand.getRandomSite();
        obj.setSite(site);
    }
    
    public void PlantHireRequestDataOnDemand.setSiteEngineer(PlantHireRequest obj, int index) {
        SiteEngineer siteEngineer = siteEngineerDataOnDemand.getRandomSiteEngineer();
        obj.setSiteEngineer(siteEngineer);
    }
    
    public void PlantHireRequestDataOnDemand.setStartDate(PlantHireRequest obj, int index) {
        Date startDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setStartDate(startDate);
    }
    
    public void PlantHireRequestDataOnDemand.setStatus(PlantHireRequest obj, int index) {
        ApprovalStatus status = ApprovalStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public void PlantHireRequestDataOnDemand.setSupplier(PlantHireRequest obj, int index) {
        Supplier supplier = supplierDataOnDemand.getRandomSupplier();
        obj.setSupplier(supplier);
    }
    
    public void PlantHireRequestDataOnDemand.setTotalCost(PlantHireRequest obj, int index) {
        BigDecimal totalCost = BigDecimal.valueOf(index);
        obj.setTotalCost(totalCost);
    }
    
    public PlantHireRequest PlantHireRequestDataOnDemand.getSpecificPlantHireRequest(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        PlantHireRequest obj = data.get(index);
        Long id = obj.getId();
        return PlantHireRequest.findPlantHireRequest(id);
    }
    
    public PlantHireRequest PlantHireRequestDataOnDemand.getRandomPlantHireRequest() {
        init();
        PlantHireRequest obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return PlantHireRequest.findPlantHireRequest(id);
    }
    
    public boolean PlantHireRequestDataOnDemand.modifyPlantHireRequest(PlantHireRequest obj) {
        return false;
    }
    
    public void PlantHireRequestDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = PlantHireRequest.findPlantHireRequestEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'PlantHireRequest' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<PlantHireRequest>();
        for (int i = 0; i < 10; i++) {
            PlantHireRequest obj = getNewTransientPlantHireRequest(i);
            try {
                obj.persist();
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
