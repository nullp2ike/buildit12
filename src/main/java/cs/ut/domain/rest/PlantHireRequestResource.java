package cs.ut.domain.rest;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;

@RooJavaBean
@RooToString
@XmlRootElement(name = "plantHireRequest")
public class PlantHireRequestResource {
	
	private BigDecimal totalCost;
	private Site site;
    private Date startDate;
    private Date endDate;
    private SiteEngineer siteEngineer;
    private int plantId;
    private Supplier supplier;
    private ApprovalStatus status;
}
