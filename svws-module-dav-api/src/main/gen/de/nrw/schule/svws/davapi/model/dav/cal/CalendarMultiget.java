package de.nrw.schule.svws.davapi.model.dav.cal;

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.09.22 at 01:09:41 PM PDT
//


import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import de.nrw.schule.svws.davapi.model.dav.Prop;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{DAV:}sync-token"/&gt;
 *         &lt;element ref="{DAV:}sync-level"/&gt;
 *         &lt;element ref="{DAV:}limit" minOccurs="0"/&gt;
 *         &lt;element ref="{DAV:}prop"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prop",
    "href"
})
@XmlRootElement(name = "calendar-multiget", namespace = "urn:ietf:params:xml:ns:caldav")
public class CalendarMultiget {

    @XmlElement(required = true, namespace = "DAV:")
    protected Prop prop;

    /**
     * Gets the value of the prop property.
     *
     * @return
     *     possible object is
     *     {@link Prop }
     *
     */
    public Prop getProp() {
        return prop;
    }

    /**
     * Sets the value of the prop property.
     *
     * @param value
     *     allowed object is
     *     {@link Prop }
     *
     */
    public void setProp(Prop value) {
        this.prop = value;
    }

    @XmlElement(required = false, namespace = "DAV:")
    protected List<String> href;

    public List<String> getHref() {
        if (href == null) {
            href = new ArrayList<>();
        }
        return this.href;
    }

}
