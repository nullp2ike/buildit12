package cs.ut.domain.soap.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.4-b01 Generated
 * source version: 2.2
 * 
 */
@WebServiceClient(name = "PlantSOAPServiceService", targetNamespace = "http://service.soap.domain.ut.cs/", wsdlLocation = "/WEB-INF/wsdl/PlantSOAPService.wsdl")
public class PlantSOAPServiceService extends Service {

	private final static URL PLANTSOAPSERVICESERVICE_WSDL_LOCATION;
	private final static WebServiceException PLANTSOAPSERVICESERVICE_EXCEPTION;
	private final static QName PLANTSOAPSERVICESERVICE_QNAME = new QName(
			"http://service.soap.domain.ut.cs/", "PlantSOAPServiceService");

	static {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/main/resources/META-INF/spring/default.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String wsdlURL = props.getProperty("wsdlPlantServiceURL");
		URL url = null;
		WebServiceException e = null;
		try {
			url = new URL(wsdlURL);
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		}
		PLANTSOAPSERVICESERVICE_WSDL_LOCATION = url;
		PLANTSOAPSERVICESERVICE_EXCEPTION = e;
	}

	public PlantSOAPServiceService() {
		super(__getWsdlLocation(), PLANTSOAPSERVICESERVICE_QNAME);
	}

	public PlantSOAPServiceService(WebServiceFeature... features) {
		super(__getWsdlLocation(), PLANTSOAPSERVICESERVICE_QNAME, features);
	}

	public PlantSOAPServiceService(URL wsdlLocation) {
		super(wsdlLocation, PLANTSOAPSERVICESERVICE_QNAME);
	}

	public PlantSOAPServiceService(URL wsdlLocation,
			WebServiceFeature... features) {
		super(wsdlLocation, PLANTSOAPSERVICESERVICE_QNAME, features);
	}

	public PlantSOAPServiceService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public PlantSOAPServiceService(URL wsdlLocation, QName serviceName,
			WebServiceFeature... features) {
		super(wsdlLocation, serviceName, features);
	}

	/**
	 * 
	 * @return returns PlantSOAPService
	 */
	@WebEndpoint(name = "PlantSOAPServicePort")
	public PlantSOAPService getPlantSOAPServicePort() {
		return super.getPort(new QName("http://service.soap.domain.ut.cs/",
				"PlantSOAPServicePort"), PlantSOAPService.class);
	}

	/**
	 * 
	 * @param features
	 *            A list of {@link javax.xml.ws.WebServiceFeature} to configure
	 *            on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns PlantSOAPService
	 */
	@WebEndpoint(name = "PlantSOAPServicePort")
	public PlantSOAPService getPlantSOAPServicePort(
			WebServiceFeature... features) {
		return super.getPort(new QName("http://service.soap.domain.ut.cs/",
				"PlantSOAPServicePort"), PlantSOAPService.class, features);
	}

	private static URL __getWsdlLocation() {
		if (PLANTSOAPSERVICESERVICE_EXCEPTION != null) {
			throw PLANTSOAPSERVICESERVICE_EXCEPTION;
		}
		return PLANTSOAPSERVICESERVICE_WSDL_LOCATION;
	}

}
