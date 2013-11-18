package cs.ut.service;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "invoice")
public class InvoiceResource {
	
	private String purchaseOrderHRef;
	private BigDecimal total;

}
