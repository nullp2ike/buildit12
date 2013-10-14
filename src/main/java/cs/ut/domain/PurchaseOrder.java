package cs.ut.domain;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.OneToOne;

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
