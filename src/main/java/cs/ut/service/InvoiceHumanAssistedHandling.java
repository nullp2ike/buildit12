package cs.ut.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import cs.ut.domain.PlantHireRequest;

@Component
public class InvoiceHumanAssistedHandling {
	
	@Value("${email.rentit}")
	String rentitEmail;

	@ServiceActivator
	public MailMessage process(Document invoice) {
		System.out.println("InvoiceHumanAssistedHandling: Done!");

		MailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(rentitEmail);
		mailMessage.setSentDate(new Date());
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
		//"<http://localhost:8081/Rentit/rest/pos/194>;rel="getPO""
		String urlFromFile = invoiceRes.getPurchaseOrderHRef();
		String totalCostFromFile = invoiceRes.getTotal().toPlainString();
		
		long id = Long.parseLong(urlFromFile.substring(urlFromFile.lastIndexOf("/") + 1));
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);
		
		if(phr == null){
				mailMessage.setSubject("Problem with the invoice url");
				mailMessage.setText("The invoice was not found in our database, you sent us the following info" +
				" url: " + urlFromFile + ", total cost: " + totalCostFromFile);
				return mailMessage;
		}
		
		//unpaid Purchase Order
		
		String costInDatabase = phr.getTotalCost().toPlainString();
		if(!totalCostFromFile.equals(costInDatabase)){
			mailMessage.setSubject("Problem with the total");
			mailMessage.setText("The invoice total cost did not match with the total cost in our database. The cost according to us should be: " + costInDatabase +
			" url: " + urlFromFile + ", total cost: " + totalCostFromFile);
			return mailMessage;
		}
		
		mailMessage.setSubject("The payment is being processed");
		mailMessage.setText("url: " + urlFromFile + ", total cost: " + totalCostFromFile);
		return mailMessage;

	}

	// CC10. When an invoice is received, the system must check that the PO
	// number in the invoice
	// corresponds to an existing and unpaid Purchase Order. If the PO does not
	// exist, an error
	// message is returned to the supplier.
	// CC11. The system must allow site engineers to approve an invoice and to
	// retrieve the PO associated
	// to an invoice.
	// CC12. The system must submit a remittance advice to the supplier after
	// the invoice is approved
	// (normally the remittance advice should only be sent after the payment has
	// been triggered, but in
	// this project we do not deal with sending payment orders to the bank).
}
