//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.09.04 at 11:14:25 AM PDT
//


package de.nrw.schule.svws.davapi.model.dav;

import de.nrw.schule.svws.davapi.model.dav.cal.CalendarMultiget;
import de.nrw.schule.svws.davapi.model.dav.cal.CalendarQuery;
import org.w3c.dom.Element;

import jakarta.xml.bind.annotation.*;


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
 *         &lt;any processContents='skip' namespace='##other'/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "report")
public class Report {

    @XmlAnyElement
    protected Element any;

    /**
     * Gets the value of the any property.
     *
     * @return
     *     possible object is
     *     {@link Element }
     *
     */
    public Element getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     *
     * @param value
     *     allowed object is
     *     {@link Element }
     *
     */
    public void setAny(Element value) {
        this.any = value;
    }

	@XmlElement(name = "sync-collection")
	protected SyncCollection syncCollection;

	public SyncCollection getSyncCollection() {
		return syncCollection;
	}

	public void setSyncCollection(SyncCollection syncCollection) {
		this.syncCollection = syncCollection;
	}

	@XmlElement(name = "calendar-multiget", namespace = "urn:ietf:params:xml:ns:caldav")
	protected CalendarMultiget calendarMultiget;

	public CalendarMultiget getCalendarMultiget() {
		return calendarMultiget;
	}

	public void setCalendarMultiget(CalendarMultiget calendarMultiget) {
		this.calendarMultiget = calendarMultiget;
	}

	@XmlElement(name = "calendar-query", namespace = "urn:ietf:params:xml:ns:caldav")
	protected CalendarQuery calendarQuery;

	public CalendarQuery getCalendarQuery() {
		return calendarQuery;
	}

	public void setCalendarQuery(CalendarQuery calendarQuery) {
		this.calendarQuery = calendarQuery;
	}

}
