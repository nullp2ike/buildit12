package cs.ut.service;

import org.springframework.integration.annotation.Gateway;
import org.w3c.dom.Document;

public interface InvoiceGateway {
	@Gateway
	void process(Document invoice);
}
