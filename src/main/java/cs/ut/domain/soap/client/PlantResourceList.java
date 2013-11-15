
package cs.ut.domain.soap.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for plantResourceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="plantResourceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listOfPlantResources" type="{http://service.soap.domain.ut.cs/}plantResource" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "plantResourceList", propOrder = {
    "listOfPlantResources"
})
public class PlantResourceList {

    protected List<PlantResource> listOfPlantResources;

    /**
     * Gets the value of the listOfPlantResources property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listOfPlantResources property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListOfPlantResources().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlantResource }
     * 
     * 
     */
    public List<PlantResource> getListOfPlantResources() {
        if (listOfPlantResources == null) {
            listOfPlantResources = new ArrayList<PlantResource>();
        }
        return this.listOfPlantResources;
    }

}
