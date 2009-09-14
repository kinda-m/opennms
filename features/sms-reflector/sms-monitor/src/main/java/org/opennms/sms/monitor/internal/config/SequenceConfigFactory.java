package org.opennms.sms.monitor.internal.config;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SequenceConfigFactory {
	private static SequenceConfigFactory m_singleton = null;
	private JAXBContext m_context;
	private Unmarshaller m_unmarshaller;
	private Marshaller m_marshaller;

	private SequenceConfigFactory() {
	}

	public static synchronized SequenceConfigFactory getInstance() {
		if (m_singleton == null) {
			m_singleton = new SequenceConfigFactory();
		}
		return m_singleton;
	}

	protected synchronized void initializeContext() throws JAXBException {
		if (m_context == null) {
			m_context = JAXBContext.newInstance(
					MobileSequenceConfig.class,
	    			SmsSequenceRequest.class,
	    			UssdSequenceRequest.class,
	    			SmsSequenceResponse.class,
	    			UssdSequenceResponse.class,
	    			SmsFromRecipientResponseMatcher.class,
	    			SmsSourceMatcher.class,
	    			TextResponseMatcher.class,
	    			UssdSessionStatusMatcher.class
			);
			m_marshaller = m_context.createMarshaller();
			m_unmarshaller = m_context.createUnmarshaller();
			
	    	m_marshaller = m_context.createMarshaller();
	    	m_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        m_marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MobileSequenceNamespacePrefixMapper());

	    	m_unmarshaller = m_context.createUnmarshaller();
	    	m_unmarshaller.setSchema(null);
		}
	}
	
	public MobileSequenceConfig getSequenceForXml(String sequenceXml) throws SequenceException {
		try {
			initializeContext();
			Reader r = new StringReader(sequenceXml);
			m_unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			MobileSequenceConfig s = (MobileSequenceConfig)m_unmarshaller.unmarshal(r);
	    	return s;
		} catch (JAXBException e) {
			throw new SequenceException("An error occurred reading the sequence.", e);
		}
	}

	public MobileSequenceConfig getSequenceForFile(File sequenceFile) throws SequenceException {
		try {
			initializeContext();
			return (MobileSequenceConfig)m_unmarshaller.unmarshal(sequenceFile);
		} catch (JAXBException e) {
			throw new SequenceException("An error occurred reading the sequence from " + sequenceFile.getPath(), e);
		}
	}
}
