package cs.ut.domain;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.OneToOne;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PlantHireRequest {

    /**
     */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @NotNull
    private Date startDate;

    /**
     */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @NotNull
    private Date endDate;

    /**
     */
    @NotNull
    private BigDecimal totalCost;

    /**
     */
    @ManyToOne
    @NotNull
    private SiteEngineer siteEngineer;

    private String comment;

    @NotNull
    private int plantId;

    /**
     */
    @ManyToOne
    @NotNull
    private Site site;

    /**
     */
    @ManyToOne
    @NotNull
    private Supplier supplier;

    @Enumerated
    @NotNull
    ApprovalStatus status;

    /**
     */
    @OneToOne
    private Invoice invoice;

    /**
     */
    @ManyToOne
    private WorksEngineer worksEngineer;
}
