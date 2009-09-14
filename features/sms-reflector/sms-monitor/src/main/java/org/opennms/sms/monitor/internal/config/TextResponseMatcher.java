package org.opennms.sms.monitor.internal.config;

import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.sms.reflector.smsservice.MobileMsgResponseMatcher;
import org.opennms.sms.reflector.smsservice.MobileMsgResponseMatchers;

@XmlRootElement(name="matches")
public class TextResponseMatcher extends SequenceResponseMatcher {

	public TextResponseMatcher() {
	}

	public TextResponseMatcher(String text) {
		this();
		setText(text);
	}

	@Override
	public MobileMsgResponseMatcher getMatcher() {
		return MobileMsgResponseMatchers.textMatches(getText());
	}

}
