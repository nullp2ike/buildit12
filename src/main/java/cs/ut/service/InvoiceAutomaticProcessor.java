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

import cs.ut.domain.Invoice;
import cs.ut.domain.InvoiceStatus;
import cs.ut.domain.PlantHireRequest;
import cs.ut.repository.PlantHireRequestRepository;

@Component
public class InvoiceAutomaticProcessor {

	@Autowired PlantHireRequestRepository phrRepository;
	@Value("${email.rentit}")
	String rentitEmail;

	@ServiceActivator
	public MailMessage process(Document invoice) {
		System.out.println("InvoiceAutomaticProcessor: Done!");

		MailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(rentitEmail);
		mailMessage.setSentDate(new Date());
		
		InvoiceResource invoiceRes = xmlToInvoiceResource(invoice);

		String urlFromFile = invoiceRes.getPurchaseOrderHRef();
		System.out.println("urlfromfile: " + urlFromFile);
		String totalCostFromFile = invoiceRes.getTotal().toPlainString();

		long purchaseOrderId = Long.parseLong(urlFromFile.substring(urlFromFile
				.lastIndexOf("/") + 1));
		System.out.println("id: " + purchaseOrderId);
		PlantHireRequest phr = phrRepository.findByPurchaseOrderId(purchaseOrderId);

		if (phr == null) {
			System.out.println("null");
			mailMessage.setSubject("Problem with the invoice url");
			mailMessage
					.setText("The invoice was not found in our database, you sent us the following info"
							+ " url: "
							+ urlFromFile
							+ ", total cost: "
							+ totalCostFromFile);
			return mailMessage;
		}
		// unpaid Purchase Order
		String costInDatabase = phr.getTotalCost().toPlainString();
		if (!totalCostFromFile.equals(costInDatabase)) {
			System.out.println("cost different");
			mailMessage.setSubject("Problem with the total");
			mailMessage
					.setText("The invoice total cost did not match with the total cost in our database. The cost according to us should be: "
							+ costInDatabase
							+ " url: "
							+ urlFromFile
							+ ", total cost: " + totalCostFromFile);
			return mailMessage;
		}

		if(phr.getInvoice().getIsPaid() == false){
			Invoice inv = Invoice.findInvoice(phr.getInvoice().getId());
			inv.setStatus(InvoiceStatus.APPROVED);
			inv.setIsPaid(true);				//normally there would be some pay method here
			inv.merge();
			mailMessage.setSubject("The payment has been made for PO: " + purchaseOrderId);
			mailMessage.setText("url: " + urlFromFile + ", total cost: " + totalCostFromFile);
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
			invoiceRes = (InvoiceResource) jaxbCtx.createUnmarshaller()
					.unmarshal(invoice);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return invoiceRes;
	}

}
