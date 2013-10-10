package cs.ut.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PlantHireRequest {

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @NotNull
    private Date startDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @NotNull
    private Date endDate;

    /**
     */
    @NotNull
    private BigDecimal totalCost;

    /**
     */
    @OneToOne
    @NotNull
    private Supplier supplier;

    /**
     */
    @OneToOne
    @NotNull
    private Site site;

    /**
     */
    @OneToOne
    @NotNull
    private SiteEngineer siteEngineer;

    /**
     */
    @OneToOne
    @NotNull
    private Plant plant;
}
