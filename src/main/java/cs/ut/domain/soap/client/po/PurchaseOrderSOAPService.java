
package cs.ut.domain.soap.client.po;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PurchaseOrderSOAPService", targetNamespace = "http://service.soap.domain.ut.cs/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PurchaseOrderSOAPService {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "createPurchaseOrder", targetNamespace = "http://service.soap.domain.ut.cs/", className = "cs.ut.domain.soap.client.po.CreatePurchaseOrder")
    @ResponseWrapper(localName = "createPurchaseOrderResponse", targetNamespace = "http://service.soap.domain.ut.cs/", className = "cs.ut.domain.soap.client.po.CreatePurchaseOrderResponse")
    @Action(input = "http://service.soap.domain.ut.cs/PurchaseOrderSOAPService/createPurchaseOrderRequest", output = "http://service.soap.domain.ut.cs/PurchaseOrderSOAPService/createPurchaseOrderResponse")
    public void createPurchaseOrder(
        @WebParam(name = "arg0", targetNamespace = "")
        long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        XMLGregorianCalendar arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        XMLGregorianCalendar arg2);

}
