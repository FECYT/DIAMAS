
package es.soltel.herramientaevaluador.common.config.client.bean.integracion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para result complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="result"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="resultMajor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resultMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resultMinor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {
    "resultMajor",
    "resultMessage",
    "resultMinor"
})
public class Result {

    protected String resultMajor;
    protected String resultMessage;
    protected String resultMinor;

    /**
     * Obtiene el valor de la propiedad resultMajor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultMajor() {
        return resultMajor;
    }

    /**
     * Define el valor de la propiedad resultMajor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultMajor(String value) {
        this.resultMajor = value;
    }

    /**
     * Obtiene el valor de la propiedad resultMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Define el valor de la propiedad resultMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultMessage(String value) {
        this.resultMessage = value;
    }

    /**
     * Obtiene el valor de la propiedad resultMinor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultMinor() {
        return resultMinor;
    }

    /**
     * Define el valor de la propiedad resultMinor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultMinor(String value) {
        this.resultMinor = value;
    }

}
