/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2011 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.nrtg.api.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Result of a {@link CollectionJob}. List of metricId/values pairs with minimal redundancy (timestamp, nodeId, service
 * and interface are stored only once). The list of {@link Measurement} will be generated on the fly.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 22.06.12
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class LightweightMeasurementSet implements MeasurementSet {
    private static final long serialVersionUID = 1166779403641774595L;
    private HashMap<String, String> m_values = new HashMap<String, String>();
    private int m_nodeId;
    private String m_interface, m_service;
    private Date m_timestamp = new Date();

    public LightweightMeasurementSet() {
    }

    public LightweightMeasurementSet(int nodeId, String service, String theInterface, Date timestamp) {
        setNodeId(nodeId);
        setService(service);
        setNetInterface(theInterface);
        setTimestamp(timestamp);
    }

    public void addMeasurement(String metricId, String value) {
        m_values.put(metricId, value);
    }

    @Override
    public List<Measurement> getMeasurements() {
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        for (String metricId : m_values.keySet()) {
            Measurement measurement = new DefaultMeasurement();

            measurement.setTimestamp(getTimestamp());
            measurement.setNetInterface(getNetInterface());
            measurement.setNodeId(getNodeId());
            measurement.setService(getService());
            measurement.setMetricId(metricId);
            measurement.setValue(m_values.get(metricId));

            measurements.add(measurement);
        }

        return measurements;
    }

    public void setNodeId(int nodeId) {
        m_nodeId = nodeId;
    }

    public void setNetInterface(String theInterface) {
        m_interface = theInterface;
    }

    public void setService(String service) {
        m_service = service;
    }

    public void setTimestamp(Date timestamp) {
        m_timestamp = timestamp;
    }

    public int getNodeId() {
        return m_nodeId;
    }

    public String getNetInterface() {
        return m_interface;
    }

    public String getService() {
        return m_service;
    }

    public Date getTimestamp() {
        return m_timestamp;
    }

    @Override
    public String getJson() {
    	StringBuilder buf = new StringBuilder("[");
    	
    	boolean first = true;
    	for(Measurement m : getMeasurements()) {
    		if (!first) { buf.append(","); } else { first = false; }
    		buf.append("{");
    		buf.append("'metricId'").append(":'").append(m.getMetricId()).append("'").append(",");
    		buf.append("'netInterface'").append(":'").append(m.getNetInterface()).append("'").append(",");
    		buf.append("'nodeId'").append(":").append(m.getNodeId()).append(",");
    		buf.append("'service'").append(":'").append(m.getService()).append("'").append(",");
    		buf.append("'timestamp'").append(":").append(m.getTimestamp().getTime()).append(",");
    		buf.append("'value'").append(":'").append(m.getValue()).append("'");
    		buf.append("}");
    	}
    	
    	buf.append("]");
    	return buf.toString();
    }


    /**
     * This toString method is for displaying reasons in the webapp NrtGrapher only.
     * It's for prototyping only.
     *
     * @return a {@link String} that contains the metrics and there values in a easy parsable way.
     */
    @Override
    public String toString() {
        return this.getJson();
    }
}
