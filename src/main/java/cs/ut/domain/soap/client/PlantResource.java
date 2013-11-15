
package cs.ut.domain.soap.client;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for plantResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="plantResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identifier" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="plantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pricePerDay" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "plantResource", propOrder = {
    "description",
    "identifier",
    "plantName",
    "pricePerDay"
})
public class PlantResource {

    protected String description;
    protected long identifier;
    protected String plantName;
    protected BigDecimal pricePerDay;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the identifier property.
     * 
     */
    public long getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     */
    public void setIdentifier(long value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the plantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlantName() {
        return plantName;
    }

    /**
     * Sets the value of the plantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlantName(String value) {
        this.plantName = value;
    }

    /**
     * Gets the value of the pricePerDay property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    /**
     * Sets the value of the pricePerDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPricePerDay(BigDecimal value) {
        this.pricePerDay = value;
    }

}
