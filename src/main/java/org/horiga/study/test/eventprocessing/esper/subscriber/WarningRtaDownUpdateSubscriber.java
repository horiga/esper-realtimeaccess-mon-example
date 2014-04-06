package org.horiga.study.test.eventprocessing.esper.subscriber;

import java.util.Map;

import org.horiga.study.test.eventprocessing.esper.event.TimedRtaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WarningRtaDownUpdateSubscriber extends SimpleUpdateSubscriber {

	private static Logger log = LoggerFactory
			.getLogger(WarningRtaDownUpdateSubscriber.class);
	
	public void update(Map<String, Object> event) {
		log.error("!!!! Warning !!!");
		TimedRtaEvent evt1 = (TimedRtaEvent)event.get("access1");
		TimedRtaEvent evt2 = (TimedRtaEvent)event.get("access2");
		TimedRtaEvent evt3 = (TimedRtaEvent)event.get("access3");
		log.error("[{}]={}", evt1.getApp(), evt1.getAccess());
		log.error("[{}]={}", evt2.getApp(), evt2.getAccess());
		log.error("[{}]={}", evt3.getApp(), evt3.getAccess());
		log.error("!!!! Warning !!!");
	}

}
