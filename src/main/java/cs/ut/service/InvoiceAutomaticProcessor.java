package cs.ut.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class InvoiceAutomaticProcessor {

	@ServiceActivator
	public void process(Document invoice) {
		System.out.println("InvoiceAutomaticProcessor: Done!");
	}

}
