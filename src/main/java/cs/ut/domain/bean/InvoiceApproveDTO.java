package cs.ut.domain.bean;

import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

import cs.ut.domain.PlantHireRequest;

@RooJavaBean
public class InvoiceApproveDTO {
	
	private List<PlantHireRequest> phrList;
	private int radio;

}