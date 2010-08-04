/**
 * Start time:16:00:31 2009-01-25<br>
 * Project: mobicents-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
package org.mobicents.slee.container.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.slee.ComponentID;
import javax.slee.EventTypeID;
import javax.slee.InitialEventSelector;
import javax.slee.SbbID;
import javax.slee.management.ComponentDescriptor;
import javax.slee.management.DependencyException;
import javax.slee.management.DeploymentException;
import javax.slee.management.LibraryID;
import javax.slee.profile.ProfileSpecificationID;
import javax.slee.resource.ResourceAdaptorTypeID;

import org.mobicents.slee.container.component.UsageParameterDescriptor;
import org.mobicents.slee.container.component.common.ProfileSpecRefDescriptor;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.SbbDescriptorImpl;
import org.mobicents.slee.container.component.sbb.AbstractSbbClassInfo;
import org.mobicents.slee.container.component.sbb.AbstractSbbClassInfoImpl;
import org.mobicents.slee.container.component.sbb.EventEntryDescriptor;
import org.mobicents.slee.container.component.sbb.ResourceAdaptorEntityBindingDescriptor;
import org.mobicents.slee.container.component.sbb.ResourceAdaptorTypeBindingDescriptor;
import org.mobicents.slee.container.component.sbb.SbbRefDescriptor;
import org.mobicents.slee.container.component.security.PermissionHolderImpl;
import org.mobicents.slee.container.component.validator.SbbComponentValidator;

/**
 * Start time:16:00:31 2009-01-25<br>
 * Project: mobicents-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class SbbComponentImpl extends AbstractSleeComponentWithUsageParametersInterface implements org.mobicents.slee.container.component.sbb.SbbComponent {

	/**
	 * the sbb descriptor
	 */
	private final SbbDescriptorImpl descriptor;

	/**
	 * the sbb abstract class
	 */
	private Class<?> abstractSbbClass;

	/**
	 * the concrete sbb class, generated by SLEE
	 */
	private Class<?> concreteSbbClass;

	/**
	 * the sbb local interface
	 */
	private Class<?> sbbLocalInterfaceClass;

	/**
	 * the concrete sbb local interface class, generated by SLEE
	 */
	private Class<?> sbbLocalInterfaceConcreteClass;

	/**
	 * the sbb own activity context interface
	 */
	private Class<?> activityContextInterface;

	/**
	 * the concrete sbb own activity context interface class, generated by SLEE
	 */
	private Class<?> activityContextInterfaceConcreteClass;

	/**
	 * the JAIN SLEE specs descriptor
	 */
	private javax.slee.management.SbbDescriptor specsDescriptor = null;

	/**
	 * the event handler methods for this sbb component
	 */
	private Map<EventTypeID, EventHandlerMethod> eventHandlerMethods = null;
	
	/**
	 * the event handler methods for this sbb component
	 */
	private Map<String, Method> initialEventSelectorMethods = null;

	/**
	 * info about the abstract sbb class
	 */
	private final AbstractSbbClassInfo abstractSbbClassInfo = new AbstractSbbClassInfoImpl();
	
	private final boolean reentrant;
	
	/**
	 * 
	 * @param descriptor
	 */
	public SbbComponentImpl(SbbDescriptorImpl descriptor) {
		this.descriptor = descriptor;
		isolateSecurityPermissionsInLocalInterface = descriptor.getSbbLocalInterface()==null ? false : descriptor.getSbbLocalInterface().isIsolateSecurityPermissions();
		reentrant = descriptor.getSbbAbstractClass().isReentrant();
	}

	/**
	 * Retrieves the sbb descriptor
	 * 
	 * @return
	 */
	public SbbDescriptorImpl getDescriptor() {
		return descriptor;
	}

	/**
	 * Retrieves the sbb id
	 * 
	 * @return
	 */
	public SbbID getSbbID() {
		return descriptor.getSbbID();
	}

	/**
	 * Retrieves the sbb abstract class
	 * 
	 * @return
	 */
	public Class<?> getAbstractSbbClass() {
		return abstractSbbClass;
	}

	/**
	 * Retrieves the concrete sbb class, generated by SLEE
	 * 
	 * @return
	 */
	public Class<?> getConcreteSbbClass() {
		return concreteSbbClass;
	}

	/**
	 * This must never return null, if no custom interface is defined, this has
	 * to return generic javax.slee.SbbLocalObject FIXME emmartins: this should
	 * return null, since in runtime it will avoid some instanceof for sure
	 * 
	 * @return
	 */
	public Class<?> getSbbLocalInterfaceClass() {
		return sbbLocalInterfaceClass;
	}

	/**
	 * Retrieves the concrete sbb local interface class, generated by SLEE
	 * 
	 * @return
	 */
	public Class<?> getSbbLocalInterfaceConcreteClass() {
		return sbbLocalInterfaceConcreteClass;
	}

	/**
	 * Retrieves the sbb own activity context interface
	 * 
	 * @return
	 */
	public Class<?> getActivityContextInterface() {
		return activityContextInterface;
	}

	/**
	 * Retrieves the concrete sbb own activity context interface class,
	 * generated by SLEE
	 * 
	 * @return
	 */
	public Class<?> getActivityContextInterfaceConcreteClass() {
		return activityContextInterfaceConcreteClass;
	}

	/**
	 * Sets the sbb abstract class
	 * 
	 * @param abstractSbbClass
	 */
	public void setAbstractSbbClass(Class<?> abstractSbbClass) {
		this.abstractSbbClass = abstractSbbClass;
	}

	/**
	 * Sets the concrete sbb class, generated by SLEE
	 * 
	 * @param concreteSbbClass
	 */
	public void setConcreteSbbClass(Class<?> concreteSbbClass) {
		this.concreteSbbClass = concreteSbbClass;
		// build the map of event handler methods, and IES methods, this actualy can be done in one step but its clearer that way, isnt it ?
		buildEventHandlerRefs();
		buildInitialEventSelectorRefs();
	}

	/**
	 * Sets the sbb local interface
	 * 
	 * @param sbbLocalInterfaceClass
	 */
	public void setSbbLocalInterfaceClass(Class<?> sbbLocalInterfaceClass) {
		this.sbbLocalInterfaceClass = sbbLocalInterfaceClass;
	}

	/**
	 * Sets the concrete sbb local interface class, generated by SLEE
	 * 
	 * @param sbbLocalInterfaceConcreteClass
	 */
	public void setSbbLocalInterfaceConcreteClass(Class<?> sbbLocalInterfaceConcreteClass) {
		this.sbbLocalInterfaceConcreteClass = sbbLocalInterfaceConcreteClass;
	}

	/**
	 * Sets the sbb own activity context interface
	 * 
	 * @param activityContextInterface
	 */
	public void setActivityContextInterface(Class<?> activityContextInterface) {
		this.activityContextInterface = activityContextInterface;
	}

	/**
	 * Sets the concrete sbb own activity context interface class, generated by
	 * SLEE
	 * 
	 * @param activityContextInterfaceConcreteClass
	 */
	public void setActivityContextInterfaceConcreteClass(Class<?> activityContextInterfaceConcreteClass) {
		this.activityContextInterfaceConcreteClass = activityContextInterfaceConcreteClass;
	}

	@Override
	public boolean isSlee11() {
		return this.descriptor.isSlee11();
	}

	@Override
	public boolean addToDeployableUnit() {
		return getDeployableUnit().getSbbComponents().put(getSbbID(), this) == null;
	}

	@Override
	public Set<ComponentID> getDependenciesSet() {
		return descriptor.getDependenciesSet();
	}

	@Override
	public ComponentID getComponentID() {
		return getSbbID();
	}

	@Override
	public boolean validate() throws DependencyException, DeploymentException {
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(this);
		validator.setComponentRepository(getDeployableUnit().getDeployableUnitRepository());
		return validator.validate();
	}

	/**
	 * Retrieves the JAIN SLEE specs descriptor
	 * 
	 * @return
	 */
	public javax.slee.management.SbbDescriptor getSpecsDescriptor() {
		if (specsDescriptor == null) {

			final LibraryID[] libraryIDs = descriptor.getLibraryRefs().toArray(new LibraryID[descriptor.getLibraryRefs().size()]);

			Set<SbbID> sbbIDSet = new HashSet<SbbID>();
			for (SbbRefDescriptor mSbbRef : descriptor.getSbbRefs()) {
				sbbIDSet.add(mSbbRef.getComponentID());
			}
			SbbID[] sbbIDs = sbbIDSet.toArray(new SbbID[sbbIDSet.size()]);

			Set<ProfileSpecificationID> profileSpecSet = new HashSet<ProfileSpecificationID>();
			for (ProfileSpecRefDescriptor mProfileSpecRef : descriptor.getProfileSpecRefs()) {
				profileSpecSet.add(mProfileSpecRef.getComponentID());
			}
			ProfileSpecificationID[] profileSpecs = profileSpecSet.toArray(new ProfileSpecificationID[profileSpecSet.size()]);

			Set<EventTypeID> eventTypeSet = new HashSet<EventTypeID>();
			for (EventEntryDescriptor mEventEntry : descriptor.getEventEntries().values()) {
				eventTypeSet.add(mEventEntry.getEventReference());
			}
			EventTypeID[] eventTypes = eventTypeSet.toArray(new EventTypeID[eventTypeSet.size()]);

			Set<ResourceAdaptorTypeID> raTypeIDSet = new HashSet<ResourceAdaptorTypeID>();
			Set<String> raLinksSet = new HashSet<String>();
			for (ResourceAdaptorTypeBindingDescriptor mResourceAdaptorTypeBinding : descriptor.getResourceAdaptorTypeBindings()) {
				raTypeIDSet.add(mResourceAdaptorTypeBinding.getResourceAdaptorTypeRef());
				for (ResourceAdaptorEntityBindingDescriptor mResourceAdaptorEntityBinding : mResourceAdaptorTypeBinding.getResourceAdaptorEntityBinding()) {
					raLinksSet.add(mResourceAdaptorEntityBinding.getResourceAdaptorEntityLink());
				}
			}
			ResourceAdaptorTypeID[] raTypeIDs = raTypeIDSet.toArray(new ResourceAdaptorTypeID[raTypeIDSet.size()]);
			String[] raLinks = raLinksSet.toArray(new String[raLinksSet.size()]);

			specsDescriptor = new javax.slee.management.SbbDescriptor(getSbbID(), getDeployableUnit().getDeployableUnitID(), getDeploymentUnitSource(), libraryIDs, sbbIDs, eventTypes, profileSpecs, 
					descriptor.getAddressProfileSpecRef(), raTypeIDs, raLinks);
		}
		return specsDescriptor;
	}

	@Override
	public ComponentDescriptor getComponentDescriptor() {
		return getSpecsDescriptor();
	}

	/**
	 * Retrieves the evetn handler methods for this sbb component, mapped by
	 * event type id
	 * 
	 * @return
	 */
	public Map<EventTypeID, EventHandlerMethod> getEventHandlerMethods() {
		return eventHandlerMethods;
	}
	
	/**
	 * Retrieves the evetn handler methods for this sbb component, mapped by
	 * event type id
	 * 
	 * @return
	 */
	public Map<String, Method> getInitialEventSelectorMethods() {
		return initialEventSelectorMethods;
	}
	
	/**
	 *  
	 * @return the abstractSbbClassInfo
	 */
	public AbstractSbbClassInfo getAbstractSbbClassInfo() {
		return abstractSbbClassInfo;
	}
	
	@Override
	public void processSecurityPermissions() throws DeploymentException {
		try {
			if (this.descriptor.getSecurityPermissions() != null) {
				super.permissions.add(new PermissionHolderImpl(super.getDeploymentDir().toURI(), this.descriptor.getSecurityPermissions()));
			}
		} catch (Exception e) {
			throw new DeploymentException("Failed to make permissions usable.", e);
		}
	}
	
	@Override
	public void undeployed() {
		super.undeployed();
		abstractSbbClass = null;
		activityContextInterface = null;
		activityContextInterfaceConcreteClass = null;
		concreteSbbClass = null;
		if (eventHandlerMethods != null) {
			eventHandlerMethods.clear();
			eventHandlerMethods = null;
		}
		sbbLocalInterfaceClass = null;
		sbbLocalInterfaceConcreteClass = null;
		sbbLocalObjectClassConstructor = null;
		specsDescriptor = null;
	}
	
	private void buildEventHandlerRefs()
	{ 
		eventHandlerMethods = new HashMap<EventTypeID, EventHandlerMethod>();
		for (EventEntryDescriptor eventEntry : descriptor.getEventEntries().values()) {
			if (eventEntry.isReceived()) {
				String eventHandlerMethodName = "on" + eventEntry.getEventName();
				for (Method method : concreteSbbClass.getMethods()) {
					if (method.getName().equals(eventHandlerMethodName)) {
						EventHandlerMethod eventHandlerMethod = new EventHandlerMethod(method);
						if (method.getParameterTypes().length == 3) {
							eventHandlerMethod.setHasEventContextParam(true);
						}
						if (descriptor.getSbbActivityContextInterface() != null) {
							eventHandlerMethod.setHasCustomACIParam(true);
						}
						eventHandlerMethods.put(eventEntry.getEventReference(), eventHandlerMethod);
						break;
					}
				}
			}
		}
	}
	private void buildInitialEventSelectorRefs()
	{
		initialEventSelectorMethods = new HashMap<String, Method>();
		Class<?>[] argtypes = new Class[] { InitialEventSelector.class };
		for (EventEntryDescriptor eventEntry : descriptor.getEventEntries().values()) {
			if (eventEntry.isReceived() && eventEntry.isInitialEvent() && eventEntry.getInitialEventSelectorMethod() != null && !this.initialEventSelectorMethods.containsKey(eventEntry.getInitialEventSelectorMethod())) {
			
				for (Method method : concreteSbbClass.getMethods()) {
					if (method.getName().equals(eventEntry.getInitialEventSelectorMethod()) && Arrays.equals(method.getParameterTypes(),argtypes)) {
						this.initialEventSelectorMethods.put(eventEntry.getInitialEventSelectorMethod(), method);
						break;
					}
				}
			}
		}
	}

	/**
	 * cached value of the isolate security permissions property of the sbb
	 * local interface in the sbb descriptor
	 */
	private final boolean isolateSecurityPermissionsInLocalInterface;

	/**
	 * Provides a shortcut to the value of the isolate security permissions
	 * property of the sbb local interface in the sbb descriptor.
	 * 
	 * @return the isolateSecurityPermissionsInLocalInterface
	 */
	public boolean isolateSecurityPermissionsInLocalInterface() {
		return isolateSecurityPermissionsInLocalInterface;
	}
	
	/**
	 * the constructor for the SLEE generated implementation class of the custom sbb local object interface
	 */
	private Constructor<?> sbbLocalObjectClassConstructor;
	
	/**
	 * Retrieves the constructor for the SLEE generated implementation class of the custom sbb local object interface.
	 * @return the sbbLocalObjectClassConstructor
	 */
	public Constructor<?> getSbbLocalObjectClassConstructor() {
		return sbbLocalObjectClassConstructor;
	}
	
	/**
	 * Sets the constructor for the SLEE generated implementation class of the custom sbb local object interface.
	 * @param sbbLocalObjectClassConstructor the sbbLocalObjectClassConstructor to set
	 */
	public void setSbbLocalObjectClassConstructor(
			Constructor<?> sbbLocalObjectClassConstructor) {
		this.sbbLocalObjectClassConstructor = sbbLocalObjectClassConstructor;
	}
	
	/* (non-Javadoc)
	 * @see org.mobicents.slee.container.component.AbstractSleeComponentWithUsageParametersInterface#getUsageParametersList()
	 */
	@Override
	public List<UsageParameterDescriptor> getUsageParametersList() {
		return descriptor.getSbbUsageParametersInterface().getUsageParameter();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.component.sbb.SbbComponent#isReentrant()
	 */
	public boolean isReentrant() {
		return reentrant;
	}
}
