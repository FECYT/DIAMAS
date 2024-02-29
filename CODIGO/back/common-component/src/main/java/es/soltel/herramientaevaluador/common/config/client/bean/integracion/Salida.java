
package es.soltel.herramientaevaluador.common.config.client.bean.integracion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para salida complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="salida"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="excepcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="outputs" type="{http://services/}outputs" minOccurs="0"/&gt;
 *         &lt;element name="resultado" type="{http://services/}result" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salida", propOrder = {
    "excepcion",
    "outputs",
    "resultado"
})
public class Salida {

    protected String excepcion;
    protected Outputs outputs;
    protected Result resultado;

    /**
     * Obtiene el valor de la propiedad excepcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcepcion() {
        return excepcion;
    }

    /**
     * Define el valor de la propiedad excepcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcepcion(String value) {
        this.excepcion = value;
    }

    /**
     * Obtiene el valor de la propiedad outputs.
     * 
     * @return
     *     possible object is
     *     {@link Outputs }
     *     
     */
    public Outputs getOutputs() {
        return outputs;
    }

    /**
     * Define el valor de la propiedad outputs.
     * 
     * @param value
     *     allowed object is
     *     {@link Outputs }
     *     
     */
    public void setOutputs(Outputs value) {
        this.outputs = value;
    }

    /**
     * Obtiene el valor de la propiedad resultado.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getResultado() {
        return resultado;
    }

    /**
     * Define el valor de la propiedad resultado.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setResultado(Result value) {
        this.resultado = value;
    }

}
