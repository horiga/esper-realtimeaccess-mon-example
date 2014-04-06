package org.horiga.study.test.eventprocessing.esper.subscriber;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUpdateSubscriber implements UpdateSubscriber {

	private static Logger log = LoggerFactory
			.getLogger(SimpleUpdateSubscriber.class);
	
	public void update(Map<String, Object> event) {
		log.info(">>>< [start - subscribe:handleUpdate] - {}", this.getClass().getName());
		for ( Map.Entry<String, Object> entity : event.entrySet()) {
			log.debug("- events: {}={}", entity.getKey(), entity.getValue());
		}
		log.info("<<<< [end - subscribe:handleUpdate] - {}", this.getClass().getName());
	}
}
