package cs.ut.domain.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cs.ut.domain.PlantHireRequest;

@RooJavaBean
public class PHRSelectDTO {
	
	private List<PlantHireRequest> phrList;
	
	private int radio;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "S-")
	private Date endDate;

}
