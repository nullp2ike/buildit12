
package cs.ut.domain.soap.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
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
@WebService(name = "PlantSOAPService", targetNamespace = "http://service.soap.domain.ut.cs/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PlantSOAPService {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns cs.ut.domain.soap.client.PlantResourceList
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAvailablePlants", targetNamespace = "http://service.soap.domain.ut.cs/", className = "cs.ut.domain.soap.client.GetAvailablePlants")
    @ResponseWrapper(localName = "getAvailablePlantsResponse", targetNamespace = "http://service.soap.domain.ut.cs/", className = "cs.ut.domain.soap.client.GetAvailablePlantsResponse")
    @Action(input = "http://service.soap.domain.ut.cs/PlantSOAPService/getAvailablePlantsRequest", output = "http://service.soap.domain.ut.cs/PlantSOAPService/getAvailablePlantsResponse")
    public PlantResourceList getAvailablePlants(
        @WebParam(name = "arg0", targetNamespace = "")
        XMLGregorianCalendar arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        XMLGregorianCalendar arg1);

    /**
     * 
     * @return
     *     returns cs.ut.domain.soap.client.PlantResourceList
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllPlants", targetNamespace = "http://service.soap.domain.ut.cs/", className = "cs.ut.domain.soap.client.GetAllPlants")
    @ResponseWrapper(localName = "getAllPlantsResponse", targetNamespace = "http://service.soap.domain.ut.cs/", className = "cs.ut.domain.soap.client.GetAllPlantsResponse")
    @Action(input = "http://service.soap.domain.ut.cs/PlantSOAPService/getAllPlantsRequest", output = "http://service.soap.domain.ut.cs/PlantSOAPService/getAllPlantsResponse")
    public PlantResourceList getAllPlants();

}
