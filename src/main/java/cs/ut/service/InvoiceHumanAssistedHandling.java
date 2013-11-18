package cs.ut.service;

import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class InvoiceHumanAssistedHandling {

	@ServiceActivator
	public MailMessage process(Document invoice) {
		System.out.println("InvoiceHumanAssistedHandling: Done!");

		MailMessage mailMessage = new SimpleMailMessage();
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
		 
		mailMessage.setTo("rentitteam12@gmail.com");
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject("The payment is being processed");
		mailMessage.setText("url: " + invoiceRes.getPurchaseOrderHRef() + ", total cost: " + invoiceRes.getTotal().toPlainString());
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
