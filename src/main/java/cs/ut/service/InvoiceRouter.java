package cs.ut.service;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class InvoiceRouter {
	// Comparing invoice's total with its corresponding po's total
	public String analyzeInvoice(Document invoice)
			throws XPathExpressionException {
		System.out.println("Analyze");
		String destinationChannel = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		xPath.evaluate("//purchaseOrderHRef", invoice);
		Float total = Float.valueOf(xPath.evaluate("//total", invoice));

		if (total <= 1000){
			destinationChannel = "MINOR";
		}
		else{
			destinationChannel = "MAJOR";
		}
			
		return destinationChannel;
	}
}