//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.17 at 04:44:01 PM WET 
//


package org.mobicents.slee.container.component.deployment.jaxb.slee.sbb;

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
    "profileSpecName",
    "profileSpecVendor",
    "profileSpecVersion",
    "profileSpecAlias"
})
@XmlRootElement(name = "profile-spec-ref")
public class ProfileSpecRef {

    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    protected Description description;
    @XmlElement(name = "profile-spec-name", required = true)
    protected ProfileSpecName profileSpecName;
    @XmlElement(name = "profile-spec-vendor", required = true)
    protected ProfileSpecVendor profileSpecVendor;
    @XmlElement(name = "profile-spec-version", required = true)
    protected ProfileSpecVersion profileSpecVersion;
    @XmlElement(name = "profile-spec-alias", required = true)
    protected ProfileSpecAlias profileSpecAlias;

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
     * Gets the value of the profileSpecName property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileSpecName }
     *     
     */
    public ProfileSpecName getProfileSpecName() {
        return profileSpecName;
    }

    /**
     * Sets the value of the profileSpecName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileSpecName }
     *     
     */
    public void setProfileSpecName(ProfileSpecName value) {
        this.profileSpecName = value;
    }

    /**
     * Gets the value of the profileSpecVendor property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileSpecVendor }
     *     
     */
    public ProfileSpecVendor getProfileSpecVendor() {
        return profileSpecVendor;
    }

    /**
     * Sets the value of the profileSpecVendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileSpecVendor }
     *     
     */
    public void setProfileSpecVendor(ProfileSpecVendor value) {
        this.profileSpecVendor = value;
    }

    /**
     * Gets the value of the profileSpecVersion property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileSpecVersion }
     *     
     */
    public ProfileSpecVersion getProfileSpecVersion() {
        return profileSpecVersion;
    }

    /**
     * Sets the value of the profileSpecVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileSpecVersion }
     *     
     */
    public void setProfileSpecVersion(ProfileSpecVersion value) {
        this.profileSpecVersion = value;
    }

    /**
     * Gets the value of the profileSpecAlias property.
     * 
     * @return
     *     possible object is
     *     {@link ProfileSpecAlias }
     *     
     */
    public ProfileSpecAlias getProfileSpecAlias() {
        return profileSpecAlias;
    }

    /**
     * Sets the value of the profileSpecAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileSpecAlias }
     *     
     */
    public void setProfileSpecAlias(ProfileSpecAlias value) {
        this.profileSpecAlias = value;
    }

}
