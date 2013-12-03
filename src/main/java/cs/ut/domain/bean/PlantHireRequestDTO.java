package cs.ut.domain.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cs.ut.domain.Site;
import cs.ut.domain.rest.PlantResource;

@RooJavaBean
public class PlantHireRequestDTO {

	private List<PlantResource> plantList;
	private List<Site> siteList;

	private int plant;
	private int site;
	private BigDecimal pricePerDay;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "S-")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "S-")
	private Date endDate;
}
