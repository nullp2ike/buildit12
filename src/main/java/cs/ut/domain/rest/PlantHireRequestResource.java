package cs.ut.domain.rest;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import cs.ut.domain.ApprovalStatus;
import cs.ut.domain.Site;
import cs.ut.domain.SiteEngineer;
import cs.ut.domain.Supplier;
import cs.ut.util.ResourceSupport;

@RooJavaBean
@RooToString
@XmlRootElement(name = "plantHireRequest")
public class PlantHireRequestResource extends ResourceSupport{
	
	private BigDecimal totalCost;
	private Site site;
    private Date startDate;
    private Date endDate;
    private SiteEngineer siteEngineer;
    private int plantId;
    private Supplier supplier;
    private ApprovalStatus status;
    private String comment;
}
