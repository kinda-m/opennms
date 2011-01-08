//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2005 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
// OpenNMS Licensing       <license@opennms.org>
//     http://www.opennms.org/
//     http://www.opennms.com/
//
package org.opennms.netmgt.poller;

import java.net.InetAddress;


/**
 * <p>MonitoredService interface.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public interface MonitoredService {

    /**
     * Returns a URL representation of the service to monitor and its parameters
     */
    String getSvcUrl();

    /**
     * Returns the svcName associated with this monitored service
     *
     * @return the svcName
     */
    String getSvcName();

    /**
     * Returns the ipAddr string associated with this monitored service
     *
     * @return the ipAddr string
     */
    String getIpAddr();

    /**
     * Returns the nodeId of the node that this service is associated with
     *
     * @return the nodeid
     */
    int getNodeId();

    /**
     * Returns the label of the node that this service is associated with
     *
     * @return the nodelabel
     */
    String getNodeLabel();

    /**
     * Returns the Netinterface object for this service.  This netinterface object is
     * guarenteed to be the same each time init or poll is called
     *
     * @return the Netinterface
     */
    NetworkInterface<InetAddress> getNetInterface();

    /**
     * Returns the InetAddress associated with the service
     *
     * @return the InetAddress
     */
    InetAddress getAddress();

}
