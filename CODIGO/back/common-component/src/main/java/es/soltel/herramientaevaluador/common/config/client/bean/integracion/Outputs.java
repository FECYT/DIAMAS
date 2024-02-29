
package es.soltel.herramientaevaluador.common.config.client.bean.integracion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para outputs complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="outputs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="firmaActualizada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="formatoFirma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipoFirma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outputs", propOrder = {
    "firmaActualizada",
    "formatoFirma",
    "tipoFirma"
})
public class Outputs {

    protected String firmaActualizada;
    protected String formatoFirma;
    protected String tipoFirma;

    /**
     * Obtiene el valor de la propiedad firmaActualizada.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmaActualizada() {
        return firmaActualizada;
    }

    /**
     * Define el valor de la propiedad firmaActualizada.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmaActualizada(String value) {
        this.firmaActualizada = value;
    }

    /**
     * Obtiene el valor de la propiedad formatoFirma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatoFirma() {
        return formatoFirma;
    }

    /**
     * Define el valor de la propiedad formatoFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatoFirma(String value) {
        this.formatoFirma = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoFirma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Define el valor de la propiedad tipoFirma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFirma(String value) {
        this.tipoFirma = value;
    }

}
