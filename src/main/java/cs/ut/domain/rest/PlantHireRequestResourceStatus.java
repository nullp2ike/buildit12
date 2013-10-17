package cs.ut.domain.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import cs.ut.domain.ApprovalStatus;

@RooJavaBean
@RooToString
@XmlRootElement(name = "plantHireRequest")
public class PlantHireRequestResourceStatus {

	private ApprovalStatus status;
	
}
