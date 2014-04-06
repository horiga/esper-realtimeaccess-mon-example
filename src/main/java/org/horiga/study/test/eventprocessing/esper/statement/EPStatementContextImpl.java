package org.horiga.study.test.eventprocessing.esper.statement;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.horiga.study.test.eventprocessing.esper.subscriber.UpdateSubscriber;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.UpdateListener;
import com.google.common.collect.Lists;

@Component
public class EPStatementContextImpl implements EPStatementContext {

	private String name;
	
	private String epl;
	
	private UpdateSubscriber subscriber;
	
	private List<Object> listeners = Lists.newLinkedList();
	
	public String name() {
		return name;
	}
	
	public String epl() {
		return epl;
	}

	public UpdateSubscriber subscriber() {
		return subscriber;
	}

	public List<Object> listeners() {
		return listeners;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEpl(String epl) {
		this.epl = epl;
	}
	
	public void setSubscriber( UpdateSubscriber subscriber) {
		this.subscriber = subscriber;
	}
	
	public void setListeners(List<Object> listeners) {
		this.listeners = listeners;
	}
	
	public void addListener(UpdateListener listener) {
		this.listeners.add(listener);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("epl.statement:").append(epl);
		sb.append(", subscriber:").append(subscriber != null ? subscriber.getClass().getName() : "N/A");
		sb.append(", listeners:").append(listeners != null ? StringUtils.join(listeners, "|") : "N/A");
		return sb.toString();
	}
	
}
