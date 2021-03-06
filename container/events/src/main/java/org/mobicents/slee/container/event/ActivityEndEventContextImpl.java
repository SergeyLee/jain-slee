/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.slee.container.event;

import javax.slee.resource.FailureReason;

/**
 * Useful activity end event version of the {@link EventContextImpl}.
 * 
 * @author martins
 *
 */
public class ActivityEndEventContextImpl extends EventContextImpl {

	/**
	 * 
	 * @param ac
	 */
	public ActivityEndEventContextImpl(EventContextData data, EventContextFactoryImpl factory) {
		super(data,factory);		
	}
	
	/* (non-Javadoc)
	 * @see org.mobicents.slee.runtime.eventrouter.SleeEventImpl#isActivityEndEvent()
	 */
	@Override
	public boolean isActivityEndEvent() {
		return true;
	}
	
	@Override
	public void eventProcessingFailed(FailureReason reason) {
		// TODO this should ensure AC is removed from SLEE, perhaps after some timeout (if ac exists but ending state persists)
		super.eventProcessingFailed(reason);
	}
	
	@Override
	public void canceled() {
		// just remove event context, don't remove reference, otherwise unreferenced callback may end AC
		remove();
	}
}
