
package cs.ut.domain.soap.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cs.ut.domain.soap.client package. 
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

    private final static QName _PlantResource_QNAME = new QName("http://service.soap.domain.ut.cs/", "plantResource");
    private final static QName _PlantResourceList_QNAME = new QName("http://service.soap.domain.ut.cs/", "plantResourceList");
    private final static QName _GetAllPlantsResponse_QNAME = new QName("http://service.soap.domain.ut.cs/", "getAllPlantsResponse");
    private final static QName _GetAllPlants_QNAME = new QName("http://service.soap.domain.ut.cs/", "getAllPlants");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cs.ut.domain.soap.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PlantResource }
     * 
     */
    public PlantResource createPlantResource() {
        return new PlantResource();
    }

    /**
     * Create an instance of {@link PlantResourceList }
     * 
     */
    public PlantResourceList createPlantResourceList() {
        return new PlantResourceList();
    }

    /**
     * Create an instance of {@link GetAllPlantsResponse }
     * 
     */
    public GetAllPlantsResponse createGetAllPlantsResponse() {
        return new GetAllPlantsResponse();
    }

    /**
     * Create an instance of {@link GetAllPlants }
     * 
     */
    public GetAllPlants createGetAllPlants() {
        return new GetAllPlants();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlantResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.domain.ut.cs/", name = "plantResource")
    public JAXBElement<PlantResource> createPlantResource(PlantResource value) {
        return new JAXBElement<PlantResource>(_PlantResource_QNAME, PlantResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlantResourceList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.domain.ut.cs/", name = "plantResourceList")
    public JAXBElement<PlantResourceList> createPlantResourceList(PlantResourceList value) {
        return new JAXBElement<PlantResourceList>(_PlantResourceList_QNAME, PlantResourceList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPlantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.domain.ut.cs/", name = "getAllPlantsResponse")
    public JAXBElement<GetAllPlantsResponse> createGetAllPlantsResponse(GetAllPlantsResponse value) {
        return new JAXBElement<GetAllPlantsResponse>(_GetAllPlantsResponse_QNAME, GetAllPlantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPlants }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.domain.ut.cs/", name = "getAllPlants")
    public JAXBElement<GetAllPlants> createGetAllPlants(GetAllPlants value) {
        return new JAXBElement<GetAllPlants>(_GetAllPlants_QNAME, GetAllPlants.class, null, value);
    }

}
