package cs.ut.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import cs.ut.domain.PlantHireRequest;
import cs.ut.repository.PlantHireRequestRepository;

@Component
public class InvoiceHumanAssistedHandling {
	
	@Autowired PlantHireRequestRepository phrRepository;
	@Value("${email.rentit}")
	String rentitEmail;

	@ServiceActivator
	public MailMessage process(Document invoice) {
		System.out.println("InvoiceHumanAssistedHandling: Done!");

		MailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(rentitEmail);
		mailMessage.setSentDate(new Date());
		
		InvoiceResource invoiceRes = xmlToInvoiceResource(invoice);

		String urlFromFile = invoiceRes.getPurchaseOrderHRef();
		String totalCostFromFile = invoiceRes.getTotal().toPlainString();
		
		long purchaseOrderId = Long.parseLong(urlFromFile.substring(urlFromFile.lastIndexOf("/") + 1));
		
		PlantHireRequest phr = phrRepository.findByPurchaseOrderId(purchaseOrderId);
		
		if(phr == null){
				mailMessage.setSubject("Problem with the invoice url");
				mailMessage.setText("The invoice was not found in our database, you sent us the following info" +
				" url: " + urlFromFile + ", total cost: " + totalCostFromFile);
				return mailMessage;
		}
		
		String costInDatabase = phr.getTotalCost().toPlainString();
		if(!totalCostFromFile.equals(costInDatabase)){
			mailMessage.setSubject("Problem with the total");
			mailMessage.setText("The invoice total cost did not match with the total cost in our database. The cost according to us should be: " + costInDatabase +
			" url: " + urlFromFile + ", total cost: " + totalCostFromFile);
			return mailMessage;
		}
		
		if(phr.getInvoice().getIsPaid() == false){
			phr.getInvoice().setNeedsApproval(true);
			mailMessage.setSubject("The payment is being processed");
			mailMessage.setText("Since the total sum is quite large, the payment will be made soon by one of our employees" + "url: " + urlFromFile + ", total cost: " + totalCostFromFile);
		}else{
			mailMessage.setSubject("The plant hire has already been paid for");
			mailMessage.setText(" According to us the plant hire has already been paid for " +
			" url: " + urlFromFile + ", total cost: " + totalCostFromFile);
		}
		
		return mailMessage;

	}

	private InvoiceResource xmlToInvoiceResource(Document invoice) {
		JAXBContext jaxbCtx;
		InvoiceResource invoiceRes = null;
		try {
			jaxbCtx = JAXBContext.newInstance(InvoiceResource.class);
			invoiceRes = (InvoiceResource) jaxbCtx
					.createUnmarshaller().unmarshal(invoice);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invoiceRes;
	}

	// CC11. The system must allow site engineers to approve an invoice and to
	// retrieve the PO associated
	// to an invoice.
	// CC12. The system must submit a remittance advice to the supplier after
	// the invoice is approved
	// (normally the remittance advice should only be sent after the payment has
	// been triggered, but in
	// this project we do not deal with sending payment orders to the bank).
}
