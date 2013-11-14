package cs.ut.domain.rest;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import cs.ut.domain.HireRequestStatus;
import cs.ut.util.ResourceSupport;

@RooJavaBean
@RooToString
@XmlRootElement(name = "purchaseOrder")
public class PurchaseOrderResource extends ResourceSupport{
	
    private Date startDate;

    private Date endDate;

    private BigDecimal totalCost;
    
    private HireRequestStatus status;
    
    private long plantHireRequestId;
    
    private PlantResource plantResource;

}
