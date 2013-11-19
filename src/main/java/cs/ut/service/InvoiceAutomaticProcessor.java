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
public class InvoiceAutomaticProcessor {

	@Value("${email.rentit}")
	String rentitEmail;

	@ServiceActivator
	public MailMessage process(Document invoice) {
		System.out.println("InvoiceAutomaticProcessor: Done!");

		MailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(rentitEmail);
		mailMessage.setSentDate(new Date());
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

		String urlFromFile = invoiceRes.getPurchaseOrderHRef();
		System.out.println("urlfromfile: " + urlFromFile);
		String totalCostFromFile = invoiceRes.getTotal().toPlainString();

		long id = Long.parseLong(urlFromFile.substring(urlFromFile
				.lastIndexOf("/") + 1));
		System.out.println("id: " + id);
		PlantHireRequest phr = PlantHireRequest.findPlantHireRequest(id);

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

		mailMessage.setSubject("The payment is being processed");
		mailMessage.setText("url: " + urlFromFile + ", total cost: "
				+ totalCostFromFile);
		return mailMessage;
	}

}
