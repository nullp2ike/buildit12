package cs.ut.service;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Component
public class InvoiceMailPreprocessor {
	@ServiceActivator
	public Document process(Message msg) throws MessagingException,
			IOException, ParserConfigurationException, SAXException {
		Document invoiceXML = null;
		Object _content = msg.getContent();
		if (_content instanceof Multipart) {
			Multipart content = (Multipart) _content;
			for (int i = 0; i < content.getCount(); i++) {
				BodyPart part = content.getBodyPart(i);
				if (part.getContentType().startsWith("text/xml")
						|| part.getContentType().startsWith("application/xml")) {
					String fileName = part.getFileName();
					if (fileName.startsWith("invoice")) {
						DocumentBuilder builder = DocumentBuilderFactory
								.newInstance().newDocumentBuilder();
						invoiceXML = builder.parse(part.getInputStream());
						break;
					}
				}
			}
		}
		if (invoiceXML == null)
			throw new IOException("No invoice was found !");
		return invoiceXML;
	}
}