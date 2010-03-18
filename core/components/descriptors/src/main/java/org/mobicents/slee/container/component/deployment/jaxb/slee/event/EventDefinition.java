//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.17 at 04:44:00 PM WET 
//


package org.mobicents.slee.container.component.deployment.jaxb.slee.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "description",
    "eventTypeName",
    "eventTypeVendor",
    "eventTypeVersion",
    "eventClassName"
})
@XmlRootElement(name = "event-definition")
public class EventDefinition {

    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    protected Description description;
    @XmlElement(name = "event-type-name", required = true)
    protected EventTypeName eventTypeName;
    @XmlElement(name = "event-type-vendor", required = true)
    protected EventTypeVendor eventTypeVendor;
    @XmlElement(name = "event-type-version", required = true)
    protected EventTypeVersion eventTypeVersion;
    @XmlElement(name = "event-class-name", required = true)
    protected EventClassName eventClassName;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the eventTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link EventTypeName }
     *     
     */
    public EventTypeName getEventTypeName() {
        return eventTypeName;
    }

    /**
     * Sets the value of the eventTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventTypeName }
     *     
     */
    public void setEventTypeName(EventTypeName value) {
        this.eventTypeName = value;
    }

    /**
     * Gets the value of the eventTypeVendor property.
     * 
     * @return
     *     possible object is
     *     {@link EventTypeVendor }
     *     
     */
    public EventTypeVendor getEventTypeVendor() {
        return eventTypeVendor;
    }

    /**
     * Sets the value of the eventTypeVendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventTypeVendor }
     *     
     */
    public void setEventTypeVendor(EventTypeVendor value) {
        this.eventTypeVendor = value;
    }

    /**
     * Gets the value of the eventTypeVersion property.
     * 
     * @return
     *     possible object is
     *     {@link EventTypeVersion }
     *     
     */
    public EventTypeVersion getEventTypeVersion() {
        return eventTypeVersion;
    }

    /**
     * Sets the value of the eventTypeVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventTypeVersion }
     *     
     */
    public void setEventTypeVersion(EventTypeVersion value) {
        this.eventTypeVersion = value;
    }

    /**
     * Gets the value of the eventClassName property.
     * 
     * @return
     *     possible object is
     *     {@link EventClassName }
     *     
     */
    public EventClassName getEventClassName() {
        return eventClassName;
    }

    /**
     * Sets the value of the eventClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventClassName }
     *     
     */
    public void setEventClassName(EventClassName value) {
        this.eventClassName = value;
    }

}
