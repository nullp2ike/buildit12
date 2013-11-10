
package cs.ut.domain.soap.client.po;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cs.ut.domain.soap.client.po package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreatePurchaseOrder_QNAME = new QName("http://service.soap.domain.ut.cs/", "createPurchaseOrder");
    private final static QName _CreatePurchaseOrderResponse_QNAME = new QName("http://service.soap.domain.ut.cs/", "createPurchaseOrderResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cs.ut.domain.soap.client.po
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreatePurchaseOrder }
     * 
     */
    public CreatePurchaseOrder createCreatePurchaseOrder() {
        return new CreatePurchaseOrder();
    }

    /**
     * Create an instance of {@link CreatePurchaseOrderResponse }
     * 
     */
    public CreatePurchaseOrderResponse createCreatePurchaseOrderResponse() {
        return new CreatePurchaseOrderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePurchaseOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.domain.ut.cs/", name = "createPurchaseOrder")
    public JAXBElement<CreatePurchaseOrder> createCreatePurchaseOrder(CreatePurchaseOrder value) {
        return new JAXBElement<CreatePurchaseOrder>(_CreatePurchaseOrder_QNAME, CreatePurchaseOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePurchaseOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.domain.ut.cs/", name = "createPurchaseOrderResponse")
    public JAXBElement<CreatePurchaseOrderResponse> createCreatePurchaseOrderResponse(CreatePurchaseOrderResponse value) {
        return new JAXBElement<CreatePurchaseOrderResponse>(_CreatePurchaseOrderResponse_QNAME, CreatePurchaseOrderResponse.class, null, value);
    }

}
