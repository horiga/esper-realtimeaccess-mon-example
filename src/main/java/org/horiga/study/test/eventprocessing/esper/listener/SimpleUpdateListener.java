package org.horiga.study.test.eventprocessing.esper.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.scopetest.SupportUpdateListener;

public class SimpleUpdateListener extends SupportUpdateListener {
	
	private static AtomicInteger id = new AtomicInteger(1);
	
	private static Logger log = LoggerFactory
			.getLogger(SimpleUpdateListener.class);
	
	private final String name;
	
	public SimpleUpdateListener() {
		super();
		this.name = "esper-update-listener#" + id.incrementAndGet();
	}
	
	public SimpleUpdateListener(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		
		log.debug("[>>>> {} - Listener - update]", name);
		
		//super.update(newData, oldData);
		
		/*
		 * saved RDB summarized-data 
		 */
		
		if (newData != null) {
			for (EventBean data : newData) {
				log.debug("[newEvent] : {}", toEventBeanString(data));
			}
		}
		if (oldData != null) {
			for (EventBean data : oldData) {
				log.debug("[oldEvent] : {}", toEventBeanString(data));
			}
		}
		
		log.debug("[<<<< {} - Listener - update]", name);
	}
	
	private static String toEventBeanString(EventBean bean) {
		StringBuilder sb = new StringBuilder();
		String[] propertyNames = bean.getEventType().getPropertyNames();
		sb.append("properties:").append(StringUtils.join(propertyNames, ","));
		sb.append(" $ values:");
		for ( String p : propertyNames) {
			sb.append("<").append(p).append("=").append(bean.get(p)).append(">");
		}
		return sb.toString();
	}
	
	public String toString() {
		return "" + name + "";
	}
}
