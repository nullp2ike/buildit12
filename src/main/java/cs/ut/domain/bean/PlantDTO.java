package cs.ut.domain.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cs.ut.domain.rest.PlantResource;

@RooJavaBean
public class PlantDTO {

	private List<PlantResource> plantList;

	private int chosenPlantId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Date endDate;
	
	/*
	 * private String description;
	 * 
	 * private BigDecimal pricePerDay;
	 * 
	 * private long identifier;
	 */
}
