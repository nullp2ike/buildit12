package cs.ut.domain;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.OneToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class PurchaseOrder {

    BigDecimal totalCost;

    Date startDate;

    Date endDate;

    int plant;

    /**
     */
    @OneToOne
    private PlantHireRequest plantHireRequest;
    
    ApprovalStatus status;
}
